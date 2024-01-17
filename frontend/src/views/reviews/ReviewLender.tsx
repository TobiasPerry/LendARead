import BookCardPlaceholder from "../../components/BookCardPlaceholder.tsx";

import ReviewCard from "../../components/reviews/ReviewCard.tsx";
import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import UseReview, {Asset_and_borrower_data} from "../../hooks/reviews/useReview.ts";
import LoadingAnimation from "../../components/LoadingAnimation.tsx";
import {useParams} from "react-router-dom";
import BookCard from "../../components/BookCard.tsx";
import NotFound from "../../components/NotFound.tsx";

export default function ReviewLender () {

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
        }, borrower: {selfUrl: "", userName: ""}
    }

    const { lendingNumber } = useParams<{ lendingNumber: string}>()

    const [loading, setLoading] = useState(true)
    const [data, setData] = useState(res_empty)
    const [found, setFound] = useState(false)

    const {t} = useTranslation();
    const { handleGetLendingInfoForLender } = UseReview()

    useEffect(() => {
        document.title = t('reviews.title')
        const fetchData = async () => {
            setLoading(true)
            const res: Asset_and_borrower_data = await handleGetLendingInfoForLender(lendingNumber)
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
            { loading? (
                <LoadingAnimation/>
            ) : (
                !found ? (
                    <NotFound/>
                ):(
                    <div className="main-class"
                         style={{display: 'flex', justifyContent: 'center', alignItems: 'center', flexDirection: 'column'}}>


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
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                )
            )}

        </>
    )
}