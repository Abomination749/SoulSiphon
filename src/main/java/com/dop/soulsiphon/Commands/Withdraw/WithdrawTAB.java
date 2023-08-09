package com.dop.soulsiphon.Commands.Withdraw;

import com.dop.soulsiphon.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WithdrawTAB implements TabCompleter {

    private final Main main;

    public WithdrawTAB(Main main) {

        this.main = main;

    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        List<String> results = new ArrayList<>();

        if (strings.length == 1) {

            if (commandSender instanceof Player) {

                Player player = (Player) commandSender;
                int heartstowithdraw = (main.health.get(player.getUniqueId()) / 2 ) - 1;
                for (int i = 1; i <= heartstowithdraw; i++) {

                    results.add(String.valueOf(i));

                }

            }
        }

        return results;
    }
}
