import * as React from "react";
//@ts-ignore
import * as ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import './i18n';
import "./index.css";
import App from "./App";




ReactDOM.createRoot(document.getElementById("root")!).render(
    <React.StrictMode>
        <BrowserRouter>
            <App />
        </BrowserRouter>
    </React.StrictMode>
);