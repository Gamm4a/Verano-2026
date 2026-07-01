import Estudiante from './Estudiante.js';

let estudiante = new Estudiante();
estudiante.nombre = "Gamma";
estudiante.carrera= "ISW";

console.log(estudiante.saludar());
console.log(estudiante.estudiar());
console.log(estudiante.verPromedio());


import {sumar as suma, PI} from './utils.js';
console.log(PI);
console.log(suma(5, 3));