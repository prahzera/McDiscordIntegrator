package com.ejemplo.chatwebhook;

import org.bukkit.plugin.java.JavaPlugin;

public class ChatWebhookPlugin extends JavaPlugin {

    // Variable para almacenar la URL del webhook de Discord (se leerá desde
    // config.yml)
    private String discordWebhookUrl;

    @Override
    public void onEnable() {
        // Guarda el archivo de configuración por defecto si no existe
        saveDefaultConfig();
        // Lee la URL del webhook desde el config.yml
        discordWebhookUrl = getConfig().getString("discord-webhook-url");

        // Verificar que la URL no esté vacía
        if (discordWebhookUrl == null || discordWebhookUrl.trim().isEmpty()) {
            getLogger().severe("No se ha configurado la URL del webhook de Discord en config.yml");
            // Opcional: deshabilitar el plugin si la URL no está configurada
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Registra el listener para el chat
        getServer().getPluginManager().registerEvents(new ChatListener(discordWebhookUrl), this);
        getLogger().info("ChatWebhookPlugin habilitado correctamente.");
    }

    @Override
    public void onDisable() {
        getLogger().info("ChatWebhookPlugin deshabilitado.");
    }
}
