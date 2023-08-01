package baguchan.bagus_archaeology;

import baguchan.bagus_archaeology.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BagusArchaeology.MODID)
public class BagusArchaeology {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "bagus_archaeology";
    public BagusArchaeology() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlockEntitys.BLOCK_ENTITY.register(modEventBus);
        ModBiomeModifiers.BIOME_MODIFIER_SERIALIZERS.register(modEventBus);
        ModCreativeTabs.CREATIVE_TABS.register(modEventBus);

        ModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        ModMenuTypes.MENU_TYPES.register(modEventBus);
        ModRecipeTypes.RECIPE_TYPES.register(modEventBus);
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
