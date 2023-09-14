package baguchan.bagus_archaeology.entity;

import baguchan.bagus_archaeology.api.AlchemyCommand;
import baguchan.bagus_archaeology.api.IAlchemyMob;
import baguchan.bagus_archaeology.api.IAlchemyOwner;
import baguchan.bagus_archaeology.element.AlchemyElement;
import baguchan.bagus_archaeology.entity.goal.AlchemyCopyHurtByOwnerTargetGoal;
import baguchan.bagus_archaeology.entity.goal.AlchemyCopyHurtOwnerTargetGoal;
import baguchan.bagus_archaeology.entity.goal.AlchemySlimeSpitGoal;
import baguchan.bagus_archaeology.material.AlchemyMaterial;
import baguchan.bagus_archaeology.registry.ModEntities;
import baguchan.bagus_archaeology.registry.ModItems;
import baguchan.bagus_archaeology.util.AlchemyUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

public class AlchemySlime extends Slime implements IAlchemyOwner, IAlchemyMob {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(AlchemyGolem.class, EntityDataSerializers.ITEM_STACK);

    @Nullable
    private UUID ownerUUID;
    @Nullable
    private Entity cachedOwner;

    private AlchemyCommand command = AlchemyCommand.FREE;

    public AlchemySlime(EntityType<? extends AlchemySlime> p_33588_, Level p_33589_) {
        super(p_33588_, p_33589_);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new AlchemySlimeSpitGoal(this));
        this.goalSelector.addGoal(0, new SlimeFollowOwnerGoal(this));
        this.targetSelector.removeAllGoals(goal -> {
            return goal instanceof NearestAttackableTargetGoal<?>;
        });
        this.targetSelector.addGoal(1, new AlchemyCopyHurtOwnerTargetGoal<>(this));
        this.targetSelector.addGoal(2, new AlchemyCopyHurtByOwnerTargetGoal<>(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, (double) 0.2F);
    }

    protected InteractionResult mobInteract(Player p_28861_, InteractionHand p_28862_) {
        ItemStack itemstack = p_28861_.getItemInHand(p_28862_);
        if (!itemstack.is(ModItems.ALCHEMY_PROJECTILE.get()) && !AlchemyUtils.hasAlchemyMaterial(itemstack)) {
            return InteractionResult.PASS;
        } else {
            float f = this.getHealth();
            float scale = 0;

            if (AlchemyUtils.hasAlchemyMaterial(this.getItem())) {
                List<AlchemyMaterial> alchemyMaterialList = AlchemyUtils.getAlchemyMaterials(this.getItem());
                for (AlchemyMaterial alchemyMaterial : alchemyMaterialList) {
                    scale += alchemyMaterial.getPower();
                    for (AlchemyElement alchemyElement : alchemyMaterial.getAlchemyElement()) {
                        scale *= alchemyElement.getSelfScale();
                    }
                }
            }
            this.heal(scale);
            if (this.getHealth() == f) {
                return InteractionResult.PASS;
            } else {
                float f1 = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(SoundEvents.SLIME_ATTACK, 1.0F, f1);
                if (!p_28861_.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }
    }

    @Override
    public void setAlchemyCommand(AlchemyCommand command) {
        this.command = command;
    }

    @Override
    public AlchemyCommand getAlchemyCommand() {
        return AlchemyCommand.STAND;
    }

    public void setOwner(@Nullable Entity p_37263_) {
        if (p_37263_ != null) {
            this.ownerUUID = p_37263_.getUUID();
            this.cachedOwner = p_37263_;
        }

    }

    @Nullable
    public Entity getOwner() {
        if (this.cachedOwner != null && !this.cachedOwner.isRemoved()) {
            return this.cachedOwner;
        } else if (this.ownerUUID != null && this.level() instanceof ServerLevel) {
            this.cachedOwner = ((ServerLevel) this.level()).getEntity(this.ownerUUID);
            return this.cachedOwner;
        } else {
            return null;
        }
    }

    public void setSize(int p_32972_, boolean p_32973_) {
        super.setSize(p_32972_, p_32973_);
        this.setStatsFromItem(this.getItem());
        if (p_32973_) {
            this.setHealth(this.getMaxHealth());
        }
    }

    public void setItem(ItemStack p_37447_) {
        if (!p_37447_.is(this.getDefaultItem()) || p_37447_.hasTag()) {
            this.getEntityData().set(DATA_ITEM_STACK, p_37447_.copyWithCount(1));
        }

    }

    @Override
    public void remove(Entity.RemovalReason p_146834_) {
        this.setRemoved(p_146834_);
        this.invalidateCaps();
    }


    public boolean removeWhenFarAway(double p_27519_) {
        return false;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    public void setStatsFromItem(ItemStack p_37447_) {
        if (!this.level().isClientSide()) {
            float health = 0;

            if (AlchemyUtils.hasAlchemyMaterial(p_37447_)) {
                List<AlchemyMaterial> alchemyMaterialList = AlchemyUtils.getAlchemyMaterials(this.getItem());
                for (AlchemyMaterial alchemyMaterial : alchemyMaterialList) {
                    health += alchemyMaterial.getPower() * 2;
                }
            }

            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.getAttributeValue(Attributes.MOVEMENT_SPEED) + health * 0.001F);
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getAttributeValue(Attributes.MAX_HEALTH) + health);
        }

    }

    public ItemStack getItemRaw() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    public ItemStack getItem() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(this.getDefaultItem()) : itemstack;
    }

    public void addAdditionalSaveData(CompoundTag p_37449_) {
        super.addAdditionalSaveData(p_37449_);
        ItemStack itemstack = this.getItemRaw();
        if (!itemstack.isEmpty()) {
            p_37449_.put("Item", itemstack.save(new CompoundTag()));
        }

        if (this.ownerUUID != null) {
            p_37449_.putUUID("Owner", this.ownerUUID);
        }
    }

    public void readAdditionalSaveData(CompoundTag p_37445_) {
        super.readAdditionalSaveData(p_37445_);
        ItemStack itemstack = ItemStack.of(p_37445_.getCompound("Item"));
        this.setItem(itemstack);

        if (p_37445_.hasUUID("Owner")) {
            this.ownerUUID = p_37445_.getUUID("Owner");
            this.cachedOwner = null;
        }
    }

    protected Item getDefaultItem() {
        return ModItems.ALCHEMY_SLIME.get();
    }

    @Override
    protected void dealDamage(LivingEntity p_33638_) {
        if (this.canAttack(p_33638_)) {
            super.dealDamage(p_33638_);
        }
    }

    public void playerTouch(Player p_33611_) {
        if (this.isDealsDamage()) {
            this.dealDamage(p_33611_);
        }

    }

    @Override
    public void push(Entity p_33636_) {
        super.push(p_33636_);
        if (!(p_33636_ instanceof IronGolem && this.isDealsDamage())) {
            this.dealDamage((LivingEntity) p_33636_);
        }
    }

    @Override
    public boolean canAttack(LivingEntity p_21171_) {
        if (this.getOwner() == p_21171_) {
            return false;
        } else
        if (p_21171_ instanceof TraceableEntity traceableEntity) {
            return traceableEntity.getOwner() != getOwner();
        } else if (p_21171_ instanceof TamableAnimal tamableAnimal) {
            return tamableAnimal.getOwner() != getOwner();
        }
        return super.canAttack(p_21171_);
    }

    @Override
    public boolean isAlliedTo(Entity p_20355_) {
        if (this.getOwner() == p_20355_) {
            return true;
        } else if (p_20355_ instanceof TraceableEntity traceableEntity) {
            return traceableEntity.getOwner() == getOwner();
        } else if (p_20355_ instanceof TamableAnimal tamableAnimal) {
            return tamableAnimal.getOwner() == getOwner();
        }
        return super.isAlliedTo(p_20355_);
    }

    public void attack() {
        this.targetSquish = 2.0F;
        this.level().broadcastEntityEvent(this, (byte) 61);
        AlchemyThrown snowball = new AlchemyThrown(ModEntities.ALCHEMY_THROWN.get(), this, this.level());
        double d0 = this.getTarget().getEyeY() - (double) this.getEyeY();
        double d1 = this.getTarget().getX() - this.getX();
        double d2 = d0;
        double d3 = this.getTarget().getZ() - this.getZ();
        double d4 = Math.sqrt(d1 * d1 + d3 * d3) * (double) 0.2F;
        snowball.shoot(d1, d2 + d4, d3, 1.0F, 6.0F);
        snowball.setXRot(snowball.getXRot() - -25.0F);
        ItemStack stack = new ItemStack(ModItems.ALCHEMY_PROJECTILE.get());
        List<AlchemyMaterial> alchemyMaterials = AlchemyUtils.getAlchemyMaterials(this.getItem());
        alchemyMaterials.forEach(alchemyMaterial -> {
            AlchemyUtils.addAlchemyMaterialToItemStack(stack, alchemyMaterial);
        });
        this.setPos(this.getX(), this.getEyeY() + 0.1F, this.getZ());

        snowball.setItem(stack);
        this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(snowball);
    }

    @Override
    public void handleEntityEvent(byte p_21375_) {
        if (p_21375_ == 61) {
            this.targetSquish = 2.0F;
        } else {
            super.handleEntityEvent(p_21375_);
        }
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return this.getItem();
    }

    @Override
    public boolean canBeAffected(MobEffectInstance p_21197_) {
        return false;
    }

    static class SlimeFollowOwnerGoal extends Goal {
        private final AlchemySlime slime;
        private int growTiredTimer;

        public SlimeFollowOwnerGoal(AlchemySlime p_33648_) {
            this.slime = p_33648_;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
        }

        public boolean canUse() {
            Entity livingentity = this.slime.getOwner();
            if (livingentity == null) {
                return false;
            } else {
                return livingentity.isAlive() && this.slime.getAlchemyCommand() == AlchemyCommand.FREE;
            }
        }

        public void start() {
            this.growTiredTimer = reducedTickDelay(300);
            super.start();
        }

        public boolean canContinueToUse() {
            Entity livingentity = this.slime.getOwner();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive() || this.slime.distanceToSqr(livingentity) < 64) {
                return false;
            } else if (this.slime.getAlchemyCommand() != AlchemyCommand.FREE) {
                return false;
            } else {
                return --this.growTiredTimer > 0;
            }
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingentity = this.slime.getTarget();
            if (livingentity != null) {
                this.slime.lookAt(livingentity, 10.0F, 10.0F);
            }
        }
    }
}
