<script lang="ts">
	import '../app.css';
	import favicon from '#lib/assets/favicon.svg';
	import { getLocale } from '#lib/paraglide/runtime.js';
	import { isTextLang } from '#lib/core/text-source.ts';
	import {
		defaultSettings,
		loadSettings,
		saveSettings,
		setSettings
	} from '#lib/settings.svelte.ts';
	import type { LayoutProps } from './$types';

	let { children }: LayoutProps = $props();

	const locale = getLocale();
	const settings = setSettings(
		loadSettings({
			...defaultSettings,
			textLang: isTextLang(locale) ? locale : defaultSettings.textLang
		})
	);

	// Synchronisation avec l'extérieur du composant : <html> et localStorage.
	$effect(() => {
		const root = document.documentElement;
		root.dataset.theme = settings.theme;
		root.style.setProperty('--text-size', `${settings.fontSize}px`);
	});

	$effect(() => saveSettings(settings.toJSON()));
</script>

<svelte:head>
	<link rel="icon" href={favicon} />
	<title>typ</title>
</svelte:head>

<main>
	{@render children()}
</main>

<style>
	main {
		max-width: 60rem;
		margin-inline: auto;
		padding: 2rem 1rem;
	}
</style>
