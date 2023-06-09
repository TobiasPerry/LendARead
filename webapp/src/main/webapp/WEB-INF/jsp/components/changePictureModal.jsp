<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script src="<c:url value="/static/javaScript/utils.js"/>"></script>
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
                <label for="uploadImage" class="user-profile-picture-modal">
                    <img id="newProfilePic" src="${param.imgSrc}"/>
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                         class="bi bi-cloud-arrow-up-fill" viewBox="0 0 16 16">
                        <path d="M8 2a5.53 5.53 0 0 0-3.594 1.342c-.766.66-1.321 1.52-1.464 2.383C1.266 6.095 0 7.555 0 9.318 0 11.366 1.708 13 3.781 13h8.906C14.502 13 16 11.57 16 9.773c0-1.636-1.242-2.969-2.834-3.194C12.923 3.999 10.69 2 8 2zm2.354 5.146a.5.5 0 0 1-.708.708L8.5 6.707V10.5a.5.5 0 0 1-1 0V6.707L6.354 7.854a.5.5 0 1 1-.708-.708l2-2a.5.5 0 0 1 .708 0l2 2z"/>
                    </svg>
                </label>
            </div>
            <input type="file" accept="image/*" name="file" id="uploadImage" style="display:none;"
                   onchange="previewImageLoad(
                           'newProfilePic'
                       )"/>
            <div class="modal-footer border-0">
                <c:url var="changeProfilePicUrl" value="/user/${param.userId}/edit"/>
                <button type="submit" class="btn btn-primary">
                    Upload
                </button>
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

