let abbreviations = [];

chrome.storage.local.get('abbreviations', function(data) {
    if (data.abbreviations) {
        abbreviations = data.abbreviations;
    } else {
        // If 'abbreviations' doesn't exist in storage, initialize it as an empty array
        chrome.storage.local.set({ 'abbreviations': [] });
    }
    renderTable();
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
        abbreviations.push({ key, value, link });
        abbreviations.sort((a, b) => a.key.localeCompare(b.key));
        chrome.storage.local.set({ 'abbreviations': abbreviations }, function() {
            document.getElementById('keyInput').value = '';
            document.getElementById('valueInput').value = '';
            document.getElementById('linkInput').value = '';
            renderTable();
        });
    } else {
        alert('Both key and value are required to add a new entry.');
    }
});

document.getElementById('configBtn').addEventListener('click', function() {
    var backupControls = document.getElementById('backupControls');
    // Toggle visibility
    if (backupControls.style.display === "none") {
        backupControls.style.display = "flex"; // Using flex here so the controls align horizontally
    } else {
        backupControls.style.display = "none";
    }
});

function renderTable(searchText = '') {
    const tableBody = document.getElementById('tableBody');
    tableBody.innerHTML = '';

    const filteredAbbreviations = abbreviations.filter(item => item.key.toLowerCase().includes(searchText));

    for (const item of filteredAbbreviations) {
        const linkWord = item.link.trim() ? `<a href="${item.link}" target="_blank">Link</a>` : '';
        const row = `
                <tr>
                    <td><span class="key-text">${item.key}</span><input type="text" value="${item.key}" class="editable-input key-input" hidden></td>
                    <td><span class="value-text">${item.value}</span><input type="text" value="${item.value}" class="editable-input value-input" hidden></td>
                    <td><span class="link-text">${linkWord}</span><input type="text" value="${item.link}" class="editable-input link-input" hidden></td>
                    <td>
                        <button class="editBtn">Edit</button>
                        <button class="deleteBtn">Delete</button>
                    </td>
                </tr>`;
        tableBody.insertAdjacentHTML('beforeend', row);
    }

    // Save button functionality
    const saveButtons = document.querySelectorAll('.saveBtn');
    saveButtons.forEach((btn, index) => {
        btn.addEventListener('click', function() {
            const row = btn.closest('tr');
            const key = row.querySelector('.key-input').value;
            const value = row.querySelector('.value-input').value;
            const link = row.querySelector('.link-input').value;

            abbreviations[index] = { key, value, link };

            chrome.storage.local.set({ 'abbreviations': abbreviations }, function() {
                renderTable(searchText);
            });
        });
    });

   // Delete button functionality
   const deleteButtons = document.querySelectorAll('.deleteBtn');
   deleteButtons.forEach((btn, index) => {
       btn.addEventListener('click', function() {
           const confirmDelete = window.confirm("Are you sure you want to delete this abbreviation?");
           if (confirmDelete) {
               abbreviations = abbreviations.filter((_, itemIndex) => itemIndex !== index);
               chrome.storage.local.set({ 'abbreviations': abbreviations }, function() {
                   renderTable(searchText);
               });
           }
       });
   });

   // Edit button functionality
   const editButtons = document.querySelectorAll('.editBtn');
   editButtons.forEach((btn, index) => {
       btn.addEventListener('click', function() {
           const row = btn.closest('tr');

           const keySpan = row.querySelector('.key-text');
           const valueSpan = row.querySelector('.value-text');
           const linkSpan = row.querySelector('.link-text');

           const keyInput = row.querySelector('.key-input');
           const valueInput = row.querySelector('.value-input');
           const linkInput = row.querySelector('.link-input');

           // If we're in edit mode, save the changes and toggle to view mode
           if (btn.innerText === "Save") {
               const key = keyInput.value;
               const value = valueInput.value;
               const link = linkInput.value;

               abbreviations[index] = { key, value, link };
               chrome.storage.local.set({ 'abbreviations': abbreviations }, function() {
                   renderTable(searchText);
               });
           }
           // If we're in view mode, switch to edit mode
           else {
               keySpan.style.display = "none";
               valueSpan.style.display = "none";
               linkSpan.style.display = "none";

               keyInput.style.display = "block";
               valueInput.style.display = "block";
               linkInput.style.display = "block";

               btn.innerText = "Save";
           }
       });
   });
}

document.getElementById('exportBackupBtn').addEventListener('click', function() {
    chrome.storage.local.get('abbreviations', function(data) {
        const blob = new Blob([JSON.stringify(data.abbreviations)], { type: 'application/json' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'extension_backup.json';
        a.click();
        URL.revokeObjectURL(url);
    });
});

document.getElementById('importBackupInput').addEventListener('change', function(event) {
    var file = event.target.files[0];

    if (file) {
        var reader = new FileReader();

        reader.onload = function(e) {
            var data;
            try {
                data = JSON.parse(e.target.result); // Assuming backup file is in JSON format
            } catch (error) {
                console.error("Error parsing backup file:", error);
                return;
            }

            if (Array.isArray(data)) {
                mergeDataWithTable(data);
            } else {
                console.error("Backup file does not contain valid data.");
            }
        };

        reader.onerror = function() {
            console.error("Error reading backup file.");
        };

        reader.readAsText(file);
    }
});

function mergeDataWithTable(data) {
    // For each entry in the imported data...
    data.forEach(entry => {
        // Check if it exists in the current abbreviations
        const exists = abbreviations.some(abbrev => abbrev.key === entry.key);

        // If not, add it to abbreviations
        if (!exists) {
            abbreviations.push(entry);
        }
    });

    // Sort and update the storage and table
    abbreviations.sort((a, b) => a.key.localeCompare(b.key));
    chrome.storage.local.set({ 'abbreviations': abbreviations }, function() {
        renderTable();
    });
}

document.getElementById('resetSearch').addEventListener('click', function() {
    const searchInput = document.getElementById('searchInput');
    searchInput.value = '';
    renderTable(searchInput.value); // Assuming this function updates your table based on the search input
});
