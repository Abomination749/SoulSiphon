package com.dop.soulsiphon.Listeners;

import com.dop.soulsiphon.Main;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

public class OnPlayerInteract implements Listener {

    private Main main;

    public OnPlayerInteract(Main main) {

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
    public void Oninteract(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            e.getPlayer().getInventory().getItemInMainHand();
            if (e.getPlayer().getInventory().getItemInMainHand().isSimilar(main.heart)) {
                Player player = e.getPlayer();
                if (main.config.getBoolean("HeartUseParticles")) {
                    Location loc = player.getLocation();
                    int radius = 2;
                    for(double y = 0; y <= 25; y+=0.25) {
                        double x = radius * Math.cos(y);
                        double z = radius * Math.sin(y);
                        Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z);
                        player.spawnParticle(Particle.HEART, loc2, 1);
                    }

                }
                if (main.config.getBoolean("HeartUseSounds")) {
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1, 1);
                    player.playSound(player.getLocation(), Sound.BLOCK_BELL_RESONATE, 1, 1);
                }
                health.put(player.getUniqueId(), health.get(player.getUniqueId()) + 2);
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health.get(player.getUniqueId()));
                player.setHealth(health.get(player.getUniqueId()));

                int amount = player.getInventory().getItemInMainHand().getAmount();
                if (amount > 1) {
                    player.getInventory().getItemInMainHand().setAmount(amount - 1);
                } else {
                    player.getInventory().removeItem(player.getInventory().getItemInMainHand());
                }
            } else if (e.getPlayer().getInventory().getItemInMainHand().equals(main.beacon)) {
                Player player = e.getPlayer();


                player.sendMessage(prefix + " What is the username of the player you would like to revive?");

                main.chatlist.add(player.getUniqueId().toString());
                System.out.println(main.chatlist);


            }
        }


    }

}
