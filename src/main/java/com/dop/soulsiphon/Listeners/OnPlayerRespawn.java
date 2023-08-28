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
                List<String> list = main.config.getStringList("PlayerBanList");
                list.add(player.getUniqueId().toString());
                main.config.set("PlayerBanList", list);
                                                        try {
                                        main.config.save(new File(main.getDataFolder(), "configuration.yml"));
                                    } catch (IOException x) {
                                        throw new RuntimeException(x);
                                    }
            } else if (main.config.getString("DeathOutcome").equals("adventure")) {

                player.setGameMode(GameMode.ADVENTURE);
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
                main.config.getStringList("PlayerBanList").add(player.getUniqueId().toString());
                                                        try {
                                        main.config.save(new File(main.getDataFolder(), "configuration.yml"));
                                    } catch (IOException x) {
                                        throw new RuntimeException(x);
                                    }
            } else if (main.config.getString("DeathOutcome").equals("banned")) {

                Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), "You have run out of hearts!", null, "console");
                player.kickPlayer("You ran out of hearts!");
                List<String> list = main.config.getStringList("PlayerBanList");
                list.add(player.getUniqueId().toString());
                main.config.set("PlayerBanList", list);


            } else {

                System.out.println(prefix + " DeathOutcome not configured right! Returned: " + main.config.getString("DeathOutcome") + "Should be spectator, adventure, or banned! Using spectator as default!");


            }

                                                try {
                                        main.config.save(new File(main.getDataFolder(), "configuration.yml"));
                                    } catch (IOException x) {
                                        throw new RuntimeException(x);
                                    }
            main.reloadConfig();
            if (main.config.getStringList("PlayerBanList").contains(player.getUniqueId().toString())) {


            } else {

                List<String> list = main.config.getStringList("PlayerBanList");
                list.add(player.getUniqueId().toString());
                main.config.set("PlayerBanList", list);
                                                    try {
                                        main.config.save(new File(main.getDataFolder(), "configuration.yml"));
                                    } catch (IOException x) {
                                        throw new RuntimeException(x);
                                    }
            }
            main.reloadConfig();

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
            System.out.println(prefix + " An issue occurred with saving heartslist.yml!");
            return;
        }



    }

}
