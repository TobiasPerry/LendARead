<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal fade" id="changeIsReservableModal" tabindex="-1" role="dialog" aria-labelledby="${param.modalTitle}" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content rounded-3 border-0 shadow">
      <div class="modal-header border-0" style="text-align: center">
        <div class="icon-box">
          <i class="fas fa-calendar-alt"></i>
        </div>
        <h3 class="modal-title w-100 mt-2">
          <spring:message code="userHomeView.changeReservabilityTitle" />
        </h3>

      </div>
      <div class="modal-body text-center py-0 border-0">
        <p class="mb-4">
          <c:if test="${asset.isReservable}">
            <spring:message code="userHomeView.changeReservabilityText" />
          </c:if>
          <c:if test="${!asset.isReservable}">
            <spring:message code="userHomeView.changeReservabilityTextNo" />
          </c:if>
      </div>
      <div class="modal-footer border-0">
        <c:url var="changeStatusUrl" value="/changeStatus/${asset.id}"/>
        <form action="${changeStatusUrl}" method="post">
          <button type="submit" class="btn btn-primary rounded-pill px-4 py-2" style="background-color: #2B3B2B; border-color: #00B4A0;">
            <c:if test="${!asset.isReservable}">
              <spring:message code="userHomeView.changeReservabilitYes.button" />
            </c:if>
            <c:if test="${asset.isReservable}">
              <spring:message code="userHomeView.changeReservabilitNo.button" />
            </c:if>
          </button>
        </form>
      </div>
    </div>
  </div>
</div>

<script>
  const changeIsReservableHandler = document.getElementById('changeIsReservable');
  changeIsReservableHandler.addEventListener('click', function() {
    new bootstrap.Modal($('#changeIsReservableModal')).show();
  });
  const noButton55 = document.getElementById('no_button55');
  noButton55.addEventListener('click', function() {
    $('#changeIsReservableModal').modal('hide')
  });
</script>