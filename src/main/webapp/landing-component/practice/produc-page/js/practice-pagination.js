// Pagination state
let currentPage = 1;
const productsPerPage = 6;
const totalPages = Math.ceil(128 / productsPerPage); // Simulating 128 total products

// Function to render pagination controls
function renderPagination() {
	const pagination = document.getElementById('pagination');
	pagination.innerHTML = '';

	// Previous button
	const prevButton = document.createElement('button');
	prevButton.className = `pagination-arrow ${currentPage === 1 ? 'disabled' : ''}`;
	prevButton.setAttribute('aria-label', 'Previous page');
	prevButton.innerHTML = `
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round">
            <polyline points="15 18 9 12 15 6"></polyline>
        </svg>
    `;
	if (currentPage > 1) {
		prevButton.addEventListener('click', () => {
			currentPage--;
			renderProducts();
			renderPagination();
		});
	}
	pagination.appendChild(prevButton);

	// Page numbers
	const startPage = Math.max(1, currentPage - 2);
	const endPage = Math.min(totalPages, startPage + 4);

	if (startPage > 1) {
		// Add first page
		addPageButton(1);

		// Add ellipsis if needed
		if (startPage > 2) {
			const ellipsis = document.createElement('span');
			ellipsis.className = 'pagination-item pagination-ellipsis';
			ellipsis.innerHTML = '...';
			pagination.appendChild(ellipsis);
		}
	}

	// Add page numbers
	for (let i = startPage; i <= endPage; i++) {
		addPageButton(i);
	}

	// Add ellipsis and last page if needed
	if (endPage < totalPages) {
		if (endPage < totalPages - 1) {
			const ellipsis = document.createElement('span');
			ellipsis.className = 'pagination-item pagination-ellipsis';
			ellipsis.innerHTML = '...';
			pagination.appendChild(ellipsis);
		}
		addPageButton(totalPages);
	}

	// Next button
	const nextButton = document.createElement('button');
	nextButton.className = `pagination-arrow ${currentPage === totalPages ? 'disabled' : ''}`;
	nextButton.setAttribute('aria-label', 'Next page');
	nextButton.innerHTML = `
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round">
            <polyline points="9 18 15 12 9 6"></polyline>
        </svg>
    `;
	if (currentPage < totalPages) {
		nextButton.addEventListener('click', () => {
			currentPage++;
			renderProducts();
			renderPagination();
		});
	}
	pagination.appendChild(nextButton);

	function addPageButton(pageNumber) {
		const pageButton = document.createElement('button');
		pageButton.className = `pagination-item ${pageNumber === currentPage ? 'active' : ''}`;
		pageButton.textContent = pageNumber;
		pageButton.setAttribute('aria-label', `Page ${pageNumber}`);
		if (pageNumber === currentPage) {
			pageButton.setAttribute('aria-current', 'page');
		} else {
			pageButton.addEventListener('click', () => {
				currentPage = pageNumber;
				renderProducts();
				renderPagination();
			});
		}
		pagination.appendChild(pageButton);
	}
}

