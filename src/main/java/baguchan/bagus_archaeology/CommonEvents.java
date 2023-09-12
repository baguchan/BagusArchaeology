package baguchan.bagus_archaeology;

import baguchan.bagus_archaeology.registry.ModBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.NoteBlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RelicsAndAlchemy.MODID)
public class CommonEvents {
    @SubscribeEvent
    public static void noteBlockPlay(NoteBlockEvent.Play event) {
        BlockState stateAbove = event.getLevel().getBlockState(event.getPos().above());
        if (stateAbove.is(ModBlocks.PIGMAN_SKULL.get())) {
            event.setCanceled(true);
            event.getLevel().playSound(null, event.getPos(), SoundEvents.ZOMBIFIED_PIGLIN_ANGRY, SoundSource.RECORDS);
        }
    }

    @SubscribeEvent
    public static void noteBlockPlay(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().getBlockState(event.getPos()).is(Blocks.CAULDRON)) {
            if (event.getItemStack().is(Items.WOODEN_SHOVEL)) {
                event.getLevel().setBlock(event.getPos(), ModBlocks.ALCHEMY_CAULDRON.get().defaultBlockState(), 3);
                event.setUseItem(Event.Result.ALLOW);
                event.setCanceled(true);
            }
        }
    }
}
