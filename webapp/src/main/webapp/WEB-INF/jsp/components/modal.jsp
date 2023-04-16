<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: martinippo
  Date: 16/4/23
  Time: 20:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle"><c:out value="${param.title}"/></h5>
      </div>
      <div class="modal-body">
        <c:out value="${param.text}"/>
      </div>
      <div class="modal-footer">
        <a type="button" class="btn btn-primary" href="<c:url value="/"/>">Seguir explorando</a>
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