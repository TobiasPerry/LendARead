<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="snackbarSucess" class="d-none position-fixed bottom-0 end-0 mb-3 me-3 bg-success text-light p-3 rounded" style="z-index: 999">
    <c:out value="${snackbarSuccessMessage}" />
</div>

<div id="snackbarFailure" class="d-none position-fixed bottom-0 start-0 mb-3 bg-danger text-light p-3 rounded" style="z-index: 999">
    <div class="snackbar-text">
        <strong><c:out value="${snackBarInvalidTextTitle}" />  </strong>
        <c:out value="Por favor verifique los valores ingresados en el formulario" />
    </div>
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

