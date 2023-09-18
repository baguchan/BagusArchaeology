package baguchan.bagus_archaeology.util;

import baguchan.bagus_archaeology.material.AlchemyMaterial;

public class AlchemyData {
    public final AlchemyMaterial alchemy;
    public final float alchemyScale;

    public AlchemyData(AlchemyMaterial alchemy, float alchemyScale) {
        this.alchemy = alchemy;
        this.alchemyScale = alchemyScale;
    }
}