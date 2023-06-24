<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal fade" id="cancelAssetModal" tabindex="-1" role="dialog" aria-labelledby="${param.modalTitle}"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content rounded-3 border-0 shadow">
            <div class="modal-header border-0" style="text-align: center">
                <div class="icon-box red-icon" style="background: #D45235;">
                    <i class="fas fa-times fa-lg"></i>
                </div>
                <h3 class="modal-title w-100 mt-2">
                    <spring:message code="userHomeView.cancelAssetTitle"/>
                </h3>
            </div>
            <div class="modal-body text-center py-0 border-0">
                <p class="mb-4">
                    <spring:message code="userHomeView.rejectAssetText"/>
            </div>
            <div class="modal-footer border-0">
                <c:url var="cancelAssetUrl" value="/cancelAsset/${lending.id}"/>
                <form action="${cancelAssetUrl}" method="post">
                    <button type="submit" class="btn-red-outline">
                        <spring:message code="cancel"/>
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    const rejectAssetBtnHandler = document.getElementById('cancelAssetBtn');
    rejectAssetBtnHandler.addEventListener('click', function () {
        new bootstrap.Modal($('#cancelAssetModal')).show();
    });
</script>