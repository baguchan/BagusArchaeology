package baguchan.bagus_archaeology.client;

import baguchan.bagus_archaeology.BagusArchaeology;
import baguchan.bagus_archaeology.client.model.WolfHeadModel;
import baguchan.bagus_archaeology.registry.ModBlockEntitys;
import baguchan.bagus_archaeology.registry.ModBlocks;
import baguchan.bagus_archaeology.registry.ModItems;
import baguchan.earthmobsmod.EarthMobsMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = BagusArchaeology.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistries {
    @SubscribeEvent
    public static void registerSkull(EntityRenderersEvent.CreateSkullModels event) {
        event.registerSkullModel(ModBlocks.SKELETON_WOLF_HEAD_TYPE, new WolfHeadModel(event.getEntityModelSet().bakeLayer(ModModelLayer.WOLF_HEAD)));
        event.registerSkullModel(ModBlocks.WITHER_SKELETON_WOLF_HEAD_TYPE, new WolfHeadModel(event.getEntityModelSet().bakeLayer(ModModelLayer.WOLF_HEAD)));
        event.registerSkullModel(ModBlocks.PIGMAN_SKULL_TYPE, new SkullModel(event.getEntityModelSet().bakeLayer(ModelLayers.PLAYER_HEAD)));

    }

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntitys.MOD_SKULL.get(), SkullBlockRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayer.WOLF_HEAD, WolfHeadModel::createWolfHeadModel);
    }

    @SubscribeEvent
    public static void clientSetupEvent(FMLClientSetupEvent event) {
        event.enqueueWork(() -> SkullBlockRenderer.SKIN_BY_TYPE.put(ModBlocks.SKELETON_WOLF_HEAD_TYPE, new ResourceLocation(EarthMobsMod.MODID, "textures/entity/skeleton_wolf/skeleton_wolf.png")));
        event.enqueueWork(() -> SkullBlockRenderer.SKIN_BY_TYPE.put(ModBlocks.WITHER_SKELETON_WOLF_HEAD_TYPE, new ResourceLocation(EarthMobsMod.MODID, "textures/entity/wither_skeleton_wolf/wither_skeleton_wolf.png")));
        event.enqueueWork(() -> SkullBlockRenderer.SKIN_BY_TYPE.put(ModBlocks.PIGMAN_SKULL_TYPE, new ResourceLocation(BagusArchaeology.MODID, "textures/entity/pigman_skull.png")));
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