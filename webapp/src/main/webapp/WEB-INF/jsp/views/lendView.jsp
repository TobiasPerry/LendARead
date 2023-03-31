<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <link href="/css/main.css" rel="stylesheet"/>
</head>
<body>

<!-- Esto va a cambiar es un mockUp -->
<jsp:include page="../components/navBar.jsp"/>

<div class="container my-5">
    <h1 class="text-center mb-5">Quieres prestar un libro?</h1>
    <div class="bg-light-blue p-4 rounded">
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
                <ul class="list-unstyled">
                    <li><strong>Título:</strong> <input type="text" class="form-control" value="" /></li>
                    <li><strong>Autor:</strong> <input type="text" class="form-control" value="" /></li>
                    <li><strong>ISBN:</strong> <input type="text" class="form-control" value="" /></li>
                    <li><strong>Ubicación:</strong> <input type="text" class="form-control" value="" /></li>
                    <li><strong>Mail:</strong> <input type="text" class="form-control" value="" /></li>
                    <li><strong>Mensaje para Retirarlo:</strong> <input type="text" class="form-control" value="" /></li>
                    <li>
                        <strong>Estado:</strong> <span class="d-inline-block">
                        <select class="form-select d-inline-block" name="condition" style="width:auto;">
                            <option value="Nuevo">Nuevo</option>
                            <option value="Poco uso">Poco uso</option>
                            <option value="Escrito">Escrito</option>
                            <option value="Muy usado">Muy usado</option>
                        </select></span>
                    </li>
                </ul>
                <button class="btn btn-primary mt-3">Request Loan</button>
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
</script>


</body>
</html>
