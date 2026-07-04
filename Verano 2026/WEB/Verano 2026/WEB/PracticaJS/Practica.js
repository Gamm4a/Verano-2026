let x = "Gamma";

console.log(x);

x = 2;

console.log(x);

let variableBol = 1; //0, "", undefined, null, NaN, false son valores falsos

if (!variableBol){
    console.log("True");
} else{
    console.log("False");
}

//funcion con parametros
function saludar(nombre ="invitado", edad = 10){
    return "hola: " + nombre+ " y tu edad es: " + edad;
}

console.log(saludar( 20));

//función anonima
let sumar = function(a, b){
    return a + b;
}

console.log(sumar(2, 3));

//función flecha
let restar = (a, b) => {console.log(a - b)};

restar(5, 3);

;(async function(){
    console.log("funcion autoejecutable");
})();

let arreglo = [];
let numeros = [1, 2, 3, 4, 5,6];
let arregloCadenas = ["cadena 1", "cadena 2", "cadena 3"];
let arreglobooleanos = [true, false, true, false];
let arregloObjetos = [{}, {}, {}];
let arregloDeArreglos = [[1, 2], [3, 4], [5, 6]];

console.log(numeros);
numeros.push(7);
console.log(numeros);
