<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <c:choose>
        <c:when test="${isLender}">
            <div class="table-title">
                <div style="display: flex; align-items: center; justify-content: space-between;">
                    <h2><spring:message code="my_books"/></h2>
                    <div>
                        <jsp:include page="filterButton.jsp">
                            <jsp:param name="table" value="my_books"/>
                            <jsp:param name="filterValue" value="none"/>
                            <jsp:param name="filterAtribuite" value="none"/>
                            <jsp:param name="attribute" value="${attribute}"/>
                            <jsp:param name="sortAttribute" value="${sortAttribute}"/>
                            <jsp:param name="title" value="filterOption.all"/>
                            <jsp:param name="buttonText" value="userHomeView.all"/>
                            <jsp:param name="filter" value="${filter}"/>
                            <jsp:param name="currentPage" value="${currentPage}"/>
                        </jsp:include>

                        <jsp:include page="filterButton.jsp">
                            <jsp:param name="table" value="my_books"/>
                            <jsp:param name="filterValue" value="public"/>
                            <jsp:param name="filterAtribuite" value="status"/>
                            <jsp:param name="attribute" value="${attribute}"/>
                            <jsp:param name="sortAttribute" value="${sortAttribute}"/>
                            <jsp:param name="title" value="filterOption.public"/>
                            <jsp:param name="buttonText" value="userHomeView.public"/>
                            <jsp:param name="filter" value="${filter}"/>
                            <jsp:param name="currentPage" value="${currentPage}"/>
                        </jsp:include>
                        <jsp:include page="filterButton.jsp">
                            <jsp:param name="table" value="my_books"/>
                            <jsp:param name="filterValue" value="private"/>
                            <jsp:param name="filterAtribuite" value="status"/>
                            <jsp:param name="attribute" value="${attribute}"/>
                            <jsp:param name="sortAttribute" value="${sortAttribute}"/>
                            <jsp:param name="title" value="filterOption.private"/>
                            <jsp:param name="buttonText" value="userHomeView.private"/>
                            <jsp:param name="filter" value="${filter}"/>
                            <jsp:param name="currentPage" value="${currentPage}"/>
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
                            <jsp:param name="filterValue" value="${filterValue}"/>
                            <jsp:param name="filterAtribuite" value="${filterAtribuite}"/>
                            <jsp:param name="currentPage" value="${currentPage}"/>
                            <jsp:param name="title" value="book_name"/>
                        </jsp:include>
                        <jsp:include page="sortButton.jsp">
                            <jsp:param name="table" value="my_books"/>
                            <jsp:param name="attribute" value="author"/>
                            <jsp:param name="sortAttribute" value="${sort_author}"/>
                            <jsp:param name="filterValue" value="${filterValue}"/>
                            <jsp:param name="filterAtribuite" value="${filterAtribuite}"/>
                            <jsp:param name="currentPage" value="${currentPage}"/>
                            <jsp:param name="title" value="author"/>
                        </jsp:include>
                        <jsp:include page="sortButton.jsp">
                            <jsp:param name="table" value="my_books"/>
                            <jsp:param name="attribute" value="language"/>
                            <jsp:param name="sortAttribute" value="${sort_language}"/>
                            <jsp:param name="filterValue" value="${filterValue}"/>
                            <jsp:param name="filterAtribuite" value="${filterAtribuite}"/>
                            <jsp:param name="currentPage" value="${currentPage}"/>
                            <jsp:param name="title" value="language"/>
                        </jsp:include>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${userAssets}" var="asset">
                        <spring:message var="booksName" code='my_books' />
                        <c:url var="userUrl" value="/myBookDetails/${asset.id}"/>
                        <tr class="table-row-clickable" data-href="${userUrl}">
                            <td>
                                <img style="height: 250px; width: 150px; object-fit: cover" src="<c:url value='/getImage/${asset.imageId}'/>" alt="<c:out value='${asset.book.name}'/>"/>
                            </td>
                            <td><c:out value="${asset.book.name}"/></td>
                            <td><c:out value="${asset.book.author}"/></td>
                            <td><c:out value="${asset.book.language}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:when>
        <c:otherwise>
            <div class="promo-box">
                <h2><spring:message code="become_lender.title"/></h2>
                <p><spring:message code="become_lender.subtitle"/></p>
                <form action="<c:url value="/changeRole"/>" method="post">
                    <button type="submit" class="button-status"><spring:message
                            code="become_lender.button"/></button>
                </form>
            </div>
        </c:otherwise>
    </c:choose>
    <div class="container-row-wrapped" style="margin-top: 25px; margin-bottom: 25px; width: 100%;">
        <div>
            <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center align-items-center">
                    <li class="page-item">
                        <c:url var="filterUrl" value="/applyFilter"/>
                        <form action="${filterUrl}" method="get">
                            <input type="hidden" name="table" value="${table}">
                            <input type="hidden" name="direction" value="${direction}" />
                            <input type="hidden" name="attribute" value="${attribute}" />
                            <input type="hidden" name="filterValue" value="${filterValue}">
                            <input type="hidden" name="filterAtribuite" value="${filterAtribuite}">
                            <input type="hidden" name="currentPage" value="${currentPage - 1}">
                            <button type="submit" class="btn mx-5 pagination-button ${previousPage ? "" : "disabled"}" style="border-color: rgba(255, 255, 255, 0)">
                                <i class="bi bi-chevron-left"></i>  <spring:message code="paginationButton.previous"/>
                            </button>
                        </form>
                    </li>

                    <li>
                        <c:out value="${currentPage}"/> / <c:out value="${totalPages}"/>
                    </li>

                    <li class="page-item">
                        <c:url var="filterUrl" value="/applyFilter"/>
                        <form action="${filterUrl}" method="get">
                            <input type="hidden" name="table" value="${table}">
                            <input type="hidden" name="direction" value="${direction}" />
                            <input type="hidden" name="attribute" value="${attribute}" />
                            <input type="hidden" name="filterValue" value="${filterValue}">
                            <input type="hidden" name="filterAtribuite" value="${filterAtribuite}">
                            <input type="hidden" name="currentPage" value="${currentPage + 1}">
                            <button type="submit" class="btn mx-5 pagination-button ${nextPage ? "" : "disabled"}" style="border-color: rgba(255, 255, 255, 0)">
                                <spring:message code="paginationButton.next"/> <i class="bi bi-chevron-right"></i>
                            </button>
                        </form>

                    </li>
                </ul>
            </nav>
        </div>
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
