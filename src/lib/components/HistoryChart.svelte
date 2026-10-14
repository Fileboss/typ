<script lang="ts">
	import uPlot from 'uplot';
	import 'uplot/dist/uPlot.min.css';
	import type { Attachment } from 'svelte/attachments';
	import { m } from '#lib/paraglide/messages.js';
	import type { GameRecord } from '#lib/history.ts';

	let { games }: { games: readonly GameRecord[] } = $props();

	/** Crée le graphique ; l'attachement est rejoué si `games` change. */
	const chart: Attachment<HTMLElement> = (node) => {
		const css = getComputedStyle(node);
		const color = (name: string) => css.getPropertyValue(name).trim();
		const axis = { stroke: color('--text-muted'), grid: { stroke: color('--surface') } };

		const plot = new uPlot(
			{
				width: node.clientWidth,
				height: 260,
				scales: { x: { time: false }, accuracy: { range: [0, 100] } },
				axes: [
					// Numéros de partie : graduations entières uniquement
					{ ...axis, incrs: [1, 2, 5, 10, 20, 50] },
					{ ...axis, size: 60 },
					{ ...axis, scale: 'accuracy', side: 1, grid: { show: false } }
				],
				series: [
					{ label: '#' },
					{ label: m.stat_wpm(), stroke: color('--accent'), width: 2 },
					{ label: `${m.stat_accuracy()} (%)`, stroke: color('--ok'), width: 2, scale: 'accuracy' }
				]
			},
			[
				games.map((_, i) => i + 1),
				games.map((game) => Math.round(game.wpm)),
				games.map((game) => Math.round(game.accuracy * 100))
			],
			node
		);

		const observer = new ResizeObserver(() =>
			plot.setSize({ width: node.clientWidth, height: 260 })
		);
		observer.observe(node);

		return () => {
			observer.disconnect();
			plot.destroy();
		};
	};
</script>

<figure>
	<figcaption>{m.stats_chart()}</figcaption>
	<div {@attach chart}></div>
</figure>

<style>
	figure {
		width: 100%;
		margin: 0;
	}

	figcaption {
		margin-bottom: 0.5rem;
		color: var(--text-muted);
	}

	div :global(.u-legend) {
		color: var(--text);
	}
</style>
