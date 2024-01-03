import  { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
// @ts-ignore
import logo from '../../assets/logo-oscuro.png';
// @ts-ignore

import loginBg from '../../assets/login-bg.jpg';
import useRegister from "../../hooks/users/useRegister.ts";

const RegisterView = () => {
    const { t } = useTranslation();
    const navigate = useNavigate();

    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [repeatPassword, setRepeatPassword] = useState('');
    const { register} = useRegister();

    const handleSubmit = async (e: any) => {
        e.preventDefault();
        const successfulRegister = await register(email, password, repeatPassword, name)
        if(successfulRegister)
            navigate('/');
           //login form?
    };

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
                            <form onSubmit={handleSubmit}>
                                <h2 className="mb-3 pb-3 text-center" style={{ letterSpacing: '1px' }}>{t('auth.register')}</h2>

                                <div className="form-outline mb-4">
                                    <label className="form-label" style={{ display: 'block', textAlign: 'left' }}>
                                        {t('auth.username')}
                                    </label>
                                    <input className="form-control" type="text" name="name" value={name} onChange={(e) => setName(e.target.value)} required />
                                </div>

                                <div className="form-outline mb-4">
                                    <label className="form-label" style={{ display: 'block', textAlign: 'left' }}>
                                        {t('auth.email')}
                                    </label>
                                    <input className="form-control" type="email" name="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                                </div>

                                <div className="form-outline mb-4">
                                    <label className="form-label" style={{ display: 'block', textAlign: 'left' }}>
                                        {t('auth.password')}
                                    </label>
                                        <input className="form-control" name="password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
                                </div>

                                <div className="form-outline mb-4">
                                    <label className="form-label" style={{ display: 'block', textAlign: 'left' }}>

                                    {t('auth.repeatPassword')}
                                    </label>
                                        <input className="form-control" name="repeatPassword" type="password" value={repeatPassword} onChange={(e) => setRepeatPassword(e.target.value)} required />

                                </div>

                                <div className="form-outline mb-4 text-center">
                                    <input className="btn btn-light" type="submit" value={t('auth.registerBtn')} />
                                </div>

                                <div className="pt-1 mb-4">
                                    <a href="/login" className="text-muted">{t('auth.alreadyHaveAccount')}</a>
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

export default RegisterView;

