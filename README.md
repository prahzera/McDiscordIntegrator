# Discord Integrator 1.2.2

[English Version](#english-version)

## Índice
1. [Descripción General](#descripción-general)
2. [Características](#características)
3. [Requisitos](#requisitos)
4. [Instalación](#instalación)
5. [Configuración](#configuración)
6. [Comandos](#comandos)
7. [Guía para Desarrolladores](#guía-para-desarrolladores)
   - [Estructura del Proyecto](#estructura-del-proyecto)
   - [Configuración del Entorno](#configuración-del-entorno)
   - [Compilación](#compilación)
   - [Desarrollo](#desarrollo)
   - [Contribución](#contribución)
8. [Historial de Versiones](#historial-de-versiones)
9. [Licencia](#licencia)

## Descripción General
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
1. Descarga el archivo JAR del plugin desde [GitHub Releases](https://github.com/prahzera/McDiscordIntegrator/releases).
2. Coloca el archivo en la carpeta `plugins/` de tu servidor Bukkit.
3. Reinicia el servidor.
4. Se generará un archivo `config.yml` en la carpeta del plugin.
5. Edita `config.yml` y agrega los valores necesarios para el bot de Discord (Token y IDs de los canales).
6. Reinicia el servidor para aplicar los cambios.
7. Opcional: Personaliza los emojis del servidor de Discord para mejorar la visualización de items en los embeds de tracking.

## Configuración
Edita el archivo `config.yml` generado en la carpeta del plugin:

```yaml
# Configuración para discordintegrator

# Token del bot de Discord
discord-token: "TU_TOKEN_DEL_BOT"

# ID del canal de Discord para el chat cruzado (mensajes generales)
discord-chat-channel-id: "ID_DEL_CANAL_CHAT"

# ID del canal de Discord para eventos (muertes, asesinatos, logros)
discord-events-channel-id: "ID_DEL_CANAL_EVENTOS"
```

## Comandos
### Discord
- `/track <usuarioDeMinecraft>`: Rastrea a un jugador y muestra sus estadísticas en tiempo real.
- `/status`: Muestra el estado general del servidor.

## Guía para Desarrolladores

### Estructura del Proyecto
El proyecto sigue una estructura Maven estándar:
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── ejemplo/
│   │           └── discordintegrator/
│   │               ├── DiscordIntegrator.java    # Clase principal
│   │               ├── DiscordListener.java      # Manejo de eventos de Discord
│   │               └── MinecraftListener.java    # Manejo de eventos de Minecraft
│   └── resources/
│       ├── config.yml    # Configuración por defecto
│       └── plugin.yml    # Metadatos del plugin
```

### Configuración del Entorno
1. Requisitos previos:
   - JDK 17 o superior
   - Maven 3.6 o superior
   - IDE (recomendado: IntelliJ IDEA o Eclipse)

2. Clonar el repositorio:
   ```bash
   git clone https://github.com/prahzera/McDiscordIntegrator.git
   cd McDiscordIntegrator
   ```

3. Importar el proyecto:
   - En IntelliJ IDEA: File > Open > Seleccionar la carpeta del proyecto
   - En Eclipse: File > Import > Existing Maven Projects

### Compilación
1. Compilar el plugin:
   ```bash
   mvn clean package
   ```
   El archivo JAR se generará en `target/DiscordIntegrator-1.2.2.jar`

2. Para desarrollo local:
   ```bash
   mvn clean install
   ```

### Desarrollo
- **DiscordIntegrator.java**: Clase principal que inicializa el plugin y el bot de Discord.
- **DiscordListener.java**: Maneja los eventos de Discord (mensajes, comandos).
- **MinecraftListener.java**: Maneja los eventos de Minecraft (chat, logros, muertes).

Principales funcionalidades:
1. **Chat Cruzado**: Implementado en `DiscordListener.java` y `MinecraftListener.java`
2. **Tracking**: Sistema de seguimiento en tiempo real usando eventos de Bukkit
3. **Comandos**: Implementados usando el sistema de comandos de JDA

### Contribución
1. Haz un fork del repositorio
2. Crea una rama para tu característica (`git checkout -b feature/AmazingFeature`)
3. Realiza tus cambios y haz commit (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Historial de Versiones
- **1.2.2**
  - Añadido botón de expulsión de jugadores en el comando `/track`
  - Añadido uso de custom emojis de Discord en lugar de emojis genéricos

- **1.1.7**
  - Integración de bot de Discord, chat cruzado, comandos `/track` y `/status`

- **1.0.2**
  - Soporte para eventos de muerte con coordenadas

- **1.0.0**
  - Soporte para mensajes a Discord vía webhooks

## Licencia
Este proyecto está bajo la licencia MIT.

---

# English Version

## Index
1. [Overview](#overview)
2. [Features](#features)
3. [Requirements](#requirements)
4. [Installation](#installation)
5. [Configuration](#configuration)
6. [Commands](#commands)
7. [Developer Guide](#developer-guide)
   - [Project Structure](#project-structure)
   - [Environment Setup](#environment-setup)
   - [Building](#building)
   - [Development](#development)
   - [Contributing](#contributing)
8. [Version History](#version-history)
9. [License](#license-1)

## Overview
This is a Bukkit 1.21.4 plugin that integrates a Discord bot to send and receive messages between Minecraft and Discord, along with commands to track players and check server status.

## Features
- **Cross Chat:** Messages from Minecraft are sent to Discord and vice versa. Minecraft messages have the format `[World] User: Message` and Discord messages appear as `[Discord] User: Message`.
  - The Minecraft world is indicated with a specific color:
    - **Overworld:** Green
    - **Nether:** Red
    - **End:** Purple
    - **Discord:** Blue
- **Player Tracking:** The `/track <minecraftUser>` command allows tracking a player's location, health, hunger, and level in real-time. The command returns an **embed** with player data and buttons to start or stop tracking.
  - **Inventory and Armor:** Shows the player's inventory contents and equipped armor with custom Discord emojis.
  - **Potion Effects:** Lists all active effects with their duration and level.
  - **Detailed Information:** Includes UUID, cardinal direction, ping, game mode, IP, playtime, and first connection date.
- **Server Status:** The `/status` command provides a summary of the server status, including the number of online players, their playtime, and current location.
- **Discord Bot:** The plugin uses a Discord bot with support for custom emojis and interactive buttons.
- **Server Events:** Notifies Discord about deaths, achievements, and other important events with coordinates and specific details.

## Requirements
- Bukkit/Spigot Server 1.21.4
- Java 17 or higher
- Discord bot with the following permissions:
  - `MESSAGE_CONTENT` - To read messages
  - `SEND_MESSAGES` - To send messages
  - `USE_EXTERNAL_EMOJIS` - To use custom emojis
  - `EMBED_LINKS` - To send embeds
  - `ATTACH_FILES` - To attach files

## Installation
1. Download the plugin JAR file from [GitHub Releases](https://github.com/prahzera/McDiscordIntegrator/releases).
2. Place the file in the `plugins/` folder of your Bukkit server.
3. Restart the server.
4. A `config.yml` file will be generated in the plugin folder.
5. Edit `config.yml` and add the necessary values for the Discord bot (Token and Channel IDs).
6. Restart the server to apply the changes.
7. Optional: Customize Discord server emojis to improve item visualization in tracking embeds.

## Configuration
Edit the `config.yml` file generated in the plugin folder:

```yaml
# Configuration for discordintegrator

# Discord bot token
discord-token: "YOUR_BOT_TOKEN"

# Discord channel ID for cross chat (general messages)
discord-chat-channel-id: "CHAT_CHANNEL_ID"

# Discord channel ID for events (deaths, kills, achievements)
discord-events-channel-id: "EVENTS_CHANNEL_ID"
```

## Commands
### Discord
- `/track <minecraftUser>`: Track a player and display their stats in real-time.
- `/status`: Show general server status.

## Developer Guide

### Project Structure
The project follows a standard Maven structure:
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── ejemplo/
│   │           └── discordintegrator/
│   │               ├── DiscordIntegrator.java    # Main class
│   │               ├── DiscordListener.java      # Discord event handling
│   │               └── MinecraftListener.java    # Minecraft event handling
│   └── resources/
│       ├── config.yml    # Default configuration
│       └── plugin.yml    # Plugin metadata
```

### Environment Setup
1. Prerequisites:
   - JDK 17 or higher
   - Maven 3.6 or higher
   - IDE (recommended: IntelliJ IDEA or Eclipse)

2. Clone the repository:
   ```bash
   git clone https://github.com/prahzera/McDiscordIntegrator.git
   cd McDiscordIntegrator
   ```

3. Import the project:
   - In IntelliJ IDEA: File > Open > Select project folder
   - In Eclipse: File > Import > Existing Maven Projects

### Building
1. Build the plugin:
   ```bash
   mvn clean package
   ```
   The JAR file will be generated in `target/DiscordIntegrator-1.2.2.jar`

2. For local development:
   ```bash
   mvn clean install
   ```

### Development
- **DiscordIntegrator.java**: Main class that initializes the plugin and Discord bot.
- **DiscordListener.java**: Handles Discord events (messages, commands).
- **MinecraftListener.java**: Handles Minecraft events (chat, achievements, deaths).

Main functionalities:
1. **Cross Chat**: Implemented in `DiscordListener.java` and `MinecraftListener.java`
2. **Tracking**: Real-time tracking system using Bukkit events
3. **Commands**: Implemented using JDA's command system

### Contributing
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Version History
- **1.2.2**
  - Added player kick button in `/track` command
  - Added Discord custom emojis instead of generic emojis

- **1.1.7**
  - Discord bot integration, cross chat, `/track` and `/status` commands

- **1.0.2**
  - Support for death events with coordinates

- **1.0.0**
  - Support for Discord messages via webhooks

## License
This project is under the MIT license.
