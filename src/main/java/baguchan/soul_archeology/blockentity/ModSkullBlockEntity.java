package baguchan.soul_archeology.blockentity;

import baguchan.soul_archeology.registry.ModBlockEntitys;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModSkullBlockEntity extends SkullBlockEntity {
    public ModSkullBlockEntity(BlockPos p_155731_, BlockState p_155732_) {
        super(p_155731_, p_155732_);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntitys.MOD_SKULL.get();
    }
}
