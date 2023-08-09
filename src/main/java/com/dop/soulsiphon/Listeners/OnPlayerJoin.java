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
            System.out.println(prefix + " Player " + e.getPlayer().getName() + " has played before!");
            if (health.get(e.getPlayer().getUniqueId()) != null) {
                System.out.println(prefix + " Player " + e.getPlayer().getName() + " has an entry in the database!");
            } else {

                System.out.println(prefix + " Player " + e.getPlayer().getName() + " does not have an entry in memory! generating...");

                if (modifyhl.contains(e.getPlayer().getUniqueId().toString())) {

                    health.put(e.getPlayer().getUniqueId(), modifyhl.getInt(e.getPlayer().getUniqueId().toString()));
                    e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health.get(e.getPlayer().getUniqueId()));

                } else {
                    System.out.println(prefix + " Player has entry in storage! Getting data...");
                    health.put(e.getPlayer().getUniqueId(), startingmaxhealth);
                    e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health.get(e.getPlayer().getUniqueId()));
                    System.out.println(prefix + " Player " + e.getPlayer().getName() + " now has an entry in memory!");
                }

            }
        } else {
            System.out.println(prefix + " Player " + e.getPlayer().getName() + " has not played before! Creating database entry...");
            health.put(e.getPlayer().getUniqueId(), startingmaxhealth);
            e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health.get(e.getPlayer().getUniqueId()));
            System.out.println(prefix + " Player " + e.getPlayer().getName() + " now has an entry in the database!");

        }


        if (main.health.get(player.getUniqueId()) <= 0) {

            if (main.getConfig().getString("DeathOutcome").equals("spectator")) {

                player.setGameMode(GameMode.SPECTATOR);
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
                main.getConfig().getStringList("PlayerBanList").add(player.getUniqueId().toString());

            } else if (main.getConfig().getString("DeathOutcome").equals("adventure")) {

                player.setGameMode(GameMode.ADVENTURE);
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
                main.getConfig().getStringList("PlayerBanList").add(player.getUniqueId().toString());

            } else if (main.getConfig().getString("DeathOutcome").equals("banned")) {

                Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), prefix + " You have run out of hearts!", null, "console");
                player.kickPlayer(prefix + " You ran out of hearts!");
                main.getConfig().getStringList("PlayerBanList").add(player.getUniqueId().toString());

            } else {

                System.out.println(prefix + " DeathOutcome not configured right! Returned: " + main.getConfig().getString("DeathOutcome") + " Should be spectator, adventure, or banned! Using spectator as default!");


            }

        } else if (main.getConfig().getStringList("PlayerBanList").contains(player.getUniqueId().toString() + "TCG")) {

            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(new Location((World) Bukkit.getWorld(main.getConfig().get("ReviveSpawn.world").toString()), (Double) main.getConfig().getDouble("ReviveSpawn.x"), (Double) main.getConfig().getDouble("ReviveSpawn.y"), (Double) main.getConfig().getDouble("ReviveSpawn.z")));

        } else if (main.getConfig().getStringList("PlayerBanList").contains(player.getUniqueId().toString() + "TCGfr")) {

            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(new Location((World) Bukkit.getWorld(main.getConfig().get("ReviveSpawn.world").toString()), (Double) main.getConfig().getDouble("ReviveSpawn.x"), (Double) main.getConfig().getDouble("ReviveSpawn.y"), (Double) main.getConfig().getDouble("ReviveSpawn.z")));

            if (main.getConfig().getBoolean("FullResetCommand")) {
                Location spawn = new Location((World) Bukkit.getWorld(main.getConfig().get("ReviveSpawn.world").toString()), (Double) main.getConfig().getDouble("ReviveSpawn.x"), (Double) main.getConfig().getDouble("ReviveSpawn.y"), (Double) main.getConfig().getDouble("ReviveSpawn.z"));
                player.teleport(spawn);
                player.getInventory().clear();
                player.getEnderChest().clear();
                player.setTotalExperience(0);
            }
        }
    }

}
