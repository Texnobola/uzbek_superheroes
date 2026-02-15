# PHASE 2: CORE REGISTRIES - DETAILED CODE GUIDE

## Complete Implementation Reference

**Date:** February 16, 2026  
**Phase:** Phase 2 - Core Registries  
**Status:** ✅ Complete  

---

## 📋 File 1: ModCreativeTabs.java

### Full Code:
```java
package uz.vavi.superheroes.item;

import net.minecraft.world.item.CreativeModeTab;
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
```

### Breaking Down the Code:

**DeferredRegister Creation:**
```java
public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
    DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Superheroes.MOD_ID);
```
- Links to `Registries.CREATIVE_MODE_TAB` - the registry for creative tabs
- Uses mod ID "superheroes" for namespace
- Lazy initialization pattern (Forge best practice)

**Tab Registration with Builder:**
```java
CREATIVE_TABS.register("superheroes_tab", () ->
    CreativeModeTab.builder()
        .title(Component.literal("Uzbek Superheroes"))  // Tab display name
        .withTabsBefore(...)                            // Tab positioning
        .icon(() -> new ItemStack(ModItems.NANO_SUIT.get()))  // Tab icon
        .displayItems((features, output) -> {           // Items in tab
            output.accept(ModItems.NANO_SUIT.get());
            output.accept(ModItems.NANO_GAUNTLETS.get());
        })
        .build()
);
```

**Tab Icon:**
```java
.icon(() -> new ItemStack(ModItems.NANO_SUIT.get()))
```
- Shows nano_suit item as the tab icon
- Uses lambda/supplier for lazy loading
- Can be changed to any other item

**Display Items Callback:**
```java
.displayItems((features, output) -> {
    output.accept(ModItems.NANO_SUIT.get());
    output.accept(ModItems.NANO_GAUNTLETS.get());
})
```
- Called when creative tab is opened
- `output.accept()` adds item to tab
- Easy to extend with more items

---

## 📋 File 2: ModBlocks.java

### Full Code:
```java
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
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                .sound(SoundType.METAL)
                .strength(5.0f, 6.0f)
                .requiresCorrectToolForDrops()
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
```

### Breaking Down the Code:

**DeferredRegister Creation:**
```java
public static final DeferredRegister<Block> BLOCKS =
    DeferredRegister.create(ForgeRegistries.BLOCKS, Superheroes.MOD_ID);
```
- Links to `ForgeRegistries.BLOCKS` - the block registry
- Uses mod ID "superheroes" for namespace
- Pattern identical to items registry

**Block Registration with Properties:**
```java
public static final RegistryObject<Block> TECH_WORKBENCH =
    registerBlock("tech_workbench",
        () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
            .sound(SoundType.METAL)
            .strength(5.0f, 6.0f)
            .requiresCorrectToolForDrops()
        )
    );
```

**Block Properties Explained:**
```java
BlockBehaviour.Properties.of(Material.METAL)  // Material type
    .sound(SoundType.METAL)                   // Sound when walked on/broken
    .strength(5.0f, 6.0f)                    // Hardness 5.0, Blast resistance 6.0
    .requiresCorrectToolForDrops()            // Must use tool to get drops
```

- **strength(5.0f, 6.0f)**: 
  - First value (5.0): Mining time (higher = harder)
  - Second value (6.0): Blast resistance
  
- **requiresCorrectToolForDrops()**: 
  - Block drops items only if mined with correct tool
  - Tech_workbench needs pickaxe (METAL material)

**Helper Method - Register Block:**
```java
private static RegistryObject<Block> registerBlock(String name, 
        net.minecraftforge.registries.DeferredRegister.DeferredSupplier<? extends Block> block) {
    RegistryObject<Block> toReturn = BLOCKS.register(name, block);
    registerBlockItem(name, toReturn);  // Auto-creates BlockItem
    return toReturn;
}
```
- Reduces code duplication
- Automatically creates and registers BlockItem
- Makes adding new blocks much easier

**Helper Method - Register BlockItem:**
```java
private static void registerBlockItem(String name, RegistryObject<Block> block) {
    ModItems.ITEMS.register(name, 
        () -> new BlockItem(block.get(), new Item.Properties()));
}
```
- Creates BlockItem (allows block to be placed from inventory)
- Registers to ModItems.ITEMS (same registry as regular items)
- Makes blocks appear in creative tab automatically

### How to Add More Blocks:

In the future, adding a new block is as simple as:
```java
// Example: Add an energy charger block
public static final RegistryObject<Block> ENERGY_CHARGER =
    registerBlock("energy_charger",
        () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
            .sound(SoundType.METAL)
            .strength(4.0f, 5.0f)
            .requiresCorrectToolForDrops()
        )
    );
```

The helper methods automatically:
- ✅ Register the block
- ✅ Create a BlockItem
- ✅ Register the BlockItem
- ✅ Add to creative tab

---

## 📋 File 3: Superheroes.java (Updated)

### Updated Constructor:
```java
public Superheroes() {
    // Event Bus - bu modingizning "nerv tizimi"
    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    // 1. GeckoLibni ishga tushiramiz (Eng muhim qism!)
    GeckoLib.initialize();

    // 2. Register all items with the mod event bus
    ModItems.ITEMS.register(modEventBus);
    
    // 3. Register blocks with the mod event bus
    ModBlocks.BLOCKS.register(modEventBus);
    
    // 4. Register creative tabs with the mod event bus
    ModCreativeTabs.CREATIVE_TABS.register(modEventBus);

    // 5. Register mod event handlers
    MinecraftForge.EVENT_BUS.register(this);

    LOGGER.info("Uzbek Superheroes modi muvaffaqiyatli yuklandi!");
}
```

### New Imports:
```java
import uz.vavi.superheroes.item.ModCreativeTabs;
import uz.vavi.superheroes.block.ModBlocks;
```

### Registration Order (Important!):
1. **GeckoLib.initialize()** - Must be first
2. **ModItems.ITEMS.register()** - Items registry
3. **ModBlocks.BLOCKS.register()** - Blocks registry
4. **ModCreativeTabs.CREATIVE_TABS.register()** - Creative tabs
5. **MinecraftForge.EVENT_BUS.register()** - Event handlers

This order ensures all dependencies are registered before use.

---

## 🎯 Usage Examples

### Adding an Item to the Creative Tab:

In ModCreativeTabs.java, add to displayItems:
```java
.displayItems((features, output) -> {
    output.accept(ModItems.NANO_SUIT.get());
    output.accept(ModItems.NANO_GAUNTLETS.get());
    output.accept(ModItems.NEW_ITEM.get());  // ← Add new items here
})
```

### Adding a New Block:

In ModBlocks.java, add after TECH_WORKBENCH:
```java
public static final RegistryObject<Block> ENERGY_CHARGER =
    registerBlock("energy_charger",
        () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
            .sound(SoundType.METAL)
            .strength(4.0f, 5.0f)
            .requiresCorrectToolForDrops()
        )
    );
```

That's it! The helper methods handle everything else.

### Custom Block Class:

If you need custom behavior, create a class:
```java
// CustomTechBlock.java
public class CustomTechBlock extends Block {
    public CustomTechBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
    
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, 
            BlockState newState, boolean isMoving) {
        // Custom behavior on block removal
    }
}
```

Then register it:
```java
public static final RegistryObject<Block> CUSTOM_BLOCK =
    registerBlock("custom_block",
        () -> new CustomTechBlock(BlockBehaviour.Properties.of(Material.METAL)
            .sound(SoundType.METAL)
            .strength(5.0f, 6.0f)
        )
    );
```

---

## 🧪 Testing the Implementation

### In Creative Mode:
1. Open creative inventory
2. Look for "Uzbek Superheroes" tab (after search tab)
3. Should see: nano_suit, nano_gauntlets, tech_workbench

### Testing Block Placement:
```bash
# Give yourself the block
/give @s superheroes:tech_workbench

# Try breaking it with different tools
# Should require pickaxe for drops
```

### Testing Tool Requirements:
- Wooden pickaxe: ✅ Breaks block, drops item
- Stone pickaxe: ✅ Breaks block, drops item
- Bare hand: ✅ Breaks block, NO drops (correct!)

---

## 📊 Registry Summary

### Items Registry (ModItems.ITEMS):
```
superheroes:nano_suit
superheroes:nano_gauntlets
superheroes:tech_workbench (auto-generated from block)
```

### Blocks Registry (ModBlocks.BLOCKS):
```
superheroes:tech_workbench
```

### Creative Tabs Registry (ModCreativeTabs.CREATIVE_TABS):
```
superheroes:superheroes_tab
├─ nano_suit
├─ nano_gauntlets
└─ tech_workbench
```

---

## ✨ Design Benefits

### Helper Methods Benefit:
```
WITHOUT helper methods (old way):
- Register block
- Create BlockItem class or inline
- Register BlockItem
- Repeat for each block

WITH helper methods (current way):
- Call registerBlock() with name and properties
- Everything else automatic!
- Clean, DRY code
- Easy to add more blocks
```

### Example: Adding 5 More Blocks

**Old way:** ~50 lines of repetitive code  
**New way:** 5 simple registerBlock() calls

This scales really well for a mod with many blocks!

---

## 🚀 Next Steps

### Phase 3 Will Add:
- Entity registry (ModEntities.java)
- Custom entity classes (AVAEntity, etc.)
- GeckoLib animation models
- Entity renderers

See `DEVELOPMENT_PLAN.md` Phase 3 for details!

---

**Phase 2 Code Guide Complete**  
*Professional Minecraft Forge 1.20.1 Modding*  
*February 16, 2026*

