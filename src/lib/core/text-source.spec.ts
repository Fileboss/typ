import { describe, expect, it } from 'vitest';
import { dictionarySource, parseWordList, sampleWords, textLangs } from './text-source.ts';

describe('parseWordList', () => {
	it('garde un mot par ligne, sans vide, expression ni doublon', () => {
		expect(parseWordList('le\r\nchat\n\n  dort \npor favor\nchat\n')).toEqual([
			'le',
			'chat',
			'dort'
		]);
	});
});

describe('sampleWords', () => {
	it('ne répète aucun mot tant que la liste suffit', () => {
		const pool = Array.from({ length: 100 }, (_, i) => `mot${i}`);
		const words = sampleWords(pool, 100);
		expect(new Set(words).size).toBe(100);
	});

	it('repart sur un nouveau tirage complet quand la liste est épuisée', () => {
		const words = sampleWords(['a', 'b', 'c'], 7);
		expect(words).toHaveLength(7);
		expect(new Set(words.slice(0, 3)).size).toBe(3);
		expect(new Set(words.slice(3, 6)).size).toBe(3);
	});

	it('refuse une liste vide', () => {
		expect(() => sampleWords([], 1)).toThrow();
	});
});

describe('dictionarySource', () => {
	it('charge des mots simples dans chaque langue', async () => {
		for (const lang of textLangs) {
			const words = await dictionarySource(lang).load(50);
			expect(words, lang).toHaveLength(50);
			expect(
				words.every((word) => word !== '' && !/\s/.test(word)),
				lang
			).toBe(true);
		}
	});
});
