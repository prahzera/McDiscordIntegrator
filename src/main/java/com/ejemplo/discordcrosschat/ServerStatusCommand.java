package com.ejemplo.discordcrosschat;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;

import javax.annotation.Nonnull;
import java.awt.Color;
import java.util.concurrent.TimeUnit;

public class ServerStatusCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("status"))
            return;

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(":globe_with_meridians: Estado del Servidor")
                .setColor(Color.BLUE)
                .addField("Jugadores Online",
                        Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers(),
                        true);

        Bukkit.getOnlinePlayers().forEach(player -> {
            String timePlayed = formatTime(player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 60);
            String location = String.format(
                    "%s [%d, %d, %d]",
                    player.getWorld().getName(),
                    player.getLocation().getBlockX(),
                    player.getLocation().getBlockY(),
                    player.getLocation().getBlockZ());

            embed.addField(
                    ":bust_in_silhouette: " + player.getName(),
                    "```yaml\n" +
                            "Tiempo jugado: " + timePlayed + "\n" +
                            "Ubicación: " + location + "```",
                    false);
        });

        event.replyEmbeds(embed.build()).queue();
    }

    private String formatTime(int minutes) {
        long hours = TimeUnit.MINUTES.toHours(minutes);
        long days = TimeUnit.HOURS.toDays(hours);
        return String.format("%dd %02dh %02dm", days, hours % 24, minutes % 60);
    }
}
