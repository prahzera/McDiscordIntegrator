package com.ejemplo.discordintegrator;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.modals.ModalMapping;
import javax.annotation.Nonnull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ModalListener extends ListenerAdapter {

    @Override
    public void onModalInteraction(@Nonnull ModalInteractionEvent event) {
        try {
            if (event.getModalId().startsWith("kick_modal_")) {
                // Acknowledge the interaction immediately
                event.deferReply(true).queue();
                
                String playerName = event.getModalId().substring("kick_modal_".length());
                ModalMapping reasonMapping = event.getValue("kick_reason");
                String kickReason = reasonMapping != null ? reasonMapping.getAsString() : "No se proporcionó razón";
                
                Bukkit.getScheduler().runTask(DiscordIntegrator.getInstance(), () -> {
                    Player player = Bukkit.getPlayer(playerName);
                    if (player != null) {
                        player.kickPlayer("Has sido expulsado: " + kickReason);
                        
                        // Use hook instead of reply since we already deferred
                        event.getHook().editOriginal("Jugador " + playerName + " expulsado con razón: " + kickReason).queue();
                        
                        if (TrackCommand.isPlayerTracked(playerName)) {
                            TrackCommand.stopTracking(playerName);
                        }
                    } else {
                        event.getHook().editOriginal("El jugador " + playerName + " ya no está en línea.").queue();
                    }
                });
            }
        } catch (Exception e) {
            // Log the error and send a message to the user
            Bukkit.getLogger().severe("Error processing modal interaction: " + e.getMessage());
            e.printStackTrace();
            event.reply("Ocurrió un error al procesar esta interacción").setEphemeral(true).queue();
        }
    }
}