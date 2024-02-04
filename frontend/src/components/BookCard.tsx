
import './styles/bookCard.css';
import {Link, useNavigate} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {useContext} from "react";
import {AuthContext} from "../contexts/authContext.tsx";
import ProfilePlaceholder from "../../public/static/profile_placeholder.jpeg"

const BookCard = ({ book }) => {
    const {t} = useTranslation()
    const navigate = useNavigate();

    const {
        title,
        author,
        image,
        physicalCondition,
        userImage,
        userName,
        userNum,
        country,
        province,
        locality,
        assetInstanceNumber,
    } = book;

    const url_book_image = "url('" + image + '?size=PORTADA' + "')"

    const {user} = useContext(AuthContext)

    const handleBookClick = () => {
        if(user !== null && user !== undefined && user.toString() === userNum.toString()) {
            navigate(`/userBook/${assetInstanceNumber}?state=owned`)
        }else {
            navigate(`/book/${assetInstanceNumber}`)
        }
    }

    const handleUserImage = () => {
        return userImage !== null && userImage !== undefined && userImage !== "" ? (userImage + '?size=PORTADA') : ProfilePlaceholder;
    }

    return (
        <>
            <div className="card text-white card-has-bg click-col cardBook"
                 style=
                     {{
                         backgroundImage: url_book_image,
                         backgroundSize: 'cover',
                         backgroundPosition: 'center',
                         backgroundRepeat: 'no-repeat',
                         height: '400px',
                         margin: '15px',
                         width: '18rem',
                         objectFit: 'cover',
                     }}          >


                <div onClick={handleBookClick} id="infoRef" style={{textDecoration: 'none'}} >

                    <img className="card-img d-none" src="" alt="Book title" />

                    <div className="card-img-overlay d-flex flex-column">
                        <div className="card-body">
                            <small className="card-meta mb-2 text-truncate">{author}</small>
                            <h3 className="card-title mt-0 text-white truncate-3-lines">{title}</h3>
                            <small className="text-white"><i className="bi bi-book-half text-white"></i> {t(physicalCondition)} </small>
                        </div>
                        <div className="card-footer">
                            <div className="media">
                                <img className="mr-3 rounded-circle" src={handleUserImage()} style={{width:'50px', height: '50px'}} alt="User Image"/>
                                <div className="media-body">
                                    <h6 className="my-0 text-white d-block text-truncate">{userName}</h6>
                                    <small className="text-white truncate-3-lines"> {locality}, {province}, {country} </small>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
        </>
    );


};

export default BookCard;
