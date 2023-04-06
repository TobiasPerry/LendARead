<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
    <script src="<c:url value="/static/javaScript/topbar.js"/>"></script>
</head>
<body data-path="${path}">
<!-- Esto va a cambiar es un mockUp -->
<jsp:include page="../components/navBar.jsp"/>

<div class="container-row-wrapped">
    <jsp:include page="../components/bookCard.jsp">
        <jsp:param name="bookTitle" value="Shoe Dog"/>
        <jsp:param name="bookAuthor" value="Phil Knight"/>
        <jsp:param name="bookAvailability" value="Available"/>
    </jsp:include>

    <jsp:include page="../components/bookCard.jsp">
        <jsp:param name="bookTitle" value="Shoe Dog"/>
        <jsp:param name="bookAuthor" value="Phil Knight"/>
        <jsp:param name="bookAvailability" value="Available"/>
    </jsp:include>

    <jsp:include page="../components/bookCard.jsp">
        <jsp:param name="bookTitle" value="Shoe Dog"/>
        <jsp:param name="bookAuthor" value="Phil Knight"/>
        <jsp:param name="bookAvailability" value="Available"/>
    </jsp:include>

    <jsp:include page="../components/bookCard.jsp">
        <jsp:param name="bookTitle" value="Shoe Dog"/>
        <jsp:param name="bookAuthor" value="Phil Knight"/>
        <jsp:param name="bookAvailability" value="Available"/>
    </jsp:include>

    <jsp:include page="../components/bookCard.jsp">
        <jsp:param name="bookTitle" value="Shoe Dog"/>
        <jsp:param name="bookAuthor" value="Phil Knight"/>
        <jsp:param name="bookAvailability" value="Available"/>
    </jsp:include>

    <jsp:include page="../components/bookCard.jsp">
        <jsp:param name="bookTitle" value="Shoe Dog"/>
        <jsp:param name="bookAuthor" value="Phil Knight"/>
        <jsp:param name="bookAvailability" value="Available"/>
    </jsp:include>

    <jsp:include page="../components/bookCard.jsp">
        <jsp:param name="bookTitle" value="Shoe Dog"/>
        <jsp:param name="bookAuthor" value="Phil Knight"/>
        <jsp:param name="bookAvailability" value="Available"/>
    </jsp:include>

    <jsp:include page="../components/bookCard.jsp">
        <jsp:param name="bookTitle" value="Shoe Dog"/>
        <jsp:param name="bookAuthor" value="Phil Knight"/>
        <jsp:param name="bookAvailability" value="Available"/>
    </jsp:include>

    <jsp:include page="../components/bookCard.jsp">
        <jsp:param name="bookTitle" value="Shoe Dog"/>
        <jsp:param name="bookAuthor" value="Phil Knight"/>
        <jsp:param name="bookAvailability" value="Available"/>
    </jsp:include>

    <jsp:include page="../components/bookCard.jsp">
        <jsp:param name="bookTitle" value="Shoe Dog"/>
        <jsp:param name="bookAuthor" value="Phil Knight"/>
        <jsp:param name="bookAvailability" value="Available"/>
    </jsp:include>

    <jsp:include page="../components/bookCard.jsp">
        <jsp:param name="bookTitle" value="Shoe Dog"/>
        <jsp:param name="bookAuthor" value="Phil Knight"/>
        <jsp:param name="bookAvailability" value="Available"/>
    </jsp:include>

    <jsp:include page="../components/bookCard.jsp">
        <jsp:param name="bookTitle" value="Shoe Dog"/>
        <jsp:param name="bookAuthor" value="Phil Knight"/>
        <jsp:param name="bookAvailability" value="Available"/>
    </jsp:include>
</div>
</body>
</html>
