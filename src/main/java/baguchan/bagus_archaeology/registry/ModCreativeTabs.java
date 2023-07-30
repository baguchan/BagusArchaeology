package baguchan.bagus_archaeology.registry;

import baguchan.bagus_archaeology.BagusArcheology;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.Stream;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BagusArcheology.MODID);

    public static final RegistryObject<CreativeModeTab> SOUL_ARCHOELOGY = CREATIVE_TABS.register("soul_received", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .title(Component.translatable("itemGroup." + "soul_archeology"))
            .icon(() -> ModItems.SKELETON_WOLF_HEAD.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.acceptAll(Stream.of(
                        ModItems.SKELETON_WOLF_HEAD,
                        ModItems.WITHER_SKELETON_WOLF_HEAD,
                        ModItems.PIGMAN_SKULL,
                        ModItems.STUDDED_HELMET,
                        ModItems.STUDDED_CHESTPLATE,
                        ModItems.STUDDED_LEGGINGS,
                        ModItems.STUDDED_BOOTS
                ).map(sup -> {
                    return sup.get().getDefaultInstance();
                }).toList());
            }).build());
}