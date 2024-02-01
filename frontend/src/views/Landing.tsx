import BookCard from "../components/BookCard.tsx";
import './styles/landing.css';
import {Link} from "react-router-dom";
import React, {useEffect, useState} from 'react'; // Add this line
import {Helmet} from "react-helmet";
import {useTranslation} from "react-i18next";
import BookCardPlaceholder from "../components/BookCardPlaceholder.tsx";
import useAssetInstance from "../hooks/assetInstance/useAssetInstance.ts";
import {SORT_DIRECTIONS, SORT_TYPES} from "./Discovery.tsx";
import LandinSvg from "../../public/static/landing.svg";


export default function Landing(){

    const {t} = useTranslation();
    const {handleAllAssetInstances} = useAssetInstance()

    const [loading, setLoading] = useState(true)
    const [data, setData] = useState([])


    const fetchData = async () => {
        setLoading(true)
        setData([])
        const res = await handleAllAssetInstances(1, 4, SORT_TYPES.RECENT, SORT_DIRECTIONS.DES, "",[], [], 1)
        if(res !== null && res !== undefined){
            const {books} = res
            setData(books)
        }
        setLoading(false)
    }

    useEffect(() => {
        fetchData().then()
    }, []);

    return (
        <>
            <Helmet>
                <meta charSet="utf-8"/>
                <link rel="canonical" href="http://mysite.com/example"/>
            </Helmet>
            <section id="hero" style={{backgroundColor: '#D0DCD0', paddingBottom: '100px',}}>
                <div className="container" style={{textAlign: 'start'}}>
                    <div className="text-container">
                        <h1>{t('landing.hero.title')}</h1>
                        <h2>{t('landing.hero.subtitle')}</h2>

                        <div className="d-flex justify-content-center mt-4">
                            <Link to="/discovery" className="btn-get-started scrollto" style={{textDecoration: 'none'}}>{t('landing.hero.btn')}</Link>
                        </div>
                    </div>
                    {/*<div className="image-container">*/}
                    <div>
                        <img src={LandinSvg} className="img-fluid animated" alt=""/>
                    </div>
                </div>
            </section>

            
            <section style={{backgroundColor: '#FFFFFF', marginTop: '100px'}}>
                <div className="container-row-wrapped">
                    <h1>{t('landing.recent.title')}</h1>
                </div>
                <div className="container-row-wrapped" style={{margin: '20px auto', paddingTop: '20px', backgroundColor: '#FFFFFF', borderRadius: '20px', width: '90%'}}>
                    {
                        data.length === 0 ? (
                            <>
                                <BookCardPlaceholder/><BookCardPlaceholder/><BookCardPlaceholder/><BookCardPlaceholder/>
                            </>
                        ) : (
                            <>
                                {
                                    data.map((book, index) => (<BookCard key={index} book={book}/>))
                                }
                            </>
                        )
                    }
                </div>
            </section>
            

            <section className="content-section text-white text-center pb-5" id="services" style={{backgroundColor: '#D0DCD0'}}>
                <div className="container px-4 px-lg-5 pt-3">
                    <div className="content-section-heading">
                        <h2 className="my-5" style={{color: '#3e4450'}}>{t('landing.instructions.title')}</h2>
                    </div>
                    <div className="row gx-4 gx-lg-5" style={{display: 'flex', justifyContent: 'space-between'}}>
                        <div className="service-item" style={{flex: '0 0 25%', maxWidth: '25%'}}>
                            <span className="service-icon rounded-circle mx-auto mb-3"><i className="bi bi-search"></i></span>
                            <h4 style={{color: '#2B3B2B'}}><strong>{t('landing.instructions.search.title')}</strong></h4>
                            <p className="mb-0" style={{color: '#3e4450'}}>{t('landing.instructions.search.subtitle')}</p>
                        </div>
                        <div className="service-item" style={{flex: '0 0 25%', maxWidth: '25%'}}>
                            <span className="service-icon rounded-circle mx-auto mb-3"><i className="bi bi-send"></i></span>
                            <h4 style={{color: '#2B3B2B'}}><strong>{t('landing.instructions.request.title')}</strong></h4>
                            <p className="mb-0" style={{color: '#3e4450'}}>{t('landing.instructions.request.subtitle')}</p>
                        </div>
                        <div className="service-item" style={{flex: '0 0 25%', maxWidth: '25%'}}>
                            <span className="service-icon rounded-circle mx-auto mb-3"><i className="bi bi-person-circle"></i></span>
                            <h4 style={{color: '#2B3B2B'}}><strong>{t('landing.instructions.contact.title')}</strong></h4>
                            <p className="mb-0" style={{color: '#3e4450'}}>
                                {t('landing.instructions.contact.subtitle')}
                            </p>
                        </div>
                        <div className="service-item" style={{flex: '0 0 25%', maxWidth: '25%'}}>
                            <span className="service-icon rounded-circle mx-auto mb-3"><i className="bi bi-arrow-left-right"></i></span>
                            <h4 style={{color: '#2B3B2B'}}><strong>{t('landing.instructions.meet.title')}</strong></h4>
                            <p className="mb-0" style={{color: '#3e4450'}}>{t('landing.instructions.meet.subtitle')}</p>
                        </div>
                    </div>

                </div>
            </section>
        </>

    );
}
