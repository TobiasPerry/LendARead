import ReviewCard from "../../components/reviews/ReviewCard.tsx";
import {useTranslation} from "react-i18next";
import UseReview, {Asset_and_lender_data, body_review} from "../../hooks/reviews/useReview.ts";
import React, {useContext, useEffect, useState} from "react";
import LoadingAnimation from "../../components/LoadingAnimation.tsx";
import NotFound from "../NotFound.tsx";
import {useNavigate, useParams} from "react-router-dom";
import BookCard from "../../components/BookCard.tsx";
import Modal from "../../components/modals/Modal.tsx";
import {AuthContext} from "../../contexts/authContext.tsx";
import {Helmet} from "react-helmet";

export default function ReviewBorrower () {

    const res_empty : Asset_and_lender_data = {
        book: {
            assetInstanceNumber: 0,
            author: "",
            country: "",
            image: "",
            locality: "",
            physicalCondition: "",
            province: "",
            title: "",
            userImage: "",
            userName: ""
        }, lender: {selfUrl: "", userName: "", userId: 0}
    }

    const navigate = useNavigate()
    const { lendingNumber } = useParams<{ lendingNumber: string}>()

    const review_empty : body_review = {
        review: "",
        rating: -1,
        lendingId: lendingNumber
    }

    const {t} = useTranslation()

    const [data, setData] = useState(res_empty)
    const [loading, setLoading] = useState(true)
    const [found, setFound] = useState(false)
    const [userReview, setUserReview] = useState(review_empty)
    const [assetInstanceReview, setAssetInstanceReview] = useState(review_empty)
    const [success, setSuccess] = useState(false)
    const [error, setError] = useState(false)
    const [alreadyReviewed, setAlreadyReviewed] = useState(false)
    const [notAllowed, setNotAllowed] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")

    const [showError_stars_user, setShowError_stars_user] = useState(false)
    const [showError_review_user, setShowError_review_user] = useState(false)
    const [showError_stars_assetInstance, setShowError_stars_assetInstance] = useState(false)
    const [showError_review_assetInstance, setShowError_review_assetInstance] = useState(false)

    const {handleGetLendingInfoForBorrower, handleSendBorrowerReview} = UseReview()
    const {user} = useContext(AuthContext)

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true)
            // @ts-ignore
            const {info, exists} : { Asset_and_lender_data, boolean } = await handleGetLendingInfoForBorrower(lendingNumber)
            setAlreadyReviewed(exists)
            setFound((!(info === null || info === undefined)))
            setNotAllowed( (info !== null && info !== undefined) && (user.toString() === info.lender.userId.toString()) )
            setData(info)
            setLoading(false)
        }
        fetchData().then()
    }, []);

    const handleChangeReview_userReview = (value) => {
        setUserReview({
            review: value,
            rating: userReview.rating,
            lendingId: userReview.lendingId
        })
    }
    const handleChangeRating_userReview = (value) => {
        setUserReview({
            review: userReview.review,
            rating: value,
            lendingId: userReview.lendingId
        })
    }

    const handleChangeReview_assetInstanceReview = (value) => {
        setAssetInstanceReview({
            review: value,
            rating: assetInstanceReview.rating,
            lendingId: assetInstanceReview.lendingId
        })
    }
    const handleChangeRating_assetInstanceReview = (value) => {
        setAssetInstanceReview({
            review: assetInstanceReview.review,
            rating: value,
            lendingId: assetInstanceReview.lendingId
        })
    }

    const handleBackClick = () => {
        navigate(`/userBook/${lendingNumber}?state=borrowed`)
    }

    const handleSendClick = async ()=>{
        if(userReview.review === "" || assetInstanceReview.review === "" || userReview.rating === -1 || assetInstanceReview.rating === -1) {
            setShowError_review_user(userReview.review === "");
            setShowError_review_assetInstance(assetInstanceReview.review === "");
            setShowError_stars_user(userReview.rating === -1);
            setShowError_stars_assetInstance(assetInstanceReview.rating === -1);
        }else{
            handleSendBorrowerReview(userReview, assetInstanceReview, data.lender.userId, data.book.assetInstanceNumber)
                .then((value) => {
                    setSuccess(value.userReviewResponse !== null && value.userReviewResponse !== undefined && value.assetInstanceReviewResponse !== null && value.assetInstanceReviewResponse!== undefined);
                    setError(value.userReviewResponse === null || value.userReviewResponse === undefined || value.assetInstanceReviewResponse === null || value.assetInstanceReviewResponse === undefined)
                    setErrorMessage(value.errorMessage)
                });
        }
    }

    return(
        <>
            <Helmet>
                <meta charSet="utf-8"/>
                <title>{t('reviews.title')}</title>
            </Helmet>
            <Modal
                showModal={success}
                title={t('reviews.success_modal.title')}
                subtitle={t('reviews.success_modal.subtitle')}
                btnText={t('reviews.success_modal.btn')}
                handleSubmitModal={() => {navigate(`/userBook/${lendingNumber}?state=borrowed`)}}
                handleCloseModal={() => {navigate(`/userBook/${lendingNumber}?state=borrowed`)}}
                icon="bi bi-check"
            />
            <Modal
                showModal={error} errorType={true}
                title={t('reviews.error_modal.title')}
                subtitle={errorMessage}
                btnText={t('reviews.error_modal.btn')}
                handleSubmitModal={() => {setError(false)}}
                handleCloseModal={() => {setError(false)}}
                icon="bi bi-x"
            />
            {
                loading ? (
                    <LoadingAnimation/>
                ) : (
                    <>
                        {
                            !found || alreadyReviewed || notAllowed ? (
                                <NotFound/>
                            ): (
                                <div className="main-class py-3">
                                    <div className="d-flex back-click flex-row align-items-center mx-3"
                                         onClick={handleBackClick}>
                                        <i className="fas fa-arrow-left mb-1"></i>
                                        <h3 className="ms-3">
                                            {`${data.book.title} ${t('view_asset.by')} ${data.book.author}`}
                                        </h3>
                                    </div>
                                    <div
                                        style={{
                                            display: 'flex',
                                            justifyContent: 'center',
                                            alignItems: 'center',
                                            flexDirection: 'column'
                                        }}>
                                        <div className="container-row-wrapped">
                                            <div className="d-flex align-items-center justify-content-center">
                                                <BookCard book={data.book}/>
                                            </div>
                                            <div className="">
                                                <div style={{
                                                    display: 'flex',
                                                    flexDirection: 'column',
                                                    justifyContent: 'space-between',
                                                    maxWidth: '800px'
                                                }}>
                                                    <ReviewCard
                                                        title={t('reviews.borrower.user.title', {user: data.lender.userName})}
                                                        error_stars={t('reviews.borrower.user.error_stars')}
                                                        error_description={t('reviews.borrower.user.error_text')}
                                                        placeholder={t('reviews.borrower.user.placeholder')}
                                                        type="1"
                                                        handleRating={handleChangeRating_userReview}
                                                        handleReview={handleChangeReview_userReview}
                                                        showError_review={showError_review_user}
                                                        showError_stars={showError_stars_user}
                                                    />
                                                    <ReviewCard
                                                        title={t('reviews.borrower.book.title', {user: "USERNAME"})}
                                                        error_stars={t('reviews.borrower.book.error_stars')}
                                                        error_description={t('reviews.borrower.book.error_text')}
                                                        placeholder={t('reviews.borrower.book.placeholder')}
                                                        type="2"
                                                        handleRating={handleChangeRating_assetInstanceReview}
                                                        handleReview={handleChangeReview_assetInstanceReview}
                                                        showError_review={showError_review_assetInstance}
                                                        showError_stars={showError_stars_assetInstance}
                                                    />
                                                    <button
                                                        onClick={
                                                            () => {handleSendClick().then()
                                                            }
                                                        }
                                                    >
                                                        {t('reviews.send')}
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            )
                        }
                    </>
                )
            }
        </>
    )
}