import AuthService from "../services/AuthService.js";

document.addEventListener('DOMContentLoaded', ()=>{
    const loginForm = document.getElementById('login-form');

    if(loginForm){
        loginForm.addEventListener('submit', async (e)=>{
            e.preventDefault();
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;

            try {
                await AuthService.login(username,password);
                window.location.href = 'index.html';
            } catch (error) {
                alert(error.message)
            }

        })
    }
    
})