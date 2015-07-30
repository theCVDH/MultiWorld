package net.huntersharpe.multiworld;

import com.google.common.eventbus.Subscribe;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.event.entity.EntitySpawnEvent;
import org.spongepowered.api.world.World;

import java.util.Iterator;

/**
 * Created by user on 7/29/2015.
 */
public class Events {

    @Subscribe
    public void onMobSpawn(EntitySpawnEvent e){
        Entity entity = e.getEntity();
        if(!isMob(entity)){
            return;
        }
        if(!MultiWorld.getInstance().getConfigNode().getNode("multiworld", entity.getWorld().getProperties().getWorldName(), "modifications", "mobs").getValue().equals("false")){
            return;
        }
        World world = entity.getWorld();
        Iterator<Entity> iterator = entity.getWorld().getEntities().iterator();
        while(isMob(iterator.next())){
            world.getEntity(iterator.next().getUniqueId()).get().remove();
            iterator.remove();
        }
        entity.remove();
    }

    @Subscribe
    public void onAnimalSpawn(EntitySpawnEvent e){
        Entity entity = e.getEntity();
        if(!isAnimal(entity)){
            return;
        }
        if(!MultiWorld.getInstance().getConfigNode().getNode("multiworld", entity.getWorld().getProperties().getWorldName(), "modifications", "animals").getValue().equals("false")){
            return;
        }
        World world = entity.getWorld();
        Iterator<Entity> iterator = entity.getWorld().getEntities().iterator();
        while(isAnimal(iterator.next())){
            world.getEntity(iterator.next().getUniqueId()).get().remove();
            iterator.remove();
        }
        entity.remove();
    }

    public boolean isMob(Entity entiy){
        if(entiy.getType().equals(EntityTypes.BAT)
                || entiy.getType().equals(EntityTypes.BAT)
                || entiy.getType().equals(EntityTypes.BLAZE)
                || entiy.getType().equals(EntityTypes.CAVE_SPIDER)
                || entiy.getType().equals(EntityTypes.CREEPER)
                || entiy.getType().equals(EntityTypes.ENDER_DRAGON)
                || entiy.getType().equals(EntityTypes.ENDERMAN)
                || entiy.getType().equals(EntityTypes.ENDERMITE)
                || entiy.getType().equals(EntityTypes.GHAST)
                || entiy.getType().equals(EntityTypes.GIANT)
                || entiy.getType().equals(EntityTypes.GUARDIAN)
                || entiy.getType().equals(EntityTypes.IRON_GOLEM)
                || entiy.getType().equals(EntityTypes.MAGMA_CUBE)
                || entiy.getType().equals(EntityTypes.PIG_ZOMBIE)
                || entiy.getType().equals(EntityTypes.SILVERFISH)
                || entiy.getType().equals(EntityTypes.SKELETON)
                || entiy.getType().equals(EntityTypes.SLIME)
                || entiy.getType().equals(EntityTypes.SPIDER)
                || entiy.getType().equals(EntityTypes.SNOWMAN)
                || entiy.getType().equals(EntityTypes.WITCH)
                || entiy.getType().equals(EntityTypes.WITHER)
                || entiy.getType().equals(EntityTypes.ZOMBIE)){
            return true;
        }else {
            return false;
        }
    }

    public boolean isAnimal(Entity entity){
        if(entity.getType().equals(EntityTypes.CHICKEN)
                || entity.getType().equals(EntityTypes.COW)
                || entity.getType().equals(EntityTypes.HORSE)
                || entity.getType().equals(EntityTypes.MUSHROOM_COW)
                || entity.getType().equals(EntityTypes.OCELOT)
                || entity.getType().equals(EntityTypes.PIG)
                || entity.getType().equals(EntityTypes.RABBIT)
                || entity.getType().equals(EntityTypes.SHEEP)
                || entity.getType().equals(EntityTypes.SQUID)
                || entity.getType().equals(EntityTypes.VILLAGER)
                || entity.getType().equals(EntityTypes.WOLF)){
            return true;
        }else {
            return false;
        }
    }

}
