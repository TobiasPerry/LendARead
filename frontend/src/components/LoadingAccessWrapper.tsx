import LoadingAnimation from "./LoadingAnimation.tsx";
import NotAllowed from "../views/NotAllowed.tsx";
import React from "react";
import {Helmet} from "react-helmet";
const LoadingAccessWrapper = ({children, isLoading, isOwner, documentTitle}) => {

    return (
        <>
            { isLoading ?
                <LoadingAnimation/>
                :
                <>
                    { !isOwner && !isLoading ?
                        <NotAllowed/> :
                        <>
                            <Helmet>
                                <title>{documentTitle}</title>
                            </Helmet>
                            {children}
                        </>
                    }
                </>
            }
        </>
    )
}

export default LoadingAccessWrapper;