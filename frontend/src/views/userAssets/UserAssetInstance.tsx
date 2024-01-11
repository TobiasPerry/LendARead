import BookDetails from "../../components/user/BookDetails.tsx";
import BookStatus from "../../components/user/BookStatus.tsx";
import BookOptions from "../../components/user/BookOptions.tsx";
const UserAssetInstance = () => {
    return (
        <div className="main-container" style={{ padding: '2rem' }}>
            <h1 className="text-center" style={{ marginBottom: '2rem' }}>Library Loan System</h1>
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