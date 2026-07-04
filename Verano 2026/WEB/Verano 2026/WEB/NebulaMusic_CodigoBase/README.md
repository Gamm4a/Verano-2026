# Nébula Music - Código Base (Instrucciones para Alumnos)

> [!IMPORTANT]
> **Antes de iniciar la clase:** Descarga o clona este directorio base completo en tu computadora local. Durante la sesión, deberás seguir la práctica guided por el profesor paso a paso para completar los formularios y layouts.

¡Bienvenido al código base de **Nébula Music**!
Esta es una plataforma de música en la nube diseñada para almacenar, organizar y reproducir tus canciones favoritas desde cualquier lugar.

En esta práctica, vas a dar tus primeros pasos construyendo la estructura fundamental de tus formularios de interacción (inicio de sesión y registro de cuentas) y una página de control de errores.

## 📁 Estructura del Proyecto
- `index.html`: Estructura principal del portal web de Nébula Music.
- `iniciar-sesion.html`: Esqueleto del formulario de autenticación de credenciales.
- `registro.html`: Esqueleto para la creación de nuevas cuentas de usuario.
- `error.html`: Estructura base para el reporte de errores del sitio.
- `styles/styles.css`: Hoja de estilos inicial con variables de color y tipografía base.
- `imgs/`: Recursos de imagen y logotipos del sitio.

## 🎯 Instrucciones de Trabajo (Práctica Guiada)

### 1. En `index.html` (Página de Inicio)
- La estructura base de la página de inicio ya se encuentra completa. Analizaremos en clase su maquetación semántica.

### 2. En `iniciar-sesion.html` (Formulario de Login)
Crea un formulario dentro de la etiqueta `<form>` con las siguientes especificaciones:
- **Atributos del Formulario:** Debe enviar sus datos usando el método `POST` y tener como destino de acción `/autenticacion`.
- **Campos del Formulario:**
  - Campo de tipo `email` para el correo electrónico del usuario (con su respectivo `<label>`).
  - Campo de tipo `password` para la contraseña (con su respectivo `<label>`).
- **Botones:** Un botón de envío (`type="submit"`) para iniciar y otro de limpieza (`type="reset"`) para cancelar.

### 3. En `registro.html` (Formulario de Registro)
Crea un formulario que envíe los datos vía `POST` a `/crear-cuenta`. Debe recolectar:
- **Nombre Completo:** Campo de texto.
- **Correo Electrónico:** Campo de tipo `email`.
- **Contraseña:** Campo de contraseña.
- **Pseudónimo:** Campo de texto con validación por patrón (`pattern="[a-zA-Z]{3,10}-[0-9]{2,10}"`) y marcador de posición `Nebula-2025`.
- **Género:** Botones de radio (`<input type="radio">`) para *Femenino*, *Masculino* y *Otro* (excluyentes, compartiendo el mismo atributo `name`).
- **Tipo de Suscripción:** Menú desplegable (`<select>`) con opciones para *Gratis*, *Premium Mensual* y *Premium Anual*.
- **Fecha de Nacimiento:** Campo de tipo `date`.
- **Aceptación de Términos:** Un checkbox (`<input type="checkbox">`).
- **Comentarios o gustos:** Área de texto (`<textarea>`).
- **Botones de Envío y Cancelación.**

### 4. En `error.html` (Página de Error)
- Construye un diseño amigable e ilustrativo para una página de error (ej. Código 404 - Recurso No Encontrado o Código 500 - Error Interno).
- Incluye un enlace visible que permita al usuario regresar a la página principal (`index.html`).

---

*¡Manos a la obra! Sigue las instrucciones del profesor durante la sesión.*
