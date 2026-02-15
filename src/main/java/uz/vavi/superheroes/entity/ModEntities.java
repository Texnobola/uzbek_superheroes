package uz.vavi.superheroes.entity;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.entity.EntityType;
import uz.vavi.superheroes.Superheroes;

/**
 * Entity registry for Uzbek Superheroes mod.
 * All mod entities (mobs, NPCs, etc.) are registered here using DeferredRegister pattern.
 *
 * @author Verfex
 * @since 1.0.0
 */
@Mod.EventBusSubscriber(modid = Superheroes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {

    /**
     * DeferredRegister for entities. Links to ForgeRegistries.ENTITY_TYPES with mod ID.
     */
    public static final DeferredRegister<EntityType<?>> ENTITIES =
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Superheroes.MOD_ID);

    // ==================== CUSTOM ENTITIES ====================
    // Entity registrations will be added here in future phases

    /**
     * Registers the entity registry with the mod event bus.
     * Must be called during mod initialization.
     *
     * @param eventBus The mod event bus
     */
    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }

}

