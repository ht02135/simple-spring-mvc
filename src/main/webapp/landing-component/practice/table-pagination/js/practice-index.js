document.addEventListener('DOMContentLoaded', () => {
    // Sample data for demonstration
    const generateData = (count) => {
        const data = [];
        const categories = ['Income', 'Expense', 'Transfer', 'Investment', 'Withdrawal'];
        const statuses = ['active', 'pending'];
        
        for (let i = 1; i <= count; i++) {
            const date = new Date();
            date.setDate(date.getDate() - Math.floor(Math.random() * 30));
            
            data.push({
                id: `TRX-${(1000 + i).toString().padStart(6, '0')}`,
                date: date.toISOString().split('T')[0],
                amount: `$${(Math.random() * 5000).toFixed(2)}`,
                category: categories[Math.floor(Math.random() * categories.length)],
                status: statuses[Math.floor(Math.random() * statuses.length)]
            });
        }
        
        return data;
    };

    const allData = generateData(87);
    let currentPage = 1;
    let rowsPerPage = 10;
    let totalPages = Math.ceil(allData.length / rowsPerPage);

    const tableBody = document.getElementById('tableBody');
    const pagination = document.getElementById('pagination');
    const pageInfo = document.getElementById('pageInfo');
    const rowsPerPageSelect = document.getElementById('rowsPerPage');
    const jumpToPageInput = document.getElementById('jumpToPage');
    const tableLoader = document.querySelector('.table-loader');

    // Pagination logic
    const createPagination = () => {
        pagination.innerHTML = '';
        
        // First page button
        const firstPageBtn = document.createElement('button');
        firstPageBtn.innerHTML = '<span>«</span>';
        firstPageBtn.setAttribute('data-tooltip', 'First Page');
        firstPageBtn.classList.add('jump');
        firstPageBtn.disabled = currentPage === 1;
        firstPageBtn.addEventListener('click', () => goToPage(1));
        pagination.appendChild(firstPageBtn);
        
        // Previous button
        const prevBtn = document.createElement('button');
        prevBtn.innerHTML = '<span>‹</span>';
        prevBtn.setAttribute('data-tooltip', 'Previous Page');
        prevBtn.disabled = currentPage === 1;
        prevBtn.addEventListener('click', () => goToPage(currentPage - 1));
        pagination.appendChild(prevBtn);
        
        // Page numbers
        const startPage = Math.max(1, currentPage - 2);
        const endPage = Math.min(totalPages, startPage + 4);
        
        if (startPage > 1) {
            pagination.appendChild(createEllipsis());
        }
        
        for (let i = startPage; i <= endPage; i++) {
            const pageBtn = document.createElement('button');
            pageBtn.innerHTML = `<span>${i}</span>`;
            if (i === currentPage) {
                pageBtn.classList.add('active');
            }
            pageBtn.addEventListener('click', () => goToPage(i));
            pagination.appendChild(pageBtn);
        }
        
        if (endPage < totalPages) {
            pagination.appendChild(createEllipsis());
        }
        
        // Next button
        const nextBtn = document.createElement('button');
        nextBtn.innerHTML = '<span>›</span>';
        nextBtn.setAttribute('data-tooltip', 'Next Page');
        nextBtn.disabled = currentPage === totalPages;
        nextBtn.addEventListener('click', () => goToPage(currentPage + 1));
        pagination.appendChild(nextBtn);
        
        // Last page button
        const lastPageBtn = document.createElement('button');
        lastPageBtn.innerHTML = '<span>»</span>';
        lastPageBtn.setAttribute('data-tooltip', 'Last Page');
        lastPageBtn.classList.add('jump');
        lastPageBtn.disabled = currentPage === totalPages;
        lastPageBtn.addEventListener('click', () => goToPage(totalPages));
        pagination.appendChild(lastPageBtn);
    };

    const createEllipsis = () => {
        const ellipsis = document.createElement('div');
        ellipsis.className = 'ellipsis';
        ellipsis.textContent = '...';
        return ellipsis;
    };

    const goToPage = (page) => {
        if (page < 1 || page > totalPages || page === currentPage) return;
        
        tableLoader.style.display = 'flex';
        
        // Simulate loading delay for better UX
        setTimeout(() => {
            currentPage = page;
            renderTable();
            createPagination();
            updatePageInfo();
            tableLoader.style.display = 'none';
        }, 300);
    };

    const renderTable = () => {
        tableBody.innerHTML = '';
        
        const start = (currentPage - 1) * rowsPerPage;
        const end = Math.min(start + rowsPerPage, allData.length);
        
        for (let i = start; i < end; i++) {
            const row = document.createElement('tr');
            const item = allData[i];
            
            row.innerHTML = `
                <td>${item.id}</td>
                <td>${item.date}</td>
                <td>${item.amount}</td>
                <td>${item.category}</td>
                <td><span class="status ${item.status}">${item.status}</span></td>
            `;
            
            tableBody.appendChild(row);
        }
    };

    const updatePageInfo = () => {
        const start = (currentPage - 1) * rowsPerPage + 1;
        const end = Math.min(currentPage * rowsPerPage, allData.length);
        pageInfo.innerHTML = `Showing <b>${start}-${end}</b> of <b>${allData.length}</b> items`;
    };

    // Event listeners
    rowsPerPageSelect.addEventListener('change', () => {
        rowsPerPage = parseInt(rowsPerPageSelect.value);
        totalPages = Math.ceil(allData.length / rowsPerPage);
        currentPage = 1; // Reset to first page
        renderTable();
        createPagination();
        updatePageInfo();
    });

    jumpToPageInput.addEventListener('keydown', (e) => {
        if (e.key === 'Enter') {
            const page = parseInt(jumpToPageInput.value);
            if (page >= 1 && page <= totalPages) {
                goToPage(page);
                jumpToPageInput.value = '';
            } else {
                jumpToPageInput.value = '';
                alert(`Please enter a page number between 1 and ${totalPages}`);
            }
        }
    });

    // Initialize
    renderTable();
    createPagination();
    updatePageInfo();
});