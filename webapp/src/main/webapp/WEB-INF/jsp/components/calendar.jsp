<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Tempus Dominus JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/@eonasdan/tempus-dominus@6.7.7/dist/js/tempus-dominus.min.js"
        crossorigin="anonymous"></script>
<!-- Tempus Dominus Styles -->
<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/@eonasdan/tempus-dominus@6.7.7/dist/css/tempus-dominus.min.css"
      crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">

<small id="dateOutOfRange" class="text-danger small " hidden="true"><spring:message code="assetView.tiemerror"/></small>
<div class="input-group log-event " style="margin-bottom: 6px" id="datetimepicker1" data-td-target-input="nearest"
     data-td-target-toggle="nearest">
    <form:input path="date" name="date" id="datetimepicker1Input" type="text" class="form-control" value=""
                data-td-target="#datetimepicker1" readonly="true"/>
    <span class="input-group-text" data-td-target="#datetimepicker1" data-td-toggle="datetimepicker">
    <i class="fas fa-calendar"></i>
  </span>
    <form:errors path="date" cssClass="text-danger small" element="small"/>
</div>

<script>

    const showError = '${dayError}' === 'true';
    if (showError) {
        document.getElementById("dateOutOfRange").hidden = false
    }

    new tempusDominus.TempusDominus(document.getElementById('datetimepicker1'), {
        display: {
            viewMode: "calendar",
            components: {
                decades: true,
                year: true,
                month: true,
                date: true,
                hours: false,
                minutes: false,
                seconds: false
            },
            icons: {
                time: 'far fa-clock',
                date: 'far fa-calendar',
                up: 'far fa-arrow-up',
                down: 'far fa-arrow-down',
                previous: 'fas fa-chevron-left',
                next: 'fas fa-chevron-right',
                today: 'far fa-calendar-check-o',
                clear: 'far fa-trash',
                close: 'far fa-times'
            },

        },
        localization: {
            format: 'L'
        },
        restrictions: {
            minDate: new Date(),
            maxDate: addDays(new Date(), parseInt(document.querySelector('body').dataset.maxdays) + 1),
            disabledDates: [addDays(new Date(), parseInt(document.querySelector('body').dataset.maxdays) + 1)],
            enabledDates: [],
            daysOfWeekDisabled: [],
            disabledTimeIntervals: [],
            disabledHours: [],
            enabledHours: []
        }

    });

    function addDays(date, days) {
        date.setDate(date.getDate() + days);
        return date;
    }
</script>
