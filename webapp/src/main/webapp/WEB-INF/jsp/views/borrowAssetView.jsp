<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
  <title>Pedir Prestado Libro</title>
  <link href="/css/main.css" rel="stylesheet"/>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
</head>
<body>

<jsp:include page="../components/navBar.jsp"/>
<div class="container my-5">
  <h1 class="text-center mb-5">Quieres pedir prestado un libro?</h1>
  <div class="p-4 rounded" >
    <div class="row">

      <div class="col-md-6 d-flex flex-column align-items-center">
        <div class="image-container position-relative">
          <img src="#" alt="Book Cover" class="img-fluid" id="bookImage" style="max-width: 100%; max-height: 100%;">
        </div>
      </div>

      <div class="col-md-6">
        <form action="" method="post">
          <ul class="list-unstyled">
            <li><strong>Ingresa tu ubicacion</strong> <input type="text" name="title" class="form-control" value="" /></li>
            <li><strong>Informacion para retirarlo </strong> <input type="text" class="form-control" value="" /></li>
            <li><strong>Mensaje para Retirarlo:</strong> <input type="text" class="form-control" value="" /></li>
          </ul>
          <button type="submit" class="btn btn-primary mt-3">Pedir prestado </button>
        </form>
      </div>
    </div>
  </div>
</div>

</body>
