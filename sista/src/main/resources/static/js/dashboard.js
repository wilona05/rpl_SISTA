function enableSidangContainer() {
    const sidangContainer = document.querySelector('.sidang-container');
    const roleSelect = document.querySelector('.role-pick');

    if (roleSelect.value) {
        sidangContainer.classList.remove('hidden'); // Remove the hidden class to show the container
    }
}

// Function for "Lihat Sidang" button to show the sidang container
function showSidangContainer() {
    const sidangContainer = document.querySelector('.sidang-container');
    const container = document.querySelector('.container');
    sidangContainer.classList.add('visible');
    container.classList.add('visible');
}