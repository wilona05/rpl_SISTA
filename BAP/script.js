document.querySelector("#upload-btn").addEventListener("click", function(){
    document.body.classList.add("active-popup");
});

document.querySelector("#upload-popup .ok-btn").addEventListener("click", function(){
    document.body.classList.remove("active-popup");
})

document.querySelector("#download-btn").addEventListener("click", function(){
    document.body.classList.add("active-popup-d");
});

document.querySelector("#download-popup .ok-btn").addEventListener("click", function(){
    document.body.classList.remove("active-popup-d");
})
