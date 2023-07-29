package baguchan.soul_archeology.block;

import baguchan.soul_archeology.blockentity.ModSkullBlockEntity;
import baguchan.soul_archeology.registry.ModBlockEntitys;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class ModSkullBlock extends SkullBlock {
    public ModSkullBlock(SkullBlock.Type type, BlockBehaviour.Properties properties) {
        super(type, properties);
    }

    public BlockEntity newBlockEntity(BlockPos p_151996_, BlockState p_151997_) {
        return new ModSkullBlockEntity(p_151996_, p_151997_);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_151992_, BlockState p_151993_, BlockEntityType<T> p_151994_) {
        if (p_151992_.isClientSide) {
            boolean flag = p_151993_.is(Blocks.DRAGON_HEAD) || p_151993_.is(Blocks.DRAGON_WALL_HEAD) || p_151993_.is(Blocks.PIGLIN_HEAD) || p_151993_.is(Blocks.PIGLIN_WALL_HEAD);
            if (flag) {
                return createTickerHelper(p_151994_, ModBlockEntitys.MOD_SKULL.get(), ModSkullBlockEntity::animation);
            }
        }

        return null;
    }
}
