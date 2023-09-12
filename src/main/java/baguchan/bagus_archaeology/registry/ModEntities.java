package baguchan.bagus_archaeology.registry;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import baguchan.bagus_archaeology.entity.AlchemyThrown;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static baguchan.bagus_archaeology.RelicsAndAlchemy.prefix;

@Mod.EventBusSubscriber(modid = RelicsAndAlchemy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES_REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RelicsAndAlchemy.MODID);

    public static final RegistryObject<EntityType<AlchemyThrown>> ALCHEMY_THROWN = ENTITIES_REGISTRY.register("alchemy_thrown", () -> EntityType.Builder.<AlchemyThrown>of(AlchemyThrown::new, MobCategory.MISC).sized(0.3F, 0.3F).build(prefix("alchemy_thrown").toString()));

}
