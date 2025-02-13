package com.ejemplo.chatwebhook;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ChatListener implements Listener {

    private final String webhookUrl;

    public ChatListener(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String playerName = event.getPlayer().getName();
        String message = event.getMessage();
        // Se formatea el mensaje usando markdown para resaltar el nombre
        String jsonPayload = String.format("{\"content\": \"**%s**: %s\"}", escapeJson(playerName), escapeJson(message));
        new Thread(() -> sendDiscordWebhook(jsonPayload)).start();
    }

    private void sendDiscordWebhook(String jsonPayload) {
        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("User-Agent", "Mozilla/5.0"); // Importante para Discord
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

    private String escapeJson(String text) {
        if (text == null) return "";
        return text.replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r");
    }
}
