<script lang="ts">
	import { tick } from 'svelte';
	import { resolve } from '$app/paths';
	import { m } from '#lib/paraglide/messages.js';
	import type { Stats } from '#lib/core/engine.ts';
	import { formatDuration } from '#lib/core/format.ts';
	import { modes, type ModeId } from '#lib/core/modes.ts';
	import { dictionarySource, type TextLang } from '#lib/core/text-source.ts';
	import { Game } from '#lib/game.svelte.ts';
	import { saveGame } from '#lib/history.ts';
	import { getSettings } from '#lib/settings.svelte.ts';
	import ResultsDialog from './ResultsDialog.svelte';
	import WordDisplay from './WordDisplay.svelte';

	let { mode }: { mode: ModeId } = $props();

	const settings = getSettings();

	let game = $state.raw<Game>();
	let loadFailed = $state(false);
	let inputEl = $state<HTMLInputElement>();
	let attempt = 0;

	const running = $derived(game?.running ?? false);

	/** Chronomètre en mode classique, compte à rebours en mode chrono. */
	const clock = $derived.by(() => {
		if (!game) return 0;
		const limit = game.state.timeLimitMs;
		return limit === null ? game.stats.elapsedMs : limit - game.stats.elapsedMs;
	});

	async function newGame() {
		const id = ++attempt;
		const lang = settings.textLang;
		const config = modes[mode].config(settings);
		try {
			const words = await dictionarySource(lang).load(config.wordCount);
			if (id !== attempt) return; // une partie plus récente a été demandée entre-temps
			game = new Game(words, config, (stats) => record(stats, lang));
			loadFailed = false;
			await tick();
			inputEl?.focus();
		} catch (error) {
			console.error(error);
			if (id === attempt) loadFailed = true;
		}
	}

	function record(stats: Stats, lang: TextLang) {
		saveGame({ ...stats, mode, lang, playedAt: Date.now() }).catch((error) =>
			console.error("Impossible d'enregistrer la partie", error)
		);
	}

	/** Un espace (ou tout blanc) valide le mot ; le reste de la saisie est ignoré. */
	function sync(field: HTMLInputElement) {
		if (!game) return;
		const [word, ...rest] = field.value.split(/\s/);
		game.input(word);
		if (rest.length > 0) game.submit();
		field.value = game.state.input;
	}

	function onkeydown(event: KeyboardEvent) {
		if (event.key === 'Escape' && game && !game.finished) {
			event.preventDefault();
			newGame();
		}
	}

	// Seule la limite de temps fait avancer la partie sans frappe : on la vérifie régulièrement.
	$effect(() => {
		if (!running || !game) return;
		const current = game;
		const interval = setInterval(() => current.tick(), 100);
		return () => clearInterval(interval);
	});

	newGame();
</script>

<svelte:window {onkeydown} />

<section>
	{#if game}
		<dl class="counters">
			<div>
				<dt>{m.stat_time()}</dt>
				<dd data-testid="clock">{formatDuration(clock)}</dd>
			</div>
			<div>
				<dt>{m.stat_correct_words()}</dt>
				<dd class="ok" data-testid="correct-words">{game.stats.correctWords}</dd>
			</div>
			<div>
				<dt>{m.stat_incorrect_words()}</dt>
				<dd class="ko" data-testid="incorrect-words">{game.stats.incorrectWords}</dd>
			</div>
		</dl>

		<WordDisplay words={game.state.words} results={game.state.results} input={game.state.input} />

		<label class="visually-hidden" for="typing-input">{m.game_input_label()}</label>
		<input
			id="typing-input"
			bind:this={inputEl}
			value={game.state.input}
			disabled={game.finished}
			autocomplete="off"
			autocapitalize="off"
			spellcheck="false"
			oninput={(event) => {
				// Pendant une composition (accents, ^ + e…), on attend le caractère final.
				if (!(event instanceof InputEvent && event.isComposing)) sync(event.currentTarget);
			}}
			oncompositionend={(event) => sync(event.currentTarget)}
			{@attach (field) => field.focus()}
		/>

		<p class="hint">{m.game_hint()}</p>

		{#if game.finished}
			<ResultsDialog stats={game.stats} onclose={newGame} />
		{/if}
	{:else if loadFailed}
		<p role="alert">{m.game_load_error()}</p>
	{:else}
		<p>{m.game_loading()}</p>
	{/if}

	<nav>
		<button class="button" onclick={newGame}>{m.game_restart()}</button>
		<a class="button" href={resolve('/play')}>{m.back()}</a>
	</nav>
</section>

<style>
	section {
		display: flex;
		flex-direction: column;
		gap: 1.25rem;
	}

	.counters {
		display: flex;
		flex-wrap: wrap;
		gap: 2rem;
		margin: 0;
	}

	dt {
		font-size: 0.8rem;
		color: var(--text-muted);
		text-transform: uppercase;
		letter-spacing: 0.05em;
	}

	dd {
		margin: 0;
		font-size: 1.75rem;
		font-weight: 700;
		font-variant-numeric: tabular-nums;
		color: var(--accent);
	}

	dd.ok {
		color: var(--ok);
	}

	dd.ko {
		color: var(--ko);
	}

	input {
		width: 100%;
		padding: 0.5rem 0.75rem;
		border: 2px solid transparent;
		border-radius: var(--radius);
		background: var(--surface);
		color: var(--accent);
		font-family: var(--font-text);
		font-size: var(--text-size);
	}

	input:focus {
		border-color: var(--focus);
		outline: none;
	}

	.hint {
		margin: 0;
		font-size: 0.875rem;
		color: var(--text-muted);
	}

	nav {
		display: flex;
		gap: 0.75rem;
	}
</style>
