<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:useBean id="formElements" scope="session" class="ar.edu.itba.paw.webapp.presentation.FormServiceAddAssetView"/>

<head>
    <title>Prestar Libro</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <script src="<c:url value="/static/javaScript/topbar.js"/>"></script>
    <link href="<c:url value="/static/css/addAssetView.css"/>" rel="stylesheet"/>
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
                        <input type="file" id="uploadImage" name="file" class="d-none" accept="image/*" onchange="previewImage(event)">
                    </label>
                </div>
            </div>

            <div class="col-md-6">

                <c:url var="addAssetUrl" value="/addAsset"/>
                <form:form modelAttribute="addAssetForm" method="post" action="${addAssetUrl}" enctype="multipart/form-data">
                    <div>
                        <form:errors path="title" cssClass="error" element="p"/>
                        <label>Title:
                        <form:input path="title" placeholder="Title"/>
                        </label>
                    </div>
                    <div>
                        <form:errors path="author" cssClass="error" element="p"/>
                        <label>Author:
                        <form:input path="author" placeholder="Author"/>
                        </label>
                    </div>
                    <div>
                        <form:errors path="language" cssClass="error" element="p"/>
                        <label>Language:
                        <form:input path="language" placeholder="Language"/>
                        </label>
                    </div>
                    <div>
                        <form:errors path="isbn" cssClass="error" element="p"/>
                        <label>ISBN:
                        <form:input path="isbn" placeholder="ISBN"/>
                        </label>
                    </div>
                    <div>
                        <label>Physical Condition:
                        <form:select path="physicalCondition">
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
                        </label>
                    </div>
                    <div>
                        <form:errors path="zipcode" cssClass="error" element="p"/>
                        <label>Postal Code:
                            <form:input path="zipcode" placeholder="Postal Code"/>
                        </label>
                    </div>
                    <div>
                        <form:errors path="locality" cssClass="error" element="p"/>
                        <label>Locality:
                            <form:input path="locality" placeholder="Locality"/>
                        </label>
                    </div>
                    <div>
                        <form:errors path="province" cssClass="error" element="p"/>
                        <label>Province:
                            <form:input path="province" placeholder="Province"/>
                        </label>
                    </div>
                    <div>
                        <form:errors path="country" cssClass="error" element="p"/>
                        <label>Country:
                            <form:input path="country" placeholder="Country"/>
                        </label>
                    </div>
                    <div>
                        <form:errors path="email" cssClass="error" element="p"/>
                        <label>Email:
                            <form:input path="email" placeholder="Email"/>
                        </label>
                    </div>
                    <div>
                        <form:errors path="name" cssClass="error" element="p"/>
                        <label>Name:
                            <form:input path="name" placeholder="Name"/>
                        </label>
                    </div>
                    <div>
                        <form:errors path="message" cssClass="error" element="p"/>
                        <label>Message:
                            <form:input path="message" placeholder="Message"/>
                        </label>
                    </div>
                    <input type="file" id="hiddenUploadImage" name="file" class="d-none" accept="image/*">
                    <button type="submit" class="btn btn-primary mt-3">Agregarlo!</button>
                </form:form>
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
