package baguchan.bagus_archaeology.client;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import baguchan.bagus_archaeology.client.render.block.CauldronBlockRender;
import baguchan.bagus_archaeology.client.render.entity.AlchemyGolemRenderer;
import baguchan.bagus_archaeology.client.render.entity.AlchemySlimeRenderer;
import baguchan.bagus_archaeology.registry.ModBlockEntitys;
import baguchan.bagus_archaeology.registry.ModEntities;
import net.minecraft.client.renderer.blockentity.BrushableBlockRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
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
    }

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntitys.MOD_BRUSHABLE.get(), BrushableBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntitys.ALCHEMY_CAULDRON.get(), CauldronBlockRender::new);

        event.registerEntityRenderer(ModEntities.ALCHEMY_THROWN.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.ALCHEMY_COMBAT_GOLEM.get(), AlchemyGolemRenderer::new);
        event.registerEntityRenderer(ModEntities.ALCHEMY_SLIME.get(), AlchemySlimeRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
    }

    @SubscribeEvent
    public static void registerColor(RegisterColorHandlersEvent.Block event) {
    }

    @SubscribeEvent
    public static void clientSetupEvent(FMLClientSetupEvent event) {
    }

    public static void renderBlockColor() {
    }


    @SubscribeEvent
    public static void setup(FMLCommonSetupEvent event) {
        renderBlockColor();
    }
}