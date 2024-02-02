import React, { useState } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import {useTranslation} from "react-i18next";


const CalendarReservable = ({
                                startDate = null,
                                endDate = null,
                                handleStartDateChange,
                                handleEndDateChange,
                                reservedDates,
                                maxLendingDays
                            }) => {

    const filterStartDates = (date : Date) => {
        const today: Date = new Date();
        today.setHours(0, 0, 0, 0);
        if(date < today) return false;

        // In the case that there's no end date already selected, just check that is not reserved on that
        if(endDate === null || endDate === undefined) {
            for (const range of reservedDates) {
                if (date >= range.start && date <= range.end)
                    return false;
            }
        }//Otherwise we have to check: Not to be before end as much as maxLendingDays
        else{
            if(date >= endDate) return false;
            const minDate = new Date(endDate)
            minDate.setDate(endDate.getDate() - maxLendingDays)
            if(date < minDate) return false;
            // Otherwise we have to check that there's no reserved day in between date and end
            for(const range of reservedDates){
                if(endDate >= range.start && (date <= range.end ))
                    return false;
            }
        }
        return true;
    }
    const filterEndDates = (date) => {
        const today: Date = new Date();
        today.setHours(0, 0, 0, 0);
        // <= because could never return today
        if (date <= today) return false;

        // In the case that there's no start date already selected, just check that is not reserved on that
        if(startDate === null || startDate === undefined) {
            for (const range of reservedDates) {
                if (date >= range.start && date <= range.end)
                    return false;
            }
        }//Otherwise we have to check: Not to be past the maxLendingDays from the start:
        else{
            if(date <= startDate) return false;
            const maxDate = new Date(startDate)
            maxDate.setDate(startDate.getDate() + maxLendingDays)
            if (date > maxDate) return false;
            // Otherwise check that there's no any reserved day before date and after start
            for(const range of reservedDates){
                if(range.end >= startDate && (range.start <= date || range.end <= date)){
                    return false;
                }
            }
        }
        return true;
    }
    const {t} = useTranslation()
    return (
        <div className="container mt-4">

            <div className="mb-3">
                <label htmlFor="startDatePicker" className="form-label">
                    {t('view_asset.start_date')}:
                </label>
                <DatePicker
                    selected={startDate}
                    onChange={handleStartDateChange}
                    selectsStart
                    filterDate={filterStartDates}
                    startDate={startDate}
                    endDate={endDate}
                    dateFormat={t('date_for_calendar')}
                    className="form-control"
                />
            </div>

            <div className="mb-3">
                <label htmlFor="endDatePicker" className="form-label">
                    {t('view_asset.end_date')}:
                </label>
                <DatePicker
                    selected={endDate}
                    onChange={handleEndDateChange}
                    selectsEnd
                    filterDate={filterEndDates}
                    startDate={startDate}
                    endDate={endDate}
                    dateFormat={t('date_for_calendar')}
                    className="form-control"
                />
            </div>
        </div>
    );
};

export default CalendarReservable;
