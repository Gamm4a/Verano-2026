const BASE_URL = 'http://localhost:8080/api'

class PostService {

    static getHeaders() {
        const token = localStorage.getItem('token')
        return {
            'Content-Type': "aplication/json",
            'Authorization': `Bearer ${token}`
        }
    }

    static async getAll() {


        const response = await fetch(`${BASE_URL}/posts`, {headers: this.getHeaders()});
        const data = await response.json();

        if(!response.ok){
            if(response.status===401) throw new Error('El token a expirado')
            const error =data.error || 'Error al obtener los posts'
            throw new Error(error);
        }

        return data;
    }



    


}


export default PostService;