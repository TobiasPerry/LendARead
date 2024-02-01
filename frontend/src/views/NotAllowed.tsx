import {useTranslation} from "react-i18next";
import BrokenLendaread from "../../public/static/broken_lendaread.png"

const NotAllowed = () => {
    const {t} = useTranslation()

    return (<>
        <div className="main-class" style={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            height: '100vh'
        }}>
            <img src={BrokenLendaread} alt="Animated Image" style={{height: '300px'}}/>
            <span>
                <h1>{t('not_allowed.title')}</h1>
                <br/>
                <h2>{t('not_allowed.subtitle')}</h2>
            </span>
        </div>
    </>)
}
export default NotAllowed
