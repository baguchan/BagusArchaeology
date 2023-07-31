package baguchan.bagus_archaeology.blockentity;

import baguchan.bagus_archaeology.BagusArchaeology;
import baguchan.bagus_archaeology.menu.SoulRecoverMenu;
import baguchan.bagus_archaeology.recipe.SoulRecoverRecipe;
import baguchan.bagus_archaeology.registry.ModBlockEntitys;
import baguchan.bagus_archaeology.registry.ModRecipeTypes;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

/*
 * Credit for aether team!
 *  https://github.com/The-Aether-Team/The-Aether/blob/1.19.4-develop/src/main/java/com/aetherteam/aether/blockentity/IncubatorBlockEntity.java
 */
public class SoulRecoverBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeHolder, StackedContentsCompatible {
    private static final int[] SLOTS_NS = {0};
    private static final int[] SLOTS_EW = {1};
    protected NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
    private ServerPlayer player;
    private int progress; // The current progress time.
    private int progressTotalTime; // Total time a recipe takes to recover.
    private int x;
    private int y;
    private int z;
    protected final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> SoulRecoverBlockEntity.this.progress;
                case 1 -> SoulRecoverBlockEntity.this.progressTotalTime;
                case 2 -> SoulRecoverBlockEntity.this.x;
                case 3 -> SoulRecoverBlockEntity.this.y;
                case 4 -> SoulRecoverBlockEntity.this.z;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> SoulRecoverBlockEntity.this.progress = value;
                case 1 -> SoulRecoverBlockEntity.this.progressTotalTime = value;
                case 2 -> SoulRecoverBlockEntity.this.x = value;
                case 3 -> SoulRecoverBlockEntity.this.y = value;
                case 4 -> SoulRecoverBlockEntity.this.z = value;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    };
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    private final RecipeManager.CachedCheck<Container, SoulRecoverRecipe> quickCheck;

    public SoulRecoverBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, ModRecipeTypes.SOUL_RECOVER.get());
    }

    public SoulRecoverBlockEntity(BlockPos pos, BlockState state, RecipeType<SoulRecoverRecipe> recipeType) {
        super(ModBlockEntitys.SOUL_RECOVER.get(), pos, state);
        this.quickCheck = RecipeManager.createCheck(recipeType);
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
        return new SoulRecoverMenu(id, playerInventory, this, this.dataAccess);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SoulRecoverBlockEntity blockEntity) {
        if (!level.isClientSide()) {
            boolean flag = blockEntity.isLit();
            boolean flag1 = false;

            ItemStack itemstack = blockEntity.items.get(1);
            boolean flag2 = !blockEntity.items.get(0).isEmpty();
            boolean flag3 = !itemstack.isEmpty();

            if (flag3 && flag2) {
                SoulRecoverRecipe recipe;
                if (flag2) {
                    recipe = blockEntity.quickCheck.getRecipeFor(blockEntity, level).orElse(null);
                } else {
                    recipe = null;
                }

                if (blockEntity.canRecover(recipe, blockEntity.items)) {
                    ++blockEntity.progress;
                    if (blockEntity.progress == blockEntity.progressTotalTime) {
                        blockEntity.progress = 0;
                        blockEntity.progressTotalTime = getTotalRecoverTime(level, blockEntity);
                        if (blockEntity.recover(recipe, blockEntity.items)) {
                            blockEntity.setRecipeUsed(recipe);
                        }
                    }
                    flag1 = true;
                } else {
                    blockEntity.progress = 0;
                }
            } else if (blockEntity.progress > 0) {
                flag1 = flag;
                blockEntity.progress = 0;
            }

            if (flag != blockEntity.isLit()) {
                flag1 = true;
                state = state.setValue(AbstractFurnaceBlock.LIT, blockEntity.isLit());
                level.setBlock(pos, state, 1 | 2);
            }

            if (flag1) {
                setChanged(level, pos, state);
            }

            if (blockEntity.x != pos.getX()) {
                blockEntity.x = pos.getX();
            }
            if (blockEntity.y != pos.getY()) {
                blockEntity.y = pos.getY();
            }
            if (blockEntity.z != pos.getZ()) {
                blockEntity.z = pos.getZ();
            }
        }
    }

    /**
     * Spawns an entity on top of the soul recover with the recipe's NBT data and the item's custom name.
     *
     * @param recipe The {@link SoulRecoverRecipe} being recoverd.
     * @param stacks The {@link NonNullList NonNullList<ItemStack>} of items in the menu.
     * @return A {@link Boolean} for whether the item successfully recover.
     */
    private boolean recover(@Nullable SoulRecoverRecipe recipe, NonNullList<ItemStack> stacks) {
        if (recipe != null && this.canRecover(recipe, stacks)) {
            ItemStack itemStack = stacks.get(0);
            ItemStack itemStack1 = stacks.get(1);
            EntityType<?> entityType = recipe.getEntity();
            BlockPos spawnPos = this.getBlockPos().above();
            if (this.getLevel() != null && !this.getLevel().isClientSide() && this.getLevel() instanceof ServerLevel serverLevel) {
                CompoundTag tag = recipe.getTag();
                Component customName = itemStack.hasCustomHoverName() ? itemStack.getHoverName() : null;
                Entity entity = entityType.spawn(serverLevel, tag, null, spawnPos, MobSpawnType.TRIGGERED, true, false);
                if (entity != null) {
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.readAdditionalSaveData(tag);
                    }
                    entity.setCustomName(customName);
                    if (this.player != null) {
                        //SoulRecoverTrigger.INSTANCE.trigger(this.player, itemStack);
                    }
                }
            }
            itemStack.shrink(1);
            itemStack1.shrink(1);
            return true;
        } else {
            return false;
        }
    }

    private boolean canRecover(@Nullable SoulRecoverRecipe recipe, NonNullList<ItemStack> stacks) {
        return !stacks.get(0).isEmpty() && !stacks.get(1).isEmpty() && recipe != null;
    }

    private static int getTotalRecoverTime(Level level, SoulRecoverBlockEntity blockEntity) {
        return blockEntity.quickCheck.getRecipeFor(blockEntity, level).map(SoulRecoverRecipe::getRecoverTime).orElse(600);
    }

    private boolean isLit() {
        return this.progress > 0;
    }

    public void setPlayer(ServerPlayer player) {
        this.player = player;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        return this.items.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        return ContainerHelper.removeItem(this.items, index, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(this.items, index);
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameTags(itemstack, stack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
        if (index == 0 && !flag) {
            this.progressTotalTime = getTotalRecoverTime(this.level, this);
            this.progress = 0;
            this.setChanged();
        }
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public void fillStackedContents(StackedContents helper) {
        for (ItemStack itemstack : this.items) {
            helper.accountStack(itemstack);
        }
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            return SLOTS_NS;
        } else {
            return SLOTS_EW;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(index, stack);
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return true;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return false;
    }

    @Override
    public void setRecipeUsed(@Nullable Recipe<?> recipe) {
        if (recipe != null) {
            ResourceLocation resourcelocation = recipe.getId();
            this.recipesUsed.addTo(resourcelocation, 1);
        }
    }

    @Nullable
    @Override
    public Recipe<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void awardUsedRecipes(Player p_281647_, List<ItemStack> p_282578_) {
    }


    @Override
    public boolean stillValid(Player player) {
        if (this.getLevel().getBlockEntity(this.getBlockPos()) != this) {
            return false;
        } else {
            return player.distanceToSqr(this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() + 0.5, this.getBlockPos().getZ() + 0.5) <= 64.0;
        }
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("menu." + BagusArchaeology.MODID + ".soul_recover");
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items);
        this.progress = tag.getInt("RecoverProgress");
        this.progressTotalTime = tag.getInt("RecoverTotalTime");
        CompoundTag compoundtag = tag.getCompound("RecipesUsed");
        for (String string : compoundtag.getAllKeys()) {
            this.recipesUsed.put(new ResourceLocation(string), compoundtag.getInt(string));
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("RecoverProgress", this.progress);
        tag.putInt("RecoverTotalTime", this.progressTotalTime);
        ContainerHelper.saveAllItems(tag, this.items);
        CompoundTag compoundTag = new CompoundTag();
        this.recipesUsed.forEach((location, integer) -> compoundTag.putInt(location.toString(), integer));
        tag.put("RecipesUsed", compoundTag);
    }

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER) {
            if (facing == Direction.UP)
                return handlers[0].cast();
            else if (facing == Direction.DOWN)
                return handlers[1].cast();
            else
                return handlers[2].cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        for (int x = 0; x < handlers.length; x++)
            handlers[x].invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.handlers = net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
    }
}