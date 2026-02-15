package uz.vavi.superheroes.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

/**
 * AVA Entity - AI companion drone for Uzbek Superheroes mod.
 *
 * AVA is a flying companion that follows the player and assists in combat.
 * Uses GeckoLib for advanced animations and PathfinderMob for AI behavior.
 *
 * Features:
 * - Hovers in water (FloatGoal)
 * - Looks at nearby players
 * - Random look around behavior
 * - Flying locomotion (custom AI can be added)
 * - GeckoLib animation support
 *
 * @author Verfex
 * @since 1.0.0
 */
public class AVAEntity extends PathfinderMob implements GeoEntity {

    // ==================== GECKOLIB CACHE ====================

    /**
     * GeckoLib requires an animatable instance cache for proper animation handling.
     * This is created once and reused for performance.
     */
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    // ==================== CONSTRUCTOR ====================

    /**
     * Constructor for AVA entity.
     *
     * @param entityType The entity type (registered in ModEntities)
     * @param level The game world/level
     */
    public AVAEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);

        // Set flying speed (it's a drone, not a ground-based mob)
        this.moveControl.setWantedPosition(this.getX(), this.getY(), this.getZ(), this.getSpeed());
    }

    // ==================== AI GOALS ====================

    /**
     * Register AI goals for AVA entity.
     * Goals are registered with priorities (lower number = higher priority).
     *
     * Priority Order:
     * 0 - Float in water (critical, prevents drowning)
     * 6 - Look at nearby players
     * 7 - Random look around (low priority)
     */
    @Override
    protected void registerGoals() {
        // Priority 0: Float in water - prevents sinking
        this.goalSelector.addGoal(0, new FloatGoal(this));

        // Priority 6: Look at nearest player within 8 blocks
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));

        // Priority 7: Randomly look around when idle
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    // ==================== ATTRIBUTES ====================

    /**
     * Create attribute suppliers for AVA entity.
     * Attributes define base properties like health, speed, etc.
     *
     * @return AttributeSupplier with configured attributes
     */
    public static AttributeSupplier createAttributes() {
        return PathfinderMob.createMobAttributes()
                // AI companion - moderate health
                .add(Attributes.MAX_HEALTH, 20.0)
                // Ground movement speed (backup in case it lands)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                // Flying speed (for aerial movement)
                .add(Attributes.FLYING_SPEED, 0.6)
                .build();
    }

    // ==================== GECKOLIB INTEGRATION ====================

    /**
     * Register animation controllers with GeckoLib.
     * This is where animation state controllers would be added in future phases.
     * Currently empty as animations will be added in Phase 3+.
     *
     * @param controllers The controller registry
     */
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // Animation controllers will be registered here in future phases
        // Example: controllers.add(new AnimationController<>(this, "controller", 0, this::idleController));
    }

    /**
     * Get the GeckoLib animatable instance cache.
     * Required by GeoEntity interface for animation system.
     *
     * @return The cached animatable instance
     */
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

}

