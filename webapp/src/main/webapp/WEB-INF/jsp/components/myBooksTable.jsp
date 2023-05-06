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
                <%--            <th><spring:message code="description" /></th>--%>
                <th><spring:message code="status"/></th>
                <th><spring:message code="delete"/></th>
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
                    <td>
                        <form action="<c:url value="/showChangeVisibilityModal?assetId=${asset.id}"/>"
                              method="post">
                            <button class="button-status" type="submit">
                                <c:choose>
                                    <c:when test="${asset.assetState.isPublic()}">
                                        <spring:message code="public"/>
                                    </c:when>
                                    <c:otherwise>
                                        <spring:message code="private"/>
                                    </c:otherwise>
                                </c:choose>
                            </button>
                        </form>
                    </td>
                    <td>
                        <form action="<c:url value="/deleteAssetModal?assetId=${asset.id}"/>"
                              method="post" style="display:inline;">
                            <button class="btn btn-link p-0 " type="submit">
                                <i class="fas fa-trash icon-style"></i>
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
