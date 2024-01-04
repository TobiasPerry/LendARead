import React from "react";
import {Link} from "react-router-dom";
import styles from './styles/error.css'

export default function NotFound(){

    const heroStyle: React.CSSProperties = {
        width: '100%',
        paddingTop: '82px',
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100vh",
    };

    const imageContainer: React.CSSProperties = {
        flex: 1,
        textAlign: "center",
        order: "1",
        padding: "0 20px",
    };

    const imgStyle: React.CSSProperties = {
        width: "600px",
    };

    const textContainer: React.CSSProperties ={
        flex: 1,
        textAlign: "center",
        padding: "20px",
    }

    return(
        <>
            {/*<section id="hero" style="background-color: #D0DCD0; padding-bottom: 100px; ">*/}
            {/*    <div className="container" style="text-align: start">*/}

            {/*        <div className="image-container">*/}
            {/*            <img src="" className="img-fluid animated"*/}
            {/*                 alt="broken book" style="width: 600px"/>*/}
            {/*        </div>*/}

            {/*        <div className="text-container">*/}
            {/*            <h1 className="mb-4">*/}
            {/*                /!*<c:out value="${errorTitle}"/>*!/*/}
            {/*            </h1>*/}
            {/*            <p className="mb-4">*/}
            {/*                /!*<c:out value="${errorSubtitle}"/>*!/*/}
            {/*            </p>*/}
            {/*            <a href="/" className="btn btn-primary mt-3" style="background-color:*/}
            {/*            #2B3B2B;border-color: #2B3B2B">*/}
            {/*            /!*<spring:message code="403.button"/>*!/*/}
            {/*        </a>*/}
            {/*    </div>*/}

            {/*</div>*/}
            {/*</section>*/}
        </>
)
}