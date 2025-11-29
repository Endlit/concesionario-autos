// vehiculos.js
console.log("vehiculos.js cargado");

document.addEventListener("DOMContentLoaded", () => {

    const form = document.querySelector("form");

    const inputMatricula = form.querySelector("input[name='matricula']");
    const inputMarca = form.querySelector("input[name='marca']");
    const inputModelo = form.querySelector("input[name='modelo']");
    const inputCilindrada = form.querySelector("input[name='cilindrada']");
    const inputPrecio = form.querySelector("input[name='precio']");
    const inputCliente = form.querySelector("select[name='clienteDni']");

    // Crea contenedor de error si no existe
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

    function validarMatricula() {
        const err = getErrorBox(inputMatricula);
        const valor = inputMatricula.value.trim();

        if (valor === "") {
            err.textContent = "La matrícula es obligatoria.";
            return false;
        }
        if (!/^[A-Z]{3}[0-9]{3}$/i.test(valor)) {
            err.textContent = "Formato inválido (ejemplo: ABC123).";
            return false;
        }
        err.textContent = "";
        return true;
    }

    function validarMarca() {
        const err = getErrorBox(inputMarca);
        const valor = inputMarca.value.trim();

        if (valor === "") {
            err.textContent = "La marca es obligatoria.";
            return false;
        }
        if (!/^[A-Za-zÁÉÍÓÚÑáéíóúñ\s]{2,}$/.test(valor)) {
            err.textContent = "La marca debe contener solo letras (mínimo 2).";
            return false;
        }
        err.textContent = "";
        return true;
    }

    function validarModelo() {
        const err = getErrorBox(inputModelo);
        const valor = inputModelo.value.trim();

        if (valor === "") {
            err.textContent = "El modelo es obligatorio.";
            return false;
        }
        if (!/^[A-Za-z0-9\-ÁÉÍÓÚÑáéíóúñ\s]{2,}$/.test(valor)) {
            err.textContent = "Modelo inválido (mínimo 2 caracteres).";
            return false;
        }
        err.textContent = "";
        return true;
    }

    function validarCilindrada() {
        const err = getErrorBox(inputCilindrada);
        const valor = inputCilindrada.value.trim();

        if (valor === "") {
            err.textContent = "La cilindrada es obligatoria.";
            return false;
        }
        if (!/^[0-9]{2,4}$/.test(valor)) {
            err.textContent = "Debe ser un número entre 2 y 4 dígitos.";
            return false;
        }
        err.textContent = "";
        return true;
    }

    function validarPrecio() {
        const err = getErrorBox(inputPrecio);
        const valor = parseFloat(inputPrecio.value);

        if (isNaN(valor) || valor <= 0) {
            err.textContent = "El precio debe ser un número mayor a 0.";
            return false;
        }
        err.textContent = "";
        return true;
    }

    function validarCliente() {
        const err = getErrorBox(inputCliente);

        if (inputCliente.value === "") {
            err.textContent = ""; // cliente opcional
            return true;
        }

        if (!/^[0-9]{6,12}$/.test(inputCliente.value)) {
            err.textContent = "El DNI del cliente no es válido.";
            return false;
        }

        err.textContent = "";
        return true;
    }

    // ---------------- EVENTOS EN TIEMPO REAL ---------------- //

    inputMatricula.addEventListener("input", validarMatricula);
    inputMarca.addEventListeners("input", validarMarca);
    inputModelo.addEventListener("input", validarModelo);
    inputCilindrada.addEventListener("input", validarCilindrada);
    inputPrecio.addEventListener("input", validarPrecio);
    inputCliente.addEventListener("change", validarCliente);

    // ---------------- VALIDAR AL ENVIAR ---------------- //

    form.addEventListener("submit", (e) => {
        let valido = true;

        if (!validarMatricula()) valido = false;
        if (!validarMarca()) valido = false;
        if (!validarModelo()) valido = false;
        if (!validarCilindrada()) valido = false;
        if (!validarPrecio()) valido = false;
        if (!validarCliente()) valido = false;

        if (!valido) {
            e.preventDefault();
            Swal.fire({
                icon: "warning",
                title: "Revisa los datos",
                text: "Corrige los errores antes de guardar el vehículo.",
                confirmButtonColor: "#f39c12"
            });
        }
    });

});