package baguchan.bagus_archaeology.material;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import baguchan.bagus_archaeology.element.AlchemyElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.List;

public class AlchemyMaterial {
    public static final Codec<AlchemyMaterial> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(instance2 -> instance2.getItem()),
                    AlchemyElement.CODEC.listOf().fieldOf("alchemy_element").orElse(List.of()).forGetter(alchemyMaterial -> alchemyMaterial.getAlchemyElement()),
                    Codec.FLOAT.fieldOf("power").forGetter(alchemyMaterial -> alchemyMaterial.getPower()))
            .apply(instance, (Item item1, List<AlchemyElement> alchemyMaterial1, Float scale) -> new AlchemyMaterial(item1, alchemyMaterial1, scale))
    );

    public static final ResourceKey<Registry<AlchemyMaterial>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(RelicsAndAlchemy.MODID, "alchemy_material"));
    private final Item item;
    private final List<AlchemyElement> alchemyElement;
    private final float scale;

    public AlchemyMaterial(Item item, List<AlchemyElement> alchemyElement, float scale) {
        this.item = item;
        this.alchemyElement = alchemyElement;
        this.scale = scale;
    }

    public Item getItem() {
        return item;
    }

    public List<AlchemyElement> getAlchemyElement() {
        return alchemyElement;
    }

    public float getPower() {
        return scale;
    }
}