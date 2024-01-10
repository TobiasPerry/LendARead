const AddAsset = () => {

    // TODO Check classes
    return (
        <div className="container">
            <h1 className="text-center mb-5">Do you want to borrow a book?</h1>
            <div className="p-4 container flex-container">
                <div className="image-wrapper">
                    <img src="/static/no_image_placeholder.jpg" alt="Book Cover" className="img-fluid object-fit-cover" /> 
                </div>
            </div>
        </div>
    )
}

export default AddAsset;
