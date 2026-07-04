function buscarUsuario(callback) {
    setTimeout(()=>{
        let usuario= {
            nombre: "Gamma",
            edad: 21
        }
        callback(usuario);
    }, 2000) //simulamos la asincronia ("una acción que tarda")
}

let funcionCallback=(usuario) => {console.log("usuario encontrado: ", usuario)};

console.log("inicio la busqueda del usuario")
buscarUsuario(funcionCallback)