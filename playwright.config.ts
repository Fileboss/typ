import { defineConfig } from '@playwright/test';

export default defineConfig({
	webServer: { command: 'npm run build && npm run preview', port: 4173 },
	testMatch: '**/*.e2e.{ts,js}',
	// Langue de l'interface déterminée par le navigateur : on la fixe pour des tests reproductibles.
	use: { locale: 'fr-FR' }
});
