import BookDetails from "../../components/user/BookDetails.tsx";
import BookStatus from "../../components/user/BookStatus.tsx";
import BookOptions from "../../components/user/BookOptions.tsx";
import {useNavigate} from "react-router-dom";
import "../styles/userBookDetails.css"
const UserAssetInstance = () => {

    const navigate = useNavigate();

    return (
        <div className="main-container" style={{ padding: '2rem' }}>
            <div className="d-flex back-click flex-row align-items-center m-3" onClick={() => navigate("/userHome")}>
                <i className="fas fa-arrow-left mb-1"></i>
            <h3 className="ms-3">
                Library Loan System
            </h3>
            </div>
            <div className="content-container" style={{ display: 'flex', flexDirection: 'row', gap: '1rem', marginBottom: '1rem' }}>
                <div className="book-details-container" style={{ flex: 1, display: 'flex', flexDirection: 'column', gap: '1rem' }}>
                    <BookDetails />
                </div>
                <div className="loan-container" style={{ flex: 1, display: 'flex', flexDirection: 'column', gap: '1rem' }}>
                    <BookStatus />
                    <BookOptions />
                </div>
            </div>
        </div>
    );
};

export default UserAssetInstance;