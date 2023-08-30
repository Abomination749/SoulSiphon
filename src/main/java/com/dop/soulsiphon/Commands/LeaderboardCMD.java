package com.dop.soulsiphon.Commands;

import com.dop.soulsiphon.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LeaderboardCMD implements CommandExecutor {

    private Main main;

    public LeaderboardCMD(Main main) {

        this.main = main;

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (main.config.getBoolean("LeaderboardEnabled")) {

            List<Map.Entry<UUID, Integer>> sortedEntries = new ArrayList<>(main.health.entrySet());
            sortedEntries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
            int count = 0;
            for (Map.Entry<UUID, Integer> entry : sortedEntries) {
                if (count >= main.config.getInt("LeaderboardSpots")) {
                    break; // Display only top 10
                }
                String playerName = Bukkit.getPlayer(entry.getKey()).getName();
                int score = entry.getValue();

                if (commandSender instanceof Player) {
                    commandSender.sendMessage(count + ") " + playerName + ": " + score);
                } else {
                    System.out.println(count + ") " + playerName + ": " + score);
                }

                count++;
            }
        }
        return false;
    }
}
