<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="snackbarSucess" class="d-none position-fixed bottom-0 end-0 mb-3 me-3 bg-success text-light p-3 rounded" style="z-index: 999">
    <c:out value="${snackbarSuccessMessage}" />
</div>

<div id="snackbarFailure" class="d-none position-fixed bottom-0 end-0 mb-3 me-3 bg-danger text-light p-3 rounded">
    <div class="snackbar-text">
        <strong><c:out value="${snackBarInvalidTextTitle}" />  </strong>
        <c:out value="Por favor verifique los valores ingresados en el formulario" />
    </div>
</div>

<script>
    const showSnackbarSucess = '${showSnackbarSucess}' === 'true';
    if (showSnackbarSucess) {
        document.getElementById('snackbarSucess').classList.remove('d-none');
        setTimeout(() => {
            document.getElementById('snackbarSucess').classList.add('d-none');
        }, 3000);
    }

    const showSnackbarInvalid = '${showSnackbarInvalid}' === 'true';
    if (showSnackbarInvalid) {
        document.getElementById('snackbarFailure').classList.remove('d-none');
        setTimeout(() => {
            document.getElementById('snackbarFailure').classList.add('d-none');
        }, 10000);
    }
</script>

