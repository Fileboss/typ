import { describe, expect, it } from 'vitest';
import { defaultSettings, parseSettings } from './settings.svelte.ts';

describe('parseSettings', () => {
	it('conserve des réglages valides', () => {
		const saved = {
			textLang: 'de',
			theme: 'light',
			fontSize: 32,
			classicWordCount: 50,
			timedSeconds: 60
		};
		expect(parseSettings(saved)).toEqual(saved);
	});

	it('remplace toute valeur invalide ou absente par sa valeur par défaut', () => {
		expect(parseSettings('corrompu')).toEqual(defaultSettings);
		expect(
			parseSettings({ textLang: 'xx', theme: 'neon', fontSize: 500, classicWordCount: 7 })
		).toEqual(defaultSettings);
	});
});
