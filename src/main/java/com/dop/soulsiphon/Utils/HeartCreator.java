package com.dop.soulsiphon.Utils;

import com.dop.soulsiphon.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.recipe.CraftingBookCategory;

import javax.swing.plaf.basic.BasicBorders;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class HeartCreator {


    private static Main main;

    public HeartCreator(Main main) {

        this.main = main;

    }

    private Material a;
    private Material b;
    private Material c;
    private Material d;
    private Material f;
    private Material g;
    private Material h;
    private Material i;
    private Material j;


    public void HeartGen() throws IOException {


        if (main.config.getBoolean("HeartsEnabled") && main.getConfig().getBoolean("HeartRecipeEnabled")) {

            //Create heart item.
            main.heart = new ItemStack(Material.NETHER_STAR);
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
            if (!main.config.getBoolean("HBRB")) {
                main.heartrecipe.shape("cac", "aba", "cac");
                main.heartrecipe.setIngredient('b', Material.TOTEM_OF_UNDYING);
                main.heartrecipe.setIngredient('a', Material.DIAMOND);
                main.heartrecipe.setIngredient('c', Material.NETHERITE_INGOT);
                main.config.set("CRKeys.a", Material.TOTEM_OF_UNDYING.toString());
                main.config.set("CRKeys.b", Material.DIAMOND.toString());
                main.config.set("CRKeys.c", Material.NETHERITE_INGOT.toString());
                main.config.set("HBR", true);
                Bukkit.addRecipe(main.heartrecipe);
                try {
                    main.config.save(new File(main.getDataFolder(), "configuration.yml"));
                    System.out.println("Configuration saved successfully!");
                } catch (IOException e) {
                    System.out.println("An error occurred while saving the configuration!");
                    e.printStackTrace();
                }

            } else {
                if (Material.getMaterial(main.config.getString("CRKeys.a")) != null) {a = (Material) Material.getMaterial(main.config.getString("CRKeysB.a"));}
                if (Material.getMaterial(main.config.getString("CRKeys.a")) != null) {b = (Material) Material.getMaterial(main.config.getString("CRKeysB.a"));}
                if (Material.getMaterial(main.config.getString("CRKeys.a")) != null) {c = (Material) Material.getMaterial(main.config.getString("CRKeysB.a"));}
                if (Material.getMaterial(main.config.getString("CRKeys.a")) != null) {d = (Material) Material.getMaterial(main.config.getString("CRKeysB.a"));}
                if (Material.getMaterial(main.config.getString("CRKeys.a")) != null) {f = (Material) Material.getMaterial(main.config.getString("CRKeysB.a"));}
                if (Material.getMaterial(main.config.getString("CRKeys.a")) != null) {g = (Material) Material.getMaterial(main.config.getString("CRKeysB.a"));}
                if (Material.getMaterial(main.config.getString("CRKeys.a")) != null) {h = (Material) Material.getMaterial(main.config.getString("CRKeysB.a"));}
                if (Material.getMaterial(main.config.getString("CRKeys.a")) != null) {i = (Material) Material.getMaterial(main.config.getString("CRKeysB.a"));}
                if (Material.getMaterial(main.config.getString("CRKeys.a")) != null) {j = (Material) Material.getMaterial(main.config.getString("CRKeysB.a"));}

                //chatgpt wrote this and I have no idea what it does, but it should work.

                String[] keys = {"a", "b", "c", "d", "f", "g", "h", "i", "j"};
                StringBuilder abcBuilder = new StringBuilder();
                StringBuilder dfgBuilder = new StringBuilder();
                StringBuilder hijBuilder = new StringBuilder();

                // Populate the groups based on material presence
                for (String key : keys) {
                    String materialName = main.config.getString("CRKeys." + key);
                    if (materialName != null && materialName.equals("air")) {
                        if ("abc".contains(key)) {
                            abcBuilder.append(" ");
                        }
                        if ("dfg".contains(key)) {
                            dfgBuilder.append(" ");
                        }
                        if ("hij".contains(key)) {
                            hijBuilder.append(" ");
                        }
                    } else {
                        if ("abc".contains(key)) {
                            abcBuilder.append(key);
                        }
                        if ("dfg".contains(key)) {
                            dfgBuilder.append(key);
                        }
                        if ("hij".contains(key)) {
                            hijBuilder.append(key);
                        }
                    }
                }

                String abc = abcBuilder.toString().trim();
                String dfg = dfgBuilder.toString().trim();
                String hij = hijBuilder.toString().trim();

                // Use the variables abc, dfg, hij in the shape
                main.heartrecipe.shape(abc, dfg, hij);

                if (abc.contains("a")) {main.heartrecipe.setIngredient('a', new RecipeChoice.ExactChoice(new ItemStack(a)));}
                if (abc.contains("b")) {main.heartrecipe.setIngredient('b', new RecipeChoice.ExactChoice(new ItemStack(b)));}
                if (abc.contains("c")) {main.heartrecipe.setIngredient('c', new RecipeChoice.ExactChoice(new ItemStack(c)));}
                if (dfg.contains("d")) {main.heartrecipe.setIngredient('d', new RecipeChoice.ExactChoice(new ItemStack(d)));}
                if (dfg.contains("f")) {main.heartrecipe.setIngredient('f', new RecipeChoice.ExactChoice(new ItemStack(f)));}
                if (dfg.contains("g")) {main.heartrecipe.setIngredient('g', new RecipeChoice.ExactChoice(new ItemStack(g)));}
                if (hij.contains("h")) {main.heartrecipe.setIngredient('h', new RecipeChoice.ExactChoice(new ItemStack(h)));}
                if (hij.contains("i")) {main.heartrecipe.setIngredient('i', new RecipeChoice.ExactChoice(new ItemStack(i)));}
                if (hij.contains("j")) {main.heartrecipe.setIngredient('j', new RecipeChoice.ExactChoice(new ItemStack(j)));}
                main.heartrecipe.setCategory(CraftingBookCategory.MISC);
                Bukkit.addRecipe(main.heartrecipe);




            }


        }


    }

}

