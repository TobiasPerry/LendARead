import { useTranslation } from 'react-i18next';
// @ts-ignore
import logo from '../../assets/logo-claro.png'; // Adjust path as necessary
// @ts-ignore
import loginBg from '../../assets/login-bg.jpg';
import {Link, useNavigate} from "react-router-dom";
import {useContext, useState} from "react";
import {AuthContext} from "../../contexts/authContext.tsx";
import {Helmet} from "react-helmet";

const LoginView = ({redirect}) => {
    const { t } = useTranslation();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [loginError, setLoginError] = useState(false);
    const [rememberMe, setRememberMe] = useState(false);
    const { login } = useContext(AuthContext);
    const handleEmailChange = (e: any) => {
        setEmail(e.target.value);
    };

    const handleLogin = async (e: any) => {
        e.preventDefault()
        const loginSuccess = await login(email, password, rememberMe)
        if(loginSuccess) {
            if (redirect) navigate(-1)
        }
        else {
           setLoginError(true)
        }
    }
    const handlePasswordChange = (e: any) => {
        setPassword(e.target.value);
    };


    const navigate = useNavigate();


    return (
        <section className="vh-100">
            <Helmet>
                <title>{t("login")}</title>
            </Helmet>
            <div className="container-fluid">
                <div className="row">
                    <div className="d-flex flex-column justify-content-center align-items-center text-black main-class col-sm-6">

                        <div className="d-flex flex-column justify-content-center align-items-center h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">
                            <form onSubmit={handleLogin} style={{ width: '23rem', margin: '0 auto' }}>
                                <h2 className="mb-3 pb-3 text-center" style={{ letterSpacing: '1px' }}>{t('auth.login')}</h2>

                                <div className="form-outline mb-4">
                                    <label className="form-label" style={{ display: 'block', textAlign: 'left' }}>
                                    {t('auth.email')}
                                    </label>
                                    <input className="form-control" type="text" name="email" value={email} onChange={handleEmailChange} />
                                </div>

                                <div className="form-outline mb-4">
                                    <label className="form-label" style={{ display: 'block', textAlign: 'left' }}>
                                        {t('auth.password')}
                                    </label>
                                    <input className="form-control" name="password" type="password" value={password} onChange={handlePasswordChange} />
                                    {loginError && <p className="error">{t('auth.loginError')}</p>}
                                </div>

                                <div className="form-outline mb-4 text-center">
                                    <label className="form-check-label">
                                        <input className="form-check-input" name="rememberme" type="checkbox" checked={rememberMe} onChange={(e) => setRememberMe(e.target.checked)} />
                                        {t('auth.rememberMe')}
                                    </label>
                                </div>

                                <div className="pt-1 mb-4 text-center">
                                    <input className="btn btn-light" type="submit" value={t('auth.logInBtn')} />
                                </div>

                                <div className="pt-1 mb-4 text-center">
                                    <Link to="/register" className="text-muted">{t('auth.doNotHaveAccount')}</Link>
                                </div>
                                <div className="pt-1 mb-4 text-center">
                                    <Link to="/forgotPassword" className="text-muted">{t('auth.forgotPassword')}</Link>
                                </div>
                            </form>
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

export default LoginView;
