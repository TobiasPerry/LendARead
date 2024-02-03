import ReviewCard from "../../components/reviews/ReviewCard.tsx";
import {useTranslation} from "react-i18next";
import React, {useContext, useEffect, useState} from "react";
import UseReview, {Asset_and_borrower_data, body_review} from "../../hooks/reviews/useReview.ts";
import LoadingAnimation from "../../components/LoadingAnimation.tsx";
import {useNavigate, useParams} from "react-router-dom";
import BookCard from "../../components/BookCard.tsx";
import NotFound from "../NotFound.tsx";
import Modal from "../../components/modals/Modal.tsx";
import {AuthContext} from "../../contexts/authContext.tsx";
import {Helmet} from "react-helmet";

export default function ReviewLender () {
    const { lendingNumber } = useParams<{ lendingNumber: string}>()

    const res_empty : Asset_and_borrower_data = {
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
        }, borrower: {selfUrl: "", userName: "", userId: -1}
    }
    const review_empty : body_review = {
        review: "",
        rating: -1,
        lendingId: lendingNumber
    }

    const navigate  = useNavigate()

    const [loading, setLoading] = useState(true)
    const [data, setData] = useState(res_empty)
    const [found, setFound] = useState(false)
    const [success, setSuccess] = useState(false)
    const [error, setError] = useState(false)
    const [alreadyReviewed, setAlreadyReviewed] = useState(false)
    const [userReview, setUserReview] = useState(review_empty)
    const [notAllowed, setNotAllowed] = useState(false)
    const [showError_stars, setShowError_stars] = useState(false)
    const [showError_review, setShowError_review] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")

    const {t} = useTranslation();
    const { handleGetLendingInfoForLender, handleSendLenderReview } = UseReview()
    const {user} = useContext(AuthContext)



    useEffect(() => {
        const fetchData = async () => {
            setLoading(true)
            // @ts-ignore
            const {info, exists}: { Asset_and_borrower_data, boolean } = await handleGetLendingInfoForLender(lendingNumber)
            setAlreadyReviewed(exists)
            setFound(!(info === null || info === undefined))
            setNotAllowed( (info !== null && info !== undefined) && (user.toString() === info.borrower.userId.toString()) )
            setData(info)
            setLoading(false)
        }
        fetchData()
    }, []);

    const handleBackClick = () => {
        navigate(`/userBook/${lendingNumber}?state=lended`)
    }

    const handleSendClick = async () => {
        if(userReview.review === "" || userReview.rating === -1 ) {
            setShowError_review(userReview.review === "");
            setShowError_stars(userReview.rating === -1);
        }else {
            handleSendLenderReview(userReview, data.borrower.userId)
                .then((value) => {
                    setSuccess(value.res !== null && value.res !== undefined);
                    setError(value.res === null || value.res === undefined);
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
                handleSubmitModal={() => {navigate(`/userBook/${lendingNumber}?state=lended`)}}
                handleCloseModal={() => {navigate(`/userBook/${lendingNumber}?state=lended`)}}
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
            { loading? (
                <LoadingAnimation/>
            ) : (
                !found || alreadyReviewed || notAllowed ? (
                    <NotFound/>
                ):(
                    <div className="main-class py-3">
                        <div className="d-flex back-click flex-row align-items-center mx-3" onClick={handleBackClick}>
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
                                            title={t('reviews.lender.user.title', {user: data.borrower.userName})}
                                            error_stars={t('reviews.lender.user.error_stars')}
                                            error_description={t('reviews.lender.user.error_text')}
                                            showError_stars={showError_stars}
                                            showError_review={showError_review}
                                            placeholder={t('reviews.lender.user.placeholder')}
                                            handleReview={(value) => {
                                                setUserReview({
                                                    review: value,
                                                    rating: userReview.rating,
                                                    lendingId: userReview.lendingId
                                                })
                                            }}
                                            handleRating={(value) => {
                                                setUserReview({
                                                    review: userReview.review,
                                                    rating: value,
                                                    lendingId: userReview.lendingId
                                                })
                                            }}
                                        />
                                        <button
                                            onClick={
                                                () => {handleSendClick().then()}
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
            )}

        </>
    )
}