package baguchan.bagus_archaeology.blockentity;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import baguchan.bagus_archaeology.block.AlchemyCauldronBlock;
import baguchan.bagus_archaeology.material.AlchemyMaterial;
import baguchan.bagus_archaeology.registry.ModBlockEntitys;
import baguchan.bagus_archaeology.registry.ModItems;
import baguchan.bagus_archaeology.util.AlchemyUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;

public class AlchemyCauldronBlockEntity extends BlockEntity implements Container {
    private NonNullList<ItemStack> items = NonNullList.withSize(8, ItemStack.EMPTY);

    @Nullable
    protected ResourceLocation lootTable;
    protected long lootTableSeed;
    public int tickCount;

    public AlchemyCauldronBlockEntity(BlockPos p_155731_, BlockState p_155732_) {
        super(ModBlockEntitys.ALCHEMY_CAULDRON.get(), p_155731_, p_155732_);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntitys.ALCHEMY_CAULDRON.get();
    }


    public boolean addItem(ItemStack itemStack) {
        for (int i = 0; i < this.items.size(); i++) {
            if (isEmpty(i) && !itemStack.isEmpty()) {
                items.set(i, itemStack.split(1));
                setChanged();
                return true;
            }
        }
        return false;
    }

    public ItemStack removeItem() {
        for (int i = 0; i < this.items.size(); i++) {
            if (!isEmpty(i)) {
                ItemStack item = getItem(i).split(1);
                setChanged();
                return item;
            }
        }
        return ItemStack.EMPTY;
    }

    public boolean isEmpty(int slot) {
        return items.get(slot).isEmpty();
    }

    public ItemStack result(ItemStack stack, Level level, BlockState state, BlockPos blockPos, boolean simulator) {
        if (stack.is(Items.GLASS_BOTTLE)) {
            ItemStack stack1 = new ItemStack(ModItems.ALCHEMY_POTION.get());
            for (ItemStack stack2 : items) {
                Optional<Holder.Reference<AlchemyMaterial>> referenceOptional = RelicsAndAlchemy.registryAccess().lookup(AlchemyMaterial.REGISTRY_KEY).get().listElements().filter(alchemyMaterialReference -> {
                    return stack2.is(alchemyMaterialReference.get().getItem());
                }).findFirst();
                if (!stack2.isEmpty() && !simulator && referenceOptional.isPresent() && referenceOptional.get().get().isUsableDrink()) {
                    ItemStack stack3 = stack2.split(1);
                    AlchemyUtils.addAlchemyMaterialToItemStack(stack1, stack3);
                }

            }
            level.setBlock(blockPos, state.setValue(AlchemyCauldronBlock.HAS_WATER, false), 3);
            setChanged();
            return stack1;
        }
        if (stack.is(Items.SLIME_BALL) || stack.is(Items.CLAY_BALL)) {
            ItemStack stack1 = new ItemStack(ModItems.ALCHEMY_PROJECTILE.get());
            for (ItemStack stack2 : items) {
                Optional<Holder.Reference<AlchemyMaterial>> referenceOptional = RelicsAndAlchemy.registryAccess().lookup(AlchemyMaterial.REGISTRY_KEY).get().listElements().filter(alchemyMaterialReference -> {
                    return stack2.is(alchemyMaterialReference.get().getItem());
                }).findFirst();
                if (!stack2.isEmpty() && !simulator && referenceOptional.isPresent()) {
                    ItemStack stack3 = stack2.split(1);
                    AlchemyUtils.addAlchemyMaterialToItemStack(stack1, stack3);
                }

            }
            level.setBlock(blockPos, state.setValue(AlchemyCauldronBlock.HAS_WATER, false), 3);
            setChanged();
            return stack1;
        }

        if (stack.is(ModItems.GOLEM_COMBAT_CORE.get())) {
            ItemStack stack1;
            if (AlchemyUtils.getAlchemyMaterialHardness(AlchemyUtils.getAlchemyMaterials(stack)) > 0) {
                stack1 = new ItemStack(ModItems.ALCHEMY_COMBAT_GOLEM.get());
            } else {
                stack1 = new ItemStack(ModItems.ALCHEMY_SLIME.get());
            }
            for (ItemStack stack2 : items) {
                Optional<Holder.Reference<AlchemyMaterial>> referenceOptional = RelicsAndAlchemy.registryAccess().lookup(AlchemyMaterial.REGISTRY_KEY).get().listElements().filter(alchemyMaterialReference -> {
                    return stack2.is(alchemyMaterialReference.get().getItem());
                }).findFirst();
                if (!stack2.isEmpty() && !simulator && referenceOptional.isPresent() && referenceOptional.get().get().isUsableConstruct()) {
                    ItemStack stack3 = stack2.split(1);
                    AlchemyUtils.addAlchemyMaterialToItemStack(stack1, stack3);
                }

            }
            level.setBlock(blockPos, state.setValue(AlchemyCauldronBlock.HAS_WATER, false), 3);
            setChanged();
            return stack1;
        }

        if (!simulator && stack.is(Items.BUCKET)) {
            level.setBlock(blockPos, state.setValue(AlchemyCauldronBlock.HAS_WATER, false), 3);
            setChanged();
            return Items.WATER_BUCKET.getDefaultInstance();
        }
        return ItemStack.EMPTY;
    }

    public static void animationTick(Level level, BlockPos pos, BlockState state, AlchemyCauldronBlockEntity alchemyCauldronBlockEntity) {
        if (state.getValue(AlchemyCauldronBlock.HAS_WATER)) {
            if (level.getBlockState(pos.below()).is(BlockTags.CAMPFIRES) || level.getBlockState(pos.below()).is(BlockTags.FIRE)) {

                double x = ((double) pos.getX()) + (level.random.nextFloat());
                double y = (double) pos.getY() + 0.8F;
                double z = ((double) pos.getZ()) + (level.random.nextFloat());

                for (int k = 0; k < 3; ++k) {
                    level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, 5.0E-4D, 0.0D);
                }
                if (level.random.nextFloat() < 0.005F) {
                    level.playLocalSound(x, y, z, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.8F, 0.8F + level.random.nextFloat() * 0.4F, false);
                }

                x = ((double) pos.getX()) + (level.random.nextFloat());
                y = (double) pos.getY() + 0.9F;
                z = ((double) pos.getZ()) + (level.random.nextFloat());

                level.addParticle(ParticleTypes.BUBBLE_POP, x, y, z, 0.0D, 5.0E-4D, 0.0D);
                level.playLocalSound(x, y, z, SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.BLOCKS, 0.6F, 0.8F + level.random.nextFloat() * 0.4F, false);
            }
            alchemyCauldronBlockEntity.tickCount++;
        }
    }

    public static void cookTick(Level p_155307_, BlockPos p_155308_, BlockState p_155309_, AlchemyCauldronBlockEntity p_155310_) {
        boolean flag = false;
        if (p_155309_.getValue(AlchemyCauldronBlock.HAS_WATER)) {

            if (p_155307_.getBlockState(p_155308_.below()).is(BlockTags.CAMPFIRES) || p_155307_.getBlockState(p_155308_.below()).is(BlockTags.FIRE)) {
                for (int i = 0; i < p_155310_.items.size(); ++i) {
                    ItemStack itemstack = p_155310_.items.get(i);
                    if (!itemstack.isEmpty()) {
                        flag = true;
                    }
                }
            }
        }

        if (flag) {
            setChanged(p_155307_, p_155308_, p_155309_);
        }

    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    public static void setLootTable(BlockGetter p_222767_, RandomSource p_222768_, BlockPos p_222769_, ResourceLocation p_222770_) {
        BlockEntity blockentity = p_222767_.getBlockEntity(p_222769_);
        if (blockentity instanceof AlchemyCauldronBlockEntity) {
            ((AlchemyCauldronBlockEntity) blockentity).setLootTable(p_222770_, p_222768_.nextLong());
        }

    }

    public void load(CompoundTag p_155349_) {
        super.load(p_155349_);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(p_155349_)) {
            ContainerHelper.loadAllItems(p_155349_, this.items);
        }
        setChanged();
    }

    protected void saveAdditional(CompoundTag p_187489_) {
        super.saveAdditional(p_187489_);
        if (!this.trySaveLootTable(p_187489_)) {
            ContainerHelper.saveAllItems(p_187489_, this.items);
        }
    }

    protected boolean tryLoadLootTable(CompoundTag p_59632_) {
        if (p_59632_.contains("LootTable", 8)) {
            this.lootTable = new ResourceLocation(p_59632_.getString("LootTable"));
            this.lootTableSeed = p_59632_.getLong("LootTableSeed");
            return true;
        } else {
            return false;
        }
    }

    protected boolean trySaveLootTable(CompoundTag p_59635_) {
        if (this.lootTable == null) {
            return false;
        } else {
            p_59635_.putString("LootTable", this.lootTable.toString());
            if (this.lootTableSeed != 0L) {
                p_59635_.putLong("LootTableSeed", this.lootTableSeed);
            }

            return true;
        }
    }

    public void unpackLootTable(@Nullable Player p_59641_) {
        if (this.lootTable != null && this.level.getServer() != null) {
            LootTable loottable = this.level.getServer().getLootData().getLootTable(this.lootTable);
            if (p_59641_ instanceof ServerPlayer) {
                CriteriaTriggers.GENERATE_LOOT.trigger((ServerPlayer) p_59641_, this.lootTable);
            }

            this.lootTable = null;
            LootParams.Builder lootparams$builder = (new LootParams.Builder((ServerLevel) this.level)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(this.worldPosition));
            if (p_59641_ != null) {
                lootparams$builder.withLuck(p_59641_.getLuck()).withParameter(LootContextParams.THIS_ENTITY, p_59641_);
            }

            loottable.fill(this, lootparams$builder.create(LootContextParamSets.CHEST), this.lootTableSeed);
        }

    }

    public void setLootTable(ResourceLocation p_59627_, long p_59628_) {
        this.lootTable = p_59627_;
        this.lootTableSeed = p_59628_;
    }

    public boolean isEmpty() {
        this.unpackLootTable((Player) null);
        return this.getItems().stream().allMatch(ItemStack::isEmpty);
    }

    public ItemStack getItem(int p_59611_) {
        this.unpackLootTable((Player) null);
        return this.getItems().get(p_59611_);
    }

    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    public ItemStack removeItem(int p_59613_, int p_59614_) {
        this.unpackLootTable((Player) null);
        ItemStack itemstack = ContainerHelper.removeItem(this.getItems(), p_59613_, p_59614_);
        if (!itemstack.isEmpty()) {
            this.setChanged();
        }

        return itemstack;
    }

    public ItemStack removeItemNoUpdate(int p_59630_) {
        this.unpackLootTable((Player) null);
        return ContainerHelper.takeItem(this.getItems(), p_59630_);
    }

    public void setItem(int p_59616_, ItemStack p_59617_) {
        this.unpackLootTable((Player) null);
        this.getItems().set(p_59616_, p_59617_);
        if (p_59617_.getCount() > this.getMaxStackSize()) {
            p_59617_.setCount(this.getMaxStackSize());
        }

        this.setChanged();
    }

    public boolean stillValid(Player p_59619_) {
        return Container.stillValidBlockEntity(this, p_59619_);
    }

    @Override
    public void clearContent() {
        this.getItems().clear();
    }

    private net.minecraftforge.common.util.LazyOptional<?> itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> createUnSidedHandler());

    protected net.minecraftforge.items.IItemHandler createUnSidedHandler() {
        return new net.minecraftforge.items.wrapper.InvWrapper(this);
    }

    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> cap, @org.jetbrains.annotations.Nullable net.minecraft.core.Direction side) {
        if (!this.remove && cap == net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER)
            return itemHandler.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> createUnSidedHandler());
    }

}
