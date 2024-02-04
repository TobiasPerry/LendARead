import "./styles/loadingAnimation.css";
import FaviconClaro from "../../public/static/favicon-claro.png"

const LoadingAnimationWhite = () => {
    return (
        <>
            <div className="loading-container" style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '100%', // Take full height of the parent
                width: '100%', // Take full width of the parent
                margin: "auto",
                top: 0,
                left: 0,
                marginTop: "50px"
            }}>
                <div style={{
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                }}>
                    <img style={{width: "200px" }} src={FaviconClaro} alt="Animated Image" className="fade-in-out"/>
                </div>
            </div>
        </>
    )
}

export default LoadingAnimationWhite;
