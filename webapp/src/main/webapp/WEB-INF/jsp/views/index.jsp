<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <link href="/css/main.css" rel="stylesheet"/>
</head>
<body>
<!-- Esto va a cambiar es un mockUp -->
<jsp:include page="../components/navBar.jsp">
    <jsp:param name="homeClass" value="nav-link disable"/>
    <jsp:param name="lendClass" value="nav-link active"/>
</jsp:include>

<jsp:include page="../components/bookCard.jsp">
    <jsp:param name="bookTitle" value="Shoe Dog"/>
    <jsp:param name="bookAuthor" value="Phil Knight"/>
    <jsp:param name="bookAvailability" value="Available"/>
</jsp:include>

</body>
</html>
