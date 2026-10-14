import type { ClientInit } from '@sveltejs/kit/hooks';
import { getLocale } from '#lib/paraglide/runtime.js';

export const init: ClientInit = () => {
	document.documentElement.lang = getLocale();
};
