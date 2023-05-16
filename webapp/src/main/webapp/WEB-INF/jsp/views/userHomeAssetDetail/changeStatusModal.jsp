<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
          <c:if test="${asset.assetState.isPrivate()}">
          <spring:message code="userHomeView.changeVisibilityTextPublic" />
            </c:if>
            <c:if test="${asset.assetState.isPublic()}">
              <spring:message code="userHomeView.changeVisibilityTextPrivate" />
            </c:if>
      </div>
      <div class="modal-footer border-0">
        <c:url var="changeStatusUrl" value="/changeStatus/${asset.id}"/>
        <form action="${changeStatusUrl}" method="post">
          <button type="submit" class="btn btn-primary rounded-pill px-4 py-2" style="background-color: #2B3B2B; border-color: #00B4A0;">
            <spring:message code="yes" />
          </button>
        </form>
          <button id="no_button2" class="btn btn-primary rounded-pill px-4 py-2" style="background-color: #2B3B2B; border-color: #00B4A0;">
            <spring:message code="no" />
          </button>
      </div>
    </div>
  </div>
</div>

<script>
  const publicPrivateBtnHandler = document.getElementById('privatePublicBtn');
  publicPrivateBtnHandler.addEventListener('click', function() {
    new bootstrap.Modal($('#changeStatusModal')).show();
  });
  const noButton1 = document.getElementById('no_button2');
  noButton1.addEventListener('click', function() {
    $('#changeStatusModal').modal('hide')
  });
</script>