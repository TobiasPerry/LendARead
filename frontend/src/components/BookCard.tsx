
import './styles/bookCard.css';

const BookCard = () => {
  return (
    <>
      <div className="card text-white card-has-bg click-col cardBook" 
        style=
        {{  
          backgroundImage: "url('http://pawserver.it.itba.edu.ar/paw-2023a-03/getImage/71')",
          backgroundSize: 'cover',
          backgroundPosition: 'center',
          backgroundRepeat: 'no-repeat',
          height: '400px',
          margin: '15px',
          width: '18rem',
          objectFit: 'cover',
        }}          >

    <a href="/" id="infoRef" style={{textDecoration: 'none'}} >

        <img className="card-img d-none" src="http://pawserver.it.itba.edu.ar/paw-2023a-03/getImage/61" alt="Book title" />

        <div className="card-img-overlay d-flex flex-column">
            <div className="card-body">
                <small className="card-meta mb-2 text-truncate">Phill Knight</small>
                <h3 className="card-title mt-0 text-white truncate-3-lines">Shoe Dog</h3>
                <small className="text-white"><i className="bi bi-book-half text-white"></i> Physical condition </small>
            </div>
            <div className="card-footer">
                <div className="media">
                    <img className="mr-3 rounded-circle" src="http://pawserver.it.itba.edu.ar/paw-2023a-03/getImage/61  " style={{width:'50px', height: '50px'}}/>
                    <div className="media-body">
                        <h6 className="my-0 text-white d-block text-truncate">Usuario</h6>
                        <small className="text-white truncate-3-lines"> Localidad, Provincia, Pais </small>
                    </div>
                </div>
            </div>
        </div>

    </a>

</div>
    </>
  );

  
};

export default BookCard;
