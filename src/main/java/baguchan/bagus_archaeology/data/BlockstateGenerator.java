package baguchan.bagus_archaeology.data;

import baguchan.bagus_archaeology.BagusArchaeology;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockstateGenerator extends BlockStateProvider {
    public BlockstateGenerator(PackOutput gen, ExistingFileHelper exFileHelper) {
        super(gen, BagusArchaeology.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }

    protected ResourceLocation texture(String name) {
        return modLoc("block/" + name);
    }

    protected String name(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
    }
}
