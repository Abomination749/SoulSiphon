package com.dop.soulsiphon.Listeners;

import com.dop.soulsiphon.Main;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.World;
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

            Particle p = Particle.REDSTONE;


            if (e.getEntity().getKiller() instanceof Player) {

                if (main.config.getBoolean("HeartsDropOnDeath") && main.config.getBoolean("HeartsEnabled")) {

                    if (main.config.getBoolean("DeathParticles")) {
                            World world = e.getEntity().getLocation().getWorld();
                            for (int i = 0; i < main.config.getInt("DeathParticlesCount"); i++) {
                                double offsetX = Math.random() * 2 - 1;
                                double offsetY = Math.random() * 2 - 1;
                                double offsetZ = Math.random() * 2 - 1;

                                world.spawnParticle(p, e.getEntity().getEyeLocation(), 1, offsetX, offsetY, offsetZ, new Particle.DustOptions(Color.RED, 1));
                            }
                    }


                    e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), heart);

                } else {
                    Player killer = e.getEntity().getKiller();
                    health.put(killer.getUniqueId(), health.get(killer.getUniqueId()) + (main.config.getInt("HeartsLostOnDeath") * 2));
                    killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health.get(killer.getUniqueId()));

                    if (main.config.getBoolean("DeathParticles")) {
                        World world = e.getEntity().getLocation().getWorld();
                        for (int i = 0; i < main.config.getInt("DeathParticlesCount"); i++) {
                            double offsetX = Math.random() * 2 - 1;
                            double offsetY = Math.random() * 2 - 1;
                            double offsetZ = Math.random() * 2 - 1;

                            world.spawnParticle(p, e.getEntity().getEyeLocation(), 1, offsetX, offsetY, offsetZ, new Particle.DustOptions(Color.RED, 1));
                        }
                    }

                    Player player = ((Player) e.getEntity()).getPlayer();
                    health.put(player.getUniqueId(), health.get(player.getUniqueId()) - (main.config.getInt("HeartsLostOnDeath") * 2));
                    if (health.get(player.getUniqueId()).equals(0)) {

                        if (main.config.getBoolean("EnderchestOnLoss")) {

                            player.getEnderChest().clear();

                        }

                        if (main.config.getBoolean("DeathMessageEnabled")) {
                            e.setDeathMessage(main.config.getString("DeathMessagePrefix") + " " + player.getDisplayName() + " " + main.config.getString("DeathMessage"));
                        }


                    }
                }
            } else {

                if (main.config.getBoolean("OtherDeathParticles")) {
                    World world = e.getEntity().getLocation().getWorld();
                    for (int i = 0; i < main.config.getInt("DeathParticlesCount"); i++) {
                        double offsetX = Math.random() * 2 - 1;
                        double offsetY = Math.random() * 2 - 1;
                        double offsetZ = Math.random() * 2 - 1;

                        world.spawnParticle(p, e.getEntity().getEyeLocation(), 1, offsetX, offsetY, offsetZ, new Particle.DustOptions(Color.RED, 1));
                    }
                }

                if (main.config.getBoolean("OtherDeathsCount")) {

                    if (health.get(e.getEntity().getUniqueId()).equals(0)) {

                        if (main.config.getBoolean("EnderchestOnLoss")) {

                            e.getEntity().getEnderChest().clear();

                        }

                        if (main.config.getBoolean("DeathMessageEnabled")) {
                            e.setDeathMessage(main.prefix + " " + e.getEntity().getDisplayName() + " " + ChatColor.translateAlternateColorCodes('&', main.lang.getString("DeathMessage")));
                        }


                    }


                    if (main.config.getBoolean("HeartsDropOnDeath") && main.config.getBoolean("HeartsEnabled") && main.config.getBoolean("OtherDeathsDrop")) {
                        e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), heart);

                        if (main.config.getBoolean("OtherDeathParticles")) {
                            World world = e.getEntity().getLocation().getWorld();
                            for (int i = 0; i < main.config.getInt("DeathParticlesCount"); i++) {
                                double offsetX = Math.random() * 2 - 1;
                                double offsetY = Math.random() * 2 - 1;
                                double offsetZ = Math.random() * 2 - 1;

                                world.spawnParticle(p, e.getEntity().getEyeLocation(), 1, offsetX, offsetY, offsetZ, new Particle.DustOptions(Color.RED, 1));
                            }
                        }
                    } else {
                        Player player = ((Player) e.getEntity()).getPlayer();
                        health.put(player.getUniqueId(), health.get(player.getUniqueId()) - (main.config.getInt("HeartsLostOnDeath") * 2));
                        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health.get(player.getUniqueId()));

                        if (main.config.getBoolean("OtherDeathParticles")) {
                            World world = e.getEntity().getLocation().getWorld();
                            for (int i = 0; i < main.config.getInt("DeathParticlesCount"); i++) {
                                double offsetX = Math.random() * 2 - 1;
                                double offsetY = Math.random() * 2 - 1;
                                double offsetZ = Math.random() * 2 - 1;

                                world.spawnParticle(p, e.getEntity().getEyeLocation(), 1, offsetX, offsetY, offsetZ, new Particle.DustOptions(Color.RED, 1));
                            }
                        }

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
            return;
        }

    }


}
