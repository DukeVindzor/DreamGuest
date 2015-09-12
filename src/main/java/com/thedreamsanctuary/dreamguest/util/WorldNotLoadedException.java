package com.thedreamsanctuary.dreamguest.util;

import java.util.UUID;

public class WorldNotLoadedException extends Exception {
    private static final long serialVersionUID = -1186707284579928164L;
    private final String unloadedWorld;

    public WorldNotLoadedException(final UUID worldUID) {
        this.unloadedWorld = worldUID.toString();
    }

    public WorldNotLoadedException(final String worldName) {
        this.unloadedWorld = worldName;
    }

    @Override
    public String getMessage() {
        return "The referenced world (" + this.unloadedWorld + ") is not currenly Loaded";
    }
}
