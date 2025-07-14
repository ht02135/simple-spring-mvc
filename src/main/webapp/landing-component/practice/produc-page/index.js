// Products data
const products = [
	{
		name: "Organic Cotton T-Shirt",
		price: "$28.99",
		image: "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80"
	},
	{
		name: "Linen Beach Shorts",
		price: "$42.50",
		image: "https://images.unsplash.com/photo-1549062572-544a64fb0c56?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80"
	},
	{
		name: "Eco Swim Trunks",
		price: "$35.99",
		image: "https://images.unsplash.com/photo-1565084888279-aca607ecce0c?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80"
	},
	{
		name: "Straw Sun Hat",
		price: "$24.99",
		image: "https://images.unsplash.com/photo-1565461073340-6d293bba9b27?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80"
	},
	{
		name: "Recycled Canvas Tote",
		price: "$38.75",
		image: "https://images.unsplash.com/photo-1544816155-12df9643f363?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80"
	},
	{
		name: "Bamboo Sunglasses",
		price: "$65.00",
		image: "https://images.unsplash.com/photo-1577803645773-f96470509666?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80"
	},
	{
		name: "Hemp Sandals",
		price: "$49.99",
		image: "https://images.unsplash.com/photo-1603487742131-4160ec9b4339?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80"
	},
	{
		name: "Recycled Surf Watch",
		price: "$89.95",
		image: "https://images.unsplash.com/photo-1524805444758-089113d48a6d?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80"
	},
	{
		name: "Coral Reef Safe Sunscreen",
		price: "$18.99",
		image: "https://images.unsplash.com/photo-1556229010-aa3f7ff66b24?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80"
	}
];

// Pagination state
let currentPage = 1;
const productsPerPage = 6;
const totalPages = Math.ceil(128 / productsPerPage); // Simulating 128 total products

// Function to render products for the current page
function renderProducts() {
	const productGrid = document.getElementById('productGrid');
	productGrid.innerHTML = '';

	// Use a selection of products from our sample data
	// In a real app, you'd fetch products for the specific page
	const displayProducts = [];
	for (let i = 0; i < productsPerPage; i++) {
		const index = i % products.length;
		displayProducts.push(products[index]);
	}

	displayProducts.forEach(product => {
		const productCard = document.createElement('div');
		productCard.className = 'product-card';
		productCard.innerHTML = `
            <img src="${product.image}" alt="${product.name}" class="product-image">
            <div class="product-info">
                <h3 class="product-name">${product.name}</h3>
                <p class="product-price">${product.price}</p>
            </div>
        `;
		productGrid.appendChild(productCard);
	});

	updatePageInfo();
}

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

// Function to update page info text
function updatePageInfo() {
	const startItem = (currentPage - 1) * productsPerPage + 1;
	const endItem = Math.min(currentPage * productsPerPage, 128);
	document.getElementById('pageInfo').textContent = `Showing ${startItem}–${endItem} of 128 products`;
}

// Initialize the view
renderProducts();
renderPagination();

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

// Dark mode toggle
const themeToggle = document.getElementById('themeToggle');
themeToggle.addEventListener('click', () => {
	document.body.classList.toggle('dark-mode');

	// Update icon
	if (document.body.classList.contains('dark-mode')) {
		themeToggle.innerHTML = `
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z"></path>
            </svg>
        `;
	} else {
		themeToggle.innerHTML = `
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <circle cx="12" cy="12" r="5"></circle>
                <line x1="12" y1="1" x2="12" y2="3"></line>
                <line x1="12" y1="21" x2="12" y2="23"></line>
                <line x1="4.22" y1="4.22" x2="5.64" y2="5.64"></line>
                <line x1="18.36" y1="18.36" x2="19.78" y2="19.78"></line>
                <line x1="1" y1="12" x2="3" y2="12"></line>
                <line x1="21" y1="12" x2="23" y2="12"></line>
                <line x1="4.22" y1="19.78" x2="5.64" y2="18.36"></line>
                <line x1="18.36" y1="5.64" x2="19.78" y2="4.22"></line>
            </svg>
        `;
	}
});

