<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .filter-button {
        background-color: #2B3B2B;
        margin-right: 10px;
        border-radius: 20px;
    }
    .filter-button-selected {
        opacity: 1;
    }

    .filter-button:not(.filter-button-selected) {
        opacity: 0.6;
    }

    .table-row-clickable {
        cursor: pointer;
        margin-bottom: 10px;
        opacity: 0.8;
    }
    .table-row-clickable:hover {
        opacity: 1;
    }
    .sort-form {
        display: inline;
    }

    .sort-button {
        background: none;
        border: none;
        padding: 0;
        font: inherit;
        cursor: pointer;
        outline: inherit;
        color: inherit;
    }

    .sort-button:hover {
        color: #2B3B2B;
        opacity: 0.7;
    }

</style>
<c:choose>
    <c:when test="${isLender}">
        <div class="table-title">
            <div style="display: flex; align-items: center; justify-content: space-between;">
                <h2><spring:message code="lended_books"/></h2>
                <div>
                    <div class="d-inline-flex">
                        <c:url var="filterUrl" value="/applyFilter"/>
                        <form action="${filterUrl}" method="get">
                            <input type="hidden" name="table" value="lended_books">
                            <input type="hidden" name="filter" value="all">
                            <button type="submit" data-bs-toggle="tooltip" data-bs-placement="top" title="<spring:message code='filterOption.all'/>" class="btn btn-primary filter-button ${filter == 'all' && table == "lended_books" || filter == null ? 'filter-button-selected' : ''}">
                                <spring:message code="userHomeView.all" />
                            </button>
                        </form>
                    </div>
                    <div class="d-inline-flex">
                        <c:url var="filterUrl" value="/applyFilter"/>
                        <form action="${filterUrl}" method="get">
                            <input type="hidden" name="table" value="lended_books">
                            <input type="hidden" name="filter" value="pending">
                            <button type="submit" data-bs-toggle="tooltip" data-bs-placement="top" title="<spring:message code='filterOption.pending'/>" class="btn btn-primary filter-button ${filter == 'pending' && table == "lended_books" ? 'filter-button-selected' : ''}">
                                <spring:message code="userHomeView.pending" />
                            </button>
                        </form>
                    </div>
                    <div class="d-inline-flex">
                        <c:url var="filterUrl" value="/applyFilter"/>
                        <form action="${filterUrl}" method="get">
                            <input type="hidden" name="table" value="lended_books">
                            <input type="hidden" name="filter" value="confirmed">
                            <button type="submit" data-bs-toggle="tooltip" data-bs-placement="top" title="<spring:message code='filterOption.confirmed'/>" class="btn btn-primary filter-button ${filter == 'confirmed' && table == "lended_books" ? 'filter-button-selected' : ''}">
                                <spring:message code="userHomeView.inProgress" />
                            </button>
                        </form>
                    </div>
                    <div class="d-inline-flex">
                        <c:url var="filterUrl" value="/applyFilter"/>
                        <form action="${filterUrl}" method="get">
                            <input type="hidden" name="table" value="lended_books">
                            <input type="hidden" name="filter" value="delayed">
                            <button type="submit" data-bs-toggle="tooltip" data-bs-placement="top" title="<spring:message code='filterOption.delayed'/>" class="btn btn-primary filter-button ${filter == 'delayed' && table == "lended_books" ? 'filter-button-selected' : ''}">
                                <spring:message code="userHomeView.delayed" />
                            </button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="table-container">
                <table class="table">
                    <thead>
                    <tr>
                        <th><spring:message code="image"/></th>
                        <th>
                            <form action="/sortUserHomeAssets" method="get" class="sort-form">
                                <input type="hidden" name="table" value="lended_books" />
                                <input type="hidden" name="attribute" value="book_name" />
                                <input type="hidden" name="direction" value="${sort_book_name ? 'desc' : 'asc'}" />
                                <button type="submit" class="sort-button">
                                    <spring:message code="book_name"/>
                                    <i class="fas fa-arrow-<c:out value='${sort_book_name ? "up" : "down"}' />"></i>
                                </button>
                            </form>
                        </th>
                        <th>
                            <form action="/sortUserHomeAssets" method="get" class="sort-form">
                                <input type="hidden" name="table" value="lended_books" />
                                <input type="hidden" name="attribute" value="expected_retrieval_date" />
                                <input type="hidden" name="direction" value="${sort_expected_retrieval_date ? 'desc' : 'asc'}" />
                                <button type="submit" class="sort-button">
                                    <spring:message code="expected_retrieval_date"/>
                                    <i class="fas fa-arrow-<c:out value='${sort_expected_retrieval_date ? "up" : "down"}' />"></i>
                                </button>
                            </form>
                        </th>
                        <th>
                            <form action="/sortUserHomeAssets" method="get" class="sort-form">
                                <input type="hidden" name="table" value="lended_books" />
                                <input type="hidden" name="attribute" value="borrower_name" />
                                <input type="hidden" name="direction" value="${sort_borrower_name ? 'desc' : 'asc'}" />
                                <button type="submit" class="sort-button">
                                    <spring:message code="borrower_name"/>
                                    <i class="fas fa-arrow-<c:out value='${sort_borrower_name ? "up" : "down"}' />"></i>
                                </button>
                            </form>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${userAssets.lendedBooks}" var="asset">
                        <spring:message var="lendedBooksName" code='lendedBooks' />
                        <c:url var="userUrl" value="/userBookDetails/${asset.id}?table=${lendedBooksName}"/>
                        <tr class="table-row-clickable" data-href="${userUrl}">
                            <td>
                                <img class="responsive-image" src="<c:url value='/getImage/${asset.imageId}'/>" alt="<c:out value='${asset.book.name}'/>"/>
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
