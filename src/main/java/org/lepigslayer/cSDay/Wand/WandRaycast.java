package org.lepigslayer.cSDay.Wand;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class WandRaycast {
    protected Entity foundEntity = null;
    protected Block foundBlock = null;

    public WandRaycast(World world, Vector start, Vector direction, Player user){
        Vector currentVector = start;
        for(int i = 0;i<1000;i++){
            if(foundBlock!=null) return;
            Location loc = currentVector.toLocation(world);
            foundEntity = findPlayer(loc, user);
            foundBlock = findBlock(loc);
            currentVector = currentVector.add(direction.normalize().multiply(0.5));
        }
    }

    private Entity findPlayer(Location loc, Player user){
        if(foundEntity != null) return foundEntity;
        for(Entity entity : loc.getWorld().getNearbyEntities(loc, 0.5, 0.5, 0.5)){
            if(entity.getUniqueId()==user.getUniqueId()) continue;
            return entity;
        }
        return null;
    }

    private Block findBlock(Location loc){
        if(foundBlock != null) return foundBlock;
        Block b = loc.getBlock();
        if(b.getType().isAir()) return null;
        return b;
    }
}
