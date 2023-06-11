function cleanISBN(isbn) {
    return isbn.replace(/[-\s]/g, '');
}

function isValidISBN(isbn) {
    if (isbn.length !== 10 && isbn.length !== 13) {
        return false; // ISBN must be 10 or 13 digits long
    }

    const weights = [1, 3];
    let checksum = 0;

    // Compute the checksum for ISBN-10
    if (isbn.length === 10) {
        let checksum = 0

        for (let i = 0; i < 9; i++) {
            checksum += parseInt(isbn.charAt(i)) * (i + 1)
        }
        let lastChar = isbn.charAt(9)
        let lastDigit = 0
        if (lastChar === 'X') {
            lastDigit = 10
        } else {
            lastDigit = parseInt(lastChar)
        }

        return checksum % 11 === lastDigit;
    }

    // Compute the checksum for ISBN-13
    if (isbn.length === 13) {
        for (let i = 0; i < 12; i++) {
            checksum += parseInt(isbn.charAt(i)) * weights[i % 2];
        }
        return (10 - (checksum % 10)) % 10 === parseInt(isbn.charAt(12));
    }

    return false;

}

function previewImageLoad(elementId) {
    const fileInput = document.getElementById('uploadImage');
    const file = fileInput.files[0];
    const img = document.getElementById(elementId);
    const reader = new FileReader();

    reader.addEventListener('load', function () {
        img.src = reader.result;
    }, false);

    if (file) {
        reader.readAsDataURL(file);
    }
}