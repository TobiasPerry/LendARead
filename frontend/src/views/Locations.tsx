import React, {useState, useEffect, useContext} from 'react';
import { useTranslation } from 'react-i18next';
import LocationModal from "../components/modals/LocationModal.tsx";
import Location from "../components/locations/Location.tsx";
import {AuthContext} from "../contexts/authContext.tsx";
import useLocations from "../hooks/locations/useLocations.ts";
import {Helmet} from "react-helmet";
import LoadingWrapper from "../components/LoadingWrapper.tsx";
import {Link} from "react-router-dom";
import Pagination from "../components/Pagination.tsx";
import Snackbar from "../components/SnackBar.tsx";


const LocationsPage = () => {
    const { t } = useTranslation();
    const { editLocation,
        deleteLocation,
        addLocation,
        fetchLocation,
        isLoading,
        locations,
        setEditingLocation,
        emptyLocation,
        editingLocation,
        currentPage,
        changePageLocations,
        totalPages,
        error
    } = useLocations()
    const [showModal, setShowModal] = useState(false);
    const {user} = useContext(AuthContext);

    useEffect(() => {
        fetchLocation(currentPage).then()
    }, []);


    const handleEdit = async (location: any) => {
        await setEditingLocation(location);
        setShowModal(true);
    };

    const handleDelete = async (locationId: any) => {
        await deleteLocation(locationId)
        await fetchLocation(currentPage)
    };

    const handleSave = async (updatedLocation: any) => {
        setShowModal(false);
        setEditingLocation(emptyLocation)

        if(editingLocation.id !== -1)
            await editLocation(updatedLocation)
        else
            await addLocation(updatedLocation)

       await fetchLocation(currentPage)
    };

    const handleBackClick = () => {

    }


    return (
        <LoadingWrapper isLoading={isLoading} documentTitle={t("locations.title")} isWhiteAnimation={false}>
        <div className="main-class" style={{ display: 'flex', flexDirection: 'column' }}>
            <div className="container" style={{textAlign: "center"}}>
                <Link className="d-flex back-click flex-row align-items-center mt-4 mb-4" to={`/user/${user}`}>
                    <i className="fas fa-arrow-left mb-1"></i>
                    <h3 className="ms-3">
                        {t("user")}
                    </h3>
                </Link>
                <h1 style={{marginTop: "50px"}}>{t('locations.title')}</h1>
                <div className="container-row-wrapped" style={{borderRadius: '20px', padding: '20px' }}>
                    {locations.map((location , i) => (
                        <Location key={i} location={locations[i]} handleEdit={() => handleEdit(location)} handleDelete={() => handleDelete(location)}/>
                    ))}
                    { totalPages === currentPage &&
                        <div className="info-container m-3 add-new-location btn-icon add-button" onClick={() => {setShowModal(true)}}
                         style={{  width: '350px', height: '350px'  }}>
                        <div className="d-flex justify-content-center align-items-center" style={{ height: '100%' }}>
                            <i className="bi bi-plus-lg"></i>
                        </div>
                    </div> }
                </div>
            </div>
            <LocationModal location={editingLocation} showModal={showModal} handleClose={() => {setShowModal(false); setEditingLocation(emptyLocation)}} handleSave={handleSave}/>
            <Pagination currentPage={currentPage} changePage={changePageLocations} totalPages={totalPages} />
            {error.status && <Snackbar message={error.text} />}
        </div>
        </LoadingWrapper>
    );
};

export default LocationsPage;
