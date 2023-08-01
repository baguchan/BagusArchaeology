package baguchan.bagus_archaeology.registry;

import baguchan.bagus_archaeology.BagusArchaeology;
import baguchan.bagus_archaeology.menu.SoulRecoverMenu;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, BagusArchaeology.MODID);
    public static final RegistryObject<MenuType<SoulRecoverMenu>> SOUL_RECOVER = register("soul_recover", SoulRecoverMenu::new);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String name, MenuType.MenuSupplier<T> menu) {
        return MENU_TYPES.register(name, () -> new MenuType<>(menu, FeatureFlags.DEFAULT_FLAGS));
    }
}