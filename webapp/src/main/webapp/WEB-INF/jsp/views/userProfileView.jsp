<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title><c:out value="Profile ${user.name}" escapeXml="true"/></title>

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
    <script src="/static/javaScript/userProfile.js" defer></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/userProfile.css"/>" rel="stylesheet"/>
    <c:if test="${isCurrent}">
        <link rel="stylesheet" href="<c:url value="/static/css/modal.css"/>">
    </c:if>

</head>
<body class="body-class">

<jsp:include page="../components/navBar.jsp"/>
<c:choose>
    <c:when test="${user.profilePhoto == null}">
        <c:url var="profilePicSrc"
               value="https://images.sftcdn.net/images/t_app-logo-xl,f_auto,dpr_2/p/b05628f2-9b32-11e6-89cc-00163ed833e7/395437428/hola-free-vpn-logo"/>
    </c:when>
    <c:otherwise>
        <c:url var="profilePicSrc"
               value="/getImage/${user.profilePhoto.id}"/>
    </c:otherwise>
</c:choose>
<div class="main-class">
    <div class="user-container">
        <div class="info-container w-100" id="user-info">
            <div class="position-relative">
                <div class="user-profile-cell">
                    <img class="user-profile-picture"
                         src="${profilePicSrc}" alt="User profile Picture"/>
                    <c:if test="${isCurrent}">
                        <div class="user-change-picture-container" id="change-profile-pic-btn">
                            <i class="fas fa-solid fa-camera"></i>
                        </div>

                    </c:if>
                </div>
            </div>
            <div class="user-info">
                <h1><c:out value="${user.name}"/></h1>
                <p class="grey-text"><spring:message code="borrower"/><c:if test="${user.behavior == 'LENDER'}"> -
                    <spring:message
                            code="lender"/></c:if></p>
            </div>
            <hr/>
            <div class="tabs-container">
                <ul class="nav nav-tabs" id="user-tab" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button class="nav-link active black-text" id="tab1-tab" data-bs-toggle="tab"
                                data-bs-target="#tab1"
                                type="button" role="tab" aria-controls="tab1" aria-selected="true">Borrower Reviews
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link black-text" id="tab2-tab" data-bs-toggle="tab" data-bs-target="#tab2"
                                type="button"
                                role="tab" aria-controls="tab2" aria-selected="false">Lender Review
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link black-text" id="tab3-tab" data-bs-toggle="tab" data-bs-target="#tab3"
                                type="button"
                                role="tab" aria-controls="tab3" aria-selected="false">Locations
                        </button>
                    </li>
                </ul>
                <div class="tab-content" id="myTabContent">
                    <div class="tab-pane fade show active" id="tab1" role="tabpanel" aria-labelledby="tab1-tab">
                        <h3>Tab 1 Content</h3>
                        <p>This is the content for Tab 1.</p>
                    </div>
                    <div class="tab-pane fade" id="tab2" role="tabpanel" aria-labelledby="tab2-tab">
                        <h3>Tab 2 Content</h3>
                        <p>This is the content for Tab 2.</p>
                    </div>
                    <div class="tab-pane fade" id="tab3" role="tabpanel" aria-labelledby="tab3-tab">
                        <h3>Tab 3 Content</h3>
                        <p>This is the content for Tab 3.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <c:if test="${isCurrent}">
        <c:url var="changeProfilePicUrl" value="/user/${user.id}/editPic"/>
        <form:form modelAttribute="changeProfilePicForm" action="${changeProfilePicUrl}" method="post"
                   enctype="multipart/form-data">
            <jsp:include page="../components/changePictureModal.jsp">
                <jsp:param name="title" value="Change Profile Picture"/>
                <jsp:param name="userId" value="${user.id}"/>
                <jsp:param name="imgSrc" value="${profilePicSrc}"/>
            </jsp:include>
        </form:form>
    </c:if>
</div>

</body>
</html>
