const multiStepForm = document.getElementById('form')

const formSteps = [...multiStepForm.querySelectorAll('[data-step]')]

let currentFS = formSteps.find(step => step.dataset.step === "1")


nextButtons = document.querySelectorAll('.next-button')
prevButtons = document.querySelectorAll('.prev-button')


nextButtons.forEach(button => {
    button.addEventListener('click', e => {
        const nextFS = currentFS.nextElementSibling;

        console.log(currentFS.nextElementSibling)

        currentFS.classList.remove('active')
        currentFS.classList.add('previous')

        nextFS.classList.remove('next')
        nextFS.classList.add('active')
        currentFS = nextFS
    })
})

prevButtons.forEach(button => {
    button.addEventListener('click', e => {
        const prevFS = currentFS.previousElementSibling

        currentFS.classList.remove('active')
        currentFS.classList.add('next')
        prevFS.classList.remove('previous')
        prevFS.classList.add('active')
        currentFS = prevFS
    })
})