
import './styles/bookCard.css';
import ProfilePlaceholder from "../../public/static/profile_placeholder.jpeg"


const BookCardPlaceholder = () => {
    return (
        <>
            <div className="card text-white card-has-bg click-col cardBook"
                 style=
                     {{
                         backgroundColor: '#000000',
                         backgroundSize: 'cover',
                         backgroundPosition: 'center',
                         backgroundRepeat: 'no-repeat',
                         height: '400px',
                         margin: '15px',
                         width: '18rem',
                         objectFit: 'cover',
                     }}          >

                <img className="card-img d-none" src="" alt="Book title" />

                <div className="card-img-overlay d-flex flex-column">
                    <div className="card-body">
                        <small className="card-meta mb-2 text-truncate placeholder-glow">
                            <span className="placeholder col-7"></span>
                        </small>
                        <h3 className="card-title mt-0 text-white truncate-3-lines placeholder-glow">
                            <span className="placeholder col-10"></span>
                        </h3>
                        <small className="text-white placeholder-glow"><i className="bi bi-book-half text-white "></i>
                            <span className="placeholder col-3 mx-2"></span>
                        </small>
                    </div>
                    <div className="card-footer">
                        <div className="media">
                            <img className="mr-3 rounded-circle" src={ProfilePlaceholder} style={{width:'50px', height: '50px'}} alt="placeholder"/>
                            <div className="media-body">
                                <h6 className="my-0 text-white d-block text-truncate placeholder-glow">
                                    <span className="placeholder col-7"></span>
                                </h6>
                                <small className="text-white truncate-3-lines placeholder-glow">
                                    <span className="placeholder col-2"></span> <span
                                    className="placeholder col-3"></span> <span className="placeholder col-3"></span>
                                </small>
                            </div>
                        </div>
                    </div>
                </div>



            </div>
        </>
    );


};

export default BookCardPlaceholder;
