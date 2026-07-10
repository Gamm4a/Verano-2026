import { obtenerProductos } from "../services/api.service.js";
import {getCartCounter,addToCart} from "../core/cart.state.js"

let allProducts = [];

const productosGrid = document.getElementById('products-grid');
const cartCounter = document.getElementById('cart-counter')
const searchImput = document.getElementById('product-search')

document.addEventListener('DOMContentLoaded', async () => {
    console.log(window)
    console.log(window.Navigation)
    allProducts = await obtenerProductos();
    renderProducts(allProducts);
    updateCartCounter();
    setupEventListener();
})

function renderProducts(products) {
    if (products.length === 0) {
        productosGrid.innerHTML = "<div class='loader'>No se encontraros productos</div>";
        return;
    }
    productosGrid.innerHTML = products.map(product => `
        <div class="product-card">
            <div class="sale-badge">Oferta</div>
            <img src="${product.thumbnail}" alt="${product.title}" class="product-image">
        <div class="product-info">
            <h3>${product.title}</h3>
            <div class="price-container">
            <span class="old-price">${(product.price * 1.25)}</span>
            <span class="product-price">${(product.price.toFixed(2))}</span>
        </div>
        <button class="btn-primary add-to-cart-btn" data-id="${product.id}">
            Agregar al carrito
        </button>
    </div>
</div>
        `).join('');

        const buttons = document.querySelectorAll('.add-to-cart-btn');

        buttons.forEach(btn =>{
            btn.addEventListener('click',()=>{
                const id = parseInt(btn.getAttribute('data-id'));
                const product = allProducts.find(p => p.id === id);
                addToCart(product);
                updateCartCounter();

            })
        })
}

function updateCartCounter(){
    if(cartCounter){
        cartCounter.textContent= getCartCounter();
    }
}

function setupEventListener(){
    searchImput.addEventListener('input' ,(e) => filterProducts(e.target.value));

}
 function filterProducts(filtro){
        const filtered = allProducts.filter(product =>
            product.title.toLowerCase().includes(filtro.toLowerCase()))
    renderProducts(filtered);

 }