<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<link rel="stylesheet" href="<c:url value="/static/css/modal.css"/>">
<div class="modal fade" id="returnAssetModal"  tabindex="-1" role="dialog" aria-labelledby="${param.modalTitle}" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content rounded-3 border-0 shadow">
            <div class="modal-header border-0" style="text-align: center">
                <div class="icon-box">
                    <i class="fas fa-reply fa-lg"></i>
                </div>
                <h3 class="modal-title w-100 mt-2">
                    <spring:message code="userHomeView.returnAssetTitle" />
                </h3>
            </div>
            <div class="modal-body text-center py-0 border-0">
                <p class="mb-4">
                    <spring:message code="userHomeView.returnAssetText" />
            </div>
            <div class="modal-footer border-0">
                <c:url var="changeStatusUrl" value="/returnAsset/${asset.lendingId}"/>
                <form action="${changeStatusUrl}" method="post">
                    <button type="submit" class="btn btn-primary rounded-pill px-4 py-2" style="background-color: #2B3B2B; border-color: #00B4A0;">
                        <spring:message code="yes" />
                    </button>
                </form>
                <button id="no_button" class="btn btn-primary rounded-pill px-4 py-2" style="background-color: #2B3B2B; border-color: #00B4A0;">
                        <spring:message code="no" />
                </button>
            </div>
        </div>
    </div>
</div>

<script>
    const returnBtnHandler = document.getElementById('returnAssetBtn');
    returnBtnHandler.addEventListener('click', function() {
        new bootstrap.Modal($('#returnAssetModal')).show();
    });
    const noButton = document.getElementById('no_button');
    noButton.addEventListener('click', function() {
        $('#returnAssetModal').modal('hide')
    });
</script>