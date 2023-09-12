package baguchan.bagus_archaeology.registry;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import baguchan.bagus_archaeology.element.AlchemyElement;
import baguchan.bagus_archaeology.element.ThickElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.*;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = RelicsAndAlchemy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModAlchemyElements {
    public static final DeferredRegister<AlchemyElement> ALCHEMY_ELEMENT = DeferredRegister.create(new ResourceLocation(RelicsAndAlchemy.MODID, "relics_and_alchemy"), RelicsAndAlchemy.MODID);


    public static final RegistryObject<AlchemyElement> THICK = ALCHEMY_ELEMENT.register("thick", () -> new ThickElement(new AlchemyElement.Properties(new AlchemyElement.AlchemyType[]{AlchemyElement.AlchemyType.AFFECT}, 4.0F)));
    public static final RegistryObject<AlchemyElement> SOUL = ALCHEMY_ELEMENT.register("soul", () -> new ThickElement(new AlchemyElement.Properties(new AlchemyElement.AlchemyType[]{AlchemyElement.AlchemyType.AFFECT}, 4.0F)));

    private static Supplier<IForgeRegistry<AlchemyElement>> registry;

    @SubscribeEvent
    public static void onNewRegistry(NewRegistryEvent event) {
        registry = event.create(new RegistryBuilder<AlchemyElement>()
                .addCallback(AlchemyElement.class)
                .setName(new ResourceLocation(RelicsAndAlchemy.MODID, "relics_and_alchemy"))
                .setDefaultKey(new ResourceLocation(RelicsAndAlchemy.MODID, "thick")));
    }

    public static Supplier<IForgeRegistry<AlchemyElement>> getRegistry() {
        if (registry == null) {
            throw new IllegalStateException("Registry not yet initialized");
        }
        return registry;
    }
}