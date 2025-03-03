package com.ejemplo.discordintegrator;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class ButtonListener extends ListenerAdapter {
    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        String componentId = event.getComponentId();
        
        try {
            // Extract player name from the first field of the embed
            String playerName = event.getMessage().getEmbeds().get(0).getFields().get(0).getValue();
            Player player = Bukkit.getPlayer(playerName);
            
            if (componentId.equals("start_tracking")) {
                // Acknowledge the interaction by deferring the edit
                event.deferEdit().queue();
                
                if (player != null && player.isOnline()) {
                    // Start tracking the player
                    TrackCommand.startTracking(event.getMessage(), player);
                    
                    // Update the message with tracking status
                    MessageEmbed updatedEmbed = new TrackCommand().createPlayerEmbed(player, true);
                    Button stopButton = Button.danger("stop_tracking", "Detener Trackeo");
                    Button kickButton = Button.danger("kick_player", "Expulsar Jugador");
                    
                    event.getHook().editOriginalEmbeds(updatedEmbed)
                           .setActionRow(stopButton, kickButton)
                           .queue();
                } else {
                    event.getHook().sendMessage("El jugador ya no está en línea").setEphemeral(true).queue();
                }
            } 
            else if (componentId.equals("stop_tracking")) {
                // Acknowledge the interaction by deferring the edit
                event.deferEdit().queue();
                
                // Stop tracking the player
                TrackCommand.stopTracking(playerName);
                
                if (player != null && player.isOnline()) {
                    // Update the message with non-tracking status
                    MessageEmbed updatedEmbed = new TrackCommand().createPlayerEmbed(player, false);
                    Button startButton = Button.success("start_tracking", "Iniciar Trackeo");
                    Button kickButton = Button.danger("kick_player", "Expulsar Jugador");
                    
                    event.getHook().editOriginalEmbeds(updatedEmbed)
                           .setActionRow(startButton, kickButton)
                           .queue();
                } else {
                    event.getHook().sendMessage("El jugador ya no está en línea").setEphemeral(true).queue();
                }
            } 
            else if (componentId.equals("kick_player")) {
                if (player != null && player.isOnline()) {
                    // Create a modal for kick reason
                    TextInput reasonInput = TextInput.create("kick_reason", "Razón de expulsión", TextInputStyle.PARAGRAPH)
                            .setPlaceholder("Ingrese la razón por la que expulsa al jugador")
                            .setRequired(true)
                            .setMaxLength(200)
                            .build();
                    
                    Modal kickModal = Modal.create("kick_modal_" + playerName, "Expulsar a " + playerName)
                            .addActionRow(reasonInput)
                            .build();
                    
                    // Reply with the modal
                    event.replyModal(kickModal).queue();
                } else {
                    event.reply("El jugador ya no está en línea").setEphemeral(true).queue();
                }
            }
        } catch (Exception e) {
            // Log the error and send a message to the user
            Bukkit.getLogger().severe("Error processing button interaction: " + e.getMessage());
            e.printStackTrace();
            event.reply("Ocurrió un error al procesar esta interacción").setEphemeral(true).queue();
        }
    }
}