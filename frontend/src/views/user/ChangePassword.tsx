import {useContext, useEffect, useState} from 'react';
import { useTranslation } from 'react-i18next';
// @ts-ignore
import logo from '../../assets/logo-oscuro.png';
// @ts-ignore
import loginBg from '../../assets/login-bg.jpg';
import {Link, useLocation, useNavigate} from "react-router-dom";
import {AuthContext} from "../../contexts/authContext.tsx";
import "../styles/ChangePassword.css";
import {Helmet} from "react-helmet";
import Snackbar from "../../components/SnackBar.tsx";

const ChangePasswordView = () => {
    const { t } = useTranslation();
    const [email, setEmail] = useState('');
    const [verificationCode, setVerificationCode] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [passwordError, setPasswordError] = useState('');
    const [repeatNewPassword, setRepeatNewPassword] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [showSnackbar, setShowSnackbar] = useState({show: false, error: true, text: ""});

    const navigate = useNavigate()
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const emailParam = decodeURIComponent(searchParams.get('email'));

    const {handleChangePassword, handleForgotPassword} = useContext(AuthContext);

    useEffect(() => {
        if (emailParam) {
            setEmail(emailParam)
        }
    }, [emailParam]);

    const handleResendToken = async (e: any) => {
        await handleForgotPassword(email)
        setShowSnackbar({show: true, error: false, text: t('changePassword.resentToken')})
    }
    const handleSubmit = async (e: any) => {
        e.preventDefault();
        if (!checkPassword())
            return;

        setIsLoading(true);

        const res = await handleChangePassword(email, verificationCode, newPassword, repeatNewPassword);

        setIsLoading(false);

        if (res === "true") {
            navigate('/')
        } else {
            setShowSnackbar({show: true, error: true, text: res})
        }
    };

    const checkPassword = () => {
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{8,}$/;

        if (!newPassword.match(passwordRegex)) {
            setPasswordError(t('auth.passwordValidationError'));
            return false;
        }

        if (newPassword !== repeatNewPassword) {
            setPasswordError(t('auth.repeatPasswordValidationError'));
            return false;
        }

        setPasswordError('');
        return true;
    };


    return (
        <section className="vh-100">
            <Helmet>
                <title>{t("changePasswordTitle")}</title>
            </Helmet>
            <div className="container-fluid">
                <div className="row">
                    <div className="d-flex flex-column justify-content-center align-items-center text-black main-class col-sm-6">
                        <div className="px-5 ms-xl-4 mt-10">
                            <Link to="/">
                                <img src={logo} alt="Lend a read logo" style={{ width: '00px' }} />
                            </Link>
                        </div>



                        <div className="d-flex flex-column justify-content-center align-items-center h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">
                            <form onSubmit={handleSubmit} style={{ width: '23rem' }}>

                                <h2 className="mb-3 pb-3 text-center" style={{ letterSpacing: '1px' }}>{t('changePassword.title')}</h2>
                                <div className="m-3 text-center p-2" style={{ backgroundColor: 'rgba(255, 255, 255, 0.3)', borderRadius: '10px', maxWidth: '100%' }}>
                                    <i className="bi bi-info-circle my-2"></i>
                                    <p className="my-2">{t('changePassword.caption')}</p>
                                </div>
                                <div className="form-outline mb-4" style={{ width: '100%' }}>
                                    <label style={{ width: '100%' }}>
                                        {t('email')}
                                        <input
                                            className="form-control"
                                            type="text"
                                            id="email"
                                            placeholder={t('changePassword.enterEmail')}
                                            value={email}
                                            onChange={(e) => setEmail(e.target.value)}
                                            required
                                        />
                                    </label>
                                </div>

                                <div className="form-outline mb-4" style={{ width: '100%' }}>
                                    <label style={{ width: '100%' }}>
                                        {t('changePassword.verificationCode')}
                                        <input
                                            className="form-control"
                                            type="text"
                                            id="verificationCode"
                                            placeholder={t('changePassword.verificationCodePlaceholder')}
                                            value={verificationCode}
                                            onChange={(e) => setVerificationCode(e.target.value)}
                                            required
                                        />
                                    </label>
                                </div>

                                <div className="form-outline mb-4" style={{ width: '100%' }}>
                                    <label style={{ width: '100%' }}>
                                        {t('changePassword.newPassword')}
                                        <input
                                            className="form-control"
                                            type="password"
                                            id="newPassword"
                                            placeholder={t('changePassword.newPasswordPlaceholder')}
                                            value={newPassword}
                                            onChange={(e) => setNewPassword(e.target.value)}
                                            required
                                        />
                                        <div className="error">{passwordError}</div>
                                    </label>
                                </div>

                                <div className="form-outline mb-4" style={{ width: '100%' }}>
                                    <label style={{ width: '100%' }}>
                                        {t('changePassword.repeatNewPassword')}
                                        <input
                                            className="form-control"
                                            type="password"
                                            id="repeatNewPassword"
                                            placeholder={t('changePassword.repeatNewPasswordPlaceholder')}
                                            value={repeatNewPassword}
                                            onChange={(e) => setRepeatNewPassword(e.target.value)}
                                            required
                                        />
                                        <div className="error">{passwordError}</div>
                                    </label>
                                </div>

                                <div className="pt-1 mb-4 text-center">
                                    <input className="btn btn-light" type="submit" value={t('changePassword.changePasswordButton')} />
                                    {showSnackbar.show && <Snackbar message={showSnackbar.text} error={showSnackbar.error} />}
                                </div>

                                {/* Already Have Account Link */}
                                <div className="pt-1 mb-4 text-center">
                                    <div onClick={handleResendToken} className="resendTokenText">{t('changePassword.resendToken')}</div>
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

export default ChangePasswordView;
