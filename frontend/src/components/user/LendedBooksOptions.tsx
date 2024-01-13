import React from 'react';
import { useTranslation } from 'react-i18next';

export const isRejected = (lending: any) => {
    return lending === "REJECTED"
}
export const isPublic = (lending: any) => {
    return lending === "PUBLIC "
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

function LendedBooksOptions({ lending, canReview }) {
    const { t } = useTranslation();

    return (
        <div style={{
            backgroundColor: '#f0f5f0',
            padding: '100px',
            borderRadius: '20px',
            width: '50%'
        }}>
            <h3>Lended Book actions</h3>
            {!isRejected(lending) && !isFinished(lending) && (
                <div className="options-menu">
                    {isActive(lending) && (
                        <>
                            <h6 style={{ color: '#7d7c7c', fontWeight: 'bold' }}>
                                {t('userHomeView.minText')} {t('userHomeView.pending')}
                            </h6>
                            <button id="confirmAssetBtn" className="btn btn-green" type="submit">
                                {t('userHomeView.verifyBook')}
                            </button>
                            <button id="rejectAssetBtn" className="btn btn-red-outline mt-2" type="submit">
                                {t('userHomeView.rejectAssetTitle')}
                            </button>
                        </>
                    )}
                    {isDelivered(lending) && (
                        <>
                            <h6 style={{ color: '#7d7c7c', fontWeight: 'bold' }}>
                                {t('userHomeView.minText')} {t('userHomeView.inProgress')}
                            </h6>
                            <button id="returnAssetBtn" className="btn btn-green" type="submit">
                                {t('userHomeView.confirmReturn')}
                            </button>
                        </>
                    )}
                </div>
            )}
            {canReview && (
                <a className="btn btn-green mt-3" href={`/review/lender/${lending.id}`}>
                    {t('makeReview')}
                </a>
            )}
            {/* Include modal components here */}
            {/* <ReturnModal lending={lending} />
      <ConfirmModal lending={lending} />
      <RejectModal lending={lending} /> */}
        </div>
    );
}

export default LendedBooksOptions;
