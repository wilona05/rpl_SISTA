/*let isEditing = false;

// Object to store saved values for later editing
let savedValues = {
    nilai1: null,
    nilai2: null,
    nilai3: null,
    nilai4: null
};

// Function to handle Edit button click
document.getElementById("editButton").addEventListener("click", function() {
    if (isEditing) {
        alert("You are already in edit mode.");
    } else {
        isEditing = true;
        alert("You can now edit the values.");
    
        // Enable input fields and restore the saved values
        document.getElementById("nilai1").value = savedValues.nilai1 || '';
        document.getElementById("nilai2").value = savedValues.nilai2 || '';
        document.getElementById("nilai3").value = savedValues.nilai3 || '';
        document.getElementById("nilai4").value = savedValues.nilai4 || '';

        const inputs = document.querySelectorAll('input');
        inputs.forEach(input => input.disabled = false); // Enable the inputs
    }
});

// Function to handle Save button click
document.getElementById("saveButton").addEventListener("click", function() {
    const nilai1 = document.getElementById("nilai1").value;
    const nilai2 = document.getElementById("nilai2").value;
    const nilai3 = document.getElementById("nilai3").value;
    const nilai4 = document.getElementById("nilai4").value;

    // Check if all values are entered
    if (nilai1 && nilai2 && nilai3 && nilai4) {
        // Save the values in the savedValues object
        savedValues.nilai1 = nilai1;
        savedValues.nilai2 = nilai2;
        savedValues.nilai3 = nilai3;
        savedValues.nilai4 = nilai4;

        // Replace the input fields with saved values as static text (in <td>)
        document.getElementById("nilai1").outerHTML = `<td>${nilai1}</td>`;
        document.getElementById("nilai2").outerHTML = `<td>${nilai2}</td>`;
        document.getElementById("nilai3").outerHTML = `<td>${nilai3}</td>`;
        document.getElementById("nilai4").outerHTML = `<td>${nilai4}</td>`;

        // Disable the inputs again after saving
        alert("Data has been saved successfully!");
        const inputs = document.querySelectorAll('input');
        inputs.forEach(input => input.disabled = true);
        isEditing = false;
    } else {
        alert("Please enter all values before saving.");
    }
});*/

let isEditing = false;

// Object to store saved values for later editing
let savedValues = {
    nilai1: null,
    nilai2: null,
    nilai3: null,
    nilai4: null
};

// Function to handle Edit button click
document.getElementById("editButton").addEventListener("click", function() {
    if (isEditing) {
        alert("Sudah dalam mode edit.");
    } else {
        isEditing = true;
        alert("Dapat melakukan edit nilai.");
    
        // Enable input fields and restore the saved values
        document.getElementById("nilai1").value = savedValues.nilai1 || '';
        document.getElementById("nilai2").value = savedValues.nilai2 || '';
        document.getElementById("nilai3").value = savedValues.nilai3 || '';
        document.getElementById("nilai4").value = savedValues.nilai4 || '';

        const inputs = document.querySelectorAll('input');
        inputs.forEach(input => input.disabled = false); // Enable the inputs
    }
});

// Function to handle Save button click
document.getElementById("saveButton").addEventListener("click", function() {
    const nilai1 = document.getElementById("nilai1").value;
    const nilai2 = document.getElementById("nilai2").value;
    const nilai3 = document.getElementById("nilai3").value;
    const nilai4 = document.getElementById("nilai4").value;

    // Check if all values are entered
    if (nilai1 && nilai2 && nilai3 && nilai4) {
        // Save the values in the savedValues object
        savedValues.nilai1 = nilai1;
        savedValues.nilai2 = nilai2;
        savedValues.nilai3 = nilai3;
        savedValues.nilai4 = nilai4;

        // Show the notification
        document.getElementById("notification").style.display = 'flex';

        // Disable the inputs again after saving
        const inputs = document.querySelectorAll('input');
        inputs.forEach(input => input.disabled = true);
        isEditing = false;
    } else {
        alert("Silahkan mengisi nilai secara lengkap.");
    }
});

// Function to handle Yes button click in the notification
document.getElementById("yesButton").addEventListener("click", function() {
    // Hide the notification
    document.getElementById("notification").style.display = 'none';

    // Update the table with the saved values
    document.getElementById("nilai1").outerHTML = `<td>${savedValues.nilai1}</td>`;
    document.getElementById("nilai2").outerHTML = `<td>${savedValues.nilai2}</td>`;
    document.getElementById("nilai3").outerHTML = `<td>${savedValues.nilai3}</td>`;
    document.getElementById("nilai4").outerHTML = `<td>${savedValues.nilai4}</td>`;
});




