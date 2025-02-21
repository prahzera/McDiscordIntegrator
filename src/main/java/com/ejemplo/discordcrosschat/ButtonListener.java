package com.ejemplo.discordcrosschat;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.MessageEmbed;
import javax.annotation.Nonnull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ButtonListener extends ListenerAdapter {

    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        if (event.getComponentId().equals("stop_tracking")) {
            // Lógica para detener el seguimiento
            String playerName = event.getMessage().getEmbeds().get(0).getFields().get(0).getValue(); // Obtén el nombre
                                                                                                     // del jugador del
                                                                                                     // embed
            Player player = Bukkit.getPlayer(playerName);
            if (player != null) {
                event.reply("Trackeo detenido para " + playerName).setEphemeral(true).queue();
            } else {
                event.reply("El jugador ya no está en línea.").setEphemeral(true).queue();
            }
        } else if (event.getComponentId().equals("refresh_tracking")) {
            // Lógica para actualizar la información
            String playerName = event.getMessage().getEmbeds().get(0).getFields().get(0).getValue(); // Obtén el nombre
                                                                                                     // del jugador del
                                                                                                     // embed
            Player player = Bukkit.getPlayer(playerName);
            if (player != null) {
                MessageEmbed updatedEmbed = new TrackCommand().createPlayerEmbed(player);
                event.editMessageEmbeds(updatedEmbed).queue();
            } else {
                event.reply("El jugador ya no está en línea.").setEphemeral(true).queue();
            }
        }
    }
}