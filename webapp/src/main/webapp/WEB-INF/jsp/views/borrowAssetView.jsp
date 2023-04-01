<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
  <title>Pedir Prestado Libro</title>
  <link href="/css/main.css" rel="stylesheet"/>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
</head>
<body>

<jsp:include page="../components/navBar.jsp"/>
<div class="container my-5">

  <div id="snackbar" class="d-none position-fixed bottom-0 end-0 mb-3 me-3 bg-success text-light p-3 rounded">
    Libro pedido exitosamente!
  </div>
  <h1 class="text-center mb-5">Quieres pedir prestado un libro?</h1>
  <div class="p-4 rounded lendView" >
    <div class="row">

      <div class="col-md-6 d-flex flex-column align-items-center">
        <div class="image-container position-relative" style="width: 100%; height: 100%;">
          <img src="https://m.media-amazon.com/images/W/IMAGERENDERING_521856-T1/images/I/61aFldsgAmL._AC_UF1000,1000_QL80_.jpg" alt="Book Cover" class="img-fluid" id="bookImage" style="width: 100%; height: 100%; object-fit: contain;">
        </div>
      </div>

      <div class="col-md-6">
        <form action="borrowAsset" method="post">
          <ul class="list-unstyled">
            <li><strong>Ingresa tu ubicacion</strong> <input type="text" name="location" class="form-control" value="" /></li>
            <li><strong>Informacion para retirarlo </strong> <input type="text" name="guidelines" class="form-control" value="" /></li>
            <li><strong>Mail</strong> <input type="text" name="email" class="form-control" value="" /></li>
          </ul>
          <button type="submit" class="btn btn-primary mt-3">Pedir prestado </button>
        </form>
      </div>
    </div>
  </div>
</div>
<script>
  const showSnackbar = ${showSnackbar}; // Retrieve the attribute value
  if (showSnackbar) {
    document.getElementById('snackbar').classList.remove('d-none');
    setTimeout(() => {
      document.getElementById('snackbar').classList.add('d-none');
    }, 3000);
  }
</script>
</body>