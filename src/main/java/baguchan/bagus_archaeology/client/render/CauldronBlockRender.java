package baguchan.bagus_archaeology.client.render;

import baguchan.bagus_archaeology.block.AlchemyCauldronBlock;
import baguchan.bagus_archaeology.blockentity.AlchemyCauldronBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class CauldronBlockRender implements BlockEntityRenderer<AlchemyCauldronBlockEntity> {
    private final ItemRenderer itemRenderer;

    public CauldronBlockRender(BlockEntityRendererProvider.Context p_277899_) {
        this.itemRenderer = p_277899_.getItemRenderer();
    }

    @Override
    public void render(AlchemyCauldronBlockEntity blockentity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
        NonNullList<ItemStack> inventory = blockentity.getItems();
        int posLong = (int) blockentity.getBlockPos().asLong();

        double height = blockentity.getBlockState().getValue(AlchemyCauldronBlock.HAS_WATER) ? 13D : -0.5D;
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.get(i);
            if (!stack.isEmpty()) {
                poseStack.pushPose();
                poseStack.translate(0.5D, (height / 16D), 0.5D);
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));

                // Neatly align items according to their index
                poseStack.translate(this.getPosition(blockentity.tickCount, i).x, this.getPosition(blockentity.tickCount, i).z, 0.0D);

                // Resize the items
                poseStack.scale(0.375F, 0.375F, 0.375F);
                poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));

                if (blockentity.getLevel() != null)
                    Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(blockentity.getLevel(), blockentity.getBlockPos().above()), combinedOverlayIn, poseStack, buffer, blockentity.getLevel(), posLong + i);
                poseStack.popPose();
            }
        }

        ItemStack stack = new ItemStack(Items.WOODEN_SHOVEL);
        poseStack.pushPose();
        poseStack.translate(0.85D, (10D / 16D), 0.85D);
        poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F));

        // Neatly align items according to their index
        poseStack.translate(0.0F, 0.5F, 0.0D);
        // Resize the items
        poseStack.scale(0.6F, 0.6F, 0.6F);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));

        if (blockentity.getLevel() != null)
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(blockentity.getLevel(), blockentity.getBlockPos().above()), combinedOverlayIn, poseStack, buffer, blockentity.getLevel(), posLong);
        poseStack.popPose();
    }

    private Vec3 getPosition(int tickCount, int idx) {
        return this.getPosition(this.getAngle(tickCount, idx), 0.3F);
    }

    private float getAngle(int tickCount, int idx) {
        return 10F * idx + (tickCount * 1.0F);
    }

    private Vec3 getPosition(float angle, float distance) {
        double dx = Math.cos((angle) * Math.PI / 180.0D) * distance;
        double dz = Math.sin((angle) * Math.PI / 180.0D) * distance;

        return new Vec3(dx, 0.0F, dz);
    }
}