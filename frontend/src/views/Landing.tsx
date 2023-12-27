import BookCard from "../components/BookCard.tsx";
import './styles/landing.css';
import {useEffect, useState} from "react";


export default function Landing(){
    const [data, setData] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch('http://127.0.0.1:8080/web/api/assetInstances');
                if (!response.ok) {
                    setData(null)
                    // throw new Error('Network response was not ok');
                }
                const data = await response.json();
                setData(data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, []);


    return (
        <>
            <section id="hero" style={{backgroundColor: '#D0DCD0', paddingBottom: '100px',}}>
                <div className="container" style={{textAlign: 'start'}}>
                    <div className="text-container">
                        <h1>{data[0].id}</h1>
                        <h2>subtitle</h2>

                        <div className="d-flex justify-content-center mt-4">
                            <a href="/discovery" className="btn-get-started scrollto" style={{textDecoration: 'none'}}>text</a>
                        </div>
                    </div>
                    <div className="image-container">
                        <img src="http://pawserver.it.itba.edu.ar/paw-2023a-03/static/images/indexPhoto.svg" className="img-fluid animated" alt=""/>
                    </div>
                </div>
            </section>

            
            <section style={{backgroundColor: '#FFFFFF', marginTop: '100px'}}>
                <div className="container-row-wrapped">
                    <h1>text</h1>
                </div>
                <div className="container-row-wrapped" style={{margin: '20px auto', paddingTop: '20px', backgroundColor: '#FFFFFF', borderRadius: '20px', width: '90%'}}>
                    <BookCard></BookCard>                    
                </div>
            </section>
            

            <section className="content-section text-white text-center pb-5" id="services" style={{backgroundColor: '#D0DCD0'}}>
                <div className="container px-4 px-lg-5">
                    <div className="content-section-heading">
                        <h2 className="my-5" style={{color: '#3e4450'}}>Titulo</h2>
                    </div>
                    <div className="row gx-4 gx-lg-5" style={{display: 'flex', justifyContent: 'space-between'}}>
                        <div className="service-item" style={{flex: '0 0 25%', maxWidth: '25%'}}>
                            <span className="service-icon rounded-circle mx-auto mb-3"><i className="bi bi-search"></i></span>
                            <h4 style={{color: '#2B3B2B'}}><strong>tect</strong></h4>
                            <p className="mb-0" style={{color: '#3e4450'}}>text</p>
                        </div>
                        <div className="service-item" style={{flex: '0 0 25%', maxWidth: '25%'}}>
                            <span className="service-icon rounded-circle mx-auto mb-3"><i className="bi bi-send"></i></span>
                            <h4 style={{color: '#2B3B2B'}}><strong>text</strong></h4>
                            <p className="mb-0" style={{color: '#3e4450'}}>text</p>
                        </div>
                        <div className="service-item" style={{flex: '0 0 25%', maxWidth: '25%'}}>
                            <span className="service-icon rounded-circle mx-auto mb-3"><i className="bi bi-person-circle"></i></span>
                            <h4 style={{color: '#2B3B2B'}}><strong>text</strong></h4>
                            <p className="mb-0" style={{color: '#3e4450'}}>
                                text
                            </p>
                        </div>
                        <div className="service-item" style={{flex: '0 0 25%', maxWidth: '25%'}}>
                            <span className="service-icon rounded-circle mx-auto mb-3"><i className="bi bi-arrow-left-right"></i></span>
                            <h4 style={{color: '#2B3B2B'}}><strong>text</strong></h4>
                            <p className="mb-0" style={{color: '#3e4450'}}>text</p>
                        </div>
                    </div>

                </div>
            </section>
        </>

    );
}