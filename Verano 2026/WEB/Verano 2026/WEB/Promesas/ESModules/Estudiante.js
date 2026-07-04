import {Persona} from "./Persona.js";

export default class Estudiante extends Persona{
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