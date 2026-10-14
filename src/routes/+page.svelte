<script lang="ts">
	import { resolve } from '$app/paths';
	import { m } from '#lib/paraglide/messages.js';
	import MenuPage from '#lib/components/MenuPage.svelte';
</script>

<MenuPage title="typ" subtitle={m.tagline()} back={false}>
	<nav>
		<a class="button" href={resolve('/play')}>{m.menu_play()}</a>
		<a class="button" href={resolve('/stats')}>{m.menu_stats()}</a>
		<a class="button" href={resolve('/settings')}>{m.menu_settings()}</a>
	</nav>
</MenuPage>

<style>
	nav {
		display: grid;
		gap: 0.75rem;
		width: min(100%, 16rem);
	}
</style>
