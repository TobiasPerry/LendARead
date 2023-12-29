import React from "react";
import {Link} from "react-router-dom";

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
            <div style={heroStyle}>
                <div style={imageContainer}>
                    <img style={imgStyle} src="/broken_lendaread.png" alt="Broken book"></img>
                </div>
                <div style={textContainer}>
                    <h1 className="font-primary text-primary">Titulo</h1>
                    <p className="font-primary text-secondary">subtitulo</p>
                    <Link to="/">Boton</Link>
                </div>
            </div>
        </>
    )
}