package com.dop.soulsiphon.Commands.Admin;

import com.dop.soulsiphon.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.recipe.CraftingBookCategory;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class AdminCMD implements CommandExecutor {
    private Main main;

    public AdminCMD(Main main) {

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
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("setrevivespawn")) {

                if (commandSender instanceof Player) {

                    Player player = (Player) commandSender;
                    if (player.hasPermission("soulsiphon.setrespawnpoint")) {
                        Location location = player.getLocation();

                        main.config.set("ReviveSpawn.x", location.getX());
                        main.config.set("ReviveSpawn.y", location.getY());
                        main.config.set("ReviveSpawn.z", location.getZ());
                        main.config.set("ReviveSpawn.world", location.getWorld().getName());
                                                            try {
                                        main.config.save(new File(main.getDataFolder(), "configuration.yml"));
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                        main.reloadConfig();
                        player.sendMessage(main.prefix + " Spawn set to " + location.getX() + " " + location.getY() + " " + location.getZ() + " in world " + location.getWorld().getName());
                    }
                } else {

                    System.out.println("Command cannot be run from console!");

                }

            } else if (args[0].equalsIgnoreCase("reload")) {


                if (commandSender instanceof Player) {
                    if (commandSender.hasPermission("soulsteal.reload")) {
                        main.reloadConfig();
                        commandSender.sendMessage(main.prefix + " Config reloaded!");
                        if (main.config.getBoolean("HeartRecipeEnabled") && main.config.getBoolean("HeartsEnabled")) {

                            main.heartrecipe.setCategory(CraftingBookCategory.MISC);
                            Bukkit.addRecipe(main.heartrecipe);


                        } else {

                            if (Bukkit.getRecipe(main.key) != null) {
                                Bukkit.removeRecipe(main.key);
                            }
                        }
                    }
                } else {
                    System.out.println(main.prefix + " Config reloaded!");
                    main.reloadConfig();
                    if (main.config.getBoolean("HeartRecipeEnabled") && main.config.getBoolean("HeartsEnabled")) {

                        main.heartrecipe.setCategory(CraftingBookCategory.MISC);
                        Bukkit.addRecipe(main.heartrecipe);


                    } else {

                        if (Bukkit.getRecipe(main.key) != null) {
                            Bukkit.removeRecipe(main.key);
                        }
                    }
                }

            } else if (args[0].equalsIgnoreCase("give")) {

                if (commandSender instanceof Player) {

                    Player p = (Player) commandSender;
                    if (p.hasPermission("soulsiphon.give")) {
                        p.getInventory().addItem(main.heart);
                    }
                } else {

                    System.out.println(main.prefix + " You can't run this here!");

                }

            } else if (args[0].equalsIgnoreCase("withdraw")) {

                Bukkit.dispatchCommand(commandSender, "withdraw");

            }else if (args[0].equalsIgnoreCase("help")) {


                if (commandSender instanceof Player) {
                    commandSender.sendMessage(ChatColor.RED + "Soul Siphon" +
                            "\n" + ChatColor.RESET + "soulsiphon withdraw <number> - Withdraws a number of hearts." +
                            "\n soulsiphon setrevivespawn - sets the revive spawn to where you are standing." +
                            "\n soulsiphon resetlives <username/all> - resets the lives of one or every player." +
                            "\n soulsiphon config <key> <value> - changes a config value." +
                            "\n soulsiphon reload - restarts the config" +
                            "\n soulsiphon give <number> - gives heart items." +
                            "\n soulsiphon help - sends this message." +
                            "\n soulsiphon savehl - saves the heartlist. (This is done automatically on server stop!)");


                } else {
                    System.out.println(ChatColor.RED + "Soul Siphon" +
                            "\n" + ChatColor.RESET + "soulsiphon withdraw <number> - Withdraws a number of hearts." +
                            "\n soulsiphon setrevivespawn - sets the revive spawn to where you are standing." +
                            "\n soulsiphon resetlives <username/all> - resets the lives of one or every player." +
                            "\n soulsiphon config <key> <value> - changes a config value." +
                            "\n soulsiphon reload - restarts the config" +
                            "\n soulsiphon give <number> - gives heart items." +
                            "\n soulsiphon help - sends this message.");
                }

            }else if (args[0].equalsIgnoreCase("SaveHL")) {

                for (Map.Entry<UUID, Integer> entry : main.health.entrySet()) {

                    UUID key = entry.getKey();
                    Integer value = entry.getValue();

                    main.modifyhl.set(key.toString(), value);


                }

                try {
                    main.modifyhl.save(main.heartslist);
                } catch (IOException ex) {
                    System.out.println(main.prefix + " An issue occurred with saving heartslist.yml!");
                    return false;
                }
            } else if (args[0].equalsIgnoreCase("heartrecipegui")) {

                if (commandSender instanceof Player && commandSender.hasPermission("soulsiphon.heartrecipegui")) {

                    ItemStack save = new ItemStack(Material.NETHER_STAR);
                    ItemMeta savem = save.getItemMeta();
                    ItemStack border = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                    savem.setDisplayName("Save");
                    save.setItemMeta(savem);

                    Inventory inv = Bukkit.createInventory((InventoryHolder) commandSender, 45, ChatColor.AQUA.toString() + "Heart Recipe GUI");

                    for (int i : new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 14, 15, 16, 17, 18, 19, 23, 24, 25, 26, 27, 28, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43}) {

                        inv.setItem(i, border);

                    }

                    inv.setItem(44, save);
                    ((Player) commandSender).openInventory(inv);

                }

            } else if (args[0].equalsIgnoreCase("beaconrecipegui")) {

                if (commandSender instanceof Player && commandSender.hasPermission("soulsiphon.heartrecipegui")) {

                    ItemStack save = new ItemStack(Material.NETHER_STAR);
                    ItemMeta savem = save.getItemMeta();
                    ItemStack border = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                    savem.setDisplayName("Save");
                    save.setItemMeta(savem);

                    Inventory inv = Bukkit.createInventory((InventoryHolder) commandSender, 45, ChatColor.AQUA.toString() + "Beacon Recipe GUI");

                    for (int i : new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 14, 15, 16, 17, 18, 19, 23, 24, 25, 26, 27, 28, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43}) {

                        inv.setItem(i, border);

                    }

                    inv.setItem(44, save);
                    ((Player) commandSender).openInventory(inv);

                }

            } else {

                if (commandSender instanceof Player) {
                    commandSender.sendMessage(main.prefix + " Invalid usage!");
                } else {
                    System.out.println(main.prefix + " Invalid usage!");
                }

            }

        } else if (args.length == 2) {

            if (args[0].equalsIgnoreCase("withdraw")) {

                Bukkit.dispatchCommand(commandSender, "withdraw " + args);

            } else if (args[0].equalsIgnoreCase("resetlives")) {

                Bukkit.dispatchCommand(commandSender, "resetlives " + args);


            } else if (args[0].equalsIgnoreCase("give")) {

                if (commandSender instanceof Player) {

                    Player player = (Player) commandSender;
                    if (player.hasPermission("soulsiphon.give.multiple"))
                        if (isInteger(args[1])) {
                            for (int i = 0; i < Integer.parseInt(args[1]); i++) {

                                player.getInventory().addItem(main.heart);

                            }

                        } else {
                            player.sendMessage(main.prefix + " You did not input a number!");
                        }

                } else {

                    System.out.println(main.prefix + " You cannot run this here!");

                }

            } else {

                if (commandSender instanceof Player) {
                    commandSender.sendMessage(main.prefix + " Invalid usage!");
                } else {
                    System.out.println(main.prefix + " Invalid usage!");
                }

            }

        } else if (args.length == 3) {

            if (args[0].equalsIgnoreCase("give")) {
                if (commandSender.hasPermission("soulsteal.give") || !(commandSender instanceof Player)) {
                    Player reciever = Bukkit.getPlayer(args[1]);
                    if (reciever != null) {

                        Player player = (Player) commandSender;
                        if (player.hasPermission("soulsiphon.give.multiple"))
                            if (isInteger(args[2])) {
                                for (int i = 0; i < Integer.parseInt(args[2]); i++) {

                                    reciever.getInventory().addItem(main.heart);

                                }

                            } else {
                                player.sendMessage(main.prefix + " You did not input a number!");
                            }

                    }
                }

            } else if (args[0].equalsIgnoreCase("config")) {

                if (commandSender.hasPermission("soulsteal.config") || !(commandSender instanceof Player)) {

                    String key = args[1];
                    if (args[2].equalsIgnoreCase("true")|| args[2].equalsIgnoreCase("false")) {

                        boolean value = Boolean.parseBoolean(args[2]);
                        main.config.set(key, value);
                                                            try {
                                        main.config.save(new File(main.getDataFolder(), "configuration.yml"));
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                    } else if (isInteger(args[2])) {

                        int value = Integer.parseInt(args[2]);
                        main.config.set(key, value);
                                                            try {
                                        main.config.save(new File(main.getDataFolder(), "configuration.yml"));
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }



                    } else {

                        String value = args[2];
                        main.config.set(key, value);
                                                            try {
                                        main.config.save(new File(main.getDataFolder(), "configuration.yml"));
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }



                    }

                                                        try {
                                        main.config.save(new File(main.getDataFolder(), "configuration.yml"));
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                    main.reloadConfig();

                    if (commandSender instanceof Player) {
                        commandSender.sendMessage(main.prefix + " Config successfully changed!");
                    } else {
                        System.out.println(main.prefix + " Config successfully changed!");
                    }

                }

            } else {

                if (commandSender instanceof Player) {
                    commandSender.sendMessage(main.prefix + " Invalid usage!");
                } else {
                    System.out.println(main.prefix + " Invalid usage!");
                }

            }

        } else if (args.length == 0) {

            if (commandSender instanceof Player) {
                commandSender.sendMessage(ChatColor.RED + "Soul Siphon" +
                        "\n" + ChatColor.RESET + "soulsiphon withdraw <number> - Withdraws a number of hearts." +
                        "\n soulsiphon setrevivespawn - sets the revive spawn to where you are standing." +
                        "\n soulsiphon resetlives <username/all> - resets the lives of one or every player." +
                        "\n soulsiphon config <key> <value> - changes a config value." +
                        "\n soulsiphon reload - restarts the config" +
                        "\n soulsiphon give <number> - gives heart items." +
                        "\n soulsiphon help - sends this message." +
                        "\n soulsiphon savehl - saves the heartlist. (This is done automatically on server stop!)");
            } else {
                System.out.println(ChatColor.RED + "Soul Siphon" +
                        "\n" + ChatColor.RESET + "soulsiphon withdraw <number> - Withdraws a number of hearts." +
                        "\n soulsiphon setrevivespawn - sets the revive spawn to where you are standing." +
                        "\n soulsiphon resetlives <username/all> - resets the lives of one or every player." +
                        "\n soulsiphon config <key> <value> - changes a config value." +
                        "\n soulsiphon reload - restarts the config" +
                        "\n soulsiphon give <number> - gives heart items." +
                        "\n soulsiphon help - sends this message." +
                        "\n soulsiphon savehl - saves the heartlist. (This is done automatically on server stop!)");
            }

        } else {

            if (commandSender instanceof Player) {
                commandSender.sendMessage(main.prefix + " Invalid usage!");
            } else {
                System.out.println(main.prefix + " Invalid usage!");
            }

        }


        return false;
    }
}
