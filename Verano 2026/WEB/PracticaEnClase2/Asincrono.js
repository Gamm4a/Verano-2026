function buscarUsuario(){
    setTimeOut(()=>{
        let usuario= {
            nombre: "Gamma",
            edad: 21
        }
        return usuario;
    }, 2000) //simulamos la asincronia ("una acción que tarda")
}

console.log("inicio la busqueda del usuario")
let usuario = buscarUsuario();
console.log("usuario encontrado: ", usuario);
console.log("fin de la busqueda del usuario")
