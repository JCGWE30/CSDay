package org.lepigslayer.cSDay.Wand;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public abstract class WandModifier {
    public Wand wand;

    public abstract void runSetup();
    public abstract void onUse(WandEvent event);
    public abstract void onEquip(Player player);
    public abstract void onUnequip(Player player);
    public abstract void onAttack(Player attacker, Entity victim);
}
