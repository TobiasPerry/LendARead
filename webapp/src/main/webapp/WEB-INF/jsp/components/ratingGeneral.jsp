<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>

<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>

<%--<style>--%>
<%--  .m-b-30 {--%>
<%--    margin-bottom: 30px;--%>
<%--  }--%>

<%--  .rating-card {--%>
<%--    background-color: #fff;--%>
<%--    padding: 30px;--%>
<%--    margin-bottom: 30px;--%>
<%--    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);--%>
<%--  }--%>

<%--  .rating-number {--%>
<%--    font-size: 50px;--%>
<%--    font-weight: 600;--%>
<%--  }--%>
<%--  .rating-number small {--%>
<%--    font-size: 16px;--%>
<%--  }--%>

<%--  .rating-stars:before {--%>
<%--    content: "";--%>
<%--    position: absolute;--%>
<%--    left: 0;--%>
<%--    top: 0;--%>
<%--    width: 100%;--%>
<%--    height: 100%;--%>
<%--    background: #eee;--%>
<%--  }--%>

<%--  .rating-stars .filled-star {--%>
<%--    position: absolute;--%>
<%--    left: 0;--%>
<%--    top: 0;--%>
<%--    width: 100%;--%>
<%--    height: 100%;--%>
<%--    background: #ffc107;--%>
<%--  }--%>

<%--  .rating-stars img {--%>
<%--    height: 100%;--%>
<%--    width: 100%;--%>
<%--    display: block;--%>
<%--    position: relative;--%>
<%--    z-index: 1;--%>
<%--  }--%>

<%--  .rating-progress {--%>
<%--    display: flex;--%>
<%--    justify-content: center;--%>
<%--    align-items: center;--%>
<%--  }--%>
<%--  .rating-progress .rating-grade {--%>
<%--    padding: 3px 20px 3px 0;--%>
<%--    float: left;--%>
<%--    width: 50px;--%>
<%--    text-align: right;--%>
<%--    display: flex;--%>
<%--    justify-content: flex-end;--%>
<%--    align-items: center;--%>
<%--  }--%>
<%--  .rating-progress .rating-grade img {--%>
<%--    height: 15px;--%>
<%--    margin-left: 3px;--%>
<%--  }--%>
<%--  .rating-progress .progress {--%>
<%--    float: left;--%>
<%--    width: calc(100% - 110px);--%>
<%--    border-radius: 10px;--%>
<%--  }--%>
<%--  .rating-progress .progress .bg-warning {--%>
<%--    background-color: #ffc107 !important;--%>
<%--    border-radius: 10px;--%>
<%--  }--%>
<%--  .rating-progress .rating-value {--%>
<%--    padding: 3px 0 3px 20px;--%>
<%--    float: left;--%>
<%--    width: 60px;--%>
<%--  }--%>
<%--  .rating-progress:after {--%>
<%--    content: "";--%>
<%--    clear: both;--%>
<%--    display: table;--%>
<%--  }--%>

<%--</style>--%>


<%--<div class="container">--%>
<%--  <h1 class="text-center text-uppercase">Rating UI</h1>--%>
<%--  <br>--%>
<%--  <br>--%>
<%--      <div class="rating-card">--%>
<%--        <div class="text-center m-b-30">--%>
<%--          <h4>Rating Overview</h4>--%>
<%--          <br>--%>
<%--          <h1 class="rating-number">2.3<small>/5</small></h1>--%>
<%--          <div class="rating-stars d-inline-block position-relative mr-2">--%>
<%--            <img src="<c:url value="/static/images/grey-star.svg"/>" alt="">--%>
<%--            <div class="filled-star" style="width:6%"></div>--%>
<%--          </div>--%>
<%--          <div class="text-muted">2,145 ratings</div>--%>
<%--        </div>--%>
<%--        <div class="rating-divided">--%>
<%--          <div class="rating-progress">--%>
<%--            <span class="rating-grade">5 <img src="<c:url value="/static/images/star.svg"/>" alt=""></span>--%>
<%--            <div class="progress">--%>
<%--              <div class="progress-bar bg-warning" role="progressbar" style="width: 75%" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100"></div>--%>
<%--            </div>--%>
<%--            <span class="rating-value">1,985</span>--%>
<%--          </div>--%>
<%--          <div class="rating-progress">--%>
<%--            <span class="rating-grade">4 <img src="<c:url value="/static/images/star.svg"/>" alt=""></span>--%>
<%--            <div class="progress">--%>
<%--              <div class="progress-bar bg-warning" role="progressbar" style="width: 10%" aria-valuenow="10" aria-valuemin="0" aria-valuemax="100"></div>--%>
<%--            </div>--%>
<%--            <span class="rating-value">356</span>--%>
<%--          </div>--%>
<%--          <div class="rating-progress">--%>
<%--            <span class="rating-grade">3 <img src="<c:url value="/static/images/star.svg"/>" alt=""></span>--%>
<%--            <div class="progress">--%>
<%--              <div class="progress-bar bg-warning" role="progressbar" style="width: 8%" aria-valuenow="8" aria-valuemin="0" aria-valuemax="100"></div>--%>
<%--            </div>--%>
<%--            <span class="rating-value">130</span>--%>
<%--          </div>--%>
<%--          <div class="rating-progress">--%>
<%--            <span class="rating-grade">2 <img src="<c:url value="/static/images/star.svg"/>" alt=""></span>--%>
<%--            <div class="progress">--%>
<%--              <div class="progress-bar bg-warning" role="progressbar" style="width: 6%" aria-valuenow="6" aria-valuemin="0" aria-valuemax="100"></div>--%>
<%--            </div>--%>
<%--            <span class="rating-value">90</span>--%>
<%--          </div>--%>
<%--          <div class="rating-progress">--%>
<%--            <span class="rating-grade">1 <img src="<c:url value="/static/images/star.svg"/>" alt=""></span>--%>
<%--            <div class="progress">--%>
<%--              <div class="progress-bar bg-warning" role="progressbar" style="width: 3%" aria-valuenow="3" aria-valuemin="0" aria-valuemax="100"></div>--%>
<%--            </div>--%>
<%--            <span class="rating-value">33</span>--%>
<%--          </div>--%>
<%--        </div>--%>
<%--      </div>--%>

<%--</div>--%>


<style>
  .rating {
    display: inline-block;
    font-size: 0; /* Prevents space between inline-block elements */
  }

  .stars {
    display: inline-block;
    font-size: 1rem; /* Reset font size to desired value */
    overflow: hidden;
    position: relative;
    color: gold;
  }

  .filled-stars {
    display: block;
    position: absolute;
    top: 0;
    left: 0;
    width: 0;
    overflow: hidden;
    white-space: nowrap;
    color: inherit;
  }
</style>


<div class="rating">
  <span class="stars">
    <span class="filled-stars"></span>
  </span>
</div>

<script>
  function fillStars(rating) {
    const filledStars = document.querySelector('.filled-stars');
    const percentage = (rating / 5) * 100;
    filledStars.style.width = `${percentage}%`;
  }
  fillStars(3.3)
  // Usage example: fillStars(3.5); // Fills stars proportionally to 3.5 out of 5
</script>