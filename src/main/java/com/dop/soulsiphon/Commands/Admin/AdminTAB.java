package com.dop.soulsiphon.Commands.Admin;

import com.dop.soulsiphon.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminTAB implements TabCompleter {


    private Main main;

    public AdminTAB(Main main) {

        this.main = main;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        List<String> results = new ArrayList<>();
        if (commandSender instanceof Player) {
            if (args.length == 1) {
                return StringUtil.copyPartialMatches(args[0], Arrays.asList("SaveHL", "heartrecipegui", "beaconrecipegui", "withdraw", "setrevivespawn", "resetlives", "config", "reload", "give", "help"), new ArrayList<>());
            } else if (args.length == 2) {

                if (args[0].equalsIgnoreCase("withdraw")) {
                    Player player = (Player) commandSender;
                    int heartstowithdraw = (main.health.get(player.getUniqueId()) / 2) - 1;
                    for (int i = 1; i <= heartstowithdraw; i++) {

                        results.add(String.valueOf(i));
                        return results;

                    }

                } else if (args[0].equalsIgnoreCase("resetlives")) {
                    List<String> names = new ArrayList<>();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        names.add(player.getName());
                    }
                    names.add("all");

                    return StringUtil.copyPartialMatches(args[0], names, new ArrayList<>());

                } else if (args[0].equalsIgnoreCase("config")) {
                    return StringUtil.copyPartialMatches(args[1], Arrays.asList("EnderchestOnLoss", "FullResetCommand", "DeathMessageEnabled", "StartingHeartsModifier", "OtherDeathsCount", "HeartsLostOnDeath", "HeartsEnabled", "HeartRecipeEnabled", "HeartsDropOnDeath", "OtherDeathsDrop", "CraftBeaconWithHearts", "DeathOutcome", "CanWithdraw", "HeartsDropIfFull"), new ArrayList<>());
                } else if (args[0].equalsIgnoreCase("give")) {

                    results.add("<number>");
                    results.add("<username> <number>");
                    return results;

                }
            } else if (args.length == 3) {

                if (args[0].equalsIgnoreCase("config")) {

                    if (args[1].equalsIgnoreCase("otherdeathscount") || args[1].equalsIgnoreCase("heartsenabled") || args[1].equalsIgnoreCase("HeartRecipeEnabled") || args[1].equalsIgnoreCase("HeartsDropOnDeath") || args[1].equalsIgnoreCase("OtherDeathsDrop") || args[1].equalsIgnoreCase("CraftBeaconWithHearts") || args[1].equalsIgnoreCase("CanWithdraw") || args[1].equalsIgnoreCase("HeartsDropIfFull") || args[1].equalsIgnoreCase("DeathMessageEnabled") || args[1].equalsIgnoreCase("FullResetCommand") || args[1].equalsIgnoreCase("EnderChestOnLoss")) {

                        return StringUtil.copyPartialMatches(args[2], Arrays.asList("true", "false"), new ArrayList<>());

                    } else if (args[1].equalsIgnoreCase("StartingHeartsModifier") || args[1].equalsIgnoreCase("HeartsLostOnDeath")) {

                        return StringUtil.copyPartialMatches(args[2], Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"), new ArrayList<>());

                    }

                } else if (args[0].equalsIgnoreCase("give") && !(isInteger(args[1]))) {
                    results.add("<number>");
                    return results;

                }

            }
        }


        return new ArrayList<>();
    }
}
