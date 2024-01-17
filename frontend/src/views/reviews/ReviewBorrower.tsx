import BookCardPlaceholder from "../../components/BookCardPlaceholder.tsx";
import ReviewCard from "../../components/reviews/ReviewCard.tsx";
import {useTranslation} from "react-i18next";
import UseReview, {Asset_and_lender_data} from "../../hooks/reviews/useReview.ts";
import {useEffect, useState} from "react";
import LoadingAnimation from "../../components/LoadingAnimation.tsx";
import NotFound from "../../components/NotFound.tsx";
import {useParams} from "react-router-dom";
import BookCard from "../../components/BookCard.tsx";

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
        }, lender: {selfUrl: "", userName: ""}
    }

    const { lendingNumber } = useParams<{ lendingNumber: string}>()

    const {t} = useTranslation()

    const [data, setData] = useState(res_empty)
    const [loading, setLoading] = useState(true)
    const [found, setFound] = useState(false)

    const {handleGetLendingInfoForBorrower} = UseReview()

    useEffect(() => {
        document.title = t('reviews.title')
        const fetchData = async () => {
            setLoading(true)
            const res : Asset_and_lender_data = await handleGetLendingInfoForBorrower(lendingNumber)
            setFound((!(res === null || res === undefined)))
            setData(res)
            setLoading(false)
        }
        fetchData()
        // for when it unmounts
        return () => {
            document.title = "Lend a Read"
        }
    }, []);

    return(
        <>
            {
                loading ? (
                    <LoadingAnimation/>
                ) : (
                    <>
                        {
                            !found ? (
                                <NotFound/>
                            ): (
                                <div className="main-class"
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
                                                />
                                                <ReviewCard
                                                    title={t('reviews.borrower.book.title', {user: "USERNAME"})}
                                                    error_stars={t('reviews.borrower.book.error_stars')}
                                                    error_description={t('reviews.borrower.book.error_text')}
                                                    placeholder={t('reviews.borrower.book.placeholder')}
                                                />
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