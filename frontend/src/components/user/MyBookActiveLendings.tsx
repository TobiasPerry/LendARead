import useLendings from "../../hooks/lendings/useLendings.ts";
import {useTranslation} from "react-i18next";

const MyBookActiveLendings = ({asset}) => {
    const {lendings} = useLendings(asset);
    const {t} = useTranslation();

    return ( <div> </div>);
}

export default MyBookActiveLendings;