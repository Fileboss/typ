<script lang="ts">
	import { m } from '#lib/paraglide/messages.js';
	import { getLocale, isLocale, locales, setLocale } from '#lib/paraglide/runtime.js';
	import { textLangs } from '#lib/core/text-source.ts';
	import { languageName } from '#lib/i18n.ts';
	import {
		classicWordCounts,
		fontSizes,
		getSettings,
		themes,
		timedDurations,
		type Theme
	} from '#lib/settings.svelte.ts';
	import MenuPage from '#lib/components/MenuPage.svelte';

	const settings = getSettings();

	const themeNames: Record<Theme, () => string> = {
		dark: () => m.theme_dark(),
		light: () => m.theme_light()
	};
</script>

<MenuPage title={m.settings_title()}>
	<form onsubmit={(event) => event.preventDefault()}>
		<label>
			<span>{m.settings_ui_language()}</span>
			<!-- Change la langue puis recharge la page (stratégie localStorage de Paraglide) -->
			<select
				value={getLocale()}
				onchange={(event) => {
					const locale = event.currentTarget.value;
					if (isLocale(locale)) setLocale(locale);
				}}
			>
				{#each locales as locale (locale)}
					<option value={locale}>{languageName(locale)}</option>
				{/each}
			</select>
		</label>

		<label>
			<span>{m.settings_text_language()}</span>
			<select bind:value={settings.textLang}>
				{#each textLangs as lang (lang)}
					<option value={lang}>{languageName(lang)}</option>
				{/each}
			</select>
		</label>

		<label>
			<span>{m.settings_theme()}</span>
			<select bind:value={settings.theme}>
				{#each themes as theme (theme)}
					<option value={theme}>{themeNames[theme]()}</option>
				{/each}
			</select>
		</label>

		<label>
			<span>{m.settings_font_size()} ({settings.fontSize} px)</span>
			<input
				type="range"
				min={fontSizes.min}
				max={fontSizes.max}
				step="2"
				bind:value={settings.fontSize}
			/>
		</label>

		<p class="preview">{m.settings_preview()}</p>

		<label>
			<span>{m.settings_classic_words()}</span>
			<select bind:value={settings.classicWordCount}>
				{#each classicWordCounts as count (count)}
					<option value={count}>{count}</option>
				{/each}
			</select>
		</label>

		<label>
			<span>{m.settings_timed_seconds()}</span>
			<select bind:value={settings.timedSeconds}>
				{#each timedDurations as seconds (seconds)}
					<option value={seconds}>{seconds} s</option>
				{/each}
			</select>
		</label>
	</form>
</MenuPage>

<style>
	form {
		display: grid;
		gap: 1rem;
		width: min(100%, 26rem);
		text-align: start;
	}

	label {
		display: grid;
		gap: 0.35rem;
	}

	label span {
		color: var(--text-muted);
	}

	select {
		padding: 0.5rem;
		border: none;
		border-radius: var(--radius);
		background: var(--surface);
		color: var(--text);
		font: inherit;
	}

	input[type='range'] {
		accent-color: var(--accent);
	}

	.preview {
		margin: 0;
		padding: 0.5rem 0.75rem;
		border-radius: var(--radius);
		background: var(--surface-strong);
		color: var(--pending);
		font-family: var(--font-text);
		font-size: var(--text-size);
	}
</style>
