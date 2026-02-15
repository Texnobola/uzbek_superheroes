package uz.vavi.superheroes.item;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.item.Item;
import uz.vavi.superheroes.Superheroes;

/**
 * Item registry for Uzbek Superheroes mod.
 * All mod items are registered here using DeferredRegister pattern.
 *
 * @author Verfex
 * @since 1.0.0
 */
@Mod.EventBusSubscriber(modid = Superheroes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {

    /**
     * DeferredRegister for items. Links to ForgeRegistries.ITEMS with mod ID.
     */
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, Superheroes.MOD_ID);

    // ==================== NANO-SUIT ITEMS ====================

    /**
     * Nano-Suit chest armor component.
     * Basic item for now, will be converted to armor when ArmorItem is implemented.
     */
    public static final RegistryObject<Item> NANO_SUIT = ITEMS.register("nano_suit",
        () -> new Item(new Item.Properties()
            .setNoRepair()
        )
    );

    /**
     * Nano-Suit gauntlets/gloves component.
     * Used for melee combat enhancement.
     */
    public static final RegistryObject<Item> NANO_GAUNTLETS = ITEMS.register("nano_gauntlets",
        () -> new Item(new Item.Properties()
            .setNoRepair()
        )
    );

}

