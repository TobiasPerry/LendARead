const UserProfile = ({ user, isCurrent }) => {
    const profilePicStyle = {
        width: '100px',
        height: '100px',
        borderRadius: '50%',
        objectFit: 'cover',
        border: '3px solid white',
        boxShadow: '0 4px 8px rgba(0,0,0,0.1)',
    };

    return (
        <div className="user-profile-card">
            <div className="profile-image-container">
                {/*<img*/}
                {/*    src={user.profilePhoto || '/static/images/user-placeholder.jpeg'}*/}
                {/*    alt={`${user.name} profile`}*/}
                {/*    style={profilePicStyle}*/}
                {/*/>*/}
            </div>
            <h3 className="mt-2">{user.name}</h3>
            {/* Display the rating and lender/borrower status */}
            {/* ... */}
        </div>
    );
};

export default UserProfile;