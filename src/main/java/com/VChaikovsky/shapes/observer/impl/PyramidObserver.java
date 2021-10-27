package com.vchaikovsky.shapes.observer.impl;

import com.vchaikovsky.shapes.entity.impl.Pyramid;
import com.vchaikovsky.shapes.entity.impl.PyramidParameters;
import com.vchaikovsky.shapes.event.PyramidEvent;
import com.vchaikovsky.shapes.exception.ShapeException;
import com.vchaikovsky.shapes.observer.Observer;
import com.vchaikovsky.shapes.service.impl.ParameterCalculator;
import com.vchaikovsky.shapes.warehouse.PyramidsWarehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PyramidObserver implements Observer {
    static final Logger logger = LogManager.getLogger();

    @Override
    public void parameterChanged(PyramidEvent event) throws ShapeException {
        PyramidsWarehouse warehouse = PyramidsWarehouse.getInstance();
        ParameterCalculator calculator = ParameterCalculator.getInstance();

        Pyramid pyramid = event.getPyramid();
        long id = pyramid.getId();

        double height = calculator.findPyramidHeight(pyramid);
        double basesSide = calculator.findBasesSideLength(pyramid);
        double volume = calculator.findVolume(pyramid);
        double surfaceSquare = calculator.findSurfaceSquare(pyramid);
        boolean isBasesOnBasicPlane = calculator.isBasesOnBasicPlane(pyramid);
        String volumeProportion = calculator.findVolumeProportion(pyramid);

        PyramidParameters pyramidParameters = new PyramidParameters(height, basesSide, volume, surfaceSquare, isBasesOnBasicPlane, volumeProportion);

        warehouse.put(id, pyramidParameters);
    }
}