let numeros = [1,2,3,4,5,2,2,3];
numeros.push(6);
console.log(numeros);

numeros.pop();
numeros.unshift(0);
numeros.shift();

console.log(numeros.length)

console.log(numeros.includes(2))

numeros.indexOf(3)
console.log(numeros.join(" - "))

console.log(numeros.find(numero => numero === 2))
console.log(numeros.filter(numero => numero === 2))


let dobles=numeros.map(num => num * 2)
console.log(numeros)
console.log(dobles)

const persona = {
    nombre: "Gamma",
    edad: 21,
    hobbies: ["Cantar", "Teclado", "Musica"],
    direccion: {
        calle: "Lomás turba",
        numero: 69,
        ciudad: "gotica"
    },

    saludar: function () {
        return "Hola, me llamo " + this.nombre
    }
}
console.log(persona);

persona.nombre = "Juan";


