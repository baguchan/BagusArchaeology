package baguchan.bagus_archaeology.entity;

import baguchan.bagus_archaeology.element.AlchemyElement;
import baguchan.bagus_archaeology.material.AlchemyMaterial;
import baguchan.bagus_archaeology.util.AlchemyUtils;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class AlchemyThrown extends ThrowableItemProjectile {
    public AlchemyThrown(EntityType<? extends ThrowableItemProjectile> p_37442_, Level p_37443_) {
        super(p_37442_, p_37443_);
    }

    public AlchemyThrown(EntityType<? extends ThrowableItemProjectile> p_37432_, double p_37433_, double p_37434_, double p_37435_, Level p_37436_) {
        super(p_37432_, p_37433_, p_37434_, p_37435_, p_37436_);
    }

    public AlchemyThrown(EntityType<? extends ThrowableItemProjectile> p_37438_, LivingEntity p_37439_, Level p_37440_) {
        super(p_37438_, p_37439_, p_37440_);
    }

    protected Item getDefaultItem() {
        return Items.SNOWBALL;
    }

    private ParticleOptions getParticle() {
        ItemStack itemstack = this.getItemRaw();
        return (ParticleOptions) (itemstack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleOption(ParticleTypes.ITEM, itemstack));
    }

    public void handleEntityEvent(byte p_37402_) {
        if (p_37402_ == 3) {
            ParticleOptions particleoptions = this.getParticle();

            for (int i = 0; i < 8; ++i) {
                this.level().addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    protected void onHitEntity(EntityHitResult p_37404_) {
        super.onHitEntity(p_37404_);
        float scale = 0F;
        if (AlchemyUtils.hasAlchemyMaterial(this.getItem())) {
            List<AlchemyMaterial> alchemyMaterialList = AlchemyUtils.getAlchemyMaterials(this.getItem());
            for (AlchemyMaterial alchemyMaterial : alchemyMaterialList) {
                scale += alchemyMaterial.getPower();
                for (AlchemyElement alchemyElement : alchemyMaterial.getAlchemyElement()) {
                    alchemyElement.projectileHit(this, p_37404_, alchemyMaterial.getItem(), scale);
                    scale *= alchemyElement.getSelfScale();
                }
            }
        }
        makeDiscard();
    }

    @Override
    protected void onHitBlock(BlockHitResult p_37258_) {
        super.onHitBlock(p_37258_);
        float scale = 0F;
        if (AlchemyUtils.hasAlchemyMaterial(this.getItem())) {
            List<AlchemyMaterial> alchemyMaterialList = AlchemyUtils.getAlchemyMaterials(this.getItem());
            for (AlchemyMaterial alchemyMaterial : alchemyMaterialList) {
                scale += alchemyMaterial.getPower();
                for (AlchemyElement alchemyElement : alchemyMaterial.getAlchemyElement()) {
                    alchemyElement.projectileHit(this, p_37258_, alchemyMaterial.getItem(), scale);
                    scale *= alchemyElement.getSelfScale();
                }
            }
        }
        makeDiscard();
    }

    public void makeDiscard() {
        if (!this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }
}
