package com.dop.soulsiphon.Listeners;

import com.dop.soulsiphon.Main;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.util.Map;
import java.util.UUID;

public class OnPlayerJoin implements Listener {

    private Main main;

    public OnPlayerJoin(Main main) {

        this.main = main;
        prefix = main.prefix;
        health = main.health;
        modifyhl = main.modifyhl;
        startingmaxhealth = main.startingmaxhealth;

    }

    private String prefix;
    private Map<UUID, Integer> health;
    private YamlConfiguration modifyhl;
    private int startingmaxhealth;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (e.getPlayer().hasPlayedBefore()) {
            if (health.get(e.getPlayer().getUniqueId()) != null) {
            } else {

                if (modifyhl.contains(e.getPlayer().getUniqueId().toString())) {

                    health.put(e.getPlayer().getUniqueId(), modifyhl.getInt(e.getPlayer().getUniqueId().toString()));
                    e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health.get(e.getPlayer().getUniqueId()));

                } else {
                    health.put(e.getPlayer().getUniqueId(), startingmaxhealth);
                    e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health.get(e.getPlayer().getUniqueId()));
                }

            }
        } else {
            health.put(e.getPlayer().getUniqueId(), startingmaxhealth);
            e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health.get(e.getPlayer().getUniqueId()));

        }


        if (main.health.get(player.getUniqueId()) <= 0) {

            if (main.config.getString("DeathOutcome").equals("spectator")) {

                player.setGameMode(GameMode.SPECTATOR);
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
                main.modifybl.set(player.getUniqueId().toString(), "BFP");

            } else if (main.config.getString("DeathOutcome").equals("adventure")) {

                player.setGameMode(GameMode.ADVENTURE);
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
                main.modifybl.set(player.getUniqueId().toString(), "BFP");

            } else if (main.config.getString("DeathOutcome").equals("banned")) {

                Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), prefix + " " + ChatColor.translateAlternateColorCodes('&', main.lang.getString("BanMessage")), null, "console");
                player.kickPlayer(prefix + " " + ChatColor.translateAlternateColorCodes('&', main.lang.getString("BanMessage")));
                main.modifybl.set(player.getUniqueId().toString(), "BFP");

            } else {

                System.out.println(prefix + " " + ChatColor.translateAlternateColorCodes('&', main.lang.getString("IncorrectOutcome1")) + " " + main.config.getString("DeathOutcome") + ". " + ChatColor.translateAlternateColorCodes('&', main.lang.getString("IncorrectOutcome2")));


            }

        } else if (main.modifybl.get(player.getUniqueId().toString()) != null && main.modifybl.get(player.getUniqueId().toString()) == "TBC") {

            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(new Location((World) Bukkit.getWorld(main.config.get("ReviveSpawn.world").toString()), (Double) main.config.getDouble("ReviveSpawn.x"), (Double) main.config.getDouble("ReviveSpawn.y"), (Double) main.config.getDouble("ReviveSpawn.z")));
            main.modifybl.set(player.getUniqueId().toString(), "NA");

        } else if (main.modifybl.get(player.getUniqueId().toString()) != null && main.modifybl.get(player.getUniqueId().toString()) == "TBCFR") {

            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(new Location((World) Bukkit.getWorld(main.config.get("ReviveSpawn.world").toString()), (Double) main.config.getDouble("ReviveSpawn.x"), (Double) main.config.getDouble("ReviveSpawn.y"), (Double) main.config.getDouble("ReviveSpawn.z")));
            main.modifybl.set(player.getUniqueId().toString(), "NA");

            if (main.config.getBoolean("FullResetCommand")) {
                Location spawn = new Location((World) Bukkit.getWorld(main.config.get("ReviveSpawn.world").toString()), (Double) main.config.getDouble("ReviveSpawn.x"), (Double) main.config.getDouble("ReviveSpawn.y"), (Double) main.config.getDouble("ReviveSpawn.z"));
                player.teleport(spawn);
                player.getInventory().clear();
                player.getEnderChest().clear();
                player.setTotalExperience(0);
            }
        }
    }
}
