
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach var="i" begin="1" end="${param.repeatCount}">
    <div class="book-card" aria-hidden="true">
        <div class="card">

            <img src="<c:url value="/static/images/placeholderLoader.png" />" class="card-img-top imagen-card" alt="loading" style="height: 400px; object-fit: cover"/>

            <div class="card-title title-text text-center my-3">
                <h2 class="card-title placeholder-glow mx-2">
                    <span class="placeholder col-6"></span>
                </h2>
                <h5 class="card-text title-text placeholder-glow text-truncate mx-2">
                    <span class="placeholder col-9"></span>
                </h5>
            </div>

        </div>
    </div>
</c:forEach>