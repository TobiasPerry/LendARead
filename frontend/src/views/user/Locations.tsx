import {useState, useEffect, useContext} from 'react';
import { useTranslation } from 'react-i18next';
import LocationModal from "../../components/locations/LocationModal.tsx";
import Location from "../../components/locations/Location.tsx";
import {AuthContext} from "../../contexts/authContext.tsx";
import useLocations from "../../hooks/locations/useLocations.ts";

export interface LocationType {
    name: string,
    province: string,
    country: string,
    locality: string,
    zipcode: number,
    selfUrl: string
}
const LocationsPage = () => {
    const emptyLocation = {name: "", province: "", country: "", locality: "", zipcode: 0, id: -1}
    const { t } = useTranslation();
    const [locations, setLocations] = useState([emptyLocation]);
    const [showModal, setShowModal] = useState(false);
    const [editingLocation, setEditingLocation] = useState(emptyLocation);

    const {user} = useContext(AuthContext)
    const { editLocation, deleteLocation, getLocations, addLocation} = useLocations()

    useEffect(() => {
       fetchLocation()
    }, []);


    const fetchLocation = async () => {
        try {
            const response = await getLocations(user);
            await setLocations(response);
        } catch (error) {
            console.error("Failed to fetch locations:", error);
        }
    }

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

        if(editingLocation.id !== -1)
            await editLocation(updatedLocation)
        else
            await addLocation(updatedLocation)

       await fetchLocation()
    };

    const handleAddNew = () => {
        setShowModal(true);
    };

    return (
        <div className="main-class" style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', flexDirection: 'column' }}>
            <div className="container">
                <h2 style={{ padding: '20px' }}>{t('locations.title')}</h2>
                <div className="container-row-wrapped" style={{ backgroundColor: '#D0DCD0', borderRadius: '20px', padding: '20px' }}>
                    {locations.map((location , i) => (
                        <Location key={i} location={locations[i]} handleEdit={() => handleEdit(location)} handleDelete={() => handleDelete(location)}/>
                    ))}
                    {locations.length <= 5 && (
                        <div className="info-container m-3 add-new-location btn-icon add-button" onClick={handleAddNew}
                             style={{ maxWidth: '300px', minWidth: '300px', height: '300px' }}>
                            <div className="d-flex justify-content-center align-items-center" style={{ height: '100%' }}>
                                <i className="bi bi-plus-lg"></i>
                            </div>
                        </div>
                    )}
                </div>
            </div>
            <LocationModal location={editingLocation} showModal={showModal} handleClose={() => setShowModal(false)} handleSave={handleSave}/>
        </div>
    );
};

export default LocationsPage;
