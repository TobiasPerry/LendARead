document.addEventListener("DOMContentLoaded", () => {

    const star5Lender = document.getElementById("star5-borrower-asset-instance");
    star5Lender.addEventListener("click", () => {
        document.getElementById("rating-form-asset-instance").value = 5;
    })

    const star4Lender = document.getElementById("star4-borrower-asset-instance");
    star4Lender.addEventListener("click", () => {
        document.getElementById("rating-form-asset-instance").value = 4;
    })

    const star3Lender = document.getElementById("star3-borrower-asset-instance");
    star3Lender.addEventListener("click", () => {
        document.getElementById("rating-form-asset-instance").value = 3;
    })

    const star2Lender = document.getElementById("star2-borrower-asset-instance");
    star2Lender.addEventListener("click", () => {
        document.getElementById("rating-form-asset-instance").value = 2;
    })

    const star1Lender = document.getElementById("star1-borrower-asset-instance");
    star1Lender.addEventListener("click", () => {
        document.getElementById("rating-form-asset-instance").value = 1;
    })

    const star5User = document.getElementById("star5-borrower-user");
    star5User.addEventListener("click", () => {
        document.getElementById("rating-form-user").value = 5;
    })

    const star4User = document.getElementById("star4-borrower-user");
    star4User.addEventListener("click", () => {
        document.getElementById("rating-form-user").value = 4;
    })

    const star3User = document.getElementById("star3-borrower-user");
    star3User.addEventListener("click", () => {
        document.getElementById("rating-form-user").value = 3;
    })

    const star2User = document.getElementById("star2-borrower-user");
    star2User.addEventListener("click", () => {
        document.getElementById("rating-form-user").value = 2;
    })

    const star1User = document.getElementById("star1-borrower-user");
    star1User.addEventListener("click", () => {
        document.getElementById("rating-form-user").value = 1;
    })

    document.getElementById("review-area-asset-instance").addEventListener("input", ()=>{
        document.getElementById("review-asset-instance-form").value = document.getElementById("review-area-asset-instance").value;
    });

    document.getElementById("review-area-user").addEventListener("input", ()=>{
        document.getElementById("review-user-form").value = document.getElementById("review-area-user").value;
    });

})