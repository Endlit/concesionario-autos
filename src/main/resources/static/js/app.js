// app.js (utilidades globales)
console.log("app.js cargado correctamente");

// Función de confirmación reutilizable
function confirmarEliminar(mensaje) {
    return confirm(mensaje || "¿Estás seguro de realizar esta acción?");
}

// Delegación automática para botones o enlaces con data-confirm
document.addEventListener("click", function (event) {
    const element = event.target.closest("[data-confirm]");
    if (!element) return;

    const mensaje = element.getAttribute("data-confirm");

    if (!confirmarEliminar(mensaje)) {
        event.preventDefault();
    }
});

document.addEventListener("DOMContentLoaded", () => {
    const hoy = new Date();
    const año = hoy.getFullYear();
    const mes = String(hoy.getMonth() + 1).padStart(2, '0');
    const dia = String(hoy.getDate()).padStart(2, '0');

    // Formato YYYY-MM-DD
    const fechaFormateada = `${año}-${mes}-${dia}`;

    document.getElementById("fechaCesion").value = fechaFormateada;
});