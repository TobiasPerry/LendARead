<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:choose>
    <c:when test="${isLender}">
        <div class="table-title">
            <h2><spring:message code="lended_books"/></h2>
            <div class="table-container">
                <table class="table">
                    <thead>
                    <tr>
                        <th><spring:message code="image"/></th>
                        <th><spring:message code="book_name"/></th>
                        <th><spring:message code="author"/></th>
                        <th><spring:message code="isbn"/></th>
                        <th><spring:message code="language"/></th>
                        <th><spring:message code="expected_retrieval_date"/></th>
                        <th><spring:message code="borrower_name"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${userAssets.lendedBooks}" var="asset">
                        <tr>
                            <td><img class="image" src="<c:url value="/getImage/${asset.imageId}"/>"
                                     alt="<c:out value="${asset.book.name}"/>"/></td>
                            <td><c:out value="${asset.book.name}"/></td>
                            <td><c:out value="${asset.book.author}"/></td>
                            <td><c:out value="${asset.book.isbn}"/></td>
                            <td><c:out value="${asset.book.language}"/></td>
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

