package baguchan.bagus_archaeology;

import baguchan.bagus_archaeology.registry.ModBlockEntitys;
import baguchan.bagus_archaeology.registry.ModBlocks;
import baguchan.bagus_archaeology.registry.ModCreativeTabs;
import baguchan.bagus_archaeology.registry.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RelicsAndAlchemy.MODID)
public class RelicsAndAlchemy {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "relics_and_alchemy";

    public RelicsAndAlchemy() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlockEntitys.BLOCK_ENTITY.register(modEventBus);
        ModCreativeTabs.CREATIVE_TABS.register(modEventBus);
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    public static String prefixForString(String string) {
        return new ResourceLocation(MODID, string).toString();
    }

    public static ResourceLocation prefix(String string) {
        return new ResourceLocation(MODID, string);
    }
}
