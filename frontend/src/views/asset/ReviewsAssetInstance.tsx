import {useNavigate, useParams} from "react-router-dom";
import useReview from "../../hooks/reviews/useReview.ts";
import useAssetInstance, {AssetData} from "../../hooks/assetInstance/useAssetInstance.ts";
import {useEffect, useState} from "react";
import LoadingAnimation from "../../components/LoadingAnimation.tsx";
import NotFound from "../NotFound.tsx";
import ShowReviewCard from "../../components/viewAsset/ShowReviewCard.tsx";
import {useTranslation} from "react-i18next";
import Pagination from "../../components/Pagination.tsx";

const ReviewsAssetInstance = () => {

    const empty ={title: "", author: "", reviews: [], pages: 0}
    const { bookNumber } = useParams<{ bookNumber: string}>()
    const {handleGetAllReviewsForBook} = useReview()
    const {t} = useTranslation()
    const navigate = useNavigate()

    const [data, setData] = useState(empty)

    const [page, setPage] = useState(1)
    const [loading, setLoading] = useState(true)
    const [notFound, setNotFound] = useState(false)

    const itemsPerPage = 10

    const fetchData = async ()=> {
        setLoading(true)
        const res = await  handleGetAllReviewsForBook(bookNumber, itemsPerPage, page)
        if(!(res === null || res === undefined)){
            setNotFound(false)
            setData(res)
            document.title = (t('view_asset_reviews.title', {title: res.title}))
        }else{
            setNotFound(true)
            document.title = t('not_found.tab_title')
        }
        setLoading(false)
    }

    useEffect(() => {
        fetchData().then()
    }, [page]);

    return (
        <>
            {
                loading ? (
                    <LoadingAnimation/>
                ):(
                    <>
                        {
                            notFound ? (
                                <NotFound/>
                            ): (
                                <div className="main-class py-3">
                                    <div className="d-flex back-click flex-row align-items-center mx-3"
                                         onClick={() => {navigate(`/book/${bookNumber}`)}}>
                                        <i className="fas fa-arrow-left mb-1"></i>
                                        <h3 className="ms-3">
                                            {`${data.title} ${t('view_asset.by')} ${data.author}`}
                                        </h3>
                                    </div>
                                    <div style={{
                                        display: 'flex',
                                        justifyContent: 'center',
                                        alignItems: 'center',
                                        flexDirection: 'column',
                                    }}>
                                        <div style={{width: '50%'}}>
                                            {
                                                data.reviews.length === 0 ? (
                                                    <>
                                                        <h1 className="text-muted text-center mt-5"><i
                                                            className="bi bi-x-circle"></i></h1>
                                                        <h6 className="text-muted text-center mb-5">
                                                            {t('view_asset.reviews.no_reviews')}
                                                        </h6>
                                                    </>
                                                ) : (
                                                    <>
                                                        {
                                                            data.reviews.map((review, index) => (
                                                                <ShowReviewCard review={review} key={index}/>
                                                            ))
                                                        }
                                                        <Pagination totalPages={data.pages} currentPage={page} changePage={(page)=>{setPage(page)}}/>
                                                    </>
                                                )
                                            }
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
export default ReviewsAssetInstance