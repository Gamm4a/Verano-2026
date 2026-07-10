import AuthService from "../services/AuthService.js"
import PostService from "../services/PostService.js"
document.addEventListener('DOMContentLoaded', () => {

    const loadPost = async () =>{
        try {
            const posts = await PostService.getAll();

            const postContainer = document.getElementById('post-container');


            postContainer.innerHTML = posts.length ? '' : "<p> No hay posts aún</p>"

            posts.forEach(post => {
                const postEl = document.createElement('div');
                postEl.className = 'post-card fade-in';
                
                const author = post.user ? post.user.username : 'Usuario';
                const createdTime = post.createdAt ? new Date(post.createdAt).toLocaleString() : 'Hace un momento';

                postEl.innerHTML = `
                    <div class="post-header">
                        <img class="avatar" src="https://ui-avatars.com/api/?name=${author}&background=random"
                            alt="Avatar">
                        <div class="post-user-info">
                            <h3>${author}</h3>
                            <span>${createdTime}</span>
                        </div>
                    </div>
                    <div class="post-content">
                        <div class="post-title">${post.title}</div>
                        <p>${post.content}</p>
                    </div>
                    <div class="post-actions">
                        <button class="action-btn"><i class="far fa-thumbs-up"></i> Me gusta</button>
                        <button class="action-btn comment-trigger"><i class="far fa-comment"></i> Comentar</button>
                    </div>
                    <div class="comments-section" id="comments-${post.id}">
                        ${post.comments ? post.comments.map(c => `
                        <div class="comment">
                            <img class="avatar" style="width: 32px; height: 32px;"
                                src="https://ui-avatars.com/api/?name=${c.user ? c.user.username : 'U'}&background=random" alt="Avatar">
                            <div class="comment-bubble">
                                <div class="comment-user">${c.user ? c.user.username : 'Usuario'}</div>
                                <div class="comment-text">${c.text}</div>
                            </div>
                        </div>
                        `).join('') : ''}
                        <form class="comment-form" data-post-id="${post.id}">
                            <img class="avatar" style="width: 32px; height: 32px;"
                                src="https://ui-avatars.com/api/?name=${user?.username || 'U'}&background=4F46E5&color=fff" alt="Avatar">
                            <input type="text" class="comment-input" placeholder="Escribe un comentario..." required>
                        </form>
                    </div>
                `;
                postContainer.appendChild(postEl);
            });


        } catch (error) {
            alert(error.message)
        }
    }

    if (AuthService.isAutenticate()) {
        loadPost();

    } else {
        window.location.href = 'login.html';

    }

})



