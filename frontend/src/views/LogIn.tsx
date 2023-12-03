import { useTranslation } from 'react-i18next';
// @ts-ignore
import logo from '../assets/logo-claro.png'; // Adjust path as necessary
// @ts-ignore
import loginBg from '../assets/logo-claro.png';
import useLogin from "../hooks/useLogin"; // Adjust path as necessary

const LoginView = () => {
    const { t } = useTranslation();
    const { email, handleEmailChange, password, handlePasswordChange, handleLogin, loginError, rememberMe, setRememberMe } = useLogin();


    return (
        <section className="vh-100">
            <div className="container-fluid">
                <div className="row">
                    <div className="d-flex flex-column justify-content-center align-items-center text-black main-class col-sm-6">
                        <div className="px-5 ms-xl-4 mt-10">
                            <a href="/">
                                <img src={logo} alt="Lend a read logo" style={{ width: '300px' }} />
                            </a>
                        </div>

                        <div className="d-flex flex-column justify-content-center align-items-center h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">
                            <form onSubmit={handleLogin} style={{ width: '23rem' }}>
                                <h2 className="mb-3 pb-3 text-center" style={{ letterSpacing: '1px' }}>{t('auth.login')}</h2>

                                <div className="form-outline mb-4" style={{ width: '100%' }}>
                                    <label>
                                        {t('auth.email')}
                                        <input className="form-control" type="text" name="email" value={email} onChange={handleEmailChange} />
                                    </label>
                                </div>

                                <div className="form-outline mb-4">
                                    <label>
                                        {t('auth.password')}
                                        <input className="form-control" name="password" type="password" value={password} onChange={handlePasswordChange} />
                                    </label>
                                    {loginError && <label style={{ color: 'red' }}>{t('auth.loginError')}</label>}
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
                                    <a href="/register" className="text-muted">{t('auth.doNotHaveAccount')}</a>
                                </div>
                                <div className="pt-1 mb-4 text-center">
                                    <a href="/forgotPassword" className="text-muted">{t('auth.forgotPassword')}</a>
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
