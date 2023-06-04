document.addEventListener("DOMContentLoaded", () => {

    const star5 = document.getElementById("star5-lender");
    star5.addEventListener("click", () => {
        document.getElementById("rating-form").value = 5;
        console.log(document.getElementById("rating-form").value);
    })

    const star4 = document.getElementById("star4-lender");
    star4.addEventListener("click", () => {
        document.getElementById("rating-form").value = 4;
        console.log(document.getElementById("rating-form").value);
    })

    const star3 = document.getElementById("star3-lender");
    star3.addEventListener("click", () => {
        document.getElementById("rating-form").value = 3;
        console.log(document.getElementById("rating-form").value);
    })

    const star2 = document.getElementById("star2-lender");
    star2.addEventListener("click", () => {
        document.getElementById("rating-form").value = 2;
        console.log(document.getElementById("rating-form").value);
    })

    const star1 = document.getElementById("star1-lender");
    star1.addEventListener("click", () => {
        document.getElementById("rating-form").value = 1;
        console.log(document.getElementById("rating-form").value);
    })

    document.getElementById("review-area").addEventListener("input", ()=>{
        document.getElementById("review-form").value = document.getElementById("review-area").value;
    });


})