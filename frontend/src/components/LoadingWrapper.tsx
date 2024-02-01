import LoadingAnimation from "./LoadingAnimation.tsx";
import {Helmet} from "react-helmet";
import LoadingAnimationWhite from "./LoadingAnimationWhite.tsx";

const LoadingWrapper = ({ isLoading, children, documentTitle, isWhiteAnimation }) => {

    const Animation = isWhiteAnimation ? <LoadingAnimationWhite/> : <LoadingAnimation/>

    return (
        <>
            { isLoading ?
                Animation
                :
                <>
                    <Helmet>
                        <title>{documentTitle}</title>
                    </Helmet>
                    {children}
                </>
            }
        </>
    )
}

export default LoadingWrapper;