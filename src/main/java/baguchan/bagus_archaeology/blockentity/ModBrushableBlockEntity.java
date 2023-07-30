package baguchan.bagus_archaeology.blockentity;

import baguchan.bagus_archaeology.registry.ModBlockEntitys;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModBrushableBlockEntity extends BrushableBlockEntity {
    public ModBrushableBlockEntity(BlockPos p_272892_, BlockState p_273759_) {
        super(p_272892_, p_273759_);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntitys.MOD_BRUSHABLE.get();
    }
}