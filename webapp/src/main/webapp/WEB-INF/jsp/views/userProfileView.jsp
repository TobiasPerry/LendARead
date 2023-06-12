<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title><c:out value="Profile ${user.name}" escapeXml="true"/></title>

    <link rel="shortcut icon" href="<c:url value='/static/images/favicon-claro-bg.ico'/>" type="image/x-icon">
    <script src="<c:url value="/static/javaScript/topbar.js"/>" defer></script>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
            integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
            integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
            crossorigin="anonymous"></script>
    <script src="<c:url value="/static/javaScript/userProfile.js"/>" defer></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/userProfile.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/profileReviewCard.css"/>" rel="stylesheet"/>
    <c:if test="${isCurrent}">
        <link rel="stylesheet" href="<c:url value="/static/css/modal.css"/>">
    </c:if>

</head>
<body data-path=${path} class="body-class">

<jsp:include page="../components/navBar.jsp">
    <jsp:param name="showSearchbar" value="true"/>
</jsp:include>
<c:choose>
    <c:when test="${user.profilePhoto == null}">
        <c:url var="profilePicSrc"
               value="/static/images/user-placeholder.jpeg"/>
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
                <p class="grey-text"><spring:message code="borrower"/>
                    <span class="user-role-stars">
                        <c:if test="${borrowerRating < 0}">
                            <c:out value="-.-"/>
                        </c:if>
                        <c:if test="${borrowerRating >= 0}">
                            <c:out value="${borrowerRating}"/>
                        </c:if>
                             ★</span>
                    <c:if test="${user.behavior == 'LENDER'}"> -
                        <spring:message code="lender"/>
                        <span class="user-role-stars">
                            <c:if test="${lenderRating < 0}">
                                <c:out value="-.-"/>
                            </c:if>
                        <c:if test="${lenderRating >= 0}">
                            <c:out value="${lenderRating}"/>
                        </c:if>
                            ★</span>
                    </c:if>
                </p>
            </div>
            <hr/>
            <div class="tabs-container">
                <ul class="nav nav-tabs" id="user-tab" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button class="nav-link black-text" id="tab1-tab" data-bs-toggle="tab"
                                data-bs-target="#tab1"
                                type="button" role="tab" aria-controls="tab1" aria-selected="true">Borrower Reviews
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link black-text" id="tab2-tab" data-bs-toggle="tab"
                                data-bs-target="#tab2"
                                type="button"
                                role="tab" aria-controls="tab2" aria-selected="false">Lender Review
                        </button>
                    </li>
                </ul>
                <div class="tab-content" id="myTabContent">
                    <div class="tab-pane show" id="tab1" role="tabpanel" aria-labelledby="tab1-tab">
                        <c:choose>
                            <c:when test="${borrowerReviews.list.size() > 0}">
                                <div class="user-profile-reviews-pane">
                                    <c:forEach var="review" items="${borrowerReviews.list}">
                                        <jsp:include page="../components/reviewCardProfile.jsp">
                                            <jsp:param name="review" value="${review.review}"/>
                                            <jsp:param name="userId" value="${review.reviewer.id}"/>
                                            <jsp:param name="reviewer" value="${review.reviewer.name}"/>
                                            <jsp:param name="role" value="${review.reviewer.behavior}"/>
                                            <jsp:param name="imgSrc" value="${review.reviewer.profilePhoto}"/>
                                        </jsp:include>
                                    </c:forEach>
                                </div>
                                <div class="user-profile-reviews-buttons">
                                    <ul class="pagination justify-content-center align-items-center">
                                        <li class="page-item">
                                            <button type="button"
                                                    class="btn mx-5 pagination-button ${borrowerReviews.currentPage != 1 ? "" : "disabled"}"
                                                    id="previousPageButtonBorrower"
                                                    style="border-color: rgba(255, 255, 255, 0)"
                                                    onclick="window.location.href = '<c:url
                                                            value="/user/${user.id}?activetab=borrowerReviews&activetabpage=${borrowerReviews.currentPage - 1}"/>'">
                                                <i class="bi bi-chevron-left"></i> <spring:message
                                                    code="paginationButton.previous"/>
                                            </button>
                                        </li>
                                        <li>
                                            <c:out value="${borrowerReviews.currentPage}"/> / <c:out
                                                value="${borrowerReviews.totalPages}"/>
                                        </li>

                                        <li class="page-item">
                                            <button type="button"
                                                    class="btn mx-5 pagination-button ${borrowerReviews.currentPage < borrowerReviews.totalPages ? "" : "disabled"}"
                                                    id="nextPageButtonBorrower"
                                                    style="border-color: rgba(255, 255, 255, 0)"
                                                    onclick="window.location.href = '<c:url
                                                            value="/user/${user.id}?activetab=borrowerReviews&activetabpage=${borrowerReviews.currentPage + 1}"/>'">
                                                <spring:message code="paginationButton.next"/> <i
                                                    class="bi bi-chevron-right"></i>
                                            </button>
                                        </li>
                                    </ul>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="user-profile-no-reviews">
                                    <h4 class="grey-text"><spring:message code="userProfile.noReviews"/></h4>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="tab-pane" id="tab2" role="tabpanel" aria-labelledby="tab2-tab">
                        <c:choose>
                            <c:when test="${lenderReviews.list.size() > 0}">
                                <div class="user-profile-reviews-pane">
                                    <c:forEach var="review" items="${lenderReviews.list}">
                                        <jsp:include page="../components/reviewCardProfile.jsp">
                                            <jsp:param name="review" value="${review.review}"/>
                                            <jsp:param name="userId" value="${review.reviewer.id}"/>
                                            <jsp:param name="reviewer" value="${review.reviewer.name}"/>
                                            <jsp:param name="role" value="${review.reviewer.behavior}"/>
                                            <jsp:param name="imgSrc"
                                                       value="${review.reviewer.profilePhoto == null ? -1 : review.reviewer.profilePhoto.id}"/>
                                        </jsp:include>
                                    </c:forEach>
                                </div>
                                <div class="user-profile-reviews-buttons">
                                    <ul class="pagination justify-content-center align-items-center">
                                        <li class="page-item">
                                            <button type="button"
                                                    class="btn mx-5 pagination-button ${lenderReviews.currentPage != 1 ? "" : "disabled"}"
                                                    id="previousPageButtonLender"
                                                    style="border-color: rgba(255, 255, 255, 0)"
                                                    onclick="window.location.href = '<c:url
                                                            value="/user/${user.id}?activetab=lenderReviews&activetabpage=${lenderReviews.currentPage - 1}"/>'">
                                                <i class="bi bi-chevron-left"></i> <spring:message
                                                    code="paginationButton.previous"/>
                                            </button>
                                        </li>
                                        <li>
                                            <c:out value="${lenderReviews.currentPage}"/> / <c:out
                                                value="${lenderReviews.totalPages}"/>
                                        </li>

                                        <li class="page-item">
                                            <button type="button"
                                                    class="btn mx-5 pagination-button ${lenderReviews.currentPage < lenderReviews.totalPages ? "" : "disabled"}"
                                                    id="nextPageButtonLender"
                                                    style="border-color: rgba(255, 255, 255, 0)"
                                                    onclick="window.location.href = '<c:url
                                                            value="/user/${user.id}?activetab=lenderReviews&activetabpage=${lenderReviews.currentPage + 1}"/>'"
                                            >
                                                <spring:message code="paginationButton.next"/> <i
                                                    class="bi bi-chevron-right"></i>
                                            </button>
                                        </li>
                                    </ul>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="user-profile-no-reviews">
                                    <h4 class="grey-text"><spring:message code="userProfile.noReviews"/></h4>
                                </div>
                            </c:otherwise>
                        </c:choose>
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

<script>
    let activeTab = `<c:out value="${activeTab}" />`
</script>

</body>
</html>
