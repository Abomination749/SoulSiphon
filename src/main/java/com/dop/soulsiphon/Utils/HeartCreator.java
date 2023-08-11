package com.dop.soulsiphon.Utils;

import com.dop.soulsiphon.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HeartCreator {


    private static Main main;

    public HeartCreator(Main main) {

        this.main = main;

    }

    public void HeartGen() {


        if (main.config.getBoolean("HeartsEnabled")) {

            //Create heart item.
            if (main.config.getBoolean("HeartsAsHeads")) {
                main.heart = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta heartskullmeta = (SkullMeta) main.heart.getItemMeta();
                if (main.config.getBoolean("HeartsFromURL")) {




                } else {
                    UUIDfetcher UUIDfetcher = new UUIDfetcher(main);
                    UUID skinUUID = UUID.fromString(UUIDfetcher.getUUID(main.config.getString("HeartSource")));
                    if (skinUUID != null) {

                        OfflinePlayer headskinp = Bukkit.getOfflinePlayer(skinUUID);
                        assert heartskullmeta != null;
                        heartskullmeta.setOwningPlayer(headskinp);

                    }
                }
            } else {
                main.heart = new ItemStack(Material.NETHER_STAR);
            }
            ItemMeta heartmeta = main.heart.getItemMeta();
            heartmeta.setDisplayName(ChatColor.GOLD + main.config.getString("HeartName"));
            List<String> Lorelist = new ArrayList<String>();
            if (main.config.getStringList("HeartLore").get(0) != null) {
                Lorelist.add(main.config.getStringList("HeartLore").get(0));
            }
            if (main.config.getStringList("HeartLore").get(1) != null) {
                Lorelist.add(main.config.getStringList("HeartLore").get(1));
            }
            if (main.config.getStringList("HeartLore").get(2) != null) {
                Lorelist.add(main.config.getStringList("HeartLore").get(2));
            }
            if (Lorelist.get(0) != null) {
                heartmeta.setLore(Lorelist);
            }
            main.heart.setItemMeta(heartmeta);
            main.heartrecipe = new ShapedRecipe(main.heart);
            if (main.config.getBoolean("HeartsEnabled") && !main.config.getBoolean("HBR")) {
                main.heartrecipe.shape("aaa", "aba", "aaa");
                main.heartrecipe.setIngredient('b', Material.TOTEM_OF_UNDYING);
                main.heartrecipe.setIngredient('a', Material.DIAMOND);
               // main.config.set("CRKeys.a", Material.TOTEM_OF_UNDYING.toString());
               // main.config.set("CRKeys.b", Material.DIAMOND.toString());
                main.config.set("HBR", true);
                main.recipechangetoggle = false;
                main.saveConfig();


            } else {

                if (main.recipechangetoggle) {

                    ItemStack a = (ItemStack) main.config.get("CRKeys.a");
                    ItemStack b = (ItemStack) main.config.get("CRKeys.b");
                    ItemStack c = (ItemStack) main.config.get("CRKeys.c");
                    ItemStack d = (ItemStack) main.config.get("CRKeys.d");
                    ItemStack f = (ItemStack) main.config.get("CRKeys.f");
                    ItemStack g = (ItemStack) main.config.get("CRKeys.g");
                    ItemStack h = (ItemStack) main.config.get("CRKeys.h");
                    ItemStack i = (ItemStack) main.config.get("CRKeys.i");
                    ItemStack j = (ItemStack) main.config.get("CRKeys.j");
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




                }

            }


        }


    }

}
