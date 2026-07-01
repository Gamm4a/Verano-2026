function temporizador(callback){
    setTimeout(()=>{
        callback("¡Éxito! Han pasado 3 segundos.");
    }, 3000);
}

// Uso:
temporizador((mensaje)=>{
    console.log(mensaje);
});
