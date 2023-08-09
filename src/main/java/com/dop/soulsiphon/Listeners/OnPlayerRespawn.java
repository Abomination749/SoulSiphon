package com.dop.soulsiphon.Listeners;

import com.dop.soulsiphon.Main;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

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

            if (main.getConfig().getBoolean("EnderchestOnLoss")) {

                player.getEnderChest().clear();

            }


            if (main.getConfig().getString("DeathOutcome").equals("spectator")) {
                player.setGameMode(GameMode.SPECTATOR);
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
                List<String> list = main.getConfig().getStringList("PlayerBanList");
                list.add(player.getUniqueId().toString());
                main.getConfig().set("PlayerBanList", list);
                try {
                    main.getConfig().save("config.yml");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                main.reloadConfig();

            } else if (main.getConfig().getString("DeathOutcome").equals("adventure")) {

                player.setGameMode(GameMode.ADVENTURE);
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
                main.getConfig().getStringList("PlayerBanList").add(player.getUniqueId().toString());
                try {
                    main.getConfig().save("config.yml");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                main.reloadConfig();

            } else if (main.getConfig().getString("DeathOutcome").equals("banned")) {

                Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), "You have run out of hearts!", null, "console");
                player.kickPlayer("You ran out of hearts!");
                List<String> list = main.getConfig().getStringList("PlayerBanList");
                list.add(player.getUniqueId().toString());
                main.getConfig().set("PlayerBanList", list);


            } else {

                System.out.println(prefix + " DeathOutcome not configured right! Returned: " + main.getConfig().getString("DeathOutcome") + "Should be spectator, adventure, or banned! Using spectator as default!");


            }

            main.saveConfig();
            main.reloadConfig();
            if (main.getConfig().getStringList("PlayerBanList").contains(player.getUniqueId().toString())) {
                System.out.println(prefix + " Player is in config!");


            } else {
                System.out.println(prefix +" Trying to save player again...");

                List<String> list = main.getConfig().getStringList("PlayerBanList");
                list.add(player.getUniqueId().toString());
                main.getConfig().set("PlayerBanList", list);
                main.saveConfig();
            }
            main.reloadConfig();

        } else {

            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health.get(player.getUniqueId()));
            System.out.println(prefix + " Player " + e.getPlayer() + " has respawned with new health!");

        }

        for (Map.Entry<UUID, Integer> entry : health.entrySet()) {

            UUID key = entry.getKey();
            Integer value = entry.getValue();

            modifyhl.set(key.toString(), value);


        }

        try {
            modifyhl.save(main.heartslist);
        } catch (IOException ex) {
            System.out.println(prefix + " An issue occurred with saving heartslist.yml!");
            return;
        }



    }

}
