package com.ejemplo.chatwebhook;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ChatListener implements Listener {

    private final String webhookUrl;

    public ChatListener(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        // Captura el nombre del jugador y el mensaje
        String playerName = event.getPlayer().getName();
        String message = event.getMessage();

        // Crea el contenido JSON para Discord. El webhook espera un JSON con al menos
        // la clave "content"
        // Se utiliza markdown para resaltar el nombre del jugador (opcional)
        String jsonPayload = String.format("{\"content\": \"**%s**: %s\"}", escapeJson(playerName),
                escapeJson(message));

        // Envia el webhook en un hilo aparte para no bloquear el hilo principal.
        // Aunque AsyncPlayerChatEvent ya se ejecuta de forma asíncrona, es buena
        // práctica aislar operaciones de E/S.
        new Thread(() -> sendDiscordWebhook(jsonPayload)).start();
    }

    /**
     * Envía una solicitud HTTP POST al webhook de Discord con el payload JSON.
     *
     * @param jsonPayload El contenido JSON a enviar.
     */
    private void sendDiscordWebhook(String jsonPayload) {
        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("User-Agent", "Mozilla/5.0"); // Añade este encabezado

            byte[] payloadBytes = jsonPayload.getBytes(StandardCharsets.UTF_8);
            connection.setFixedLengthStreamingMode(payloadBytes.length);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(payloadBytes);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != 204 && responseCode != 200) {
                System.err.println("Error al enviar webhook de Discord, código de respuesta: " + responseCode);
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Escapa caracteres especiales para que el JSON sea válido.
     *
     * @param text El texto a escapar.
     * @return El texto escapado.
     */
    private String escapeJson(String text) {
        if (text == null)
            return "";
        return text.replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}
