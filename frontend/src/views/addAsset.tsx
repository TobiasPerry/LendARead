import './styles/addAsset.css';

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

    const showImage = () => {
        const image = document.querySelector('.image-container img') as HTMLImageElement;
        const input = document.querySelector('#uploadImage') as HTMLInputElement;
        const reader = new FileReader();
        reader.onload = function(e) {
            image.src = e.target.result as string;
        }
        reader.readAsDataURL(input.files[0]);
    }

    // TODO Check classes
    return (
        <div className="addasset-container">
            <h1 className="text-center mb-5">Do you want to borrow a book?</h1>
            <div className="p-4 container flex-container">
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
                        <fieldset>
                            <h2>ISBN</h2>
                            <text>We need the ISBN to get info about the book. If there's missing parts, you'll be asked to complete it.</text>
                            <input type="text" placeholder="ISBN" className='form-control'/>
                            <small className="text-danger small">Please input a valid ISBN</small>
                            <div className="button-container">
                                <input type='button' className='prev-button btn btn-outline-success mx-1' value='Previous' disabled/>
                                <input type='button' className='next-button btn btn-outline-success mx-1' value='Next'/>
                            </div>
                        </fieldset>

                        <fieldset>
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
                                            return <option value={state[0]}>{state[1]}</option>
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
                        </fieldset>
                            <h2>Max Borrowing Period</h2>
                            <div className='field-group'>
                                <label>Lend for</label>
                                <input type='number' className='form-control' id='borrow-time-quantity' name='borrow-time-quantity' min='1' placeholder='Lend for' />
                                <select id='borrow-time-type' className='form-select' >
                                    {timeTypes.map((timeType) => {
                                        return <option value={timeType[0]}>{timeType[1]}</option>
                                    })}
                                </select>
                            </div>
                        <fieldset>

                        </fieldset>
                        <input type="file" className='d-none' accept="image/*" name="file" id="uploadImage" onChange={showImage}/>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default AddAsset;
