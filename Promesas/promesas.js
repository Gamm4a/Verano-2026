function obtenerDatos(){
    return new Promise((resolve, reject)=>{
        setTimeout(()=>{
            let exito = true;
            if (exito){
                resolve("Datos obtenidos correctamente")
            } else {
                reject("Error al obtener datos")
            }
        },2000)
    })
}
/*
obtenerDatos()
    .then(resultado => {
        console.log("then: ", resultado)
    })
    .catch(error => {
        console.error("catch: ", error)
    })
    .finally(() => {
        console.log("Finalizó la ejecución")
    })
*/


async function main(){
    try{
        let respuesta = await obtenerDatos();
        console.log(respuesta);
    }catch (error) {
        console.error(error);
    }
}

//main();

fetch('https://jsonplaceholder.typicode.com/todos/2')
    .then(response => response.json())
    .then(json => console.log(json))



async function fetchearPagina() {
    let response = await fetch('https://jsonplaceholder.typicode.com/todos/2');
    let json = await response.json();
}
