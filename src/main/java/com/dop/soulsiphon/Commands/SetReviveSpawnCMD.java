package com.dop.soulsiphon.Commands;

import com.dop.soulsiphon.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetReviveSpawnCMD implements CommandExecutor {

    private final Main main;
    public SetReviveSpawnCMD(Main main) {

        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {

            Player player = (Player) commandSender;
            if (player.hasPermission("soulsiphon.setrespawnpoint")) {
                Location location = player.getLocation();

                main.getConfig().set("ReviveSpawn.x", location.getX());
                main.getConfig().set("ReviveSpawn.y", location.getY());
                main.getConfig().set("ReviveSpawn.z", location.getZ());
                main.getConfig().set("ReviveSpawn.world", location.getWorld().getName());
                main.saveConfig();
                main.reloadConfig();
                player.sendMessage(main.prefix + " Spawn set to " + location.getX() + " " + location.getY() + " " + location.getZ() + " in world " + location.getWorld().getName());
            }
        } else {

            System.out.println("Command cannot be run from console!");

        }


        return false;
    }
}

