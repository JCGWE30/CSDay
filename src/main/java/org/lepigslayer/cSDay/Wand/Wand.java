package org.lepigslayer.cSDay.Wand;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.lepigslayer.cSDay.WandModificationException;

public class Wand {
    private static Wand instance;

    private String name = "Cool wand";
    private Material material = Material.STICK;
    private boolean glowing = true;
    private ChatColor color = ChatColor.GOLD;
    private boolean inSetup = true;

    private int power = 1;

    public Wand(){
        instance = this;
    }

    public void setName(String name){
        if(!inSetup) throwSetupError();
        this.name = name;
    }

    public void setMaterial(Material material){
        if(!inSetup) throwSetupError();
        this.material = material;
    }

    public void setGlowing(boolean glowing){
        if(!inSetup) throwSetupError();
        this.glowing = glowing;
    }

    public void setColor(ChatColor color){
        if(!inSetup) throwSetupError();
        this.color = color;
    }


    public int getPower(){
        return this.power;
    }

    protected static int changePower(int amount){
        int power = instance.power+amount;
        if(power>5)
            power = 5;
        if(power<1)
            power = 1;
        instance.power = power;
        return power;
    }

    protected static int getStaticPower(){
        return instance.power;
    }

    protected void endSetup(){
        if(!inSetup) throwSetupError();
        this.inSetup = false;
    }

    protected ItemStack assemble(){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§"+color.getChar()+name);
        if(glowing)
            meta.addEnchant(Enchantment.LOYALTY,1,true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }

    private void throwSetupError(){
        throw new WandModificationException("A value of the wand was modified after the setup ended");
    }

    public static boolean isHolding(Player player){
        ItemStack item = player.getInventory().getItemInMainHand();
        return isWand(item);
    }

    public static boolean isWand(ItemStack item){
        if(item==null) return false;
        if(!item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        String itemName = meta.getDisplayName();
        String modifiedName = "§"+instance.color.getChar()+instance.name;
        return itemName.equals(modifiedName);
    }
}
