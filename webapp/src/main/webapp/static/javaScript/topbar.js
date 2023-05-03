document.addEventListener("DOMContentLoaded",(event)=>{
    let path = document.querySelector('body').dataset.path;
    try {
        const querySelector = document.querySelector(`#${path}`)
        if (querySelector !== null) {
            querySelector.classList.add("active");
        }
    }catch (error){

    }
})