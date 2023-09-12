package baguchan.bagus_archaeology.client;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import baguchan.bagus_archaeology.client.model.WolfHeadModel;
import baguchan.bagus_archaeology.client.render.AlchemyGolemRenderer;
import baguchan.bagus_archaeology.client.render.CauldronBlockRender;
import baguchan.bagus_archaeology.registry.ModBlockEntitys;
import baguchan.bagus_archaeology.registry.ModBlocks;
import baguchan.bagus_archaeology.registry.ModEntities;
import baguchan.bagus_archaeology.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.blockentity.BrushableBlockRenderer;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = RelicsAndAlchemy.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistries {
    @SubscribeEvent
    public static void registerSkull(EntityRenderersEvent.CreateSkullModels event) {
        event.registerSkullModel(ModBlocks.PIGMAN_SKULL_TYPE, new SkullModel(event.getEntityModelSet().bakeLayer(ModelLayers.PLAYER_HEAD)));
    }

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntitys.MOD_BRUSHABLE.get(), BrushableBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntitys.MOD_SKULL.get(), SkullBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntitys.ALCHEMY_CAULDRON.get(), CauldronBlockRender::new);

        event.registerEntityRenderer(ModEntities.ALCHEMY_THROWN.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.ALCHEMY_GOLEM.get(), AlchemyGolemRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayer.WOLF_HEAD, WolfHeadModel::createWolfHeadModel);
    }

    @SubscribeEvent
    public static void registerColor(RegisterColorHandlersEvent.Block event) {
        event.register((p_92621_, p_92622_, p_92623_, p_92624_) -> {
            return p_92622_ != null && p_92623_ != null ? BiomeColors.getAverageWaterColor(p_92622_, p_92623_) : -1;
        }, ModBlocks.ALCHEMY_CAULDRON.get());
    }

    @SubscribeEvent
    public static void clientSetupEvent(FMLClientSetupEvent event) {
        event.enqueueWork(() -> SkullBlockRenderer.SKIN_BY_TYPE.put(ModBlocks.PIGMAN_SKULL_TYPE, new ResourceLocation(RelicsAndAlchemy.MODID, "textures/entity/pigman_skull.png")));
    }

    public static void renderBlockColor() {
        Minecraft.getInstance().getItemColors().register((p_92708_, p_92709_) -> {
            return p_92709_ > 0 ? -1 : ((DyeableLeatherItem) p_92708_.getItem()).getColor(p_92708_);
        }, ModItems.STUDDED_BOOTS.get(), ModItems.STUDDED_LEGGINGS.get(), ModItems.STUDDED_CHESTPLATE.get(), ModItems.STUDDED_HELMET.get());
    }


    @SubscribeEvent
    public static void setup(FMLCommonSetupEvent event) {
        renderBlockColor();
    }
}