package uz.vavi.superheroes.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uz.vavi.superheroes.Superheroes;
import uz.vavi.superheroes.entity.ModEntities;
import uz.vavi.superheroes.entity.client.AVARenderer;

@Mod.EventBusSubscriber(modid = Superheroes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.AVA.get(), AVARenderer::new);
    }
}
