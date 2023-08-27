package com.dop.soulsiphon.Commands;

import com.dop.soulsiphon.Main;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.Console;
import java.io.File;
import java.io.IOException;

public class ResetLivesCMD implements CommandExecutor {

    private Main main;

    public ResetLivesCMD(Main main) {

        this.main = main;

        prefix = main.prefix;

    }

    private String prefix;
    public Location spawn;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        spawn = new Location((World) Bukkit.getWorld(main.config.get("ReviveSpawn.world").toString()), (Double) main.config.getDouble("ReviveSpawn.x"), (Double) main.config.getDouble("ReviveSpawn.y"), (Double) main.config.getDouble("ReviveSpawn.z"));
        if (commandSender.hasPermission("soulsteal.resetlives") || !(commandSender instanceof Player))
            if (args.length == 1) {
                if (Bukkit.getPlayer(args[0]) != null && !args[0].equals("all")) {
                    if (args[0].equals("all") || main.config.getStringList("PlayerBanList").contains(Bukkit.getPlayer(args[0]).getUniqueId().toString()) || main.config.getStringList("PlayerBanList").contains(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString())) {

                        if (commandSender instanceof Player) {
                            if (commandSender.hasPermission("soulsiphon.resetlives")) {
                                Player p = Bukkit.getPlayer(args[0]);
                                if (p != null && !args[0].equals("all")) {


                                    main.health.put(p.getUniqueId(), main.startingmaxhealth);
                                    commandSender.sendMessage(prefix + " Reset player's hearts!");
                                    main.config.getStringList("PlayerBanList").remove(p.getUniqueId().toString());
                                    p.setGameMode(GameMode.SURVIVAL);
                                    p.teleport(spawn);
                                    p.sendMessage(prefix + " Your lives have been reset!");
                                                                        try {
                                        main.config.save(new File(main.getDataFolder(), "configuration.yml"));
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }


                                } else if (!args[0].equals("all")) {

                                    commandSender.sendMessage(prefix + " Player returned null!");


                                }

                            }
                        } else {
                            Player p = Bukkit.getPlayer(args[0]);
                            if (p != null && !args[0].equals("all")) {

                                main.health.put(Bukkit.getPlayer(args[0]).getUniqueId(), main.startingmaxhealth);
                                commandSender.sendMessage(prefix + " Reset player's hearts!");
                                main.config.getStringList("PlayerBanList").remove(p.getUniqueId().toString());
                                p.setGameMode(GameMode.SURVIVAL);
                                p.teleport(spawn);
                                                                    try {
                                        main.config.save(new File(main.getDataFolder(), "configuration.yml"));
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                            } else if (!args[0].equals("all")) {

                                commandSender.sendMessage(prefix + " Player returned null!");


                            }
                        }
                    } else {

                        System.out.println(prefix + " This player is not in the banlist! Reseting lives...");
                        if (Bukkit.getPlayer(args[0]) != null) {

                            main.health.put(Bukkit.getPlayer(args[0]).getUniqueId(), main.startingmaxhealth);
                            Player player = Bukkit.getPlayer(args[0]);
                            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(main.startingmaxhealth);

                            if (main.config.getBoolean("FullResetCommand")) {

                                player.teleport(spawn);
                                player.getInventory().clear();
                                player.getEnderChest().clear();
                                player.setTotalExperience(0);

                            }

                        } else {
                            System.out.println(prefix + " Player returned null! are they online?");
                        }

                    }
                } else if (args[0].equals("all")) {

                    if (!(commandSender instanceof Player) || commandSender.hasPermission("soulsiphon.resetlives")) {

                        main.heartslist.delete();


                        for (int i = 0; i < main.config.getStringList("PlayerBanList").size(); i++) {

                            for (Player o : Bukkit.getOnlinePlayers()) {
                                if (main.config.getStringList("PlayerBanList").get(i).equals(o.getUniqueId().toString())) {

                                    o.setGameMode(GameMode.SURVIVAL);
                                    main.health.put(o.getUniqueId(), main.startingmaxhealth);
                                    Bukkit.getBanList(BanList.Type.NAME).pardon(o.getName());
                                    o.teleport(spawn);
                                    System.out.println(prefix + " reset hearts of" + o.getName());
                                    o.sendMessage(prefix + " Your lives have been reset!");

                                }


                            }

                            for (OfflinePlayer of : Bukkit.getOfflinePlayers()) {
                                if (main.config.getStringList("PlayerBanList").get(i).equals(of.getUniqueId().toString())) {

                                    main.health.put(of.getUniqueId(), main.startingmaxhealth);
                                    if (of.isBanned()) {
                                        Bukkit.getBanList(BanList.Type.NAME).pardon(of.getName());
                                    }
                                    main.config.getStringList("PlayerBanList").add(of.getUniqueId().toString() + "TCG");
                                    System.out.println(prefix + " reset hearts of" + of.getName());

                                }


                            }


                            main.config.getStringList("PlayerBanList").remove(i);


                        }
                        System.out.println(prefix + " Completely reset hearts!");
                    }


                                                        try {
                                        main.config.save(new File(main.getDataFolder(), "configuration.yml"));
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                    try {
                        main.modifyhl.save("heartslist.yml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    main.reloadConfig();
                } else {

                    if (Bukkit.getOfflinePlayer(args[0]) != null && main.config.getStringList("PlayerBanList").contains(Bukkit.getOfflinePlayer(args[0]).getUniqueId())) {

                        OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
                        main.health.put(Bukkit.getPlayer(args[0]).getUniqueId(), main.startingmaxhealth);
                        commandSender.sendMessage(prefix + " Reset player's hearts!");
                        main.config.getStringList("PlayerBanList").remove(p.getUniqueId().toString());
                        main.config.getStringList("PlayerBanList").add(p.getUniqueId().toString() + "TCG");
                                                            try {
                                        main.config.save(new File(main.getDataFolder(), "configuration.yml"));
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }


                    } else if (Bukkit.getOfflinePlayer(args[0]) != null) {

                        OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
                        main.health.put(p.getUniqueId(), main.startingmaxhealth);
                        commandSender.sendMessage(prefix + " Reset player's hearts!");
                        main.config.getStringList("PlayerBanList").add(p.getUniqueId().toString() + "TCGfr");





                    }

                }
            }
                                            try {
                                        main.config.save(new File(main.getDataFolder(), "configuration.yml"));
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
        main.reloadConfig();

        return false;
    }
}
