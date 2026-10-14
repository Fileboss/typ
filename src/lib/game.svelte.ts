import {
	computeStats,
	createGame,
	currentIndex,
	isOver,
	setInput,
	submitWord,
	tick,
	type GameConfig,
	type GameState,
	type Stats
} from './core/engine.ts';

/** Partie en cours : enveloppe réactive autour du moteur pur. */
export class Game {
	#state: GameState;
	#now = $state(0);
	#onfinish: ((stats: Stats) => void) | undefined;

	readonly stats: Stats;

	constructor(words: readonly string[], config: GameConfig, onfinish?: (stats: Stats) => void) {
		this.#state = $state.raw(createGame(words, config.timeLimitMs));
		this.stats = $derived(computeStats(this.#state, this.#now));
		this.#onfinish = onfinish;
	}

	get state(): GameState {
		return this.#state;
	}

	get index(): number {
		return currentIndex(this.#state);
	}

	get running(): boolean {
		return this.#state.startedAt !== null && !isOver(this.#state);
	}

	get finished(): boolean {
		return isOver(this.#state);
	}

	input(value: string, now = performance.now()): void {
		this.#apply(setInput(this.#state, value, now), now);
	}

	submit(now = performance.now()): void {
		this.#apply(submitWord(this.#state, now), now);
	}

	tick(now = performance.now()): void {
		this.#apply(tick(this.#state, now), now);
	}

	#apply(next: GameState, now: number): void {
		const wasOver = isOver(this.#state);
		this.#state = next;
		this.#now = now;
		if (!wasOver && isOver(next)) this.#onfinish?.(this.stats);
	}
}
