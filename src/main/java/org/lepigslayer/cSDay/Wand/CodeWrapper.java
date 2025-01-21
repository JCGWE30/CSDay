package org.lepigslayer.cSDay.Wand;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CodeWrapper {
    private WandModifier modifier;
    private Wand wand;

    public CodeWrapper(Object obj) {
        try{
            modifier = (WandModifier) obj;
            wand = new Wand();
            modifier.wand = wand;
            modifier.runSetup();
            wand.endSetup();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void triggerWand(WandEvent event) {
        modifier.onUse(event);
    }

    public void equipWand(Player holder) {
        modifier.onEquip(holder);
    }

    public void unequipWand(Player holder) {
        modifier.onUnequip(holder);
    }

    public void attackWithWand(Player attacker, Entity victim){
        modifier.onAttack(attacker, victim);
    }

    public Wand getWand(){
        return wand;
    }
}
