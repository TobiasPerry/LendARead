
import './styles/bookCard.css';
import {Link} from "react-router-dom";

const BookCard = ({ book }) => {
    const {
        title,
        author,
        language,
        image,
        physicalCondition,
        userImage,
        userName,
        country,
        province,
        locality
    } = book;

    const url_book_image = "url('" + image + "')"
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


    <Link to="/" id="infoRef" style={{textDecoration: 'none'}} >



        <img className="card-img d-none" src="http://pawserver.it.itba.edu.ar/paw-2023a-03/getImage/61" alt="Book title" />

        <div className="card-img-overlay d-flex flex-column">
            <div className="card-body">
                <small className="card-meta mb-2 text-truncate">{author}</small>
                <h3 className="card-title mt-0 text-white truncate-3-lines">{title}</h3>
                <small className="text-white"><i className="bi bi-book-half text-white"></i> {physicalCondition} (i18n!!) </small>
            </div>
            <div className="card-footer">
                <div className="media">
                    <img className="mr-3 rounded-circle" src={userImage} style={{width:'50px', height: '50px'}}/>
                    <div className="media-body">
                        <h6 className="my-0 text-white d-block text-truncate">{userName}</h6>
                        <small className="text-white truncate-3-lines"> {locality}, {province}, {country} </small>
                    </div>
                </div>
            </div>
        </div>
    </Link>


</div>
    </>
  );

  
};

export default BookCard;
