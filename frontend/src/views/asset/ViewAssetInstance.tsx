import {Link, useNavigate, useParams} from "react-router-dom";
import React, {useContext, useEffect, useState} from "react";
import "../styles/assetView.css"
import useAssetInstance from "../../hooks/assetInstance/useAssetInstance.ts";
import {AssetData} from "../../hooks/assetInstance/useAssetInstance.ts";
import LoadingAnimation from "../../components/LoadingAnimation.tsx";
import NotFound from "../NotFound.tsx";
import {useTranslation} from "react-i18next";
import {AuthContext} from "../../contexts/authContext.tsx";
import CalendarReservable from "../../components/viewAsset/CalendarReservable.tsx";
import CalendarNotReservable from "../../components/viewAsset/CalendarNotReservable.tsx";
import Modal from "../../components/modals/Modal.tsx";
import StarsReviews from "../../components/viewAsset/StarsReviews.tsx";
import ShowReviewCard from "../../components/viewAsset/ShowReviewCard.tsx";
import GreenButton from "../../components/GreenButton.tsx";
import {Helmet} from "react-helmet";
import ProfilePlaceholder from "../../../public/static/profile_placeholder.jpeg"

const ViewAssetInstance = () => {

    // @ts-ignore
    const book : AssetData = {
        title: "",
        author: "",
        language: {
            code: "",
            name: ""
        },
        image: "",
        physicalCondition: "",
        userImage: "",
        userName: "",
        isbn: "",
        location: {
            zipcode: "",
            locality: "",
            province: "",
            country: "",
        },
        reviews: undefined,
        description: "",
        reservable: false,
        maxLendingDays: -1
    }

    const {user, isLoggedIn} = useContext(AuthContext)

    const {t} = useTranslation()

    const { bookNumber } = useParams<{ bookNumber: string}>()

    const {handleAssetInstance, handleSendLendingRequest, handleGetReservedDays} = useAssetInstance()

    // Status: is it loading, was not found, error, etc
    const [loading, setLoading] = useState(true)
    const [found, setFound] = useState(false)
    const [success, setSuccess] = useState(false)
    const [error, setError] = useState(false)
    const [noDatesSelected, setNoDatesSelected] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")

    // AssetInstance Information
    const [data, setData] = useState(book)
    const [hasReviewAsLender, setHasReviewAsLender] = useState(false) // To decide if show rating alongside the user
    const [hasUserImage, setHasUserImage] = useState(false)
    const [hasDescription, setHasDescription] = useState(false)
    const [hasReviews, setHasReviews] = useState(false)
    const [reservedDates, setReservedDates] = useState([])

    // Lending request info
    const [beginDate, setBeginDate] = useState(null)
    const [endDate, setEndDate] = useState(null)
    const [lendingId, setLendingId] = useState(-1)

    const navigate = useNavigate()

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            setData(book)
            const res: AssetData = await handleAssetInstance(bookNumber, 3)
            // if found
            if(!(res === null || res === undefined)){
                setFound(true)
                setHasReviews(res.rating_assetInstance != 0)
                setHasReviewAsLender(res.rating_as_lender != 0)
                setHasUserImage((!(res.userImage === null || res.userImage === undefined)))
                setHasDescription((!(res.description === null || res.description === undefined || res.description === "")))
                setData(res)
                // if log in and assetInstance reservable, get the reserved dates
                if(isLoggedIn && res.reservable) {
                    const res_reserved_dates = await handleGetReservedDays(bookNumber);
                    setReservedDates((res_reserved_dates === null || res_reserved_dates === undefined) ? [] : res_reserved_dates)
                }
                // Otherwise, just set initial date today and limit the end date
                else {
                    const today = new Date();
                    today.setHours(0,0,0)
                    setBeginDate(today)
                }
                setLoading(false)
            }else {
                setFound(false)
                setLoading(false)
            }
        };
        fetchData()

    }, []);

    // Request the lending
    const handleClickSendLending = async () =>  {
        if(beginDate === null || beginDate === undefined || endDate === undefined|| endDate === null){
            setNoDatesSelected(true)
        }
        else{
            const res = await handleSendLendingRequest(
                {borrowDate: beginDate, devolutionDate: endDate, assetInstanceId: bookNumber}
            );
            if (!res.error) {
                setLendingId(res.lendingId)
                setSuccess(true)
            } else {
                setError(true)
                setErrorMessage(res.errorMessage)
            }
        }
    }

    // These are the links to redirect to discovery with filters applied
    const authorURL = `/discovery?search=${data.author}`
    const physicalConditionURL = `/discovery?physicalCondition=${data.physicalCondition}`
    const languageURL = `/discovery?language=${data.language.code}`

    return (
        <>
            <Helmet>
                <meta charSet="utf-8"/>
                <title>{loading || !found ? "Lend a Read" : t('view_asset.title', {title: data.title, author: data.author})}</title>
            </Helmet>
            <Modal
                showModal={success}
                title={t('view_asset.success_modal.title')} subtitle={t('view_asset.success_modal.sub_title')} btnText={t('view_asset.success_modal.btn_text')}
                handleSubmitModal={() => {navigate(`/userBook/${lendingId}?state=borrowed`)}}
                handleCloseModal={() => {navigate(`/userBook/${lendingId}?state=borrowed`)}}
                icon="bi bi-check"
            />
            <Modal
                showModal={error} errorType={true}
                title={t('view_asset.error_modal.title')} subtitle={errorMessage} btnText={t('view_asset.error_modal.btn_text')}
                handleSubmitModal={() => {setError(false)}}
                handleCloseModal={() => {setError(false)}}
                icon="bi bi-x"
            />
            {
                 loading ? (
                     <LoadingAnimation/>
                 ) : (<>
                     {
                         !found ? (
                             <NotFound></NotFound>
                         ) : (
                             <>
                                 <div className="main-class"
                                      style={{display: 'flex', justifyContent: 'center', alignItems: 'center', flexDirection: 'column'}}>

                                     <div style={{
                                         backgroundColor: '#f0f5f0',
                                         margin: '50px',
                                         borderRadius: '20px',
                                         padding: '20px',
                                         width: '50%'
                                     }}>
                                         <div style={{display: 'flex', flexFlow: 'row', width: '100%', justifyContent: 'start'}}>
                                             <img src={data.image} alt="Book cover"
                                                  style={{
                                                      marginLeft: '0',
                                                      marginRight: '50px',
                                                      height: '500px',
                                                      width: '300px',
                                                      objectFit: 'cover',
                                                      borderRadius: '10px'
                                                  }}/>
                                             <div className="mx-2">

                                                 <h1 className="textOverflow" title="text">
                                                     {data.title}
                                                 </h1>

                                                 <h3 className="textOverflow" id="authorClick" data-author="data-author">
                                                     {t('view_asset.by')}:
                                                     <span className="text-clickable">
                                                         <Link to={authorURL} style={{ textDecoration: 'none', color: 'inherit' }}>
                                                            {data.author}
                                                         </Link>
                                                    </span>
                                                 </h3>
                                                 <h6 id="physicalConditionClick" className="text-clickable">
                                                     <Link to={physicalConditionURL} style={{ textDecoration: 'none', color: 'inherit' }}>
                                                         <i><u>
                                                             {t(data.physicalCondition)}
                                                         </u></i>
                                                     </Link>
                                                 </h6>

                                                 <h6 id="languageClick" data-language="data-language"
                                                     style={{color: '#7d7c7c'}}>
                                                     {t('view_asset.language')}:
                                                     <Link to={languageURL} style={{ textDecoration: 'none', color: 'inherit' }}>
                                                        <span className="text-clickable"> {data.language.name} </span>
                                                     </Link>
                                                 </h6>

                                                 <h6 style={{color: '#7d7c7c'}}>
                                                     {t('view_asset.isbn')}: {data.isbn} </h6>


                                                 <div className="container-row" style={{justifyContent: 'start', display: 'flex', alignItems: 'center'}}>
                                                     <Link to={`/user/${data.userId}`}>
                                                         {
                                                             hasUserImage ? (
                                                                 <img className="rounded-circle img-hover-click"
                                                                      style={{width: '25px', height: '25px'}}
                                                                      src={data.userImage +`?size=PORTADA`}
                                                                      alt="profile picture"/>
                                                             ) : (
                                                                 <img className="rounded-circle img-hover-click" style={{width: '25px'}}
                                                                    src={ProfilePlaceholder}
                                                                    alt="profile picture"/>
                                                             )
                                                         }
                                                         <span className="mx-2 text-clickable">{data.userName}</span>
                                                     </Link>
                                                     <p style={{display: 'flex', alignContent: 'center'}}>

                                                         {
                                                             hasReviewAsLender ? (
                                                                 <small>
                                                                     <span
                                                                         style={{margin: 0}}
                                                                         className={
                                                                             `badge ${data.rating_as_lender >= 3.5 ? (`bg-success`) : (data.rating_as_lender >= 2.5) ? `bg-warning` : `bg-danger`} `
                                                                         }
                                                                     >
                                                                       {data.rating_as_lender} ★
                                                                     </span>
                                                                 </small>
                                                             ) : (
                                                                 <small>
                                                                     <span className="badge bg-secondary">
                                                                         -.- ★
                                                                     </span>
                                                                 </small>
                                                             )
                                                         }
                                                     </p>
                                                 </div>

                                                 {
                                                     isLoggedIn ? (
                                                         <>
                                                             {
                                                                 data.reservable ? (
                                                                     <CalendarReservable
                                                                         reservedDates={reservedDates}
                                                                         startDate={beginDate}
                                                                         endDate={endDate}
                                                                         handleStartDateChange={setBeginDate}
                                                                         handleEndDateChange={setEndDate}
                                                                         maxLendingDays={data.maxLendingDays}
                                                                     />
                                                                 ) : (
                                                                     <CalendarNotReservable
                                                                         startDate={beginDate}
                                                                         endDate={endDate}
                                                                         handleEndDateChange={setEndDate}
                                                                         maxLendingDays={data.maxLendingDays}
                                                                     />
                                                                 )
                                                             }
                                                             <button className="btn btn-green"
                                                                     onClick={ () => {
                                                                         handleClickSendLending().then()
                                                                     }
                                                                    }
                                                             >
                                                                 {t('view_asset.lending_btn')}
                                                             </button>
                                                             {
                                                                 noDatesSelected ? (<><br/> <p className="error">{t('view_asset.error_no_dates')}</p></>) : (<></>)
                                                             }
                                                         </>
                                                     ) : (
                                                         <>
                                                             <button className="btn btn-green" onClick={() => {navigate("/login")}}>
                                                                 {t('view_asset.login_btn')}
                                                             </button>
                                                         </>
                                                     )
                                                 }
                                             </div>
                                         </div>
                                     </div>

                                     <div className="container-row"
                                          style={{width: '50%', marginBottom: '20px'}}>

                                         <div className="container-column" style={{flex: '0 0 100 %', width: '100%'}}>
                                             <div className="card"
                                                  style={{backgroundColor: '#e3e6e3', height: 'fit-content', borderRadius: '25px'}}>
                                                 <div className="card-body">
                                                     <h5 className="card-title" style={{textAlign: 'center'}}>
                                                         {t('view_asset.location.title')}
                                                     </h5>
                                                     <p className="card-text" style={{marginBottom: '-5px'}}>
                                                         {t('view_asset.location.zip_code')}
                                                     </p>
                                                     <h3 className="textOverflow">
                                                         {data.location.zipcode}
                                                     </h3>

                                                     <p className="card-text" style={{marginBottom: '-5px'}}>
                                                         {t('view_asset.location.locality')}
                                                     </p>
                                                     <h3 className="textOverflow">
                                                         {data.location.locality}
                                                     </h3>

                                                     <p className="card-text" style={{marginBottom: '-5px'}}>
                                                         {t('view_asset.location.province')}
                                                     </p>
                                                     <h3 className="textOverflow">
                                                         {data.location.province}
                                                     </h3>

                                                     <p className="card-text" style={{marginBottom: '-5px'}}>
                                                         {t('view_asset.location.country')}
                                                     </p>
                                                     <h3 className="textOverflow">
                                                         {data.location.country}
                                                     </h3>
                                                 </div>
                                             </div>
                                         </div>

                                     </div>
                                     <div className="container-row" style={{width: '50%', marginBottom: '20px'}}>
                                         <div className="container-column" style={{flex: '0 0 100%'}}>
                                             <div className="card"
                                                  style={{backgroundColor: '#e3e6e3', height: 'fit-content', borderRadius: '25px'}}>
                                                 <div className="card-body">
                                                     <h5 className="card-title" style={{textAlign: 'center'}}>
                                                         {t('view_asset.description.title')}
                                                     </h5>
                                                     {hasDescription ? (
                                                         <p style={{
                                                             wordWrap: 'break-word',
                                                             wordBreak: 'break-word',
                                                             maxHeight: '200px',
                                                             overflowY: 'auto'
                                                         }}>
                                                             {data.description}
                                                         </p>
                                                     ) : (
                                                         <>
                                                             <h1 className="text-muted text-center mt-5"><i
                                                                 className="bi bi-x-circle"></i></h1>
                                                             <h6 className="text-muted text-center mb-5">
                                                                 {t('view_asset.description.no_description')}
                                                             </h6>
                                                         </>
                                                     )
                                                     }
                                                 </div>
                                             </div>
                                         </div>
                                     </div>



                                     <div className="container-row" style={{width: '50%', marginBottom: '20px'}}>
                                         <div className="container-column" style={{flex: '0 0 100%'}}>
                                             <div className="card p-2"
                                                  style={{backgroundColor: '#e3e6e3', height: 'fit-content', borderRadius: '25px'}}>
                                                 <div className="container-row">
                                                     <div className=""
                                                          style={{
                                                              flexGrow: '1',
                                                              display: 'flex',
                                                              justifyContent: 'center',
                                                              alignItems: 'center'
                                                          }}
                                                          id="scrollspyRating">
                                                         <div className="container-column" style={{width: '100%'}}>
                                                             {
                                                                 !hasReviews ? (
                                                                     <>
                                                                         <h1 className="text-muted text-center mt-5"><i
                                                                             className="bi bi-x-circle"></i></h1>
                                                                         <h6 className="text-muted text-center mb-5">
                                                                             {t('view_asset.reviews.no_reviews')}
                                                                         </h6>
                                                                     </>
                                                                 ) : (
                                                                     <>
                                                                         <div className="text-center">
                                                                             <h1>{data.rating_assetInstance} <small>/ 5</small></h1>
                                                                             <StarsReviews
                                                                                 rating={parseInt(data.rating_assetInstance.toString(), 10)}/>
                                                                         </div>
                                                                         {
                                                                             data.reviews.map((review, index) => (<ShowReviewCard review={review} key={index}/> ))
                                                                         }
                                                                         <div className="text-center">
                                                                             {/*@ts-ignore*/}
                                                                            <GreenButton style={{width: '20%'}}
                                                                                         text={t('view_asset.reviews.see_all')}
                                                                                         action={() => {navigate(`/book/${bookNumber}/reviews`)}} />
                                                                         </div>
                                                                     </>
                                                                 )
                                                             }

                                                         </div>
                                                     </div>
                                                 </div>

                                             </div>
                                         </div>
                                     </div>

                                 </div>
                             </>
                         )
                     }
                     </>
                )
            }
        </>
    )
}
export default ViewAssetInstance;
