document.addEventListener("DOMContentLoaded",(event)=>{
    let path = document.querySelector('body').dataset.path;
    document.querySelector(`#${path}`).classList.add("active");

})