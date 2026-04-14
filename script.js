/**
 * Gestión del sistema de registro y censo animal
 * Versión: Simulación de Persistencia en Memoria
 */

// 1. "Base de datos" temporal en memoria
let censoAnimales = [
    { nombre: "Toby", microchip: "123456789012345", especie: "PERRO", peso: 12.5 },
    { nombre: "Luna", microchip: "987654321098765", especie: "GATO", peso: 4.2 }
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
            peso: parseFloat(document.getElementById('peso').value)
        };

        if (validarFormulario(animalData)) {
            // Guardamos en nuestro array local
            censoAnimales.push(animalData);
            
            // Refrescamos la tabla con el nuevo dato
            actualizarTabla(censoAnimales);
            
            // Feedback y limpieza
            feedbackVisualBoton();
            form.reset();
            document.getElementById('nombre').focus();
            
            console.log("Nuevo animal registrado en memoria:", animalData);
        }
    });

    // Evento: Búsqueda por microchip
    btnBuscar.addEventListener('click', () => {
        const microchipBusqueda = inputBuscar.value.trim();

        if (microchipBusqueda.length === 15) {
            // Filtramos el array por el microchip exacto
            const resultado = censoAnimales.filter(a => a.microchip === microchipBusqueda);
            actualizarTabla(resultado);
            
            if (resultado.length === 0) {
                alert("No se ha encontrado ningún animal con ese microchip en el censo actual.");
            }
        } else if (microchipBusqueda.length === 0) {
            // Si el campo está vacío, restauramos la lista completa
            actualizarTabla(censoAnimales);
        } else {
            alert("El microchip debe tener exactamente 15 dígitos para la búsqueda.");
        }
    });
});

function validarFormulario(datos) {
    // Comprobar si el microchip ya existe en nuestro censo local
    const duplicado = censoAnimales.find(a => a.microchip === datos.microchip);
    if (duplicado) {
        alert("Error: Ya existe un animal registrado con este número de microchip.");
        return false;
    }

    if (datos.microchip.length !== 15 || isNaN(datos.microchip)) {
        alert("Error Legal: El microchip debe tener 15 dígitos numéricos.");
        return false;
    }

    if (datos.peso <= 0) {
        alert("Error: El peso debe ser un número positivo.");
        return false;
    }

    return true;
}

function feedbackVisualBoton() {
    const btn = document.querySelector('.btn-submit');
    const textoOriginal = btn.innerHTML;

    btn.style.backgroundColor = "#8db580"; 
    btn.innerText = "¡Guardado con éxito!";

    setTimeout(() => {
        btn.style.backgroundColor = ""; 
        btn.innerHTML = textoOriginal;
    }, 2000);
}

function actualizarTabla(lista) {
    const contenedor = document.getElementById('tablaAnimalesContainer');
    
    if (lista.length === 0) {
        contenedor.innerHTML = "<p style='text-align: center; padding: 20px; color: #888;'>No se han encontrado registros.</p>";
        return;
    }

    let html = `
        <table class="tabla-datos">
            <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Microchip</th>
                    <th>Especie</th>
                    <th>Peso (kg)</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>`;

    lista.forEach(animal => {
        html += `
            <tr>
                <td>${animal.nombre}</td>
                <td>${animal.microchip}</td>
                <td>${animal.especie}</td>
                <td>${animal.peso.toFixed(2)}</td>
                <td>
                    <button class="btn-delete" onclick="eliminarAnimal('${animal.microchip}')">
                        Eliminar
                    </button>
                </td>
            </tr>`;
    });

    html += `</tbody></table>`;
    contenedor.innerHTML = html;
}

/**
 * Elimina un animal del censo mediante su microchip
 */
function eliminarAnimal(microchip) {
    // Confirmación de seguridad
    const confirmar = confirm(`¿Estás seguro de que deseas eliminar el registro con microchip ${microchip}?`);
    
    if (confirmar) {
        // Filtramos el censo para mantener todos menos el que coincide con el microchip
        censoAnimales = censoAnimales.filter(animal => animal.microchip !== microchip);
        
        // Refrescamos la tabla para que el cambio sea visible
        actualizarTabla(censoAnimales);
        
        console.log("Registro eliminado. Nuevo censo:", censoAnimales);
    }
}