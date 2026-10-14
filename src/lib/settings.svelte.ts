import { createContext } from 'svelte';
import { isTextLang, type TextLang } from './core/text-source.ts';

export const themes = ['dark', 'light'] as const;
export type Theme = (typeof themes)[number];

export const fontSizes = { min: 16, max: 48 } as const;
export const classicWordCounts = [10, 15, 25, 50] as const;
export const timedDurations = [15, 30, 60, 120] as const;

export type SettingsValues = {
	textLang: TextLang;
	theme: Theme;
	fontSize: number;
	classicWordCount: number;
	timedSeconds: number;
};

export const defaultSettings: SettingsValues = {
	textLang: 'fr',
	theme: 'dark',
	fontSize: 24,
	classicWordCount: 15,
	timedSeconds: 30
};

const pick = <T>(value: unknown, allowed: readonly T[], fallback: T): T =>
	allowed.includes(value as T) ? (value as T) : fallback;

/** Valide des réglages venant du stockage : toute valeur invalide est remplacée par sa valeur par défaut. */
export function parseSettings(
	raw: unknown,
	defaults: SettingsValues = defaultSettings
): SettingsValues {
	const input = (typeof raw === 'object' && raw !== null ? raw : {}) as Record<string, unknown>;
	const fontSize = Number(input.fontSize);
	return {
		textLang: isTextLang(input.textLang) ? input.textLang : defaults.textLang,
		theme: pick(input.theme, themes, defaults.theme),
		fontSize:
			Number.isInteger(fontSize) && fontSize >= fontSizes.min && fontSize <= fontSizes.max
				? fontSize
				: defaults.fontSize,
		classicWordCount: pick(input.classicWordCount, classicWordCounts, defaults.classicWordCount),
		timedSeconds: pick(input.timedSeconds, timedDurations, defaults.timedSeconds)
	};
}

export class Settings implements SettingsValues {
	textLang: TextLang;
	theme: Theme;
	fontSize: number;
	classicWordCount: number;
	timedSeconds: number;

	constructor(values: SettingsValues) {
		this.textLang = $state(values.textLang);
		this.theme = $state(values.theme);
		this.fontSize = $state(values.fontSize);
		this.classicWordCount = $state(values.classicWordCount);
		this.timedSeconds = $state(values.timedSeconds);
	}

	toJSON(): SettingsValues {
		return {
			textLang: this.textLang,
			theme: this.theme,
			fontSize: this.fontSize,
			classicWordCount: this.classicWordCount,
			timedSeconds: this.timedSeconds
		};
	}
}

const STORAGE_KEY = 'typ:settings';

export function loadSettings(defaults: SettingsValues): Settings {
	let raw: unknown = null;
	try {
		raw = JSON.parse(localStorage.getItem(STORAGE_KEY) ?? 'null');
	} catch {
		// stockage indisponible ou corrompu : on garde les valeurs par défaut
	}
	return new Settings(parseSettings(raw, defaults));
}

export function saveSettings(values: SettingsValues): void {
	try {
		localStorage.setItem(STORAGE_KEY, JSON.stringify(values));
	} catch {
		// stockage indisponible (navigation privée…) : les réglages ne durent que la session
	}
}

export const [getSettings, setSettings] = createContext<Settings>();
