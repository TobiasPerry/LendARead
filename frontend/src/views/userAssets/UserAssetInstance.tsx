import BookDetails from "../../components/userAssets/BookDetails.tsx";
import BookStatus from "../../components/userAssets/BookStatus.tsx";
import BorrowedBookOptions from "../../components/userAssets/BorrowedBookOptions.tsx";
import {useLocation, useNavigate, useParams} from "react-router-dom";
import "../styles/userBookDetails.css"
import {useTranslation} from "react-i18next";
import useUserAssetInstance from "../../hooks/assetInstance/useUserAssetInstance.ts";
import React, {useEffect} from "react";
import MyBooksOptions from "../../components/userAssets/MyBooksOptions.tsx";
import LendedBooksOptions from "../../components/userAssets/LendedBooksOptions.tsx";
import MyBookActiveLendings from "../../components/userAssets/MyBookActiveLendings.tsx";
import LoadingAccessWrapper from "../../components/LoadingAccessWrapper.tsx";
import LendingsReviews from "../../components/userAssets/LendingsReviews.tsx";
import Snackbar from "../../components/SnackBar.tsx";

const UserAssetInstance = () => {

    const navigate = useNavigate();
    const location = useLocation();
    const { id } = useParams();
    const { t } = useTranslation();
    const {
        assetDetails,
        fetchUserAssetDetails,
        hasActiveLendings,
        deleteAssetInstance,
        state,
        isLoading,
        isOwner,
        error,
        hasLendingsNotFinished
    } = useUserAssetInstance(location, id)


    const handleDelete = async (asset: any) => {
        await deleteAssetInstance(asset)
        handleBackClick()
    }
    const handleBackClick = () => {
        navigate(`/userAssets?table=${state}_books`)
    }

    useEffect(() => {
        fetchUserAssetDetails().then()
    }, [id])

    useEffect(() => {
        if(error.state && error.text === t("errors.failedFetchingAssetDetails"))
            navigate("/notfound")
    }, [error])

    return (
        <LoadingAccessWrapper isLoading={isLoading} documentTitle={assetDetails.title} isOwner={isOwner}>
            <div className="main-class" style={{height: "100%", margin: 0}}>
                <div style={{ padding: "40px"}}>
                    <div style={{backgroundColor: "#f9f9f9", padding: "30px", borderRadius: "20px"}}>
                <div className="d-flex back-click flex-row align-items-center m-3" onClick={handleBackClick}>
                    <i className="fas fa-arrow-left mb-1"></i>
                    <h3 className="ms-3">
                        {state === "owned" ? t('my_books') : state === "lended" ? t('lended_books') : t('borrowed_books')}
                    </h3>
                </div>
                <div className="content-container"
                     style={{display: 'flex', flexDirection: 'row', gap: '1rem', marginBottom: '1rem'}}>
                    <BookDetails data={assetDetails}/>
                    <div className="loan-container"
                         style={{flex: 1, display: 'flex', flexDirection: 'column', gap: '1rem'}}>
                        <BookStatus
                            asset={assetDetails}
                            state={state}
                        />
                        {state === "owned" && <MyBooksOptions
                            asset={assetDetails}
                            haveActiveLendings={hasActiveLendings}
                            handleDelete={handleDelete}
                            fetchUserAssetDetails={fetchUserAssetDetails}
                            hasLendingsNotFinished={hasLendingsNotFinished}
                        />}
                        {state === "lended" && <LendedBooksOptions
                            asset={assetDetails}
                            fetchUserAssetDetails={fetchUserAssetDetails}/>}
                        {state === "borrowed" && <BorrowedBookOptions
                            asset={assetDetails}
                            fetchUserAssetInstance={fetchUserAssetDetails}/> }
                    </div>

                </div>
                {state === "owned" && <MyBookActiveLendings asset={assetDetails} /> }
                {(state === "lended" || state === "borrowed") && <LendingsReviews asset={assetDetails} />}
                    </div>
                </div>
            </div>
            {error.state && <Snackbar message={error.text} />}
        </LoadingAccessWrapper>
    );
};

export default UserAssetInstance;