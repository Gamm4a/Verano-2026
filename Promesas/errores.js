function dividir(a, b){
    try {
        if(b === 0){
            throw "Error, no se puede dividir por cero";
        }
        return a/b;
    } catch (error) {
        console.error("Ocurrio un error en la divicion", error);
    } finally{
        console.log("Finalizo la ejecucion")
    }

}

console.log(dividir(10, 5))