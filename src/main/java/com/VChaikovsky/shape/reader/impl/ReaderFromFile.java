package com.vchaikovsky.shape.reader.impl;

import com.vchaikovsky.shape.exception.ShapeException;
import com.vchaikovsky.shape.reader.ReaderFromFileInt;
import com.vchaikovsky.shape.validator.impl.DataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReaderFromFile implements ReaderFromFileInt {
    static final Logger logger = LogManager.getLogger();
    static ReaderFromFile instance;

    private ReaderFromFile() {}

    public static ReaderFromFile getInstance() {
        if(instance == null) {
            instance = new ReaderFromFile();
        }
        return instance;
    }

    @Override
    public List<String> readData(String filepath) throws ShapeException {
        DataValidator validator = DataValidator.getInstance();
        List<String> strings;
        try {
            strings = Files
                    .lines(Path.of(filepath))
                    .filter(validator :: isValidString)
                    .toList();
        } catch (IOException e) {
            logger.error("File "+filepath+" was not found.", e);
            throw new ShapeException("File "+filepath+" was not found.", e);
        }
        return strings;
    }
}