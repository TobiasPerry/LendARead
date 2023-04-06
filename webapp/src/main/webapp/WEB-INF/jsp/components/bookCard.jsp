<%--
  Created by IntelliJ IDEA.
  User: martinippo
  Date: 31/3/23
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="book-card">
    <a href="<c:url value="/borrowAssetView"/>">
        <div class="card">
            <img src="https://i.pinimg.com/originals/d4/2e/d7/d42ed7bf30a4c1a6a201565f0bc61190.jpg" class="card-img-top" alt="Shoe Dog cover">
            <div class="card-body">
                <h2 class="card-title">${param.bookTitle}</h2>
                <h5 class="card-text">${param.bookAuthor} - ${param.bookAvailability}</h5>
            </div>
        </div>

    </a>
</div>