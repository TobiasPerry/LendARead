import "./styles/loadingAnimation.css";

const LoadingAnimationWhite = () => {
    return (
        <>
            <div className="loading-container" style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '100%', // Take full height of the parent
                width: '100%', // Take full width of the parent
                backgroundColor: 'white', // Set background color to white
                margin: "auto",
                top: 0,
                left: 0,
                marginTop: "50px"
            }}>
                <div className="main-class" style={{
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    backgroundColor: 'white', // Set background color to white
                }}>
                    <img style={{width: "200px" }}src="/static/favicon-claro.png" alt="Animated Image" className="fade-in-out"/>
                </div>
            </div>
        </>
    )
}

export default LoadingAnimationWhite;
