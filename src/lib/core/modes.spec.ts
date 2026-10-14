import { describe, expect, it } from 'vitest';
import { isModeId, modes } from './modes.ts';

const settings = { classicWordCount: 25, timedSeconds: 60 };

describe('modes', () => {
	it('configure chaque mode à partir des réglages', () => {
		expect(modes.classic.config(settings)).toEqual({ wordCount: 25, timeLimitMs: null });
		expect(modes.timed.config(settings).timeLimitMs).toBe(60_000);
	});

	it("n'accepte que les modes déclarés dans l'URL", () => {
		expect(isModeId('classic')).toBe(true);
		expect(isModeId('timed')).toBe(true);
		expect(isModeId('toString')).toBe(false);
		expect(isModeId('zen')).toBe(false);
	});
});
