import React, {useContext, useState} from 'react';
import { useTranslation } from 'react-i18next';
// @ts-ignore
import logo from '../../assets/logo-oscuro.png';
// @ts-ignore
import loginBg from '../../assets/login-bg.jpg';
import {Link, useNavigate} from "react-router-dom";
import {AuthContext} from "../../contexts/authContext.tsx";
import {Helmet} from "react-helmet";

const ForgotPasswordView = () => {
    const { t } = useTranslation();
    const {handleForgotPassword} = useContext(AuthContext);
    const navigate = useNavigate()
    const [email, setEmail] = useState('');
    const [error, setError] = useState(false);

    const handleSubmit = async () => {
       setError(false)
       const validEmail = await handleForgotPassword(email)

       if(validEmail)
           navigate(`/changePassword?email=${encodeURIComponent(email)}`);
        else
            setError(true)
    }
    return (
        <section className="vh-100">
            <Helmet>
                <title>{t("forgotPassword")}</title>
            </Helmet>
            <div className="container-fluid">
                <div className="row">
                    <div className="d-flex flex-column justify-content-center align-items-center text-black main-class col-sm-6">
                        <div className="px-5 ms-xl-4 mt-10">
                            <Link to="/">
                                <img src={logo} alt="Lend a read logo" style={{ width: '300px' }} />
                            </Link>
                        </div>

                        <div className="d-flex flex-column justify-content-center align-items-center h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">
                            <div style={{ width: '23rem' }}>
                                <h2 className="mb-3 pb-3 text-center" style={{ letterSpacing: '1px' }}>{t('forgotpassword.title')}</h2>

                                <div className="form-outline mb-4" style={{ width: '100%' }}>
                                    <label style={{ width: '100%' }}>
                                        {t('auth.email')}
                                        <input
                                            className="form-control"
                                            type="text"
                                            id="email"
                                            placeholder="Email"
                                            value={email}
                                            onChange={(e) => setEmail(e.target.value)}
                                            required
                                        />
                                        {error && <div className="error">{t("errorPasswordEmail")}</div>}
                                    </label>
                                </div>

                                <div className="pt-1 mb-4 text-center">
                                    <button className="btn btn-light" onClick={handleSubmit}> {t('forgotpassword.changePasswordButton')} </button>
                                </div>

                            </div>
                        </div>
                    </div>

                    <div className="col-sm-6 px-0 d-none d-sm-block">
                        <img src={loginBg} alt="Login image" className="w-100 vh-100" style={{ objectFit: 'cover', objectPosition: 'left' }} />
                    </div>
                </div>
            </div>
        </section>
    );
};

export default ForgotPasswordView;
