import { describe, expect, it } from 'vitest';
import {
	computeStats,
	createGame,
	currentIndex,
	setInput,
	submitWord,
	tick,
	type GameState
} from './engine.ts';

/** Tape `text` caractère par caractère, à partir de l'instant `at`. */
const typeChars = (state: GameState, text: string, at = 0): GameState =>
	[...text].reduce((s, _, i) => setInput(s, s.input + text[i], at), state);

describe('setInput', () => {
	it('démarre le chronomètre à la première frappe seulement', () => {
		const state = createGame(['chat']);
		expect(state.startedAt).toBeNull();
		const typed = typeChars(state, 'ch', 1000);
		expect(typed.startedAt).toBe(1000);
		expect(setInput(typed, 'cha', 2000).startedAt).toBe(1000);
	});

	it('compte une frappe juste tant que la saisie est un début du mot, fausse ensuite', () => {
		const state = typeChars(createGame(['chat']), 'cxat');
		expect(state.keystrokes.map((k) => k.correct)).toEqual([true, false, false, false]);
	});

	it('ne compte pas les effacements, mais recompte les caractères retapés', () => {
		let state = typeChars(createGame(['chat']), 'cx');
		state = setInput(state, 'c', 0);
		state = setInput(state, 'ch', 0);
		expect(state.keystrokes.map((k) => k.correct)).toEqual([true, false, true]);
	});
});

describe('submitWord', () => {
	it('enregistre le mot comme juste ou faux et passe au suivant', () => {
		let state = submitWord(typeChars(createGame(['le', 'chat', 'dort']), 'le'), 0);
		state = submitWord(typeChars(state, 'chta'), 0);
		expect(state.results).toEqual([
			{ typed: 'le', correct: true },
			{ typed: 'chta', correct: false }
		]);
		expect(currentIndex(state)).toBe(2);
		expect(state.input).toBe('');
	});

	it('ignore une validation sans saisie', () => {
		const state = createGame(['le', 'chat']);
		expect(submitWord(state, 0)).toBe(state);
	});

	it('termine la partie au dernier mot et ignore toute saisie ensuite', () => {
		const done = submitWord(typeChars(createGame(['fin']), 'fin', 500), 800);
		expect(done.endedAt).toBe(800);
		expect(setInput(done, 'x', 900)).toBe(done);
	});
});

describe('limite de temps', () => {
	it("ne s'écoule pas tant que la partie n'a pas commencé", () => {
		const state = createGame(['a', 'b'], 1000);
		expect(tick(state, 999_999).endedAt).toBeNull();
	});

	it("termine la partie exactement à l'échéance", () => {
		const started = typeChars(createGame(['abc', 'def'], 1000), 'a', 100);
		expect(tick(started, 1099).endedAt).toBeNull();
		expect(tick(started, 5000).endedAt).toBe(1100);
	});

	it("n'accepte plus de saisie après l'échéance", () => {
		const started = typeChars(createGame(['abc', 'def'], 1000), 'a', 0);
		const late = setInput(started, 'ab', 1500);
		expect(late.endedAt).toBe(1000);
		expect(late.input).toBe('a');
		expect(late.keystrokes).toHaveLength(1);
	});
});

describe('computeStats', () => {
	it('calcule les mots par minute sur les mots justes, espaces compris', () => {
		let state = submitWord(typeChars(createGame(['abcd', 'efgh', 'ijkl']), 'abcd', 0), 0);
		state = submitWord(typeChars(state, 'efgx', 0), 0);
		state = submitWord(typeChars(state, 'ijkl', 0), 6000);
		const stats = computeStats(state, 99_999);
		// 2 mots justes × (4 lettres + 1 espace) = 10 caractères = 2 mots standard en 0,1 min
		expect(stats.wpm).toBeCloseTo(20);
		expect(stats.elapsedMs).toBe(6000);
		expect(stats).toMatchObject({ correctWords: 2, incorrectWords: 1 });
		expect(stats).toMatchObject({ correctKeystrokes: 11, incorrectKeystrokes: 1 });
		expect(stats.accuracy).toBeCloseTo(11 / 12);
	});

	it("plafonne la durée à la limite de temps, même avant l'arrêt effectif", () => {
		const started = typeChars(createGame(['abc'], 1000), 'a', 0);
		expect(computeStats(started, 4000).elapsedMs).toBe(1000);
	});

	it('renvoie des statistiques nulles avant la première frappe', () => {
		expect(computeStats(createGame(['abc']), 5000)).toEqual({
			elapsedMs: 0,
			correctWords: 0,
			incorrectWords: 0,
			correctKeystrokes: 0,
			incorrectKeystrokes: 0,
			wpm: 0,
			accuracy: 0
		});
	});
});
