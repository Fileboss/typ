import { defineParams } from '@sveltejs/kit/params';
import { isModeId } from '#lib/core/modes.ts';

export const params = defineParams({
	mode: (param) => (isModeId(param) ? param : undefined)
});
