// // Function for "Lihat Sidang" button to show the sidang container
// function showSidangContainer() {
//     const sidangContainer = document.querySelector('.sidang-container');
//     const searchBar = document.querySelector('.search-bar');
//     const dropDown = document.querySelector('.dropdown');
//     const roleSelect = document.querySelector('.role-pick select');
//     const lihatButton = document.querySelector('.lihat-sidang-button');
//     const selectedRole = roleSelect.value;

//     // Check if a role is selected
//     if (!selectedRole) {
//         alert("Please select a role first.");
//         return;
//     }

//     roleSelect.classList.add('hidden');
//     lihatButton.classList.add('hidden');

//     // Make the sidang container visible
//     sidangContainer.classList.remove('hidden');

//     // Optionally, show additional UI elements (e.g., search bar, dropdown)
//     searchBar.classList.remove('hidden');
//     dropDown.classList.remove('hidden');
    
//     // Log selected role for debugging
//     console.log(`Role selected: ${selectedRole}`);
// }
