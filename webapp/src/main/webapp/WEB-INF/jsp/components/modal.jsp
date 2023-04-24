<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div class="modal fade" id="myModal" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="${param.modalTitle}" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle"><c:out value="${param.modalTitle}"/></h5>
      </div>
      <div class="modal-body">
        <c:out value="${param.text}"/>
      </div>
      <div class="modal-footer">
        <a type="button" class="btn btn-primary" href="<c:url value="/"/>"><spring:message code="exploringModal.continue" /></a>
      </div>
    </div>
  </div>
</div>

<script>
  const showSnackbarSucess = '${showSnackbarSucess}' === 'true';
  if (showSnackbarSucess) {
    $('#myModal').modal();
  }
</script>