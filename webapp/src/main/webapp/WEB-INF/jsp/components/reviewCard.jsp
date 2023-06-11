<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="card container-row my-1" style="background-color:#e3e6e3; height: fit-content; border-radius: 25px;">
    <div class="container-column mx-5 my-3">
        <img src="<c:url value="/getImage/${param.imgSrc}"/>" class="rounded-circle mb-3"
             style="width: 75px; aspect-ratio: 1/1;"
             alt="Avatar"/>
        <h5 class="mb-2"><strong> <c:out value="${param.reviewer}"/> </strong></h5>
        <p class="text-muted"><span class="badge" style="background-color: #2B3B2B">Borrower</span> <span class="badge"
                                                                                                          style="background-color: #2B3B2B">Lender</span>
        </p>
    </div>
    <div class="container-column mx-5 my-3" style="justify-content: center !important;">

        <p>
            <i class="bi bi-quote"></i>
            <c:out value="${param.review}"/>
            <i class="bi bi-quote"></i>
        </p>

    </div>
</div>