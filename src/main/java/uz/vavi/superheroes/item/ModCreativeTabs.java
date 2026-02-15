package uz.vavi.superheroes.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import uz.vavi.superheroes.Superheroes;

/**
 * Creative mode tabs registry for Uzbek Superheroes mod.
 * Organizes mod items into custom creative tabs.
 *
 * @author Verfex
 * @since 1.0.0
 */
@Mod.EventBusSubscriber(modid = Superheroes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeTabs {

    /**
     * DeferredRegister for creative mode tabs.
     */
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Superheroes.MOD_ID);

    /**
     * Main Uzbek Superheroes creative tab.
     * Contains all mod items for easy access in creative mode.
     */
    public static final RegistryObject<CreativeModeTab> SUPERHEROES_TAB =
        CREATIVE_TABS.register("superheroes_tab", () ->
            CreativeModeTab.builder()
                .title(Component.literal("Uzbek Superheroes"))
                .withTabsBefore(Registries.CREATIVE_MODE_TAB.getOrThrow(CreativeModeTab.TAB_SEARCH))
                .icon(() -> new ItemStack(ModItems.NANO_SUIT.get()))
                .displayItems((features, output) -> {
                    // Add all mod items to the creative tab
                    output.accept(ModItems.NANO_SUIT.get());
                    output.accept(ModItems.NANO_GAUNTLETS.get());
                })
                .build()
        );

}

