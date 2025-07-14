// Toggle between grid and list view
const viewBtns = document.querySelectorAll('.view-btn');

viewBtns.forEach(btn => {
	btn.addEventListener('click', () => {
		viewBtns.forEach(b => b.classList.remove('active'));
		btn.classList.add('active');

		const productGrid = document.getElementById('productGrid');
		if (btn.getAttribute('aria-label') === 'List view') {
			productGrid.style.gridTemplateColumns = '1fr';
		} else {
			productGrid.style.gridTemplateColumns = 'repeat(auto-fill, minmax(180px, 1fr))';
		}
	});
});
