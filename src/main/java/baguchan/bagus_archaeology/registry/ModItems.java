package baguchan.bagus_archaeology.registry;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import baguchan.bagus_archaeology.item.AlchemyPotionItem;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

public class ModItems {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RelicsAndAlchemy.MODID);

        public static final RegistryObject<Item> PIGMAN_SKULL = ITEMS.register("pigman_skull", () -> new StandingAndWallBlockItem(Objects.requireNonNull(ModBlocks.PIGMAN_SKULL.get()), Objects.requireNonNull(ModBlocks.PIGMAN_SKULL_WALL.get()), (new Item.Properties()), Direction.DOWN));

        public static final RegistryObject<Item> STUDDED_HELMET = ITEMS.register("studded_helmet", () -> new DyeableArmorItem(ModArmorMaterials.STUDDED, ArmorItem.Type.HELMET, (new Item.Properties())));
        public static final RegistryObject<Item> STUDDED_CHESTPLATE = ITEMS.register("studded_chestplate", () -> new DyeableArmorItem(ModArmorMaterials.STUDDED, ArmorItem.Type.CHESTPLATE, (new Item.Properties())));
        public static final RegistryObject<Item> STUDDED_LEGGINGS = ITEMS.register("studded_leggings", () -> new DyeableArmorItem(ModArmorMaterials.STUDDED, ArmorItem.Type.LEGGINGS, (new Item.Properties())));
        public static final RegistryObject<Item> STUDDED_BOOTS = ITEMS.register("studded_boots", () -> new DyeableArmorItem(ModArmorMaterials.STUDDED, ArmorItem.Type.BOOTS, (new Item.Properties())));
        public static final RegistryObject<Item> STUDDED_LEATHER = ITEMS.register("studded_leather", () -> new Item((new Item.Properties())));

    public static final RegistryObject<Item> ALCHEMY_POTION = ITEMS.register("alchemy_potion", () -> new AlchemyPotionItem((new Item.Properties())));

}
