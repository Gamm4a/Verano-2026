export class Persona{
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

