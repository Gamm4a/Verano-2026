function cortarIngredientes(ingredientes, callback){
    setTimeout(()=>{
        console.log("Cortando ingredientes: ", ingredientes);
        callback();
    }, 2000)

}

function cocinar(accion, callback){
    setTimeout(()=>{
        console.log("Cocinando ingredientes: ", accion);
        callback();
    }, 3000)
}



function servirPlato(plato ){
    console.log("¡listo!: tu plato está servido: ", plato);
}

function prepararReceta(plato,callback){
        console.log("Preparando la receta ", plato)
        cortarIngredientes(plato.ingredientes, ()=>{
            cocinar("saltear",()=>{
                cortarIngredientes("Pollo", ()=>{
                    cocinar("Freir", ()=>{
                        cocinar("Mezclar todo",()=>{
                            servirPlato(plato);
                            callback();
                        })

                    })
                })
            })
        })
}


prepararReceta("pizza",()=>{
    console.log("Receta finalizada, buen provecho")
});