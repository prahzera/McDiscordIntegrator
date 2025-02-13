package com.ejemplo.chatwebhook;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private final String webhookUrl;

    public DeathListener(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller(); // Puede ser nulo si la muerte no fue por otro jugador
        Location loc = victim.getLocation();
        String locationStr = String.format("X: %d, Y: %d, Z: %d", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        
        String message;
        if (killer != null) {
            // Asignamos el nombre del killer a una variable local y comprobamos si es nulo
            String killerName = killer.getName();
            if (killerName == null) {
                killerName = "Desconocido";
            }
            message = String.format("**%s** fue eliminado por **%s** en [%s]", 
                victim.getName(), killerName, locationStr);
        } else {
            // event.getDeathMessage() podría ser nulo; damos un valor por defecto en ese caso
            String deathMsg = (event.getDeathMessage() != null) ? event.getDeathMessage() : "sin motivo";
            message = String.format("**%s** ha muerto (%s) en [%s]", 
                victim.getName(), escapeJson(deathMsg), locationStr);
        }
        sendDiscordWebhook(message);
    }

    private void sendDiscordWebhook(String content) {
        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("User-Agent", "Mozilla/5.0");
            
            String jsonPayload = String.format("{\"content\": \"%s\"}", escapeJson(content));
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
        } catch (IOException e) {
            // Puedes usar e.printStackTrace() o un logger para registrar el error
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
