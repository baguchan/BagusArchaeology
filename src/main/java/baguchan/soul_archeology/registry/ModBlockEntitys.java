package baguchan.soul_archeology.registry;

import baguchan.soul_archeology.SoulArcheology;
import baguchan.soul_archeology.blockentity.ModSkullBlockEntity;
import com.mojang.datafixers.types.Type;
import net.minecraft.Util;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntitys {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SoulArcheology.MODID);

    public static final RegistryObject<BlockEntityType<ModSkullBlockEntity>> MOD_SKULL = BLOCK_ENTITY.register("mod_skull", () -> register(SoulArcheology.prefixForString("mod_skull"), BlockEntityType.Builder.of(ModSkullBlockEntity::new,
            ModBlocks.SKELETON_WOLF_HEAD.get(),
            ModBlocks.SKELETON_WOLF_HEAD_WALL.get(),
            ModBlocks.WITHER_SKELETON_WOLF_HEAD.get(),
            ModBlocks.WITHER_SKELETON_WOLF_HEAD_WALL.get(),
            ModBlocks.PIGMAN_SKULL.get(),
            ModBlocks.PIGMAN_SKULL_WALL.get())));

    private static <T extends BlockEntity> BlockEntityType<T> register(String p_200966_0_, BlockEntityType.Builder<T> p_200966_1_) {
        Type<?> type = Util.fetchChoiceType(References.BLOCK_ENTITY, p_200966_0_);
        return p_200966_1_.build(type);
    }
}