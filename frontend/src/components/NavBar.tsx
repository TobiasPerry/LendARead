//import {useTranslation} from "react-i18next";
import './styles/navBar.css';
import './styles/searchBar.css';
import {Link, useLocation, useNavigate} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {useContext, useState} from "react";
import {AuthContext} from "../contexts/authContext.tsx";
import logoClaro from "../assets/logo-claro.png";

// @ts-ignore
function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
}

export default function NavBar() {

    const { t, i18n } = useTranslation();
    const {isLoggedIn, logout, smallUserImage, user} = useContext(AuthContext)
    const [language, setLanguage] = useState('en');

    const navigate = useNavigate()
    const location = useLocation()
    const showSearchBar = location.pathname !== '/discovery'

    const toggleLanguage = () => {
        const next_language = language === 'en' ? 'es' : 'en';
        i18n.changeLanguage(next_language);
        setLanguage(next_language);
    };
    const navigation = [
        { name: t('Explore'), href: '/discovery', current: true },
        { name: t('Lend book'), href: '/', current: false },
    ]

    const changeLanguage = (lng: string) => {
        i18n.changeLanguage(lng);
    };

    const handleLogout = () => {
        logout()
        navigate('/')
    }

    const handleSearch = (event) => {
        if (event.key === 'Enter'){
            navigate(`/discovery?search=${event.target.value}`)
            event.target.value = ""
        }
    }

    return (
        <>
            <nav className="navbar navbar-expand-lg" style={{backgroundColor: '#111711'}} data-bs-theme="dark">
                <div className="container-fluid">
                    <Link to="/" className="nav-icon">
                        <img src={logoClaro} alt="Lend a read logo" style={{width: '150px'}}/>
                    </Link>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse d-lg-flex justify-content-lg-end align-items-center" id="navbarSupportedContent">
                        <ul className="navbar-nav mb-2 mb-lg-0">
                            {
                                showSearchBar ? (
                                    <div className="form mx-3" style={{marginBlockEnd: '0'}}>

                                        <i className="fa fa-search fa-search-class" ></i>
                                        <input type="text" className="form-input" name="search"
                                               style={{marginLeft: '4px'}}
                                               placeholder={t('navbar.searchbar.placeholder')} id="nav-bar-search-bar"
                                               onKeyPress={handleSearch}
                                        />

                                    </div>
                                ) : (
                                    <>
                                    </>
                                )
                            }

                            <li className="nav-item  d-flex align-items-center">
                                <Link className="nav-link navItem" id="home" aria-current="page" to="/discovery">{t('navbar.explore')}</Link>
                            </li>
                            <li className="nav-item  d-flex align-items-center">
                                <Link className="nav-link navItem"  id="addAsset" aria-current="page" to="/addAssetView">{t('navbar.lend')}</Link>
                            </li>

                            { !isLoggedIn && (
                                <li className="nav-item d-flex align-items-center">
                                    <Link className="nav-link navItem" id="userHome"  aria-current="page" to="/login" data-testid="login-btn" >
                                        {t('navbar.login')}
                                    </Link>
                                </li>
                            )}
                            { isLoggedIn && (
                                <>
                                    <li className="nav-item  d-flex align-items-center">
                                        <Link className="nav-link navItem" id="home" aria-current="page" to="/userAssets">{t('navbar.my_books')}</Link>
                                    </li>
                                    <li className="nav-item d-flex align-items-center">
                                        <Link className="nav-link navItem" id="userView"  aria-current="page" to={`/user/${user}`} data-testid="profile-btn">

                                        <span className="navbar-brand">
                                            <img src={smallUserImage} className="rounded-circle" width="30" height="30" alt="logo" data-testid="user-img"/>
                                        </span>

                                        </Link>
                                    </li>
                                    <li className="nav-item d-flex align-items-center">
                                        <button className="nav-link navItem" id="logout" aria-current="page" onClick={handleLogout} data-testid="logout-btn">
                                            <i className="fas fa-sign-out-alt fa-x" ></i>
                                        </button>
                                    </li>
                                </>
                            )}
                        </ul>
                    </div>
                </div>
            </nav>
        </>
    )
}
