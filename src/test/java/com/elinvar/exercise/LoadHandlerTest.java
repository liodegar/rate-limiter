package com.elinvar.exercise;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 *
 * Tests the proper functionality of the LoadHandler class
 * Created by Liodegar on 11/28/2018.
 */
public class LoadHandlerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }


    @Test
    public void testReceive() throws Exception {
        // given
        Consumer consumer = new Consumer();
        LoadHandler loadHandler = new LoadHandler(consumer);
        URI uri = ClassLoader.getSystemResource("com/elinvar/exercise").toURI();
        String outputPath = Paths.get(uri).toString();
        Path path = Paths.get(outputPath ,"testOutput.txt");
        //expected text without break lines
        String testOutput = Files.lines(path, Charset.defaultCharset())
                .parallel()
                .map(String::trim)
                .collect(Collectors.joining())
                .replace("\n", "").replace("\r", "");

        // when
        generateUpdates(loadHandler);

        // then
        Assert.assertEquals(outContent.toString().replace("\n", "").replace("\r", ""), testOutput);
    }

    public void generateUpdates(LoadHandler loadHandler) {
        for (int i = 1; i <= 15; i++) {
            loadHandler.receive(new PriceUpdate("Apple", 97.85));
            loadHandler.receive(new PriceUpdate("Google", 160.71));
            loadHandler.receive(new PriceUpdate("Facebook", 91.66));
            loadHandler.receive(new PriceUpdate("Google", 160.73));
            loadHandler.receive(new PriceUpdate("Facebook", 91.71));
            loadHandler.receive(new PriceUpdate("Google", 160.76));
            loadHandler.receive(new PriceUpdate("Apple", 1));
            loadHandler.receive(new PriceUpdate("Google", 2));
            loadHandler.receive(new PriceUpdate("Facebook", 3));
        }
    }
}