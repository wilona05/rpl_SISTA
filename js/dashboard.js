function enableSidangContainer() {
    const sidangContainer = document.querySelector('.sidang-container');
    const roleSelect = document.querySelector('.role-pick select');

    if (roleSelect.value) {
        sidangContainer.classList.remove('hidden'); // Remove the hidden class to show the container
    }
}

// Function for "Lihat Sidang" button to show the sidang container
function showSidangContainer() {
    const sidangContainer = document.querySelector('.sidang-container');
    const searchBar = document.querySelector('.search-bar')
    const dropDown = document.querySelector(".dropdown");
    sidangContainer.classList.add('visible'); 
    searchBar.classList.add('visible'); 
    dropDown.classList.add('visible'); 
}