<script lang="ts">
	import type { Snippet } from 'svelte';
	import { resolve } from '$app/paths';
	import { m } from '#lib/paraglide/messages.js';

	let {
		title,
		subtitle,
		back = true,
		children
	}: { title: string; subtitle?: string; back?: boolean; children: Snippet } = $props();
</script>

<section>
	<header>
		<h1>{title}</h1>
		{#if subtitle}<p>{subtitle}</p>{/if}
	</header>

	{@render children()}

	{#if back}
		<a class="button back" href={resolve('/')}>{m.back()}</a>
	{/if}
</section>

<style>
	section {
		display: flex;
		flex-direction: column;
		align-items: center;
		gap: 1.5rem;
		text-align: center;
	}

	h1 {
		margin: 0;
		font-size: 2.5rem;
	}

	header p {
		margin: 0.25rem 0 0;
		color: var(--text-muted);
	}

	.back {
		margin-top: 1rem;
	}
</style>
