package baguchan.bagus_archaeology.event;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public class AlchemyEvent extends Event {
    private final Level level;
    private final ItemStack stack;
    private final boolean simurator;
    private boolean consumeWater = false;

    public AlchemyEvent(ItemStack stack, Level level, boolean simulator) {
        this.level = level;
        this.stack = stack;
        this.simurator = simulator;
    }

    public Level getLevel() {
        return level;
    }

    public ItemStack getStack() {
        return stack;
    }

    public boolean isSimurator() {
        return simurator;
    }

    public boolean isConsumeWater() {
        return consumeWater;
    }

    public void setConsumeWater(boolean consumeWater) {
        this.consumeWater = consumeWater;
    }

    @Cancelable
    public static class Pre extends AlchemyEvent {
        private ItemStack resultStack = ItemStack.EMPTY;

        public Pre(ItemStack stack, Level level, boolean simulator) {
            super(stack, level, simulator);
        }

        public void setResultStack(ItemStack resultStack) {
            this.resultStack = resultStack;
        }
    }

    public static class Post extends AlchemyEvent {
        public Post(ItemStack stack, Level level, boolean simulator) {
            super(stack, level, simulator);
        }
    }
}