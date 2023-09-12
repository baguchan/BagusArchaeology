package baguchan.bagus_archaeology.registry;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import baguchan.bagus_archaeology.blockentity.ModBrushableBlockEntity;
import baguchan.bagus_archaeology.blockentity.ModSkullBlockEntity;
import com.mojang.datafixers.types.Type;
import net.minecraft.Util;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntitys {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RelicsAndAlchemy.MODID);

    public static final RegistryObject<BlockEntityType<ModSkullBlockEntity>> MOD_SKULL = BLOCK_ENTITY.register("mod_skull", () -> register(RelicsAndAlchemy.prefixForString("mod_skull"), BlockEntityType.Builder.of(ModSkullBlockEntity::new,
            ModBlocks.PIGMAN_SKULL.get(),
            ModBlocks.PIGMAN_SKULL_WALL.get())));
    public static final RegistryObject<BlockEntityType<ModBrushableBlockEntity>> MOD_BRUSHABLE = BLOCK_ENTITY.register("mod_brushable", () -> register(RelicsAndAlchemy.prefixForString("mod_brushable"), BlockEntityType.Builder.of(ModBrushableBlockEntity::new
            , ModBlocks.SUSPICIOUS_SOUL_SAND.get()
            , ModBlocks.SUSPICIOUS_SOUL_SOIL.get())));

    private static <T extends BlockEntity> BlockEntityType<T> register(String p_200966_0_, BlockEntityType.Builder<T> p_200966_1_) {
        Type<?> type = Util.fetchChoiceType(References.BLOCK_ENTITY, p_200966_0_);
        return p_200966_1_.build(type);
    }
}