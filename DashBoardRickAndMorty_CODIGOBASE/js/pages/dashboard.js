import { store } from '../state/store.js';
import { getCharacters } from '../core/api.js';

/**
 * Objeto Dashboard que maneja toda la lógica de la interfaz de usuario.
 */
export const dashboard = {
    // Referencias a elementos del DOM
    elements: {
        grid: document.getElementById('charactersGrid'),
        favoritesList: document.getElementById('favoritesList'),
        search: document.getElementById('characterSearch'),
        filter: document.getElementById('statusFilter'),
        loader: document.getElementById('loader'),
        noResults: document.getElementById('noResults'),
        prevBtn: document.getElementById('prevPage'),
        nextBtn: document.getElementById('nextPage'),
        pageInfo: document.getElementById('pageInfo')
    },

    /**
     * Inicializa la página cargando los eventos y los datos iniciales.
     */
    async init() {
        this.setupEventListeners();
        await this.loadCharacters();
        this.renderFavorites();
        lucide.createIcons();
        // TODO: 1. Configurar los escuchadores de eventos llamando a setupEventListeners()
        // TODO: 2. Cargar los personajes iniciales llamando a loadCharacters()
        // TODO: 3. Renderizar los favoritos iniciales (si existen en el local storage) llamando a renderFavorites()
    },

    /**
     * Configura los escuchadores de eventos para búsqueda, filtro y paginación.
     */
    setupEventListeners() {
// TODO: 1. Escuchar el evento 'input' en el input de búsqueda (this.elements.search)
        //          - Se sugiere implementar un 'debounce' (retardo) de 500ms para evitar múltiples peticiones seguidas al API.
        //          - Al escribir, actualizar store.state.filters.name con el texto limpio y reiniciar store.state.currentPage a 1.
        //          - Llamar a loadCharacters() para recargar la grilla.
        //
        let timeout;
        this.elements.search.addEventListener('input', (e) => {
            clearTimeout(timeout);
            timeout = setTimeout(() => {
                store.state.filters.name = e.target.value.trim();
                store.state.currentPage = 1;

                this.loadCharacters();
            }, 1000)
        })

        // TODO: 2. Escuchar el evento 'change' en el select de filtros (this.elements.filter)
        //          - Actualizar store.state.filters.status con el valor del select y reiniciar store.state.currentPage a 1.
        //          - Llamar a loadCharacters() para recargar.
        //
        this.elements.filter.addEventListener('change', (e) => {
            store.state.filters.status = e.target.value;
            store.state.currentPage = 1;
            this.loadCharacters();
        })

        // TODO: 3. Escuchar el evento 'click' en el botón de página anterior (this.elements.prevBtn)
        //          - Si store.state.currentPage es mayor que 1, restarle 1.
        //          - Llamar a loadCharacters() y hacer scroll hacia arriba (window.scrollTo({ top: 0, behavior: 'smooth' })).
        //
        this.elements.prevBtn.addEventListener('click', () => {
            if (store.state.currentPage > 1) {
                store.state.currentPage--;
                this.loadCharacters();
                window.scrollTo({ top: 0, behavior: 'smooth' });
            }
        })
        // TODO: 4. Escuchar el evento 'click' en el botón de página siguiente (this.elements.nextBtn)
        //          - Si store.state.currentPage es menor que store.state.totalPages, sumarle 1.
        //          - Llamar a loadCharacters() y hacer scroll hacia arriba.
        this.elements.nextBtn.addEventListener('click', () => {
            if (store.state.currentPage < store.state.totalPages) {
                store.state.currentPage++;
                this.loadCharacters();
                window.scrollTo({ top: 0, behavior: 'smooth' });
            }
        })    },

    /**
     * Solicita los personajes a la API según el estado actual de filtros y página.
     */
    async loadCharacters() {
        // TODO: 1. Mostrar el loader (showLoader(true)) y ocultar el mensaje de no resultados (this.elements.noResults)
        this.showLoader(true);
        this.elements.noResults.classList.add('hidden');
        // TODO: 2. Llamar a la función getCharacters importada, pasándole la página y los filtros actuales del store.
        const data = await getCharacters(store.state.currentPage, store.state.filters);
        // TODO: 3. Si la respuesta contiene un error o no tiene resultados (.results), vaciar store.state.characters, poner totalPages en 0 y mostrar el elemento noResults (quitar la clase 'hidden').
        // TODO: 4. Si la respuesta es exitosa, guardar los resultados en store.state.characters y el número total de páginas en store.state.totalPages.
        if (data.error || !data.results) {

            store.state.characters = [];
            store.state.currentPage = 0;
            this.elements.noResults.classList.remove('hidden');
        } else {
            store.state.characters = data.results
            store.state.currentPage = data.info.pages
        }

        // TODO: 5. Llamar a renderGrid() para pintar los personajes, updatePagination() para actualizar los controles y ocultar el loader (showLoader(false)).

        this.renderGrid();
        this.updatePagination();
        this.showLoader(false);
    },

    /**
     * Dibuja la cuadrícula de personajes en el DOM.
     */
    renderGrid() {
        // TODO: 1. Limpiar las tarjetas de personajes anteriores en la grilla (puedes borrar todos los elementos con clase '.character-card').
        // TODO: 2. Recorrer la lista de personajes en store.state.characters.
        // TODO: 3. Para cada personaje, crear un elemento div con la clase 'character-card'.
        // TODO: 4. Inyectar el HTML de la tarjeta (usa la plantilla del personaje que viene en recursos/resursos.md).
        //          - Asegúrate de mapear: imagen, nombre, estado (Alive, Dead, Unknown - usa las clases css status-alive, status-dead, status-unknown) e ID del personaje.
        //          - Marca el botón de favoritos como activo (clase 'active') si el personaje ya está en favoritos (usa store.isFavorite(id)).
        // TODO: 5. Agregar un escuchador de eventos click al botón de favorito (.fav-btn) de la tarjeta:
        //          - Llamar a store.toggleFavorite(personaje) para alternarlo.
        //          - Actualizar visualmente el botón (añadir/quitar clase 'active' y cambiar texto a 'En Favoritos' o 'Añadir a Favoritos').
        //          - Volver a renderizar la sección de favoritos lateral (renderFavorites()) para reflejar el cambio.
        //          - Invocar lucide.createIcons() para refrescar los iconos de Lucide en los nuevos elementos.
        // TODO: 6. Agregar la tarjeta creada al contenedor de la grilla (this.elements.grid.appendChild).
        // TODO: 7. Al finalizar el bucle, llamar a lucide.createIcons() para refrescar los iconos iniciales.

        const cards = this.elements.grid.querySelectorAll('.character-card');
        cards.forEach(c => c.remove());

        store.state.characters.forEach(char => {
            const isFav = store.isFavorite(char.id);
            const card = document.createElement('div');
            card.className = 'character-card';

            card.innerHTML = `
            Tarjeta para el personaje
                <div class="card-image-wrapper">
                    <img src="${char.image}" alt="${char.name}" loading="lazy">
                </div>
                <div class="card-content">
                    <h3 class="character-name">${char.name}</h3>
                    <div class="status- label status-${char.status.toLowerCase()}">
                        <span class="status-indicator"></span>
                        ${char.status}
                    </div>
                    <button class="fav-btn ${isFav ? 'active' : ''}" data-id="${char.id}">
                        <i data-lucide="star"></i>
                        <span>${isFav ?'En favoritos' : 'Añadir a favoritos'}</span>
                    </button>
                </div>
            `;

            card.querySelector('.fav-btn').addEventListener('click', (e)=>{
                const btn = e.currentTarget;
                const added = store.toggleFavorite(char);

                btn.classList.toggle('active', added);
                btn.querySelector('span').textContent = added ? 'En favoritos' : 'Añadir a favoritos';
                this.renderFavorites();


            })
                this.elements.grid.appendChild(card);

        })


    },

    /**
     * Dibuja la lista de personajes favoritos en la barra lateral.
     */
    renderFavorites() {
        const { favoritesList } = this.elements;

        // TODO: 1. Si la lista store.state.favorites está vacía:
        //          - Inyectar el HTML para estado vacío (puedes ver la plantilla de vacíos en index.html o usar un div simple con clase 'empty-favorites').
        //          - Terminar la ejecución de la función.
        //
        // TODO: 2. Si hay favoritos, vaciar favoritesList.innerHTML.
        // TODO: 3. Recorrer cada favorito de store.state.favorites.
        // TODO: 4. Crear un elemento div con clase 'fav-item' e inyectarle su estructura HTML (usa recursos/resursos.md como guía).
        // TODO: 5. Agregar un escuchador de eventos click en el botón de eliminar favorito (.remove-fav):
        //          - Aquel que llama a store.toggleFavorite(favorito) para quitarlo.
        //          - Llamar de nuevo a renderFavorites() y renderGrid() para que toda la interfaz se actualice.
        // TODO: 6. Añadir el elemento creado a favoritesList.
        // TODO: 7. Llamar a lucide.createIcons() para pintar el icono de cierre.
        if (store.state.favorites.length === 0) {
            favoritesList.innerHTML = `<div class= "empty-favorites">No hay favoritos</div>`;
            return;
        }
        favoritesList.innerHTML = '';
        store.state.favorites.forEach(fav => {
            const item = document.createElement('div');
            item.className = 'fav-item';
            item.innerHTML =`
            <img src="${fav.image}" alt="${fav.name}">
                <div class="fav-item-info">
                    <p class="fav-item-name">${fav.name}</p>
                </div>
                <button class="remove-fav" data-id="${fav.id}">
                    X
                </button>
            `;
            item.querySelector('.remove-fav').addEventListener('click', (e) => {
                store.toggleFavorite(fav);
                this.renderFavorites();
                this.renderGrid();
            });
            favoritesList.appendChild(item);
        });

    },

    /**
     * Actualiza el estado de los botones de paginación y el texto informativo.
     */
    updatePagination() {
            this.elements.prevBtn.disabled = store.state.currentPage == 1;
        this.elements.nextBtn.disabled = store.state.currentPage >= store.state.totalPages;
        this.elements.pageInfo.textContent = `Página ${store.state.currentPage} de ${store.state.totalPages}`


        // TODO: 1. Deshabilitar o habilitar this.elements.prevBtn si estamos en la página 1 (store.state.currentPage <= 1).
        // TODO: 2. Deshabilitar o habilitar this.elements.nextBtn si estamos en la página máxima (store.state.currentPage >= store.state.totalPages).
        // TODO: 3. Cambiar el textContent de this.elements.pageInfo para mostrar la página actual y el total (Ej: "Página X de Y").
    },

    /**
     * Muestra u oculta el spinner de carga.
     * 
     * @param {boolean} show - True para mostrar, False para ocultar
     */
    showLoader(show) {
        this.elements.loader.classList.toggle('hidden', !show);

        // TODO: Alternar la clase 'hidden' en el contenedor del loader (this.elements.loader) de acuerdo al parámetro 'show'.
    }
};
