<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title><spring:message code="addAssetView.titleView"/></title>
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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>

    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
    <script src="<c:url value="/static/javaScript/addAssetView.js"/>" defer></script>
    <script src="<c:url value="/static/javaScript/utils.js"/>" defer></script>
    <link href="<c:url value="/static/css/addAssetView.css"/>" rel="stylesheet"/>
    <script src="<c:url value="/static/javaScript/addAssetForm.js"/>" defer></script>
    <link href="<c:url value="/static/css/searchBar.css"/>" rel="stylesheet"/>
    <style>
        .big-switch:checked ~ .custom-control-label::before {
            background-color: #28a745; /* Nice Green */
        }

        .custom-switch .custom-control-input:checked ~ .custom-control-label::before {
            background-color: #28a745; /* Nice Green */
        }

    </style>

</head>

<body data-path="${path}" class="body-class">

<jsp:include page="../components/navBar.jsp">
    <jsp:param name="showSearchbar" value="true"/>
</jsp:include>
<div class="main-class">
    <jsp:include page="../components/snackbarComponent.jsp"/>
    <div class="container my-5">
        <h1 class="text-center mb-5"><spring:message code="addAssetView.title"> </spring:message></h1>
        <div class="p-4 rounded">
            <div class="flex-container">

                <div class="image-wrapper">
                    <label for="uploadImage" class="image-container position-relative ${showSnackbarInvalid ? 'image-border-error' : ''}">
                        <img src="<c:url value="/static/images/no_image_placeholder.jpg"/>" alt="Book Cover"
                             class="img-fluid" id="bookImage" style="width: 400px; height: 600px; object-fit: cover">
                        <div class="img-hover-text">
                            <i class="bi bi-cloud-upload"></i>
                            <spring:message code="addAssetView.uploadImage"/>
                        </div>
                    </label>
                </div>

                <div class="form-wrapper">
                    <c:url var="addAssetUrl" value="/addAsset"/>
                    <form:form modelAttribute="addAssetForm" method="post" onsubmit="return beforeSubmit()"
                               action="${addAssetUrl}" enctype="multipart/form-data" id="form" accept-charset="utf-9">
                        <div class="stepper-wrapper">
                            <div class="stepper-item" data-step-count="1">
                                <div class="step-counter">1</div>
                                <div class="step-name">
                                    <spring:message code="addAssetView.steps.ISBN"/>
                                </div>
                            </div>
                            <div class="stepper-item" data-step-count="2">
                                <div class="step-counter">2</div>
                                <div class="step-name">
                                    <spring:message code="addAssetView.steps.INFO"/>
                                </div>
                            </div>
                            <div class="stepper-item" data-step-count="3">
                                <div class="step-counter">3</div>
                                <div class="step-name">
                                    <spring:message code="addAssetView.steps.TIME"/>
                                </div>
                            </div>
                            <div class="stepper-item" data-step-count="4">
                                <div class="step-counter">4</div>
                                <div class="step-name">
                                    <spring:message code="addAssetView.steps.LOCATION"/>
                                </div>
                            </div>
                        </div>
                        <fieldset class="info-container d-none" data-step="1" id="isbn-fs">
                            <spring:message code="addAssetView.isbnLabel" var="isbnLabel"/>
                            <h2><spring:message code="addAssetView.steps.ISBN.title"/></h2>
                            &#x1F6C8;
                            <text class="form-subtitle">
                                <spring:message code="addAssetView.steps.ISBN_NOTE"/>
                            </text>
                            <spring:message code="addAssetView.placeholders.isbn" var="isbnPH"/>
                            <form:input path="isbn" id="isbn"
                                        placeholder="${isbnPH}"
                                        class="form-control"/>
                            <small id="isbnError" class="text-danger small d-none"><spring:message code="Pattern.addAssetForm.isbn"/></small>
                            <form:errors path="isbn" cssClass="text-danger small" element="small"/>
                            <div class="mt-3 form-button-container">
                                <input type="button" class="prev-button btn btn-outline-success mx-1"
                                       value="<spring:message code="addAssetView.steps.prevButton"/>" disabled/>
                                <input type="button" class="next-button btn btn-outline-success mx-1"
                                       value="<spring:message code="addAssetView.steps.nextButton"/>"/>
                            </div>
                        </fieldset>
                        <fieldset class="info-container d-none" data-step="2">
                            <h2><spring:message code="addAssetView.steps.INFO.title"/></h2>
                            <div class="field-group">
                                <div class="field">
                                    <label for="title" class="form-label"> <spring:message
                                            code="addAssetView.titleLabel"/></label>
                                    <spring:message code="addAssetView.placeholders.title" var="titlePH"/>
                                    <form:input path="title" id="title" placeholder="${titlePH}" class="form-control"
                                                readonly="true"/>
                                    <form:errors path="title" cssClass="text-danger small" element="small"/>
                                </div>
                                <div class="field">
                                    <label for="physicalCondition" class="form-label"> <spring:message
                                            code="addAssetView.physicalConditionLabel"/></label>
                                    <form:select path="physicalCondition" id="physicalCondition" class="form-control"
                                                 accept-charset="utf-9">
                                        <form:option value="asnew"><spring:message
                                                code="enum.ASNEW"/></form:option>
                                        <form:option value="fine"><spring:message
                                                code="enum.FINE"/></form:option>
                                        <form:option value="verygood"><spring:message
                                                code="enum.VERYGOOD"/></form:option>
                                        <form:option value="good"><spring:message
                                                code="enum.GOOD"/></form:option>
                                        <form:option value="fair"><spring:message
                                                code="enum.FAIR"/></form:option>
                                        <form:option value="poor"><spring:message
                                                code="enum.POOR"/></form:option>
                                        <form:option value="exlibrary"><spring:message
                                                code="enum.EXLIBRARY"/></form:option>
                                        <form:option value="bookclub"><spring:message
                                                code="enum.BOOKCLUB"/></form:option>
                                        <form:option value="bindingcopy"><spring:message
                                                code="enum.BINDINGCOPY"/></form:option>

                                    </form:select>
                                </div>
                            </div>
                            <div class="field-group">
                                <div class="field">
                                    <spring:message code="addAssetView.authorLabel" var="authorLabel"/>
                                    <spring:message code="addAssetView.placeholders.author" var="authorPH"/>
                                    <label for="author" class="form-label">${authorLabel}</label>
                                    <form:input path="author" id="author" placeholder="${authorPH}"
                                                class="form-control"
                                                readonly="true"/>
                                    <form:errors path="author" cssClass="text-danger small" element="small"/>
                                </div>
                                <div class="field">
                                    <spring:message code="addAssetView.languageLabel" var="languageLabel"/>
                                    <spring:message code="addAssetView.placeholders.language" var="languagePH"/>

                                    <label for="language" class="form-label">${languageLabel}</label>
                                    <form:select class="form-control round" id="languageSelect" path="languageSelect" disabled="true">
                                        <c:forEach var="lang" items="${langs}">
                                            <form:option value="${lang.code}"><c:out value="${lang.name}"/></form:option>
                                        </c:forEach>
                                    </form:select>
                                    <form:input path="language" id="language" placeholder="${languagePH}"
                                                class="form-control d-none" readonly="true"/>
                                    <form:errors path="language" cssClass="text-danger small" element="small"/>
                                </div>

                            </div>
                            <div class="field-group">
                                <spring:message code="addAssetView.placeholders.description" var="descriptionPH"/>
                                <spring:message code="addAssetView.descriptionLabel" var="descriptionLabel"/>

                                <h6  class="form-label">${descriptionLabel}</h6>
                                <form:textarea path="description" id="description" placeholder="${descriptionPH}"
                                               class="form-control" />
                                <form:errors path="description" cssClass="text-danger small" element="small"/>
                            </div>
                            <div class="mt-3 form-button-container">
                                <input type="button" class="prev-button btn btn-outline-success mx-1"
                                       value="<spring:message code="addAssetView.steps.prevButton"/>"
                                />
                                <input type="button" class="next-button btn btn-outline-success mx-1"
                                       value="<spring:message code="addAssetView.steps.nextButton"/>"
                                />
                            </div>
                        </fieldset>
                        <fieldset class="info-container d-none" data-step="3" id="duration-fs">
                            <h2><spring:message code="addAssetView.steps.TIME.title"/></h2>
                            <div class="d-flex justify-content-center">
                                <label class="align-baseline mx-1"><spring:message code="addAssetView.steps.TIME.lendFor"/></label>
                                <input type="number" id="borrow-time-quantity" name="borrow-time-quantity" value="1" min="1" class="w-25 mx-1 form-control mr-2"/>
                                <select class="w-25 mx-1 form-select" id="borrow-time-type">
                                    <option value="1"><spring:message code="addAssetView.steps.TIME.days"/></option>
                                    <option value="7"><spring:message code="addAssetView.steps.TIME.weeks"/></option>
                                    <option value="31"><spring:message code="addAssetView.steps.TIME.months"/></option>
                                </select>
                                <form:input path="maxDays" id="maxDays" class="d-none" min="1"/>
                            </div>
                            <form:errors path="maxDays" cssClass="text-danger small" element="small"/>
                            <small id="durationError" class="text-danger small d-none"><spring:message code="Min.addAssetForm.maxDays"/></small>

                            <div class="custom-control custom-switch mt-3 d-flex justify-content-center">
                                <spring:message code="addAssetView.tooltip.reservation" var="tooltiptext"/>
                                <form:checkbox cssClass="custom-control-input big-switch" id="reservationSwitch" path="isReservable" value="1" data-toggle="tooltip" title="${tooltiptext}" />
                                <label class="custom-control-label" for="reservationSwitch"><spring:message code="addAssetView.label.acceptReservation"/></label>
                            </div>

                            <div class="mt-3 form-button-container">
                                <input type="button" class="prev-button btn btn-outline-success mx-1"
                                       value="<spring:message code="addAssetView.steps.prevButton"/>"/>
                                <input type="button" class="next-button btn btn-outline-success mx-1"
                                       value="<spring:message code="addAssetView.steps.nextButton"/>"/>
                            </div>
                        </fieldset>
                        <fieldset class="info-container d-none" data-step="4">
                            <h2><spring:message code="addAssetView.steps.LOCATION.title"/> </h2>
                            <div class="d-flex align-items-center justify-content-between">
                                    <button id="prev-location" type="button" class="prev-location-button btn" onclick="changeLocation(-1)">
                                        <i class="bi bi-chevron-left"></i>
                                    </button>
                                <div class="flex-grow-1 mx-3 p-3" style="background-color: #D1E9C3; border-radius: 20px;">
                                    <div class="field">
                                        <p id="name" style="font-size: 1.5em; font-weight: bold; border: none; outline: none; background-color: transparent;">${locations[0].name}</p>
                                    </div>
                                    <div class="field-group">
                                        <div class="field">
                                            <label class="form-label"><spring:message code="addAssetView.localityLabel"/> </label>
                                            <p id="locality">${locations[0].locality}</p>
                                        </div>
                                        <div class="field">
                                            <label class="form-label"><spring:message code="addAssetView.placeholders.province"/> </label>
                                            <p id="province">${locations[0].province}</p>
                                        </div>
                                    </div>
                                    <div class="field-group">
                                        <div class="field">
                                            <label class="form-label"><spring:message code="addAssetView.countryLabel"/> </label>
                                            <p id="country">${locations[0].country}</p>
                                        </div>
                                        <div class="field">
                                            <label class="form-label"><spring:message code="addAssetView.zipcodeLabel"/> </label>
                                            <p id="zipcode">${locations[0].zipcode}</p>
                                        </div>
                                    </div>
                                    <form:input type="hidden" path="id" id="id" value="${locations[0].id}" class="form-control d-none" readonly="true"/>
                                </div>

                                <button type="button" id="next-location" class="next-location-button btn"  onclick="changeLocation(1)">
                                    <i class="bi bi-chevron-right"></i>
                                </button>
                            </div>

                            <div class="mt-3 form-button-container">
                                <input type="button" class="prev-button btn btn-outline-success mx-1" value="<spring:message code="addAssetView.steps.prevButton"/>"/>
                                <spring:message code="addAssetView.addButton" var="addButton"/>
                                <input type="submit" class="btn btn-green mx-1" value="<c:out value="${addButton}"/>"/>
                            </div>
                        </fieldset>
                        <input type="file" accept="image/*" name="file" id="uploadImage" style="display:none;" onchange="previewImage()"/>
                    </form:form>

                </div>
            </div>
        </div>
    </div>
</div>

<spring:message code="addAssetView.addBookModal.title" var="modalTitle"/>
<spring:message code="addAssetView.addBookModal.text" var="modalText"/>
<% request.setCharacterEncoding("utf-8"); %>
<jsp:include page="../components/modal.jsp">
    <jsp:param name="modalTitle" value="${modalTitle}"/>
    <jsp:param name="text" value="${modalText}"/>
    <jsp:param name="redirectionUrl" value="/myBookDetails/${assetId}"/>
</jsp:include>

<spring:message code="addAssetView.changeRole.title" var="title"/>
<spring:message code="addAssetView.changeRole.text" var="text"/>

<% request.setCharacterEncoding("utf-8"); %>
<jsp:include page="../components/addAssetModal.jsp">
    <jsp:param name="modalTitle" value="${title}"/>
    <jsp:param name="text" value="${text}"/>
</jsp:include>
<script>
    $(document).ready(function(){
        $('[data-toggle="tooltip"]').tooltip();
    });
</script>
<script>
    let locations = [
        <c:forEach var="location" items="${locations}" varStatus="status">
        {
            "id": "<c:out value="${location.id}"/>",
            "name": "<c:out value="${location.name}"/>",
            "zipcode": "<c:out value="${location.zipcode}"/>",
            "locality": "<c:out value="${location.locality}"/>",
            "province": "<c:out value="${location.province}"/>",
            "country": "<c:out value="${location.country}"/>"
        } <c:if test="${!status.last}">,</c:if>
        </c:forEach>
    ];

    let currentLocationIndex = 0;

    function updateLocationFields(index) {
        document.getElementById('id').value = locations[index].id;
        document.getElementById('name').innerText = locations[index].name;
        document.getElementById('locality').innerText = locations[index].locality;
        document.getElementById('province').innerText = locations[index].province;
        document.getElementById('country').innerText = locations[index].country;
        document.getElementById('zipcode').innerText = locations[index].zipcode;

        // Update visibility of navigation buttons
        document.getElementById('prev-location').style.display = (index > 0) ? 'inline' : 'none';
        document.getElementById('next-location').style.display = (index < locations.length - 1) ? 'inline' : 'none';
    }

    function changeLocation(direction) {
        currentLocationIndex += direction;
        currentLocationIndex = Math.max(0, Math.min(locations.length - 1, currentLocationIndex));
        updateLocationFields(currentLocationIndex);
    }
    document.addEventListener("DOMContentLoaded", e => {
        errorCode = '${errorCode}';
        console.log("LLEGUE")
        if (errorCode === 2) {
            new bootstrap.Modal($('#borrowUser')).show();
        }});

    // Initialize
    updateLocationFields(currentLocationIndex);


</script>
</body>




<script>
    window.isbnUrl = `<c:url value="/book"><c:param name="isbn" value="${isbn}" /></c:url>`
</script>

<script>
    let bindingResult = `<%= request.getAttribute("org.springframework.validation.BindingResult.addAssetForm") %>`;
    const invalidImg = `<c:out value="${invalidImg}"/>`
</script>
</html>