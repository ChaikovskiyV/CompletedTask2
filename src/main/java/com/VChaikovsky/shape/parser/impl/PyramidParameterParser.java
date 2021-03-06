package com.vchaikovsky.shape.parser.impl;

import com.vchaikovsky.shape.exception.ShapeException;
import com.vchaikovsky.shape.parser.PyramidParameterParserInt;
import com.vchaikovsky.shape.validator.impl.DataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Stream;

public class PyramidParameterParser implements PyramidParameterParserInt {
    static final Logger logger = LogManager.getLogger();
    private static final String DELIMITER = "\\s";
    private static PyramidParameterParser instance;

    private PyramidParameterParser() {}

    public static PyramidParameterParser getInstance() {
        if(instance == null) {
            instance = new PyramidParameterParser();
        }
        return instance;
    }

    @Override
    public List<double[]> parseStrToPyramidParam(List<String> list) throws ShapeException {
        DataValidator validator = DataValidator.getInstance();
        List<double[]> arraysList;

        if(list.isEmpty()) {
            logger.error("Source data is empty or wrong.");
            throw new ShapeException("Source data is empty or wrong.");
        }
            arraysList = list
                    .stream()
                    .map(x->x.split(DELIMITER))
                    .filter(validator :: isValidParam)
                    .map(this::parseStrArrToDoubleArr)
                    .toList();

        return arraysList;
    }

    private double[] parseStrArrToDoubleArr(String[] strArray){
        Double[] doubles = Stream
                .of(strArray)
                .map(Double :: parseDouble)
                .toArray(Double[]::new);

        return parseDoubleArrToPrimitive(doubles);
    }

    private double[] parseDoubleArrToPrimitive(Double [] array){
        double[] newArray = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        return newArray;
    }
}