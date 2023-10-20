let abbreviations = [];

// Load abbreviations from storage
chrome.storage.local.get('abbreviations', function(data) {
    if (data.abbreviations) {
        abbreviations = data.abbreviations;
        renderTable();
    }
});

document.getElementById('searchInput').addEventListener('input', function(e) {
    const searchText = e.target.value.toLowerCase();
    renderTable(searchText);
});

document.getElementById('addBtn').addEventListener('click', function() {
    const key = document.getElementById('keyInput').value.trim();
    const value = document.getElementById('valueInput').value.trim();
    const link = document.getElementById('linkInput').value.trim();

    if (key && value) {
        // Add the new entry to the abbreviations array
        abbreviations.push({ key, value, link });

        // Sort the abbreviations array based on the key
        abbreviations.sort((a, b) => a.key.localeCompare(b.key));

        // Save the updated abbreviations array to chrome storage
        chrome.storage.local.set({ 'abbreviations': abbreviations }, function() {
            // Clear the input fields after successful addition
            document.getElementById('keyInput').value = '';
            document.getElementById('valueInput').value = '';
            document.getElementById('linkInput').value = '';

            // Re-render the table
            renderTable();
        });
    } else {
        alert('Both key and value are required to add a new entry.');
    }
});

function renderTable(searchText = '') {
    const tableBody = document.getElementById('tableBody');
    tableBody.innerHTML = '';
    const filteredAbbreviations = abbreviations.filter(item => item.key.toLowerCase().includes(searchText));

    for (const item of filteredAbbreviations) {
        const row = `<tr>
                        <td>${item.key}</td>
                        <td>${item.value}</td>
                        <td><a href="${item.link}" target="_blank">Link</a></td>
                        <td><button class="deleteBtn">Delete</button></td>
                     </tr>`;
        tableBody.innerHTML += row;
    }

    // After rendering the rows, add delete button functionality
    const deleteButtons = document.querySelectorAll('.deleteBtn');
    deleteButtons.forEach((btn, index) => {
        btn.addEventListener('click', function() {
            // Filter out the entry from the abbreviations array
            abbreviations = abbreviations.filter((_, itemIndex) => itemIndex !== index);

            // Save the updated abbreviations array to chrome storage
            chrome.storage.local.set({ 'abbreviations': abbreviations }, function() {
                renderTable(searchText);
            });
        });
    });
}
