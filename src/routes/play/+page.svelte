<script lang="ts">
	import { resolve } from '$app/paths';
	import { m } from '#lib/paraglide/messages.js';
	import { modeIds, type ModeId } from '#lib/core/modes.ts';
	import { modeName } from '#lib/i18n.ts';
	import { getSettings } from '#lib/settings.svelte.ts';
	import MenuPage from '#lib/components/MenuPage.svelte';

	const settings = getSettings();

	const descriptions: Record<ModeId, () => string> = {
		classic: () => m.mode_classic_desc({ count: settings.classicWordCount }),
		timed: () => m.mode_timed_desc({ seconds: settings.timedSeconds })
	};
</script>

<MenuPage title={m.play_title()}>
	<ul>
		{#each modeIds as id (id)}
			<li>
				<a class="button" href={resolve('/play/[mode=mode]', { mode: id })}>
					<strong>{modeName(id)}</strong>
					<span>{descriptions[id]()}</span>
				</a>
			</li>
		{/each}
	</ul>
</MenuPage>

<style>
	ul {
		display: grid;
		gap: 0.75rem;
		width: min(100%, 22rem);
		margin: 0;
		padding: 0;
		list-style: none;
	}

	a {
		flex-direction: column;
		gap: 0;
		width: 100%;
		padding-block: 0.75rem;
	}

	span {
		font-weight: 400;
		font-size: 0.875rem;
		opacity: 0.85;
	}
</style>
