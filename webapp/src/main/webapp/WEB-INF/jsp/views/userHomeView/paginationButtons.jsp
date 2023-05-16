<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="container-row-wrapped" style="margin-top: 25px; margin-bottom: 25px; width: 100%;">
    <div>
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center align-items-center">
                <li class="page-item">
                    <c:url var="nextUserHomePageUrl" value="/nextUserHomePage"/>
                    <form action="${nextUserHomePageUrl}" method="get">
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
                    <c:url var="nextUserHomePageUrl" value="/nextUserHomePage"/>
                    <form action="${nextUserHomePageUrl}" method="get">
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
