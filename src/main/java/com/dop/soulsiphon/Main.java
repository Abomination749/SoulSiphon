package com.dop.soulsiphon;

import com.dop.soulsiphon.Bstats.Metrics;
import com.dop.soulsiphon.Commands.Admin.AdminCMD;
import com.dop.soulsiphon.Commands.Admin.AdminTAB;
import com.dop.soulsiphon.Commands.ResetLivesCMD;
import com.dop.soulsiphon.Commands.SetReviveSpawnCMD;
import com.dop.soulsiphon.Commands.Withdraw.WithdrawCMD;
import com.dop.soulsiphon.Commands.Withdraw.WithdrawTAB;
import com.dop.soulsiphon.Listeners.*;
import com.dop.soulsiphon.Utils.HeartCreator;

import org.bukkit.*;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
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
    public ItemStack heart;

    public ShapedRecipe heartrecipe;

    public boolean recipechangetoggle;

    public NamespacedKey key;

     public Configuration config;


    @Override
    public void onEnable() {

        if(!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        saveDefaultConfig();
        config = getConfig();

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

        HeartCreator heartCreator = new HeartCreator(this);
        heartCreator.HeartGen();

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
