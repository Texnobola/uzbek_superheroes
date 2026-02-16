package uz.vavi.superheroes.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import uz.vavi.superheroes.Superheroes;
import uz.vavi.superheroes.entity.ModEntities;
import uz.vavi.superheroes.entity.custom.AVAEntity;

/**
 * Event handlers for Uzbek Superheroes mod.
 * Handles entity attribute registration and other mod events.
 *
 * @author Verfex
 * @since 1.0.0
 */
@Mod.EventBusSubscriber(modid = Superheroes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventHandlers {

    /**
     * Register entity attributes when the entity attribute creation event fires.
     * This allows entities to have proper health, damage, movement speed, etc.
     *
     * Attributes must be registered here so the game knows what attributes
     * each entity type supports.
     *
     * @param event The entity attribute creation event
     */
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        // Register AVA entity attributes
        // This allows AVA to have health (20 = 10 hearts), movement speed, flying speed, etc.
        event.put(ModEntities.AVA.get(), AVAEntity.createAttributes());
    }

}

