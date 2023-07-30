package baguchan.bagus_archaeology.block;

import baguchan.bagus_archaeology.blockentity.ModBrushableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BrushableSoulSandBlock extends BrushableBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);

    public BrushableSoulSandBlock(Properties properties) {
        super(Blocks.SOUL_SAND, properties, SoundEvents.SOUL_SAND_HIT, SoundEvents.SOUL_SAND_BREAK);
    }

    public BlockEntity newBlockEntity(BlockPos p_272913_, BlockState p_273465_) {
        return new ModBrushableBlockEntity(p_272913_, p_273465_);
    }

    public VoxelShape getCollisionShape(BlockState p_56702_, BlockGetter p_56703_, BlockPos p_56704_, CollisionContext p_56705_) {
        return SHAPE;
    }

    public VoxelShape getBlockSupportShape(BlockState p_56707_, BlockGetter p_56708_, BlockPos p_56709_) {
        return Shapes.block();
    }

    public VoxelShape getVisualShape(BlockState p_56684_, BlockGetter p_56685_, BlockPos p_56686_, CollisionContext p_56687_) {
        return Shapes.block();
    }

    public BlockState updateShape(BlockState p_56689_, Direction p_56690_, BlockState p_56691_, LevelAccessor p_56692_, BlockPos p_56693_, BlockPos p_56694_) {

        return super.updateShape(p_56689_, p_56690_, p_56691_, p_56692_, p_56693_, p_56694_);
    }

    public boolean isPathfindable(BlockState p_56679_, BlockGetter p_56680_, BlockPos p_56681_, PathComputationType p_56682_) {
        return false;
    }

    public float getShadeBrightness(BlockState p_222462_, BlockGetter p_222463_, BlockPos p_222464_) {
        return 0.2F;
    }
}