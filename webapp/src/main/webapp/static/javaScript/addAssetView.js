inputs = document.getElementsByClassName("onlyNumber-input")

Array.prototype.forEach.call(inputs, function (item,indx)
{
    item.addEventListener("keypress", (event) => {
        if (event.key.length !== 1) {
            return true;
        }
        if (event.target.value.length > 0) {
            if ("0123456789".indexOf(event.key) === -1) {
                event.preventDefault();
                return false;
            }
        } else {
            if ("123456789".indexOf(event.key) === -1) {
                event.preventDefault();
                return false;
            }
        }
    })});


function previewImage() {
    const fileInput = document.getElementById('uploadImage');
    const file = fileInput.files[0];
    const img = document.getElementById('bookImage');
    const reader = new FileReader();

    reader.addEventListener('load', function () {
        img.src = reader.result;
    }, false);

    if (file) {
        reader.readAsDataURL(file);
    }
}