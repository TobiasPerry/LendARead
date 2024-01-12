import React from 'react';
import { useTranslation } from 'react-i18next';

function LendedBooksOptions({ lending, canReview }) {
    const { t } = useTranslation();

    return (
        <div style={{
            backgroundColor: '#f0f5f0',
            padding: '100px',
            borderRadius: '20px',
            padding: '20px',
            width: '50%'
        }}>
            <h3>Lended Book actions</h3>
            {/*{!lending.active.getIsRejected && !lending.active.getIsFinished && (*/}
                <div className="options-menu">
                    {/*{lending.active.getIsActive && (*/}
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
                    {/*)}*/}
                    {/*{lending.active.getIsDelivered && (*/}
                        <>
                            <h6 style={{ color: '#7d7c7c', fontWeight: 'bold' }}>
                                {t('userHomeView.minText')} {t('userHomeView.inProgress')}
                            </h6>
                            <button id="returnAssetBtn" className="btn btn-green" type="submit">
                                {t('userHomeView.confirmReturn')}
                            </button>
                        </>
                    {/*)}*/}
                </div>
            {/*)}*/}
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
