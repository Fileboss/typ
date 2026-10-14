import { expect, it } from 'vitest';
import { formatDuration } from './format.ts';

it('formate une durée en mm:ss', () => {
	expect(formatDuration(0)).toBe('00:00');
	expect(formatDuration(61_999)).toBe('01:01');
	expect(formatDuration(-500)).toBe('00:00');
});
