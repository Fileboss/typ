import type { GameConfig } from './engine.ts';

export type ModeSettings = Readonly<{ classicWordCount: number; timedSeconds: number }>;

/** Un mode de jeu = une façon de configurer une partie à partir des réglages. */
export type GameMode = Readonly<{ config: (settings: ModeSettings) => GameConfig }>;

/** Assez de mots pour ne jamais en manquer en mode chronométré (jusqu'à ~300 mots/min). */
const TIMED_WORDS_PER_SECOND = 5;

export const modes = {
	classic: {
		config: (settings) => ({ wordCount: settings.classicWordCount, timeLimitMs: null })
	},
	timed: {
		config: (settings) => ({
			wordCount: settings.timedSeconds * TIMED_WORDS_PER_SECOND,
			timeLimitMs: settings.timedSeconds * 1000
		})
	}
} as const satisfies Record<string, GameMode>;

export type ModeId = keyof typeof modes;

export const modeIds = Object.keys(modes) as ModeId[];

export const isModeId = (value: string): value is ModeId => Object.hasOwn(modes, value);
