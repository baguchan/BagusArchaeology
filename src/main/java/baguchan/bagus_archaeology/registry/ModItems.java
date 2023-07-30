package baguchan.bagus_archaeology.registry;

import baguchan.bagus_archaeology.BagusArcheology;
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
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BagusArcheology.MODID);

        public static final RegistryObject<Item> SKELETON_WOLF_HEAD = ITEMS.register("skeleton_wolf_head", () -> new StandingAndWallBlockItem(Objects.requireNonNull(ModBlocks.SKELETON_WOLF_HEAD.get()), Objects.requireNonNull(ModBlocks.SKELETON_WOLF_HEAD_WALL.get()), (new Item.Properties()), Direction.DOWN));
        public static final RegistryObject<Item> WITHER_SKELETON_WOLF_HEAD = ITEMS.register("wither_skeleton_wolf_head", () -> new StandingAndWallBlockItem(Objects.requireNonNull(ModBlocks.WITHER_SKELETON_WOLF_HEAD.get()), Objects.requireNonNull(ModBlocks.WITHER_SKELETON_WOLF_HEAD_WALL.get()), (new Item.Properties()), Direction.DOWN));
        public static final RegistryObject<Item> PIGMAN_SKULL = ITEMS.register("pigman_skull", () -> new StandingAndWallBlockItem(Objects.requireNonNull(ModBlocks.PIGMAN_SKULL.get()), Objects.requireNonNull(ModBlocks.PIGMAN_SKULL_WALL.get()), (new Item.Properties()), Direction.DOWN));

        public static final RegistryObject<Item> STUDDED_HELMET = ITEMS.register("studded_helmet", () -> new DyeableArmorItem(ModArmorMaterials.STUDDED, ArmorItem.Type.HELMET, (new Item.Properties())));
        public static final RegistryObject<Item> STUDDED_CHESTPLATE = ITEMS.register("studded_chestplate", () -> new DyeableArmorItem(ModArmorMaterials.STUDDED, ArmorItem.Type.CHESTPLATE, (new Item.Properties())));
        public static final RegistryObject<Item> STUDDED_LEGGINGS = ITEMS.register("studded_leggings", () -> new DyeableArmorItem(ModArmorMaterials.STUDDED, ArmorItem.Type.LEGGINGS, (new Item.Properties())));
        public static final RegistryObject<Item> STUDDED_BOOTS = ITEMS.register("studded_boots", () -> new DyeableArmorItem(ModArmorMaterials.STUDDED, ArmorItem.Type.BOOTS, (new Item.Properties())));

}
