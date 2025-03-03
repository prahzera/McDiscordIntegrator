package com.ejemplo.discordintegrator;

import javax.annotation.Nonnull;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class DiscordListener extends ListenerAdapter {
    private final DiscordIntegrator plugin;
    private final String chatChannelId;

    public DiscordListener(DiscordIntegrator plugin) {
        this.plugin = plugin;
        this.chatChannelId = plugin.getConfig().getString("discord-chat-channel-id");
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String receivedChannelId = event.getChannel().getId();
        if (!receivedChannelId.equals(chatChannelId)) return;

        String content = event.getMessage().getContentRaw();
        boolean hasAttachments = !event.getMessage().getAttachments().isEmpty();
        boolean hasEmbeds = !event.getMessage().getEmbeds().isEmpty();

        final String finalContent;
        if (content.isEmpty() && (hasAttachments || hasEmbeds)) {
            finalContent = "(Contenido no textual)";
        } else if (content.isEmpty()) {
            System.out.println("[DEBUG] Mensaje vacío ignorado");
            return;
        } else {
            finalContent = content;
        }

        String minecraftMessage = ChatColor.BLUE + "[Discord] " 
                                + ChatColor.WHITE + event.getAuthor().getName() 
                                + ": " + finalContent;

        Bukkit.getScheduler().runTask(plugin, () -> {
            Bukkit.broadcastMessage(minecraftMessage);
        });
    }
}