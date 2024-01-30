import React, { useState } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';


const CalendarReservable = ({
                                startDate = null,
                                endDate = null,
                                handleStartDateChange,
                                handleEndDateChange,
                                reservedDates
                            }) => {

    const filterStartDates = (date) => {
        return true
    }
    const filterEndDates = (date) => {
        return true
    }

    return (
        <div className="container mt-4">

            <div className="mb-3">
                <label htmlFor="startDatePicker" className="form-label">
                    text:
                </label>
                <DatePicker
                    selected={startDate}
                    onChange={handleStartDateChange}
                    selectsStart
                    filterDate={filterStartDates}
                    startDate={startDate}
                    endDate={endDate}
                    dateFormat="MM/dd/yyyy"
                    className="form-control"
                />
            </div>

            <div className="mb-3">
                <label htmlFor="endDatePicker" className="form-label">
                    text:
                </label>
                <DatePicker
                    selected={endDate}
                    onChange={handleEndDateChange}
                    selectsEnd
                    filterDate={filterEndDates}
                    startDate={startDate}
                    endDate={endDate}
                    dateFormat="MM/dd/yyyy"
                    className="form-control"
                />
            </div>
        </div>
    );
};

export default CalendarReservable;
