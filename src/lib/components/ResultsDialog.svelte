<script lang="ts">
	import { resolve } from '$app/paths';
	import { m } from '#lib/paraglide/messages.js';
	import { getLocale } from '#lib/paraglide/runtime.js';
	import type { Stats } from '#lib/core/engine.ts';
	import { formatDuration } from '#lib/core/format.ts';

	/** `onclose` est appelé quand la fenêtre se ferme (bouton Rejouer ou Échap). */
	let { stats, onclose }: { stats: Stats; onclose: () => void } = $props();

	const integer = new Intl.NumberFormat(getLocale(), { maximumFractionDigits: 0 });
	const percent = new Intl.NumberFormat(getLocale(), { style: 'percent' });

	const rows = $derived([
		[m.stat_time(), formatDuration(stats.elapsedMs)],
		[m.stat_wpm(), integer.format(stats.wpm)],
		[m.stat_accuracy(), percent.format(stats.accuracy)],
		[m.stat_correct_words(), integer.format(stats.correctWords)],
		[m.stat_incorrect_words(), integer.format(stats.incorrectWords)],
		[m.stat_correct_inputs(), integer.format(stats.correctKeystrokes)],
		[m.stat_incorrect_inputs(), integer.format(stats.incorrectKeystrokes)]
	]);
</script>

<dialog aria-labelledby="results-title" {onclose} {@attach (dialog) => dialog.showModal()}>
	<h2 id="results-title">{m.results_title()}</h2>
	<dl>
		{#each rows as [label, value] (label)}
			<div>
				<dt>{label}</dt>
				<dd>{value}</dd>
			</div>
		{/each}
	</dl>
	<form method="dialog">
		<button class="button">{m.results_replay()}</button>
		<a class="button" href={resolve('/')}>{m.game_menu()}</a>
	</form>
</dialog>

<style>
	dialog {
		min-width: min(90vw, 22rem);
		border: none;
		border-radius: var(--radius);
		background: var(--surface);
		color: var(--text);
	}

	dialog::backdrop {
		background: rgb(0 0 0 / 0.5);
	}

	h2 {
		margin-top: 0;
	}

	dl {
		display: grid;
		gap: 0.25rem;
		margin: 0 0 1.5rem;
	}

	dl div {
		display: flex;
		justify-content: space-between;
		gap: 2rem;
	}

	dt {
		color: var(--text-muted);
	}

	dd {
		margin: 0;
		font-weight: 600;
		font-variant-numeric: tabular-nums;
	}

	form {
		display: flex;
		gap: 0.75rem;
		justify-content: flex-end;
	}
</style>
