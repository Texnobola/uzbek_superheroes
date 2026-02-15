package uz.vavi.superheroes.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import uz.vavi.superheroes.Superheroes;
import uz.vavi.superheroes.item.ModItems;

/**
 * Block registry for Uzbek Superheroes mod.
 * All mod blocks are registered here using DeferredRegister pattern.
 * BlockItems are automatically registered via the registerBlockItem helper method.
 *
 * @author Verfex
 * @since 1.0.0
 */
@Mod.EventBusSubscriber(modid = Superheroes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {

    /**
     * DeferredRegister for blocks. Links to ForgeRegistries.BLOCKS with mod ID.
     */
    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(ForgeRegistries.BLOCKS, Superheroes.MOD_ID);

    // ==================== TECH/CRAFTING BLOCKS ====================

    /**
     * Tech Workbench - crafting station for nano-suit components.
     * Used for assembling superhero equipment and upgrades.
     */
    public static final RegistryObject<Block> TECH_WORKBENCH =
        registerBlock("tech_workbench",
            () -> new Block(BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.IRON_BLOCK)
                .sound(SoundType.METAL)
                .strength(5.0f)
            )
        );

    // ==================== HELPER METHODS ====================

    /**
     * Helper method to register a block and its corresponding BlockItem.
     * Automatically creates and registers a BlockItem for the given block.
     *
     * @param name The block's registry name
     * @param block The block supplier
     * @return RegistryObject wrapping the registered block
     */
    private static RegistryObject<Block> registerBlock(String name, net.minecraftforge.registries.DeferredRegister.DeferredSupplier<? extends Block> block) {
        RegistryObject<Block> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    /**
     * Helper method to register a BlockItem for a block.
     * BlockItems allow blocks to be placed from player inventory.
     *
     * @param name The item's registry name
     * @param block The block's RegistryObject
     */
    private static void registerBlockItem(String name, RegistryObject<Block> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

}

