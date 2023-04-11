<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="formElements" scope="session" class="ar.edu.itba.paw.webapp.presentation.FormServiceAddAssetView"/>

<head>
    <title>Prestar Libro</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <script src="<c:url value="/static/javaScript/topbar.js"/>"></script>
    <link href="<c:url value="/static/css/addAssetView.css"/>" rel="stylesheet"/>
</head>

<body>

<jsp:include page="../components/navBar.jsp"/>
<div class="container my-5">

    <jsp:include page="../components/snackbarComponent.jsp" />

    <h1 class="text-center mb-5">Quieres prestar un libro?</h1>
    <div class="form-container">
        <div class="row">

            <div class="col-md-6 d-flex flex-column align-items-center">
                <div class="image-container position-relative">
                    <img src="#" alt="Book Cover" class="img-fluid" id="bookImage" style="max-width: 100%; max-height: 100%;">
                    <label for="uploadImage" class="position-absolute bottom-0 end-0 btn btn-primary" id="uploadLabel">
                        <i class="bi bi-cloud-upload"></i> Subir foto
                        <input type="file" id="uploadImage" name="file" class="d-none" accept="image/*" onchange="previewImage(event)">
                    </label>
                </div>
            </div>

            <div class="col-md-6">
                <form action="addAsset" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <h2>Informacion del libro</h2>
                        <c:forEach var="element" items="${formElements.bookInfoElements}">
                            <div class="form-group">
                                <label>${element.label}:</label>
                                <input type="${element.inputType}" name="${element.inputName}" class="form-control"/>
                            </div>
                        </c:forEach>
                    </div>

                    <div class="form-group">
                        <label>${formElements.conditionsDropDown.label}</label>
                        <select class="form-select" name="${formElements.conditionsDropDown.inputName}">
                            <c:forEach var="option" items="${formElements.conditionsDropDown.selectOptions}">
                                <option value="${option}">${option}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <h2>Ubicacion</h2>
                        <p>Esta informacion se va a presentar para filtrar las busquedas, nunca se va a presentar junto con tu email
                            y/o nombre sin tu consentimiento</p>
                        <c:forEach var="element" items="${formElements.locationInfoElements}">
                            <div class="form-group">
                                <label>${element.label}:</label>
                                <input type="${element.inputType}" name="${element.inputName}" class="form-control"/>
                            </div>
                        </c:forEach>
                    </div>

                    <div class="form-group">
                        <h2>Contacto</h2>
                        <c:forEach var="element" items="${formElements.contactInfoElements}">
                            <div class="form-group">
                                <label>${element.label}:</label>
                                <input type="${element.inputType}" name="${element.inputName}" class="form-control"/>
                            </div>
                        </c:forEach>
                    </div>

                    <input type="file" id="hiddenUploadImage" name="file" class="d-none" accept="image/*">
                    <button type="submit" class="btn btn-primary mt-3">Agregarlo!</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    function previewImage(event) {
        const reader = new FileReader();
        reader.onload = function() {
            const output = document.getElementById('bookImage');
            output.src = reader.result;
            document.getElementById('uploadLabel').style.display = 'none';
        };
        reader.readAsDataURL(event.target.files[0]);
        document.getElementById('hiddenUploadImage').files = event.target.files;
    }
</script>

</body>
