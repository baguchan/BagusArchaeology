package baguchan.bagus_archaeology.data;

import baguchan.bagus_archaeology.BagusArchaeology;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, BagusArchaeology.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
    }
}
