package baguchan.bagus_archaeology.material;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import baguchan.bagus_archaeology.element.AlchemyElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AlchemyMaterial {
    public static final Codec<AlchemyMaterial> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(instance2 -> instance2.getItem()),
                    AlchemyElement.CODEC.listOf().fieldOf("alchemy_element").orElse(List.of()).forGetter(alchemyMaterial -> alchemyMaterial.getAlchemyElement()),
                    Codec.FLOAT.fieldOf("power").orElse(0F).forGetter(alchemyMaterial -> alchemyMaterial.getPower()),
                    Codec.FLOAT.fieldOf("hardness").orElse(0F).forGetter(alchemyMaterial -> alchemyMaterial.getHardness()),
                    Codec.FLOAT.fieldOf("toughness").orElse(0F).forGetter(alchemyMaterial -> alchemyMaterial.getToughness()))
            .apply(instance, (Item item1, List<AlchemyElement> alchemyMaterial1, Float scale, Float hardness, Float toughness) -> new AlchemyMaterial(item1, alchemyMaterial1, scale, hardness, toughness))
    );

    public static final ResourceKey<Registry<AlchemyMaterial>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(RelicsAndAlchemy.MODID, "alchemy_material"));
    private final Item item;
    private final List<AlchemyElement> alchemyElement;
    private final float power;
    private final float hardness;
    private final float toughness;

    public AlchemyMaterial(Item item, List<AlchemyElement> alchemyElement, float power, float hardness, float toughness) {
        this.item = item;
        this.alchemyElement = alchemyElement;
        this.power = power;
        this.hardness = hardness;
        this.toughness = toughness;
    }

    public Item getItem() {
        return item;
    }

    public List<AlchemyElement> getAlchemyElement() {
        return alchemyElement;
    }

    public float getPower() {
        return power;
    }

    public float getHardness() {
        return hardness;
    }

    public float getToughness() {
        return toughness;
    }

    public final boolean isUsableDrink() {
        return (this.alchemyElement.stream().noneMatch(alchemyElement1 -> {
            return !alchemyElement1.isUsableDrink();
        }) || this.alchemyElement.isEmpty()) && this.hardness <= 0 && this.toughness <= 0;
    }

    public final boolean isUsableConstruct() {
        return this.alchemyElement.stream().noneMatch(alchemyElement1 -> {
            return !alchemyElement1.isUsableConstruct();
        }) || this.alchemyElement.isEmpty();
    }

    public Component getName() {
        return this.getItem().getName(new ItemStack(this.getItem()));
    }
}