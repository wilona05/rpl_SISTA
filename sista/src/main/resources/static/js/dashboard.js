

// Function for "Lihat Sidang" button to show the sidang container
function showSidangContainer() {
    const sidangContainer = document.querySelector('.sidang-container');
    const searchBar = document.querySelector('.search-bar');
    const dropDown = document.querySelector('.dropdown');
    const roleSelect = document.querySelector('.role-pick select');
    const selectedRole = roleSelect.value;

    if (!selectedRole) {
        alert("Please select a role first.");
        return;
    }

}