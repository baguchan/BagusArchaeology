package baguchan.bagus_archaeology.api;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TraceableEntity;

import javax.annotation.Nullable;

public interface IAlchemyOwner extends TraceableEntity {
    void setOwner(@Nullable Entity p_37263_);

    void setAlchemyCommand(AlchemyCommand command);

    AlchemyCommand getAlchemyCommand();
}
