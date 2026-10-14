import { m } from './paraglide/messages.js';
import { getLocale } from './paraglide/runtime.js';
import type { ModeId } from './core/modes.ts';

const modeNames: Record<ModeId, () => string> = {
	classic: () => m.mode_classic(),
	timed: () => m.mode_timed()
};

export const modeName = (mode: ModeId): string => modeNames[mode]();

const languageNames = new Intl.DisplayNames([getLocale()], { type: 'language' });

/** Nom d'une langue dans la langue de l'interface, ex. `de` → « allemand ». */
export const languageName = (code: string): string => languageNames.of(code) ?? code;
