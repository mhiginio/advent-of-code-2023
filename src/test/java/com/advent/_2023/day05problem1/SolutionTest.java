package com.advent._2023.day05problem1;

import com.advent._2023.InputParser;
import com.advent._2023.StringParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
    private final Solution solution = new Solution();

    @ParameterizedTest
    @MethodSource("com.advent._2023.day05problem1.SolutionTest#mappingData")
    void mapping_parse_should_return_correct_data(String rawData, Solution.Mapping expected) {
        assertEquals(Solution.Mapping.parse(rawData), expected);
    }

    public static Stream<Arguments> mappingData() {
        return Stream.of(
                Arguments.of("1 2 3", new Solution.Mapping(1, 2, 3)),
                Arguments.of("13 2 374", new Solution.Mapping(13, 2, 374))
        );
    }

    @Test
    void mapper_parse_should_return_correct_data() {
        String[] data = {
                "50 98 2",
                "52 50 48",
                "1 2 555555555"};
        List<Solution.Mapping> parse = Solution.Mapper.parseMappingList(Arrays.stream(data).iterator());
        assertEquals(3, parse.size());
        assertEquals(parse.get(0), new Solution.Mapping(50, 98, 2));
        assertEquals(parse.get(1), new Solution.Mapping(52, 50, 48));
        assertEquals(parse.get(2), new Solution.Mapping(1, 2, 555555555));
    }

    @Test
    void almanac_parse_should_return_correct_data() {
        String[] data = {
                "seeds: 79 14 55 13",
                "",
                "seed-to-soil map:",
                "50 98 2",
                "52 50 48",
                "",
                "soil-to-fertilizer map:",
                "0 15 37",
                "37 52 2",
                "39 0 15"};
        Solution.Almanac almanac = Solution.Almanac.parse(Arrays.stream(data).toList());
        assertEquals(List.of(79L, 14L, 55L, 13L), almanac.getSeeds());
        assertEquals(2, almanac.getMappers().size());
        assertEquals(List.of("seed-to-soil map:", "soil-to-fertilizer map:"),
                almanac.getMappers().stream().map(Solution.Mapper::getName).toList());
        assertEquals(List.of(2, 3),
                almanac.getMappers().stream().map(Solution.Mapper::getMappings).map(List::size).toList());
    }

    @Test
    void mapper_mapped_values_are_correct() {
        Solution.Mapper mapper = new Solution.Mapper();
        mapper.setMappings(List.of(new Solution.Mapping(50, 98, 2),
                new Solution.Mapping(52, 50, 48)));
        assertEquals(0, mapper.mapValue(0));
        assertEquals(33, mapper.mapValue(33));
        assertEquals(49, mapper.mapValue(49));
        assertEquals(52, mapper.mapValue(50));
        assertEquals(53, mapper.mapValue(51));
        assertEquals(99, mapper.mapValue(97));
        assertEquals(50, mapper.mapValue(98));
    }

    @Test
    void example_data_should_return_35() {
        String[] data = {
                "seeds: 79 14 55 13",
                "",
                "seed-to-soil map:",
                "50 98 2",
                "52 50 48",
                "",
                "soil-to-fertilizer map:",
                "0 15 37",
                "37 52 2",
                "39 0 15",
                "",
                "fertilizer-to-water map:",
                "49 53 8",
                "0 11 42",
                "42 0 7",
                "57 7 4",
                "",
                "water-to-light map:",
                "88 18 7",
                "18 25 70",
                "",
                "light-to-temperature map:",
                "45 77 23",
                "81 45 19",
                "68 64 13",
                "",
                "temperature-to-humidity map:",
                "0 69 1",
                "1 0 69",
                "",
                "humidity-to-location map:",
                "60 56 37",
                "56 93 4"};
        assertEquals(35, solution.solution(new StringParser(data)));
    }

    @Test
    void provided_data_should_return_382895070() {
        assertEquals(382895070,
                solution.solution(new InputParser(getClass().getResourceAsStream("/day05problem1/input.txt"))));
    }
}