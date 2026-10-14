import { Dexie, type EntityTable } from 'dexie';
import type { Stats } from './core/engine.ts';
import type { ModeId } from './core/modes.ts';
import type { TextLang } from './core/text-source.ts';

export type GameRecord = Stats & {
	id: number;
	playedAt: number;
	mode: ModeId;
	lang: TextLang;
};

const db = new Dexie('typ') as Dexie & { games: EntityTable<GameRecord, 'id'> };

db.version(1).stores({ games: '++id, playedAt' });

export async function saveGame(record: Omit<GameRecord, 'id'>): Promise<void> {
	await db.games.add(record);
}

/** Toutes les parties, de la plus ancienne à la plus récente. */
export const listGames = (): Promise<GameRecord[]> => db.games.orderBy('playedAt').toArray();

export const clearGames = (): Promise<void> => db.games.clear();
