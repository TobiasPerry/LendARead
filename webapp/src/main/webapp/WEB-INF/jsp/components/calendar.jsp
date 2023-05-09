<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Tempus Dominus JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/@eonasdan/tempus-dominus@6.7.7/dist/js/tempus-dominus.min.js" crossorigin="anonymous"></script>
<!-- Tempus Dominus Styles -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@eonasdan/tempus-dominus@6.7.7/dist/css/tempus-dominus.min.css" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">


<div class="input-group log-event " style="margin-bottom: 6px" id="datetimepicker1" data-td-target-input="nearest" data-td-target-toggle="nearest">
  <form:input path="date" name="date" id="datetimepicker1Input" type="text" class="form-control" data-td-target="#datetimepicker1" readonly="true"/>
  <form:errors path="date" cssClass="text-danger small" element="small"/>
  <span class="input-group-text" data-td-target="#datetimepicker1" data-td-toggle="datetimepicker">
    <i class="fas fa-calendar"></i>
  </span>
</div>

<script>

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
    localization:{
      format: 'L'
    },
    restrictions: {
      minDate: new Date(),
      maxDate: addDays(new Date(),parseInt(document.querySelector('body').dataset.maxdays)+1),
      disabledDates: [addDays(new Date(),parseInt(document.querySelector('body').dataset.maxdays )+1)],
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
