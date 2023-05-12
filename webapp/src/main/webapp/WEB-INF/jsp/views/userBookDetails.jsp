<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

    <title><spring:message code="userAssetDetailView.title"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="<c:url value="/static/javaScript/topbar.js"/>"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
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
    <link href="<c:url value="/static/css/userHomeView.css"/>" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css?family=Overpass:400,700|Roboto:400,700" rel="stylesheet">
    <link rel="shortcut icon" href="<c:url value='/static/images/favicon-claro.ico'/>" type="image/x-icon">
</head>
<body data-path="${path}" class="body-class">

<jsp:include page="../components/navBar.jsp"/>
<div>
<div class="back-button" style="display: flex; flex-direction: row; ">
    <a href="<c:url value='/userHomeReturn' />" class="btn-breadcrumb" >
        <i class="fas fa-arrow-left"></i>
    </a>
    <h2 class="textOverflow"> <c:out value="${table}" /> </h2>
</div>
    <div class="main-class" style="   display: flex; justify-content: center;align-items: center;flex-direction: column;">

    <div style="background-color: #f0f5f0; margin: 50px; border-radius: 20px; padding: 20px;width:fit-content; min-width: 50%">
        <div class="container-row-wrapped">
            <img src="<c:url value="/getImage/${asset.imageId}"/>" class="mx-3" alt="Book cover"
                 style="height: 500px; width: 300px; object-fit: cover">
            <div class="w-25 h-25 mx-5">
                <h1 class="textOverflow"><c:out value="${asset.book.name} "/></h1>
                <h3 class="textOverflow"><spring:message code="assetView.by"/> <c:out
                        value="${asset.book.author}"/></h3>
                <h6>
                    <i>
                        <u><c:out value="${asset.physicalCondition}"/></u>
                    </i>
                </h6>
                <h6 style="color: #7d7c7c"><spring:message code="assetView.language"/>: <c:out
                        value="${asset.book.language}"/></h6>
                <h6 style="color: #7d7c7c"><spring:message code="assetView.isbn"/>: <c:out
                        value="${asset.book.isbn}"/></h6>

            </div>

            <div>
                <h1 class="textOverflow"><spring:message code="userAssetDetailView.options" /></h1>
                <c:when test="${table} == 'Lended Books'">
                    <jsp:include page="../components/myBookOptions.jsp" />
                </c:when>
            </div>
        </div>

    </div>
    <div class="container-row" style="min-width: 50%; width: fit-content; margin-bottom: 20px">


    </div>
</div>
</div>
</body>
<% request.setCharacterEncoding("utf-8"); %>
<jsp:include page="../components/userHomeModal.jsp">
    <jsp:param name="modalType" value="${modalType}"/>
    <jsp:param name="assetId" value="${assetId}"/>
</jsp:include>

</html>
