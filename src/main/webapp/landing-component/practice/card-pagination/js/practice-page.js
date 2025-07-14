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