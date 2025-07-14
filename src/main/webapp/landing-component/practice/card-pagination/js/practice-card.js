function displayCards() {
	// Show loading state
	cardsContainer.innerHTML = '';

	// Calculate start and end indices for the current page
	const startIndex = (currentPage - 1) * itemsPerPage;
	const endIndex = Math.min(startIndex + itemsPerPage, cardData.length);

	// Check if there are no cards to display
	if (cardData.length === 0) {
		showNoResults();
		return;
	}

	// Create and append card elements
	for (let i = startIndex; i < endIndex; i++) {
		const card = cardData[i];
		const cardElement = document.createElement('div');
		cardElement.className = 'card';
		cardElement.style.animationDelay = `${(i - startIndex) * 0.1}s`;

		cardElement.innerHTML = `
            <img class="card-img" src="${card.image}" alt="${card.title}">
            <div class="card-content">
                <h3 class="card-title">${card.title}</h3>
                <p class="card-text">${card.description}</p>
                <span class="card-tag">${card.tag}</span>
            </div>
        `;

		cardsContainer.appendChild(cardElement);
	}

	// Add class for page transition animation
	cardsContainer.classList.add('page-transition');

	// Remove the animation class after it completes
	setTimeout(() => {
		cardsContainer.classList.remove('page-transition');
	}, 300);
}

/* ////////////////////////////////// */

function showNoResults() {
	const noResultsElement = document.createElement('div');
	noResultsElement.className = 'no-results';

	noResultsElement.innerHTML = `
        <div class="no-results-icon">📂</div>
        <h3 class="no-results-text">No resources found</h3>
        <p class="no-results-subtext">We couldn't find any design resources matching your criteria.</p>
    `;

	cardsContainer.appendChild(noResultsElement);
}