/** Une source de mots à taper : dictionnaire, citations, texte libre… */
export interface TextSource {
	load(count: number): Promise<string[]>;
}

export const textLangs = ['fr', 'en', 'de', 'es'] as const;

export type TextLang = (typeof textLangs)[number];

export const isTextLang = (value: unknown): value is TextLang =>
	textLangs.includes(value as TextLang);

/** Un mot par ligne ; ignore les lignes vides, les expressions de plusieurs mots et les doublons. */
export function parseWordList(raw: string): string[] {
	const words = raw
		.split(/\r?\n/)
		.map((line) => line.trim())
		.filter((line) => line !== '' && !/\s/.test(line));
	return [...new Set(words)];
}

/**
 * Tire `count` mots au hasard. Aucun mot n'est répété tant que la liste
 * n'est pas épuisée ; au-delà, on repart d'un nouveau tirage.
 */
export function sampleWords(
	pool: readonly string[],
	count: number,
	random: () => number = Math.random
): string[] {
	if (pool.length === 0) throw new Error('La liste de mots est vide');

	const result: string[] = [];
	while (result.length < count) {
		const deck = [...pool];
		for (let i = deck.length - 1; i > 0 && result.length < count; i--) {
			const j = Math.floor(random() * (i + 1));
			[deck[i], deck[j]] = [deck[j], deck[i]];
			result.push(deck[i]);
		}
		if (result.length < count) result.push(deck[0]);
	}
	return result;
}

const dictionaries = import.meta.glob<string>('./dictionaries/*.txt', {
	query: '?raw',
	import: 'default'
});

const cache = new Map<TextLang, Promise<string[]>>();

function loadDictionary(lang: TextLang): Promise<string[]> {
	let words = cache.get(lang);
	if (!words) {
		words = dictionaries[`./dictionaries/${lang}.txt`]().then(parseWordList);
		cache.set(lang, words);
	}
	return words;
}

/** Mots courants d'une langue, chargés à la demande. */
export const dictionarySource = (lang: TextLang): TextSource => ({
	load: async (count) => sampleWords(await loadDictionary(lang), count)
});
