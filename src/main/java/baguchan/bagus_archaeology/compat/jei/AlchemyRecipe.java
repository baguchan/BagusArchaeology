package baguchan.bagus_archaeology.compat.jei;

import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.List;

public class AlchemyRecipe {
    private final List<ItemStack> inputs;

    public AlchemyRecipe(Collection<ItemStack> input) {
        this.inputs = List.copyOf(input);
    }


    public List<ItemStack> getInputs() {
        return inputs;
    }
}
