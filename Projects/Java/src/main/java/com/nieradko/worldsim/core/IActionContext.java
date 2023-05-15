package com.nieradko.worldsim.core;

import com.nieradko.worldsim.IGUIContext;
import com.nieradko.worldsim.core.animals.Animal;

import java.util.stream.Stream;

public interface IActionContext extends IWorldContext {
    int getNumberOfLivingOrganisms();
    Stream<Animal> getNearbyAnimals(Position position);
    Stream<Organism> getNearbyOrganisms(Position position);
    IGUIContext getGUIContext();
}
