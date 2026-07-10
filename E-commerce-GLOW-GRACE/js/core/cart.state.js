import { storage } from "../services/storage.service.js"

export let cart = storage.getCarrito();

export function addToCart(product) {
    let item = cart.find(i => i.id === product.id);

    if (item) {
        item.quantity += 1;
    } else {
        product = { ...product, quantity: 1 }
        cart.push(product)
    }

    saveCart();

}


export function saveCart() {
    storage.setCarrito(cart)
}


export function removeFromCart(productId) {
    const index = cart.findIndex(i => i.id === productId)
    if (index !== -1) {
        cart.splice(index, 1);
    }
    saveCart();

}

export function clearCart(){
    cart.length= 0;
    saveCart()
}

export function getCartTotal(){
    return cart.reduce((total, item) => total+(item.price*item.quantity));
}

export function getCartCounter(){
    return cart.reduce((contador,item) => contador + item.quantity,0);

}


