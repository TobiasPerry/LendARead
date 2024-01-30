import React, { useState } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';


const CalendarNotReservable = ({
    startDate = null,
    endDate = null,
    handleEndDateChange
}) => {


    return (
        <div className="container mt-4">

            <div className="mb-3">
                <label htmlFor="startDatePicker" className="form-label">
                    text:
                </label>
                <DatePicker
                    selected={startDate}
                    onChange={handleEndDateChange}
                    selectsEnd
                    startDate={startDate}
                    endDate={endDate}
                    dateFormat="MM/dd/yyyy"
                    className="form-control"
                />
            </div>

        </div>
    );
};

export default CalendarNotReservable;
