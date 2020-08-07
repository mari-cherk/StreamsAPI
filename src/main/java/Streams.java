import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Streams {

    public static void main(String[] args) throws IOException {

        //- filter — отбирает элементы, значение которых меньше 300,
        //  - map — прибавляет 11 к каждому числу,
        //  - limit — ограничивает количество элементов до 3.
        IntStream.of(120, 410, 85, 32, 314, 12)
                .filter(x -> x < 300)
                .map(x -> x + 11)
                .limit(3)
                .forEach(System.out::println);

        // Integer Stream
        IntStream
                .range(1, 10)
                .forEach(System.out::print);

        // Integer Stream with skip
        IntStream
                .range(1, 10)
                .skip(5)
                .forEach(x -> System.out.println(x));

        // Integer Stream with sum
        System.out.println(
                IntStream
                        .range(1, 5)
                        .sum());
        System.out.println();

        // Stream.of, sorted and findFirst
        Stream.of("hello", "aloha", "hi")
                .sorted()
                .findFirst()
                .ifPresent(System.out::println);

        // Stream from Array, sort, filter and print
        String[] names = {"Al", "Ankit", "Kushal", "Brent", "Sarika", "Amanda", "Hans", "Shivika", "Sarah"};
        Arrays.stream(names)	// same as Stream.of(names)
                .filter(x -> x.endsWith("a"))
                .sorted()
                .forEach(System.out::println);

        // Average of squares of an int array
        Arrays.stream(new int[] {2, 4, 6, 8, 10})
                .map(x -> x * x)
                .average()
                .ifPresent(System.out::println);

        // Stream from List, filter and print
        List<String> people = Arrays.asList("Al", "Ankit", "Brent", "Sarika", "amanda", "Hans", "Shivika", "Sarah");
        people
                .stream()
                .map(String::toLowerCase)
                .filter(x -> x.startsWith("a"))
                .forEach(System.out::println);

        // Stream rows from text file, sort, filter, and print
        Stream<String> bands = Files.lines(Paths.get("src/data.txt"));
        bands
                .sorted()
                .filter(x -> x.length() > 13)
                .forEach(System.out::println);
        bands.close();

        //Stream rows from text file and save to List
        List<String> bands2 = Files.lines(Paths.get("src/data.txt"))
                .filter(x -> x.contains("second"))
                .collect(Collectors.toList());
        bands2.forEach(x -> System.out.println(x));

        // Stream rows from CSV file and count
        Stream<String> rows1 = Files.lines(Paths.get("src/data.txt"));
        int rowCount = (int)rows1
                .map(x -> x.split(" "))
                .filter(x -> x.length == 2)
                .count();
        System.out.println(rowCount + " rows.");
        rows1.close();

        // Stream rows from CSV file, parse data from rows
        Stream<String> rows2 = Files.lines(Paths.get("src/data.txt"));
        rows2
                .map(x -> x.split(" "))
                .filter(x -> x.length == 3)
                .filter(x -> Integer.parseInt(x[1]) > 15)
                .forEach(x -> System.out.println(x[0] + "  " + x[1] + "  " + x[2]));
        rows2.close();

        //  Stream rows from CSV file, store fields in HashMap
        Stream<String> rows3 = Files.lines(Paths.get("src/data.txt"));
        Map<String, Integer> map = new HashMap<>();
        map = rows3
                .map(x -> x.split(","))
                .filter(x -> x.length == 3)
                .filter(x -> Integer.parseInt(x[1]) > 15)
                .collect(Collectors.toMap(
                        x -> x[0],
                        x -> Integer.parseInt(x[1])));
        rows3.close();
        for (String key : map.keySet()) {
            System.out.println(key + "  " + map.get(key));
        }

        //  Reduction - sum
        double total = Stream.of(7.3, 1.5, 4.8)
                .reduce(0.0, (Double a, Double b) -> a + b);
        System.out.println("Total = " + total);

        // Reduction - summary statistics
        IntSummaryStatistics summary = IntStream.of(7, 2, 19, 88, 73, 4, 10)
                .summaryStatistics();
        System.out.println(summary);


    }
}
