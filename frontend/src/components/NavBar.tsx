//import {useTranslation} from "react-i18next";
import './styles/navBar.css';
import './styles/searchBar.css';

// @ts-ignore
function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
}

export default function NavBar() {

    // const { t, i18n } = useTranslation();
    //const [language, setLanguage] = useState('en');

    // const toggleLanguage = () => {
    //     const next_language = language === 'en' ? 'es' : 'en';
    //     i18n.changeLanguage(next_language);
    //     setLanguage(next_language);
    // };
    // const navigation = [
    //     { name: t('Explore'), href: '/discovery', current: true },
    //     { name: t('Lend book'), href: '/', current: false },
    // ]

    // const changeLanguage = (lng: string) => {
    //     i18n.changeLanguage(lng);
    // };

    return (
        <>
            <nav className="navbar navbar-expand-lg" style={{backgroundColor: '#111711'}} data-bs-theme="dark">
                <div className="container-fluid">
                    <a href="/" className="nav-icon"><img src="../../public/logo-claro.png" alt="Lend a read logo" style={{width: '150px'}}/></a>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse d-lg-flex justify-content-lg-end align-items-center" id="navbarSupportedContent">
                        <ul className="navbar-nav mb-2 mb-lg-0">
                            <div className="form mx-3" style={{marginBlockEnd: '0'}}>
                                
                                
                                <i className="fa fa-search fa-search-class" ></i>
                                <input type="text" className="form-input" name="search"
                                    style={{marginLeft: '4px'}}
                                    placeholder="placeholder" id="nav-bar-search-bar"
                                />
                                
                            </div>

                            

                            <li className="nav-item  d-flex align-items-center">
                                <a className="nav-link navItem" id="home" aria-current="page" href="/discovery">text</a>
                            </li>
                            <li className="nav-item  d-flex align-items-center">
                                <a className="nav-link navItem"  id="addAsset" aria-current="page" href="/addAssetView">text</a>
                            </li>
                            
                            <li className="nav-item d-flex align-items-center">
                                <a className="nav-link navItem" id="userHome"  aria-current="page" href="/userHome">
                                    text
                                </a>
                            </li>
                                <li className="nav-item d-flex align-items-center">
                                    <a className="nav-link navItem" id="userView"  aria-current="page" href="/user">
                                        
                                        <a className="navbar-brand" href="/user">
                                            <img src="http://pawserver.it.itba.edu.ar/paw-2023a-03/getImage/70" className="rounded-circle" width="30" height="30" alt="logo"/>
                                        </a>
                                    
                                    </a>
                                </li>
                            <li className="nav-item d-flex align-items-center">
                                <a className="nav-link navItem" id="logout"  aria-current="page" href="/logout">
                                    <i className="fas fa-sign-out-alt"></i>
                                </a>
                            </li>                            
                        </ul>
                    </div>
                </div>
            </nav>
        </>
    )
}
