import useReview, {ShowReview} from "../../hooks/reviews/useReview.ts";
import {useEffect, useState} from "react";
import StarsReviews from "./StarsReviews.tsx";
import {useTranslation} from "react-i18next";

const ShowReviewCard = ({review}) => {

    const empty_data : ShowReview = {
        rating: 0, role: "", text: "", userImage: "", userName: ""
    }
    const {t} = useTranslation()
    const { handleGetReviewDataForAssetInstance } = useReview()
    const [data, setData] = useState(empty_data)
    const [hasImage, setHasImage] = useState(false)
    const fetchAuxData = async () => {
        const res : ShowReview = await handleGetReviewDataForAssetInstance(review)
        if(res !== null && res !== undefined)
            setData(res);
    }
    useEffect(() => {
        fetchAuxData()
    }, []);


    return (
        <div className="row d-flex justify-content-center" style={{}}>
            <div style={{width: '90%'}} className="my-2" >
                <div className="card">
                    <div className="card-body m-3">
                        <div className="row">
                            <div className="col-lg-4 justify-content-center align-tems-center">
                                <img src={hasImage ? `${data.userImage}` : "/static/profile_placeholder.jpeg"}
                                     className="rounded-circle img-fluid shadow-1" alt="avatar" width="50"
                                     height="50"/>
                                <p className="fw-bold lead mb-2"><strong>{data.userName}</strong></p>
                                <p className="fw-bold text-muted mb-0">{t(data.role)}</p>
                            </div>
                            <div className="col-lg-8">
                                <StarsReviews rating={data.rating}/>
                                <p className="fw-light mb-4">
                                    {data.text}
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default ShowReviewCard;