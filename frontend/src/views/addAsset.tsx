import './styles/addAsset.css';
import { useState } from 'react';
import axios from 'axios';

const ISBN_API_BASE_URL = 'https://openlibrary.org';

const AddAsset = () => {
    const states = [
        ["ASNEW", "As New"],
        ["FINE", "Fine"],
        ["VERYGOOD", "Very Good"],
        ["GOOD", "Good"],
        ["FAIR", "Fair"],
        ["POOR", "Poor"],
        ["EXLIBRARY", "Ex-Library"],
        ["BOOKCLUB", "Book Club"],
        ["BINDINGCOPY", "Binding Copy"],
    ]

    const timeTypes = [
        [1, "day/s"],
        [7, "week/s"],
        [31, "month/s"],
    ]

    const locations = [
        {
            locality: "Cebu City",
            province: "Cebu",
            country: "Philippines",
            zipcode: "6000",
        }
    ]

    const [step, setStep] = useState(1);

    const getAuthor = async (authors: any[]) => {
        if (authors.length === 0) {
            return null;
        }
        const authorId: string = authors[0].key;
        try {
            const url = ISBN_API_BASE_URL + authorId + '.json';
            const response = await axios.get(url);
            if (response.status === 200) {
                return response.data.name;
            } else {
                return null;
            }
        } catch (error) {
            return null
        }
    }

    const getLanguage = (langArray: any[]) => {
        if (langArray.length === 0) {
            return null;
        }
        return langArray[0].key.replace('/languages/', '')
    }

    const getBookOpenLib = async (isbn: string) => {
        try {
            const url = ISBN_API_BASE_URL + '/isbn/' + isbn + '.json';
            const response = await axios.get(url);

            if (response.status === 200) {
                const data = response.data;
                const title = data.title;
                const langArray = data.languages || [];
                const authorArray = data.authors || [];
                const author = await getAuthor(authorArray);
                const lang = getLanguage(langArray);

                return {
                    title: title,
                    author: author,
                    lang: lang,
                    isbn: isbn,
                }
            }
            return null;
        } catch (error) {
            return null;
        }
    }

    const validateIsbn = (isbn: string) => {
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



    const showImage = () => {
        const image = document.querySelector('.image-container img') as HTMLImageElement;
        const input = document.querySelector('#uploadImage') as HTMLInputElement;
        const reader = new FileReader();
        reader.onload = function(e) {
            image.src = e.target.result as string;
        }
        reader.readAsDataURL(input.files[0]);
    }

    const getFormSteps = () => {
        const children = document.querySelectorAll('.form-container')[0].children;
        const steps = Array.from(children).filter((step) => {
            // Return true if is class "info-container"
            return step.classList.contains('info-container');
        })
        return steps;
    }

    const validateStep1 = async () => {
        const isbnInput = document.getElementById('isbn') as HTMLInputElement;
        const isbn = isbnInput.value.replace(/[-\s]/g, '');
        if (!isbn || !validateIsbn(isbn)) {
            document.getElementById('isbn-error')?.classList.remove('d-none');
            return false;
        }

        isbnInput.classList.remove('is-invalid');
        isbnInput.classList.add('is-loading');

        const nextBtn = document.getElementById('isbn-next-btn') as HTMLInputElement;
        nextBtn.disabled = true;


        // TODO: First try with our API


        // If it is not in our API, try with OpenLibrary API
        const book = await getBookOpenLib(isbn);

        if (book.title) {
            const titleInput = document.getElementById('title') as HTMLInputElement;
            titleInput.value = book.title;
            titleInput.readOnly = true;
        }

        if (book.author) {
            const authorInput = document.getElementById('author') as HTMLInputElement;
            authorInput.value = book.author;
            authorInput.readOnly = true;
        }

        if (book.lang) {
            const languageInput = document.getElementById('language') as HTMLInputElement;
            languageInput.value = book.lang;
            languageInput.readOnly = true;
        }


        nextBtn.disabled = false;
        return true;


    }


    const validations = [
        validateStep1,
    ]

    const nextStep = async () => {
        const steps = getFormSteps();
        const currentStep = steps[step - 1] as HTMLFieldSetElement;
        const nextStep = steps[step] as HTMLFieldSetElement;

        const validation = validations[step - 1];
        const is_valid = await validation();
        if (!is_valid) {
            return
        }
        currentStep.classList.add('d-none');
        nextStep.classList.remove('d-none');
        setStep(step + 1);
    }

    const prevStep = () => {
        const steps = getFormSteps();
        const currentStep = steps[step - 1] as HTMLFieldSetElement;
        const prevStep = steps[step - 2] as HTMLFieldSetElement;
        currentStep.classList.add('d-none');
        prevStep.classList.remove('d-none');
        setStep(step - 1);
    }


    // TODO Check classes
    return (
        <div className="addasset-container flex-column">
            <h1 className="text-center mb-5">Do you want to borrow a book?</h1>
            <div className="p-4 addasset-container flex-container">
                <div className='flex-container'>
                    <div className="image-wrapper">
                        <label htmlFor='uploadImage' className='image-container position-relative'>
                            <img src="/static/no_image_placeholder.jpg" alt="Book Cover" className="img-fluid object-fit-cover" />
                            <div className='img-hover-text'>
                                <i className="bi bi-cloud-upload"></i>
                            </div>
                        </label>
                    </div>

                    <form className="form-container">
                        <div className='stepper'>
                        </div>
                        <fieldset className="info-container">
                            <h2>ISBN</h2>
                            <p> We need the ISBN to get info about the book. If there's missing parts, you'll be asked to complete it.</p>
                            <input type="text" placeholder="ISBN" className='form-control' id='isbn' />
                            <small id='isbn-error' className="text-danger small d-none">Please input a valid ISBN</small>
                            <div className="button-container">
                                <input type='button' className='prev-button btn btn-outline-success mx-1' value='Previous' disabled />
                                <input type='button' className='next-button btn btn-outline-success mx-1' value='Next' id='isbn-next-btn' onClick={nextStep} />
                            </div>
                        </fieldset>

                        <fieldset className="d-none info-container">
                            <h2>Book Details</h2>
                            <div className='field-group'>
                                <div className='field'>
                                    <label htmlFor='title' className='form-label'>Title</label>
                                    <input type='text' className='form-control' id='title' placeholder='Title' readOnly />
                                </div>
                                <div className='field'>
                                    <label htmlFor='physicalCondition' className='form-label'>Physical Condition</label>
                                    <select id='physicalCondition' className='form-control'>
                                        {states.map((state) => {
                                            return <option key={state[0]} value={state[0]}>{state[1]}</option>
                                        })}
                                    </select>
                                </div>
                            </div>
                            <div className='field-group'>
                                <div className='field'>
                                    <label htmlFor='author' className='form-label'>Author</label>
                                    <input type='text' className='form-control' id='author' placeholder='Author' readOnly />
                                </div>
                                <div className='field'>
                                    <label htmlFor='language' className='form-label'>Language</label>
                                    <input type='text' className='form-control' id='language' placeholder='Language' readOnly />
                                </div>
                            </div>
                            <div className="button-container">
                                <input type='button' className='prev-button btn btn-outline-success mx-1' onClick={prevStep} value='Previous' />
                                <input type='button' className='next-button btn btn-outline-success mx-1' onClick={nextStep} value='Next' />
                            </div>
                        </fieldset>

                        <fieldset className="d-none info-container">
                            <h2>Max Borrowing Period</h2>
                            <div className='field-group'>
                                <label>Lend for</label>
                                <input type='number' className='form-control' id='borrow-time-quantity' name='borrow-time-quantity' min='1' placeholder='Lend for' />
                                <select id='borrow-time-type' className='form-select' >
                                    {timeTypes.map((timeType) => {
                                        return <option key={timeType[0]} value={timeType[0]}>{timeType[1]}</option>
                                    })}
                                </select>
                            </div>
                            <div className="button-container">
                                <input type='button' className='prev-button btn btn-outline-success mx-1' onClick={prevStep} value='Previous' />
                                <input type='button' className='next-button btn btn-outline-success mx-1' onClick={nextStep} value='Next' />
                            </div>
                        </fieldset>
                        <fieldset className='info-container d-none'>

                        </fieldset>
                        <input type="file" className='d-none' accept="image/*" name="file" id="uploadImage" onChange={showImage} />
                    </form>
                </div>
            </div>
        </div>
    )
}

export default AddAsset;
