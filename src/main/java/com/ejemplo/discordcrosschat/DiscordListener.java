package com.ejemplo.discordcrosschat;// Agrega esta importación:

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

public class DiscordListener extends ListenerAdapter {
    private final DiscordCrossChatPlugin plugin;
    private final String chatChannelId; // Declarar variable

    public DiscordListener(DiscordCrossChatPlugin plugin) {
        this.plugin = plugin;
        this.chatChannelId = plugin.getConfig().getString("discord-chat-channel-id"); // Asignar valor
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            System.out.println("[DEBUG] Mensaje de bot ignorado");
            return;
        }

        String receivedChannelId = event.getChannel().getId();
        System.out.println("[DEBUG] ID del canal recibido: " + receivedChannelId);
        System.out.println("[DEBUG] ID del canal configurado: " + chatChannelId);

        if (!receivedChannelId.equals(chatChannelId)) {
            System.out.println("[DEBUG] Mensaje ignorado (canal incorrecto)");
            return;
        }

        String content = event.getMessage().getContentRaw();
        System.out.println("[DEBUG] Contenido RAW: '" + content + "'");
        System.out.println("[DEBUG] ¿Tiene adjuntos? " + !event.getMessage().getAttachments().isEmpty());

        final String finalContent;
        if (content.isEmpty() && !event.getMessage().getAttachments().isEmpty()) {
            finalContent = "(Archivo adjunto)";
        } else if (content.isEmpty()) {
            System.out.println("[DEBUG] Mensaje vacío ignorado");
            return;
        } else {
            finalContent = content;
        }

        // Envía el mensaje a Minecraft
        Bukkit.getScheduler().runTask(plugin, () -> {
            Bukkit.broadcastMessage("<" + event.getAuthor().getName() + "> " + finalContent);
            System.out.println("[DEBUG] Mensaje enviado a Minecraft: " + finalContent);
        });
    }
}
