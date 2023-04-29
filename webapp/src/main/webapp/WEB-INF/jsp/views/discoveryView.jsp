<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/index.css"/>" rel="stylesheet"/>
    <script src="<c:url value="/static/javaScript/topbar.js"/>"></script>
    <link href="<c:url value="/static/css/bookCard.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/searchBar.css"/>" rel="stylesheet"/>
    <title>Lend a read</title>
    <link rel="shortcut icon" href="<c:url value='/static/images/favicon-claro.ico'/>" type="image/x-icon">
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
                    <input type="text" class="form-control form-input" placeholder="Search anything...">
                </div>
            </div>
        </div>
    </div>


    <div class="container-row">
        <div class="container-column" style="flex: 0 0 15%; margin: 10px;">

            <h5><spring:message code="discovery.filters.author"/></h5>
            <ul>
                <c:forEach var="author" items="${authors}" varStatus="status">
                    <input class="form-check-input" type="checkbox" value="" id="author-${status.index}">
                    <label class="form-check-label authorLabel" for="author-${status.index}" id="author-${status.index}-label"><c:out value="${author}"/></label>
                    <br>
                </c:forEach>
            </ul>
            <h5><spring:message code="discovery.filters.language"/></h5>
            <ul>
                <c:forEach var="language" items="${languages}" varStatus="status">
                    <input class="form-check-input" type="checkbox" value="" id="language-${status.index}">
                    <label class="form-check-label languageLabel" for="language-${status.index}" id="language-${status.index}-label"><c:out value="${language}"/></label>
                    <br>
                </c:forEach>
            </ul>
            <h5><spring:message code="discovery.filters.physicalCondition"/></h5>
            <ul>
                <c:forEach var="physicalCondition" items="${physicalConditions}" varStatus="status">
                    <input class="form-check-input" type="checkbox" value="" id="physicalCondition-${status.index}">
                    <label class="form-check-label physicalConditionLabel" for="physicalCondition-${status.index}" id="physicalCondition-${status.index}-label"><c:out value="${physicalCondition}"/></label>
                    <br>
                </c:forEach>
            </ul>

            <c:url	value="/discovery"	var="discoveryPageUrl"	/>
            <form:form method="get" action="${discoveryPageUrl}" modelAttribute="searchFilterSortForm" id="springForm">
                <input class="btn btn-light" type="submit" value="submit" id="submit-filter"/>
                <input type ="hidden" name="currentPage" id="currentPageID" value="1"/>
            </form:form>

        </div>

        <div class="container-column" style="flex: 0 1 85%;">
            <c:if test="${books.size() > 0}">
                <div class="container-row-wrapped" style="margin-top: 25px; width: 100%;">
                    <h1><spring:message code="discovery.title"/></h1>
                </div>

                <div class="container-row-wrapped" style="margin-top: 50px; width: 100%;">
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

                <div class="container-row-wrapped" style="margin-top: 25px; width: 100%;">
<%--                    PAGINATION--%>
<%--                    <jsp:include page="../components/paginationButton.jsp">--%>
<%--                        <jsp:param name="previous" value="${previousPage}"/>--%>
<%--                        <jsp:param name="next" value="${nextPage}"/>--%>
<%--                        <jsp:param name="page" value="${page}"/>--%>
<%--                    </jsp:include>--%>

                    <div>
                        <nav aria-label="Page navigation example">
                            <ul class="pagination justify-content-center">
                                <c:choose>
                                    <c:when test="${previousPage == false}">
                                        <li class="page-item disabled">
                                            <p class="page-link"><spring:message code="paginationButton.previous" /></p>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item">
                                            <input type="button" id="previousPageButton" value="<spring:message code="paginationButton.previous" />"/>
                                        </li>
                                    </c:otherwise>
                                </c:choose>

                                <c:choose>
                                    <c:when test="${nextPage == false}">
                                        <li class="page-item disabled">
                                            <p class="page-link"><spring:message code="paginationButton.next" /></p>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item">
<%--                                            <input type="button" id="nextPageButton" value="<spring:message code="paginationButton.next" />"/>--%>
                                        </li>
                                    </c:otherwise>
                                </c:choose>

                            </ul>
                        </nav>
                    </div>
    <input type="button" id="nextPageButton" value="<spring:message code="paginationButton.next" />"/>
<%--                    PAGINATION--%>
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

<script>
    document.getElementById("submit-filter").addEventListener("click", () => {
        event.preventDefault(); // Prevent the default form submission behavior
        let i = 0
        let j = 0
        for (const author of document.getElementsByClassName("authorLabel")) {
                if (document.getElementById("author-" + i).checked) {
                    document.getElementById("springForm").innerHTML += `<input type ="hidden" name="authors[` + j + `]" id="authorId-` + j + `">`
                    document.getElementById("authorId-" + j).value = document.getElementById("author-" + i + "-label").childNodes[0].textContent
                    console.log(document.getElementById("author-" + i + "-label").childNodes[0].textContent)
                    j++
                }
                i++
            }
            i = 0
            j = 0
            for (const author of document.getElementsByClassName("languageLabel")) {
                if(document.getElementById("language-" + i).checked){
                    document.getElementById("springForm").innerHTML += `<input type ="hidden" name="languages[` + j + `]" id="languageId-` + j + `">`
                    document.getElementById("languageId-" + j).value = document.getElementById("language-" + i + "-label").childNodes[0].textContent
                    console.log(document.getElementById("language-" + i + "-label").childNodes[0].textContent)
                    j++
                }
                i++
            }
            i = 0
            j = 0
            for (const author of document.getElementsByClassName("physicalConditionLabel")) {
                if(document.getElementById("physicalCondition-" + i).checked){
                    document.getElementById("springForm").innerHTML += `<input type ="hidden" name="physicalConditions[` + j + `]" id="physicalConditionId-` + j + `">`
                    document.getElementById("physicalConditionId-" + j).value = document.getElementById("physicalCondition-" + i + "-label").childNodes[0].textContent
                    console.log(document.getElementById("physicalCondition-" + i + "-label").childNodes[0].textContent)
                    j++
                }
                i++
            }
        document.getElementById("springForm").submit();
    }, true)

    document.getElementById("previousPageButton").addEventListener("click", () => {
        event.preventDefault(); // Prevent the default form submission behavior
        document.getElementById("currentPageID").value = document.getElementById("currentPageID").value - 1
        document.getElementById("springForm").submit();
    });

    document.getElementById("nextPageButton").addEventListener("click", () => {
        event.preventDefault(); // Prevent the default form submission behavior
        document.getElementById("currentPageID").value = document.getElementById("currentPageID").value + 1
        document.getElementById("springForm").submit();
    });
</script>
