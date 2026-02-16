package uz.vavi.superheroes.entity.client;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import uz.vavi.superheroes.Superheroes;
import uz.vavi.superheroes.entity.custom.AVAEntity;

public class AVAModel extends GeoModel<AVAEntity> {
    @Override
    public ResourceLocation getModelResource(AVAEntity animatable) {
        return new ResourceLocation(Superheroes.MOD_ID, "geo/ava.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AVAEntity animatable) {
        return new ResourceLocation(Superheroes.MOD_ID, "textures/entity/ava.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AVAEntity animatable) {
        return new ResourceLocation(Superheroes.MOD_ID, "animations/ava.animation.json");
    }
}
