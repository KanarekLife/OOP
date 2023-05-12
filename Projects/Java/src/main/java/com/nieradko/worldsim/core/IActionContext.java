package com.nieradko.worldsim.core;

import com.nieradko.worldsim.core.animals.Animal;

public interface IActionContext extends IWorldContext {
    int getNumberOfLivingOrganisms();
    Iterable<Animal> getNearbyAnimals(SquarePosition position);
}
