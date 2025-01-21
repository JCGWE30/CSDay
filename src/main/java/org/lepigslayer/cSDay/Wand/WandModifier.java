package org.lepigslayer.cSDay.Wand;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface WandModifier {
    void runSetup(Wand wand);
    void onUse(WandEvent event);
    void onEquip(Player player);
    void onUnequip(Player player);
    void onAttack(Player attacker, Entity victim);
}
