package baguchan.bagus_archaeology.client.render.entity;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import baguchan.bagus_archaeology.client.model.AlchemyGolemModel;
import baguchan.bagus_archaeology.entity.AlchemyGolem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AlchemyGolemRenderer extends MobRenderer<AlchemyGolem, AlchemyGolemModel<AlchemyGolem>> {
    public static final ResourceLocation GOLEM_LOCATION = new ResourceLocation(RelicsAndAlchemy.MODID, "textures/entity/alchemy_golem/alchemy_golem.png");
    public static final ResourceLocation GOLEM_LOCATION_CRACK_1 = new ResourceLocation(RelicsAndAlchemy.MODID, "textures/entity/alchemy_golem/alchemy_golem_crack_1.png");
    public static final ResourceLocation GOLEM_LOCATION_CRACK_2 = new ResourceLocation(RelicsAndAlchemy.MODID, "textures/entity/alchemy_golem/alchemy_golem_crack_2.png");
    public static final ResourceLocation GOLEM_LOCATION_CRACK_3 = new ResourceLocation(RelicsAndAlchemy.MODID, "textures/entity/alchemy_golem/alchemy_golem_crack_3.png");

    public AlchemyGolemRenderer(EntityRendererProvider.Context p_174188_) {
        super(p_174188_, new AlchemyGolemModel<>(p_174188_.bakeLayer(ModelLayers.IRON_GOLEM)), 0.7F);
    }

    public ResourceLocation getTextureLocation(AlchemyGolem p_115012_) {
        return switch (p_115012_.getCrackiness()) {
            case LOW -> GOLEM_LOCATION_CRACK_1;
            case MEDIUM -> GOLEM_LOCATION_CRACK_2;
            case HIGH -> GOLEM_LOCATION_CRACK_3;
            default -> GOLEM_LOCATION;
        };
    }

    @Override
    protected void scale(AlchemyGolem p_115314_, PoseStack p_115315_, float p_115316_) {
        super.scale(p_115314_, p_115315_, p_115316_);
        p_115315_.scale(0.6F, 0.6F, 0.6F);
    }

    protected void setupRotations(AlchemyGolem p_115014_, PoseStack p_115015_, float p_115016_, float p_115017_, float p_115018_) {
        super.setupRotations(p_115014_, p_115015_, p_115016_, p_115017_, p_115018_);
        if (!((double) p_115014_.walkAnimation.speed() < 0.01D)) {
            float f = 13.0F;
            float f1 = p_115014_.walkAnimation.position(p_115018_) + 6.0F;
            float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
            p_115015_.mulPose(Axis.ZP.rotationDegrees(6.5F * f2));
        }
    }
}