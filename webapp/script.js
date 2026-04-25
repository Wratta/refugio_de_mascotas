/**
 * Sistema de Gestión Zoosanitaria
 * Lógica de persistencia en memoria y gestión de estados
 */

let censoAnimales = [];

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('animalForm');
    const btnBuscar = document.getElementById('btnBuscar');
    const inputBuscar = document.getElementById('buscarMicrochip');

    // 1. CARGA INICIAL: Pedimos los datos reales a la base de datos
    cargarAnimalesDesdeDB();

    // 2. REGISTRO: Enviamos el nuevo animal al Servlet
    form.addEventListener('submit', (event) => {
        event.preventDefault();

        const animalData = {
            nombre: document.getElementById('nombre').value.trim(),
            microchip: document.getElementById('microchip').value.trim(),
            especie: document.getElementById('especie').value,
            peso: parseFloat(document.getElementById('peso').value),
            idAdoptante: null
        };

        if (validarFormulario(animalData)) {
            guardarEnServidor(animalData);
        }
    });

    // 3. BÚSQUEDA: Filtramos sobre los datos ya cargados
    btnBuscar.addEventListener('click', () => {
        const microchipBusqueda = inputBuscar.value.trim();
        if (microchipBusqueda.length === 15) {
            const resultado = censoAnimales.filter(a => a.microchip === microchipBusqueda);
            actualizarTabla(resultado);
        } else if (microchipBusqueda.length === 0) {
            actualizarTabla(censoAnimales);
        } else {
            alert("Introduce los 15 dígitos del microchip.");
        }
    });
});

/**
 * PETICIÓN GET: Obtiene la lista de animales desde MySQL
 */
function cargarAnimalesDesdeDB() {
    fetch('animales')
            .then(response => response.json()) {
            if (!response.ok) throw new Error("Error al obtener datos");
            return response.json();
        })
        .then(datos => {
            censoAnimales = datos;
            actualizarTabla(censoAnimales);
        })
        .catch(error => {
            console.error("Error de conexión:", error);
            mostrarMensajeError("No se pudo conectar con el servidor Java.");
        });
}

/**
 * PETICIÓN POST: Envía el objeto JSON al Servlet para insertarlo en la DB
 */
function guardarEnServidor(animal) {
    fetch('animales', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(animal)
    })
    .then(response => {
        if (response.ok) {
            alert("Registro guardado con éxito en MySQL");
            document.getElementById('animalForm').reset();
            cargarAnimalesDesdeDB(); // Refrescamos la tabla con el dato real
            feedbackVisualBoton();
        } else {
            alert("Error al guardar en el servidor.");
        }
    })
    .catch(error => console.error("Error en el POST:", error));
}

/**
 * PETICIÓN DELETE (Simulada o real según el Servlet)
 */
function eliminarAnimal(microchip) {
    const animal = censoAnimales.find(a => a.microchip === microchip);
    
    if (animal && animal.idAdoptante) {
        alert("Restricción: No se puede eliminar un animal con adoptante vinculado.");
        return;
    }

    if (confirm(`¿Deseas dar de baja a ${animal.nombre}?`)) {
        alert("Operación de baja enviada al sistema.");
        // cargarAnimalesDesdeDB(); 
    }
}

/**
 * VALIDACIONES Y RENDERIZADO
 */

function validarFormulario(datos) {
    if (censoAnimales.some(a => a.microchip === datos.microchip)) {
        alert("Error: Microchip duplicado en el censo.");
        return false;
    }
    if (datos.microchip.length !== 15) {
        alert("El microchip debe tener 15 dígitos.");
        return false;
    }
    return true;
}

function ejecutarLogin() {
    const user = document.getElementById("user").value;
    const pass = document.getElementById("pass").value;

    // Enviamos los datos al Servlet
    fetch(`login?username=${user}&password=${pass}`, { method: 'POST' })
        .then(res => res.json())
        .then(data => {
            if (data.success) {
                document.getElementById("loginSection").style.display = "none";
                document.getElementById("contenidoWeb").style.display = "block";
                
                // Si es VETERINARIO, mostramos sus herramientas
                if (data.rol === "VETERINARIO") {
                    document.getElementById("controlesVeterinario").style.display = "block";
                }
                
                cargarAnimales();
            } else {
                alert(data.message);
            }
        });
}

function actualizarTabla(lista) {
    const contenedor = document.getElementById('tablaAnimalesContainer');
    
    if (lista.length === 0) {
        contenedor.innerHTML = "<p style='text-align:center; padding:20px;'>Censo vacío.</p>";
        return;
    }

    let html = `<table class="tabla-datos">
        <thead>
            <tr>
                <th>Nombre</th><th>Microchip</th><th>Especie</th><th>Peso</th><th>Estado</th><th>Acciones</th>
            </tr>
        </thead>
        <tbody>`;

    lista.forEach(animal => {
        const esAdoptado = animal.idAdoptante !== null && animal.idAdoptante !== undefined;
        const textoEstado = esAdoptado ? `Adoptado (${animal.idAdoptante})` : "Disponible";
        const claseEstado = esAdoptado ? "status-adopted" : "status-available";
        
        html += `
            <tr>
                <td>${animal.nombre}</td>
                <td>${animal.microchip}</td>
                <td>${animal.especie}</td>
                <td>${animal.peso}kg</td>
                <td><span class="status-badge ${claseEstado}">${textoEstado}</span></td>
                <td>
                    <div class="action-buttons">
                        ${!esAdoptado ? `<button class="btn-adopt" onclick="simularAdopcion('${animal.microchip}')">Adoptar</button>` : `<span class="label-lock">🔒</span>`}
                        <button class="btn-delete" onclick="eliminarAnimal('${animal.microchip}')">Eliminar</button>
                    </div>
                </td>
            </tr>`;
    });

    html += `</tbody></table>`;
    contenedor.innerHTML = html;
}

function feedbackVisualBoton() {
    const btn = document.querySelector('.btn-submit');
    const original = btn.innerHTML;
    btn.innerText = "¡Enviado a Java!";
    btn.style.backgroundColor = "#8db580";
    setTimeout(() => {
        btn.innerHTML = original;
        btn.style.backgroundColor = "";
    }, 2000);
}

function mostrarMensajeError(msg) {
    document.getElementById('tablaAnimalesContainer').innerHTML = `<p style="color:red; text-align:center;">${msg}</p>`;
}