<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="changeProfilePicModal" tabindex="-1" role="dialog"
     aria-labelledby="${param.modalTitle}" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content rounded-3 border-0 shadow">
            <div class="modal-header border-0" style="text-align: center">
                <div class="icon-box" style="background: #2B3B2B;">
                    <i class="fas fa-solid fa-camera fa-lg"></i>
                </div>
                <h3 class="modal-title w-100 mt-2">
                    Change profile picture
                </h3>
            </div>
            <div class="modal-body text-center py-0 border-0">
            </div>
            <div class="modal-footer border-0">
                <c:url var="changeProfilePicUrl" value="/user/${param.userId}/edit"/>
                <form action="${changeProfilePicUrl}" method="post">
                    <button type="submit" class="btn btn-primary">
                        Upload
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>


<script>
    const changeProfilePicBtnHandler = document.getElementById('change-profile-pic-btn');
    if (changeProfilePicBtnHandler !== null) {
        changeProfilePicBtnHandler.addEventListener('click', function () {
            new bootstrap.Modal($('#changeProfilePicModal')).show();
        });
    }
    const noButton2 = document.getElementById('no_button1');
    if (noButton2 !== null) {
        noButton2.addEventListener('click', function () {
            $('#changeProfilePicModal').modal('hide')
        });
    }
</script>
