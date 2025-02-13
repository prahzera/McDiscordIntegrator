# Minecraft Chat to Discord Webhook

Este es un plugin de Spigot 1.16.5 que envía todos los mensajes del chat de Minecraft a un webhook de Discord.

## Características
- Captura todos los mensajes del chat.
- Envía los mensajes formateados al webhook de Discord.
- Compatible con Spigot 1.16.5.

## Formato del mensaje
```plaintext
<usuario>: <mensaje>
```

## Instalación
1. Descarga el archivo JAR del plugin.
2. Coloca el archivo en la carpeta `plugins/` de tu servidor Spigot.
3. Reinicia el servidor.
4. Se generará un archivo `config.yml` en la carpeta del plugin.
5. Edita `config.yml` y agrega la URL de tu webhook de Discord.
6. Reinicia el servidor para aplicar los cambios.

## Configuración (`config.yml`)
```yaml
webhook-url: "https://discord.com/api/webhooks/tu_webhook"
```

## Uso
Una vez instalado y configurado, el plugin comenzará a enviar los mensajes del chat de Minecraft al webhook de Discord automáticamente.

## Descarga
Puedes descargar la última versión del plugin desde [GitHub Releases](https://github.com/tu-repo/minecraft-chat-webhook/releases).

## Versión
- **1.0** - Versión inicial con funcionalidad básica.

## Licencia
Este proyecto está bajo la licencia MIT.

