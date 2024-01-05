import React, { useState, useEffect } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import LocationModal from "../../components/locations/LocationModal.tsx";
import Location from "../../components/locations/Location.tsx";

const LocationsPage = () => {
    const { t } = useTranslation();
    const [locations, setLocations] = useState([{}]); // Assuming locations are fetched from an API or similar
    const [showModal, setShowModal] = useState(false);
    const [editingLocation, setEditingLocation] = useState({});

    // Fetch locations from API (placeholder function)
    const fetchLocations = async () => {
        // Fetch locations and set them in state
    };

    useEffect(() => {
        fetchLocations();
    }, []);

    const handleEdit = (location: any) => {
        setEditingLocation(location);
        setShowModal(true);
    };

    const handleDelete = (locationId: any) => {
        // Handle deletion logic

        //delete from api

        //delete from locations
        const updated = locations
        updated.filter(idx => idx != editingLocation)
        setLocations(updated)
    };

    const handleSave = (updatedLocation: any) => {
        setShowModal(false);
        //store the new location into the api

        //display new location
        const updated = locations
        updated.push(editingLocation)
        setLocations(updated)
    };

    const handleAddNew = () => {
        setEditingLocation({ id: null, name: '', locality: '', province: '', country: '', zipcode: '' });
        setShowModal(true);
    };

    return (
        <div className="main-class" style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', flexDirection: 'column' }}>
            <div className="container">
                <h2 style={{ padding: '20px' }}>{t('locations.title')}</h2>
                <div className="container-row-wrapped" style={{ backgroundColor: '#D0DCD0', borderRadius: '20px', padding: '20px' }}>
                    {locations.map((location, i) => (
                        <Location location={locations[i]} handleEdit={() => handleEdit(location)} handleDelete={() => handleDelete(location.id)}/>
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
            <LocationModal location={editingLocation} showModal={showModal} handleClose={() => setShowModal(false)} handleSave={handleSave}> </LocationModal>
        </div>
    );
};

export default LocationsPage;
