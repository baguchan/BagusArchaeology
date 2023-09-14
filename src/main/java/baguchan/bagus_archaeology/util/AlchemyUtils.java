package baguchan.bagus_archaeology.util;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import baguchan.bagus_archaeology.element.AlchemyElement;
import baguchan.bagus_archaeology.material.AlchemyMaterial;
import com.google.common.collect.Lists;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class AlchemyUtils {
    public static final String TAG_ALCHEMY_ELEMENT = "AlchemyElement";
    public static final String TAG_STORED_ALCHEMY_MATERIAL = "StoredAlchemyMaterials";
    public static final String TAG_ALCHEMY_MATERIAL = "AlchemyMaterial";
    public static final String TAG_ALCHEMY_SCALE = "AlchemyMaterial";

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
     * get Mob Enchantments From ItemStack
     *
     * @param stack AlchemyElemented Item
     */
    public static List<AlchemyMaterial> getAlchemyMaterials(ItemStack stack) {
        ListTag listnbt = getAlchemyMaterialListForNBT(stack.getTag());
        return makeAlchemyMaterialListFromListNBT(listnbt);
    }

    private static List<AlchemyMaterial> makeAlchemyMaterialListFromListNBT(ListTag p_226652_0_) {
        List<AlchemyMaterial> linkedList = Lists.newLinkedList();

        for (int i = 0; i < p_226652_0_.size(); ++i) {
            CompoundTag compoundnbt = p_226652_0_.getCompound(i);
            AlchemyMaterial alchemy = getAlchemyMaterialFromString(compoundnbt.getString(TAG_ALCHEMY_MATERIAL));
            if (alchemy != null) {
                linkedList.add(alchemy);
            }
        }

        return linkedList;
    }

    public static void addAlchemyMaterialToItemStack(ItemStack itemIn, ItemStack alchemyMaterial) {
        ListTag listnbt = getAlchemyMaterialListForNBT(itemIn.getTag());

        Optional<Holder.Reference<AlchemyMaterial>> referenceOptional = RelicsAndAlchemy.registryAccess().lookup(AlchemyMaterial.REGISTRY_KEY).get().listElements().filter(alchemyMaterialReference -> {
            return alchemyMaterial.is(alchemyMaterialReference.get().getItem());
        }).findFirst();

        if (referenceOptional.isPresent()) {
            CompoundTag compoundnbt1 = new CompoundTag();
            compoundnbt1.putString(TAG_ALCHEMY_MATERIAL, String.valueOf(referenceOptional.get().key().location()));
            listnbt.add(compoundnbt1);
        }

        itemIn.getOrCreateTag().put(TAG_STORED_ALCHEMY_MATERIAL, listnbt);
    }

    public static void addAlchemyMaterialToItemStack(ItemStack itemIn, AlchemyMaterial alchemyMaterial) {
        ListTag listnbt = getAlchemyMaterialListForNBT(itemIn.getTag());

        ResourceLocation resourcelocation = RelicsAndAlchemy.registryAccess().registryOrThrow(AlchemyMaterial.REGISTRY_KEY).getKey(alchemyMaterial);


            CompoundTag compoundnbt1 = new CompoundTag();
        compoundnbt1.putString(TAG_ALCHEMY_MATERIAL, String.valueOf(resourcelocation));
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

    public static int getAlchemyMaterialToughness(List<AlchemyMaterial> list) {
        int count = 0;
        for (AlchemyMaterial element : list) {
            count += (int) element.getToughness();
        }
        return count;
    }

    public static int getAlchemyMaterialHardness(List<AlchemyMaterial> list) {
        int count = 0;
        for (AlchemyMaterial element : list) {
            count += (int) element.getHardness();
        }
        return count;
    }
}
