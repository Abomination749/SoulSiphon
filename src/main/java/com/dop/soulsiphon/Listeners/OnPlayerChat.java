package com.dop.soulsiphon.Listeners;

import com.dop.soulsiphon.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

public class OnPlayerChat implements Listener {

    private Main main;

    public OnPlayerChat(Main main) {

        this.main = main;
    }

    public Location spawn;

    @EventHandler
    public void PlayerChatEvent(AsyncPlayerChatEvent e) {

        if (main.chatlist.contains(e.getPlayer().getUniqueId().toString())) {
            e.setCancelled(true);
            main.chatlist.remove(e.getPlayer().getUniqueId().toString());
            spawn = new Location((World) Bukkit.getWorld(main.config.get("ReviveSpawn.world").toString()), (Double) main.config.getDouble("ReviveSpawn.x"), (Double) main.config.getDouble("ReviveSpawn.y"), (Double) main.config.getDouble("ReviveSpawn.z"));

            String s = e.getMessage();
            OfflinePlayer oP = Bukkit.getOfflinePlayer(s);
            Player p = Bukkit.getPlayer(s);

            if (p != null) {

                if (main.modifybl.get(p.getUniqueId().toString()) != null && main.modifybl.get(p.getUniqueId().toString()) != "BFP") {
                    BukkitRunnable task = new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 10, 1));
                            main.health.put(p.getUniqueId(), main.startingmaxhealth);
                            main.modifybl.set(p.getUniqueId().toString(), "NA");
                            p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 10, 1));
                            if (main.config.getBoolean("BeaconUseSounds")) {
                                p.stopSound(SoundCategory.MUSIC);
                                p.stopSound(SoundCategory.AMBIENT);
                                p.playSound(p.getLocation(), Sound.BLOCK_BELL_RESONATE, 1, 1);
                                p.playSound(p.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1, 1);
                                p.playSound(p.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1, 1);

                                e.getPlayer().stopSound(SoundCategory.MUSIC);
                                e.getPlayer().stopSound(SoundCategory.AMBIENT);
                                e.getPlayer().playSound(p.getLocation(), Sound.BLOCK_BELL_RESONATE, 1, 1);
                                e.getPlayer().playSound(p.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1, 1);
                                e.getPlayer().playSound(p.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1, 1);
                            }
                            if (main.config.getBoolean("BeaconUseMusic")) {
                                p.playSound(p.getLocation(), Sound.MUSIC_CREDITS, 1000F, 1F);
                                e.getPlayer().playSound(p.getLocation(), Sound.MUSIC_CREDITS, 1000F, 1F);
                            }

                            p.setGameMode(GameMode.SURVIVAL);
                            p.teleport(spawn);
                            p.sendMessage(main.prefix + ChatColor.DARK_RED + ChatColor.BOLD + " Welcome back to the land of the living.");
                            try {
                                main.modifybl.save(main.banlist);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            if (main.config.getBoolean("BeaconUseParticles")) {
                                Location location = e.getPlayer().getLocation();

                                for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
                                    double radius = Math.sin(i);
                                    double y = Math.cos(i);
                                    for (double a = 0; a < Math.PI * 2; a += Math.PI / 10) {
                                        double x = Math.cos(a) * radius;
                                        double z = Math.sin(a) * radius;
                                        location.add(x, y, z);
                                        e.getPlayer().spawnParticle(Particle.END_ROD, location, 1);
                                        location.subtract(x, y, z);
                                    }
                                }
                            }
                        }
                    };
                    task.runTask(main);


                } else {
                    e.getPlayer().sendMessage(main.prefix + " This player has not been lost! Did you enter their name correctly?");
                }

            } else if (oP != null) {
                if (oP.getUniqueId().toString() != null && main.modifybl.get(oP.getUniqueId().toString()) == "BFP") {

                    main.health.put(oP.getUniqueId(), main.startingmaxhealth);
                    if (oP.isBanned()) {
                        Bukkit.getBanList(BanList.Type.NAME).pardon(oP.getName());
                    }
                    main.modifybl.set(oP.getUniqueId().toString(), "TBC");
                    System.out.println(main.prefix + " reset hearts of" + oP.getName());
                    BukkitRunnable task = new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (main.config.getBoolean("BeaconUseSounds")) {
                                e.getPlayer().stopSound(SoundCategory.MUSIC);
                                e.getPlayer().stopSound(SoundCategory.AMBIENT);
                                e.getPlayer().playSound(p.getLocation(), Sound.BLOCK_BELL_RESONATE, 1, 1);
                                e.getPlayer().playSound(p.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1, 1);
                                e.getPlayer().playSound(p.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1, 1);
                            }
                            if (main.config.getBoolean("BeaconUseMusic")) {
                                e.getPlayer().playSound(p.getLocation(), Sound.MUSIC_CREDITS, 1000F, 1F);
                            }

                            if (main.config.getBoolean("BeaconUseParticles")) {

                                Location location = e.getPlayer().getLocation();

                                for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
                                    double radius = Math.sin(i);
                                    double y = Math.cos(i);
                                    for (double a = 0; a < Math.PI * 2; a += Math.PI / 10) {
                                        double x = Math.cos(a) * radius;
                                        double z = Math.sin(a) * radius;
                                        location.add(x, y, z);
                                        e.getPlayer().spawnParticle(Particle.END_ROD, location, 1);
                                        location.subtract(x, y, z);
                                    }
                                }
                            }
                        }
                    };
                    task.runTask(main);

                } else {
                    e.getPlayer().sendMessage(main.prefix + " This player has not been lost! Did you enter their name correctly?");
                }

            } else {
                e.getPlayer().sendMessage(main.prefix + " Player not found! Did you enter their name correctly?");
            }

        }

    }

}
