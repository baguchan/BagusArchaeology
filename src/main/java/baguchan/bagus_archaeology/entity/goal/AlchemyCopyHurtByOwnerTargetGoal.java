package baguchan.bagus_archaeology.entity.goal;

import baguchan.bagus_archaeology.api.IAlchemyOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class AlchemyCopyHurtByOwnerTargetGoal<T extends Mob & IAlchemyOwner> extends TargetGoal {
    private final TargetingConditions copyOwnerTargeting = TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting();

    public final T alchemyMob;

    public AlchemyCopyHurtByOwnerTargetGoal(T p_34056_) {
        super(p_34056_, false);
        this.alchemyMob = p_34056_;
    }

    public boolean canUse() {
        return this.alchemyMob.getTarget() == null && this.alchemyMob.getOwner() instanceof LivingEntity livingOwner && livingOwner.getLastHurtMob() != null && this.canAttack(livingOwner.getLastHurtMob(), this.copyOwnerTargeting);
    }

    public void start() {
        if (this.alchemyMob.getOwner() instanceof LivingEntity livingOwner) {
            this.alchemyMob.setTarget(livingOwner.getLastHurtMob());
        }
        super.start();
    }
}