package uz.vavi.superheroes;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;
import uz.vavi.superheroes.item.ModItems;
import uz.vavi.superheroes.item.ModCreativeTabs;
import uz.vavi.superheroes.block.ModBlocks;

// Bu qator modingizni Minecraftga tanishtiradi.
// "superheroes" so'zi mods.toml dagi modId bilan bir xil bo'lishi SHART!
@Mod("superheroes")
public class Superheroes {

    // Loglar uchun (konsolda xatolarni ko'rish uchun)
    public static final Logger LOGGER = LogUtils.getLogger();
    // Mod ID - keyinchalik ko'p joyda kerak bo'ladi
    public static final String MOD_ID = "superheroes";

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
}