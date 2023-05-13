<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="discovery.head.title"/></title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
    <script src="<c:url value="/static/javaScript/topbar.js"/>"></script>
    <script src="<c:url value="/static/javaScript/discovery.js"/>"></script>
    <link href="<c:url value="/static/css/bookCard.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/searchBar.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/discovery.css"/>" rel="stylesheet"/>
    <link rel="shortcut icon" href="<c:url value='/static/images/favicon-claro.ico'/>" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>

    <!-- jQuery library -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- Popper JS library -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/2.9.3/umd/popper.min.js"></script>

    <!-- Bootstrap JS library -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.1.3/js/bootstrap.min.js"></script>

</head>
<body data-path="${path}" class = "body-class" >
<!-- Esto va a cambiar es un mockUp -->
<jsp:include page="../components/navBar.jsp"/>
<jsp:include page="../components/snackbarComponent.jsp"/>
<div class="main-class">

    <div class="container">
        <div class="row height d-flex justify-content-center align-items-center">
            <div class="">
                <div class="form">
                    <i class="fa fa-search"></i>
                    <input type="text" class="form-control form-input" placeholder="<spring:message code="discovery.search.placeholder"/>" id="search-bar" value="${search}">
                </div>
            </div>
        </div>
    </div>


    <div class="container-row">
        <div class="container-column" style="flex: 0 0 15%; margin: 10px;">

            <div class="btn-group mx-2 mb-4 mt-2" role="group">
                <button type="button" class="btn btn-light dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false" style="background-color: rgba(255, 255, 255, 0.3)">
                    <spring:message code="discovery.sort.title"/> : <spring:message code="enum.${sort.name()}.${sortDirection.name()}"/>
                </button>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" id="mostRecent"><spring:message code="discovery.sort.mostRecent"/></a></li>
                    <li><a class="dropdown-item" id="leastRecent"><spring:message code="discovery.sort.leastRecent"/></a></li>
                    <li><a class="dropdown-item" id="titleAscending"><spring:message code="discovery.sort.titleAscending"/></a></li>
                    <li><a class="dropdown-item" id="titleDescending"><spring:message code="discovery.sort.titleDescending"/></a></li>
                    <li><a class="dropdown-item" id="authorAscending"><spring:message code="discovery.sort.authorAscending"/></a></li>
                    <li><a class="dropdown-item" id="authorDescending"><spring:message code="discovery.sort.authorDescending"/></a></li>
                </ul>
            </div>

                <h5><spring:message code="discovery.filters.language"/></h5>
                <ul>
                    <c:choose>
                        <c:when test="${languagesFiltered.size() <= 0}">
                            <ul style="max-height: 200px; overflow-y: scroll">
                                <c:forEach var="language" items="${languages}" varStatus="status">
                                    <input class="form-check-input" type="checkbox" value="" id="language-${status.index}">
                                    <label class="form-check-label languageLabel" for="language-${status.index}" id="language-${status.index}-label"><span class="d-inline-block text-truncate" style="max-width: 150px;"><c:out value="${language}"/></span></label>
                                    <br>
                                </c:forEach>
                            </ul>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="language" items="${languagesFiltered}" varStatus="status">
                                <ul class="list-group">
                                    <li class="list-group-item language-filtered-item filtered-item"><span class="d-inline-block text-truncate" style="max-width: 150px; margin-right: 10px"><c:out value="${language}"/></span></li>
                                </ul>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </ul>
                <h5><spring:message code="discovery.filters.physicalCondition"/></h5>
                <ul>
                    <c:choose>
                        <c:when test="${physicalConditionsFiltered.size() <= 0}">
                            <ul style="max-height: 200px; overflow-y: scroll">
                                <c:forEach var="physicalCondition" items="${physicalConditions}" varStatus="status">
                                    <input class="form-check-input" type="checkbox" value="" id="physicalCondition-${status.index}">
                                    <label class="form-check-label physicalConditionLabel" for="physicalCondition-${status.index}" id="physicalCondition-${status.index}-label" data-physicalcondition="<c:out value="${physicalCondition}"/>">
                                        <span class="d-inline-block text-truncate" style="max-width: 150px;">
                                        <spring:message code="enum.${physicalCondition}"/>
                                        </span>
                                    </label>
                                    <br>
                                </c:forEach>
                            </ul>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="physicalCondition" items="${physicalConditionsFiltered}" varStatus="status">
                                <ul class="list-group">
                                    <li class="list-group-item physicalCondition-filtered-item filtered-item">
                                        <span class="d-inline-block text-truncate" style="max-width: 150px;">
                                            <spring:message code="enum.${physicalCondition}"/>
                                        </span>
                                    </li>
                                </ul>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </ul>

            <c:url	value="/discovery"	var="discoveryPageUrl"	/>
            <form:form method="get" action="${discoveryPageUrl}" modelAttribute="searchFilterSortForm" id="springForm">
                <input type ="hidden" name="currentPage" id="currentPageID" value="${page}"/>

                <input type ="hidden" name="sort" id="sort" value="${sort}"/>
                <input type ="hidden" name="sortDirection" id="sortDirection" value="${sortDirection}"/>

                <c:forEach var="language" items="${languagesFiltered}" varStatus="status">
                    <input type ="hidden" name="languages[${status.index}]" value="${language}"/>
                </c:forEach>

                <c:forEach var="physicalCondition" items="${physicalConditionsFiltered}" varStatus="status">
                    <input type ="hidden" name="physicalConditions[${status.index}]" value="${physicalCondition}"/>
                </c:forEach>


            </form:form>
            <div class="container-row-wrapped" style="margin-top: 10px; margin-bottom: 25px; width: 100%;">
                <input class="btn btn-light mx-2" type="submit" value="<spring:message code="discovery.filters.apply"/>" id="submit-filter" style="margin:10px; width: 100px"/>
                <a href="<c:url value="/discovery"/>">
                    <input type="button" class="btn btn-outline-dark mx-2" value="<spring:message code="discovery.filters.clear"/>" style="margin: 10px; width: 100px"/>
                </a>
            </div>
        </div>

        <div class="container-column" style="flex: 0 1 85%;">
            <c:if test="${books.size() > 0}">
                <div class="container-row-wrapped" style="margin: 20px auto; padding-top: 20px; background-color: rgba(255, 255, 255, 0.3); border-radius: 20px; width: 90%">
                    <c:forEach var="book" items="${books}">
                        <% request.setCharacterEncoding("utf-8"); %>
                        <jsp:include page="../components/bookCard.jsp">
                            <jsp:param name="id" value="${book.id}"/>
                            <jsp:param name="bookTitle" value="${book.book.name}"/>
                            <jsp:param name="bookAuthor" value="${book.book.author}"/>
                            <jsp:param name="imageId" value="${book.imageId}"/>
                        </jsp:include>
                    </c:forEach>
                </div>


                <div class="container-row-wrapped" style="margin-top: 25px; margin-bottom: 25px; width: 100%;">
                    <div>
                        <nav aria-label="Page navigation example">
                            <ul class="pagination justify-content-center align-items-center">
                                <li class="page-item">
                                    <button type="button" class="btn mx-5 pagination-button ${previousPage ? "" : "disabled"}" id="previousPageButton" style="border-color: rgba(255, 255, 255, 0)">
                                        <i class="bi bi-chevron-left"></i>  <spring:message code="paginationButton.previous"/>
                                    </button>
                                </li>

                                <li>
                                    <c:out value="${currentPage}"/> / <c:out value="${totalPages}"/>
                                </li>

                                <li class="page-item">
                                    <button type="button" class="btn mx-5 pagination-button ${nextPage ? "" : "disabled"}" id="nextPageButton" style="border-color: rgba(255, 255, 255, 0)">
                                        <spring:message code="paginationButton.next"/> <i class="bi bi-chevron-right"></i>
                                    </button>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </c:if>

            <c:if test="${books.size() <= 0}">
                <div class="container-row-wrapped" style="margin-top: 50px; width: 100%;">
                    <h1><spring:message code="discovery.noBooks" /></h1>
                </div>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
