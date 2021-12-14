package com.srp;

import java.util.List;

import com.srp.Car;

public class CarRater
{
    public Car getBestCar(List<Car> cars)
    {
        Car bestCar = null;
        for (Car car : cars) {
            if (bestCar == null || car.getModel().compareTo(bestCar.getModel()) > 0) {
                bestCar = car;
            }
        }
        return bestCar;
    }
}
