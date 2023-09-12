package baguchan.bagus_archaeology.registry;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import baguchan.bagus_archaeology.block.BrushableSoulSandBlock;
import baguchan.bagus_archaeology.block.BrushableSoulSoilBlock;
import baguchan.bagus_archaeology.block.ModSkullBlock;
import baguchan.bagus_archaeology.block.ModWallSkullBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RelicsAndAlchemy.MODID);
    public static final SkullBlock.Type PIGMAN_SKULL_TYPE = new SkullBlock.Type() {
    };
    public static final RegistryObject<SkullBlock> PIGMAN_SKULL = noItemRegister("pigman_skull", () -> new ModSkullBlock(PIGMAN_SKULL_TYPE, BlockBehaviour.Properties.of().strength(1F).sound(SoundType.BONE_BLOCK)));
    public static final RegistryObject<WallSkullBlock> PIGMAN_SKULL_WALL = noItemRegister("pigman_skull_wall", () -> new ModWallSkullBlock(PIGMAN_SKULL_TYPE, BlockBehaviour.Properties.of().strength(1F).sound(SoundType.BONE_BLOCK)));
    public static final RegistryObject<BrushableSoulSandBlock> SUSPICIOUS_SOUL_SAND = register("suspicious_soul_sand", () -> new BrushableSoulSandBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.35F).instrument(NoteBlockInstrument.COW_BELL).speedFactor(0.4F).sound(SoundType.SUSPICIOUS_SAND)));
    public static final RegistryObject<BrushableSoulSoilBlock> SUSPICIOUS_SOUL_SOIL = register("suspicious_soul_soil", () -> new BrushableSoulSoilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.35F).sound(SoundType.SUSPICIOUS_SAND)));

    private static ToIntFunction<BlockState> litBlockEmission(int p_50760_) {
        return (p_50763_) -> {
            return p_50763_.getValue(BlockStateProperties.LIT) ? p_50760_ : 0;
        };
    }

    private static <T extends Block> RegistryObject<T> baseRegister(String name, Supplier<? extends T> block, Function<RegistryObject<T>, Supplier<? extends Item>> item) {
        RegistryObject<T> register = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, item.apply(register));
        return register;
    }

    private static <T extends Block> RegistryObject<T> noItemRegister(String name, Supplier<? extends T> block) {
        RegistryObject<T> register = BLOCKS.register(name, block);
        return register;
    }

    private static <B extends Block> RegistryObject<B> register(String name, Supplier<? extends Block> block) {
        return (RegistryObject<B>) baseRegister(name, block, (object) -> ModBlocks.registerBlockItem(object));
    }

    private static <T extends Block> Supplier<BlockItem> registerBlockItem(final RegistryObject<T> block) {
        return () -> {
            return new BlockItem(Objects.requireNonNull(block.get()), new Item.Properties());
        };
    }
}