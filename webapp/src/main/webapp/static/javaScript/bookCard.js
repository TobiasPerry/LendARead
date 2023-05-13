document.addEventListener('DOMContentLoaded', function() {
    function loader() {
        window.addEventListener('load', function () {
            const placeholderGroup = document.querySelector('.placeholder-group');
            placeholderGroup.style.display = 'none';
        });
    }
    loader();
});