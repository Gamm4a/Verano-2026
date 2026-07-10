class persona{
    constructor(nombre) {
        this.nombre = nombre;
        this.id = this.#generarID();
    }

    saludar() {
    return "Hola, me llamo " + this.nombre;
    }

    #generarID(){
        return Math.floor(Math.random() * 1000);

    }
}
class Estudiante extends persona{
    constructor() {
        super();
        this.carrera="";
    }

    estudiar(){
        return "Estudiando en la carrera de " + this.carrera;
    }

    #calcularPromedio(){
        return 6.9;
    }

    verPromedio(){
        return "Promedio: " + this.#calcularPromedio();
    }
}

let estudiante = new Estudiante();
estudiante.nombre = "Gamma";
estudiante.carrera= "ISW";

console.log(estudiante.saludar());
console.log(estudiante.estudiar());
console.log(estudiante.verPromedio());
