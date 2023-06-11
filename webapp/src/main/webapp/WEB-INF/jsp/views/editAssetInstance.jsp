
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title><c:out value="${assetInstance.book.name}"/> <spring:message
          code="assetView.by"/> <c:out
          value="${assetInstance.book.author}"/>
  </title>
  <link rel="shortcut icon" href="<c:url value='/static/images/favicon-claro-bg.ico'/>" type="image/x-icon">
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
  <script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.1/css/all.min.css" integrity="sha512-gMjQeDaELJ0ryCI+FtItusU9MkAifCZcGq789FrzkiM49D8lbDhoaUaIX4ASU187wofMNlgBJ4ckbrXM9sE6Pg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <script src="<c:url value="/static/javaScript/map.js"/>"></script>
  <script src="<c:url value="/static/javaScript/assetView.js"/>"></script>
    <script src="<c:url value="/static/javaScript/addAssetView.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/static/css/main.css"/>">
    <link href="<c:url value="/static/css/changeAsset.css"/>" rel="stylesheet"/>

    <link rel="stylesheet" href="<c:url value="/static/css/changeAsset.css"/>">
</head>
<body data-path="${path}" class=" body-class" data-maxDays="<c:out value="${assetInstance.maxDays}" /> ">
<jsp:include page="../components/navBar.jsp"/>
<c:url var="changeAsset" value="/editAsset/${assetInstance.id}"/>
<form:form modelAttribute="assetInstanceForm" method="post"
           action="${changeAsset}" enctype="multipart/form-data" onsubmit="return beforeSubmit()" id="form" accept-charset="utf-8" >
<div class="main-class" style="display: flex; justify-content: center;align-items: center;flex-direction: column;">

    <div style="background-color: #f0f5f0; margin: 50px; border-radius: 20px; padding: 20px; width: 50%">
    <div style="display: flex; flex-flow: row; width: 100%; justify-content: start;">
        <div class="image-wrapper">
            <label for="uploadImage" class="image-container position-relative ${showSnackbarInvalid ? 'image-border-error' : ''}">
                <img src="<c:url value="/getImage/${assetInstance.image.id}"/>" alt="Book Cover"
                     class="img-fluid" id="bookImage" style="width: 400px; height: 600px; object-fit: cover">
                <div class="img-hover-text">
                    <i class="fas fa-pencil-alt" style="color: #D1E9C3"></i>
                    <spring:message code="editAssetView.changeImage"/>
                </div>
            </label>
        </div>

      <div class="mx-2">

        <h1 class="textOverflow" title="<c:out value="${assetInstance.book.name}"/>"><c:out
                value="${assetInstance.book.name} "/></h1>


        <h3 class="textOverflow" id="authorClick" data-author="<c:out value="${assetInstance.book.author}"/>"><spring:message
                code="assetView.by"/>
          <span class="textOverflow">
                        <c:out value="${assetInstance.book.author}"/>
          </span>
        </h3>

        <form:input path="image" type="file"  accept="image/*" name="file" id="uploadImage" style="display:none;"
          onchange="previewImage()"/>
          <h6 id="physicalConditionClick"
            data-physicalcondition="<c:out value="${assetInstance.physicalCondition}"/>">
          <form:select path="physicalCondition" id="physicalCondition" class="form-control form-select"
                       accept-charset="utf-9" >
              <form:option  value="ASNEW"    ><spring:message
                      code="enum.ASNEW"/></form:option>
              <form:option value="FINE" ><spring:message
                      code="enum.FINE"/></form:option>
              <form:option  value="VERYGOOD">
                <spring:message code="enum.VERYGOOD"/></form:option>
              <form:option value="GOOD"><spring:message
                      code="enum.GOOD"/></form:option>
              <form:option value="FAIR" ><spring:message
                      code="enum.FAIR"/></form:option>
              <form:option value="POOR"  ><spring:message
                      code="enum.POOR"/></form:option>
              <form:option value="EXLIBRARY"><spring:message
                      code="enum.EXLIBRARY"/></form:option>
              <form:option  value="BOOKCLUB" ><spring:message
                      code="enum.BOOKCLUB"/></form:option>
              <form:option  value="BINDINGCOPY" ><spring:message
                      code="enum.BINDINGCOPY"/></form:option>
          </form:select>
        </h6>

        <h6 id="languageClick" data-language="<c:out value="${assetInstance.book.language}"/>" style="color: #7d7c7c">
          <spring:message code="assetView.language"/>: <span > <c:out
                value="${assetInstance.book.language}"/></span>
        </h6>

        <h6 style="color: #7d7c7c"><spring:message code="assetView.isbn"/>: <c:out
                value="${assetInstance.book.isbn}"/>
        </h6>
          <h6 class="align-baseline mx-1"><spring:message code="addAssetView.steps.TIME.lendFor"/></h6>
          <div>
              <div class="d-flex">
                  <input type="number" value="${assetInstance.maxDays}" id="borrow-time-quantity" name="borrow-time-quantity" min="1" style="margin-right: 5px" class="w-25 form-control"/>
                  <select class="w-26 ml-2 form-select" id="borrow-time-type">
                      <option value="1"><spring:message code="addAssetView.steps.TIME.days"/></option>
                      <option value="7"><spring:message code="addAssetView.steps.TIME.weeks"/></option>
                      <option value="31"><spring:message code="addAssetView.steps.TIME.months"/></option>
                  </select>
                  <form:input path="maxDays" id="maxDays" class="d-none" min="1"/>
              </div >
              <form:errors path="maxDays" id="durationError" cssClass="text-danger small" element="small"/>
          </div>

          <input class="btn btn-green mt-2" type="submit"  value="<spring:message code="editAssetView.editButton"/>">
      </div>
    </div>
    </div>

  <div class="container-row" style="min-width: 50%; width: fit-content; margin-bottom: 20px">
    <div class="container-column" style="flex: 0 0 100%">
      <div class="card" style="background-color:#e3e6e3;height: fit-content; border-radius: 25px">
        <div class="card-body">
            <h5 class="card-title" style="text-align: center"><spring:message code="assetView.locality"/></h5>
            <div class="d-flex align-items-center justify-content-between">
                <button id="prev-location" type="button" class="carousel-control-prev btn"  style="background-color: #777777;width: 3.5%;border-top-left-radius: 25px;border-bottom-left-radius: 25px" onclick="changeLocation(-1)">
                    <i class="fas fa-angle-left"></i>
                </button>
                <div class="flex-grow-1 mx-3 p-3" style="border-radius: 20px;">
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
                <button type="button" id="next-location" class="carousel-control-next btn" style="background-color: #777777; width: 3.5%;border-top-right-radius: 25px;border-bottom-right-radius: 25px" onclick="changeLocation(1)">
                    <i class="fas fa-angle-right"></i>
                </button>
        </div>
      </div>
    </div>
    </div>
  </div>
  <div class="container-row" style="min-width: 50%; width: fit-content; margin-bottom: 20px">
        <div class="container-column" style="flex: 0 0 100%">
            <div class="card" style="background-color:#e3e6e3;height: fit-content; border-radius: 25px">
                <div class="card-body">
                    <h5 class="card-title" style="text-align: center"><spring:message code="assetView.description"/></h5>
                    <textarea class="form-control" id="myTextArea" onchange="here();"><c:out value="${assetInstance.description}"/></textarea>
                    <form:input type="hidden" id="content" path="description" value="${assetInstance.description}"/>
                    <form:errors path="description" cssClass="text-danger small" element="small"/>
                </div>
            </div>
        </div>
  </div>

</div>
</form:form>

<script>
    var text1 = document.getElementById('myTextArea').value;
        function here() {
        text1 = document.getElementById('myTextArea').value;
        document.getElementById("content").value = text1;
    }
  document.addEventListener("DOMContentLoaded", () => {
    options = document.getElementById("physicalCondition").options;
    for (let i = 0; i < options.length; i++) {
      if (options[i].value === "${assetInstance.physicalCondition}") {
        options[i].selected = true;
      }
    }
  });
    async function checkDuration() {
        const durationInput = parseInt(document.getElementById('borrow-time-quantity').value)
        const durationErrorMsg = document.getElementById('durationError')
        if (durationInput < 1) {
            durationErrorMsg.classList.remove('d-none')
            return false
        }
        durationErrorMsg.classList.add('d-none')
        return true
    }
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

        console.log(locations.length)
        // Update visibility of navigation buttons
        document.getElementById('prev-location').style.display = (index > 0) ? 'inline' : 'none';
        document.getElementById('next-location').style.display = (index < locations.length -1) ? 'inline' : 'none';
    }

    function changeLocation(direction) {
        currentLocationIndex += direction;
        currentLocationIndex = Math.max(0, Math.min(locations.length - 1, currentLocationIndex));
        updateLocationFields(currentLocationIndex);
    }

    // Initialize
    updateLocationFields(currentLocationIndex);

    function beforeSubmit() {
      const borrowTimeQuantity = document.getElementById('borrow-time-quantity')
      const borrowTimeType = document.getElementById('borrow-time-type')
      const timeInDays = document.getElementById('maxDays')
        if (borrowTimeQuantity.value === "") {
            borrowTimeQuantity.value = "0"
        }
       const totalTimeInDays = parseInt(borrowTimeQuantity.value) * parseInt(borrowTimeType.value)

      timeInDays.value = totalTimeInDays

      return true
  }
</script>
