package me.squidxtv.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class Day {

    private static final String PATH = "/y%d/day%02d.txt";
    protected final List<String> input;

    protected Day(int day, int year) throws URISyntaxException, IOException {
        URL path = getClass().getResource(PATH.formatted(year, day));
        input = Files.readAllLines(Path.of(path.toURI()));
    }

    public abstract void execute();

}
