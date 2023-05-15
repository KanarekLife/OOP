package com.nieradko.worldsim;

import com.nieradko.worldsim.core.World;

public interface IWorldEventsHandler {
    IGUIContext getGUIContext();
    void updateWorld(World world);
}
