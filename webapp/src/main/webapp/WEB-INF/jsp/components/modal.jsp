<%--
  Created by IntelliJ IDEA.
  User: martinippo
  Date: 16/4/23
  Time: 20:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="staticBackdropLabel">${param.title}</h1>
      </div>
      <div class="modal-body">
        ${param.text}
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary">Volver a inicio</button>
      </div>
    </div>
  </div>
</div>