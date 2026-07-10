import AuthService from "../services/AuthService.js";

document.addEventListener('DOMContentLoaded', ()=>{
    const registerForm = document.getElementById('register-form');

    if(registerForm){
        registerForm.addEventListener('submit', async (e)=>{
            e.preventDefault();

            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;
            const email = document.getElementById('email').value
            try {
                await AuthService.register(username,email,password);
                window.location.href = 'login.html';
            } catch (error) {
                alert(error.message)
            }

        })
    }
    
})