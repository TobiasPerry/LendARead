<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:useBean id="formElements" scope="session" class="ar.edu.itba.paw.webapp.form.FormServiceAddAssetView"/>

<head>
    <title>Prestar Libro</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <script src="<c:url value="/static/javaScript/topbar.js"/>"></script>
    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
</head>

<body>

<jsp:include page="../components/navBar.jsp"/>
<div class="container my-5">

    <jsp:include page="../components/snackbarComponent.jsp" />

    <h1 class="text-center mb-5">Quieres prestar un libro?</h1>
    <div class="p-4 rounded" >
        <div class="row">

            <div class="col-md-6 d-flex flex-column align-items-center">
                <div class="image-container position-relative">
                    <img src="#" alt="Book Cover" class="img-fluid" id="bookImage" style="max-width: 100%; max-height: 100%;">
                    <label for="uploadImage" class="position-absolute bottom-0 end-0 btn btn-primary" id="uploadLabel">
                        <i class="bi bi-cloud-upload"></i> Subir foto
<%--                        image input--%>
                    </label>
                </div>
            </div>

            <div class="col-md-6">
                <c:url var="addAssetUrl" value="/addAsset"/>
                <form:form modelAttribute="addAssetForm" method="post" action="${addAssetUrl}" enctype="multipart/form-data" class="container">
                        <div class="book-info-container">
                            <h2>Libro:</h2>
                            <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="physicalCondition" class="form-label">Estado:</label>
                                <form:select path="physicalCondition" id="physicalCondition" class="form-control">
                                    <form:option value="asnew">Nuevo</form:option>
                                    <form:option value="fine">Casi nuevo</form:option>
                                    <form:option value="verygood">Muy bien</form:option>
                                    <form:option value="good">Bien</form:option>
                                    <form:option value="fair">Aceptable</form:option>
                                    <form:option value="poor">Pobre</form:option>
                                    <form:option value="exlibrary">Ex-biblioteca</form:option>
                                    <form:option value="bookclub">Book club</form:option>
                                    <form:option value="bindingcopy">Dorso dañado</form:option>
                                </form:select>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="isbn" class="form-label">ISBN:</label>
                                <form:input path="isbn" id="isbn" placeholder="ISBN" class="form-control"/>
                                <form:errors path="isbn" cssClass="text-danger small" element="small"/>
                            </div>

                        </div>
                    </div>
                    <div>
                        <h2>Ubicacion: </h2>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="locality" class="form-label">Localidad:</label>
                                <form:input path="locality" id="locality" placeholder="Locality" class="form-control"/>
                                <form:errors path="locality" cssClass="text-danger small" element="small"/>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="province" class="form-label">Provincia:</label>
                                <form:input path="province" id="province" placeholder="Province" class="form-control"/>
                                <form:errors path="province" cssClass="text-danger small" element="small"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="country" class="form-label">Pais:</label>
                                <form:input path="country" id="country" placeholder="Country" class="form-control"/>
                                <form:errors path="country" cssClass="text-danger small" element="small"/>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="zipcode" class="form-label">Codigo postal:</label>
                                <form:input path="zipcode" id="zipcode" placeholder="Postal Code" class="form-control"/>
                                <form:errors path="zipcode" cssClass="text-danger small" element="small"/>
                            </div>

                        </div>
                    </div>
                    <div>
                        <h2>Contacto: </h2>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="name" class="form-label">Nombre:</label>
                                <form:input path="name" id="name" placeholder="Name" class="form-control"/>
                                <form:errors path="name" cssClass="text-danger small" element="small"/>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="email" class="form-label">Email:</label>
                                <form:input path="email" id="email" placeholder="Email" class="form-control"/>
                                <form:errors path="email" cssClass="text-danger small" element="small"/>
                            </div>
                        </div>
                    </div>
                    <div>
                        <h4>Mensaje:</h4>
                        <form:input path="message" id="message" placeholder="Message" class="form-control"/>
                        <form:errors path="message" cssClass="text-danger small" element="small"/>
                    </div>
                    <!-- Add this line inside the form element -->
                    <input type="file" name="file" id="uploadImage" style="display:none;" onchange="previewImage()" />
                    <button type="submit" class="btn btn-primary mt-3">Agregarlo!</button>
                </form:form>
            </div>
        </div>
    </div>
</div>

<script>
    function previewImage() {
        const fileInput = document.getElementById('uploadImage');
        const file = fileInput.files[0];
        const img = document.getElementById('bookImage');
        const reader = new FileReader();

        reader.addEventListener('load', function () {
            img.src = reader.result;
        }, false);

        if (file) {
            reader.readAsDataURL(file);
        }
    }
</script>


</body>
