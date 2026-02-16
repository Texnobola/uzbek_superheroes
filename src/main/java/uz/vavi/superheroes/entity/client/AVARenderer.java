package uz.vavi.superheroes.entity.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import uz.vavi.superheroes.entity.custom.AVAEntity;

public class AVARenderer extends GeoEntityRenderer<AVAEntity> {
    public AVARenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AVAModel());
    }
}
