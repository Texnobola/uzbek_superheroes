# AVAEntity - Quick Reference Guide

## Class Overview

**File:** `uz.vavi.superheroes.entity.custom.AVAEntity`  
**Extends:** `PathfinderMob` (AI and pathfinding)  
**Implements:** `GeoEntity` (GeckoLib animations)  
**Lines:** 128 lines of professional code

---

## Key Components

### 1. GeckoLib Cache (Required)
```java
private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

@Override
public AnimatableInstanceCache getAnimatableInstanceCache() {
    return this.geoCache;
}
```

### 2. Constructor
```java
public AVAEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
    super(entityType, level);
    this.moveControl.setWantedPosition(this.getX(), this.getY(), this.getZ(), this.getSpeed());
}
```

### 3. AI Goals (registerGoals)
- **Priority 0:** FloatGoal - Prevents sinking in water
- **Priority 6:** LookAtPlayerGoal - Looks at nearby players (8 blocks)
- **Priority 7:** RandomLookAroundGoal - Looks around when idle

### 4. Attributes (createAttributes)
- MAX_HEALTH: 20.0 (10 hearts)
- MOVEMENT_SPEED: 0.3
- FLYING_SPEED: 0.6

### 5. GeckoLib Methods
- `registerControllers()` - Empty now, add animation controllers in Phase 3+
- `getAnimatableInstanceCache()` - Returns geoCache

---

## How to Register

```java
// In ModEntities.java:
public static final RegistryObject<EntityType<AVAEntity>> AVA =
    ENTITIES.register("ava",
        () -> EntityType.Builder.of(AVAEntity::new, MobCategory.CREATURE)
            .sized(0.6F, 1.8F)
            .build("ava")
    );
```

---

## AI Goals Explanation

| Goal | Priority | Effect | Range |
|------|----------|--------|-------|
| FloatGoal | 0 | Doesn't sink in water | - |
| LookAtPlayerGoal | 6 | Looks at players | 8 blocks |
| RandomLookAroundGoal | 7 | Random idle looks | - |

**Lower priority = higher importance**

---

## Attributes Explained

- **MAX_HEALTH (20.0):** 10 hearts total
- **MOVEMENT_SPEED (0.3):** Ground walking speed
- **FLYING_SPEED (0.6):** Aerial flight speed (2x walking)

---

## GeckoLib Integration

```java
// Class implements GeoEntity
public class AVAEntity extends PathfinderMob implements GeoEntity {
    // Provides animation cache
    private final AnimatableInstanceCache geoCache = ...
    
    // Must implement these methods
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) { }
    public AnimatableInstanceCache getAnimatableInstanceCache() { }
}
```

---

## What's Missing (For Later Phases)

✗ Renderer (AVARenderer.java)  
✗ GeckoLib Model (geo.json)  
✗ Textures  
✗ Animations  
✗ Follow owner behavior  
✗ Attack behavior  
✗ Custom sounds  

---

## Status

✅ Entity class created  
✅ All requirements implemented  
✅ Ready to register in ModEntities  
✅ Ready for Phase 3 continuation

---

*AVAEntity - Professional GeckoLib Entity for Minecraft Forge 1.20.1*

