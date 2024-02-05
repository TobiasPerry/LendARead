
import React, { useState, useEffect } from 'react';

const Snackbar = ({ message, error = true, duration = 4000 }) => {
    const [isVisible, setIsVisible] = useState(false);

    useEffect(() => {
        setIsVisible(true);

        const timer = setTimeout(() => {
            setIsVisible(false);
        }, duration);

        return () => clearTimeout(timer);
    }, [message, error, duration]);

    const handleClose = () => {
        setIsVisible(false);
    };


    return isVisible && (
        <div style={{ backgroundColor: !error ? '#53b453' :  '#dc3b4b', color: 'white', position: 'fixed', bottom: '20px', left: '20px', padding: '10px', borderRadius: '20px', paddingLeft: "25px", zIndex: "1000" }}>
            {message}
            <button onClick={handleClose} style={{backgroundColor: !error ? '#53b453' :  '#dc3b4b', marginLeft: "2px"}}>
                <i className="fa fa-times"></i>
            </button>
        </div>
    );
};

export default Snackbar;

