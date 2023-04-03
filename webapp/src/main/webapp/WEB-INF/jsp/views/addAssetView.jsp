<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="formElements" scope="session" class="ar.edu.itba.paw.webapp.presentation.FormServiceAddAssetView"/>

<head>
    <title>Prestar Libro</title>
    <link href="/css/main.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <style>
        .snackbar-text {
            white-space: pre-wrap;
            word-wrap: break-word;
            overflow-wrap: break-word;
            max-width: 100%;
        }
    </style>
</head>
<body>

<jsp:include page="../components/navBar.jsp"/>
<div class="container my-5">

    <div id="snackbarSucess" class="d-none position-fixed bottom-0 end-0 mb-3 me-3 bg-success text-light p-3 rounded">
        Libro agregado exitosamente!
    </div>

    <div id="snackbarFailure" class="d-none position-fixed bottom-0 end-0 mb-3 me-3 bg-danger text-light p-3 rounded">
        <div class="snackbar-text">
            <strong><c:out value="${snackBarInvalidTextTitle}" />  </strong>
            <c:out value="${snackBarInvalidText}" />
        </div>
    </div>

    <h1 class="text-center mb-5">Quieres prestar un libro?</h1>
    <div class="p-4 rounded" >
        <div class="row">

            <div class="col-md-6 d-flex flex-column align-items-center">
                <div class="image-container position-relative">
                    <img src="#" alt="Book Cover" class="img-fluid" id="bookImage" style="max-width: 100%; max-height: 100%;">
                    <label for="uploadImage" class="position-absolute bottom-0 end-0 btn btn-primary" id="uploadLabel">
                        <i class="bi bi-cloud-upload"></i> Subir foto
                        <input type="file" id="uploadImage" class="d-none" accept="image/*" onchange="previewImage(event)">
                    </label>
                </div>
            </div>

            <div class="col-md-6">
                <form action="addAsset" method="post">

                    <ul class="list-unstyled">
                        <li>
                            <h2> Informacion del libro </h2>
                            <c:forEach var="element" items="${formElements.bookInfoElements}">
                                <li style="background: ${element.status}"><strong>${element.label}:</strong>
                                    <input type="${element.inputType}" name="${element.inputName}" class="form-control"/>
                                </li>
                            </c:forEach>
                            <li><strong>${formElements.conditionsDropDown.label}</strong>
                            <select class="form-select d-inline-block" name="${formElements.conditionsDropDown.inputName}" style="width:auto;">
                                <c:forEach var="option" items="${formElements.conditionsDropDown.selectOptions}">
                                    <option value="${option}">${option}</option>
                                </c:forEach>
                            </select>
                        <li>
                            <h2> Ubicacion </h2>
                            <p> Esta informacion se va a presentar para filtrar las busquedas, nunca se va a presentar junto con tu email
                                y/o nombre sin tu consentimiento</p>
                            <c:forEach var="element" items="${formElements.locationInfoElements}">
                                <li><strong>${element.label}:</strong>
                                    <input type="${element.inputType}" name="${element.inputName}" class="form-control"  />
                                </li>
                            </c:forEach>
                        <li>
                            <h2> Contacto </h2>
                            <c:forEach var="element" items="${formElements.contactInfoElements}">
                            <li><strong>${element.label}:</strong>
                                <input type="${element.inputType}" name="${element.inputName}" class="form-control"  />
                            </li>
                            </c:forEach>
                    </ul>
                    <button type="submit" class="btn btn-primary mt-3">Agregarlo! </button>
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
    }

    const showSnackbarSucess = '${showSnackbarSucess}' === 'true';
    if (showSnackbarSucess) {
        document.getElementById('snackbarSucess').classList.remove('d-none');
        setTimeout(() => {
            document.getElementById('snackbarSucess').classList.add('d-none');
        }, 3000);
    }

    const showSnackbarInvalid = '${showSnackbarInvalid}' === 'true';
    if (showSnackbarInvalid) {
        document.getElementById('snackbarFailure').classList.remove('d-none');
        setTimeout(() => {
            document.getElementById('snackbarFailure').classList.add('d-none');
        }, 10000);
    }
</script>


</body>