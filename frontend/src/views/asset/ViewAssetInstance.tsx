import {Link, useNavigate, useParams} from "react-router-dom";
import {useContext, useEffect, useState} from "react";
import "../styles/assetView.css"
import useAssetInstance from "../../hooks/assetInstance/useAssetInstance.ts";
import {AssetData} from "../../hooks/assetInstance/useAssetInstance.ts";
import LoadingAnimation from "../../components/LoadingAnimation.tsx";
import NotFound from "../../components/NotFound.tsx";
import {useTranslation} from "react-i18next";
import {AuthContext} from "../../contexts/authContext.tsx";
import CalendarReservable from "../../components/viewAsset/CalendarReservable.tsx";
import CalendarNotReservable from "../../components/viewAsset/CalendarNotReservable.tsx";
import Modal from "../../components/modals/Modal.tsx";
import StarsReviews from "../../components/viewAsset/StarsReviews.tsx";

const ViewAssetInstance = () => {

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

    // AssetInstance Information
    const [data, setData] = useState(book)
    const [hasReviewAsLender, setHasReviewAsLender] = useState(false) // To decide if show rating alongside the user
    const [hasUserImage, setHasUserImage] = useState(false)
    const [hasDescription, setHasDescription] = useState(false)
    const [hasReviews, setHasReviews] = useState(false)
    const [ownerRating, setOwnerRating] = useState(3)
    const [reservedDates, setReservedDates] = useState([])

    // Lending request info
    const [beginDate, setBeginDate] = useState(null)
    const [endDate, setEndDate] = useState(null)

    const navigate = useNavigate()

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            setData(book)
            const res: AssetData = await handleAssetInstance(bookNumber)
            // if found
            if(!(res === null || res === undefined)){
                setFound(true)
                setHasReviews(res.rating !== 0)
                setHasUserImage((!(res.userImage === null || res.userImage === undefined)))
                setHasDescription((!(res.description === null || res.description === undefined || res.description === "")))
                setData(res)
                // if reservable, get the reserved dates
                if(res.reservable) {
                    const res_reserved_dates = await handleGetReservedDays(bookNumber);
                    setReservedDates((res_reserved_dates === null || res_reserved_dates === undefined) ? [] : res_reserved_dates)
                }
                // Otherwise, just set initial date today and limit the end date
                else {
                    const today = new Date();
                    today.setHours(0,0,0)
                    setBeginDate(today)
                }
                // Set the header title
                document.title = t('view_asset.title', {title: res.title, author: res.author})
                setLoading(false)
            }else {
                setFound(false)
                setLoading(false)
                document.title = "Not Found"
            }
        };
        fetchData()

        // for when it unmounts
        return () => {
            document.title = "Lend a Read"
        }
    }, []);

    // Request the lending
    const handleClickSendLending = async () =>  {
        const res = await handleSendLendingRequest(
            { borrowDate: beginDate, devolutionDate: endDate, assetInstanceId: bookNumber }
        );
    }

    // These are the links to redirect to discovery with filters applied
    const authorURL = `/discovery?search=${data.author}`
    const physicalConditionURL = `/discovery?physicalCondition=${data.physicalCondition}`
    const languageURL = `/discovery?language=${data.language.code}`

    return (
        <>
            <Modal
                showModal={success}
                title="text" subtitle="text" btnText="text"
                handleSubmitModal={() => {navigate('/discovery')}}
                handleCloseModal={() => {navigate('/discovery')}}
            />
            <Modal
                showModal={error} errorType={true}
                title="text" subtitle="text" btnText="text"
                handleSubmitModal={() => {setError(false)}}
                handleCloseModal={() => {setError(false)}}
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


                                                 <div className="container-row" style={{justifyContent: 'start'}}>
                                                     <Link to="/">
                                                         {
                                                             hasUserImage ? (
                                                                 <img className="rounded-circle img-hover-click"
                                                                      style={{width: '25px', height: '25px'}}
                                                                      src={data.userImage}
                                                                      alt="profile picture"/>
                                                             ) : (
                                                                 <img className="rounded-circle img-hover-click" style={{width: '25px'}}
                                                                      src="/static/profile_placeholder.jpeg"
                                                                      alt="profile picture"/>
                                                             )
                                                         }
                                                     </Link>
                                                     {/*<a href="<c:url value="/user/${assetInstance.owner.id}"/>" style="color: inherit; text-decoration: none;">*/}
                                                     <Link to="/">
                                                         <span className="mx-2 text-clickable">{data.userName}</span>
                                                     </Link>
                                                     {/*</a>*/}
                                                     <p>
                                                         {
                                                             hasReviewAsLender ? (
                                                                 <span className="badge bg-success">
                                               {ownerRating} ★
                                            </span>
                                                             ) : (
                                                                 <span className="badge bg-secondary">-.- ★</span>
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
                                                                         handleClickSendLending().then((value) => {
                                                                             setSuccess(value !== null && value !== undefined)
                                                                             setError(value === null || value === undefined)
                                                                         })
                                                                     }
                                                                    }
                                                             >
                                                                 {t('view_asset.lending_btn')}
                                                             </button>
                                                         </>
                                                     ) : (
                                                         <>
                                                             <button className="btn btn-green" onClick={() => {navigate("/login")}}>
                                                                 text de login
                                                             </button>
                                                         </>
                                                     )
                                                 }
                                             </div>
                                         </div>
                                     </div>

                                     <div className="container-row"
                                          style={{minWidth: '50%', width: 'fit-content', marginBottom: '20px'}}>

                                         <div className="container-column" style={{flex: '0 0 100 %'}}>
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
                                     <div className="container-row" style={{width: '50 %', marginBottom: '20px'}}>
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



                                     <div className="container-row" style={{width: '50 %', marginBottom: '20px'}}>
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
                                                         <div className="container-column">
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
                                                                             <h1>{data.rating} <small>/ 5</small></h1>
                                                                         </div>
                                                                         <StarsReviews rating={parseInt(data.rating.toString(), 10)}/>
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
