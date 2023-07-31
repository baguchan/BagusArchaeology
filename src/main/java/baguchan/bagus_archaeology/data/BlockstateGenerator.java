package baguchan.bagus_archaeology.data;

import baguchan.bagus_archaeology.BagusArchaeology;
import baguchan.bagus_archaeology.registry.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockstateGenerator extends BlockStateProvider {
    public BlockstateGenerator(PackOutput gen, ExistingFileHelper exFileHelper) {
        super(gen, BagusArchaeology.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.cutoutBlock(ModBlocks.SOUL_RECOVER.get());
    }

    public void cutoutBlock(Block block) {
        simpleBlock(block, cutoutCubeAll(block));
    }

    private ModelFile cutoutCubeAll(Block block) {
        return models().cubeAll(name(block), blockTexture(block)).renderType("minecraft:cutout");
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
