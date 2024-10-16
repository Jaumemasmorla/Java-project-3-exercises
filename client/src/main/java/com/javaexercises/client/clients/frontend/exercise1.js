
const itemTableBody = document.getElementById('item-table-body');
const errorMessage = document.getElementById('error-message');
const successMessage = document.getElementById('success-message');


const getAllItemsBtn = document.getElementById('get-all-items');
const getItemByNameBtn = document.getElementById('get-item-by-name');
const addItemBtn = document.getElementById('add-item');
const updateItemBtn = document.getElementById('update-item');
const deleteItemBtn = document.getElementById('delete-item');


const getItemContainer = document.getElementById('get-item-container');
const addItemContainer = document.getElementById('add-item-container');
const updateItemContainer = document.getElementById('update-item-container');
const deleteItemContainer = document.getElementById('delete-item-container');


document.addEventListener('DOMContentLoaded', () => {
    fetchItems();
});


getAllItemsBtn.addEventListener('click', fetchItems);
getItemByNameBtn.addEventListener('click', () => {
    toggleInputVisibility('get');
});
addItemBtn.addEventListener('click', () => {
    toggleInputVisibility('add');
});
updateItemBtn.addEventListener('click', () => {
    toggleInputVisibility('update');
});
deleteItemBtn.addEventListener('click', () => {
    toggleInputVisibility('delete');
});


function fetchItems() {
    fetch('http://localhost:8081/api/v1/client/items')
        .then(response => {
            if (!response.ok) {
                throw new Error('Error fetching items: ' + response.statusText);
            }
            return response.json();
        })
        .then(items => {
            displayItems(items);
        })
        .catch(error => {
            displayError(error.message);
        });
}


function displayItems(items) {
    itemTableBody.innerHTML = '';

    if (items.length === 0) {
        itemTableBody.innerHTML = '<tr><td colspan="1">No items found</td></tr>';
    } else {
        items.forEach(item => {
            const row = document.createElement('tr');
            const cell = document.createElement('td');
            cell.textContent = item.name;
            row.appendChild(cell);
            itemTableBody.appendChild(row);
        });
    }
}


function displayError(message) {
    errorMessage.textContent = message;
    successMessage.textContent = '';
}


function displaySuccess(message) {
    successMessage.textContent = message;
    errorMessage.textContent = '';
}


function toggleInputVisibility(action) {
    getItemContainer.style.display = 'none';
    addItemContainer.style.display = 'none';
    updateItemContainer.style.display = 'none';
    deleteItemContainer.style.display = 'none';

    if (action === 'get') {
        getItemContainer.style.display = 'block';
    } else if (action === 'add') {
        addItemContainer.style.display = 'block';
    } else if (action === 'update') {
        updateItemContainer.style.display = 'block';
    } else if (action === 'delete') {
        deleteItemContainer.style.display = 'block';
    }
}


document.getElementById('fetch-item').addEventListener('click', () => {
    const itemName = document.getElementById('item-name-get').value;
    fetch(`http://localhost:8081/api/v1/client/items/${itemName}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error fetching item: ' + response.statusText);
            }
            return response.json();
        })
        .then(item => {
            displayItems([item]);
        })
        .catch(error => {
            displayError(error.message);
        });
});

// Add item
document.getElementById('submit-item').addEventListener('click', () => {
    const newItemName = document.getElementById('new-item-name').value;
    fetch('http://localhost:8081/api/v1/client/items', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ name: newItemName }),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error adding item: ' + response.statusText);
            }
            return response.json();
        })
        .then(() => {

            fetchItems();
        })
        .catch(error => {
            displayError(error.message);
        });
});


document.getElementById('submit-update').addEventListener('click', () => {
    const itemName = document.getElementById('update-item-name').value.trim();
    const newName = document.getElementById('new-name').value.trim();

    fetch(`http://localhost:8081/api/v1/client/items/${itemName}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ name: newName }),
    })
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                return response.text().then(text => {
                    throw new Error('Error updating item: ' + text);
                });
            }
        })
        .then(data => {

            alert(`Item "${itemName}" updated to "${newName}" successfully!`);
            displaySuccess(data);
            fetchItems();
        })
        .catch(error => {
            displayError(error.message);
        });
});






// Delete item
// Function to delete an item
async function deleteItem(name) {
    const cleanedName = name.replace(/[{}"]/g, '').trim();  // Clean the name by removing special characters
    const response = await fetch(`http://localhost:8081/api/v1/client/items/${encodeURIComponent(cleanedName)}`, {
        method: 'DELETE',
    });

    if (response.ok) {
        alert(`${name} has been deleted`);
        fetchItems(); // Refresh the list after deletion
    } else {
        alert(`Failed to delete ${name}`);
    }
}


// Delete item event listener
document.getElementById('submit-delete').addEventListener('click', () => {
    const itemName = document.getElementById('item-name-delete').value;

    deleteItem(itemName.trim()) // Call the deleteItem function
        .catch(error => {
            displayError(error.message); // Display error if deletion fails
        });
});


