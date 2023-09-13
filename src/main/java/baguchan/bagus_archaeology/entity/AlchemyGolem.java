package baguchan.bagus_archaeology.entity;

import baguchan.bagus_archaeology.element.AlchemyElement;
import baguchan.bagus_archaeology.material.AlchemyMaterial;
import baguchan.bagus_archaeology.registry.ModItems;
import baguchan.bagus_archaeology.util.AlchemyUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class AlchemyGolem extends AbstractGolem implements TraceableEntity {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(AlchemyGolem.class, EntityDataSerializers.ITEM_STACK);
    private int attackAnimationTick;


    @Nullable
    private UUID ownerUUID;
    @Nullable
    private Entity cachedOwner;


    public AlchemyGolem(EntityType<? extends AlchemyGolem> p_28834_, Level p_28835_) {
        super(p_28834_, p_28835_);
        this.setMaxUpStep(1.0F);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
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

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new AlchemyGolemCopyHurtOwnerTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false, (p_28879_) -> {
            return p_28879_ instanceof Enemy && !(p_28879_ instanceof Creeper);
        }));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 40.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.KNOCKBACK_RESISTANCE, 0.2D).add(Attributes.ATTACK_DAMAGE, 2.0D).add(Attributes.ARMOR_TOUGHNESS).add(Attributes.ARMOR);
    }

    protected InteractionResult mobInteract(Player p_28861_, InteractionHand p_28862_) {
        ItemStack itemstack = p_28861_.getItemInHand(p_28862_);
        if (!itemstack.is(ModItems.ALCHEMY_PROJECTILE.get()) && !AlchemyUtils.hasAlchemyMaterial(itemstack)) {
            return InteractionResult.PASS;
        } else {
            float f = this.getHealth();
            this.heal(10.0F);
            if (this.getHealth() == f) {
                return InteractionResult.PASS;
            } else {
                float f1 = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(SoundEvents.IRON_GOLEM_REPAIR, 1.0F, f1);
                if (!p_28861_.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }
    }

    protected void playStepSound(BlockPos p_28864_, BlockState p_28865_) {
        this.playSound(SoundEvents.STONE_STEP, 0.8F, 1.0F);
    }

    protected int decreaseAirSupply(int p_28882_) {
        return p_28882_;
    }

    protected void doPush(Entity p_28839_) {
        if (p_28839_ instanceof Enemy && !(p_28839_ instanceof Creeper) && this.getRandom().nextInt(20) == 0) {
            this.setTarget((LivingEntity) p_28839_);
        }

        super.doPush(p_28839_);
    }

    public void aiStep() {
        super.aiStep();
        if (this.attackAnimationTick > 0) {
            --this.attackAnimationTick;
        }
    }

    public boolean canSpawnSprintParticle() {
        return this.getDeltaMovement().horizontalDistanceSqr() > (double) 2.5000003E-7F && this.random.nextInt(5) == 0;
    }

    @Override
    public boolean canAttack(LivingEntity p_21171_) {
        if (p_21171_ instanceof TraceableEntity traceableEntity) {
            return traceableEntity.getOwner() == getOwner();
        } else if (p_21171_ instanceof TamableAnimal tamableAnimal) {
            return tamableAnimal.getOwner() == getOwner();
        } else if (p_21171_.getType() == EntityType.CREEPER) {
            return false;
        }
        return super.canAttack(p_21171_);
    }

    @Override
    public boolean isAlliedTo(Entity p_20355_) {
        if (p_20355_ instanceof TraceableEntity traceableEntity) {
            return traceableEntity.getOwner() == getOwner();
        } else if (p_20355_ instanceof TamableAnimal tamableAnimal) {
            return tamableAnimal.getOwner() == getOwner();
        }
        return super.isAlliedTo(p_20355_);
    }

    private float getAttackDamage() {
        return (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    public boolean doHurtTarget(Entity p_28837_) {
        this.attackAnimationTick = 10;
        this.level().broadcastEntityEvent(this, (byte) 4);
        float f = this.getAttackDamage();

        float scale = 0F;
        if (AlchemyUtils.hasAlchemyMaterial(this.getItem())) {
            List<AlchemyMaterial> alchemyMaterialList = AlchemyUtils.getAlchemyMaterials(this.getItem());
            for (AlchemyMaterial alchemyMaterial : alchemyMaterialList) {
                scale += alchemyMaterial.getPower() * this.getDamageScale();
                for (AlchemyElement alchemyElement : alchemyMaterial.getAlchemyElement()) {
                    alchemyElement.entityAttack(this, p_28837_, alchemyMaterial.getItem(), scale);
                    scale *= alchemyElement.getSelfScale();
                }
            }
        }

        boolean flag = p_28837_.hurt(this.damageSources().mobAttack(this), f + scale * 0.5F);
        if (flag) {
            double d2;
            if (p_28837_ instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) p_28837_;
                d2 = livingentity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            } else {
                d2 = 0.0D;
            }

            double d0 = d2;
            double d1 = Math.max(0.0D, 1.0D - d0);
            p_28837_.setDeltaMovement(p_28837_.getDeltaMovement().add(0.0D, (double) 0.3F * d1, 0.0D));
            this.doEnchantDamageEffects(this, p_28837_);
        }

        this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        return flag;
    }

    public float getDamageScale() {
        return 0.25F;
    }

    public boolean hurt(DamageSource p_28848_, float p_28849_) {
        IronGolem.Crackiness irongolem$crackiness = this.getCrackiness();
        boolean flag = super.hurt(p_28848_, p_28849_);
        if (flag && this.getCrackiness() != irongolem$crackiness) {
            this.playSound(SoundEvents.IRON_GOLEM_DAMAGE, 1.0F, 1.0F);
        }

        return flag;
    }

    public IronGolem.Crackiness getCrackiness() {
        return IronGolem.Crackiness.byFraction(this.getHealth() / this.getMaxHealth());
    }

    public void handleEntityEvent(byte p_28844_) {
        if (p_28844_ == 4) {
            this.attackAnimationTick = 10;
            this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        } else {
            super.handleEntityEvent(p_28844_);
        }

    }

    public int getAttackAnimationTick() {
        return this.attackAnimationTick;
    }

    public void setItem(ItemStack p_37447_) {
        if (!p_37447_.is(this.getDefaultItem()) || p_37447_.hasTag()) {
            this.getEntityData().set(DATA_ITEM_STACK, p_37447_.copyWithCount(1));
        }

    }

    public void setStatsFromItem(ItemStack p_37447_) {
        if (!this.level().isClientSide()) {
            float hardness = 0;
            float toughness = 0;

            if (AlchemyUtils.hasAlchemyMaterial(p_37447_)) {
                List<AlchemyMaterial> alchemyMaterialList = AlchemyUtils.getAlchemyMaterials(this.getItem());
                for (AlchemyMaterial alchemyMaterial : alchemyMaterialList) {
                    hardness = alchemyMaterial.getHardness();
                    toughness = alchemyMaterial.getHardness();
                }
            }

            this.getAttribute(Attributes.ARMOR).setBaseValue(this.getAttributeValue(Attributes.ARMOR) + hardness);
            this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(this.getAttributeValue(Attributes.ARMOR_TOUGHNESS) + toughness);
        }
    }

    protected ItemStack getItemRaw() {
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
        return Items.SNOWBALL;
    }

    public class AlchemyGolemCopyHurtOwnerTargetGoal extends TargetGoal {
        private final TargetingConditions copyOwnerTargeting = TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting();

        public AlchemyGolemCopyHurtOwnerTargetGoal(PathfinderMob p_34056_) {
            super(p_34056_, false);
        }

        public boolean canUse() {
            return AlchemyGolem.this.getOwner() instanceof LivingEntity livingOwner && livingOwner.getLastHurtByMob() != null && this.canAttack(livingOwner.getLastHurtByMob(), this.copyOwnerTargeting);
        }

        public void start() {
            if (AlchemyGolem.this.getOwner() instanceof LivingEntity livingOwner) {
                AlchemyGolem.this.setTarget(livingOwner.getLastHurtByMob());
            }
            super.start();
        }
    }

    public class AlchemyGolemCopyOwnerTargetGoal extends TargetGoal {
        private final TargetingConditions copyOwnerTargeting = TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting();

        public AlchemyGolemCopyOwnerTargetGoal(PathfinderMob p_34056_) {
            super(p_34056_, false);
        }

        public boolean canUse() {
            return AlchemyGolem.this.getOwner() instanceof LivingEntity livingOwner && livingOwner.getLastHurtMob() != null && this.canAttack(livingOwner.getLastHurtMob(), this.copyOwnerTargeting);
        }

        public void start() {
            if (AlchemyGolem.this.getOwner() instanceof LivingEntity livingOwner) {
                AlchemyGolem.this.setTarget(livingOwner.getLastHurtMob());
            }
            super.start();
        }
    }
}
