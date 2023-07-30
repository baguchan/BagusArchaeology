package baguchan.bagus_archaeology.block;

import baguchan.bagus_archaeology.blockentity.ModBrushableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BrushableSoulSoilBlock extends BrushableBlock {
    public BrushableSoulSoilBlock(Properties properties) {
        super(Blocks.SOUL_SOIL, properties, SoundEvents.SOUL_SOIL_HIT, SoundEvents.SOUL_SOIL_BREAK);
    }

    public BlockEntity newBlockEntity(BlockPos p_272913_, BlockState p_273465_) {
        return new ModBrushableBlockEntity(p_272913_, p_273465_);
    }
}