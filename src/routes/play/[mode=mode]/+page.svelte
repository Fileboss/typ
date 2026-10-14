<script lang="ts">
	import GameScreen from '#lib/components/GameScreen.svelte';
	import type { PageProps } from './$types';

	let { params }: PageProps = $props();
</script>

<!-- Changer de mode recrée entièrement l'écran de jeu -->
{#key params.mode}
	<GameScreen mode={params.mode} />
{/key}
