<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <div class="table-title">
        <div style="display: flex; align-items: center; justify-content: space-between;">
            <h2><spring:message code="borrowed_books"/></h2>
            <div>
                <jsp:include page="filterButton.jsp">
                    <jsp:param name="table" value="borrowed_books"/>
                    <jsp:param name="filterValue" value="none"/>
                    <jsp:param name="filterAtribuite" value="none"/>
                    <jsp:param name="title" value="filterOption.all"/>
                    <jsp:param name="buttonText" value="userHomeView.all"/>
                    <jsp:param name="filter" value="${filter}"/>
                </jsp:include>

                <jsp:include page="filterButton.jsp">
                    <jsp:param name="table" value="borrowed_books"/>
                    <jsp:param name="filterValue" value="pending"/>
                    <jsp:param name="filterAtribuite" value="status"/>
                    <jsp:param name="title" value="filterOption.pending"/>
                    <jsp:param name="buttonText" value="userHomeView.pending"/>
                    <jsp:param name="filter" value="${filter}"/>
                </jsp:include>
                <jsp:include page="filterButton.jsp">
                    <jsp:param name="table" value="borrowed_books"/>
                    <jsp:param name="filterValue" value="borrowed"/>
                    <jsp:param name="filterAtribuite" value="status"/>
                    <jsp:param name="title" value="filterOption.confirmed"/>
                    <jsp:param name="buttonText" value="userHomeView.inProgress"/>
                    <jsp:param name="filter" value="${filter}"/>
                </jsp:include>

                <jsp:include page="filterButton.jsp">
                    <jsp:param name="table" value="borrowed_books"/>
                    <jsp:param name="filterValue" value="delayed"/>
                    <jsp:param name="filterAtribuite" value="status"/>
                    <jsp:param name="title" value="filterOption.delayed"/>
                    <jsp:param name="buttonText" value="userHomeView.delayed"/>
                    <jsp:param name="filter" value="${filter}"/>
                </jsp:include>

            </div>
        </div>
        <div class="table-container">
            <table class="table">
                <thead>
                <tr>
                    <th style="opacity: 0.7"><spring:message code="image"/></th>
                    <jsp:include page="sortButton.jsp">
                        <jsp:param name="table" value="borrowed_books"/>
                        <jsp:param name="attribute" value="book_name"/>
                        <jsp:param name="sortAttribute" value="${sort_book_name}"/>
                        <jsp:param name="title" value="book_name"/>
                    </jsp:include>

                    <jsp:include page="sortButton.jsp">
                        <jsp:param name="table" value="borrowed_books"/>
                        <jsp:param name="attribute" value="expected_retrieval_date"/>
                        <jsp:param name="sortAttribute" value="${sort_expected_retrieval_date}"/>
                        <jsp:param name="title" value="expected_retrieval_date"/>
                    </jsp:include>

                    <jsp:include page="sortButton.jsp">
                        <jsp:param name="table" value="borrowed_books"/>
                        <jsp:param name="attribute" value="borrower_name"/>
                        <jsp:param name="sortAttribute" value="${sort_borrower_name}"/>
                        <jsp:param name="title" value="borrower_name"/>
                    </jsp:include>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${userAssets}" var="asset">
                    <spring:message var="lendedBooksName" code='borrowed_books' />
                    <c:url var="userUrl" value="/borrowedBookDetails?lendingId=${asset.lendingId}"/>
                    <tr class="table-row-clickable" data-href="${userUrl}">
                        <td>
                            <img style="height: 250px; width: 150px; object-fit: cover" src="<c:url value='/getImage/${asset.imageId}'/>" alt="<c:out value='${asset.book.name}'/>"/>
                        </td>
                        <td><c:out value="${asset.book.name}"/></td>
                        <td><c:out value="${asset.dueDate}"/></td>
                        <td><c:out value="${asset.borrower}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
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