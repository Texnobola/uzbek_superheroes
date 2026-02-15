# Uzbek Superheroes - Development Plan

## Phase 1: Project Configuration & Setup ⚙️

### 1.1 Fix Gradle Properties
**File:** `gradle.properties`

Replace template values with actual project information:
```gradle
mod_id=superheroes
mod_name=Uzbek Superheroes
mod_license=MIT  # or your preferred license
mod_group_id=uz.vavi.superheroes
mod_authors=Verfex
mod_description=A Minecraft mod featuring Uzbek-inspired superheroes with nano-suits, AI companions, and advanced combat abilities.
mod_version=1.0.0
```

### 1.2 Create Project Structure
Create the following package structure in `src/main/java/uz/vavi/superheroes/`:

```
uz/vavi/superheroes/
├── Superheroes.java (existing)
├── item/
│   ├── ModItems.java           # Item registry
│   ├── custom/
│   │   ├── NanoSuitItem.java   # Wearable nano-suit
│   │   ├── ClawsItem.java      # Superhero claws
│   │   └── JetpackItem.java    # Jetpack accessory
│   └── armor/
│       └── NanoSuitArmorItem.java
├── block/
│   ├── ModBlocks.java          # Block registry
│   ├── custom/
│   │   ├── TechWorkbench.java  # Crafting block
│   │   └── ChargerBlock.java   # Energy charger
│   └── entity/
│       └── TechWorkbenchEntity.java
├── entity/
│   ├── ModEntities.java        # Entity registry
│   ├── custom/
│   │   ├── AVAEntity.java      # AI companion (with GeckoLib)
│   │   └── SuperheroNPCEntity.java
│   └── model/
│       ├── AVAModel.java       # GeckoLib model
│       └── SuperheroModel.java
├── client/
│   ├── ModEventHandlers.java
│   ├── renderer/
│   │   ├── AVARenderer.java    # Custom entity renderer
│   │   └── NanoSuitRenderer.java
│   ├── animation/
│   │   ├── AVAAnimation.java   # GeckoLib animations
│   │   └── CombatAnimation.java
│   └── model/
│       ├── ARmorModel.java     # Armor model
│       └── AccessoryModel.java
├── event/
│   ├── ModCommonEvents.java    # Server-side events
│   └── ModClientEvents.java    # Client-side events
├── network/
│   ├── NetworkHandler.java     # Packet setup
│   ├── packet/
│   │   ├── PowerActivatePacket.java
│   │   └── AnimationSyncPacket.java
│   └── handler/
│       └── PacketHandlers.java
├── ability/
│   ├── Ability.java            # Base ability class
│   ├── SuperpowerRegistry.java
│   └── powers/
│       ├── SuperStrengthAbility.java
│       ├── TeleportAbility.java
│       └── LaserAbility.java
├── config/
│   ├── ModConfig.java          # Configuration manager
│   ├── ServerConfig.java
│   └── ClientConfig.java
├── util/
│   ├── Constants.java          # Game constants
│   ├── TextureUtils.java
│   └── AnimationHelper.java
└── data/
    └── ModDataManager.java     # Player data (nano-suit level, etc.)
```

### 1.3 Create CreativeTab
File: `src/main/java/uz/vavi/superheroes/item/ModCreativeTabs.java`

```java
public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = 
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Superheroes.MOD_ID);
    
    public static final RegistryObject<CreativeModeTab> SUPERHEROES_TAB = 
        CREATIVE_TABS.register("superheroes_tab", 
            () -> CreativeModeTab.builder()
                .title(Component.literal("Uzbek Superheroes"))
                .withTabsBefore(CreativeModeTabs.COMBAT)
                .icon(() -> new ItemStack(ModItems.NANO_SUIT.get()))
                .displayItems((features, output) -> {
                    // Add items to creative tab
                })
                .build());
}
```

### 1.4 Create ItemRegistry
File: `src/main/java/uz/vavi/superheroes/item/ModItems.java`

```java
public class ModItems {
    public static final DeferredRegister<Item> ITEMS = 
        DeferredRegister.create(ForgeRegistries.ITEMS, Superheroes.MOD_ID);
    
    // Nano-suit components
    public static final RegistryObject<Item> NANO_SUIT = ITEMS.register("nano_suit",
        () -> new ArmorItem(ArmorMaterials.DIAMOND, Type.CHESTPLATE, new Item.Properties()));
    
    public static final RegistryObject<Item> NANO_GAUNTLETS = ITEMS.register("nano_gauntlets",
        () -> new Item(new Item.Properties()));
    
    public static final RegistryObject<Item> JETPACK = ITEMS.register("jetpack",
        () -> new Item(new Item.Properties()));
    
    // Add more items...
}
```

---

## Phase 2: Core Registries & Infrastructure 🏗️

### 2.1 Register ModItems in Main Class
File: Update `Superheroes.java`

```java
public Superheroes() {
    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    
    GeckoLib.initialize();
    
    // Register items, blocks, entities, etc.
    ModItems.ITEMS.register(modEventBus);
    ModBlocks.BLOCKS.register(modEventBus);
    ModEntities.ENTITIES.register(modEventBus);
    ModCreativeTabs.CREATIVE_TABS.register(modEventBus);
    
    MinecraftForge.EVENT_BUS.register(this);
    LOGGER.info("Uzbek Superheroes mod loaded!");
}
```

### 2.2 Create Block Registry
File: `src/main/java/uz/vavi/superheroes/block/ModBlocks.java`

```java
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = 
        DeferredRegister.create(ForgeRegistries.BLOCKS, Superheroes.MOD_ID);
    
    public static final RegistryObject<Block> TECH_WORKBENCH = BLOCKS.register("tech_workbench",
        () -> new Block(BlockBehaviour.Properties.of()
            .material(Material.METAL)
            .strength(3.0F, 6.0F)
            .sound(SoundType.METAL)));
    
    public static final RegistryObject<Item> TECH_WORKBENCH_ITEM = ModItems.ITEMS.register("tech_workbench",
        () -> new BlockItem(TECH_WORKBENCH.get(), new Item.Properties()));
    
    // Add more blocks...
}
```

### 2.3 Create Entity Registry
File: `src/main/java/uz/vavi/superheroes/entity/ModEntities.java`

```java
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = 
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Superheroes.MOD_ID);
    
    public static final RegistryObject<EntityType<AVAEntity>> AVA = ENTITIES.register("ava",
        () -> EntityType.Builder.of(AVAEntity::new, MobCategory.CREATURE)
            .sized(0.6F, 1.8F)
            .build("ava"));
    
    // Add more entities...
}
```

---

## Phase 3: GeckoLib Integration 🎬

### 3.1 Create GeckoLib Model
File: `src/main/java/uz/vavi/superheroes/entity/model/AVAModel.java`

```java
public class AVAModel extends GeoModel<AVAEntity> {
    @Override
    public ResourceLocation getModelResource(AVAEntity object) {
        return new ResourceLocation(Superheroes.MOD_ID, "geo/entity/ava.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AVAEntity object) {
        return new ResourceLocation(Superheroes.MOD_ID, "textures/entity/ava.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AVAEntity animatable) {
        return new ResourceLocation(Superheroes.MOD_ID, "animations/entity/ava.animation.json");
    }
}
```

### 3.2 Create AVA Entity with GeckoLib
File: `src/main/java/uz/vavi/superheroes/entity/custom/AVAEntity.java`

```java
public class AVAEntity extends PathfinderMob implements GeoEntity {
    private final AnimationState idleAnimationState = new AnimationState();
    
    public AVAEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }
    
    // Implement GeoEntity methods...
    
    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()) {
            setupAnim();
        }
    }
    
    private void setupAnim() {
        if (isIdle()) {
            this.idleAnimationState.startIfStopped(this.tickCount);
        }
    }
    
    private boolean isIdle() {
        return !this.isMoving();
    }
}
```

### 3.3 Create Renderer for AVA
File: `src/main/java/uz/vavi/superheroes/client/renderer/AVARenderer.java`

```java
public class AVARenderer extends GeoEntityRenderer<AVAEntity> {
    public AVARenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new AVAModel());
        this.shadowRadius = 0.5F;
    }
}
```

---

## Phase 4: Features Implementation 🦸

### 4.1 Nano-Suit System
- Custom armor with visual effects
- Energy/power system (using ItemStack NBT)
- Durability based on energy level
- Special abilities per piece

### 4.2 Combat System
- Integrate Better Combat for enhanced melee
- Add combo attacks
- Implement damage multipliers
- Add knockback/stun effects

### 4.3 AVA AI Companion
- Follows player
- Assists in combat
- Voice/chat interactions
- Customization menu

### 4.4 Superhero Powers
- Laser attacks (using Photon)
- Teleportation ability
- Super strength (Better Combat integration)
- Speed boost

---

## Phase 5: Assets & Resources 🎨

### 5.1 Textures Needed
- `assets/superheroes/textures/item/` - Item textures
- `assets/superheroes/textures/block/` - Block textures
- `assets/superheroes/textures/entity/` - Entity textures (AVA, superheroes)
- `assets/superheroes/textures/armor/` - Armor layer textures

### 5.2 Models Needed
- `assets/superheroes/models/item/` - Item models
- `assets/superheroes/models/block/` - Block models
- `assets/superheroes/geo/entity/` - GeckoLib geo models

### 5.3 Animations (GeckoLib)
- `assets/superheroes/animations/entity/ava.animation.json`
- Combat animations
- Movement animations
- Power activation animations

---

## Phase 6: Testing & Polish 🧪

### 6.1 Unit Tests
- Test item registry
- Test block registry
- Test entity spawning
- Test abilities activation

### 6.2 Integration Tests
- Test multiplayer functionality
- Test mod interactions with other mods
- Test resource pack compatibility

### 6.3 Performance Testing
- Check FPS impact
- Memory usage
- Network traffic (multiplayer)

---

## Dependency Checklist

- ✓ Forge 47.4.10
- ✓ GeckoLib 4.4.7
- ✓ Better Combat
- ✓ PlayerAnimator
- ✓ Curios API
- ✓ Kleiders Custom Renderer
- ✓ Photon VFX

---

## Build & Run Commands

```bash
# Setup IDE (IntelliJ IDEA)
./gradlew genIntellijRuns

# Run client
./gradlew runClient

# Run server
./gradlew runServer

# Build mod JAR
./gradlew build

# Rebuild data generators
./gradlew runData
```

---

## Quick Reference Links

- [Minecraft Forge Documentation](https://docs.minecraftforge.net/)
- [GeckoLib Wiki](https://github.com/bernie-g/geckolib/wiki)
- [Fabric/Forge Dev Guide](https://www.mcnederlands.nl/index.php?title=Modding:_1.20_Forge)

---

**Last Updated:** February 16, 2026  
**Status:** Ready for Phase 1 Implementation

