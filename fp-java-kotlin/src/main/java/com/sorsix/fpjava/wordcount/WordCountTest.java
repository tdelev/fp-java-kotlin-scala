package com.sorsix.fpjava.wordcount;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.regex.Pattern;

public class WordCountTest {

    public static void main(String[] args) throws IOException {
        StringBuilder result = new StringBuilder();
        if (args.length == 0) {
            System.out.println("Provide filename as argument");
            return;
        }
        for (String fileName : args) {
            try {
                long start = System.nanoTime();
                //String wordCount = processFile(fileName);
                FileStat wordCount = pureFunctional(fileName);
                long end = System.nanoTime();
                System.out.printf("Took: %.3fms\n", (end - start) / 1000000.);
                result.append(String.format("%s -> %s\n", fileName, wordCount));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        System.out.println(result.toString());
    }

    /**
     * Solution using {@link BufferedReader} reading line by line
     */
    private static String processFile(String fileName) throws IOException {
        int linesCount = 0;
        int wordsCount = 0;
        int charactersCount = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                linesCount++;
                String[] words = line.split("\\s+");
                wordsCount += words.length;
                charactersCount += line.length() + 1;
            }
        }
        return String.format("%d %d %d", linesCount, wordsCount, charactersCount);
    }

    private static FileStat pureFunctional(String fileName) throws IOException {
        return Files.lines(Paths.get(fileName))
                .map(new WcCounter())
                .reduce(FileStat.identity(), FileStat::add);
    }

    static class FileStat {
        private final long lines;
        private final long chars;
        private final long words;

        FileStat(long lines, long chars, long words) {
            this.lines = lines;
            this.chars = chars;
            this.words = words;
        }

        static FileStat identity() {
            return new FileStat(0, 0, 0);
        }

        FileStat add(FileStat stat) {
            return new FileStat(this.lines + stat.lines, this.chars + stat.chars, this.words + stat.words);
        }

        @Override
        public String toString() {
            return String.format("%d %d %d", lines, words, chars);
        }
    }

    static class WcCounter implements Function<String, FileStat> {
        private static final Pattern WORD = Pattern.compile("\\s+");

        @Override
        public FileStat apply(String line) {
            //int words = WORD.split(line).length;
            int words = WordCounter.count(line).count();
            return new FileStat(1, line.length() + 1, words);
        }
    }
}
