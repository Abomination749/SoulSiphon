package com.dop.soulsiphon.Listeners;

import com.dop.soulsiphon.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

public class OnPlayerCraft implements Listener {

    private Main main;

    public OnPlayerCraft(Main main) {

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
    public void OnCraft(PrepareItemCraftEvent e) {

        if (e.getRecipe() != null && e.getRecipe().getResult() != null  && e.getRecipe().getResult().equals(Material.BEACON)) {

            if (e.getRecipe() != null && e.getRecipe().getResult() != null && e.getRecipe().getResult().getType() == Material.BEACON) {

                for (int a = 1; a < 9; a++) {
                    if (e.getInventory().getItem(a) != null) {
                        if (e.getInventory().getItem(a).equals(heart)) {
                            ItemStack air = new ItemStack(Material.AIR);
                            e.getInventory().setResult(air);
                        }
                    }
                }
            }
        }
    }
}
