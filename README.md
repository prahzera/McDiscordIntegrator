# Discord Integrator 1.2.2

Este es un plugin de Bukkit 1.21.4 que integra un bot de Discord para enviar y recibir mensajes entre Minecraft y Discord, además de incluir comandos para rastrear jugadores y consultar el estado del servidor.

## Características
- **Chat cruzado (Cross Chat):** Los mensajes de Minecraft se envían a Discord y viceversa. Los mensajes de Minecraft tienen el formato `[Mundo] Usuario: Mensaje` y los mensajes de Discord aparecen como `[Discord] Usuario: Mensaje`.
  - El mundo de Minecraft se indica con un color específico:
    - **Overworld:** Verde
    - **Nether:** Rojo
    - **End:** Morado
    - **Discord:** Azul
- **Tracking de Jugadores:** El comando `/track <usuarioDeMinecraft>` permite rastrear la ubicación, salud, hambre y nivel de un jugador en tiempo real. El comando devuelve un **embed** con los datos del jugador y botones para iniciar o detener el seguimiento.
  - **Inventario y Armadura:** Muestra el contenido del inventario y la armadura equipada del jugador con emojis personalizados de Discord.
  - **Efectos de Poción:** Lista todos los efectos activos con su duración y nivel.
  - **Información Detallada:** Incluye UUID, dirección cardinal, ping, modo de juego, IP, tiempo jugado y fecha de primera conexión.
- **Estado del Servidor:** El comando `/status` proporciona un resumen del estado del servidor, incluyendo la cantidad de jugadores en línea, su tiempo jugado y ubicación actual.
- **Bot de Discord:** El plugin utiliza un bot de Discord con soporte para emojis personalizados y botones interactivos.
- **Eventos del Servidor:** Notifica en Discord sobre muertes, logros y otros eventos importantes con coordenadas y detalles específicos.

## Formato del Chat

Los mensajes que provienen de Minecraft tienen el siguiente formato:
```plaintext
[Mundo] <usuario>: <mensaje>
```
Ejemplo: **[Overworld] prahzera: holis** 
Los mensajes enviados desde Discord se muestran así en Minecraft:


```plaintext
[Discord] <usuario>: <mensaje>
```
Comando `/track <usuarioDeMinecraft>`El comando `/track <usuarioDeMinecraft>` permite rastrear el estado del jugador en tiempo real. Los datos mostrados en el embed incluyen: 
- **Jugador:**  Nombre del jugador.
 
- **Coordenadas:**  Ubicación actual en el mundo.
 
- **Vida:**  Salud del jugador.
 
- **Hambre:**  Nivel de hambre del jugador.
 
- **Nivel:**  Nivel de experiencia del jugador.

El embed también incluye tres botones:
 
- **Detener Trackeo:**  Para dejar de seguir al jugador.
 
- **Reiniciar Trackeo:**  Para reiniciar el seguimiento de las estadísticas del jugador.
 
- **Expulsar Jugador:** Para expulsar al jugador del servidor.
Comando `/status`El comando `/status` devuelve información sobre el estado del servidor: 
- **Jugadores Online:**  Muestra la cantidad de jugadores en línea y el máximo de jugadores permitidos.
 
- **Jugadores Detallados:**  Muestra el tiempo jugado y la ubicación de cada jugador en el servidor.

## Requisitos

- Servidor Bukkit/Spigot 1.21.4
- Java 17 o superior
- Bot de Discord con los siguientes permisos:
  - `MESSAGE_CONTENT` - Para leer mensajes
  - `SEND_MESSAGES` - Para enviar mensajes
  - `USE_EXTERNAL_EMOJIS` - Para usar emojis personalizados
  - `EMBED_LINKS` - Para enviar embeds
  - `ATTACH_FILES` - Para adjuntar archivos

## Instalación 

1. Descarga el archivo JAR del plugin.
 
2. Coloca el archivo en la carpeta `plugins/` de tu servidor Bukkit.

3. Reinicia el servidor.
 
4. Se generará un archivo `config.yml` en la carpeta del plugin.
 
5. Edita `config.yml` y agrega los valores necesarios para el bot de Discord (Token y IDs de los canales).

6. Reinicia el servidor para aplicar los cambios.

7. Opcional: Personaliza los emojis del servidor de Discord para mejorar la visualización de items en los embeds de tracking.
Configuración (`config.yml`)

```yaml
# Configuración para discordintegrator

# Token del bot de Discord
discord-token: "TU_TOKEN_DEL_BOT"

# ID del canal de Discord para el chat cruzado (mensajes generales)
discord-chat-channel-id: "ID_DEL_CANAL_CHAT"

# ID del canal de Discord para eventos (muertes, asesinatos, logros)
discord-events-channel-id: "ID_DEL_CANAL_EVENTOS"
```

## Comandos de Discord 
`/track <usuarioDeMinecraft>`
Este comando permite rastrear a un jugador de Minecraft y ver sus estadísticas en Discord.
`/status`
Este comando muestra el estado general del servidor, incluyendo la cantidad de jugadores en línea y su tiempo jugado.

## Descarga 
Puedes descargar la última versión del plugin desde [GitHub Releases](https://github.com/prahzera/McDiscordIntegrator/releases) .
## Versión 
 
- **1.2.2**  
    - Añadido botón de expulsión de jugadores en el comando `/track`.  
    - Añadido uso de custom emojis de Discord en lugar de emojis genéricos. 

- **1.1.7**  
    - Integración de bot de Discord, chat cruzado, comandos `/track` y `/status`.  

- **1.0.2**  
    - Soporte para eventos de muerte con coordenadas.  

- **1.0.0**  
    - Soporte para mensajes a Discord vía webhooks.  


## Licencia 

Este proyecto está bajo la licencia MIT.
