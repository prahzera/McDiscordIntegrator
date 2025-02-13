# Discord Integrator 1.0.2

Este es un plugin de Bukkit 1.21.4 que envía todos los mensajes del chat y eventos de muerte a un webhook de Discord.

## Características
- Captura todos los mensajes del chat.
- Envía los mensajes formateados al webhook de Discord.
- Captura muertes de jugadores y envía las coordenadas donde ocurrieron.
- Compatible con Bukkit 1.21.4.

## Formato del mensaje
```plaintext
<usuario>: <mensaje>
```

## Formato de las muertes
```plaintext
<usuario> ha muerto en (X, Y, Z)
```

## Instalación
1. Descarga el archivo JAR del plugin.
2. Coloca el archivo en la carpeta `plugins/` de tu servidor Bukkit.
3. Reinicia el servidor.
4. Se generará un archivo `config.yml` en la carpeta del plugin.
5. Edita `config.yml` y agrega la URL de tu webhook de Discord.
6. Reinicia el servidor para aplicar los cambios.

## Configuración (`config.yml`)
```yaml
webhook-url: "https://discord.com/api/webhooks/tu_webhook"
```

## Uso
Una vez instalado y configurado, el plugin comenzará a enviar los mensajes del chat y eventos de muerte al webhook de Discord automáticamente.

## Descarga
Puedes descargar la última versión del plugin desde [GitHub Releases](https://github.com/prahzera/McDiscordIntegrator/releases).

## Versión
- **1.0.2** - Soporte para eventos de muerte con coordenadas.
- **1.0.0** - Soporte para mensajes a discord via webhooks.

## Licencia
Este proyecto está bajo la licencia MIT.

