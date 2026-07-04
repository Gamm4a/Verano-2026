// =====================================
// Sistema de Gestión de Tareas
// =====================================

// =====================================
// 1. Arreglo inicial de tareas
// =====================================

const tareas = [
    { nombre: "Ir a la playa", prioridad: 1, completada: false },
    { nombre: "Estudiar JS", prioridad: 2, completada: true },
    { nombre: "Ver películas", prioridad: 3, completada: false }
];


// =====================================
// 2. Funciones
// =====================================

/*
TODO 1:
Recorrer arreglo y mostrar nombre + estado
*/
function mostrarTareas(lista) {
    lista.forEach(tarea => {
        const estado = tarea.completada ? "Completada" : "Pendiente";
        console.log(`${tarea.nombre} - ${estado}`);
    });
}

/*
TODO 2:
Retornar solo tareas completadas
*/
const obtenerCompletadas = (lista) => {
    return lista.filter(tarea => tarea.completada);
};

/*
TODO 3:
Retornar solo tareas pendientes
*/
const obtenerPendientes = (lista) => {
    return lista.filter(tarea => !tarea.completada);
};

/*
TODO 4:
Retornar solo nombres de tareas
*/
const obtenerNombres = (lista) => {
    return lista.map(tarea => tarea.nombre);
};

/*
TODO 5:
Retornar total de tareas
*/
function contarTareas(lista) {
    return lista.length;
}


// =====================================
// 3. Objeto sistema
// =====================================

const sistema = {
    tareas: tareas,

    mostrarTareas: function() {
        mostrarTareas(this.tareas);
    },

    mostrarCompletadas: function() {
        console.log("Tareas completadas:");
        obtenerCompletadas(this.tareas).forEach(t => console.log(t.nombre));
    },

    mostrarPendientes: function() {
        console.log("Tareas pendientes:");
        obtenerPendientes(this.tareas).forEach(t => console.log(t.nombre));
    }
};


// =====================================
// 4. Condicionales
// =====================================

if (tareas.length === 0) {
    console.log("No hay tareas");
} else if (tareas.every(t => t.completada)) {
    console.log("Todas las tareas completadas");
}


// =====================================
// 5. Switch
// =====================================

const opcion = 1;

switch (opcion) {
    case 1:
        sistema.mostrarTareas();
        break;

    case 2:
        sistema.mostrarCompletadas();
        break;

    case 3:
        sistema.mostrarPendientes();
        break;

    default:
        console.log("Opción inválida");
}


// =====================================
// 6. Pruebas
// =====================================

console.log("=== Pruebas ===");
mostrarTareas(tareas); // Mostrar todas
console.log("Completadas:", obtenerCompletadas(tareas));
console.log("Pendientes:", obtenerPendientes(tareas));
console.log("Nombres:", obtenerNombres(tareas));
console.log("Total de tareas:", contarTareas(tareas));

sistema.mostrarTareas();
sistema.mostrarCompletadas();
sistema.mostrarPendientes();
