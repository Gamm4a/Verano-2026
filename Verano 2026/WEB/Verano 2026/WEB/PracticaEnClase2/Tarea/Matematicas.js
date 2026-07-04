function operar(num1, num2, callback){
    const resultado = callback(num1, num2);
    console.log("Resultado:", resultado);
}

// Callbacks de operaciones
function suma(a, b){ return a + b; }
function resta(a, b){ return a - b; }
function multiplicacion(a, b){ return a * b; }
function division(a, b){
    return b !== 0 ? a / b : "Error: división entre cero";
}

// Ejemplos de uso:
operar(10, 5, suma);           // Resultado: 15
operar(10, 5, resta);          // Resultado: 5
operar(10, 5, multiplicacion); // Resultado: 50
operar(10, 5, division);       // Resultado: 2
