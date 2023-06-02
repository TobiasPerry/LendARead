
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="card text-white card-has-bg click-col" style="background-image:url(<c:url value="/getImage/${param.imageId}"/>);
        background-size: cover;
        background-position: center;
        background-repeat: no-repeat;
        height: 400px; margin: 15px;width: 18rem; object-fit: cover">

    <a href="<c:url value="/info/${param.id}"/>" style="text-decoration: none">

        <img class="card-img d-none" src="<c:url value="/getImage/${param.imageId}"/>" alt="<c:out value="${param.bookTitle}"/>">

        <div class="card-img-overlay d-flex flex-column">
            <div class="card-body">
                <small class="card-meta mb-2 text-truncate"><c:out value="${param.bookAuthor}"/></small>
                <h3 class="card-title mt-0 text-white truncate-3-lines"><c:out value="${param.bookTitle}"/></h3>
                <small class="text-white"><i class="bi bi-book-half text-white"></i> Good </small>
            </div>
            <div class="card-footer">
                <div class="media">
                    <img class="mr-3 rounded-circle" src="https://www.ippo.com.ar/assets/img/DSC_0154.JPG" alt="Generic placeholder image" style="width:50px; height: 50px">
                    <div class="media-body">
                        <h6 class="my-0 text-white d-block">ippo</h6>
                        <small class="text-white"> Villa Martelli, Buenos Aires </small>
                    </div>
                </div>
            </div>
        </div>

    </a>

</div>