package com.dop.soulsiphon.Listeners;

import com.dop.soulsiphon.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class OnPlayerClick implements Listener {

    private Main main;

    public OnPlayerClick(Main main) {

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
    public void PlayerClick(InventoryClickEvent e) {

        if (e.getView().getTitle().equals(main.HeartGUIName) && main.config.getBoolean("HeartsEnabled") && main.config.getBoolean("HeartRecipeEnabled")) {

            if (e.getRawSlot() == 11 ||e.getRawSlot() == 12 || e.getRawSlot() == 13 || e.getRawSlot() == 20 || e.getRawSlot() == 21 || e.getRawSlot() == 22 || e.getRawSlot() == 29 || e.getRawSlot() == 30 || e.getRawSlot() == 31 || e.getRawSlot() == 44) {


                Material a = null;
                Material b = null;
                Material c = null;
                Material d = null;
                Material f = null;
                Material g = null;
                Material h = null;
                Material i = null;
                Material j = null;


                if (e.getRawSlot() == 44) {


                    Inventory inv = e.getInventory();
                    if (inv.getItem(11) != null) {a = Material.valueOf(Objects.requireNonNull(inv.getItem(11)).getType().toString());}
                    if (inv.getItem(12) != null) {b = Material.valueOf(Objects.requireNonNull(inv.getItem(12)).getType().toString());}
                    if (inv.getItem(13) != null) {c = Material.valueOf(Objects.requireNonNull(inv.getItem(13)).getType().toString());}
                    if (inv.getItem(20) != null) {d = Material.valueOf(Objects.requireNonNull(inv.getItem(20)).getType().toString());}
                    if (inv.getItem(21) != null) {f = Material.valueOf(Objects.requireNonNull(inv.getItem(21)).getType().toString());}
                    if (inv.getItem(22) != null) {g = Material.valueOf(Objects.requireNonNull(inv.getItem(22)).getType().toString());}
                    if (inv.getItem(29) != null) {h = Material.valueOf(Objects.requireNonNull(inv.getItem(29)).getType().toString());}
                    if (inv.getItem(30) != null) {i = Material.valueOf(Objects.requireNonNull(inv.getItem(30)).getType().toString());}
                    if (inv.getItem(31) != null) {j = Material.valueOf(Objects.requireNonNull(inv.getItem(31)).getType().toString());}


                    if (a != null) {
                        main.config.set("CRKeys.a", a.toString());
                    } else {main.config.set("CRKeys.a", "air");}
                    if (b != null) {
                        main.config.set("CRKeys.b", b.toString());
                    } else {main.config.set("CRKeys.b", "air");}
                    if (c != null) {
                        main.config.set("CRKeys.c", c.toString());
                    } else {main.config.set("CRKeys.c", "air");}
                    if (d != null) {
                        main.config.set("CRKeys.d", d.toString());
                    } else {main.config.set("CRKeys.d", "air");}
                    if (f != null) {
                        main.config.set("CRKeys.f", f.toString());
                    } else {main.config.set("CRKeys.f", "air");}
                    if (g != null) {
                        main.config.set("CRKeys.g", g.toString());
                    } else {main.config.set("CRKeys.g", "air");}
                    if (h != null) {
                        main.config.set("CRKeys.h", h.toString());
                    } else {main.config.set("CRKeys.h", "air");}
                    if (i != null) {
                        main.config.set("CRKeys.i", i.toString());
                    } else {main.config.set("CRKeys.i", "air");}
                    if (j != null) {
                        main.config.set("CRKeys.j", j.toString());
                    } else {main.config.set("CRKeys.j", "air");}


                    main.config.set("HBRB", true);
                    try {
                        main.config.save(new File(main.getDataFolder(), "configuration.yml"));
                    } catch (IOException x) {
                        throw new RuntimeException(x);
                    }

                    e.getWhoClicked().closeInventory();

                    e.getWhoClicked().sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', main.lang.getString("RecipeSaved")));

                    e.setCancelled(true);
                }
            } else if (e.getRawSlot() >= 0 && e.getRawSlot() <=43){
                e.setCancelled(true);
            }

        } else if (e.getView().getTitle().equals(main.BeaconGUIName) && main.config.getBoolean("BeaconsEnabled")) {
            if (e.getRawSlot() == 11 ||e.getRawSlot() == 12 || e.getRawSlot() == 13 || e.getRawSlot() == 20 || e.getRawSlot() == 21 || e.getRawSlot() == 22 || e.getRawSlot() == 29 || e.getRawSlot() == 30 || e.getRawSlot() == 31 || e.getRawSlot() == 44) {


                Material a = null;
                Material b = null;
                Material c = null;
                Material d = null;
                Material f = null;
                Material g = null;
                Material h = null;
                Material i = null;
                Material j = null;


                if (e.getRawSlot() == 44) {


                    Inventory inv = e.getInventory();
                    if (inv.getItem(11) != null) {a = Material.valueOf(Objects.requireNonNull(inv.getItem(11)).getType().toString());}
                    if (inv.getItem(12) != null) {b = Material.valueOf(Objects.requireNonNull(inv.getItem(12)).getType().toString());}
                    if (inv.getItem(13) != null) {c = Material.valueOf(Objects.requireNonNull(inv.getItem(13)).getType().toString());}
                    if (inv.getItem(20) != null) {d = Material.valueOf(Objects.requireNonNull(inv.getItem(20)).getType().toString());}
                    if (inv.getItem(21) != null) {f = Material.valueOf(Objects.requireNonNull(inv.getItem(21)).getType().toString());}
                    if (inv.getItem(22) != null) {g = Material.valueOf(Objects.requireNonNull(inv.getItem(22)).getType().toString());}
                    if (inv.getItem(29) != null) {h = Material.valueOf(Objects.requireNonNull(inv.getItem(29)).getType().toString());}
                    if (inv.getItem(30) != null) {i = Material.valueOf(Objects.requireNonNull(inv.getItem(30)).getType().toString());}
                    if (inv.getItem(31) != null) {j = Material.valueOf(Objects.requireNonNull(inv.getItem(31)).getType().toString());}


                    //Checking for hearts.
                    if (a != null && a.equals(main.heart)) {
                        main.config.set("CRKeysB.a", "Heart");
                    }
                    if (a != null && b.equals(main.heart)) {
                        main.config.set("CRKeysB.b", "Heart");
                    }
                    if (a != null && c.equals(main.heart)) {
                        main.config.set("CRKeysB.c", "Heart");
                    }
                    if (a != null && d.equals(main.heart)) {
                        main.config.set("CRKeysB.d", "Heart");
                    }
                    if (a != null && f.equals(main.heart)) {
                        main.config.set("CRKeysB.f", "Heart");
                    }
                    if (a != null && g.equals(main.heart)) {
                        main.config.set("CRKeysB.g", "Heart");
                    }
                    if (a != null && h.equals(main.heart)) {
                        main.config.set("CRKeysB.h", "Heart");
                    }
                    if (a != null && i.equals(main.heart)) {
                        main.config.set("CRKeysB.i", "Heart");
                    }
                    if (a != null && j.equals(main.heart)) {
                        main.config.set("CRKeysB.j", "Heart");
                    }


                    //Checking for air.
                    if (a != null) {
                        main.config.set("CRKeysB.a", a.toString());
                    } else {main.config.set("CRKeysB.a", "air");}
                    if (b != null) {
                        main.config.set("CRKeysB.b", b.toString());
                    } else {main.config.set("CRKeysB.b", "air");}
                    if (c != null) {
                        main.config.set("CRKeysB.c", c.toString());
                    } else {main.config.set("CRKeysB.c", "air");}
                    if (d != null) {
                        main.config.set("CRKeysB.d", d.toString());
                    } else {main.config.set("CRKeysB.d", "air");}
                    if (f != null) {
                        main.config.set("CRKeysB.f", f.toString());
                    } else {main.config.set("CRKeysB.f", "air");}
                    if (g != null) {
                        main.config.set("CRKeysB.g", g.toString());
                    } else {main.config.set("CRKeysB.g", "air");}
                    if (h != null) {
                        main.config.set("CRKeysB.h", h.toString());
                    } else {main.config.set("CRKeysB.h", "air");}
                    if (i != null) {
                        main.config.set("CRKeysB.i", i.toString());
                    } else {main.config.set("CRKeysB.i", "air");}
                    if (j != null) {
                        main.config.set("CRKeysB.j", j.toString());
                    } else {main.config.set("CRKeysB.j", "air");}


                    main.config.set("HBRC", true);
                    try {
                        main.config.save(new File(main.getDataFolder(), "configuration.yml"));
                    } catch (IOException x) {
                        throw new RuntimeException(x);
                    }

                    e.getWhoClicked().closeInventory();

                    e.getWhoClicked().sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', main.lang.getString("RecipeSaved")));

                    e.setCancelled(true);
                }
            } else if (e.getRawSlot() >= 0 && e.getRawSlot() <=43){
                e.setCancelled(true);
            }
        }

    }




}




