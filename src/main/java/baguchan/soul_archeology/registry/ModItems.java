package baguchan.soul_archeology.registry;

import baguchan.soul_archeology.SoulArcheology;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

public class ModItems {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SoulArcheology.MODID);

        public static final RegistryObject<Item> SKELETON_WOLF_HEAD = ITEMS.register("skeleton_wolf_head", () -> new StandingAndWallBlockItem(Objects.requireNonNull(ModBlocks.SKELETON_WOLF_HEAD.get()), Objects.requireNonNull(ModBlocks.SKELETON_WOLF_HEAD_WALL.get()), (new Item.Properties()), Direction.DOWN));
        public static final RegistryObject<Item> WITHER_SKELETON_WOLF_HEAD = ITEMS.register("wither_skeleton_wolf_head", () -> new StandingAndWallBlockItem(Objects.requireNonNull(ModBlocks.WITHER_SKELETON_WOLF_HEAD.get()), Objects.requireNonNull(ModBlocks.WITHER_SKELETON_WOLF_HEAD_WALL.get()), (new Item.Properties()), Direction.DOWN));

}
