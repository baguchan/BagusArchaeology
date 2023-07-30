package baguchan.bagus_archaeology;

import baguchan.bagus_archaeology.registry.ModBlocks;
import baguchan.bagus_archaeology.registry.ModItems;
import baguchan.earthmobsmod.entity.SkeletonWolf;
import baguchan.earthmobsmod.registry.ModEntities;
import baguchan.earthmobsmod.registry.ModSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.level.NoteBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BagusArcheology.MODID)
public class CommonEvents {
    @SubscribeEvent
    public static void visionPercent(LivingEvent.LivingVisibilityEvent event) {
        if (event.getLookingEntity() != null) {
            ItemStack itemstack = event.getEntity().getItemBySlot(EquipmentSlot.HEAD);
            if ((event.getLookingEntity() instanceof SkeletonWolf || event.getLookingEntity() instanceof Skeleton) && (itemstack.is(ModItems.SKELETON_WOLF_HEAD.get()))) {
                event.modifyVisibility(0.75D);
            }
            if ((event.getLookingEntity() instanceof SkeletonWolf || event.getLookingEntity() instanceof Skeleton) && (itemstack.is(ModItems.WITHER_SKELETON_WOLF_HEAD.get()))) {
                event.modifyVisibility(0.75D);
            }
        }
    }

    @SubscribeEvent
    public static void onLootDropEntity(LivingDropsEvent event) {
        if (event.getSource().getEntity() instanceof Creeper creeper) {
            if (creeper.canDropMobsSkull()) {
                if (event.getEntity().getType() == ModEntities.SKELETON_WOLF.get()) {
                    event.getEntity().spawnAtLocation(ModItems.SKELETON_WOLF_HEAD.get());
                } else if (event.getEntity().getType() == ModEntities.WITHER_SKELETON_WOLF.get()) {
                    event.getEntity().spawnAtLocation(ModItems.WITHER_SKELETON_WOLF_HEAD.get());
                }
                creeper.increaseDroppedSkulls();
            }
        }
    }

    @SubscribeEvent
    public static void noteBlockPlay(NoteBlockEvent.Play event) {
        BlockState stateAbove = event.getLevel().getBlockState(event.getPos().above());
        if (stateAbove.is(ModBlocks.SKELETON_WOLF_HEAD.get())) {
            event.setCanceled(true);
            event.getLevel().playSound(null, event.getPos(), ModSounds.SKELETON_WOLF_BARK.get(), SoundSource.RECORDS);
        }
        if (stateAbove.is(ModBlocks.WITHER_SKELETON_WOLF_HEAD.get())) {
            event.setCanceled(true);
            event.getLevel().playSound(null, event.getPos(), ModSounds.SKELETON_WOLF_BARK.get(), SoundSource.RECORDS);
        }
        if (stateAbove.is(ModBlocks.PIGMAN_SKULL.get())) {
            event.setCanceled(true);
            event.getLevel().playSound(null, event.getPos(), SoundEvents.ZOMBIFIED_PIGLIN_ANGRY, SoundSource.RECORDS);
        }
    }
}
