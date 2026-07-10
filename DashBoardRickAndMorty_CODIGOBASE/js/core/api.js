/**
 * URL base de la API oficial de Rick and Morty
 */
const BASE_URL = 'https://rickandmortyapi.com/api';

/**
 * Función central para obtener personajes desde la API.
 * 
 * @param {number} page - El número de página a solicitar.
 * @param {Object} filters - Objeto con filtros opcionales (name, status).
 * @returns {Promise<Object>} Datos de los personajes o error.
 */
export async function getCharacters(page = 1, filters = {}) {
    // TODO: 1. Construir la URL de la API de Rick and Morty con el parámetro de página (?page=...)
    let url= `${BASE_URL}/character?page=${page}`
    // TODO: 2. Si se proporciona un filtro por n.ombre (filters.name), agregarlo a la URL como query parameter (&name=...)
    if (filters.name) {
        url += `&name=${filters.name}`;
    }

    // TODO: 3. Si se proporciona un filtro por estado (filters.status) y este es diferente de 'all', agregarlo a la URL (&status=...)
    if(filters.status && filters.status !== 'all') url += `&status=${filters.status}`;
    // TODO: 4. Realizar la petición HTTP a la API usando la función fetch() de JavaScript
    // TODO: 5. Convertir la respuesta a JSON y retornarla. En caso de error, capturarlo y retornar un objeto con el error.
    try {
        const response = await fetch(url);
        return await response.json();
    } catch (error) {
        return [];
        
    }
    

}

