import {useContext, useEffect, useState} from 'react';
import { useTranslation } from 'react-i18next';
// @ts-ignore
import logo from '../../assets/logo-oscuro.png';
// @ts-ignore
import loginBg from '../../assets/login-bg.jpg';
import {Link, useLocation} from "react-router-dom";
import {AuthContext} from "../../contexts/authContext.tsx";

const ChangePasswordView = () => {
    const { t } = useTranslation();
    const [email, setEmail] = useState('');
    const [verificationCode, setVerificationCode] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [repeatNewPassword, setRepeatNewPassword] = useState('');

    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const emailParam = searchParams.get('email');

    const {handleChangePassword} = useContext(AuthContext);

    useEffect(() => {
        if (emailParam) {
            setEmail(emailParam)
        }
    }, [emailParam]);

    const handleSubmit = async (e: any) => {
        e.preventDefault();
        const res = await handleChangePassword(email, verificationCode, newPassword, repeatNewPassword);
    };

    return (
        <section className="vh-100">
            <div className="container-fluid">
                <div className="row">
                    <div className="d-flex flex-column justify-content-center align-items-center text-black main-class col-sm-6">
                        <div className="px-5 ms-xl-4 mt-10">
                            <Link to="/">
                                <img src={logo} alt="Lend a read logo" style={{ width: '300px' }} />
                            </Link>
                        </div>

                        <div className="d-flex flex-column justify-content-center align-items-center h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">
                            <form onSubmit={handleSubmit} style={{ width: '23rem' }}>
                                <h2 className="mb-3 pb-3 text-center" style={{ letterSpacing: '1px' }}>{t('changePassword.title')}</h2>

                                <div className="form-outline mb-4" style={{ width: '100%' }}>
                                    <label style={{ width: '100%' }}>
                                        {t('email')}
                                        <input
                                            className="form-control"
                                            type="text"
                                            id="email"
                                            placeholder={t('changePassword.enterEmail')}
                                            value={email}
                                            onChange={(e) => setVerificationCode(e.target.value)}
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
                                    </label>
                                </div>

                                <div className="pt-1 mb-4 text-center">
                                    <input className="btn btn-light" type="submit" value={t('changePassword.changePasswordButton')} />
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
