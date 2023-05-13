<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="table-title">
    <div style="display: flex; align-items: center; justify-content: space-between;">
        <h2><spring:message code="borrowed_books"/></h2>
            <div>
                <jsp:include page="filterButton.jsp">
                    <jsp:param name="table" value="my_books"/>
                    <jsp:param name="filterValue" value="none"/>
                    <jsp:param name="filterAtribuite" value="none"/>
                    <jsp:param name="title" value="filterOption.all"/>
                    <jsp:param name="buttonText" value="userHomeView.all"/>
                    <jsp:param name="filter" value="${filter}"/>
                </jsp:include>

                <jsp:include page="filterButton.jsp">
                    <jsp:param name="table" value="my_books"/>
                    <jsp:param name="filterValue" value="public"/>
                    <jsp:param name="filterAtribuite" value="status"/>
                    <jsp:param name="title" value="filterOption.public"/>
                    <jsp:param name="buttonText" value="userHomeView.public"/>
                    <jsp:param name="filter" value="${filter}"/>
                </jsp:include>
                <jsp:include page="filterButton.jsp">
                    <jsp:param name="table" value="my_books"/>
                    <jsp:param name="filterValue" value="private"/>
                    <jsp:param name="filterAtribuite" value="status"/>
                    <jsp:param name="title" value="filterOption.private"/>
                    <jsp:param name="buttonText" value="userHomeView.private"/>
                    <jsp:param name="filter" value="${filter}"/>
                </jsp:include>

            </div>
        </div>
        <table class="table">
            <thead>
            <tr>
                <th><spring:message code="image"/></th>
                <jsp:include page="sortButton.jsp">
                    <jsp:param name="table" value="my_books"/>
                    <jsp:param name="attribute" value="book_name"/>
                    <jsp:param name="sortAttribute" value="${sort_book_name}"/>
                    <jsp:param name="title" value="book_name"/>
                </jsp:include>
                <jsp:include page="sortButton.jsp">
                    <jsp:param name="table" value="my_books"/>
                    <jsp:param name="attribute" value="author"/>
                    <jsp:param name="sortAttribute" value="${sort_author}"/>
                    <jsp:param name="title" value="author"/>
                </jsp:include>
                <jsp:include page="sortButton.jsp">
                    <jsp:param name="table" value="my_books"/>
                    <jsp:param name="attribute" value="language"/>
                    <jsp:param name="sortAttribute" value="${sort_language}"/>
                    <jsp:param name="title" value="language"/>
                </jsp:include>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${userAssets}" var="asset">
                <spring:message var="booksName" code='my_books' />
                <c:url var="userUrl" value="/myBookDetails?id=${asset.id}"/>
                <tr class="table-row-clickable" data-href="${userUrl}">
                    <td>
                        <img class="responsive-image" src="<c:url value='/getImage/${asset.imageId}'/>" alt="<c:out value='${asset.book.name}'/>"/>
                    </td>
                    <td><c:out value="${asset.book.name}"/></td>
                    <td><c:out value="${asset.book.author}"/></td>
                    <td><c:out value="${asset.book.language}"/></td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
        const tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
            return new bootstrap.Tooltip(tooltipTriggerEl)
        })
    })
    document.addEventListener('DOMContentLoaded', function () {
        var clickableRows = document.querySelectorAll('.table-row-clickable');
        clickableRows.forEach(function (row) {
            row.addEventListener('click', function () {
                window.location.href = row.getAttribute('data-href');
            });
        });
    });

</script>
