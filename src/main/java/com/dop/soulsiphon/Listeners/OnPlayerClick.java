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

        if (e.getView().getTitle().equals(ChatColor.AQUA.toString() + "Recipe GUI") && main.config.getBoolean("HeartsEnabled") && main.config.getBoolean("HeartRecipeEnabled")) {
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
                            System.out.println("a placed");
                        } else {main.config.set("CRKeys.a", "air");}
                        if (b != null) {
                            main.config.set("CRKeys.b", b.toString());
                            System.out.println("b placed");
                        } else {main.config.set("CRKeys.b", "air");}
                        if (c != null) {
                            main.config.set("CRKeys.c", c.toString());
                            System.out.println("c placed");
                        } else {main.config.set("CRKeys.c", "air");}
                        if (d != null) {
                            main.config.set("CRKeys.d", d.toString());
                            System.out.println("d placed");
                        } else {main.config.set("CRKeys.d", "air");}
                        if (f != null) {
                            main.config.set("CRKeys.f", f.toString());
                            System.out.println("f placed");
                        } else {main.config.set("CRKeys.f", "air");}
                        if (g != null) {
                            main.config.set("CRKeys.g", g.toString());
                            System.out.println("g placed");
                        } else {main.config.set("CRKeys.g", "air");}
                        if (h != null) {
                            main.config.set("CRKeys.h", h.toString());
                            System.out.println("h placed");
                        } else {main.config.set("CRKeys.h", "air");}
                        if (i != null) {
                            main.config.set("CRKeys.i", i.toString());
                            System.out.println("i placed");
                        } else {main.config.set("CRKeys.i", "air");}
                        if (j != null) {
                            main.config.set("CRKeys.j", j.toString());
                            System.out.println("j placed");
                        } else {main.config.set("CRKeys.j", "air");}


                        main.config.set("HBRB", true);
                        try {
                            main.config.save(new File(main.getDataFolder(), "configuration.yml"));
                            System.out.println("saved to file");
                        } catch (IOException x) {
                            throw new RuntimeException(x);
                        }

                        e.getWhoClicked().closeInventory();

                        e.getWhoClicked().sendMessage(prefix + " Recipe saved! Due to Spigot API restrictions, a server restart is required to enable the recipe...");

                        e.setCancelled(true);
                    }
                } else if (e.getRawSlot() >= 0 && e.getRawSlot() <=43){
                e.setCancelled(true);
            }








        }

    }




}




