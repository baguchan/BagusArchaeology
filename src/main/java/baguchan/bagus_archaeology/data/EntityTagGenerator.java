package baguchan.bagus_archaeology.data;

import baguchan.bagus_archaeology.registry.ModEntities;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class EntityTagGenerator extends EntityTypeTagsProvider {
    public EntityTagGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(EntityTypeTags.FROG_FOOD).add(ModEntities.ALCHEMY_SLIME.get());
    }
}
