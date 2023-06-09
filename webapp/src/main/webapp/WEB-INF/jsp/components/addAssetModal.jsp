
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<link rel="stylesheet" href="<c:url value="/static/css/modal.css"/>">
<link rel="stylesheet" href="<c:url value="/static/css/main.css"/>">

<style>
  .form-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    background-color: #D0DCD0;
    border-radius: 15px;
    padding: 20px;
  }
  .form-group {
    flex: 0 0 calc(50% - 10px);
    margin-bottom: 10px;
  }
  .title-input-edit {
    font-size: 1.5em;
    font-weight: bold;
  }

</style>

<div class="modal fade" id="borrowUser" data-bs-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="${param.modalTitle}" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content rounded-3 border-0 shadow">
      <div class="modal-header border-0" style="text-align: center">
        <div class="icon-box">
          <i class="fas fa-book-reader"></i>
        </div>
        <h3 class="modal-title w-100 mt-2"><c:out value="${param.modalTitle}" /></h3>
      </div>
      <form action="<c:url value="/defaultLocation"/>" method="post">
        <div class="modal-body text-center py-0 border-0">
          <p class="mb-4"><c:out value="${param.text}" /></p>
          <div class="form-container">
            <spring:message code="addAssetView.titleLabel" var="titleLabel"/>
            <input type="text" class="form-control title-input-edit" id="name" name="name" required maxlength="100" placeholder="${titleLabel}">
            <div class="form-group">
              <label for="zipcode"><spring:message code="addAssetView.placeholders.zipcode"/> </label>
              <input type="text" class="form-control" id="zipcode" name="zipcode" required pattern="^[a-zA-Z0-9]+$" maxlength="100">
            </div>
            <div class="form-group">
              <label for="locality"><spring:message code="addAssetView.localityLabel"/> </label>
              <input type="text" class="form-control" id="locality" name="locality" required maxlength="100">
            </div>
            <div class="form-group">
              <label for="province"><spring:message code="addAssetView.placeholders.province"/> </label>
              <input type="text" class="form-control" id="province" name="province" required maxlength="100">
            </div>
            <div class="form-group">
              <label for="country"><spring:message code="addAssetView.countryLabel"/></label>
              <input type="text" class="form-control" id="country" name="country" required maxlength="100">
            </div>
          </div>
          <input type="hidden" name="id" value="${location.id == null ? -1 : location.id}">
        </div>
        <div class="modal-footer border-0">
          <a class="btn btn-outline-dark mx-2 rounded-pill px-4 py-2" href="<c:url value="/discovery"/>">
            <spring:message code="addAssetView.buttonNoThanks"/>
          </a>
          <button type="submit" class="btn btn-primary rounded-pill px-4 py-2" style="background-color: #2B3B2B; border-color: #00B4A0;">
            <spring:message code="addAssetView.changeRole" />
          </button>
        </div>
      </form>
    </div>
  </div>
</div>


<script>
  const borrowUserModal = '${borrowerUser}' === 'true';
  if (borrowUserModal) {
    new bootstrap.Modal($('#borrowUser')).show();

  }
</script>
