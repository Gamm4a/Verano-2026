const API_URL = "https://dummyjson.com";

export async function obtenerProductos(params) {
    
    try {
        const response = await fetch(`${API_URL}/products/category/beauty`);

        if(!response.ok){
            return [];
        }
        const data = await response.json();

        return data.products;
    } catch (error) {
        return [];
    }
}
