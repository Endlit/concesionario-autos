// usuario.js
console.log("usuario.js cargado");

document.addEventListener("DOMContentLoaded", () => {

    const form = document.querySelector("form");

    const inputDni = form.querySelector("input[name='dni']");
    const inputNombre = form.querySelector("input[name='nombre']");
    const inputDireccion = form.querySelector("input[name='direccion']");
    const inputTelefono = form.querySelector("input[name='telefono']");
    const inputRol = form.querySelector("select[name='rol']");

    // Crea un contenedor de error si no existe
    function getErrorBox(input) {
        let box = input.parentElement.querySelector(".error-msg");
        if (!box) {
            box = document.createElement("div");
            box.classList.add("error-msg");
            input.parentElement.appendChild(box);
        }
        return box;
    }

    // ---------------- VALIDACIONES ---------------- //

    function validarDni() {
        const err = getErrorBox(inputDni);
        const valor = inputDni.value.trim();

        if (valor === "") {
            err.textContent = "El DNI es obligatorio.";
            return false;
        }
        if (!/^[0-9]{6,12}$/.test(valor)) {
            err.textContent = "El DNI debe tener entre 6 y 12 números.";
            return false;
        }
        err.textContent = "";
        return true;
    }

    function validarNombre() {
        const err = getErrorBox(inputNombre);
        const valor = inputNombre.value.trim();

        if (valor === "") {
            err.textContent = "El nombre es obligatorio.";
            return false;
        }
        if (!/^[A-Za-zÁÉÍÓÚÑáéíóúñ\s]{3,}$/.test(valor)) {
            err.textContent = "El nombre debe contener solo letras y mínimo 3 caracteres.";
            return false;
        }
        err.textContent = "";
        return true;
    }

    function validarDireccion() {
        const err = getErrorBox(inputDireccion);
        const valor = inputDireccion.value.trim();

        if (valor.length > 120) {
            err.textContent = "La dirección no puede superar 120 caracteres.";
            return false;
        }
        err.textContent = "";
        return true;
    }

    function validarTelefono() {
        const err = getErrorBox(inputTelefono);
        const valor = inputTelefono.value.trim();

        if (valor !== "" && !/^[0-9]{7,10}$/.test(valor)) {
            err.textContent = "El teléfono debe contener solo números (7–10 dígitos).";
            return false;
        }
        err.textContent = "";
        return true;
    }

    function validarRol() {
        const err = getErrorBox(inputRol);

        if (inputRol.value === "" || inputRol.value === null) {
            err.textContent = "Debe seleccionar un rol.";
            return false;
        }
        err.textContent = "";
        return true;
    }

    // ---------------- EVENTOS EN TIEMPO REAL ---------------- //

    inputDni.addEventListener("input", validarDni);
    inputNombre.addEventListener("input", validarNombre);
    inputDireccion.addEventListener("input", validarDireccion);
    inputTelefono.addEventListener("input", validarTelefono);
    inputRol.addEventListener("change", validarRol);

    // ---------------- VALIDAR AL ENVIAR ---------------- //

    form.addEventListener("submit", (e) => {
        let valido = true;

        if (!validarDni()) valido = false;
        if (!validarNombre()) valido = false;
        if (!validarDireccion()) valido = false;
        if (!validarTelefono()) valido = false;
        if (!validarRol()) valido = false;

        if (!valido) {
            e.preventDefault();
            alert("Corrige los errores antes de guardar el usuario.");
        }
    });

});
