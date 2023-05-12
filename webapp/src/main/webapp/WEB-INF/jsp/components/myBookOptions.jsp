<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div>
        <button id="privatePublicBtn" class="btn-green" type="submit">
            <c:choose>
                <c:when test="${asset.assetState.isPublic()}">
                    <spring:message code="public"/>
                </c:when>
                <c:otherwise>
                    <spring:message code="private"/>
                </c:otherwise>
            </c:choose>
        </button>

        <button class="btn-green " type="submit">
            <spring:message code="delete" />
        </button>
</div>


<div class="modal fade" id="myModal" data-bs-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="${param.modalTitle}" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content rounded-3 border-0 shadow">
            <div class="modal-header border-0" style="text-align: center">
                <div class="icon-box">
                    <c:choose>
                        <c:when test="${modalType == 'changeBookVisibility'}">
                            <i class="fas fa-eye-slash fa-lg"></i>
                        </c:when>
                        <c:when test="${modalType == 'deleteBook'}">
                            <i class="fas fa-trash fa-lg"></i>
                        </c:when>
                    </c:choose>
                </div>
                <h3 class="modal-title w-100 mt-2">
                    <c:choose>
                        <c:when test="${modalType == 'changeBookVisibility'}">
                            <spring:message code="userHomeView.changeVisibilityTitle" />
                        </c:when>
                        <c:when test="${modalType == 'deleteBook'}">
                            <spring:message code="userHomeView.deleteBookTitle" />
                        </c:when>
                    </c:choose>
                </h3>
            </div>
            <div class="modal-body text-center py-0 border-0">
                <p class="mb-4">
                    <c:choose>
                        <c:when test="${modalType == 'changeBookVisibility'}">
                            <spring:message code="userHomeView.changeVisibilityText" />
                        </c:when>
                        <c:when test="${modalType == 'deleteBook'}">
                            <spring:message code="userHomeView.deleteBookText" />
                        </c:when>
                    </c:choose>
                    <c:out value="${param.text}" /></p>
            </div>
            <div class="modal-footer border-0">
                <c:choose>
                    <c:when test="${modalType == 'changeBookVisibility'}">
                        <c:url var="changeStatusUrl" value="/changeStatus/${assetId}"/>
                        <form action="${changeStatusUrl}" method="post">
                            <button type="submit" class="btn btn-primary rounded-pill px-4 py-2" style="background-color: #2B3B2B; border-color: #00B4A0;">
                                <spring:message code="yes" />
                            </button>
                        </form>
                        <c:url var="userHomeUrl" value="/userHome"/>
                        <form action="${userHomeUrl}" method="get">
                            <button type="submit" class="btn btn-primary rounded-pill px-4 py-2" style="background-color: #2B3B2B; border-color: #00B4A0;">
                                <spring:message code="no" />
                            </button>
                        </form>
                    </c:when>
                    <c:when test="${modalType == 'deleteBook'}">
                        <c:url var="deleteAssetUrl" value="/deleteAsset/${assetId}"/>
                        <form action="${deleteAssetUrl}" method="post">
                            <button type="submit" class="btn btn-primary rounded-pill px-4 py-2" style="background-color: #2B3B2B; border-color: #00B4A0;">
                                <spring:message code="yes" />
                            </button>
                        </form>
                        <c:url var="userHomeUrl" value="/userHome"/>
                        <form action="${userHomeUrl}" method="get">
                            <button type="submit" class="btn btn-primary rounded-pill px-4 py-2" style="background-color: #2B3B2B; border-color: #00B4A0;">
                                <spring:message code="no" />
                            </button>
                        </form>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </div>
</div>
