import {useState, useEffect, useContext} from 'react';
import { useTranslation } from 'react-i18next';
import LocationModal from "../components/modals/LocationModal.tsx";
import Location from "../components/locations/Location.tsx";
import {AuthContext} from "../contexts/authContext.tsx";
import useLocations from "../hooks/locations/useLocations.ts";
import {Helmet} from "react-helmet";
import LoadingWrapper from "../components/LoadingWrapper.tsx";


const LocationsPage = () => {
    const { t } = useTranslation();
    const { editLocation, deleteLocation, getLocations, addLocation, fetchLocation, isLoading, locations, setEditingLocation,emptyLocation, editingLocation} = useLocations()
    const [showModal, setShowModal] = useState(false);

    useEffect(() => {
       fetchLocation().then()
    }, []);


    const handleEdit = async (location: any) => {
        await setEditingLocation(location);
        setShowModal(true);
    };

    const handleDelete = async (locationId: any) => {
        await deleteLocation(locationId)
        await fetchLocation()
    };

    const handleSave = async (updatedLocation: any) => {
        setShowModal(false);
        setEditingLocation(emptyLocation)

        if(editingLocation.id !== -1)
            await editLocation(updatedLocation)
        else
            await addLocation(updatedLocation)

       await fetchLocation()
    };


    return (
        <LoadingWrapper isLoading={isLoading} documentTitle={t("locations.title")}>
        <div className="main-class" style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', flexDirection: 'column' }}>
            <div className="container" style={{textAlign: "center"}}>
                <h1 style={{marginTop: "50px"}}>{t('locations.title')}</h1>
                <div className="container-row-wrapped" style={{ backgroundColor: '#D0DCD0', borderRadius: '20px', padding: '20px' }}>
                    {locations.map((location , i) => (
                        <Location key={i} location={locations[i]} handleEdit={() => handleEdit(location)} handleDelete={() => handleDelete(location)}/>
                    ))}
                    {locations.length <= 5 && (
                        <div className="info-container m-3 add-new-location btn-icon add-button" onClick={() => {setShowModal(true)}}
                             style={{  width: '350px', height: '350px'  }}>
                            <div className="d-flex justify-content-center align-items-center" style={{ height: '100%' }}>
                                <i className="bi bi-plus-lg"></i>
                            </div>
                        </div>
                    )}
                </div>
            </div>
            <LocationModal location={editingLocation} showModal={showModal} handleClose={() => {setShowModal(false); setEditingLocation(emptyLocation)}} handleSave={handleSave}/>
        </div>
        </LoadingWrapper>
    );
};

export default LocationsPage;
