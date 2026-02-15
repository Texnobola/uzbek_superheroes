package uz.vavi.superheroes.entity;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import uz.vavi.superheroes.Superheroes;
import uz.vavi.superheroes.entity.custom.AVAEntity;

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

    /**
     * AVA Entity - AI companion drone for the player.
     * A small flying entity with AI behaviors and animation support.
     */
    public static final RegistryObject<EntityType<AVAEntity>> AVA = ENTITIES.register("ava",
        () -> EntityType.Builder.of(AVAEntity::new, MobCategory.CREATURE)
            .sized(0.6f, 1.8f)
            .build("ava")
    );

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

