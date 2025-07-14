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

