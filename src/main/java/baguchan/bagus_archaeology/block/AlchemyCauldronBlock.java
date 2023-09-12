package baguchan.bagus_archaeology.block;

import baguchan.bagus_archaeology.blockentity.AlchemyCauldronBlockEntity;
import baguchan.bagus_archaeology.registry.ModBlockEntitys;
import baguchan.bagus_archaeology.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class AlchemyCauldronBlock extends BaseEntityBlock {
    private static final int SIDE_THICKNESS = 2;
    private static final int LEG_WIDTH = 4;
    private static final int LEG_HEIGHT = 3;
    private static final int LEG_DEPTH = 2;
    protected static final int FLOOR_LEVEL = 4;
    private static final VoxelShape INSIDE = box(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    protected static final VoxelShape SHAPE = Shapes.join(Shapes.block(), Shapes.or(box(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 12.0D), box(4.0D, 0.0D, 0.0D, 12.0D, 3.0D, 16.0D), box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D), INSIDE), BooleanOp.ONLY_FIRST);


    public static final BooleanProperty HAS_WATER = BooleanProperty.create("has_water");

    public AlchemyCauldronBlock(BlockBehaviour.Properties p_151946_) {
        super(p_151946_);
        this.registerDefaultState(this.stateDefinition.any().setValue(HAS_WATER, false));

    }


    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    protected double getContentHeight(BlockState p_151948_) {
        return 0.8D;
    }

    @Override
    public void entityInside(BlockState p_153506_, Level p_153507_, BlockPos p_153508_, Entity p_153509_) {
        if (this.isEntityInsideContent(p_153506_, p_153508_, p_153509_)) {
            //p_153509_.hurt(p_153509_.damageSources().inFire(), 3.0F);
        }
    }

    protected boolean isEntityInsideContent(BlockState p_151980_, BlockPos p_151981_, Entity p_151982_) {
        return isFull(p_151980_) && p_151982_.getY() < (double) p_151981_.getY() + this.getContentHeight(p_151980_) && p_151982_.getBoundingBox().maxY > (double) p_151981_.getY() + 0.25D;
    }

    public InteractionResult use(BlockState p_151969_, Level p_151970_, BlockPos p_151971_, Player p_151972_, InteractionHand p_151973_, BlockHitResult p_151974_) {
        ItemStack itemstack = p_151972_.getItemInHand(p_151973_);
        BlockEntity blockentity = p_151970_.getBlockEntity(p_151971_);
        if (blockentity instanceof AlchemyCauldronBlockEntity alchemyCauldronBlockEntity) {
            if (!p_151969_.getValue(HAS_WATER) && itemstack.isEmpty() && !alchemyCauldronBlockEntity.isEmpty()) {
                p_151972_.playSound(SoundEvents.ITEM_PICKUP);
                ItemStack stack = alchemyCauldronBlockEntity.removeItem();
                if (!p_151972_.addItem(stack)) {
                    p_151972_.drop(stack, false);
                }
                return InteractionResult.SUCCESS;
            } else if (!p_151969_.getValue(HAS_WATER) && itemstack.is(Items.WATER_BUCKET)) {
                p_151970_.setBlock(p_151971_, p_151969_.setValue(HAS_WATER, true), 3);
                return emptyBucket(p_151970_, p_151971_, p_151972_, p_151973_, itemstack);
            } else if (p_151969_.getValue(HAS_WATER)) {
                ItemStack testItem = alchemyCauldronBlockEntity.result(itemstack, p_151970_, p_151969_, p_151971_, true);

                if (!testItem.isEmpty() || itemstack.isEmpty()) {

                    if (itemstack.isEmpty() && testItem.isEmpty() && !alchemyCauldronBlockEntity.isEmpty()) {
                        p_151972_.playSound(SoundEvents.ITEM_PICKUP);
                        ItemStack stack = alchemyCauldronBlockEntity.removeItem();
                        if (!p_151972_.addItem(stack)) {
                            p_151972_.drop(stack, false);
                        }
                        return InteractionResult.SUCCESS;
                    }
                    ItemStack resultItem = alchemyCauldronBlockEntity.result(itemstack, p_151970_, p_151969_, p_151971_, false);

                    return fillBucket(p_151969_, p_151970_, p_151971_, p_151972_, p_151973_, itemstack, resultItem, (state) -> state.getValue(HAS_WATER));
                } else {
                    if (alchemyCauldronBlockEntity.addItem(itemstack)) {
                        p_151972_.playSound(SoundEvents.ITEM_PICKUP);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return super.use(p_151969_, p_151970_, p_151971_, p_151972_, p_151973_, p_151974_);
    }

    static InteractionResult fillBucket(BlockState p_175636_, Level p_175637_, BlockPos p_175638_, Player p_175639_, InteractionHand p_175640_, ItemStack p_175641_, ItemStack p_175642_, Predicate<BlockState> p_175643_) {
        if (!p_175643_.test(p_175636_)) {
            return InteractionResult.PASS;
        } else {
            if (!p_175637_.isClientSide) {
                Item item = p_175641_.getItem();
                p_175639_.setItemInHand(p_175640_, ItemUtils.createFilledResult(p_175641_, p_175639_, p_175642_, false));
                p_175639_.awardStat(Stats.USE_CAULDRON);
                p_175639_.awardStat(Stats.ITEM_USED.get(item));
                p_175637_.setBlockAndUpdate(p_175638_, ModBlocks.ALCHEMY_CAULDRON.get().defaultBlockState());
                p_175637_.playSound((Player) null, p_175638_, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                p_175637_.gameEvent((Entity) null, GameEvent.FLUID_PICKUP, p_175638_);
            }

            return InteractionResult.sidedSuccess(p_175637_.isClientSide);
        }
    }

    static InteractionResult emptyBucket(Level p_175619_, BlockPos p_175620_, Player p_175621_, InteractionHand p_175622_, ItemStack p_175623_) {
        if (!p_175619_.isClientSide) {
            Item item = p_175623_.getItem();
            p_175621_.setItemInHand(p_175622_, ItemUtils.createFilledResult(p_175623_, p_175621_, new ItemStack(Items.BUCKET)));
            p_175621_.awardStat(Stats.FILL_CAULDRON);
            p_175621_.awardStat(Stats.ITEM_USED.get(item));
            p_175619_.playSound((Player) null, p_175620_, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            p_175619_.gameEvent((Entity) null, GameEvent.FLUID_PLACE, p_175620_);
        }

        return InteractionResult.sidedSuccess(p_175619_.isClientSide);
    }

    public VoxelShape getShape(BlockState p_151964_, BlockGetter p_151965_, BlockPos p_151966_, CollisionContext p_151967_) {
        return SHAPE;
    }

    public VoxelShape getInteractionShape(BlockState p_151955_, BlockGetter p_151956_, BlockPos p_151957_) {
        return INSIDE;
    }

    public boolean hasAnalogOutputSignal(BlockState p_151986_) {
        return true;
    }

    public boolean isPathfindable(BlockState p_151959_, BlockGetter p_151960_, BlockPos p_151961_, PathComputationType p_151962_) {
        return false;
    }

    public boolean isFull(BlockState p_151984_) {
        return p_151984_.getValue(HAS_WATER);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new AlchemyCauldronBlockEntity(p_153215_, p_153216_);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152755_, BlockState p_152756_, BlockEntityType<T> p_152757_) {
        return p_152755_.isClientSide() ? createTickerHelper(p_152757_, ModBlockEntitys.ALCHEMY_CAULDRON.get(), AlchemyCauldronBlockEntity::animationTick) : createTickerHelper(p_152757_, ModBlockEntitys.ALCHEMY_CAULDRON.get(), AlchemyCauldronBlockEntity::cookTick);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        super.createBlockStateDefinition(p_49915_);
        p_49915_.add(HAS_WATER);
    }
}