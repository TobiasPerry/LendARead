import * as React from "react";
//@ts-ignore
import * as ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import './i18n';
import "./index.css";
import App from "./App";


const basePath = import.meta.env.VITE_APP_BASE_PATH || '/';


ReactDOM.createRoot(document.getElementById("root")!).render(
        <BrowserRouter basename={basePath}>
            <App />
        </BrowserRouter>
);
