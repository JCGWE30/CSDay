package org.lepigslayer.cSDay.Wand;

import org.bukkit.block.Block;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Optional;

public class WandEvent {
    private Player user;
    private Block targetedBlock;
    private Entity targetedEntity;

    public WandEvent(Player wandUser, Block targetedBlock, Entity targetedEntity){
        this.targetedBlock = targetedBlock;
        this.targetedEntity = targetedEntity;
        this.user = wandUser;
    }

    public Player getUser() {
        return user;
    }

    public Block getTargetedBlock() {
        return targetedBlock;
    }

    public Entity getTargetedEntity() {
        return targetedEntity;
    }

    public boolean hasEntity(){
        return targetedEntity != null;
    }
}
