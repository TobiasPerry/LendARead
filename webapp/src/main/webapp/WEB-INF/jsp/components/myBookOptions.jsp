<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
        integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
        crossorigin="anonymous"></script>
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

        <button id="deleteBtn" class="btn-green " type="submit">
            <spring:message code="delete" />
        </button>
</div>


<div class="modal fade" id="deleteBookModal" data-bs-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="${param.modalTitle}" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content rounded-3 border-0 shadow">
            <div class="modal-header border-0" style="text-align: center">
                <div class="icon-box">
                    <i class="fas fa-trash fa-lg"></i>
                </div>
                <h3 class="modal-title w-100 mt-2">
                    <spring:message code="userHomeView.deleteBookTitle" />
                </h3>
            </div>
            <div class="modal-body text-center py-0 border-0">
                <p class="mb-4">
                    <spring:message code="userHomeView.deleteBookText" />
            </div>
            <div class="modal-footer border-0">
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
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="changeStatusModal" data-bs-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="${param.modalTitle}" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content rounded-3 border-0 shadow">
            <div class="modal-header border-0" style="text-align: center">
                <div class="icon-box">
                    <i class="fas fa-eye-slash fa-lg"></i>

                </div>
                <h3 class="modal-title w-100 mt-2">
                    <spring:message code="userHomeView.changeVisibilityTitle" />
                </h3>
            </div>
            <div class="modal-body text-center py-0 border-0">
                <p class="mb-4">
                    <spring:message code="userHomeView.changeVisibilityText" />
            </div>
            <div class="modal-footer border-0">
                <c:url var="changeStatusUrl" value="/changeStatus/${asset.id}"/>
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
            </div>
        </div>
    </div>
</div>

<script>
    const publicPrivateBtnHandler = document.getElementById('publicPrivateBtn');
    const deleteBookModal = document.getElementById('deleteBookModal');
    const deleteModal = new bootstrap.Modal(deleteBookModal);
    publicPrivateBtnHandler.addEventListener('click', function() {
        deleteModal.show();
    });

    const deleteBtnHandler = document.getElementById('deleteBtn');
    const changeStatusModal = document.getElementById('changeStatusModal');
    const myModal = new bootstrap.Modal(changeStatusModal);
    deleteBtnHandler.addEventListener('click', function() {
        myModal.show();
    });
</script>


