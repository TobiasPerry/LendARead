import {useContext, useRef, useState} from "react";
import {AuthContext} from "../../contexts/authContext.tsx";
import {useTranslation} from "react-i18next";
import {Link} from "react-router-dom";

const UserProfile = () => {
    const { t } = useTranslation();
    const {userDetails, userImage, uploadUserImage} = useContext(AuthContext);
    const fileInputRef = useRef(null);

    const [tempImage, setTempImage] = useState("");
    const [storeImage, setStoreImage] = useState(null);
    const [showConfirmation, setShowConfirmation] = useState(false);

    const handleUploadImage = async (e) => {
        if (!e.target.files || !e.target.files[0]) return;

        const file = e.target.files[0];
        setStoreImage(file)
        const reader = new FileReader();

        reader.onloadend = () => {
            // @ts-ignore
            setTempImage(reader.result);
            setShowConfirmation(true);
        };

        reader.readAsDataURL(file);
    };

    const handleSave = async () => {
        try {
            const res = await uploadUserImage(storeImage);
        } catch (e) {

        }
        setShowConfirmation(false);
    };

    const handleDiscard = () => {
        setTempImage("");
        setShowConfirmation(false);
    };

    const handleClick = () => {
        fileInputRef.current.click();
    };

    return (
        <div>
            <div className="position-relative">
                <div className="user-profile-cell">
                    <img className="user-profile-picture" src={tempImage || userImage} alt="user profile" />
                        <div>
                            {!showConfirmation &&
                            <div className="user-change-picture-container" onClick={handleClick}>
                                <i className="fas fa-solid fa-camera"></i>
                            </div>
                            }
                            {showConfirmation && (
                                <>
                                    <div className="user-change-picture-container" style={{backgroundColor: "#53b453"}} onClick={handleSave}>
                                        <i className="fas fa-thumbs-up fa-solid "></i>
                                    </div>
                                    <div className="user-change-picture-container discard-image" onClick={handleDiscard}>
                                        <i className="fas fa-thumbs-down fa-solid "></i>
                                    </div>
                                </>
                            )}
                            <input
                                type="file"
                                ref={fileInputRef}
                                onChange={handleUploadImage}
                                style={{ display: 'none' }}
                            />
                        </div>
                </div>
            </div>
            <div className="d-flex flex-row">
            <div className="user-info-profile">
                <h1>{userDetails.userName}</h1>
                <p className="grey-text">
                    <>
                        { userDetails.role === "LENDER" &&
                            <>
                                {t('userProfile.lender')}
                                <span className="user-role-stars">
                                        {userDetails.ratingAsLender <= 0 ? (
                                            "-.- ") : (
                                            Math.round(userDetails.ratingAsLender * 10) / 10
                                        )}
                                    ★ </span>
                            </>
                        }
                    </>
                    <>
                        {t('userProfile.borrower')}
                        <span className="user-role-stars">
                            {userDetails.ratingAsBorrower <= 0 ? (
                                "-.- ") : (
                                Math.round(userDetails.ratingAsBorrower * 10) / 10
                            )}
                        ★</span>
                    </>
                </p>
            </div>
                { userDetails.role === "LENDER" &&
                <Link  to={"/locations"} style={{width: "150px", padding: "10px 5px", height: "50px", margin: "auto", marginTop: "30px"}}>
                <button>
                    {t("userProfile.my_locations")}
                </button>
                </Link>
            }
            </div>
        </div>
    );
};

export default UserProfile;