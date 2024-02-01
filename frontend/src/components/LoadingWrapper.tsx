import LoadingAnimation from "./LoadingAnimation.tsx";
import {Helmet} from "react-helmet";

const LoadingWrapper = ({ isLoading, children, documentTitle }) => {

    return (
        <>
            { isLoading ?
                <LoadingAnimation/>
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