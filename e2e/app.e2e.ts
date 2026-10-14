import { expect, test, type Page } from '@playwright/test';

const currentWord = (page: Page) =>
	page.locator('.word[data-state="current"]').getAttribute('data-word');

test('partie classique complète, résultats puis historique', async ({ page }) => {
	await page.goto('/');
	await page.getByRole('link', { name: 'Jouer' }).click();
	await page.getByRole('link', { name: /Classique/ }).click();

	const input = page.getByLabel('Tape le mot surligné');
	await expect(input).toBeFocused();

	// Une faute volontaire sur le premier mot, puis les 14 autres sans erreur
	await input.pressSequentially('zzz ');
	for (let i = 1; i < 15; i++) {
		await input.pressSequentially(`${await currentWord(page)} `);
	}

	const results = page.getByRole('dialog', { name: 'Partie terminée' });
	await expect(results).toBeVisible();
	await expect(results.getByRole('definition').nth(3)).toHaveText('14');
	await expect(results.getByRole('definition').nth(4)).toHaveText('1');

	await results.getByRole('button', { name: 'Rejouer' }).click();
	await expect(results).toBeHidden();
	await expect(page.getByTestId('correct-words')).toHaveText('0');
	await expect(input).toBeFocused();

	await page.getByRole('link', { name: 'Retour' }).click();
	await page.getByRole('link', { name: 'Retour' }).click();
	await page.getByRole('link', { name: 'Statistiques' }).click();
	const history = page.getByRole('table', { name: 'Dernières parties' });
	await expect(history.getByRole('row')).toHaveCount(2);
	await expect(history.getByRole('row').nth(1)).toContainText('Classique');
});

test('les réglages sont conservés après rechargement', async ({ page }) => {
	await page.goto('/settings');
	await page.getByLabel('Langue des mots').selectOption('en');
	await page.getByLabel('Thème').selectOption('light');
	await page.reload();

	await expect(page.getByLabel('Langue des mots')).toHaveValue('en');
	await expect(page.locator('html')).toHaveAttribute('data-theme', 'light');

	await page.getByLabel("Langue de l'interface").selectOption('en');
	await expect(page.getByRole('heading', { name: 'Settings' })).toBeVisible();
});

test('le mode chrono se termine seul à la fin du temps', async ({ page }) => {
	await page.clock.install();
	await page.goto('/play/timed');

	const input = page.getByLabel('Tape le mot surligné');
	await input.pressSequentially(`${await currentWord(page)} `);
	await expect(page.getByTestId('correct-words')).toHaveText('1');

	await page.clock.runFor(30_000);
	const results = page.getByRole('dialog', { name: 'Partie terminée' });
	await expect(results).toBeVisible();
	await expect(results.getByRole('definition').first()).toHaveText('00:30');
	await expect(input).toBeDisabled();
});
