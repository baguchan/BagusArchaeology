package baguchan.bagus_archaeology.client.render.item;

import baguchan.bagus_archaeology.client.model.AlchemyGolemModel;
import baguchan.bagus_archaeology.client.render.entity.AlchemyGolemRenderer;
import baguchan.bagus_archaeology.item.AlchemyGolemItem;
import baguchan.bagus_archaeology.registry.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class AlchemyGolemBWLR extends BlockEntityWithoutLevelRenderer {
    private AlchemyGolemModel<?> entityModel;

    public AlchemyGolemBWLR() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        this.entityModel = new AlchemyGolemModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.IRON_GOLEM));
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemDisplayContext pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pOverlay) {
        if (pStack.getItem() instanceof AlchemyGolemItem) {
            if (pStack.is(ModItems.ALCHEMY_GOLEM.get())) {
                pPoseStack.pushPose();
                pPoseStack.scale(1.0F, -1.0F, -1.0F);
                pPoseStack.translate(0.65F, -0.65F, 0);
                VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entityTranslucentCull(AlchemyGolemRenderer.GOLEM_LOCATION));
                this.entityModel.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, pOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
                pPoseStack.popPose();
            }
        }
    }
}