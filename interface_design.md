# Diseño de la interfaz

## Esquema general de navegación

Inicialmente la aplicación comienza en el login, donde el usuario tendrá que iniciar sesión. Posteriormente de haber iniciado
sesión, se navegará a la landing page, donde se mostrará la descripción de la aplicación y se habrá habilitado la
hamburguesa de navegación, con la que el usuario podrá navegar entre las diferentes secciones de la aplicación. Estas
secciones son:

   - Inicio
   - Diferentes tiendas de productos
   - Carrito de compras global
   - Chat con administradores
   - Ver ofertas recibidas
   - Ver mensajes enviados
   - Pedidos

<img src="https://github.com/dhrodao/Android-Shop-App/blob/dev/docs/flujo-ui.jpg" alt="Demo vert" data-canonical-src="docs/screenshot.png" width="300"/>

## Look and feel de las diferentes pantallas
   - Login: La página de login se ha diseñado con una doble funcionalidad. Esta página tiene dos pestañas mediante las cuales el usuario puede hacer login, o por el contrario, si este no dispone de una cuenta en la plataforma, registrar su usuario.
<img src="https://github.com/dhrodao/Android-Shop-App/blob/dev/docs/login.png" alt="Demo vert" data-canonical-src="docs/screenshot.png" width="300"/>

   - Inicio: Esta página es muy simple, únicamente contiene un header con la foto de la tienda física y una breve descripción del producto. Esta es la página a la que se redirige al usuario una vez hace un login exitoso en la plataforma.
<img src="https://github.com/dhrodao/Android-Shop-App/blob/dev/docs/landing.png" alt="Demo vert" data-canonical-src="docs/screenshot.png" width="300"/>

   - Tiendas de productos: Hay diferentes tiendas disponibles en la aplicación, todas tienen el mismo diseño. Estas tiendas disponen de un selector de productos, una vez se ha seleccionado el producto aparecen diferentes opciones entre las que el usuario puede seleccionar la cantidad de unidades del producto que desea comprar, además a la vez que cambian las unidades del producto, cambia el precio total. Finalmente, cuando el usuario pulsa el botón para añadir a la cesta, este producto se muestra en el basket (el RecyclerView) de la tienda.
<img src="https://github.com/dhrodao/Android-Shop-App/blob/dev/docs/shop.png" alt="Demo vert" data-canonical-src="docs/screenshot.png" width="300"/>

   - Carrito de compras global: Esta vista muestra mediante un RecyclerView la lista total de artículos que el usuario ha añadido, además en la parte inferior muestra el precio total de la compra. EL usuario podrá realizar la compra mediante el botón de comprar situado en la parte inferior de la pantalla.
<img src="https://github.com/dhrodao/Android-Shop-App/blob/dev/docs/basket.png" alt="Demo vert" data-canonical-src="docs/screenshot.png" width="300"/>

   - Chat con administradores: Esta pantalla es similar a un chat de WhatsApp en donde se muestra la conversación que el usuario mantiene con servicio técnico. El usuario podrá enviar mensajes escribiendo en el cuadro de texto habilitado para ello.
<img src="https://github.com/dhrodao/Android-Shop-App/blob/dev/docs/chat.png" alt="Demo vert" data-canonical-src="docs/screenshot.png" width="300"/>

   - Ver ofertas recibidas: Esta pantalla se ha implementado mediante un RecyclerView de manera muy similar al chat, donde se muestran los mensajes de ofertas.
<img src="https://github.com/dhrodao/Android-Shop-App/blob/dev/docs/received.png" alt="Demo vert" data-canonical-src="docs/screenshot.png" width="300"/>
    
   - Ver mensajes enviados: Esta pantalla es similar a las dos anteriores, en ella se muestran los mensajes enviados por el usuario.
<img src="https://github.com/dhrodao/Android-Shop-App/blob/dev/docs/sent.png" alt="Demo vert" data-canonical-src="docs/screenshot.png" width="300"/>
     
   - Pedidos: Esta pantalla es similar a la de carrito de compras global, pero en esta pantalla se muestran los pedidos realizados por el usuario. Consiste en 
un _RecyclerView_ con una lista de pedidos, en el que para cada pedido se muestra su precio total.
<img src="https://github.com/dhrodao/Android-Shop-App/blob/dev/docs/pedidos.png" alt="Demo vert" data-canonical-src="docs/pedidos.png" width="300"/>
