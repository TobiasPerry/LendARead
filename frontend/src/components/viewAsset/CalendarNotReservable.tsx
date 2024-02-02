import React, { useState } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import {useTranslation} from "react-i18next";


const CalendarNotReservable = ({
    startDate = null,
    endDate = null,
    maxLendingDays,
    handleEndDateChange
}) => {
    const {t} = useTranslation()

    const filterDate = (date) => {
        const maxDate = new Date(startDate)
        maxDate.setDate(startDate.getDate() + maxLendingDays)
        return (date >= startDate && date <= maxDate)
    }

    return (
        <div className="container mt-4">

            <div className="mb-3">
                <label htmlFor="startDatePicker" className="form-label">
                    {t('view_asset.end_date')}:
                </label>
                <DatePicker
                    selected={endDate}
                    onChange={handleEndDateChange}
                    selectsEnd
                    filterDate={filterDate}
                    startDate={startDate}
                    endDate={endDate}
                    // dateFormat="dd/MM/yyyy"
                    dateFormat={t('date_for_calendar')}
                    className="form-control"
                />
            </div>

        </div>
    );
};

export default CalendarNotReservable;
