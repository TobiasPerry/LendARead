<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="table-title">
    <h2><spring:message code="my_books"/></h2>
    <div class="table-container">
        <table class="table">
            <thead>
            <tr>
                <th><spring:message code="image"/></th>
                <th><spring:message code="book_name"/></th>
                <th><spring:message code="author"/></th>
                <th><spring:message code="language"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${userAssets.myBooks}" var="asset">
                <tr>
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
</div>
