console.log("app.js cargado correctamente");

// ================================
// 📅 Cargar fecha automática
// ================================
document.addEventListener("DOMContentLoaded", () => {
    const hoy = new Date();
    const año = hoy.getFullYear();
    const mes = String(hoy.getMonth() + 1).padStart(2, "0");
    const dia = String(hoy.getDate()).padStart(2, "0");

    const fechaFormateada = `${año}-${mes}-${dia}`;
    const inputFecha = document.getElementById("fechaCesion");

    if (inputFecha) {
        inputFecha.value = fechaFormateada;
    } else {
        console.warn(
            "⚠ El elemento con id 'fechaCesion' no existe en esta página."
        );
    }
});

// ================================
// 🗑 Eliminar con SweetAlert2
// ================================
document.addEventListener("click", function (event) {
    const btn = event.target.closest("[data-delete]");
    if (!btn) return;

    event.preventDefault();
    const url = btn.getAttribute("href");

    Swal.fire({
        title: "¿Eliminar este registro?",
        text: "Esta acción no se puede deshacer.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: "Cancelar",
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = url;
        }
    });
});

// ================================
// 🚨 Mostrar mensaje global si hay errores
// (ideal para páginas 4xx / 5xx o validaciones)
// ================================
document.addEventListener("DOMContentLoaded", () => {
    const error = document.getElementById("errorMessage");

    if (error && error.textContent.trim() !== "") {
        Swal.fire({
            title: "Error detectado",
            text: error.textContent,
            icon: "error",
        });
    }
});
