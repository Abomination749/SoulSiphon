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
                    if (args[0].equals("all") || main.modifybl.get(Bukkit.getPlayer(args[0]).getUniqueId().toString()) != null || main.modifybl.get(Bukkit.getPlayer(args[0]).getUniqueId().toString()) != "BFP" ||  main.modifybl.get(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString()) != "BFP") {

                        if (commandSender instanceof Player) {
                            if (commandSender.hasPermission("soulsiphon.resetlives")) {
                                Player p = Bukkit.getPlayer(args[0]);
                                if (p != null && !args[0].equals("all")) {


                                    main.health.put(p.getUniqueId(), main.startingmaxhealth);
                                    commandSender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', main.lang.getString("ResetHearts")));
                                    main.modifybl.set(p.getUniqueId().toString(), "NA");
                                    p.setGameMode(GameMode.SURVIVAL);
                                    p.teleport(spawn);
                                    p.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', main.lang.getString("ResetHearts")));
                                    try {
                                        main.modifybl.save(main.banlist);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }


                                } else if (!args[0].equals("all")) {

                                    commandSender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', main.lang.getString("NullPlayer")));


                                }

                            }
                        } else {
                            Player p = Bukkit.getPlayer(args[0]);
                            if (p != null && !args[0].equals("all")) {

                                main.health.put(Bukkit.getPlayer(args[0]).getUniqueId(), main.startingmaxhealth);
                                commandSender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', main.lang.getString("ResetHearts")));
                                main.modifybl.set(p.getUniqueId().toString(), "NA");
                                p.setGameMode(GameMode.SURVIVAL);
                                p.teleport(spawn);
                                try {
                                    main.modifybl.save(main.banlist);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                            } else if (!args[0].equals("all")) {

                                commandSender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', main.lang.getString("NullPlayer")));


                            }
                        }
                    } else {

                        System.out.println(prefix + " " + ChatColor.translateAlternateColorCodes('&', main.lang.getString("NotInBanList")));
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
                            System.out.println(prefix + " " + ChatColor.translateAlternateColorCodes('&', main.lang.getString("NullPlayer")));
                        }

                    }
                } else if (args[0].equals("all")) {

                    if (!(commandSender instanceof Player) || commandSender.hasPermission("soulsiphon.resetlives")) {

                        main.heartslist.delete();

                        for (Player o : Bukkit.getOnlinePlayers()) {
                            if (main.modifybl.get(o.getUniqueId().toString()) == "BFP") {

                                o.setGameMode(GameMode.SURVIVAL);
                                main.health.put(o.getUniqueId(), main.startingmaxhealth);
                                Bukkit.getBanList(BanList.Type.NAME).pardon(o.getName());
                                o.teleport(spawn);
                                System.out.println(prefix + " " + ChatColor.translateAlternateColorCodes('&', main.lang.getString("MedHeartReset")) + " " + o.getName());
                                o.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', main.lang.getString("ResetHeartsMSG")));

                            }


                        }

                        for (int i = 0; i < main.modifybl.getKeys(true).size(); i++) {

                            for (OfflinePlayer of : Bukkit.getOfflinePlayers()) {
                                if (main.modifybl.get(of.getUniqueId().toString()) == "BFP") {

                                    main.health.put(of.getUniqueId(), main.startingmaxhealth);
                                    if (of.isBanned()) {
                                        Bukkit.getBanList(BanList.Type.NAME).pardon(of.getName());
                                    }
                                    main.modifybl.set(of.getUniqueId().toString(), "TBC");
                                    System.out.println(prefix + " " + ChatColor.translateAlternateColorCodes('&', main.lang.getString("MedHeartReset")) + " " + of.getName());

                                }


                            }
                        }

                        main.banlist.delete();

                        System.out.println(prefix + " " + ChatColor.translateAlternateColorCodes('&', main.lang.getString("ResetAllHearts")));
                    }


                    try {
                        main.modifybl.save(main.banlist);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        main.modifyhl.save("heartslist.yml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {

                    if (Bukkit.getOfflinePlayer(args[0]) != null && main.modifybl.get(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString()) != null && main.modifybl.get(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString()) == "BFP") {

                        OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
                        main.health.put(Bukkit.getPlayer(args[0]).getUniqueId(), main.startingmaxhealth);
                        commandSender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', main.lang.getString("ResetHearts")));
                        main.modifybl.set(p.getUniqueId().toString(), "TBC");
                        try {
                            main.modifybl.save(main.banlist);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                    } else if (Bukkit.getOfflinePlayer(args[0]) != null) {

                        OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
                        main.health.put(p.getUniqueId(), main.startingmaxhealth);
                        commandSender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', main.lang.getString("ResetHearts")));
                        main.modifybl.set(p.getUniqueId().toString(), "TBCFR");





                    }

                }
            }
        try {
            main.modifybl.save(main.banlist);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
