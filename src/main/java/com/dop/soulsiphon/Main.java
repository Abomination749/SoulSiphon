package com.dop.soulsiphon;

import com.dop.soulsiphon.Bstats.Metrics;
import com.dop.soulsiphon.Commands.Admin.AdminCMD;
import com.dop.soulsiphon.Commands.Admin.AdminTAB;
import com.dop.soulsiphon.Commands.LeaderboardCMD;
import com.dop.soulsiphon.Commands.ResetLivesCMD;
import com.dop.soulsiphon.Commands.SetReviveSpawnCMD;
import com.dop.soulsiphon.Commands.Withdraw.WithdrawCMD;
import com.dop.soulsiphon.Commands.Withdraw.WithdrawTAB;
import com.dop.soulsiphon.Listeners.*;
import com.dop.soulsiphon.Utils.BeaconCreator;
import com.dop.soulsiphon.Utils.DefaultConfig;
import com.dop.soulsiphon.Utils.HeartCreator;

import com.dop.soulsiphon.Utils.Updater;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.*;

public class Main extends JavaPlugin {

    //Initializing variables

    public Map<UUID, Integer> health = new HashMap();

    public YamlConfiguration config;
    public YamlConfiguration lang;
    public int startingmaxhealth;

    public File configfile = new File(getDataFolder(), "configuration.yml");

    public File langfile = new File(getDataFolder(), "language.yml");
    public File heartslist = new File(getDataFolder(), "heartslist.yml");

    public File banlist = new File(getDataFolder(), "banlist.yml");
    public YamlConfiguration modifyhl = YamlConfiguration.loadConfiguration(heartslist);
    public YamlConfiguration modifybl = YamlConfiguration.loadConfiguration(banlist);
    public String prefix;
    public ItemStack heart;
    public ItemStack beacon;

    public ShapedRecipe heartrecipe;
    public ShapedRecipe beaconrecipe;

    public List<String> chatlist = new ArrayList<>();

    DefaultConfig dc = new DefaultConfig(this);

    public String HeartGUIName;
    public String BeaconGUIName;



    @Override
    public void onEnable() {

        //Create language.yml + defaults
        if (!langfile.exists()) {
            try {
                langfile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            dc.copyDefaults("language.yml", "language.yml");

        }

        //Lang variable
        lang = YamlConfiguration.loadConfiguration(langfile);

        //Auto updater stuff.
        int ID = 112281;
        Updater updater = new Updater(this, ID, this.getFile(), Updater.UpdateType.CHECK_DOWNLOAD, false);

        //Creating data folder. The custom YAML files won't do it automatically.
        if(!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        //Bstats Stuff
        int pluginId = 19424;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));

        //Create heartlist if it does not exist.
        if (!heartslist.exists()) {
            try {
                heartslist.createNewFile();
            } catch (IOException e) {
                System.out.println(prefix + " " + ChatColor.translateAlternateColorCodes('&', lang.getString("UnableToLoad")) + " 'heartslist.yml'!");
                return;
            }
        }

        //Create banlist if it does not exist.
        if (!banlist.exists()) {
            try {
                banlist.createNewFile();
            } catch (IOException e) {
                System.out.println(prefix + " " + ChatColor.translateAlternateColorCodes('&', lang.getString("UnableToLoad")) + " 'banlist.yml'!");
                return;
            }
        }

        //Create configuration.yml and copy the defaults from it.
        if (!configfile.exists()) {
            try {
                configfile.createNewFile();
            } catch (IOException e) {
                System.out.println(prefix + " " + ChatColor.translateAlternateColorCodes('&', lang.getString("UnableToLoad")) + " 'configuration.yml'!");
            }

            //Copy defaults.
            dc.copyDefaults("config.yml", "configuration.yml");

        }



        //Set config variable to a YAML config.
        config = YamlConfiguration.loadConfiguration(configfile);


        //Set up heart recipes
        HeartCreator heartCreator = new HeartCreator(this);
        try {
            heartCreator.HeartGen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Set up beacon recipes
        BeaconCreator beaconCreator = new BeaconCreator(this);
        try {
            beaconCreator.BeaconGen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Get the hearts data from file.
        for (String key : modifyhl.getKeys(false)) {
            health.put(UUID.fromString(key), modifyhl.getInt(key));
        }

        //set the values for the starting health and the prefix.
        startingmaxhealth = 20 + (config.getInt("StartingHeartsModifier") * 2);
        this.prefix = ChatColor.translateAlternateColorCodes('&', lang.getString("Prefix"));

        System.out.println(prefix + " " + ChatColor.translateAlternateColorCodes('&', lang.getString("Startup")));

        //Register all events.
        Bukkit.getPluginManager().registerEvents(new OnPlayerJoin(this), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerDeath(this), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerRespawn(this), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerInteract(this), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerCraft(this), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerClick(this), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerChat(this), this);

        //Register commands
        this.getCommand("withdraw").setExecutor(new WithdrawCMD(this));
        this.getCommand("resetlives").setExecutor(new ResetLivesCMD(this));
        this.getCommand("withdraw").setTabCompleter(new WithdrawTAB(this));
        this.getCommand("setrevivespawn").setExecutor(new SetReviveSpawnCMD(this));
        this.getCommand("soulsiphon").setExecutor(new AdminCMD(this));
        this.getCommand("soulsiphon").setTabCompleter(new AdminTAB(this));
        this.getCommand("ssleaderboard").setExecutor(new LeaderboardCMD(this));

        HeartGUIName = ChatColor.translateAlternateColorCodes('&', lang.getString("HeartGUI"));
        HeartGUIName = ChatColor.translateAlternateColorCodes('&', lang.getString("BeaconGUI"));

    }










    @Override
    public void onDisable() {

        //Save hearts to file
        for (Map.Entry<UUID, Integer> entry : health.entrySet()) {

            UUID key = entry.getKey();
            Integer value = entry.getValue();

            modifyhl.set(key.toString(), value);


        }

        try {
            modifyhl.save(heartslist);
        } catch (IOException ex) {
            System.out.println(prefix + " " + ChatColor.translateAlternateColorCodes('&', lang.getString("UnableToSave")));
            return;
        }

    }
}



