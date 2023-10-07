package baguchan.bagus_archaeology.util;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import baguchan.bagus_archaeology.element.AlchemyElement;
import baguchan.bagus_archaeology.material.AlchemyMaterial;
import com.google.common.collect.Lists;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class AlchemyUtils {
    public static final String TAG_ALCHEMY_ELEMENT = "AlchemyElement";
    public static final String TAG_STORED_ALCHEMY_MATERIAL = "StoredAlchemyMaterials";
    public static final String TAG_ALCHEMY_MATERIAL = "AlchemyMaterial";
    public static final String TAG_ALCHEMY_SCALE = "AlchemyMaterialScale";


    public static float getEnchantLevelFromNBT(@Nullable CompoundTag tag) {
        if (tag != null) {
            return tag.getFloat(TAG_ALCHEMY_SCALE);
        } else {
            return 0;
        }
    }
    /**
     * get AlchemyMaterial From NBT
     *
     * @param tag nbt tag
     */
    @Nullable
    public static AlchemyMaterial getAlchemyMaterialFromNBT(@Nullable CompoundTag tag) {
        if (tag != null) {
            return RelicsAndAlchemy.registryAccess().registryOrThrow(AlchemyMaterial.REGISTRY_KEY).get(ResourceLocation.tryParse(tag.getString(TAG_ALCHEMY_MATERIAL)));
        } else {
            return null;
        }
    }

    @Nullable
    public static AlchemyMaterial getAlchemyMaterialFromString(@Nullable String id) {
        if (id != null) {
            return RelicsAndAlchemy.registryAccess().registryOrThrow(AlchemyMaterial.REGISTRY_KEY).get(ResourceLocation.tryParse(id));
        } else {
            return null;
        }
    }


    /**
     * check ItemStack has AlchemyElement
     *
     * @param stack AlchemyElemented Item
     */
    public static boolean hasAlchemyMaterial(ItemStack stack) {
        CompoundTag compoundnbt = stack.getTag();
        return compoundnbt != null && compoundnbt.contains(TAG_STORED_ALCHEMY_MATERIAL);
    }

    /**
     * check NBT has AlchemyElement
     *
     * @param compoundnbt nbt tag
     */
    public static ListTag getAlchemyMaterialListForNBT(CompoundTag compoundnbt) {
        return compoundnbt != null ? compoundnbt.getList(TAG_STORED_ALCHEMY_MATERIAL, 10) : new ListTag();
    }

    /**
     * get Alchemy Material From ItemStack
     *
     * @param stack AlchemyElemented Item
     */
    public static List<AlchemyData> getAlchemyMaterials(ItemStack stack) {
        ListTag listnbt = getAlchemyMaterialListForNBT(stack.getTag());
        return makeAlchemyMaterialListFromListNBT(listnbt);
    }

    private static List<AlchemyData> makeAlchemyMaterialListFromListNBT(ListTag p_226652_0_) {
        List<AlchemyData> map = Lists.newLinkedList();

        for (int i = 0; i < p_226652_0_.size(); ++i) {
            CompoundTag compoundnbt = p_226652_0_.getCompound(i);
            AlchemyMaterial mobEnchant = getAlchemyMaterialFromString(compoundnbt.getString(TAG_ALCHEMY_MATERIAL));
            map.add(new AlchemyData(mobEnchant, compoundnbt.getFloat(TAG_ALCHEMY_SCALE) + 1.0F));

        }

        return map;
    }

    public static void addAlchemyMaterialToItemStackWithRandom(ItemStack itemIn, ItemStack alchemyMaterial, RandomSource randomSource) {
        ListTag listnbt = getAlchemyMaterialListForNBT(itemIn.getTag());

        Optional<Holder.Reference<AlchemyMaterial>> referenceOptional = RelicsAndAlchemy.registryAccess().lookup(AlchemyMaterial.REGISTRY_KEY).get().listElements().filter(alchemyMaterialReference -> {
            return alchemyMaterial.is(alchemyMaterialReference.get().getItem());
        }).findFirst();

        if (referenceOptional.isPresent()) {
            CompoundTag compoundnbt1 = new CompoundTag();
            compoundnbt1.putString(TAG_ALCHEMY_MATERIAL, String.valueOf(referenceOptional.get().key().location()));
            compoundnbt1.putFloat(TAG_ALCHEMY_SCALE, (float) (randomSource.nextDouble() * referenceOptional.get().get().getPowerBalance()));
            listnbt.add(compoundnbt1);

        }

        itemIn.getOrCreateTag().put(TAG_STORED_ALCHEMY_MATERIAL, listnbt);
    }

    public static void addAlchemyMaterialToItemStackWithRandom(ItemStack itemIn, AlchemyMaterial alchemyMaterial, RandomSource randomSource) {
        ListTag listnbt = getAlchemyMaterialListForNBT(itemIn.getTag());

        ResourceLocation resourcelocation = RelicsAndAlchemy.registryAccess().registryOrThrow(AlchemyMaterial.REGISTRY_KEY).getKey(alchemyMaterial);


            CompoundTag compoundnbt1 = new CompoundTag();
        compoundnbt1.putString(TAG_ALCHEMY_MATERIAL, String.valueOf(resourcelocation));
        compoundnbt1.putFloat(TAG_ALCHEMY_SCALE, (float) (randomSource.nextDouble() * alchemyMaterial.getPowerBalance()));
            listnbt.add(compoundnbt1);

        itemIn.getTag().put(TAG_STORED_ALCHEMY_MATERIAL, listnbt);
    }

    public static void addAlchemyMaterialToItemStack(ItemStack itemIn, ItemStack alchemyMaterial, float scale) {
        ListTag listnbt = getAlchemyMaterialListForNBT(itemIn.getTag());

        Optional<Holder.Reference<AlchemyMaterial>> referenceOptional = RelicsAndAlchemy.registryAccess().lookup(AlchemyMaterial.REGISTRY_KEY).get().listElements().filter(alchemyMaterialReference -> {
            return alchemyMaterial.is(alchemyMaterialReference.get().getItem());
        }).findFirst();

        if (referenceOptional.isPresent()) {
            CompoundTag compoundnbt1 = new CompoundTag();
            compoundnbt1.putString(TAG_ALCHEMY_MATERIAL, String.valueOf(referenceOptional.get().key().location()));
            compoundnbt1.putFloat(TAG_ALCHEMY_SCALE, 1.0F - scale);
            listnbt.add(compoundnbt1);

        }

        itemIn.getOrCreateTag().put(TAG_STORED_ALCHEMY_MATERIAL, listnbt);
    }

    public static void addAlchemyMaterialToItemStack(ItemStack itemIn, AlchemyMaterial alchemyMaterial, float scale) {
        ListTag listnbt = getAlchemyMaterialListForNBT(itemIn.getTag());

        ResourceLocation resourcelocation = RelicsAndAlchemy.registryAccess().registryOrThrow(AlchemyMaterial.REGISTRY_KEY).getKey(alchemyMaterial);


        CompoundTag compoundnbt1 = new CompoundTag();
        compoundnbt1.putString(TAG_ALCHEMY_MATERIAL, String.valueOf(resourcelocation));
        compoundnbt1.putFloat(TAG_ALCHEMY_SCALE, 1.0F - scale);
        listnbt.add(compoundnbt1);

        itemIn.getOrCreateTag().put(TAG_STORED_ALCHEMY_MATERIAL, listnbt);
    }

    public static boolean findAlchemyElementHandler(List<AlchemyElement> list, AlchemyElement findAlchemyElement) {
        for (AlchemyElement element : list) {
            if (element.equals(findAlchemyElement)) {
                return true;
            }
        }
        return false;
    }

    public static int getAlchemyElementHandlerSize(List<AlchemyElement> list, AlchemyElement findAlchemyElement) {
        int count = 0;
        for (AlchemyElement element : list) {
            if (element.equals(findAlchemyElement)) {
                ++count;
            }
        }
        return count;
    }

    public static float getAlchemyMaterialToughness(List<AlchemyData> list) {
        float count = 0;
        for (AlchemyData element : list) {
            count += (int) element.alchemy.getToughness();
        }
        return count;
    }

    public static float getAlchemyMaterialHardness(List<AlchemyData> list) {
        float count = 0;
        for (AlchemyData element : list) {
            count += (int) element.alchemy.getHardness();
        }
        return count;
    }
}
