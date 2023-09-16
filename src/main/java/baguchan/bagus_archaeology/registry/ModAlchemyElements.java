package baguchan.bagus_archaeology.registry;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import baguchan.bagus_archaeology.element.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = RelicsAndAlchemy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModAlchemyElements {
    public static final DeferredRegister<AlchemyElement> ALCHEMY_ELEMENT = DeferredRegister.create(new ResourceLocation(RelicsAndAlchemy.MODID, "alchemy_element"), RelicsAndAlchemy.MODID);
    public static final Supplier<IForgeRegistry<AlchemyElement>> ALCHEMY_ELEMENT_REGISTRY = ALCHEMY_ELEMENT.makeRegistry(
            () -> new RegistryBuilder<AlchemyElement>()
                    .addCallback(AlchemyElement.class)
                    .setName(new ResourceLocation(RelicsAndAlchemy.MODID, "alchemy_element"))
                    .setDefaultKey(new ResourceLocation(RelicsAndAlchemy.MODID, "thick")));

    public static final RegistryObject<AlchemyElement> GLOW = ALCHEMY_ELEMENT.register("thick", () -> new ThickElement(new AlchemyElement.Properties(new AlchemyElement.AlchemyType[]{AlchemyElement.AlchemyType.CORE})));
    public static final RegistryObject<AlchemyElement> EXTEND = ALCHEMY_ELEMENT.register("extend", () -> new ThickElement(new AlchemyElement.Properties(new AlchemyElement.AlchemyType[]{AlchemyElement.AlchemyType.CORE})));
    public static final RegistryObject<AlchemyElement> SOUL = ALCHEMY_ELEMENT.register("soul", () -> new SoulEchoElement(new AlchemyElement.Properties(new AlchemyElement.AlchemyType[]{AlchemyElement.AlchemyType.PROJECTILE})));
    public static final RegistryObject<AlchemyElement> SUGAR = ALCHEMY_ELEMENT.register("sugar", () -> new SugarElement(new AlchemyElement.Properties(new AlchemyElement.AlchemyType[]{AlchemyElement.AlchemyType.NETURAL})));
    public static final RegistryObject<AlchemyElement> WARPED = ALCHEMY_ELEMENT.register("warped", () -> new WarpedElement(new AlchemyElement.Properties(new AlchemyElement.AlchemyType[]{AlchemyElement.AlchemyType.PROJECTILE})));
    public static final RegistryObject<AlchemyElement> EXPLOSION = ALCHEMY_ELEMENT.register("explosion", () -> new ExplosionElement(new AlchemyElement.Properties(new AlchemyElement.AlchemyType[]{AlchemyElement.AlchemyType.PROJECTILE})));
    public static final RegistryObject<AlchemyElement> USABLE = ALCHEMY_ELEMENT.register("usable", () -> new UsableElement(new AlchemyElement.Properties(new AlchemyElement.AlchemyType[]{AlchemyElement.AlchemyType.SELF})));
    public static final RegistryObject<AlchemyElement> FLOWER = ALCHEMY_ELEMENT.register("flower", () -> new FlowerElemnt(new AlchemyElement.Properties(new AlchemyElement.AlchemyType[]{AlchemyElement.AlchemyType.SELF})));
    public static final RegistryObject<AlchemyElement> FREEZE = ALCHEMY_ELEMENT.register("freeze", () -> new FreezeElement(new AlchemyElement.Properties(new AlchemyElement.AlchemyType[]{AlchemyElement.AlchemyType.PROJECTILE})));
    public static final RegistryObject<AlchemyElement> TELEPORT = ALCHEMY_ELEMENT.register("teleport", () -> new TeleportElement(new AlchemyElement.Properties(new AlchemyElement.AlchemyType[]{AlchemyElement.AlchemyType.PROJECTILE})));
    public static final RegistryObject<AlchemyElement> FIRE = ALCHEMY_ELEMENT.register("fire", () -> new FireElement(new AlchemyElement.Properties(new AlchemyElement.AlchemyType[]{AlchemyElement.AlchemyType.PROJECTILE})));

}