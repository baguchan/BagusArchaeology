package baguchan.bagus_archaeology.entity.goal;

import baguchan.bagus_archaeology.entity.AlchemySlime;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class AlchemySlimeSpitGoal extends Goal {
    public final AlchemySlime alchemySlime;

    public int tick;

    public AlchemySlimeSpitGoal(AlchemySlime mob) {
        this.alchemySlime = mob;
    }

    @Override
    public boolean canUse() {
        return this.alchemySlime.isAlive() && this.alchemySlime.getTarget() != null && this.alchemySlime.canAttack(this.alchemySlime.getTarget()) && this.alchemySlime.getSensing().hasLineOfSight(this.alchemySlime.getTarget()) && this.alchemySlime.getTarget().isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        return this.alchemySlime.isAlive() && this.alchemySlime.getTarget() != null && this.alchemySlime.canAttack(this.alchemySlime.getTarget()) && this.alchemySlime.getSensing().hasLineOfSight(this.alchemySlime.getTarget()) && this.alchemySlime.getTarget().isAlive();
    }

    public void tick() {
        LivingEntity livingentity = this.alchemySlime.getTarget();

        if (livingentity != null && --this.tick < 0) {
            double d0 = this.alchemySlime.getPerceivedTargetDistanceSquareForMeleeAttack(livingentity);
            this.checkAndPerformAttack(livingentity, d0);
        }
    }

    protected void checkAndPerformAttack(LivingEntity p_25557_, double p_25558_) {
        double d0 = this.getAttackReachSqr(p_25557_);
        if (p_25558_ <= d0) {
            if (this.alchemySlime.onGround()) {
                this.alchemySlime.attack();
                this.tick = 80 - 20 * this.alchemySlime.getSize();
            }
        }

    }

    protected double getAttackReachSqr(LivingEntity p_25556_) {
        return (double) 18 * 18;
    }
}