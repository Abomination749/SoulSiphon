package com.dop.soulsiphon;

import com.dop.soulsiphon.Bstats.Metrics;
import com.dop.soulsiphon.Commands.Admin.AdminCMD;
import com.dop.soulsiphon.Commands.Admin.AdminTAB;
import com.dop.soulsiphon.Commands.ResetLivesCMD;
import com.dop.soulsiphon.Commands.SetReviveSpawnCMD;
import com.dop.soulsiphon.Commands.Withdraw.WithdrawCMD;
import com.dop.soulsiphon.Commands.Withdraw.WithdrawTAB;
import com.dop.soulsiphon.Listeners.*;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main extends JavaPlugin {

    public Map<UUID, Integer> health = new HashMap();
    public int startingmaxhealth = 20 + (getConfig().getInt("StartingHeartsModifier") * 2);
    public File heartslist = new File(getDataFolder(), "heartslist.yml");
    public YamlConfiguration modifyhl = YamlConfiguration.loadConfiguration(heartslist);
    public String prefix = getConfig().getString("Prefix");
    public ItemStack heart = new ItemStack(Material.NETHER_STAR);

    public ShapedRecipe heartrecipe;

    public boolean recipechangetoggle;

    public NamespacedKey key;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        key = new NamespacedKey(this, "heartRecipeKey");

        int pluginId = 19424;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));

        System.out.println("[SoulSiphon] Plugin enabled successfully!");
        Bukkit.getPluginManager().registerEvents(new OnPlayerJoin(this), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerDeath(this), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerRespawn(this), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerInteract(this), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerCraft(this), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerClick(this), this);


        String prefix = getConfig().getString("Prefix");
        this.getCommand("withdraw").setExecutor(new WithdrawCMD(this));
        this.getCommand("resetlives").setExecutor(new ResetLivesCMD(this));
        this.getCommand("withdraw").setTabCompleter(new WithdrawTAB(this));
        this.getCommand("setrevivespawn").setExecutor(new SetReviveSpawnCMD(this));
        this.getCommand("soulsiphon").setExecutor(new AdminCMD(this));
        this.getCommand("soulsiphon").setTabCompleter(new AdminTAB(this));
        if (!heartslist.exists()) {
            try {
                heartslist.createNewFile();
            } catch (IOException e) {
                System.out.println("Cannot load file 'heartslist'!");
                return;
            }
        }
        if (getConfig().getBoolean("HeartsEnabled")) {

            //Create heart item.
            ItemMeta heartmeta = heart.getItemMeta();
            heartmeta.setDisplayName(ChatColor.GOLD + getConfig().getString("HeartName"));
            List<String> Lorelist = new ArrayList<String>();
            if (getConfig().getStringList("HeartLore").get(0) != null) {
                Lorelist.add(getConfig().getStringList("HeartLore").get(0));
            }
            if (getConfig().getStringList("HeartLore").get(1) != null) {
                Lorelist.add(getConfig().getStringList("HeartLore").get(1));
            }
            if (getConfig().getStringList("HeartLore").get(2) != null) {
                Lorelist.add(getConfig().getStringList("HeartLore").get(2));
            }
            if (Lorelist.get(0) != null) {
                heartmeta.setLore(Lorelist);
            }
            heart.setItemMeta(heartmeta);
            heartrecipe = new ShapedRecipe(heart);
            if (getConfig().getBoolean("HeartsEnabled") && !getConfig().getBoolean("HRB")) {
                heartrecipe.shape("aaa", "aba", "aaa");
                heartrecipe.setIngredient('b', Material.TOTEM_OF_UNDYING);
                heartrecipe.setIngredient('a', Material.DIAMOND);
                getConfig().set("CRKeys.a", Material.TOTEM_OF_UNDYING.toString());
                getConfig().set("CRKeys.b", Material.DIAMOND.toString());
                getConfig().set("HRB", true);
                saveConfig();
                recipechangetoggle = false;


            } else {

                if (recipechangetoggle) {

                    ItemStack a = (ItemStack) getConfig().get("CRKeys.a");
                    ItemStack b = (ItemStack) getConfig().get("CRKeys.b");
                    ItemStack c = (ItemStack) getConfig().get("CRKeys.c");
                    ItemStack d = (ItemStack) getConfig().get("CRKeys.d");
                    ItemStack f = (ItemStack) getConfig().get("CRKeys.f");
                    ItemStack g = (ItemStack) getConfig().get("CRKeys.g");
                    ItemStack h = (ItemStack) getConfig().get("CRKeys.h");
                    ItemStack i = (ItemStack) getConfig().get("CRKeys.i");
                    ItemStack j = (ItemStack) getConfig().get("CRKeys.j");
                    heartrecipe.shape("abc", "dfg", "hij");

                    heartrecipe.setIngredient('a', a != null ? new RecipeChoice.ExactChoice(a) : new RecipeChoice.MaterialChoice(Material.AIR));
                    heartrecipe.setIngredient('b', b != null ? new RecipeChoice.ExactChoice(b) : new RecipeChoice.MaterialChoice(Material.AIR));
                    heartrecipe.setIngredient('c', c != null ? new RecipeChoice.ExactChoice(c) : new RecipeChoice.MaterialChoice(Material.AIR));
                    heartrecipe.setIngredient('d', d != null ? new RecipeChoice.ExactChoice(d) : new RecipeChoice.MaterialChoice(Material.AIR));
                    heartrecipe.setIngredient('f', f != null ? new RecipeChoice.ExactChoice(f) : new RecipeChoice.MaterialChoice(Material.AIR));
                    heartrecipe.setIngredient('g', g != null ? new RecipeChoice.ExactChoice(g) : new RecipeChoice.MaterialChoice(Material.AIR));
                    heartrecipe.setIngredient('h', h != null ? new RecipeChoice.ExactChoice(h) : new RecipeChoice.MaterialChoice(Material.AIR));
                    heartrecipe.setIngredient('i', i != null ? new RecipeChoice.ExactChoice(i) : new RecipeChoice.MaterialChoice(Material.AIR));
                    heartrecipe.setIngredient('j', j != null ? new RecipeChoice.ExactChoice(j) : new RecipeChoice.MaterialChoice(Material.AIR));




                }

            }


        }


    }


    @Override
    public void onDisable() {

        for (Map.Entry<UUID, Integer> entry : health.entrySet()) {

            UUID key = entry.getKey();
            Integer value = entry.getValue();

            modifyhl.set(key.toString(), value);


        }

        try {
            modifyhl.save(heartslist);
        } catch (IOException ex) {
            System.out.println(prefix + " An issue occurred with saving heartslist.yml!");
            return;
        }

    }
}
