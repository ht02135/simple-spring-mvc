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

document.addEventListener('DOMContentLoaded', function() {
	// Initialize the pagination and display cards
	initPagination();
	displayCards();
});