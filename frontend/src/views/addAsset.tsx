import './styles/addAsset.css';
import { useState, useEffect, useContext, useRef} from 'react';
import { useNavigate } from 'react-router-dom';
import {useTranslation} from "react-i18next";
import axios from 'axios';
import useLocations from '../hooks/locations/useLocations.ts'
import useChangeRole from '../hooks/users/useChangeRole.ts'
import useLanguages from '../hooks/languages/useLanguages.ts'
import useAsset from '../hooks/asset/useAsset.ts'
import useAssetInstance from '../hooks/assetInstance/useAssetInstance.ts'
import {AuthContext} from "../contexts/authContext.tsx";
import NewLenderModal from '../components/modals/NewLenderModal.tsx'
import noImagePlacegolder from '../../public/static/no_image_placeholder.jpg'
import LocationModal from "../components/modals/LocationModal.tsx";

const ISBN_API_BASE_URL = 'https://openlibrary.org';

type LanguageDTO = {
    code: string,
    name: string,
}

type LanguagesDTO = LanguageDTO[]


const AddAsset = () => {
    
    const {t} = useTranslation();
    const navigate = useNavigate();
    const { makeLender } = useChangeRole();
    const { getAllLanguages } = useLanguages();
    const { uploadAssetInstanceImage, uploadAssetInstance } = useAssetInstance();
    const { uploadAsset, getAssetByIsbn } = useAsset();

    const states = [
        ["ASNEW", t('ASNEW')],
        ["FINE", t('FINE')],
        ["VERYGOOD", t('VERYGOOD')],
        ["GOOD", t('GOOD')],
        ["FAIR", t('FAIR')],
        ["POOR", t('POOR')],
        ["EXLIBRARY", t('EXLIBRARY')],
        ["BOOKCLUB", t('BOOKCLUB')],
        ["BINDINGCOPY", t('BINDINGCOPY')]
    ]

    const timeTypes = [
        [1, t('addAsset.duration.days')],
        [7, t('addAsset.duration.weeks')],
        [31, t('addAsset.duration.months')]
    ]

    const {user, userDetails}  = useContext(AuthContext);
    const [step, setStep] = useState(1);
    const [languages, setLanguages] = useState<LanguagesDTO>([])
    const [showLocModal, setShowLocModal] = useState(false);
    const {addLocation, getAllLocations} = useLocations()
    const emptyLocation = {name: "", province: "", country: "", locality: "", zipcode: 0, id: -1}
    const [locations, setLocations] = useState([])
    const [selectedLocation, setSelectedLocation] = useState<any>({})
    const alreadyHaveAsset = useRef(false)
    const assetId = useRef(-1)

    const [newLocationModal, setNewLocationModal] = useState(false)

    // Data from the form
    const [isbn, setIsbn] = useState('');
    const [title, setTitle] = useState('');
    const [author, setAuthor] = useState('');
    const [language, setLanguage] = useState('');
    const [physicalCondition, setPhysicalCondition] = useState(states[0][0]);
    const [description, setDescription] = useState('');
    const [borrowTimeQuantity, setBorrowTimeQuantity] = useState(0);
    const [borrowTimeType, setBorrowTimeType] = useState(timeTypes[0][0]);
    const [acceptsReservations, setAcceptsReservations] = useState(false);
    const [image, setImage] = useState<any>({});
    const [locationId, setLocationId] = useState(-1);

    const handleLocationSave = async (newLocation: any) => {
        setShowLocModal(false);
        // First, make the user lender
        const roleChanged = await makeLender(user)

        if (!roleChanged) {
            console.error("Error en cambiar el rol")
            setShowLocModal(true)
            return 
        }

        const resAddLocation = await addLocation(newLocation)
        if(!resAddLocation){
            console.error("Error en cargar la ubicacion")
        }else{ 
            getAllLocations(user).then((response) => {
                setLocations(response)
            })
        }


    }

    useEffect(() => {
        if (locations.length > 0) {
            changeSelectedLocation(locations[0].selfUrl.split('/').pop())
        }
    }, [locations])

    useEffect(() => {
        getAllLanguages().then((response) => {
            setLanguages(response)
        })
    }, [])

    useEffect(() => {
        getAllLocations(user).then((response) => {
            setLocations(response)
        })
    }, [])

    useEffect(() => {
        if (user == "") {
            setShowLocModal(false)
        }
        if(userDetails.role == "BORROWER"){
            setShowLocModal(true)
        }
    }, [user, userDetails]);

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

    const changeSelectedLocation = (locId: string) => {
        const filtered = locations.filter((location) => { return location.selfUrl.split('/').pop() == locId })
        const location = filtered[0]

        if (location) {
            setSelectedLocation(location)
            setLocationId(parseInt(locId))
        } else {
            setSelectedLocation(emptyLocation)
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



    const showImage = (e: any) => {
        const image = document.querySelector('.image-container img') as HTMLImageElement;
        const input = document.querySelector('#uploadImage') as HTMLInputElement;
        const reader = new FileReader();
        reader.onload = function(e) {
            image.src = e.target.result as string;
        }
        setImage(e.target.files[0])
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
        isbnInput.classList.add('loading');

        const nextBtn = document.getElementById('isbn-next-btn') as HTMLInputElement;
        nextBtn.disabled = true;


        let book = null;
        // TODO: First try with our API
        // This returns a string
        const asset = await getAssetByIsbn(isbn);
        if (asset) {
            book = asset
            alreadyHaveAsset.current = true
            assetId.current = book.selfUrl.split('/').pop()
        } else {
            // If it is not in our API, try with OpenLibrary API
            book = await getBookOpenLib(isbn);
        }
        const languageSelect = document.getElementById('languageSelect') as HTMLSelectElement;

        if (book) {
            const titleInput = document.getElementById('title') as HTMLInputElement;
            if (book.title) {
                titleInput.value = book.title;
                titleInput.readOnly = true;
                setTitle(book.title)
            } else {
                titleInput.value = '';
                titleInput.readOnly = false;
            }
            
            const authorInput = document.getElementById('author') as HTMLInputElement;
            if (book.author) {
                authorInput.value = book.author;
                setAuthor(book.author)
                authorInput.readOnly = true;
            } else {
                authorInput.value = '';
                authorInput.readOnly = false;
            }

            // const languageInput = document.getElementById('language') as HTMLInputElement;
            if (book.lang) {
                let selectedLang = languages.filter((language) => { return language.code == book.lang })[0]
                if (selectedLang) {
                    languageSelect.value = selectedLang.code;
                    languageSelect.disabled = true;
                    setLanguage(selectedLang.code)
                } else {
                    languageSelect.disabled = false;
                }
            } else {
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
            languageSelect.disabled = false;
        }

        nextBtn.disabled = false;
        isbnInput.classList.remove('loading');
        document.getElementById('isbn-error')?.classList.add('d-none');
        return true;
    }

    const validateStep2 = () => {
        let valid = true
        const titleInput = document.getElementById('title') as HTMLInputElement;
        const authorInput = document.getElementById('author') as HTMLInputElement;
        const languageInput = document.getElementById('language') as HTMLInputElement;
        const languageSelect = document.getElementById('languageSelect') as HTMLSelectElement;
        const physicalConditionInput = document.getElementById('physicalCondition') as HTMLInputElement;
        const descriptionInput = document.getElementById('description') as HTMLInputElement;

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

        if (!languageSelect.value || languageSelect.value == 'invalid' ) {
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

        if (!descriptionInput.value ) {
            valid = false;
            const error = document.getElementById('description-error') as HTMLInputElement;
            error.classList.remove('d-none');
        } else {
            const error = document.getElementById('description-error') as HTMLInputElement;
            error.classList.add('d-none');
        }

        if (valid) {
            const titleError = document.getElementById('title-error') as HTMLInputElement;
            const authorError = document.getElementById('author-error') as HTMLInputElement;
            const languageError = document.getElementById('language-error') as HTMLInputElement;
            const physicalConditionError = document.getElementById('physicalCondition-error') as HTMLInputElement;
            const descriptionError = document.getElementById('description-error') as HTMLInputElement;
            languageInput.value = languageSelect.value;
            titleError.classList.add('d-none');
            authorError.classList.add('d-none');
            languageError.classList.add('d-none');
            physicalConditionError.classList.add('d-none');
            descriptionError.classList.add('d-none');
        }
        return valid;
    }

    const validateStep3 = () => {
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

    const validateStep4 = () => {
        const locationSelect = document.getElementById('location-select') as HTMLSelectElement;
        const locationError = document.getElementById('location-error') as HTMLInputElement;
        if (!locationSelect.value || parseInt(locationSelect.value) <= 0) {
            locationError.classList.remove('d-none');
            return false;
        }
        locationError.classList.add('d-none');

        const imageInput = document.getElementById('uploadImage') as HTMLInputElement;
        const imageLabel = document.querySelector('.image-container') as HTMLInputElement;
        const imageError = document.getElementById('image-error') as HTMLInputElement;
        if (!imageInput.value) {
            imageError.classList.remove('d-none');
            imageLabel.classList.add('img-error');
            return false;
        } else {
            imageLabel.classList.remove('img-error');
            imageError.classList.add('d-none');
        }
        return handleSubmit().then((_) => {
           return false;
        })
    }

    const handleSubmit = async () => {
        // Print the type of borrowTimeType
        const maxDays = (borrowTimeQuantity * (borrowTimeType as number)) // The LSP is saying that it is not a number, but printing the type gives "number". Idk

        // First, post the image
        const imageId = await uploadAssetInstanceImage(image)
        if (imageId === -1) {
            return false;
        }

        // Then, if the asset is new, post it
        if (!alreadyHaveAsset.current) {
            const uploadedAssetId = await uploadAsset(isbn, title, author, language, description)
            if (uploadedAssetId === -1) {
                return false;
            }
            assetId.current = uploadedAssetId
        } 
        
        // Lastly, the assetInstnace
        const assetInstnace = {
            physicalCondition: physicalCondition,
            maxDays: maxDays,
            isReservable: acceptsReservations,
            imageId: imageId.toString(),
            locationId: locationId.toString(),
            description: description,
            status: "PUBLIC",
            assetId: assetId.current.toString()
        }

        const assetInstanceId = await uploadAssetInstance(assetInstnace)
        if (assetInstanceId === -1) {
            return false;     
        }
        navigate(`/userBook/${assetInstanceId}?state=owned`)
    }


    const validations = [
        validateStep1,
        validateStep2,
        validateStep3,
        validateStep4,
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
        if (nextStep) {
          nextStep.classList.remove('d-none');
          setStep(step + 1);
        }
    }

    const prevStep = () => {
        const steps = getFormSteps();
        const currentStep = steps[step - 1] as HTMLFieldSetElement;
        const prevStep = steps[step - 2] as HTMLFieldSetElement;
        currentStep.classList.add('d-none');
        prevStep.classList.remove('d-none');
        setStep(step - 1);
    }

    const handleSaveNewLocation = async (formData: any) => {
        const res = await addLocation(formData)
        if (res) {
            setNewLocationModal(false)
            getAllLocations(user).then((response) => {
                setLocations(response)
            })
        }
    }


    // TODO Check classes
    return (
        <>
        <NewLenderModal
            location={emptyLocation}
            showModal={showLocModal}
            handleClose={() =>{navigate(-1)}}
            handleSave={handleLocationSave}
        />
        <LocationModal
            handleSave={handleSaveNewLocation}
            location={()=>{}} showModal={newLocationModal}
            handleClose={()=>{setNewLocationModal(false)}}
        />
        <div className="addasset-container flex-column">
            <h1 className="text-center mb-5">{t('addAsset.title')}</h1>
            <div className="p-4 addasset-container flex-container">
                <div className='flex-container'>
                    <div className="image-wrapper">
                        <label htmlFor='uploadImage' className='image-container position-relative'>
                            <img src={noImagePlacegolder} alt="Book Cover" className="img-fluid object-fit-cover" />
                            <div className='img-hover-text'>
                                <i className="bi bi-cloud-upload"></i>
                            </div>
                        </label>
                        <small id='image-error' className="text-danger small d-none">{t('addAsset.image.validation-error')}</small>
                    </div>

                    <div className="form-container"> 
                        <div className='stepper'>
                        </div>
                        <fieldset className="info-container">
                            <h2>{t('addAsset.isbn.ISBN')}</h2>
                            <p>{t('addAsset.isbn.explanation')}</p>
                            <input type="text" placeholder="ISBN" className='form-control' id='isbn' onChange={(e) => setIsbn(e.target.value)}/>
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
                                    <input type='text' className='form-control' id='title' placeholder='Title' onChange={(e) => setTitle(e.target.value)}/>
                                    <small id='title-error' className="text-danger small d-none">{t('addAsset.bookInfo.title-validation-error')}</small>
                                </div>
                                <div className='field'>
                                    <label htmlFor='physicalCondition' className='form-label'>{t('addAsset.bookInfo.physicalCondition')}</label>
                                    <select id='physicalCondition' className='form-control round' onChange={(e) => setPhysicalCondition(e.target.value)}>
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
                                    <input type='text' className='form-control' id='author' placeholder='Author' onChange={(e) => setAuthor(e.target.value)}/>
                                    <small id='author-error' className="text-danger small d-none">{t('addAsset.bookInfo.author-validation-error')}</small>
                                </div>
                                <div className='field'>
                                    <label htmlFor='language' className='form-label'>{t('addAsset.bookInfo.language')}</label>
                                    <select className='form-control round' id='languageSelect' defaultValue={'invalid'} onChange={(e) => setLanguage(e.target.value)} disabled>
                                        <option key='invalid' value='invalid' disabled>{t('addAsset.bookInfo.selectLanguage')}</option>
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
                            <div className="field-group mt-2 flex-column">
                                <textarea className="form-control" id="description" placeholder={t('addAsset.bookInfo.description')} onChange={(e) => setDescription(e.target.value)}/>
                                <small id='description-error' className="text-danger small d-none">{t('addAsset.bookInfo.description-validation-error')}</small>
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
                                <input type='number' className='form-control' id='borrow-time-quantity' name='borrow-time-quantity' min='1' placeholder={t('addAsset.duration.lendFor')} onChange={(e) => setBorrowTimeQuantity(parseInt(e.target.value))}/>
                                <select id='borrow-time-type' className='form-select' onChange={(e) => setBorrowTimeType(parseInt(e.target.value))}>
                                    {timeTypes.map((timeType) => {
                                        return <option key={timeType[0]} value={timeType[0]}>{timeType[1]}</option>
                                    })}
                                </select>
                            </div>
                            <small id='borrow-time-error' className="text-danger small d-none">{t('addAsset.duration.validation-error')}</small>
                            <div className="custom-control custom-switch mt-3 d-flex justify-content-center" >
                                <input type="checkbox" className="custom-control-input big-switch mx-2" checked={acceptsReservations} id="reservationSwitch" data-toggle="tooltip" title="" onChange={(e) => setAcceptsReservations(e.target.checked)}/>
                                {t('addAsset.duration.acceptsReservations')}
                            </div>
                            <div className="button-container">
                                <input type='button' className='prev-button btn btn-outline-success mx-1' onClick={prevStep} value='Previous' />
                                <input type='button' className='next-button btn btn-outline-success mx-1' onClick={nextStep} value='Next' />
                            </div>
                        </fieldset>
                        <fieldset className='info-container d-none'>
                            <h2>{t('addAsset.location.detail')}</h2>
                            <div className='location-selector'>
                                <select id='location-select' className='form-select' onChange={(e) => {
                                    changeSelectedLocation(e.target.value)
                                }}>
                                    {locations.map((location) => {
                                        const locId = location.selfUrl.split('/').pop()
                                        return <option key={locId} value={locId}>{location.name}</option>
                                    })}
                                </select>
                                <p className="text-clickable mt-3" onClick={() => {setNewLocationModal(true)}}>
                                    {t('addAsset.location.new')}
                                </p>
                                <small id='location-error' className="text-danger small d-none">{t('addAsset.location.validation-error')}</small>
                                <div className='flex-grow-1 location-display'>
                                    <div className="field-set">
                                        <div className='field'>
                                            <label className='form-label'>{t('addAsset.location.locality')}</label>
                                            <p id='locality'>{selectedLocation.locality}</p>
                                        </div>
                                        <div className='field'>
                                            <label className='form-label'>{t('addAsset.location.province')}</label>
                                            <p id='locality'>{selectedLocation.province}</p>
                                        </div>
                                    </div>
                                    <div className="field-set">
                                        <div className='field'>
                                            <label className='form-label'>{t('addAsset.location.country')}</label>
                                            <p id='locality'>{selectedLocation.country}</p>
                                        </div>
                                        <div className='field'>
                                            <label className='form-label'>{t('addAsset.location.zipcode')}</label>
                                            <p id='locality'>{selectedLocation.zipcode}</p>
                                        </div>
                                    </div>

                                </div>
                            </div>
                            <div className="button-container">
                                <input type='button' className='prev-button btn btn-outline-success mx-1' onClick={prevStep} value='Previous' />
                                <input type='submit' className='next-button btn btn-outline-success mx-1' onClick={nextStep} value='Send' />
                            </div>
                        </fieldset>
                        <input type="file" className='d-none' accept="image/*" name="file" id="uploadImage" onChange={(e) => showImage(e)} />
                    </div>
                </div>
            </div>
        </div>
        </>
    )
}

export default AddAsset;
