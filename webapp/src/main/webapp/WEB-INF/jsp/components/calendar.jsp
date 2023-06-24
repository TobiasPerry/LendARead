<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Popperjs -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" crossorigin="anonymous"></script>
<!-- Tempus Dominus JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/@eonasdan/tempus-dominus@6.7.7/dist/js/tempus-dominus.min.js" crossorigin="anonymous"></script>

<!-- Tempus Dominus Styles -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@eonasdan/tempus-dominus@6.7.7/dist/css/tempus-dominus.min.css" crossorigin="anonymous">

<small id="dateOutOfRange" class="text-danger small " hidden="true"><spring:message code="assetView.tiemerror"/></small>
<div class="input-group log-event " style="margin-bottom: 6px" id="datetimepicker1" data-td-target-input="nearest"
     data-td-target-toggle="nearest">
    <form:input path="date" name="date" id="datetimepicker1Input" type="text" class="form-control" value=""
                data-td-target="#datetimepicker1" readonly="true"/>
    <span class="input-group-text" data-td-target="#datetimepicker1" data-td-toggle="datetimepicker">
    <i class="fas fa-calendar"></i>
  </span>
<%--    <form:errors path="date" cssClass="text-danger small" element="small"/>--%>
</div>
<div class="input-group log-event " style="margin-bottom: 6px" id="datetimepicker2" data-td-target-input="nearest"
     data-td-target-toggle="nearest">
    <form:input path="date" name="date" id="datetimepicker2Input" type="text" class="form-control" value=""
                data-td-target="#datetimepicker2" readonly="true"/>
    <span class="input-group-text" data-td-target="#datetimepicker2" data-td-toggle="datetimepicker">
    <i class="fas fa-calendar"></i>
  </span>
    <%--    <form:errors path="date" cssClass="text-danger small" element="small"/>--%>
</div>
<form:errors path="date" cssClass="text-danger small" element="small"/><br>
<div id="lendings" hidden="hidden">
<c:forEach var="listValue" items="${lendings}">
    <div hidden="hidden" data-lendate="${listValue.lendDate}" data-dev="${listValue.devolutionDate}"></div>
</c:forEach>
</div>
<script>

    const showError = '${dayError}' === 'true';
    const dates = [];
    $(document).ready(function() {
        var children = Array.from(document.getElementById("lendings").children);
        children.forEach(function (child) {
            const dateRange = getDateRange(new Date(child.dataset.lendate), new Date(child.dataset.dev));
            dates.push(...dateRange); // Use the spread operator to concatenate the arrays
        });

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
            restrictions: {
                minDate: new Date(),
                enabledDates: [],
                disabledDates: dates,
                daysOfWeekDisabled: [],
                disabledHours: [],
                enabledHours: []
            }

        });
        new tempusDominus.TempusDominus(document.getElementById('datetimepicker2'), {
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

            restrictions: {
                minDate: new Date(),
                disabledDates: [new Date()],
                maxDate:new Date()
            }

        });
    });
    if (showError) {
        document.getElementById("dateOutOfRange").hidden = false
    }
    function getDateRange(startDate, endDate) {
        const dates = [];
        const currentDate = new Date(startDate);

        while (currentDate <= endDate) {
            dates.push(new Date(currentDate));
            currentDate.setDate(currentDate.getDate() + 1);
        }

        return dates;
    }

    document
        .getElementById('datetimepicker1')
        .addEventListener('change.td', (event) => {
            document.getElementById("datetimepicker2Input").value = null;
            new tempusDominus.TempusDominus(document.getElementById('datetimepicker2'),
                {
                    defaultDate: new Date(event.detail.date),
                    restrictions: {
                        minDate: new Date(event.detail.date),
                        maxDate: addDays(new Date(event.detail.date), parseInt(document.querySelector('body').dataset.maxdays)),
                        disabledDates: dates,
                    },
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
                }
            )
        });
    function addDays(date, days) {
        date.setDate(date.getDate() + days);
        return date;
    }
</script>
