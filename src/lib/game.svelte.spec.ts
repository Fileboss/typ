import { describe, expect, it, vi } from 'vitest';
import { Game } from './game.svelte.ts';

const classic = { wordCount: 2, timeLimitMs: null };

describe('Game', () => {
	it('dérive les statistiques de chaque action', () => {
		const game = new Game(['le', 'chat'], classic);
		game.input('le', 0);
		game.submit(1000);
		expect(game.index).toBe(1);
		expect(game.running).toBe(true);
		expect(game.stats).toMatchObject({ correctWords: 1, correctKeystrokes: 2, elapsedMs: 1000 });
	});

	it('signale la fin de partie une seule fois, avec les statistiques finales', () => {
		const onfinish = vi.fn();
		const game = new Game(['le'], classic, onfinish);
		game.input('le', 0);
		game.submit(500);
		game.tick(9000);
		expect(game.finished).toBe(true);
		expect(onfinish).toHaveBeenCalledOnce();
		expect(onfinish).toHaveBeenCalledWith(
			expect.objectContaining({ correctWords: 1, elapsedMs: 500 })
		);
	});

	it('termine une partie chronométrée quand le temps est écoulé, sans frappe', () => {
		const onfinish = vi.fn();
		const game = new Game(['le', 'chat'], { wordCount: 2, timeLimitMs: 1000 }, onfinish);
		game.input('l', 0);
		game.tick(999);
		expect(onfinish).not.toHaveBeenCalled();
		game.tick(1000);
		expect(game.finished).toBe(true);
		expect(onfinish).toHaveBeenCalledOnce();
	});
});
