
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
  <script src="<c:url value="/static/javaScript/map.js"/>"></script>
  <script src="<c:url value="/static/javaScript/assetView.js"/>"></script>
  <script src="<c:url value="/static/javaScript/addAssetView.js"/>"></script>
  <link rel="stylesheet" href="<c:url value="/static/css/main.css"/>">
  <link rel="stylesheet" href="<c:url value="/static/css/changeAsset.css"/>">
</head>
<body data-path="${path}" class=" body-class" data-maxDays="<c:out value="${assetInstance.maxDays}" /> ">
<jsp:include page="../components/navBar.jsp"/>
<c:url var="changeAsset" value="/editAsset/${assetInstance.id}"/>
<form:form modelAttribute="assetInstanceForm" method="post"
           action="${changeAsset}" enctype="multipart/form-data" id="form" accept-charset="utf-8" >
<div class="main-class" style="display: flex; justify-content: center;align-items: center;flex-direction: column;">

    <div style="background-color: #f0f5f0; margin: 50px; border-radius: 20px; padding: 20px; width: 50%">
    <div style="display: flex; flex-flow: row; width: 100%; justify-content: start;">

      <div class="image-div image-wrapper">
        <label for="uploadImage" class="image-container position-relative">
            <img id="bookImage" src="<c:url value="/getImage/${assetInstance.image.id}"/>" alt="Book cover"
               style="margin-left: 0; margin-right: 50px; height: 500px; width: 300px; object-fit: cover">
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

        <form:input path="image" type="file" value="${image}" accept="image/*" name="file" id="uploadImage" style="display:none;"
          onchange="previewImage()"/>
          <h6 id="physicalConditionClick"
            data-physicalcondition="<c:out value="${assetInstance.physicalCondition}"/>">
          <form:select path="physicalCondition" id="physicalCondition" class="form-control"
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
          <input class="btn btn-green" type="submit" value="<spring:message code="editAssetView.editButton"/>">
      </div>
    </div>
    </div>

  <div class="container-row" style="min-width: 50%; width: fit-content; margin-bottom: 20px">

    <div class="container-column" style="flex: 0 0 100%">
      <div class="card" style="background-color:#e3e6e3;height: fit-content; border-radius: 25px">
        <div class="card-body">


          <h5 class="card-title"><spring:message code="assetView.locationTitle"/></h5>
            <h6 class="card-text" style="margin-bottom: 5px"><spring:message code="assetView.zipcode"/></h6>
          <form:input path="zipcode" class="form-control" style="margin-bottom: 5px" value="${assetInstance.location.zipcode}"/>

          <h6 class="card-text" style="margin-bottom: 5px"><spring:message code="assetView.locality"/></h6>
         <form:input path="locality" class="form-control" style="margin-bottom: 5px" value="${assetInstance.location.locality}"/>

          <h6 class="card-text" style="margin-bottom: 5px"><spring:message code="assetView.province"/></h6>
          <form:input path="province" class="form-control" style="margin-bottom: 5px" value="${assetInstance.location.province}"/>


          <h6 class="card-text" style="margin-bottom: 5px"><spring:message code="assetView.country"/></h6>
          <form:input path="country" class="form-control" style="margin-bottom: 5px" value="${assetInstance.location.country}"/>

        </div>
      </div>
    </div>


</div>

</div>
</form:form>

<script>
  document.addEventListener("DOMContentLoaded", () => {
    options = document.getElementById("physicalCondition").options;
    for (let i = 0; i < options.length; i++) {
      if (options[i].value === "${assetInstance.physicalCondition}") {
        options[i].selected = true;
      }
    }
  });
</script>
