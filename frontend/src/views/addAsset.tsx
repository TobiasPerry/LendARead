import './styles/addAsset.css';
import {useState, useEffect, useContext} from 'react';
import {useTranslation} from "react-i18next";
import axios from 'axios';
import { api } from '../hooks/api/api.ts'
import {AuthContext} from "../contexts/authContext.tsx";

const ISBN_API_BASE_URL = 'https://openlibrary.org';

const AddAsset = () => {
    
    const {t} = useTranslation();

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
        [1, t('addAsset.duration.days')],
        [7, t('addAsset.duration.weeks')],
        [31, t('addAsset.duration.months')]
    ]

    const {userDetails} = useContext(AuthContext);
    const [step, setStep] = useState(1);
    const [languages, setLanguages] = useState<any[]>([])

    useEffect(() => {
        api.get('/languages').then((response) => {
            setLanguages(response.data)
        })
    }, [])

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
        console.log(userDetails)
        const isbnInput = document.getElementById('isbn') as HTMLInputElement;
        const isbn = isbnInput.value.replace(/[-\s]/g, '');
        if (!isbn || !validateIsbn(isbn)) {
            document.getElementById('isbn-error')?.classList.remove('d-none');
            return false;
        }
        isbnInput.classList.add('loading');

        const nextBtn = document.getElementById('isbn-next-btn') as HTMLInputElement;
        nextBtn.disabled = true;


        // TODO: First try with our API


        // If it is not in our API, try with OpenLibrary API
        const book = await getBookOpenLib(isbn);

        if (book) {
            const titleInput = document.getElementById('title') as HTMLInputElement;
            if (book.title) {
                titleInput.value = book.title;
                titleInput.readOnly = true;
            } else {
                titleInput.value = '';
                titleInput.readOnly = false;
            }
            
            const authorInput = document.getElementById('author') as HTMLInputElement;
            if (book.author) {
                authorInput.value = book.author;
                authorInput.readOnly = true;
            } else {
                authorInput.value = '';
                authorInput.readOnly = false;
            }

            const languageInput = document.getElementById('language') as HTMLInputElement;
            const languageSelect = document.getElementById('languageSelect') as HTMLSelectElement;
            if (book.lang) {
                let selectedLang = languages.filter((language) => { return language.code == book.lang })[0]
                if (selectedLang) {
                    languageSelect.value = selectedLang.code;
                    languageSelect.disabled = true;
                } else {
                    languageSelect.value = '';
                    languageSelect.disabled = false;
                }
            } else {
                languageSelect.value = '';
                languageSelect.disabled = false;
            }

        } else {
            const titleInput = document.getElementById('title') as HTMLInputElement;
            titleInput.value = '';
            titleInput.readOnly = false;
            const authorInput = document.getElementById('author') as HTMLInputElement;
            authorInput.value = '';
            authorInput.readOnly = false;
            const languageInput = document.getElementById('language') as HTMLInputElement;
            languageInput.value = '';
            languageInput.readOnly = false;
        }

        nextBtn.disabled = false;
        isbnInput.classList.remove('loading');
        document.getElementById('isbn-error')?.classList.add('d-none');
        return true;
    }

    const validateStep2 = () => {
        console.log(userDetails)
        let valid = true
        const titleInput = document.getElementById('title') as HTMLInputElement;
        const authorInput = document.getElementById('author') as HTMLInputElement;
        const languageInput = document.getElementById('language') as HTMLInputElement;
        const languageSelect = document.getElementById('languageSelect') as HTMLSelectElement;
        const physicalConditionInput = document.getElementById('physicalCondition') as HTMLInputElement;

        if (!titleInput.value ) {
            valid = false;
            const error = document.getElementById('title-error') as HTMLInputElement;
            error.classList.remove('d-none');
        } else {
            const error = document.getElementById('title-error') as HTMLInputElement;
            error.classList.add('d-none');
        }
        if (!authorInput.value ) {
            valid = false;
            const error = document.getElementById('author-error') as HTMLInputElement;
            error.classList.remove('d-none');
        } else {
            const error = document.getElementById('author-error') as HTMLInputElement;
            error.classList.add('d-none');
        }

        if (!languageSelect.value ) {
            valid = false;
            const error = document.getElementById('language-error') as HTMLInputElement;
            error.classList.remove('d-none');
        } else {
            const error = document.getElementById('language-error') as HTMLInputElement;
            error.classList.add('d-none');
        }

        if (!physicalConditionInput.value ) {
            valid = false;
            const error = document.getElementById('physicalCondition-error') as HTMLInputElement;
            error.classList.remove('d-none');
        } else {
            const error = document.getElementById('physicalCondition-error') as HTMLInputElement;
            error.classList.add('d-none');
        }

        if (valid) {
            const titleError = document.getElementById('title-error') as HTMLInputElement;
            const authorError = document.getElementById('author-error') as HTMLInputElement;
            const languageError = document.getElementById('language-error') as HTMLInputElement;
            const physicalConditionError = document.getElementById('physicalCondition-error') as HTMLInputElement;
            languageInput.value = languageSelect.value;
            titleError.classList.add('d-none');
            authorError.classList.add('d-none');
            languageError.classList.add('d-none');
            physicalConditionError.classList.add('d-none');
        }
        return valid;
    }

    const validateStep3 = () => {
        console.log(userDetails)
        const borrowTimeQuantityInput = document.getElementById('borrow-time-quantity') as HTMLInputElement;
        const borrowTimeTypeInput = document.getElementById('borrow-time-type') as HTMLInputElement;
        const borrowTimeError = document.getElementById('borrow-time-error') as HTMLInputElement;
        
        if (!borrowTimeQuantityInput.value || parseInt(borrowTimeQuantityInput.value) <= 0) {
            borrowTimeError.classList.remove('d-none');
            return false;
        }

        if (!borrowTimeTypeInput.value || parseInt(borrowTimeTypeInput.value) <= 0) {
            borrowTimeError.classList.remove('d-none');
            return false;
        }

        borrowTimeError.classList.add('d-none');
        return true;
    }


    const validations = [
        validateStep1,
        validateStep2,
        validateStep3,
        () => { return true },
        () => { return true },
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
            <h1 className="text-center mb-5">{t('addAsset.title')}</h1>
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
                            <h2>{t('addAsset.isbn.ISBN')}</h2>
                            <p>{t('addAsset.isbn.explanation')}</p>
                            <input type="text" placeholder="ISBN" className='form-control' id='isbn' />
                            <small id='isbn-error' className="text-danger small d-none">Please input a valid ISBN</small>
                            <div className="button-container">
                                <input type='button' className='prev-button btn btn-outline-success mx-1' value='Previous' disabled />
                                <input type='button' className='next-button btn btn-outline-success mx-1' value='Next' id='isbn-next-btn' onClick={nextStep} />
                            </div>
                        </fieldset>

                        <fieldset className="d-none info-container">
                            <h2>{t('addAsset.bookInfo.detail')}</h2>
                            <div className='field-group'>
                                <div className='field'>
                                    <label htmlFor='title' className='form-label'>{t('addAsset.bookInfo.title')}</label>
                                    <input type='text' className='form-control' id='title' placeholder='Title' />
                                    <small id='title-error' className="text-danger small d-none">{t('addAsset.bookInfo.title-validation-error')}</small>
                                </div>
                                <div className='field'>
                                    <label htmlFor='physicalCondition' className='form-label'>{t('addAsset.bookInfo.physicalCondition')}</label>
                                    <select id='physicalCondition' className='form-control round'>
                                        {states.map((state) => {
                                            return <option key={state[0]} value={state[0]}>{state[1]}</option>
                                        })}
                                    </select>
                                    <small id='physicalCondition-error' className="text-danger small d-none">{t('addAsset.bookInfo.physicalCondition-validation-error')}</small>
                                </div>
                            </div>
                            <div className='field-group'>
                                <div className='field'>
                                    <label htmlFor='author' className='form-label'>{t('addAsset.bookInfo.author')}</label>
                                    <input type='text' className='form-control' id='author' placeholder='Author' />
                                    <small id='author-error' className="text-danger small d-none">{t('addAsset.bookInfo.author-validation-error')}</small>
                                </div>
                                <div className='field'>
                                    <label htmlFor='language' className='form-label'>{t('addAsset.bookInfo.language')}</label>
                                    <select className='form-control round' id='languageSelect' defaultValue={'invalid'} disabled>
                                        <option key='invalid' value='' disabled>{t('addAsset.bookInfo.selectLanguage')}</option>
                                        {   
                                            languages.map((language) => {
                                                return <option key={language.code} value={language.code}>{language.name}</option>
                                            })
                                        }
                                    </select>
                                    <input className='d-none' id='language' readOnly={true} />
                                    <small id='language-error' className="text-danger small d-none">{t('addAsset.bookInfo.language-validation-error')}</small>
                                </div>
                            </div>
                            <div className="button-container">
                                <input type='button' className='prev-button btn btn-outline-success mx-1' onClick={prevStep} value='Previous' />
                                <input type='button' className='next-button btn btn-outline-success mx-1' onClick={nextStep} value='Next' />
                            </div>
                        </fieldset>

                        <fieldset className="d-none info-container">
                            <h2>{t('addAsset.duration.detail')}</h2>
                            <div className='field-group'>
                                <label>{t('addAsset.duration.lendFor')}</label>
                                <input type='number' className='form-control' id='borrow-time-quantity' name='borrow-time-quantity' min='1' placeholder='Lend for' />
                                <select id='borrow-time-type' className='form-select' >
                                    {timeTypes.map((timeType) => {
                                        return <option key={timeType[0]} value={timeType[0]}>{timeType[1]}</option>
                                    })}
                                </select>
                            </div>
                            <small id='borrow-time-error' className="text-danger small d-none">{t('addAsset.duration.validation-error')}</small>
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
