package com.dop.soulsiphon.Listeners;

import com.dop.soulsiphon.Main;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OnPlayerRespawn implements Listener {

    private Main main;

    public OnPlayerRespawn(Main main) {

        this.main = main;
        prefix = main.prefix;
        health = main.health;
        modifyhl = main.modifyhl;
        startingmaxhealth = main.startingmaxhealth;
        heart = main.heart;

    }
    private String prefix;
    private Map<UUID, Integer> health;
    private YamlConfiguration modifyhl;
    private int startingmaxhealth;
    private ItemStack heart;





    @EventHandler
    public void Onrespawn(PlayerRespawnEvent e) {

        Player player = e.getPlayer();


        if (health.get(player.getUniqueId()) <= 0) {

            if (main.config.getBoolean("EnderchestOnLoss")) {

                player.getEnderChest().clear();

            }


            if (main.config.getString("DeathOutcome").equals("spectator")) {
                player.setGameMode(GameMode.SPECTATOR);
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
                main.modifybl.set(player.getUniqueId().toString(), "BFP");
                try {
                    main.modifybl.save(main.banlist);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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

            try {
                main.config.save(new File(main.getDataFolder(), "configuration.yml"));
            } catch (IOException x) {
                throw new RuntimeException(x);
            }
            main.reloadConfig();

            try {
                main.modifybl.save(main.banlist);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            if (main.modifybl.get(player.getUniqueId().toString()) != null && main.modifybl.get(player.getUniqueId().toString()) != "BFP") {


            } else {

                main.modifybl.set(player.getUniqueId().toString(), "BFP");

            }

        } else {

            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health.get(player.getUniqueId()));

        }

        for (Map.Entry<UUID, Integer> entry : health.entrySet()) {

            UUID key = entry.getKey();
            Integer value = entry.getValue();

            modifyhl.set(key.toString(), value);


        }

        try {
            modifyhl.save(main.heartslist);
        } catch (IOException ex) {
            return;
        }

        try {
            main.modifybl.save(main.banlist);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

}
