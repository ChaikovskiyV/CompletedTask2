package com.vchaikovsky.shape.comparator;

import com.vchaikovsky.shape.entity.impl.Point;
import com.vchaikovsky.shape.entity.impl.Pyramid;
import com.vchaikovsky.shape.service.impl.ParameterCalculator;
import com.vchaikovsky.shape.warehouse.PyramidsWarehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.function.ToDoubleFunction;

public enum PyramidComparator {
    ID,
    BASES_CENTER_POINT,
    HEIGHT,
    VOLUME,
    SURFACE_SQUARE,
    RADIUS,
    CORNERS_NUMBER;

     static final Logger logger = LogManager.getLogger();

    Comparator<Pyramid> getComparator(){
        return switch (this) {
            case ID -> Comparator.comparingLong(Pyramid::getId);
            case RADIUS -> Comparator.comparingDouble(Pyramid::getCircumcircleRadius);
            case CORNERS_NUMBER -> Comparator.comparingInt(Pyramid::getBasesCornersNumber);
            case HEIGHT -> Comparator.comparingDouble((ToDoubleFunction<? super Pyramid>) p->
                    PyramidsWarehouse.getInstance()
                            .get(p.getId())
                            .height());
            case VOLUME -> Comparator.comparingDouble((ToDoubleFunction<? super Pyramid>) p->
                    PyramidsWarehouse
                            .getInstance()
                            .get(p.getId())
                            .pyramidVolume());
            case SURFACE_SQUARE -> Comparator.comparingDouble((ToDoubleFunction<? super Pyramid>) p->
                    PyramidsWarehouse
                            .getInstance()
                            .get(p.getId())
                            .surfaceSquare());
            case BASES_CENTER_POINT -> Comparator.comparingDouble((ToDoubleFunction<? super Pyramid>) p-> {
                        Point basesCenter = p.getBasesCenter();
                        return ParameterCalculator
                                .getInstance()
                                .findDistanceToZeroPoint(basesCenter);
                        });
            default -> Comparator.comparingLong(Pyramid::getId);
        };
    }
}