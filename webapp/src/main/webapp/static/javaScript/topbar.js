document.addEventListener("DOMContentLoaded",(event)=>{
    let path = document.querySelector('body').dataset.path;
    if(path !== "home")
        document.querySelector(`#${path}`).classList.add("active");

})