<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<div id="snackbarFailure" class="d-none position-fixed bottom-0 start-0 m-3 bg-danger text-light p-3 rounded" style="z-index: 999">
        <spring:message code="snackbar.verifyFormMsg" />
</div>

<script>
    const showSnackbarInvalid = '${showSnackbarInvalid}' === 'true';
    if (showSnackbarInvalid) {
        document.getElementById('snackbarFailure').classList.remove('d-none');
        setTimeout(() => {
            document.getElementById('snackbarFailure').classList.add('d-none');
        }, 2000);
    }
</script>

