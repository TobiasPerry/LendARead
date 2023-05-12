document.addEventListener('DOMContentLoaded', function() {
    function loader() {
        let cards = document.querySelectorAll('.placeholder');
        cards.forEach(checkCard => {
            checkCard.classList.remove('placeholder');
        });
    }
    loader();
});