const multiStepForm = document.getElementById('form')

const formSteps = [...multiStepForm.querySelectorAll('[data-step]')]
const stepCounters = [...multiStepForm.querySelectorAll('.stepper-item')]

let currentStep;
let currentFS;

const titleInput = document.getElementById('title');
const authorInput = document.getElementById('author');
const languageInput = document.getElementById('language');
const languageSelect = document.getElementById('languageSelect')
const form = document.getElementById('form')


nextButtons = document.querySelectorAll('.next-button')
prevButtons = document.querySelectorAll('.prev-button')

//Set up nextButtons behaviour
nextButtons.forEach(button => {
    button.addEventListener('click', async e => {
        const nextFS = currentFS.nextElementSibling;

        let OK = await checkNext(currentFS, nextFS)
        if (!OK) {
            return
        }

        const nextStepCounter = currentStep.nextElementSibling;

        currentFS.classList.remove('active')
        currentFS.classList.add('previous')

        nextFS.classList.remove('next')
        nextFS.classList.add('active')
        nextStepCounter.classList.add('active-step')
        currentFS = nextFS
        currentStep = nextStepCounter
    })
})

//Set up prevButtons behaviour
prevButtons.forEach(button => {
    button.addEventListener('click', e => {
        const prevFS = currentFS.previousElementSibling
        const prevStepCounter = currentStep.previousElementSibling

        currentFS.classList.remove('active')
        currentFS.classList.add('next')
        prevFS.classList.remove('previous')
        prevFS.classList.add('active')
        currentStep.classList.remove('active-step')
        currentStep = prevStepCounter
        currentFS = prevFS
    })
})

//Function to check if an extra validation needs to be done before going to the next step
async function checkNext(current, next) {
    if (current.id === 'isbn-fs') {
        return checkAndFetchFromISBN()
    }
    return true
}

//Extra Validation for the ISBN step
async function checkAndFetchFromISBN() {
    const isbnInput = document.getElementById('isbn')
    const isbn = cleanISBN(isbnInput.value)

    if (!isValidISBN(isbn)) {
        document.getElementById('isbnError')?.classList.remove('d-none')
        return false
    }

    isbnInput.classList.add('loading')
    nextButtons.item(0).readOnly = true


    let url = window.isbnUrl;
    const response = await fetch(url + isbn);
    const book = await response.json();

    if (book.language !== undefined && book.language.length > 0) {
        if (book.language.length !== 3) {
            for (let opt in languageSelect.options) {
                const langText = languageSelect.options[opt].innerText
                if (book.language === langText) {
                    languageInput.value = langText
                    languageSelect.value = languageSelect.options[opt].value
                    break
                }
            }
        } else {
            for (let opt in languageSelect.options) {
                const langCode = languageSelect.options[opt].value
                if (book.language.includes(langCode)) {
                    languageInput.value = languageSelect[opt].innerText
                    languageSelect.value = languageSelect.options[opt].value
                    break
                }
            }
        }
    } else {
        languageSelect.disabled = false
    }


    titleInput.value = book.name || '';
    authorInput.value = book.author || '';
    languageInput.value = book.language || '';

    if (!book.name) titleInput.readOnly = false;
    if (!book.author) authorInput.readOnly = false;
    if (!book.language) languageInput.readOnly = false;

    isbnInput.classList.remove('loading')
    nextButtons.item(0).readOnly = false

    isbnInput.value = isbn
    return true
}



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

//Before submitting
function beforeSubmit() {
    const borrowTimeQuantity = document.getElementById('borrow-time-quantity')
    const borrowTimeType = document.getElementById('borrow-time-type')
    const timeInDays = document.getElementById('maxDays')

    const totalTimeInDays = parseInt(borrowTimeQuantity.value) * parseInt(borrowTimeType.value)
    console.log("Total Days: " + totalTimeInDays)

    timeInDays.value = totalTimeInDays

    languageInput.value = languageSelect.options[languageSelect.selectedIndex].innerText

    return true
}

//Precent the Enter to submit the form
form.addEventListener('keydown', e => {
    if (e.key === 'Enter') {
        e.preventDefault();
    }
})

//Once all loaded
document.addEventListener("DOMContentLoaded", e => {

    const stepsInputs = []

    formSteps.forEach(form => {
        const children = form.querySelectorAll("input[type=text]")
        const inputNames = []
        children.forEach(child => {
            inputNames.push(child.getAttribute('name'))

        })
        stepsInputs[form.dataset.step] = inputNames
    })

    let fsIndex = 1
    if (!bindingResult.includes("BindingResult: 0 errors")) {
        let errors = bindingResult.split('\n').slice(1)
        let fieldNames = errors.map(e => {
            const match = e.match(/on field '([^']+)'/);
            if (match) return match[1]
            return null
        })
        for (let step in stepsInputs) {
            let found = false
            fieldNames.every(err => {
                if (stepsInputs[step].includes(err)) {
                    found = true
                    return false
                }
                return true
            })
            if (found) {
                fsIndex = parseInt(step)
                break
            }
            fsIndex = formSteps.length
        }
    }

    currentFS = formSteps.find(step => step.dataset.step === fsIndex.toString())
    currentStep = stepCounters[formSteps.indexOf(currentFS)]
    formSteps.forEach(form => {
        let step = parseInt(form.dataset.step)
        if (step < fsIndex) {
            form.classList.add('previous')
        }
        if (step === fsIndex) {
            form.classList.add('active')
        }
        if (step > fsIndex) {
            form.classList.add('next')
        }
        form.classList.remove('d-none')
    })


    let stepCounterIndex = parseInt(currentStep.dataset.stepCount)
    stepCounters.forEach(step => {
        let index = parseInt(step.dataset.stepCount)
        if (index <= stepCounterIndex) {
            step.classList.add('active-step')
        }
    })


    //One final check. If the currentFS is 2, we should not make readOnly the empty inputs
    if (fsIndex === 2) {
        const titleInput = document.getElementById('title');
        const authorInput = document.getElementById('author');
        const languageInput = document.getElementById('language');

        console.log(titleInput.value.length)

        if (titleInput.value === '') {
            titleInput.readOnly = false
        }
        if (authorInput.value === '') {
            authorInput.readOnly = false
        }
        if (languageInput.value === '') {
            languageInput.readOnly = false
        }
    }
})