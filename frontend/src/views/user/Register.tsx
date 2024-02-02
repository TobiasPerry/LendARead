import {useContext, useState} from 'react';
import { useTranslation } from 'react-i18next';
import {Link, useNavigate} from 'react-router-dom';
// @ts-ignore
import logo from '../../assets/logo-oscuro.png';
// @ts-ignore
import {Helmet} from "react-helmet";

// @ts-ignore
import loginBg from '../../assets/login-bg.jpg';
import useRegister from "../../hooks/users/useRegister.ts";
import {AuthContext} from "../../contexts/authContext.tsx";

interface FormErrors {
    email: string,
    password: string,
    repeatPassword: string,
    name: string
}
const RegisterView = () => {
    const { t } = useTranslation();
    const navigate = useNavigate();

    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [repeatPassword, setRepeatPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState("");

    const {register} = useRegister();
    const {login } = useContext(AuthContext);

    const [formErrors, setFormErrors] = useState<FormErrors>({
        email: "",
        password: "",
        repeatPassword: "",
        name:""
    });

    const validateForm = () => {
        let errors = { email: "",
            password: "",
            repeatPassword: "",
            name:"" };
        let hasErrors : boolean = false;
        // Validate email
        if (!email.match(/^\S+@\S+\.\S+$/) || email.length < 3 || email.length > 100) {
            errors.email = t('auth.emailValidationError');
            hasErrors = true
        }

        // Validate password
        if (!password.match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{8,}$/)) {
            errors.password = t('auth.passwordValidationError');
            hasErrors = true
        }

        // Validate repeatPassword
        if (password !== repeatPassword) {
            errors.repeatPassword = t('auth.repeatPasswordValidationError');
            hasErrors = true
        }

        // Validate name
        if (name.length < 3 || name.length > 100) {
            errors.name = t('auth.nameValidationError');
            hasErrors = true
        }

        // Validate telephone (Add your own validation logic based on your requirements)

        return {errors, hasErrors};
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const {errors, hasErrors} = validateForm();

        if (!hasErrors) {
            const {successfulRegister, errorMessage} = await register(email, password, repeatPassword, name);
            if (successfulRegister) {
                await login(email, password)
                navigate('/');
            }else{
                setErrorMessage(errorMessage);
            }
        } else {
            setFormErrors(errors);
        }
    };

    return (
        <section className="vh-100">
            <Helmet>
                <title>{t("register")}</title>
            </Helmet>
            <div className="container-fluid">
                <div className="row">
                    <div className="d-flex flex-column justify-content-center align-items-center text-black main-class col-sm-6">


                        <div className="d-flex flex-column justify-content-center align-items-center h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">
                            <form onSubmit={handleSubmit}>
                                <h2 className="mb-3 pb-3 text-center" style={{ letterSpacing: '1px' }}>{t('auth.register')}</h2>

                                {/* Name Field */}
                                <div className="form-outline mb-4">
                                    <label className="form-label" htmlFor="name" style={{ display: 'block', textAlign: 'left' }}>
                                        {t('auth.username')}
                                    </label>
                                    <input className="form-control" type="text" id="name" name="name" value={name} onChange={(e) => setName(e.target.value)} required />
                                    {formErrors.name && <div className="error-message">{formErrors.name}</div>}
                                </div>

                                {/* Email Field */}
                                <div className="form-outline mb-4">
                                    <label className="form-label" htmlFor="email" style={{ display: 'block', textAlign: 'left' }}>
                                        {t('auth.email')}
                                    </label>
                                    <input className="form-control" type="email" id="email" name="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                                    {formErrors.email && <div className="error-message">{formErrors.email}</div>}
                                </div>

                                {/* Password Field */}
                                <div className="form-outline mb-4">
                                    <label className="form-label" htmlFor="password" style={{ display: 'block', textAlign: 'left' }}>
                                        {t('auth.password')}
                                    </label>
                                    <input className="form-control" name="password" type="password" id="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
                                    {formErrors.password && <div className="error-message">{formErrors.password}</div>}
                                </div>

                                {/* Repeat Password Field */}
                                <div className="form-outline mb-4">
                                    <label className="form-label" htmlFor="repeatPassword" style={{ display: 'block', textAlign: 'left' }}>
                                        {t('auth.repeatPassword')}
                                    </label>
                                    <input className="form-control" name="repeatPassword" type="password" id="repeatPassword" value={repeatPassword} onChange={(e) => setRepeatPassword(e.target.value)} required />
                                    {formErrors.repeatPassword && <div className="error-message">{formErrors.repeatPassword}</div>}
                                </div>

                                {/* Register Button */}
                                <div className="form-outline mb-4 text-center">
                                    <input className="btn btn-light" type="submit" value={t('auth.registerBtn')} />
                                    <br/>
                                    <p className="error">{errorMessage}</p>
                                </div>

                                {/* Already Have Account Link */}
                                <div className="pt-1 mb-4">
                                    <Link to="/login" className="text-muted">{t('auth.alreadyHaveAccount')}</Link>
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

