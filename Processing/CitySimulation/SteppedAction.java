package actions;

import city.City;

public abstract class SteppedAction extends Action {
    private int currentStep = 0;
    private int steps = 0;

    public SteppedAction(int steps) {
        this.steps = steps;
    }

    @Override
    public boolean run(City city) {
        this.runAction(city, currentStep++);
        return currentStep == steps;
    }

    public abstract void runAction(City city, int step);
}
