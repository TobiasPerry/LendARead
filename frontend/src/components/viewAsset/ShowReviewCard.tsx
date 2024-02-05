import useReview, {ShowReview} from "../../hooks/reviews/useReview.ts";
import {useEffect, useState} from "react";
import StarsReviews from "./StarsReviews.tsx";
import {useTranslation} from "react-i18next";
import ProfilePlaceholder from "../../../public/static/profile_placeholder.jpeg"
import {Link} from "react-router-dom";

const ShowReviewCard = ({review}) => {

    const empty_data : ShowReview = {
        rating: 0, role: "", text: "", userImage: "", userName: "", userId: -1
    }
    const {t} = useTranslation()
    const { handleGetReviewDataForAssetInstance } = useReview()
    const [data, setData] = useState(empty_data)
    const [hasImage, setHasImage] = useState(false)
    const [imgSrc, setImgSrc] = useState<any>(ProfilePlaceholder)
    const fetchAuxData = async () => {
        const res : ShowReview = await handleGetReviewDataForAssetInstance(review)
        if(res !== null && res !== undefined) {
            setData(res);
            setHasImage(res.userImage !== null && res.userImage !== undefined)
        }
    }
    useEffect(() => {
        fetchAuxData()
    }, []);

    useEffect(() => {
        if (hasImage) {
            setImgSrc(data.userImage + '?size=PORTADA')
        } else {
            setImgSrc(ProfilePlaceholder)
        }
    }, [hasImage]);


    return (
        <div className="row d-flex justify-content-center" style={{}}>
            <div style={{width: '90%'}} className="my-2" >
                <div className="card">
                    <div className="card-body m-3">
                        <div className="row">
                            <div className="col-lg-4 justify-content-center align-tems-center">
                                <Link to={`/user/${data.userId}`}>
                                    <img src={imgSrc}
                                         className="rounded-circle img-fluid shadow-1 img-hover-click" alt="avatar"
                                         style={{
                                             objectFit: 'cover',
                                             width: '50px', height: '50px'
                                         }}
                                    />
                                </Link>
                                <Link to={`/user/${data.userId}`}><p className="fw-bold lead mb-2 text-clickable"><strong>{data.userName}</strong></p></Link>
                                <p className="fw-bold text-muted mb-0">{t(data.role)}</p>
                            </div>
                            <div className="col-lg-8">
                                <StarsReviews rating={data.rating}/>
                                <p className="fw-light mb-4" >
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
