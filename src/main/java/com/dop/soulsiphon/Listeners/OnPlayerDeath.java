package com.dop.soulsiphon.Listeners;

import com.dop.soulsiphon.Main;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class OnPlayerDeath implements Listener {


    private Main main;

    public OnPlayerDeath(Main main) {

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
    public void onPlayerDeath(PlayerDeathEvent e) {

        if (e.getEntity() instanceof Player) {


            if (e.getEntity().getKiller() instanceof Player) {
                System.out.println(prefix + " Player " + e.getEntity().getName() + " has died to a player! Modifying healths...");

                if (main.getConfig().getBoolean("HeartsDropOnDeath") && main.getConfig().getBoolean("HeartsEnabled")) {

                    e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), heart);

                } else {
                    Player killer = e.getEntity().getKiller();
                    health.put(killer.getUniqueId(), health.get(killer.getUniqueId()) + (main.getConfig().getInt("HeartsLostOnDeath") * 2));
                    killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health.get(killer.getUniqueId()));

                    Player player = ((Player) e.getEntity()).getPlayer();
                    health.put(player.getUniqueId(), health.get(player.getUniqueId()) - (main.getConfig().getInt("HeartsLostOnDeath") * 2));
                    System.out.println(prefix + " Player " + e.getEntity().getName() + " and " + e.getEntity().getKiller() + "'s healths have been modified!");
                    if (health.get(player.getUniqueId()).equals(0)) {

                        if (main.getConfig().getBoolean("EnderchestOnLoss")) {

                            player.getEnderChest().clear();

                        }

                        if (main.getConfig().getBoolean("DeathMessageEnabled")) {
                            e.setDeathMessage(main.getConfig().getString("DeathMessagePrefix") + " " + player.getDisplayName() + " " + main.getConfig().getString("DeathMessage"));
                        }


                    }
                }
            } else {
                System.out.println(prefix + " Player " + e.getEntity() + " has died to a non-player cause! Checking config...");
                if (main.getConfig().getBoolean("OtherDeathsCount")) {

                    if (health.get(e.getEntity().getUniqueId()).equals(0)) {

                        if (main.getConfig().getBoolean("EnderchestOnLoss")) {

                            e.getEntity().getEnderChest().clear();

                        }

                        if (main.getConfig().getBoolean("DeathMessageEnabled")) {
                            e.setDeathMessage(main.getConfig().getString("DeathMessagePrefix") + " " + e.getEntity().getDisplayName() + " " + main.getConfig().getString("DeathMessage"));
                        }


                    }


                    if (main.getConfig().getBoolean("HeartsDropOnDeath") && main.getConfig().getBoolean("HeartsEnabled") && main.getConfig().getBoolean("OtherDeathsDrop")) {
                        e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), heart);
                    } else {
                        Player player = ((Player) e.getEntity()).getPlayer();
                        health.put(player.getUniqueId(), health.get(player.getUniqueId()) - (main.getConfig().getInt("HeartsLostOnDeath") * 2));
                        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health.get(player.getUniqueId()));
                        System.out.println(prefix + " Player " + e.getEntity().getName() + "'s health has been modified!");
                    }
                }
            }
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
