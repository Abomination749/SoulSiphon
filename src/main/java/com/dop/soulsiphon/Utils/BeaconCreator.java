package com.dop.soulsiphon.Utils;

import com.dop.soulsiphon.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.recipe.CraftingBookCategory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeaconCreator {


    private static Main main;

    public BeaconCreator(Main main) {

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

    public void BeaconGen() throws IOException {

        //If beacons are enabled
        if (main.config.getBoolean("BeaconsEnabled")) {

            //Create beacon item.
            main.beacon = new ItemStack(Material.BEACON);
            ItemMeta beaconmeta = main.beacon.getItemMeta();
            beaconmeta.setDisplayName(ChatColor.GOLD + main.config.getString("BeaconName"));
            List<String> Lorelist = new ArrayList<String>();
            if (main.config.getStringList("BeaconLore").get(0) != "") {
                Lorelist.add(main.config.getStringList("BeaconLore").get(0));
            }
            if (main.config.getStringList("BeaconLore").get(1) != "") {
                Lorelist.add(main.config.getStringList("BeaconLore").get(1));
            }
            if (main.config.getStringList("BeaconLore").get(2) != "") {
                Lorelist.add(main.config.getStringList("BeaconLore").get(2));
            }
            if (Lorelist.get(0) != null) {
                beaconmeta.setLore(Lorelist);
            }
            main.beacon.setItemMeta(beaconmeta);

            //Create beacon recipe
            main.beaconrecipe = new ShapedRecipe(main.beacon);
            //If beacon recipe has not been edited
            if (!main.config.getBoolean("HBRC")) {
                main.beaconrecipe.shape("bcb", "cac", "bcb");
                main.beaconrecipe.setIngredient('a', new RecipeChoice.ExactChoice(main.heart));
                main.beaconrecipe.setIngredient('b', Material.TOTEM_OF_UNDYING);
                main.beaconrecipe.setIngredient('c', Material.NETHERITE_INGOT);
                main.config.set("CRKeysB.a", "Heart");
                main.config.set("CRKeysB.b", Material.TOTEM_OF_UNDYING.toString());
                main.config.set("CRKeysB.c", Material.NETHERITE_INGOT.toString());
                main.config.set("HBRC", true);
                Bukkit.addRecipe(main.beaconrecipe);
                try {
                    main.config.save(new File(main.getDataFolder(), "configuration.yml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            //If beacon recipe has been edited.
            } else {
                //Set null materials to air
                if (Material.getMaterial(main.config.getString("CRKeysB.a")) != null) {a = (Material) Material.getMaterial(main.config.getString("CRKeysB.a"));}
                if (Material.getMaterial(main.config.getString("CRKeysB.a")) != null) {b = (Material) Material.getMaterial(main.config.getString("CRKeysB.a"));}
                if (Material.getMaterial(main.config.getString("CRKeysB.a")) != null) {c = (Material) Material.getMaterial(main.config.getString("CRKeysB.a"));}
                if (Material.getMaterial(main.config.getString("CRKeysB.a")) != null) {d = (Material) Material.getMaterial(main.config.getString("CRKeysB.a"));}
                if (Material.getMaterial(main.config.getString("CRKeysB.a")) != null) {f = (Material) Material.getMaterial(main.config.getString("CRKeysB.a"));}
                if (Material.getMaterial(main.config.getString("CRKeysB.a")) != null) {g = (Material) Material.getMaterial(main.config.getString("CRKeysB.a"));}
                if (Material.getMaterial(main.config.getString("CRKeysB.a")) != null) {h = (Material) Material.getMaterial(main.config.getString("CRKeysB.a"));}
                if (Material.getMaterial(main.config.getString("CRKeysB.a")) != null) {i = (Material) Material.getMaterial(main.config.getString("CRKeysB.a"));}
                if (Material.getMaterial(main.config.getString("CRKeysB.a")) != null) {j = (Material) Material.getMaterial(main.config.getString("CRKeysB.a"));}

                //chatgpt wrote this and I have no idea what it does, but it should work.

                String[] keys = {"a", "b", "c", "d", "f", "g", "h", "i", "j"};
                StringBuilder abcBuilder = new StringBuilder();
                StringBuilder dfgBuilder = new StringBuilder();
                StringBuilder hijBuilder = new StringBuilder();

                // Populate the groups based on material presence
                for (String key : keys) {
                    String materialName = main.config.getString("CRKeysB." + key);
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

                //Create recipe shape
                main.beaconrecipe.shape(abc, dfg, hij);

                //Get used recipes
                if (abc.contains("a")) {if (main.config.getString("CRKeysB").equals("Heart")) {main.beaconrecipe.setIngredient('a', new RecipeChoice.ExactChoice(main.heart));} else {main.beaconrecipe.setIngredient('a', new RecipeChoice.ExactChoice(new ItemStack(a)));}}
                if (abc.contains("b")) {if (main.config.getString("CRKeysB").equals("Heart")) {main.beaconrecipe.setIngredient('b', new RecipeChoice.ExactChoice(main.heart));} else {main.beaconrecipe.setIngredient('b', new RecipeChoice.ExactChoice(new ItemStack(b)));}}
                if (abc.contains("c")) {if (main.config.getString("CRKeysB").equals("Heart")) {main.beaconrecipe.setIngredient('c', new RecipeChoice.ExactChoice(main.heart));} else {main.beaconrecipe.setIngredient('c', new RecipeChoice.ExactChoice(new ItemStack(c)));}}
                if (abc.contains("d")) {if (main.config.getString("CRKeysB").equals("Heart")) {main.beaconrecipe.setIngredient('d', new RecipeChoice.ExactChoice(main.heart));} else {main.beaconrecipe.setIngredient('d', new RecipeChoice.ExactChoice(new ItemStack(d)));}}
                if (abc.contains("f")) {if (main.config.getString("CRKeysB").equals("Heart")) {main.beaconrecipe.setIngredient('f', new RecipeChoice.ExactChoice(main.heart));} else {main.beaconrecipe.setIngredient('f', new RecipeChoice.ExactChoice(new ItemStack(f)));}}
                if (abc.contains("g")) {if (main.config.getString("CRKeysB").equals("Heart")) {main.beaconrecipe.setIngredient('g', new RecipeChoice.ExactChoice(main.heart));} else {main.beaconrecipe.setIngredient('g', new RecipeChoice.ExactChoice(new ItemStack(g)));}}
                if (abc.contains("h")) {if (main.config.getString("CRKeysB").equals("Heart")) {main.beaconrecipe.setIngredient('h', new RecipeChoice.ExactChoice(main.heart));} else {main.beaconrecipe.setIngredient('h', new RecipeChoice.ExactChoice(new ItemStack(h)));}}
                if (abc.contains("i")) {if (main.config.getString("CRKeysB").equals("Heart")) {main.beaconrecipe.setIngredient('i', new RecipeChoice.ExactChoice(main.heart));} else {main.beaconrecipe.setIngredient('i', new RecipeChoice.ExactChoice(new ItemStack(i)));}}
                if (abc.contains("j")) {if (main.config.getString("CRKeysB").equals("Heart")) {main.beaconrecipe.setIngredient('j', new RecipeChoice.ExactChoice(main.heart));} else {main.beaconrecipe.setIngredient('j', new RecipeChoice.ExactChoice(new ItemStack(j)));}}
                main.beaconrecipe.setCategory(CraftingBookCategory.MISC);
                Bukkit.addRecipe(main.beaconrecipe);




            }


        }


    }

}

