package com.ejemplo.discordcrosschat;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.plugin.java.JavaPlugin;

public class DiscordCrossChatPlugin extends JavaPlugin {

    private JDA jda;
    private TextChannel chatChannel;
    private TextChannel eventsChannel;

    @Override
    public void onEnable() {
        // Guarda la configuración por defecto (config.yml)
        saveDefaultConfig();
        String token = getConfig().getString("discord-token");
        String chatChannelId = getConfig().getString("discord-chat-channel-id");
        String eventsChannelId = getConfig().getString("discord-events-channel-id");

        // Validación de la configuración
        if (token == null || token.trim().isEmpty() ||
                chatChannelId == null || chatChannelId.trim().isEmpty() ||
                eventsChannelId == null || eventsChannelId.trim().isEmpty()) {
            getLogger().severe("Token o IDs de canales de Discord no configurados en config.yml.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        try {
            // Inicializa JDA con el intent MESSAGE_CONTENT
            jda = JDABuilder.createDefault(token)
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT) // ¡Agrega esta línea!
                    .build();
            jda.awaitReady();
        } catch (Exception e) {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Obtiene los canales de Discord a partir de sus IDs
        chatChannel = jda.getTextChannelById(chatChannelId);
        eventsChannel = jda.getTextChannelById(eventsChannelId);

        if (chatChannel == null || eventsChannel == null) {
            getLogger().severe("No se han encontrado los canales de Discord especificados en config.yml.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Registra el listener de Discord para recibir mensajes
        jda.addEventListener(new DiscordListener(this));

        // Registra los listeners de eventos del juego
        getServer().getPluginManager().registerEvents(new GameChatListener(chatChannel), this);
        getServer().getPluginManager().registerEvents(new DeathListener(eventsChannel), this);
        getServer().getPluginManager().registerEvents(new AchievementListener(eventsChannel), this);

        getLogger().info("DiscordCrossChatPlugin habilitado correctamente.");
    }

    public TextChannel getChatChannel() {
        return chatChannel;
    }

    public JDA getJDA() {
        return jda;
    }

    @Override
    public void onDisable() {
        if (jda != null) {
            jda.shutdown();
        }
        getLogger().info("DiscordCrossChatPlugin deshabilitado.");
    }
}
