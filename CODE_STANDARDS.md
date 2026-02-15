# Uzbek Superheroes - Code Quality & Best Practices Guide

## 1. Code Standards 📋

### 1.1 Naming Conventions
- **Classes:** PascalCase (e.g., `AVAEntity`, `NanoSuitItem`)
- **Methods:** camelCase (e.g., `activatePower()`, `handleAnimation()`)
- **Variables:** camelCase (e.g., `itemStack`, `playerEntity`)
- **Constants:** UPPER_SNAKE_CASE (e.g., `MOD_ID`, `ARMOR_DURABILITY`)
- **Packages:** lowercase with dots (e.g., `uz.vavi.superheroes.item`)

### 1.2 JavaDoc Standards
Every public class and method should have JavaDoc:

```java
/**
 * Represents the nano-suit armor with advanced combat capabilities.
 * 
 * Features:
 * - Energy-based durability system
 * - Multiple power modes
 * - Integration with Better Combat
 * 
 * @author Verfex
 * @since 1.0.0
 */
public class NanoSuitItem extends ArmorItem {
    
    /**
     * Activates the nano-suit's special ability.
     * 
     * @param player The player wearing the suit
     * @param ability The ability to activate (0-3)
     * @return true if the ability was successfully activated
     */
    public boolean activateAbility(Player player, int ability) {
        // Implementation...
    }
}
```

### 1.3 Code Organization
Within each file, organize in this order:
1. Package declaration
2. Imports (alphabetically sorted)
3. JavaDoc class comment
4. Class declaration
5. Constants (UPPER_SNAKE_CASE)
6. Static variables
7. Instance variables
8. Constructors
9. Public methods
10. Protected methods
11. Private methods
12. Inner classes

Example:
```java
package uz.vavi.superheroes.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
// ... more imports

/**
 * Nano-Suit armor class.
 */
public class NanoSuitItem extends ArmorItem {
    
    // Constants
    public static final int MAX_ENERGY = 1000;
    public static final int ENERGY_DRAIN_RATE = 5;
    
    // Static variables
    private static final String ENERGY_NBT_KEY = "nanosuit_energy";
    
    // Instance variables
    private int currentEnergy;
    
    // Constructor
    public NanoSuitItem(ArmorMaterial material, Type type, Item.Properties properties) {
        super(material, type, properties);
    }
    
    // Public methods
    public void useEnergy(ItemStack stack, int amount) {
        // ...
    }
    
    // Private methods
    private void validateEnergy() {
        // ...
    }
}
```

---

## 2. Minecraft-Specific Best Practices 🎮

### 2.1 Network Safety (Multiplayer)
Always use packets for client→server communication:

```java
// ❌ WRONG - Client can cheat
if(Minecraft.getInstance().isWindow.isFocused()) {
    player.setHealth(player.getMaxHealth());
}

// ✓ CORRECT - Server validates
public class HealPlayerPacket {
    private int healAmount;
    
    public HealPlayerPacket(int amount) {
        this.healAmount = amount;
    }
    
    public static void handle(HealPlayerPacket pkt, NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer != null && canHeal(serverPlayer)) {
                serverPlayer.heal(pkt.healAmount);
            }
        });
    }
}
```

### 2.2 Rendering (Client-Side Only)
Always check `level.isClientSide()` before rendering:

```java
@Override
public void tick() {
    super.tick();
    
    // ✓ CORRECT - Client-side check
    if (this.level().isClientSide()) {
        updateAnimations();
        playEffects();
    }
    
    // Server-side logic
    updateAI();
    checkCollisions();
}
```

### 2.3 Event Priority & Cancellation
Use appropriate event priorities:

```java
@SubscribeEvent(priority = EventPriority.LOWEST)
public static void onPlayerAttack(LivingAttackEvent event) {
    // Run after other mods process
    Entity entity = event.getEntity();
    if (entity instanceof Player player) {
        // Custom logic
    }
}

// For events that can be cancelled
@SubscribeEvent
public static void onItemUse(UseItemEvent event) {
    if (shouldPreventUse(event.getItemStack())) {
        event.setCanceled(true);  // Prevent vanilla behavior
    }
}
```

### 2.4 NBT Data (Persistent Storage)
For custom item data:

```java
public class EnergyUtils {
    private static final String ENERGY_KEY = "Energy";
    private static final String MAX_ENERGY_KEY = "MaxEnergy";
    
    public static int getEnergy(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getInt(ENERGY_KEY);
    }
    
    public static void setEnergy(ItemStack stack, int energy) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(ENERGY_KEY, Math.max(0, Math.min(energy, getMaxEnergy(stack))));
    }
    
    public static int getMaxEnergy(ItemStack stack) {
        return stack.getOrCreateTag().getInt(MAX_ENERGY_KEY);
    }
}
```

---

## 3. GeckoLib Best Practices 🎬

### 3.1 Animation States
Manage animations efficiently:

```java
public class AVAEntity extends PathfinderMob implements GeoEntity {
    private final AnimationState idleState = new AnimationState();
    private final AnimationState walkState = new AnimationState();
    private final AnimationState attackState = new AnimationState();
    
    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            updateAnimations();
        }
    }
    
    private void updateAnimations() {
        if (this.isAttacking()) {
            this.idleState.stop();
            this.walkState.stop();
            this.attackState.startIfStopped(this.tickCount);
        } else if (this.getDeltaMovement().lengthSqr() > 0.01) {
            this.idleState.stop();
            this.attackState.stop();
            this.walkState.startIfStopped(this.tickCount);
        } else {
            this.walkState.stop();
            this.attackState.stop();
            this.idleState.startIfStopped(this.tickCount);
        }
    }
}
```

### 3.2 Model File Organization
Keep Blockbench models organized:

```
assets/superheroes/geo/entity/
├── ava.geo.json              # Main model
├── ava_helmet.geo.json       # Separate part
└── ava_armor.geo.json        # Armor variant

assets/superheroes/animations/entity/
├── ava.animation.json         # All animations for AVA
└── combat.animation.json      # Shared combat animations
```

### 3.3 Texture Layering
For complex entities:

```
assets/superheroes/textures/entity/ava/
├── base.png                   # Base skin
├── lights.png                 # Emissive texture
├── overlay.png                # Effects layer
└── damage.png                 # Damage state
```

---

## 4. Performance Optimization 🚀

### 4.1 Lazy Initialization
Only create expensive objects when needed:

```java
public class RendererCache {
    private static VertexConsumer cachedConsumer;
    
    public static VertexConsumer getConsumer() {
        if (cachedConsumer == null) {
            cachedConsumer = initializeConsumer();
        }
        return cachedConsumer;
    }
    
    private static VertexConsumer initializeConsumer() {
        // Expensive operation
    }
}
```

### 4.2 Object Pooling
For frequently created/destroyed objects:

```java
public class Vector3fPool {
    private static final Queue<Vector3f> pool = new ConcurrentLinkedQueue<>();
    private static final int POOL_SIZE = 100;
    
    static {
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.offer(new Vector3f());
        }
    }
    
    public static Vector3f get() {
        Vector3f v = pool.poll();
        return v != null ? v : new Vector3f();
    }
    
    public static void release(Vector3f v) {
        if (pool.size() < POOL_SIZE) {
            v.set(0, 0, 0);
            pool.offer(v);
        }
    }
}
```

### 4.3 Caching
Cache expensive calculations:

```java
public class PowerCalculator {
    private static final Map<Integer, Float> damageCache = new HashMap<>();
    private static long lastCacheTime = 0;
    private static final long CACHE_DURATION = 100; // ms
    
    public static float calculateDamage(int level) {
        long now = System.currentTimeMillis();
        if (now - lastCacheTime > CACHE_DURATION) {
            damageCache.clear();
            lastCacheTime = now;
        }
        
        return damageCache.computeIfAbsent(level, lvl -> {
            // Expensive calculation
            return (float) (lvl * 1.5 + Math.random());
        });
    }
}
```

---

## 5. Error Handling & Logging 📝

### 5.1 Proper Logging
Use the mod's logger appropriately:

```java
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

public class Superheroes {
    private static final Logger LOGGER = LogUtils.getLogger();
    
    public void loadFeatures() {
        try {
            // Feature loading logic
            LOGGER.info("Loaded 5 superheroes");
        } catch (Exception e) {
            LOGGER.error("Failed to load superheroes", e);
            // Don't crash, log and continue
        }
    }
}
```

### 5.2 Graceful Degradation
Never let a feature completely break the mod:

```java
public class FeatureLoader {
    public static void loadOptionalFeature() {
        try {
            initializeAdvancedRendering();
        } catch (NoClassDefFoundError e) {
            LOGGER.warn("Advanced rendering not available, using fallback");
            useBasicRendering();
        }
    }
}
```

### 5.3 Assert vs. Check
Use asserts for development, checks for production:

```java
public class DataValidator {
    // ❌ WRONG - Assertions can be disabled
    assert stack != null : "ItemStack cannot be null";
    
    // ✓ CORRECT - Always validated
    if (stack == null) {
        LOGGER.warn("Attempted to process null ItemStack");
        return;
    }
}
```

---

## 6. Testing Strategy 🧪

### 6.1 Unit Test Template
```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NanoSuitItemTest {
    
    @Test
    public void testEnergyDecrease() {
        ItemStack suit = new ItemStack(ModItems.NANO_SUIT.get());
        int initialEnergy = 1000;
        EnergyUtils.setEnergy(suit, initialEnergy);
        
        EnergyUtils.drainEnergy(suit, 100);
        
        assertEquals(900, EnergyUtils.getEnergy(suit));
    }
    
    @Test
    public void testEnergyNeverNegative() {
        ItemStack suit = new ItemStack(ModItems.NANO_SUIT.get());
        EnergyUtils.setEnergy(suit, 50);
        
        EnergyUtils.drainEnergy(suit, 100);
        
        assertTrue(EnergyUtils.getEnergy(suit) >= 0);
    }
}
```

---

## 7. Common Pitfalls to Avoid ⚠️

### Pitfall 1: Modifying Vanilla Collections
```java
// ❌ WRONG - Will throw exception
List<Item> items = new ArrayList<>(Items.REGISTRIES);
items.add(customItem);

// ✓ CORRECT - Use DeferredRegister
ITEMS.register("custom", () -> customItem);
```

### Pitfall 2: Hardcoding Resource Locations
```java
// ❌ WRONG - If mod ID changes, breaks
ResourceLocation loc = new ResourceLocation("superheroes:texture");

// ✓ CORRECT
ResourceLocation loc = new ResourceLocation(Superheroes.MOD_ID, "texture");
```

### Pitfall 3: Assuming Client-Side
```java
// ❌ WRONG - Crashes on dedicated server
double x = Minecraft.getInstance().player.getX();

// ✓ CORRECT
if (entity instanceof Player player && level.isClientSide()) {
    player.displayFireAnimation();
}
```

### Pitfall 4: Memory Leaks from Static References
```java
// ❌ WRONG - Keeps entities alive
public static Player cachedPlayer;

// ✓ CORRECT - Use weak references or cleanup
private WeakReference<Player> playerRef;

public void cleanup() {
    playerRef.clear();
}
```

### Pitfall 5: Not Resetting Server Data
```java
// ❌ WRONG - Data persists across server restarts
public static Map<UUID, Integer> playerPowers = new HashMap<>();

// ✓ CORRECT - Use ServerTickEvent to clean up
@SubscribeEvent
public static void onServerTick(ServerTickEvent event) {
    if (event.phase == Phase.END && event.server.overworld().getGameTime() % 20 == 0) {
        validatePlayerData();
    }
}
```

---

## 8. Version Control Best Practices 📚

### 8.1 .gitignore Template
Ensure your .gitignore includes:
```
# Gradle
.gradle/
build/
gradle/

# IDE
.idea/
.vscode/
*.iml
*.swp

# Game
run/
run-data/

# Build artifacts
*.jar
*.zip

# System
.DS_Store
Thumbs.db
```

### 8.2 Commit Messages
Use clear, descriptive messages:
```
✓ GOOD:
- Add nano-suit armor with energy system
- Fix AVA animation sync on multiplayer
- Implement laser ability power calculation

❌ BAD:
- Update code
- Fix bug
- Stuff
```

---

## 9. Documentation Standards 📖

### 9.1 README.md Template
Should include:
- Feature overview
- Installation instructions
- Configuration options
- How to report bugs
- Credits

### 9.2 Code Examples in Comments
```java
/**
 * Creates a nano-suit with specified energy level.
 * 
 * Example:
 * <pre>
 * ItemStack suit = createNanoSuit(1000);
 * player.addItem(suit);
 * </pre>
 * 
 * @param initialEnergy The starting energy (0-1000)
 * @return A new nano-suit ItemStack
 */
public static ItemStack createNanoSuit(int initialEnergy) {
    // Implementation
}
```

---

## 10. Security Checklist ✅

- [ ] No hardcoded API keys or credentials
- [ ] Server validates all client-sent data
- [ ] No arbitrary code execution from NBT/files
- [ ] Proper permission checks for admin commands
- [ ] No access to sensitive player data without consent
- [ ] Proper exception handling (no stack traces in chat)
- [ ] Input validation for all user-provided data

---

## Quick Reference Checklist

Before committing code:
- [ ] Code compiles without warnings
- [ ] JavaDoc for all public methods
- [ ] No hardcoded resource locations (use MOD_ID constant)
- [ ] Client-side code wrapped in `isClientSide()` check
- [ ] Network operations use packets
- [ ] Proper logging (not System.out.println)
- [ ] No null pointer exceptions possible
- [ ] Consistent naming conventions
- [ ] Performance optimized (no hot-path allocations)
- [ ] Unit tests pass

---

**Last Updated:** February 16, 2026  
**Status:** Reference Guide for Development

