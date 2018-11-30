package edu.web.cms.page;

/**
 * Types of children pages list on the container page
 */
public enum ContainerType {
    NONE(0), TILES(1), SIDE(2);

    private final int value;
    private ContainerType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
