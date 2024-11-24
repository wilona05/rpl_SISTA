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
    const showSidangButton = document.querySelector('.lihat-sidang-button')
    sidangContainer.classList.add('visible'); 
}