import { obtenerProductos } from "../services/api.service.js"
import { getCartCounter, addToCart} from "../core/cart.state.js"

let allProducts = [];

const productsGrid = document.getElementById('products-grid')
const cartCounter = document.getElementById('cart-counter');
const searchInput = document.getElementById('product-search');

document.addEventListener('DOMContentLoaded', async () => {
    console.log(window);
    console.log(window.navigation);
    
    allProducts = await obtenerProductos();
    renderProducts(allProducts);
    updateCartCounter();
    setupEventsListeners();

})

function renderProducts(products) {
    if (products.lenght === 0) {
        productsGrid.innerHTML = " <div class='loader'>no se encontraron productos</div>"
        return;
    }

    productsGrid.innerHTML = products.map(product => `
        <div class="product-card">
            <div class="sale-badge">Oferta</div>
            <img src="${product.thumbnail}" alt="${product.title}" class="product-image">
            <div class="product-info">
                <h3>${product.title}</h3>
                <div class="price-container">
                    <span class="old-price">${(product.price * 1.25).toFixed(2)}</span>
                    <span class="product-price">${product.price.toFixed(2)}</span>
                </div>
                <button class="btn-primary add-to-cart-btn" data-id="${product.id}">
                    Agregar al carrito
                </button>
            </div>
        </div>
        `).join('');

    const buttons = document.querySelectorAll('.add-to-cart-btn');

    buttons.forEach(btn => {
        btn.addEventListener('click', () => {
            const id = parseInt(btn.getAttribute('data-id'));
            const product = allProducts.find(p => p.id === id)
            addToCart(product);
            updateCartCounter();

        })
    })

}

function updateCartCounter() {
    if (cartCounter) {
        cartCounter.textContent = getCartCounter();
    }
}

function setupEventsListeners(){
    searchInput.addEventListener('input',(e)=> filterProducts(e.target.value)

    )
}

function filterProducts(filtro){
    const filtered = allProducts.filter(product=>
        product.title.toLowerCase().includes(filtro.toLowerCase())
)
    renderProducts(filtered);

}




