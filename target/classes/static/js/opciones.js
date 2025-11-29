// opciones.js
console.log("opciones.js cargado");

// Esperar a que el documento se cargue
document.addEventListener("DOMContentLoaded", () => {

    const form = document.querySelector("form");
    const inputNombre = form.querySelector("input[name='nombre']");
    const inputDescripcion = form.querySelector("input[name='descripcion']");

    // Crear contenedores de mensaje si no existen
    function crearContenedorError(input) {
        let error = input.parentElement.querySelector(".error-msg");
        if (!error) {
            error = document.createElement("div");
            error.classList.add("error-msg");
            input.parentElement.appendChild(error);
        }
        return error;
    }

    // Validaciones
    function validarNombre() {
        const error = crearContenedorError(inputNombre);
        const valor = inputNombre.value.trim();

        if (valor === "") {
            error.textContent = "El nombre es obligatorio.";
            return false;
        }
        if (valor.length < 3) {
            error.textContent = "El nombre debe tener al menos 3 caracteres.";
            return false;
        }

        error.textContent = "";
        return true;
    }

    function validarDescripcion() {
        const error = crearContenedorError(inputDescripcion);
        const valor = inputDescripcion.value.trim();

        if (valor.length > 100) {
            error.textContent = "La descripción no puede superar 100 caracteres.";
            return false;
        }

        error.textContent = "";
        return true;
    }

    // Eventos en tiempo real
    inputNombre.addEventListener("input", validarNombre);
    inputDescripcion.addEventListener("input", validarDescripcion);

    // Validar al enviar
    form.addEventListener("submit", (e) => {
        let valido = true;

        if (!validarNombre()) valido = false;
        if (!validarDescripcion()) valido = false;

        if (!valido) {
            e.preventDefault();
            Swal.fire({
                icon: "warning",
                title: "Validación requerida",
                text: "Corrige los errores antes de continuar.",
                confirmButtonColor: "#3085d6"
            });
        }
    });
});