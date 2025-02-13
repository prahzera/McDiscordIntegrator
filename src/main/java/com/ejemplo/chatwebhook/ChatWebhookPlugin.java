package com.ejemplo.chatwebhook;

import org.bukkit.plugin.java.JavaPlugin;

public class ChatWebhookPlugin extends JavaPlugin {

    private String discordWebhookUrl;

    @Override
    public void onEnable() {
        // Guarda el config por defecto (config.yml)
        saveDefaultConfig();
        discordWebhookUrl = getConfig().getString("discord-webhook-url");

        if (discordWebhookUrl == null || discordWebhookUrl.trim().isEmpty()) {
            getLogger().severe("No se ha configurado la URL del webhook de Discord en config.yml");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        // Registra el listener del chat
        getServer().getPluginManager().registerEvents(new ChatListener(discordWebhookUrl), this);
        // Registra el listener para muertes (kills)
        getServer().getPluginManager().registerEvents(new DeathListener(discordWebhookUrl), this);

        getLogger().info("ChatWebhookPlugin habilitado correctamente para Bukkit 1.20.4");
    }

    @Override
    public void onDisable() {
        getLogger().info("ChatWebhookPlugin deshabilitado.");
    }
}
