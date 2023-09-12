package baguchan.bagus_archaeology;

import baguchan.bagus_archaeology.material.AlchemyMaterial;
import baguchan.bagus_archaeology.registry.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import net.minecraftforge.registries.DataPackRegistryEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RelicsAndAlchemy.MODID)
public class RelicsAndAlchemy {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "relics_and_alchemy";

    public RelicsAndAlchemy() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModAlchemyElements.ALCHEMY_ELEMENT.register(modEventBus);
        ModAlchemyMaterials.ALCHEMY_MATERIAL.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlockEntitys.BLOCK_ENTITY.register(modEventBus);
        ModCreativeTabs.CREATIVE_TABS.register(modEventBus);
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::dataSetup);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void dataSetup(final DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(AlchemyMaterial.REGISTRY_KEY, AlchemyMaterial.CODEC, AlchemyMaterial.CODEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    public static String prefixForString(String string) {
        return new ResourceLocation(MODID, string).toString();
    }

    public static ResourceLocation prefix(String string) {
        return new ResourceLocation(MODID, string);
    }

    public static RegistryAccess registryAccess() {
        if (EffectiveSide.get().isServer()) {
            return ServerLifecycleHooks.getCurrentServer().registryAccess();
        }
        return Minecraft.getInstance().getConnection().registryAccess();
    }
}
