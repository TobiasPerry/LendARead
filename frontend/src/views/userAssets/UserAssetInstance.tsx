import BookDetails from "../../components/user/BookDetails.tsx";
import BookStatus from "../../components/user/BookStatus.tsx";
import BookOptions from "../../components/user/BookOptions.tsx";
import {useLocation, useNavigate, useParams} from "react-router-dom";
import "../styles/userBookDetails.css"
import {useTranslation} from "react-i18next";
import useUserAssetInstance from "../../hooks/assetInstance/useUserAssetInstance.ts";
import {useEffect} from "react";
import MyBooksOptions from "../../components/user/MyBooksOptions.tsx";
import LendedBooksOptions from "../../components/user/LendedBooksOptions.tsx";
const UserAssetInstance = () => {

    const navigate = useNavigate();
    const location = useLocation();
    const { id } = useParams();
    const { t } = useTranslation();
    const {
        assetDetails,
        fetchUserAssetDetails,
        isLending,
        hasActiveLendings,
        editAssetVisbility,
        editAssetReservability,
        deleteAssetInstance,
        editAsset
    } = useUserAssetInstance(location, id)


    const handleDelete = async (asset: any) => {
        await deleteAssetInstance(asset)
        handleBackClick()
    }
    const handleBackClick = () => {
        navigate("/userHome")
    }

    useEffect(() => {
        fetchUserAssetDetails().then()
    }, [])

    //need to make api post to review service, this makes a link to that page
    const canReview = false

    return (
        <div className="main-container" style={{ padding: '2rem' }}>
            <div className="d-flex back-click flex-row align-items-center m-3" onClick={handleBackClick}>
                <i className="fas fa-arrow-left mb-1"></i>
            <h3 className="ms-3">
                {t('my_books')}
            </h3>
            </div>
            <div className="content-container" style={{ display: 'flex', flexDirection: 'row', gap: '1rem', marginBottom: '1rem' }}>
                <BookDetails data={assetDetails}/>
                <div className="loan-container" style={{ flex: 1, display: 'flex', flexDirection: 'column', gap: '1rem' }}>
                    <BookStatus />
                     <MyBooksOptions
                         asset={assetDetails}
                         haveActiveLendings={hasActiveLendings}
                         handleDelete={handleDelete}
                         editAssetReservability={editAssetReservability}
                         editAssetVisbility={editAssetVisbility}
                         editAsset={editAsset}
                     />
                    {/*{isLending && <LendedBooksOptions lending={assetDetails} canReview={canReview} /> }*/}
                </div>
            </div>
        </div>
    );
};

export default UserAssetInstance;