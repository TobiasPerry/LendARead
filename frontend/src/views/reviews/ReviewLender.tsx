import BookCardPlaceholder from "../../components/BookCardPlaceholder.tsx";

import ReviewCard from "../../components/reviews/ReviewCard.tsx";
import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import UseReview, {Asset_and_borrower_data, body_review} from "../../hooks/reviews/useReview.ts";
import LoadingAnimation from "../../components/LoadingAnimation.tsx";
import {useNavigate, useParams} from "react-router-dom";
import BookCard from "../../components/BookCard.tsx";
import NotFound from "../../components/NotFound.tsx";
import Modal from "../../components/modals/Modal.tsx";

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

    const {t} = useTranslation();
    const { handleGetLendingInfoForLender, handleSendLenderReview } = UseReview()



    useEffect(() => {
        document.title = t('reviews.title')
        const fetchData = async () => {
            setLoading(true)
            // @ts-ignore
            const {info, exists}: { Asset_and_borrower_data, boolean } = await handleGetLendingInfoForLender(lendingNumber)
            setAlreadyReviewed(exists)
            setFound(!(info === null || info === undefined))
            setData(info)
            setLoading(false)
        }
        fetchData()
        // for when it unmounts
        return () => {
            document.title = "Lend a Read"
        }
    }, []);

    const handleBackClick = () => {
        navigate(`/userBook/${data.book.assetInstanceNumber}?state=lended`)
    }

    return(
        <>
            <Modal
                showModal={success}
                title={t('reviews.success_modal.title')}
                subtitle={t('reviews.success_modal.subtitle')}
                btnText={t('reviews.success_modal.btn')}
                handleSubmitModal={() => {navigate('/userAssets')}}
                handleCloseModal={() => {setSuccess(false)}}
            />
            <Modal
                showModal={error} errorType={true}
                title={t('reviews.error_modal.title')}
                subtitle={t('reviews.error_modal.subtitle')}
                btnText={t('reviews.error_modal.btn')}
                handleSubmitModal={() => {navigate('/userAssets')}}
                handleCloseModal={() => {setError(false)}}
            />
            { loading? (
                <LoadingAnimation/>
            ) : (
                !found || alreadyReviewed ? (
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
                                                () => {
                                                    handleSendLenderReview(userReview, data.borrower.userId)
                                                        .then((value) => {
                                                            setSuccess(value !== null && value !== undefined);
                                                            setError(value === null || value === undefined)
                                                        });
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
            )}

        </>
    )
}