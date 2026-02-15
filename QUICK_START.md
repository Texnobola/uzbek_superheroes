# Uzbek Superheroes - Quick Start Guide

## 🚀 Get Started in 5 Minutes

### 1. Open the Project in IntelliJ IDEA
```bash
# In your project root:
./gradlew genIntellijRuns
```

Then open in IntelliJ IDEA:
- File → Open → Select project folder
- Wait for Gradle sync to complete

### 2. Verify It Compiles
```bash
./gradlew build
```

Should see: **BUILD SUCCESSFUL**

### 3. Run in Minecraft
From IntelliJ IDEA:
- Click "Run" dropdown → Select "runClient"
- Wait for Minecraft to launch
- You should see your mod in the mods list

---

## 📋 One-Page Checklist

### Before First Coding Session
- [ ] Read REVIEW_SUMMARY.md (10 min)
- [ ] Read DEVELOPMENT_PLAN.md section 1 (5 min)
- [ ] Read CODE_STANDARDS.md sections 1-2 (10 min)
- [ ] Set up IDE (15 min)
- [ ] Run project to verify setup (5 min)

### First Coding Task (Phase 1)
```java
Priority 1: Update gradle.properties
Priority 2: Create item folder structure
Priority 3: Create ModItems.java
Priority 4: Register in main class
Priority 5: Verify compilation
```

---

## 📂 Folder Structure Quick Reference

```
📦 Your Mod Root
├── 📄 build.gradle          ← Build configuration
├── 📄 gradle.properties      ← Project settings (⚠️ UPDATE THESE)
├── 📄 settings.gradle        ← Gradle settings
├── 📦 src
│   └── 📦 main
│       ├── 📦 java/uz/vavi/superheroes/
│       │   ├── 📄 Superheroes.java  ✅ Exists
│       │   ├── 📂 item/             ❌ Create this
│       │   ├── 📂 block/            ❌ Create this
│       │   ├── 📂 entity/           ❌ Create this
│       │   ├── 📂 client/           ❌ Create this
│       │   ├── 📂 event/            ❌ Create this
│       │   ├── 📂 network/          ❌ Create this
│       │   ├── 📂 config/           ❌ Create this
│       │   ├── 📂 util/             ❌ Create this
│       │   └── 📂 ability/          ❌ Create this
│       └── 📦 resources
│           ├── 📂 assets/superheroes/
│           │   ├── 📂 textures/
│           │   ├── 📂 models/
│           │   ├── 📂 animations/
│           │   └── 📂 sounds/
│           └── 📂 META-INF/
│               └── 📄 mods.toml
└── 📂 gradle/
    └── 📂 wrapper/
```

---

## 🔑 Key Files to Edit First

### 1. `gradle.properties`
**Lines to change:**
```gradle
Line 45: mod_id=examplemod  →  mod_id=superheroes
Line 46: mod_name=Example Mod  →  mod_name=Uzbek Superheroes
Line 51: mod_group_id=com.example.examplemod  →  mod_group_id=uz.vavi.superheroes
Line 54: mod_authors=YourNameHere  →  mod_authors=Verfex
```

### 2. `Superheroes.java`
**Add after GeckoLib.initialize();**
```java
ModItems.ITEMS.register(modEventBus);
ModBlocks.BLOCKS.register(modEventBus);
ModCreativeTabs.CREATIVE_TABS.register(modEventBus);
```

### 3. Create `ModItems.java`
```java
public class ModItems {
    public static final DeferredRegister<Item> ITEMS = 
        DeferredRegister.create(ForgeRegistries.ITEMS, Superheroes.MOD_ID);
    
    public static final RegistryObject<Item> NANO_SUIT = ITEMS.register("nano_suit",
        () -> new Item(new Item.Properties()));
    
    public static final RegistryObject<Item> NANO_GAUNTLETS = ITEMS.register("nano_gauntlets",
        () -> new Item(new Item.Properties()));
}
```

---

## 🎯 5-Day Implementation Plan

### Day 1: Setup & Configuration
**Goal:** Fix template values, create folder structure
- [ ] Update gradle.properties
- [ ] Create all package folders
- [ ] Verify project still compiles
- **Time:** 1-2 hours

### Day 2: Item Registry
**Goal:** Create ModItems with 5 starter items
- [ ] Create ModItems.java
- [ ] Add item definitions
- [ ] Register in main class
- [ ] Run and verify no errors
- **Time:** 2-3 hours

### Day 3: Block Registry
**Goal:** Create ModBlocks with tech workbench
- [ ] Create ModBlocks.java
- [ ] Add block definitions
- [ ] Create block items
- [ ] Register everything
- **Time:** 2-3 hours

### Day 4: Creative Tab & Testing
**Goal:** Get items/blocks visible in creative mode
- [ ] Create ModCreativeTabs.java
- [ ] Link to main class
- [ ] Run in Minecraft
- [ ] Verify items appear
- **Time:** 1-2 hours

### Day 5: Entity & Documentation
**Goal:** Set up entity infrastructure
- [ ] Create ModEntities.java skeleton
- [ ] Create basic AVA entity
- [ ] Document progress
- [ ] Plan next phase
- **Time:** 2-3 hours

**Total Time Estimate:** 8-13 hours = ~2 hours per day over a week

---

## 🧪 Testing Checklist

After Each Change:
- [ ] Code compiles: `./gradlew build`
- [ ] No new warnings
- [ ] Correct package structure
- [ ] Proper @Mod annotation
- [ ] Event bus registration correct

Before Running in Minecraft:
- [ ] All registries created
- [ ] All registries registered in main class
- [ ] Main class has correct @Mod("superheroes")
- [ ] Gradle build successful

In Minecraft:
- [ ] Mod appears in mods list
- [ ] Items visible in creative tab
- [ ] No console errors
- [ ] Can place/use items

---

## 💻 Essential Commands

```bash
# Setup IDE
./gradlew genIntellijRuns

# Build mod
./gradlew build

# Run client
./gradlew runClient

# Run server
./gradlew runServer

# Clean build
./gradlew clean build

# Generate data (recipes, models)
./gradlew runData

# Check for errors
./gradlew check
```

---

## 🐛 Troubleshooting Quick Fixes

### "Build failed"
```bash
./gradlew clean build
# If still fails, check:
# 1. Java version (must be 17)
# 2. gradle.properties values
# 3. Import errors in Java files
```

### "Minecraft won't launch"
```bash
# Check console for errors
# Common issues:
# - Missing @Mod annotation
# - Incorrect mod ID mismatch
# - Missing event bus registration
# - Gradle cache issue: ./gradlew --stop
```

### "Items don't appear in creative"
```java
// Check:
// 1. ModItems.ITEMS.register(modEventBus) called?
// 2. ModCreativeTabs.CREATIVE_TABS.register(modEventBus) called?
// 3. CreativeTab has display items configured?
```

### "Can't find class"
```bash
# Reimport Gradle project:
# IntelliJ: File → Invalidate Caches → Restart
# Then: View → Tool Windows → Gradle → Refresh
```

---

## 📚 Documentation Quick Links

**In Your Project:**
- `REVIEW_SUMMARY.md` - Overall project status
- `DEVELOPMENT_PLAN.md` - Detailed implementation guide  
- `CODE_STANDARDS.md` - Code quality guidelines
- `PROJECT_REVIEW.md` - Detailed technical review

**External Resources:**
- [Minecraft Forge Docs](https://docs.minecraftforge.net/)
- [GeckoLib Tutorial](https://github.com/bernie-g/geckolib/wiki)
- [Better Combat Guide](https://www.curseforge.com/minecraft/mods/better-combat)

---

## ⚡ Pro Tips

1. **Save Time:** Use IDE code completion (Ctrl+Space in IntelliJ)
2. **Debug:** Add `LOGGER.info()` statements to trace execution
3. **Reference:** Keep DEVELOPMENT_PLAN.md open while coding
4. **Test Frequently:** Build after each registry is done
5. **Ask Questions:** Minecraft Forge Discord is helpful
6. **Version Control:** Commit after each completed day

---

## 🎨 Asset Pipeline Preview

When ready to add visuals:

```
Item texture:  assets/superheroes/textures/item/nano_suit.png
Item model:    assets/superheroes/models/item/nano_suit.json
Block texture: assets/superheroes/textures/block/tech_workbench.png
Block model:   assets/superheroes/models/block/tech_workbench.json
GeckoLib model: assets/superheroes/geo/entity/ava.geo.json
Animation:     assets/superheroes/animations/entity/ava.animation.json
```

Tools needed:
- **Blockbench** (free) - 3D modeling for GeckoLib
- **Aseprite** or **Piskel** (free) - Pixel art for textures
- **Audacity** (free) - Sound editing

---

## ✨ Success Milestones

Celebrate these achievements:

🎉 **Day 1:** Project compiles with no errors  
🎉 **Day 2:** Items appear in creative tab  
🎉 **Day 3:** Blocks placed in world  
🎉 **Day 4:** First custom entity spawns  
🎉 **Day 5:** Basic animation plays  
🎉 **Week 2:** First complete feature works  
🎉 **Week 3:** Multiplayer tested successfully  
🎉 **Week 4:** Ready for beta testing  
🎉 **Week 6:** Version 1.0 released! 🚀

---

## 📞 Getting Help

If stuck:

1. **Check the docs:** All answers are in DEVELOPMENT_PLAN.md
2. **Check errors:** Look at full console output, not just last line
3. **Google it:** "Minecraft Forge [error message]" usually helps
4. **Discord:** [Minecraft Forge Community](https://discord.gg/minecraftforge)
5. **Stack Overflow:** Tag with `minecraft-forge` and `java`

---

## 🎓 Learning Resources

**Estimated time to productive:**
- Setup & configuration: 1 hour
- First registry: 2 hours  
- Creative tab: 1 hour
- First feature: 3-4 hours
- **Total: 7-8 hours to first working feature**

After that, each new feature gets faster!

---

## 📊 Progress Tracker

Copy this to your notes and mark as complete:

```
Phase 1: Setup & Configuration
- [ ] Update gradle.properties
- [ ] Create package structure
- [ ] Verify compilation
- [ ] Understand event system

Phase 2: Core Registries
- [ ] Create ModItems.java
- [ ] Create ModBlocks.java
- [ ] Create ModCreativeTabs.java
- [ ] Test in Minecraft

Phase 3: Entities & Animation
- [ ] Create ModEntities.java
- [ ] Create AVA entity
- [ ] Setup GeckoLib
- [ ] First animation working

Phase 4: Features
- [ ] First armor piece
- [ ] Energy system
- [ ] Combat integration
- [ ] Power system

Phase 5: Content
- [ ] 3-5 superheroes
- [ ] Complete abilities
- [ ] Recipes & crafting
- [ ] Balancing

Phase 6: Polish
- [ ] Bug fixes
- [ ] Performance optimization
- [ ] Documentation
- [ ] Release!
```

---

**Ready to start?** Begin with Day 1 checklist above!  
**Questions?** Check DEVELOPMENT_PLAN.md for your next step.  
**Good luck!** 🚀

*Last Updated: February 16, 2026*

