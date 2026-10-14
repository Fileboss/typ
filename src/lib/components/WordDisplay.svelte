<script lang="ts">
	import type { WordResult } from '#lib/core/engine.ts';

	let {
		words,
		results,
		input
	}: { words: readonly string[]; results: readonly WordResult[]; input: string } = $props();

	const index = $derived(results.length);
	const current = $derived(words[index] ?? '');

	/** Lettres du mot courant, plus les lettres tapées en trop. */
	const chars = $derived(
		Array.from({ length: Math.max(current.length, input.length) }, (_, i) => ({
			char: current[i] ?? input[i],
			state: i >= input.length ? 'pending' : input[i] === current[i] ? 'ok' : 'ko',
			caret: i === input.length
		}))
	);

	/** Fait défiler le texte pour garder la ligne du mot courant visible. */
	function keepInView(word: HTMLElement) {
		const box = word.parentElement;
		if (!box) return;
		const lineHeight = word.offsetHeight + parseFloat(getComputedStyle(box).rowGap || '0');
		box.scrollTop = Math.max(0, word.offsetTop - lineHeight);
	}
</script>

<div class="words">
	{#each words as word, i (i)}
		{#if i < index}
			<span class="word" data-word={word} data-state={results[i].correct ? 'ok' : 'ko'}>{word}</span
			>
		{:else if i === index}
			<span class="word" data-word={word} data-state="current" {@attach keepInView}>
				{#each chars as { char, state, caret }, j (j)}
					<span data-state={state} class={{ caret }}>{char}</span>
				{/each}
				{#if input.length >= chars.length}<span class="caret end"></span>{/if}
			</span>
		{:else}
			<span class="word" data-word={word} data-state="pending">{word}</span>
		{/if}
	{/each}
</div>

<style>
	.words {
		--line: 1.7em;
		position: relative;
		display: flex;
		flex-wrap: wrap;
		column-gap: 0.65em;
		height: calc(3 * var(--line));
		overflow: hidden;
		font-family: var(--font-text);
		font-size: var(--text-size);
		line-height: var(--line);
	}

	[data-state='pending'] {
		color: var(--pending);
	}

	[data-state='ok'] {
		color: var(--ok);
	}

	[data-state='ko'] {
		color: var(--ko);
	}

	.word[data-state='ko'] {
		text-decoration: underline wavy;
		text-underline-offset: 0.25em;
	}

	.word[data-state='current'] {
		padding-inline: 0.15em;
		border-radius: 4px;
		background: var(--surface-strong);
	}

	.caret {
		box-shadow: inset 2px 0 0 var(--focus);
	}

	.caret.end {
		display: inline-block;
		width: 2px;
		height: 1em;
		vertical-align: -0.1em;
		box-shadow: none;
		background: var(--focus);
	}

	@media (prefers-reduced-motion: no-preference) {
		.caret.end {
			animation: blink 1s steps(1) infinite;
		}
	}

	@keyframes blink {
		50% {
			opacity: 0;
		}
	}
</style>
