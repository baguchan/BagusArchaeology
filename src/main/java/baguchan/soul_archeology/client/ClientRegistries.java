package baguchan.soul_archeology.client;

import baguchan.earthmobsmod.EarthMobsMod;
import baguchan.soul_archeology.SoulArcheology;
import baguchan.soul_archeology.client.model.WolfHeadModel;
import baguchan.soul_archeology.registry.ModBlockEntitys;
import baguchan.soul_archeology.registry.ModBlocks;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SoulArcheology.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistries {
    @SubscribeEvent
    public static void registerSkull(EntityRenderersEvent.CreateSkullModels event) {
        event.registerSkullModel(ModBlocks.SKELETON_WOLF_HEAD_TYPE, new WolfHeadModel(event.getEntityModelSet().bakeLayer(ModModelLayer.WOLF_HEAD)));
        event.registerSkullModel(ModBlocks.WITHER_SKELETON_WOLF_HEAD_TYPE, new WolfHeadModel(event.getEntityModelSet().bakeLayer(ModModelLayer.WOLF_HEAD)));
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
    }
}