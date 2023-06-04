<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="review.lender.headTitle"/></title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/neoBookCard.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/starRating.css"/>" rel="stylesheet"/>

    <script src="<c:url value="/static/javaScript/reviewLender.js"/>"></script>

    <link rel="shortcut icon" href="<c:url value='/static/images/favicon-claro-bg.ico'/>" type="image/x-icon">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Overpass:wght@700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body class="body-class">
  <jsp:include page="../components/navBar.jsp"/>

  <div class="main-class" style="display: flex; justify-content: center;align-items: center;flex-direction: column;">


    <div class="container-row-wrapped">
        <div class="card text-white card-has-bg click-col" style="background-image:url(<c:url value="/getImage/${assetInstance.image.id}"/>); height: 400px; width: 300px">
          <img class="card-img d-none" src="<c:url value="/getImage/${assetInstance.image.id}"/>" alt="<c:out value="${book.name} cover"/>">
          <div class="card-img-overlay d-flex flex-column">
              <div class="card-body">
                  <small class="card-meta mb-2">Phill H. Knight</small>
                  <h3 class="card-title mt-0 ">Shoe Dog</h3>
                  <small><i class="far fa-clock"></i> Good </small>
              </div>
              <div class="card-footer">
                  <div class="media">
                      <img class="mr-3 rounded-circle" src="<c:url value="/getImage/${assetInstance.image.id}"/>" alt="Generic placeholder image" style="width:50px; height: 50px">
                      <div class="media-body">
                          <h6 class="my-0 text-white d-block">ippo</h6>
                          <small> Villa Martelli, Buenos Aires </small>
                      </div>
                  </div>
              </div>
          </div>
        </div>

        <div class="">
            <div style="display: flex; flex-direction: column; justify-content: space-between; max-width: 800px;">

                <div style="background-color: #f0f5f0; border-radius: 20px; margin: 20px; padding: 20px">
                    <h2>
                        <spring:message code="review.lender.userReview.title">
                            <spring:argument><c:out value="${user.name}"/></spring:argument>
                        </spring:message>
                    </h2>
                    <div class="rating-wrapper">
                        <!-- star 5 -->
                        <input type="radio" id="lender-5-star-rating" name="star-rating" value="5">
                        <label for="lender-5-star-rating" class="star-rating" id="star5-lender">
                            <i class="fas fa-star d-inline-block star"></i>
                        </label>

                        <!-- star 4 -->
                        <input type="radio" id="lender-4-star-rating" name="star-rating" value="4">
                        <label for="lender-4-star-rating" class="star-rating star" id="star4-lender">
                            <i class="fas fa-star d-inline-block star" ></i>
                        </label>

                        <!-- star 3 -->
                        <input type="radio" id="lender-3-star-rating" name="star-rating" value="3">
                        <label for="lender-3-star-rating" class="star-rating star" id="star3-lender">
                            <i class="fas fa-star d-inline-block star"></i>
                        </label>

                        <!-- star 2 -->
                        <input type="radio" id="lender-2-star-rating" name="star-rating" value="2">
                        <label for="lender-2-star-rating" class="star-rating star" id="star2-lender">
                            <i class="fas fa-star d-inline-block star" ></i>
                        </label>

                        <!-- star 1 -->
                        <input type="radio" id="lender-1-star-rating" name="star-rating" value="1">
                        <label for="lender-1-star-rating" class="star-rating star" id="star1-lender">
                            <i class="fas fa-star d-inline-block star"></i>
                        </label>
                    </div>
                    <textarea class="form-control" aria-label="With textarea" id="review-area" placeholder="<spring:message code="review.user.placeholder"/>"></textarea>
                </div>

                <c:url value="/review/lenderAdd" var="reviewsBorrowerUrl"/>
                <form:form method="post" accept-charset="UTF-8" action="${reviewsBorrowerUrl}" id="spring-form">
                    <input type="submit" class="btn btn-green mx-1" id="btn-submit"
                           value="<spring:message code="review.sendReview"/>"
                    />
                    <input type="hidden" name="rating" id="rating-form" value=""/>
                    <input type="hidden" name="review" id="review-form" value=""/>
                    <input type="hidden" name="lendingId" value="${lendingId}"/>
                </form:form>

            </div>
        </div>

    </div>

  </div>

</body>
</html>
