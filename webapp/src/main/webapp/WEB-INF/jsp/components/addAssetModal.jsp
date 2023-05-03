
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<link rel="stylesheet" href="<c:url value="/static/css/modal.css"/>">
<div class="modal fade" id="borrowUser" data-bs-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="${param.modalTitle}" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content rounded-3 border-0 shadow">
      <div class="modal-header border-0" style="text-align: center">
        <div class="icon-box">
          <i class="fas fa-book-reader"></i>
        </div>
        <h3 class="modal-title w-100 mt-2"><c:out value="${param.modalTitle}" /></h3>
      </div>
      <div class="modal-body text-center py-0 border-0">
        <p class="mb-4"><c:out value="${param.text}" /></p>
      </div>
      <div class="modal-footer border-0">
        <form action="<c:url value="/changeRole"/>" method="post">
          <button type="submit" class="btn btn-primary rounded-pill px-4 py-2" style="background-color: #2B3B2B; border-color: #00B4A0;">
            <spring:message code="addAssetView.changeRole" />
          </button>
        </form>
      </div>
    </div>
  </div>
</div>


<script>
  const borrowUserModal = '${borrowerUser}' === 'true';
  if (borrowUserModal) {
    new bootstrap.Modal($('#borrowUser')).show();

  }
</script>
