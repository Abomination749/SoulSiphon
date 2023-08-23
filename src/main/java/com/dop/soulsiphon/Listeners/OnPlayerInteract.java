package com.dop.soulsiphon.Listeners;

import com.dop.soulsiphon.Main;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

public class OnPlayerInteract implements Listener {

    private Main main;

    public OnPlayerInteract(Main main) {

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
    public void Oninteract(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            e.getPlayer().getInventory().getItemInMainHand();
            if (e.getPlayer().getInventory().getItemInMainHand().isSimilar(main.heart)) {
                Player player = e.getPlayer();
                health.put(player.getUniqueId(), health.get(player.getUniqueId()) + 2);
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health.get(player.getUniqueId()));
                player.setHealth(health.get(player.getUniqueId()));

                int amount = player.getInventory().getItemInMainHand().getAmount();
                if (amount > 1) {
                    player.getInventory().getItemInMainHand().setAmount(amount - 1);
                } else {
                    player.getInventory().removeItem(player.getInventory().getItemInMainHand());
                }
            }
        }


    }

}
