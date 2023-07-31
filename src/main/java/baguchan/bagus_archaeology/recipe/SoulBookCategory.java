package baguchan.bagus_archaeology.recipe;

import net.minecraft.util.StringRepresentable;

public enum SoulBookCategory implements StringRepresentable {
    FOSSIL("fossil"),
    GOLEM("golem"),
    MISC("misc");
    private final String name;

    private SoulBookCategory(String p_248549_) {
        this.name = p_248549_;
    }

    public String getSerializedName() {
        return this.name;
    }

    public static SoulBookCategory findByName(String name) {
        for (SoulBookCategory value : values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name;
    }
}