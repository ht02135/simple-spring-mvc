document.addEventListener('DOMContentLoaded', function() {
	// Sample data for cards - Realistic design resources data
	const cardData = [
		{
			title: "Grid Layout Templates",
			description: "Collection of responsive grid systems for structured UI design with flexible breakpoints.",
			image: "https://images.unsplash.com/photo-1561070791-2526d30994b5?ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=80",
			tag: "Layout"
		},
		{
			title: "Color Harmony Guide",
			description: "Professional palettes with accessibility ratings and contrast values for inclusive design.",
			image: "https://images.unsplash.com/photo-1579546929518-9e396f3cc809?ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=80",
			tag: "Color Theory"
		},
		{
			title: "Typography Pairing Kit",
			description: "Curated font combinations with typographic scales for balanced visual hierarchy.",
			image: "https://images.unsplash.com/photo-1588345921523-c2dcdb7f1dcd?ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=80",
			tag: "Typography"
		},
		{
			title: "Micro-interaction Library",
			description: "Ready-to-use subtle animations that enhance user feedback and interface responsiveness.",
			image: "https://images.unsplash.com/photo-1558655146-9f40138edfeb?ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=80",
			tag: "Animation"
		},
		{
			title: "UX Pattern Collection",
			description: "Research-backed interface patterns solving common usability problems across platforms.",
			image: "https://images.unsplash.com/photo-1559028012-481c04fa702d?ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=80",
			tag: "UX Design"
		},
		{
			title: "Form Component Kit",
			description: "Accessible form elements with validation states and error handling patterns.",
			image: "https://images.unsplash.com/photo-1595776613215-fe04b78de7d0?ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=80",
			tag: "Components"
		},
		{
			title: "Iconography System",
			description: "Cohesive vector icon set with consistent visual language across multiple categories.",
			image: "https://images.unsplash.com/photo-1611162616475-46b635cb6868?ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=80",
			tag: "Icons"
		},
		{
			title: "Mobile Navigation Patterns",
			description: "Touch-optimized navigation solutions for complex information architecture.",
			image: "https://images.unsplash.com/photo-1559028006-448665bd7c7b?ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=80",
			tag: "Mobile UI"
		},
		{
			title: "Data Visualization Templates",
			description: "Chart and graph frameworks for translating complex data into intuitive visuals.",
			image: "https://images.unsplash.com/photo-1551288049-bebda4e38f71?ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=80",
			tag: "Data Design"
		},
		{
			title: "Accessibility Guidelines",
			description: "Practical implementation guide for WCAG compliance with code examples and checklists.",
			image: "https://images.unsplash.com/photo-1584697964358-3e14ca57658b?ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=80",
			tag: "Accessibility"
		},
		{
			title: "Card UI Patterns",
			description: "Versatile card designs for content organization with responsive behaviors.",
			image: "https://images.unsplash.com/photo-1561070791-32d8b0659445?ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=80",
			tag: "UI Components"
		},
		{
			title: "Prototyping Wireframes",
			description: "Low-fidelity wireframe templates for rapid concept validation and user testing.",
			image: "https://images.unsplash.com/photo-1581291518633-83b4ebd1d83e?ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=80",
			tag: "Wireframing"
		},
		{
			title: "Responsive Image Patterns",
			description: "Techniques for optimal image delivery across devices with performance considerations.",
			image: "https://images.unsplash.com/photo-1542744173-8e7e53415bb0?ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=80",
			tag: "Performance"
		},
		{
			title: "Design System Examples",
			description: "Component libraries and documentation structures from leading design systems.",
			image: "https://images.unsplash.com/photo-1523726491678-bf852e717f6a?ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=80",
			tag: "Systems"
		},
		{
			title: "Dark Mode Conversions",
			description: "Guidelines for adapting interfaces to dark mode with proper contrast and readability.",
			image: "https://images.unsplash.com/photo-1555066931-4365d14bab8c?ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=80",
			tag: "UI Themes"
		},
		{
			title: "Empty State Designs",
			description: "Creative solutions for zero-data screens that guide users and reduce confusion.",
			image: "https://images.unsplash.com/photo-1543286386-713bdd548da4?ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=80",
			tag: "UX Patterns"
		},
		{
			title: "Motion Design Principles",
			description: "Animation timing and easing functions that create natural, purposeful movement.",
			image: "https://images.unsplash.com/photo-1550745165-9bc0b252726f?ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=80",
			tag: "Motion"
		},
		{
			title: "Whitespace Usage Guide",
			description: "Strategies for proper spatial relationships that improve content legibility and focus.",
			image: "https://images.unsplash.com/photo-1557682250-33bd709cbe85?ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=80",
			tag: "Composition"
		},
		{
			title: "Dashboard UI Framework",
			description: "Modular components for data-heavy interfaces with customizable widgets and layouts.",
			image: "https://images.unsplash.com/photo-1551288049-bebda4e38f71?ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=80",
			tag: "Dashboard"
		},
		{
			title: "Loading State Collection",
			description: "Engaging loaders and progress indicators that maintain user engagement during wait times.",
			image: "https://images.unsplash.com/photo-1607706189992-eae578626c86?ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=80",
			tag: "Microinteractions"
		}
	];

	const itemsPerPage = 6;
	const totalPages = Math.ceil(cardData.length / itemsPerPage);
	let currentPage = 1;

	const cardsContainer = document.getElementById('cardsContainer');
	const paginationNumbers = document.getElementById('paginationNumbers');
	const prevBtn = document.getElementById('prevBtn');
	const nextBtn = document.getElementById('nextBtn');
	const currentPageIndicator = document.getElementById('currentPageIndicator');

	// Initialize pagination
	function initPagination() {
		// Clear pagination
		paginationNumbers.innerHTML = '';

		const maxPagesToShow = window.innerWidth < 600 ? 3 : 5;
		let startPage = Math.max(1, currentPage - Math.floor(maxPagesToShow / 2));
		let endPage = Math.min(totalPages, startPage + maxPagesToShow - 1);

		if (endPage - startPage + 1 < maxPagesToShow) {
			startPage = Math.max(1, endPage - maxPagesToShow + 1);
		}

		// Add first page button if not already in range
		if (startPage > 1) {
			addPageButton(1);

			if (startPage > 2) {
				addEllipsis();
			}
		}

		// Add page numbers
		for (let i = startPage; i <= endPage; i++) {
			addPageButton(i);
		}

		// Add last page button if not already in range
		if (endPage < totalPages) {
			if (endPage < totalPages - 1) {
				addEllipsis();
			}
			addPageButton(totalPages);
		}

		// Update navigation buttons
		prevBtn.disabled = currentPage === 1;
		nextBtn.disabled = currentPage === totalPages;

		// Update current page indicator
		currentPageIndicator.textContent = `Page ${currentPage} of ${totalPages}`;
	}
	
	/* ////////////////////////////////// */

	function addPageButton(pageNum) {
		const listItem = document.createElement('li');
		listItem.className = 'page-item';

		const pageLink = document.createElement('a');
		pageLink.className = 'page-link' + (pageNum === currentPage ? ' active' : '');
		pageLink.textContent = pageNum;
		pageLink.href = '#';
		pageLink.setAttribute('aria-label', `Page ${pageNum}`);

		pageLink.addEventListener('click', function(e) {
			e.preventDefault();
			if (currentPage !== pageNum) {
				goToPage(pageNum);
			}
		});

		listItem.appendChild(pageLink);
		paginationNumbers.appendChild(listItem);
	}

	/* ////////////////////////////////// */
	
	function addEllipsis() {
		const listItem = document.createElement('li');
		listItem.className = 'page-item';

		const pageEllipsis = document.createElement('span');
		pageEllipsis.className = 'page-dots';
		pageEllipsis.textContent = '...';
		pageEllipsis.setAttribute('aria-hidden', 'true');

		listItem.appendChild(pageEllipsis);
		paginationNumbers.appendChild(listItem);
	}

	/* ////////////////////////////////// */
	
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
	
	/* ////////////////////////////////// */

	function goToPage(pageNum) {
		currentPage = pageNum;
		displayCards();
		initPagination();

		// Smooth scroll to top of card container
		cardsContainer.scrollTo({
			top: 0,
			behavior: 'smooth'
		});
	}

	// Event listeners for navigation buttons
	prevBtn.addEventListener('click', function() {
		if (currentPage > 1) {
			goToPage(currentPage - 1);
		}
	});

	nextBtn.addEventListener('click', function() {
		if (currentPage < totalPages) {
			goToPage(currentPage + 1);
		}
	});
	
	// Add keyboard navigation
	document.addEventListener('keydown', function(e) {
		if (e.key === 'ArrowLeft' && !prevBtn.disabled) {
			goToPage(currentPage - 1);
		} else if (e.key === 'ArrowRight' && !nextBtn.disabled) {
			goToPage(currentPage + 1);
		}
	});

	// Handle window resize to adjust pagination display
	window.addEventListener('resize', function() {
		initPagination();
	});

	// Initialize the pagination and display cards
	initPagination();
	displayCards();
});