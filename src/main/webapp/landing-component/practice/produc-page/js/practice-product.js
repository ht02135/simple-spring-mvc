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

/* //////////////////////////////////////// */

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

/* //////////////////////////////////////// */

// Function to update page info text
function updatePageInfo() {
	const startItem = (currentPage - 1) * productsPerPage + 1;
	const endItem = Math.min(currentPage * productsPerPage, 128);
	document.getElementById('pageInfo').textContent = `Showing ${startItem}–${endItem} of 128 products`;
}