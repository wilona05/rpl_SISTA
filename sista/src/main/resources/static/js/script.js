// JavaScript to handle the popup
document.getElementById('saveButton').addEventListener('click', function () {
    document.getElementById('popup').classList.remove('hidden');
  });
  
  document.getElementById('closePopup').addEventListener('click', function () {
    document.getElementById('popup').classList.add('hidden');
  });
  
// Ambil elemen yang dibutuhkan
const textArea = document.getElementById('textArea');
const editButton = document.getElementById('editButton');
const saveButton = document.getElementById('saveButton');
const popup = document.getElementById('popup');
const closePopup = document.getElementById('closePopup');

// Aktifkan text area saat tombol Edit ditekan
editButton.addEventListener('click', () => {
  textArea.disabled = false; // Aktifkan text area
  textArea.placeholder = ''; // Hilangkan teks placeholder
  textArea.focus(); // Arahkan kursor langsung ke text area
});

// Tampilkan popup saat tombol Save ditekan
saveButton.addEventListener('click', () => {
  if (!textArea.disabled) {
    textArea.disabled = true; // Nonaktifkan kembali text area setelah disimpan
    popup.classList.remove('hidden'); // Tampilkan popup
  }
});

// Tutup popup saat tombol Yes ditekan
closePopup.addEventListener('click', () => {
  popup.classList.add('hidden');
});
