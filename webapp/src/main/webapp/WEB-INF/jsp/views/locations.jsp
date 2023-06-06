<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <link rel="shortcut icon" href="<c:url value='/static/images/favicon-claro-bg.ico'/>" type="image/x-icon">
  <script src="<c:url value="/static/javaScript/topbar.js"/>"></script>
  <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
          integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
          crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
          integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
          crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
          integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
          crossorigin="anonymous"></script>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
  <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
  <script src="<c:url value="/static/javaScript/addAssetView.js"/>" defer></script>
  <script src="<c:url value="/static/javaScript/utils.js"/>" defer></script>
  <link href="<c:url value="/static/css/addAssetView.css"/>" rel="stylesheet"/>
  <script src="<c:url value="/static/javaScript/addAssetForm.js"/>" defer></script>
  <title><spring:message code="addAssetView.locationInfo"/></title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
  <style>
    .btn-icon {
      color: #000000;
      font-size: 2rem;
      cursor: pointer;
      transition: box-shadow 0.3s ease;
    }

    .btn-icon:hover {
      box-shadow: 0px 0px 15px 2px rgba(0,0,0,0.25);
    }
  </style>
</head>



<jsp:include page="../components/navBar.jsp"/>

<body class="body-class">
<div class="main-class" style="display: flex; justify-content: center; align-items: center; flex-direction: column;">
  <div class="container">
    <h2 style="padding: 20px;"><spring:message code="addAssetView.locationInfo"/></h2>
    <div class="locations-grid d-flex flex-wrap" style="background-color: #D0DCD0; border-radius: 20px; padding: 20px;">
      <c:forEach var="location" items="${locations}" >
        <jsp:include page="../components/locationCard.jsp">
          <jsp:param name="location" value="${location}"/>
        </jsp:include>
      </c:forEach>
      <div class="info-container m-3 add-new-location btn-icon" style="max-width: 300px; min-width: 300px; height: 250px;">
        <div class="d-flex justify-content-center align-items-center" style="height: 100%;">
          <i class="bi bi-plus-lg"></i>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>

<script>
  $(function() {
    $('body').on('click', '.edit-button', function() {
      $(this).closest('form').find('input[type="text"]').removeAttr('disabled');
      $(this).addClass('d-none');
      $(this).siblings('.save-button').removeClass('d-none');
    });
  });

  $(document).ready(function() {
    $(".add-new-location").click(function() {
      let newCard = `
        <jsp:include page="../components/locationCard.jsp">
         <jsp:param name="location" value="${location}"/>
       </jsp:include>`;

      $(this).before(newCard);
    });
  });
</script>
