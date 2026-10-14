/**
 * Moteur de jeu : fonctions pures sur un état immuable.
 * Aucune dépendance à l'UI ; le temps est toujours passé en paramètre (ms).
 */

export type WordResult = Readonly<{ typed: string; correct: boolean }>;

export type Keystroke = Readonly<{ at: number; correct: boolean }>;

export type GameConfig = Readonly<{
	/** Nombre de mots à taper. */
	wordCount: number;
	/** Durée maximale d'une partie, `null` pour une partie sans limite de temps. */
	timeLimitMs: number | null;
}>;

export type GameState = Readonly<{
	words: readonly string[];
	/** Un résultat par mot validé : l'index du mot courant est `results.length`. */
	results: readonly WordResult[];
	/** Saisie en cours pour le mot courant. */
	input: string;
	keystrokes: readonly Keystroke[];
	timeLimitMs: number | null;
	startedAt: number | null;
	endedAt: number | null;
}>;

export type Stats = Readonly<{
	elapsedMs: number;
	correctWords: number;
	incorrectWords: number;
	correctKeystrokes: number;
	incorrectKeystrokes: number;
	/** Mots par minute : caractères des mots justes (espaces compris) / 5. */
	wpm: number;
	/** Part des frappes justes, entre 0 et 1. */
	accuracy: number;
}>;

export function createGame(words: readonly string[], timeLimitMs: number | null = null): GameState {
	return {
		words,
		results: [],
		input: '',
		keystrokes: [],
		timeLimitMs,
		startedAt: null,
		endedAt: null
	};
}

export const currentIndex = (state: GameState): number => state.results.length;

export const isOver = (state: GameState): boolean => state.endedAt !== null;

/** Termine la partie si la limite de temps est atteinte. */
export function tick(state: GameState, now: number): GameState {
	if (isOver(state) || state.startedAt === null || state.timeLimitMs === null) return state;
	const deadline = state.startedAt + state.timeLimitMs;
	return now >= deadline ? { ...state, endedAt: deadline } : state;
}

/**
 * Met à jour la saisie du mot courant (sans espace).
 * Chaque caractère ajouté compte comme une frappe, juste si toute la saisie
 * jusqu'à lui correspond au début du mot attendu.
 */
export function setInput(state: GameState, input: string, now: number): GameState {
	const current = tick(state, now);
	if (isOver(current) || input === current.input) return current;

	const target = current.words[currentIndex(current)];
	const added: Keystroke[] = [];
	for (let i = current.input.length; i < input.length; i++) {
		added.push({ at: now, correct: target.startsWith(input.slice(0, i + 1)) });
	}

	return {
		...current,
		input,
		keystrokes: added.length > 0 ? [...current.keystrokes, ...added] : current.keystrokes,
		startedAt: current.startedAt ?? now
	};
}

/** Valide le mot courant. Sans effet si la saisie est vide. */
export function submitWord(state: GameState, now: number): GameState {
	const current = tick(state, now);
	if (isOver(current) || current.input === '') return current;

	const target = current.words[currentIndex(current)];
	const results = [...current.results, { typed: current.input, correct: current.input === target }];

	return {
		...current,
		results,
		input: '',
		endedAt: results.length === current.words.length ? now : null
	};
}

export function computeStats(state: GameState, now: number): Stats {
	const deadline =
		state.startedAt !== null && state.timeLimitMs !== null
			? state.startedAt + state.timeLimitMs
			: Infinity;
	const end = state.endedAt ?? Math.min(now, deadline);
	const elapsedMs = state.startedAt === null ? 0 : Math.max(0, end - state.startedAt);

	let correctWords = 0;
	let correctChars = 0;
	for (const result of state.results) {
		if (result.correct) {
			correctWords++;
			correctChars += result.typed.length + 1;
		}
	}

	const correctKeystrokes = state.keystrokes.filter((k) => k.correct).length;
	const totalKeystrokes = state.keystrokes.length;

	return {
		elapsedMs,
		correctWords,
		incorrectWords: state.results.length - correctWords,
		correctKeystrokes,
		incorrectKeystrokes: totalKeystrokes - correctKeystrokes,
		wpm: elapsedMs > 0 ? correctChars / 5 / (elapsedMs / 60_000) : 0,
		accuracy: totalKeystrokes > 0 ? correctKeystrokes / totalKeystrokes : 0
	};
}
