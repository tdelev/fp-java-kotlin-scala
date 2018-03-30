package com.sorsix.fpjava.wordcount;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class WordCountTest {
    private static final Pattern WORD = Pattern.compile("\\s+");
    static final int N = 50;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Provide filename as argument");
            return;
        }
        Function<String, FileStat> function = WordCountTest::pureFunctional;

        TimedResult<FileStat> result = Arrays.stream(args)
                .flatMap(fileName -> IntStream.range(0, N).mapToObj(i -> timed(fileName, function)))
                .reduce(new TimedResult<>(0L, FileStat.identity()),
                        (a, b) -> new TimedResult<>(a.time + b.time, a.result.add(b.result)));

        System.out.println(result.getResult());
        System.out.printf("Time: %.2fms\n", result.getTime() / (N * 100000.));
    }

    /**
     * Solution using {@link BufferedReader} reading line by line
     */
    private static FileStat wordCount(String fileName) {
        int lines = 0;
        int words = 0;
        int characters = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines++;
                String[] wordParts = WORD.split(line);
                words += wordParts.length;
                characters += line.length() + 1;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return FileStat.identity();
        }
        return new FileStat(lines, characters, words);
    }

    private static FileStat pureFunctional(String fileName) {
        try {
            return Files.lines(Paths.get(fileName))
                    .parallel()
                    .map(new WcCounter())
                    .reduce(FileStat.identity(), FileStat::add);
        } catch (IOException e) {
            return FileStat.identity();
        }
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

    static class TimedResult<T> {
        private final Long time;
        private final T result;

        TimedResult(Long time, T result) {
            this.time = time;
            this.result = result;
        }

        public Long getTime() {
            return time;
        }

        public T getResult() {
            return result;
        }
    }

    static class WcCounter implements Function<String, FileStat> {


        @Override
        public FileStat apply(String line) {
            int words = WORD.split(line).length;
            //int words = WordCounter.count(line).count();
            return new FileStat(1, line.length() + 1, words);
        }
    }

    static <T, R> TimedResult<R> timed(T argument, Function<T, R> function) {
        long start = System.nanoTime();
        R result = function.apply(argument);
        return new TimedResult<>(System.nanoTime() - start, result);
    }
}
