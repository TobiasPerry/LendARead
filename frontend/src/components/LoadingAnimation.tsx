import "./styles/loadingAnimation.css"
import FaviconClaro from "../../public/static/favicon-claro-bg.ico"

const LoadingAnimation = () => {
    return (
        <>
            <div className="main-class" style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '100vh'
            }}>
                <img src={FaviconClaro} alt="Animated Image" className="fade-in-out"/>
            </div>
        </>
    )
}

export default LoadingAnimation;
