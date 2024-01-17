import {useEffect} from 'react';
import { useTranslation } from 'react-i18next';
import {Link} from "react-router-dom";
import LoadingAnimation from "../LoadingAnimation.tsx";

export const isRejected = (lending: any) => {
    return lending === "REJECTED"
}
export const isPublic = (lending: any) => {
    return lending === "PUBLIC"
}
export const isPrivate = (lending: any) => {
    return lending === "PRIVATE"
}
export const isFinished = (lending: string) => {
    return lending === "FINISHED"
}

export const isActive = (lending: string) => {
    return lending === "ACTIVE"
}
export const isDelivered = (lending: string) => {
    return lending === "DELIVERED"
}

function LendedBooksOptions({ asset, canReview }) {
    const { t } = useTranslation();

    useEffect( () => {

        console.log(asset)
    }, [asset])
    return (
        <>
            {!(asset === undefined || asset.lending === undefined) && (
                <div style={{
                    backgroundColor: '#f0f5f0',
                    padding: '10px',
                    borderRadius: '20px',
                    display: "flex",
                    alignContent: "center",
                }} className="flex-column">
                    <h3>Lended Book actions</h3>
                    {!isRejected(asset.lending.state) && !isFinished(asset.lending.state) && (
                        <div className="options-menu">
                            {isActive(asset.lending.state) && (
                                <>
                                    <h6 style={{ color: '#7d7c7c', fontWeight: 'bold' }}>
                                        {t('userHomeView.pendingText')}
                                    </h6>
                                    <div className=" d-flex flex-center m-auto">
                                        <button id="confirmAssetBtn" className="btn btn-green" type="submit">
                                            {t('userHomeView.verifyBook')}
                                        </button>
                                        <button id="rejectAssetBtn" className="btn btn-red-outline mt-2" type="submit">
                                            {t('userHomeView.rejectAssetTitle')}
                                        </button>
                                    </div>
                                </>
                            )}
                            {isDelivered(asset.lending.state) && (
                                <>
                                    <h6 style={{ color: '#7d7c7c', fontWeight: 'bold' }}>
                                        {t('userHomeView.inProgress')}
                                    </h6>
                                    <button id="returnAssetBtn" className="btn btn-green" type="submit">
                                        {t('userHomeView.confirmReturn')}
                                    </button>
                                </>
                            )}
                        </div>
                    )}
                    {canReview && (
                        <Link className="btn btn-green mt-3" to="/reviews">
                            {t('makeReview')}
                        </Link>
                    )}
                    {/* Include modal components here */}
                    {/* <ReturnModal lending={lending} />
      <ConfirmModal lending={lending} />
      <RejectModal lending={lending} /> */}
                </div>
            )}
        </>
    );
}

export default LendedBooksOptions;
