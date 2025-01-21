package org.lepigslayer.cSDay.Wand;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.lepigslayer.cSDay.CSDay;

public class WandEventHandler implements Listener {

    private static float[] pitches = {
            1.0f,
            1.5f,
            2.0f
    };

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(!Wand.isHolding(e.getPlayer())) return;
        if(e.getAction() != Action.RIGHT_CLICK_AIR) return;
        Vector start = e.getPlayer().getLocation().toVector().add(new Vector(0,e.getPlayer().getEyeHeight(),0));
        Vector direction = e.getPlayer().getLocation().getDirection();

        WandRaycast raycast = new WandRaycast(e.getPlayer().getWorld(),start,direction,e.getPlayer());
        WandEvent event = new WandEvent(e.getPlayer(),raycast.foundBlock, raycast.foundEntity);

        CSDay.compiler.getWrapper().triggerWand(event);
    }

    @EventHandler
    public void attack(EntityDamageByEntityEvent e) {
        if(!(e.getDamager() instanceof Player)) return;
        Player holder = (Player) e.getDamager();

        if(!Wand.isHolding(holder)) return;

        CSDay.compiler.getWrapper().attackWithWand(holder,e.getEntity());
    }

    @EventHandler
    public void equip(PlayerItemHeldEvent e) {
        ItemStack[] items = e.getPlayer().getInventory().getContents();
        ItemStack newItem = items[e.getNewSlot()];
        ItemStack oldItem = items[e.getPreviousSlot()];
        if(Wand.isWand(newItem)) CSDay.compiler.getWrapper().equipWand(e.getPlayer());
        if(Wand.isWand(oldItem)) CSDay.compiler.getWrapper().unequipWand(e.getPlayer());
    }

    @EventHandler
    public void drop(PlayerDropItemEvent e){
        if(Wand.isWand(e.getItemDrop().getItemStack())){
            e.setCancelled(true);
            int oldAmount = Wand.getStaticPower();
            int change = e.getPlayer().isSneaking() ? -1 : 1;
            int newAmount = Wand.changePower(change);
            if(newAmount==oldAmount) return;
            String message = "Power Level: "+newAmount;
            if(newAmount==1){
                e.getPlayer().playSound(e.getPlayer(), Sound.ENTITY_CAT_AMBIENT,2f,0.1f);
                e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy("§7"+message));
            }else if(newAmount==5){
                e.getPlayer().playSound(e.getPlayer(), Sound.ENTITY_ENDER_DRAGON_GROWL,2f,2f);
                e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy("§c§l"+message));
            }else{
                e.getPlayer().playSound(e.getPlayer(),Sound.BLOCK_NOTE_BLOCK_HARP,2f,pitches[newAmount-2]);
                e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy("§a"+message));
            }
        }
    }
}
