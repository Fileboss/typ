# typ

Logiciel d'entraînement à la frappe au clavier : simple, lisible (grande police, couleurs marquées) et multilingue.

Réécriture en Svelte de la version JavaFX d'origine, conservée dans [`legacy/`](legacy/).

## Fonctionnalités

- **Mode classique** : taper une série de mots courants (10 à 50) le plus vite possible.
- **Mode chrono** : un maximum de mots en 15, 30, 60 ou 120 secondes.
- Coloration en direct, lettre par lettre ; compteurs de mots justes/faux et chronomètre.
- Résultats en fin de partie : temps, mots par minute, précision, mots et frappes justes/fausses.
- **Historique** des parties (stocké dans le navigateur) avec graphique de progression.
- **Réglages** : langue de l'interface et des mots (français, anglais, allemand, espagnol), thème clair/sombre, taille du texte.
- Raccourcis : <kbd>Espace</kbd> valide un mot, <kbd>Échap</kbd> recommence la partie.

## Stack

SvelteKit 3 (Svelte 5, runes) · TypeScript · Vite · adapter-static (SPA) · Paraglide (i18n) · Dexie (IndexedDB) · uPlot · Vitest · Playwright

## Développement

```sh
npm install
npm run dev          # serveur de développement
npm run build        # site statique dans build/
npm run check        # vérification des types
npm run lint         # Prettier + ESLint
npm run test:unit    # tests unitaires (Vitest)
npm run test:e2e     # tests d'intégration (Playwright)
```

## Architecture

```
src/lib/core/        moteur pur, sans dépendance à l'UI (testé unitairement)
  engine.ts          état de partie immuable : setInput, submitWord, tick, computeStats
  modes.ts           un mode = une configuration (nombre de mots, limite de temps)
  text-source.ts     sources de mots (dictionnaires chargés à la demande)
src/lib/game.svelte.ts      partie réactive (runes) autour du moteur
src/lib/settings.svelte.ts  réglages persistés, fournis par contexte
src/lib/history.ts          historique des parties (IndexedDB)
src/lib/components/         écran de jeu, affichage des mots, résultats, graphique
src/routes/                 menu, choix du mode, /play/[mode], statistiques, réglages
messages/                   traductions de l'interface
```

Pour ajouter un mode de jeu, il suffit d'une entrée dans `modes.ts` et de son nom dans `src/lib/i18n.ts`. Pour une nouvelle source de texte, il suffit d'implémenter `TextSource`.
