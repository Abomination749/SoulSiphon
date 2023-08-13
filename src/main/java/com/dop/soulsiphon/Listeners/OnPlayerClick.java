package com.dop.soulsiphon.Listeners;

import com.dop.soulsiphon.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.recipe.CraftingBookCategory;

import java.io.IOException;
import java.util.Map;
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


                ItemStack a = null;
                ItemStack b = null;
                ItemStack c = null;
                ItemStack d = null;
                ItemStack f = null;
                ItemStack g = null;
                ItemStack h = null;
                ItemStack i = null;
                ItemStack j = null;




                switch (e.getRawSlot()) {

                    case 11:
                        a = e.getInventory().getItem(11);
                        break;
                    case 12:
                        b = e.getInventory().getItem(12);
                        break;
                    case 13:
                        c = e.getInventory().getItem(13);
                        break;
                    case 20:
                        d = e.getInventory().getItem(20);
                        break;
                    case 21:
                        f = e.getInventory().getItem(21);
                        break;
                    case 22:
                        g = e.getInventory().getItem(22);
                        break;
                    case 29:
                        h = e.getInventory().getItem(29);
                        break;
                    case 30:
                        i = e.getInventory().getItem(30);
                        break;
                    case 31:
                        j = e.getInventory().getItem(31);
                        break;
                    case 44:


                        if (Bukkit.getRecipe(main.key) != null) {
                            for (org.bukkit.inventory.Recipe recipe : Bukkit.getRecipesFor(main.heartrecipe.getResult())) {
                                if (recipe.getResult().isSimilar(main.heartrecipe.getResult())) {
                                    Bukkit.getRecipesFor(main.heartrecipe.getResult()).remove(recipe);
                                }
                            }
                        }


                        main.heartrecipe.shape("abc", "dfg", "hij");

                        main.heartrecipe.setIngredient('a', a != null ? new RecipeChoice.ExactChoice(a) : new RecipeChoice.MaterialChoice(Material.AIR));
                        main.heartrecipe.setIngredient('b', b != null ? new RecipeChoice.ExactChoice(b) : new RecipeChoice.MaterialChoice(Material.AIR));
                        main.heartrecipe.setIngredient('c', c != null ? new RecipeChoice.ExactChoice(c) : new RecipeChoice.MaterialChoice(Material.AIR));
                        main.heartrecipe.setIngredient('d', d != null ? new RecipeChoice.ExactChoice(d) : new RecipeChoice.MaterialChoice(Material.AIR));
                        main.heartrecipe.setIngredient('f', f != null ? new RecipeChoice.ExactChoice(f) : new RecipeChoice.MaterialChoice(Material.AIR));
                        main.heartrecipe.setIngredient('g', g != null ? new RecipeChoice.ExactChoice(g) : new RecipeChoice.MaterialChoice(Material.AIR));
                        main.heartrecipe.setIngredient('h', h != null ? new RecipeChoice.ExactChoice(h) : new RecipeChoice.MaterialChoice(Material.AIR));
                        main.heartrecipe.setIngredient('i', i != null ? new RecipeChoice.ExactChoice(i) : new RecipeChoice.MaterialChoice(Material.AIR));
                        main.heartrecipe.setIngredient('j', j != null ? new RecipeChoice.ExactChoice(j) : new RecipeChoice.MaterialChoice(Material.AIR));
                        if (a != null) {main.config.set("CRKeys.a", a.toString());}
                        if (b != null) {main.config.set("CRKeys.b", b.toString());}
                        if (c != null) {main.config.set("CRKeys.c", c.toString());}
                        if (d != null) {main.config.set("CRKeys.d", d.toString());}
                        if (f != null) {main.config.set("CRKeys.f", f.toString());}
                        if (g != null) {main.config.set("CRKeys.g", g.toString());}
                        if (h != null) {main.config.set("CRKeys.h", h.toString());}
                        if (i != null) {main.config.set("CRKeys.i", i.toString());}
                        if (j != null) {main.config.set("CRKeys.j", j.toString());}


                        main.recipechangetoggle = true;
                        main.heartrecipe.setCategory(CraftingBookCategory.MISC);
                        Bukkit.addRecipe(main.heartrecipe);
                        try {
                            main.config.save("configuration.yml");
                        } catch (IOException x) {
                            throw new RuntimeException(x);
                        }

                        e.getWhoClicked().closeInventory();

                        e.getWhoClicked().sendMessage(prefix + " Recipe saved! Due to Spigot API restrictions, a server restart is required to enable the recipe...");

                        e.setCancelled(true);
                        break;
                }








            } else if (e.getRawSlot() >= 0 && e.getRawSlot() <=43){
                e.setCancelled(true);
            }

        }


    }



}
