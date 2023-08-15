package com.dop.soulsiphon.Commands.Withdraw;

import com.dop.soulsiphon.Main;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class WithdrawCMD implements CommandExecutor {
    private final Main main;
    public WithdrawCMD(Main main) {
        this.main = main;
        prefix = main.prefix;
    }
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String prefix;

    int x = 1;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player) {

            Player player = (Player) commandSender;
            if (player.hasPermission("soulsiphon.withdraw")) {
                if (main.getConfig().getBoolean("HeartsAsHeads")) {
                }
                Map<UUID, Integer> health = main.health;


                if (args.length == 0) {

                    x = 1;

                } else if (args.length == 1 && isInteger(args[0]) && player.hasPermission("soulsiphon.withdraw.multiple")) {

                    x = Integer.parseInt(args[0]);

                } else {

                    x = 1;

                }


                if (main.config.getBoolean("HeartsEnabled") && main.config.getBoolean("CanWithdraw")) {

                    if (health.get(player.getUniqueId()) > 2 * x) {
                        if (player.getInventory().firstEmpty() != -1) {
                            health.put(player.getUniqueId(), health.get(player.getUniqueId()) - 2 * x);
                            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health.get(player.getUniqueId()));

                            for (int i = 0; i < x; i++) {



                                player.getInventory().addItem(main.heart);

                            }

                        } else if (main.config.getBoolean("HeartDropsIfFull")) {
                            health.put(player.getUniqueId(), health.get(player.getUniqueId()) - 2 * x);
                            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health.get(player.getUniqueId()));
                            for (int i = 0; i < x; i++) {

                                player.getWorld().dropItem(player.getLocation(), main.heart);

                            }
                            player.sendMessage(prefix + " Your inventory was full so the heart fell to the ground!");
                        } else {
                            player.sendMessage(prefix + " Please have an inventory slot open before using /withdraw!");
                        }
                    } else {
                        player.sendMessage(prefix + " Your hearts are too low to withdraw!");
                    }


                } else {

                    player.sendMessage(prefix + " This command is not enabled!");

                }
            }
        }

        return false;

    }
}
