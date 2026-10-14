<script lang="ts">
	import { m } from '#lib/paraglide/messages.js';
	import { getLocale } from '#lib/paraglide/runtime.js';
	import { formatDuration } from '#lib/core/format.ts';
	import { clearGames, listGames, type GameRecord } from '#lib/history.ts';
	import { languageName, modeName } from '#lib/i18n.ts';
	import HistoryChart from '#lib/components/HistoryChart.svelte';
	import MenuPage from '#lib/components/MenuPage.svelte';

	const CHART_SIZE = 50;
	const TABLE_SIZE = 20;

	let games = $state(listGames());
	let confirming = $state(false);

	const integer = new Intl.NumberFormat(getLocale(), { maximumFractionDigits: 0 });
	const percent = new Intl.NumberFormat(getLocale(), { style: 'percent' });
	const date = new Intl.DateTimeFormat(getLocale(), { dateStyle: 'short', timeStyle: 'short' });

	function summarize(list: readonly GameRecord[]) {
		const average = (pick: (game: GameRecord) => number) =>
			list.reduce((sum, game) => sum + pick(game), 0) / list.length;
		return [
			[m.stats_games(), integer.format(list.length)],
			[m.stats_best_wpm(), integer.format(Math.max(...list.map((game) => game.wpm)))],
			[m.stats_avg_wpm(), integer.format(average((game) => game.wpm))],
			[m.stats_avg_accuracy(), percent.format(average((game) => game.accuracy))]
		];
	}

	async function clear() {
		if (!confirming) {
			confirming = true;
			return;
		}
		await clearGames();
		confirming = false;
		games = listGames();
	}
</script>

<MenuPage title={m.stats_title()}>
	{#await games then list}
		{#if list.length === 0}
			<p>{m.stats_empty()}</p>
		{:else}
			<dl>
				{#each summarize(list) as [label, value] (label)}
					<div>
						<dt>{label}</dt>
						<dd>{value}</dd>
					</div>
				{/each}
			</dl>

			<HistoryChart games={list.slice(-CHART_SIZE)} />

			<div class="table">
				<table>
					<caption>{m.stats_recent()}</caption>
					<thead>
						<tr>
							<th scope="col">{m.stats_date()}</th>
							<th scope="col">{m.stats_mode()}</th>
							<th scope="col">{m.stats_lang()}</th>
							<th scope="col">{m.stat_wpm()}</th>
							<th scope="col">{m.stat_accuracy()}</th>
							<th scope="col">{m.stat_correct_words()}</th>
							<th scope="col">{m.stat_incorrect_words()}</th>
							<th scope="col">{m.stat_time()}</th>
						</tr>
					</thead>
					<tbody>
						{#each list.slice(-TABLE_SIZE).reverse() as game (game.id)}
							<tr>
								<td>{date.format(game.playedAt)}</td>
								<td>{modeName(game.mode)}</td>
								<td>{languageName(game.lang)}</td>
								<td>{integer.format(game.wpm)}</td>
								<td>{percent.format(game.accuracy)}</td>
								<td>{game.correctWords}</td>
								<td>{game.incorrectWords}</td>
								<td>{formatDuration(game.elapsedMs)}</td>
							</tr>
						{/each}
					</tbody>
				</table>
			</div>

			<button
				class={['button', confirming && 'danger']}
				onclick={clear}
				onblur={() => (confirming = false)}
			>
				{confirming ? m.stats_clear_confirm() : m.stats_clear()}
			</button>
		{/if}
	{:catch}
		<p role="alert">{m.stats_load_error()}</p>
	{/await}
</MenuPage>

<style>
	dl {
		display: flex;
		flex-wrap: wrap;
		justify-content: center;
		gap: 2rem;
		margin: 0;
	}

	dt {
		font-size: 0.8rem;
		color: var(--text-muted);
	}

	dd {
		margin: 0;
		font-size: 1.75rem;
		font-weight: 700;
		color: var(--accent);
		font-variant-numeric: tabular-nums;
	}

	.table {
		width: 100%;
		overflow-x: auto;
	}

	table {
		width: 100%;
		border-collapse: collapse;
		font-variant-numeric: tabular-nums;
	}

	caption {
		margin-bottom: 0.5rem;
		color: var(--text-muted);
		text-align: start;
	}

	th,
	td {
		padding: 0.4rem 0.6rem;
		border-bottom: 1px solid var(--surface);
		text-align: start;
		white-space: nowrap;
	}

	th {
		color: var(--text-muted);
		font-weight: 600;
	}
</style>
