/**
 * Sistema de Gestión Zoosanitaria
 * Lógica de persistencia en memoria y gestión de estados
 */

// 1. Censo inicial de datos
let censoAnimales = [
    { nombre: "Toby", microchip: "123456789012345", especie: "PERRO", peso: 12.5, idAdoptante: null },
    { nombre: "Luna", microchip: "987654321098765", especie: "GATO", peso: 4.2, idAdoptante: "AD-772" }
];

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('animalForm');
    const btnBuscar = document.getElementById('btnBuscar');
    const inputBuscar = document.getElementById('buscarMicrochip');

    // Carga inicial de la tabla
    actualizarTabla(censoAnimales);

    // Evento: Registro de nuevo animal
    form.addEventListener('submit', (event) => {
        event.preventDefault();

        const animalData = {
            nombre: document.getElementById('nombre').value.trim(),
            microchip: document.getElementById('microchip').value.trim(),
            especie: document.getElementById('especie').value,
            peso: parseFloat(document.getElementById('peso').value),
            idAdoptante: null // Registro inicial siempre disponible
        };

        if (validarFormulario(animalData)) {
            censoAnimales.push(animalData);
            actualizarTabla(censoAnimales);
            
            feedbackVisualBoton();
            form.reset();
            document.getElementById('nombre').focus();
        }
    });

    // Evento: Búsqueda filtrada
    btnBuscar.addEventListener('click', () => {
        const microchip = inputBuscar.value.trim();
        if (microchip.length === 15) {
            const resultado = censoAnimales.filter(a => a.microchip === microchip);
            actualizarTabla(resultado);
        } else if (microchip.length === 0) {
            actualizarTabla(censoAnimales);
        } else {
            alert("Introduce los 15 dígitos del microchip.");
        }
    });
});

/**
 * Validaciones de integridad de datos
 */
function validarFormulario(datos) {
    const duplicado = censoAnimales.find(a => a.microchip === datos.microchip);
    if (duplicado) {
        alert("Error: El microchip ya existe en el censo.");
        return false;
    }
    if (datos.microchip.length !== 15 || isNaN(datos.microchip)) {
        alert("El microchip debe ser numérico y tener 15 dígitos.");
        return false;
    }
    return true;
}

/**
 * Renderizado dinámico de la tabla
 */
function actualizarTabla(lista) {
    const contenedor = document.getElementById('tablaAnimalesContainer');
    
    if (lista.length === 0) {
        contenedor.innerHTML = "<p style='text-align: center; padding: 20px; color: #888;'>Sin registros.</p>";
        return;
    }

    let html = `
        <table class="tabla-datos">
            <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Microchip</th>
                    <th>Esp.</th>
                    <th>Peso</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>`;

    lista.forEach(animal => {
        const tieneAdoptante = animal.idAdoptante !== null && animal.idAdoptante !== undefined && animal.idAdoptante !== "";
        const textoEstado = tieneAdoptante ? `Adoptado (${animal.idAdoptante})` : "Disponible";
        const claseEstado = tieneAdoptante ? "status-adopted" : "status-available";
        
        html += `
            <tr>
                <td>${animal.nombre}</td>
                <td>${animal.microchip}</td>
                <td>${animal.especie}</td>
                <td>${animal.peso.toFixed(1)}kg</td>
                <td><span class="status-badge ${claseEstado}">${textoEstado}</span></td>
                <td>
                    <div class="action-buttons">
                        ${!tieneAdoptante ? 
                            `<button class="btn-adopt" onclick="simularAdopcion('${animal.microchip}')">Adoptar</button>` : 
                            `<span class="label-lock">Vinculado</span>`
                        }
                        <button class="btn-delete" onclick="eliminarAnimal('${animal.microchip}')">Eliminar</button>
                    </div>
                </td>
            </tr>`;
    });

    html += `</tbody></table>`;
    contenedor.innerHTML = html;
}

/**
 * Gestión de Adopciones
 */
function simularAdopcion(microchip) {
    const id = prompt("ID del Adoptante:");
    if (id && id.trim() !== "") {
        const animal = censoAnimales.find(a => a.microchip === microchip);
        if (animal) {
            animal.idAdoptante = id.trim();
            actualizarTabla(censoAnimales);
        }
    }
}

/**
 * Gestión de Bajas
 */
function eliminarAnimal(microchip) {
    const animal = censoAnimales.find(a => a.microchip === microchip);
    if (animal.idAdoptante) {
        alert("No se puede eliminar un animal con contrato de adopción activo.");
        return;
    }
    if (confirm(`¿Eliminar a ${animal.nombre}?`)) {
        censoAnimales = censoAnimales.filter(a => a.microchip !== microchip);
        actualizarTabla(censoAnimales);
    }
}

function feedbackVisualBoton() {
    const btn = document.querySelector('.btn-submit');
    const original = btn.innerHTML;
    btn.innerText = "¡Registrado!";
    btn.style.backgroundColor = "#8db580";
    setTimeout(() => {
        btn.innerHTML = original;
        btn.style.backgroundColor = "";
    }, 2000);
}