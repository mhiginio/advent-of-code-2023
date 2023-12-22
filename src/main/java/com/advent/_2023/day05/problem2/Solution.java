package com.advent._2023.day05.problem2;

import com.advent._2023.Parser;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.stream.Stream;

import static com.advent._2023.ParseUtils.asLongList;

public class Solution {
    public long solution(Parser parser) {
        Almanac almanac = Almanac.parse(parser.streamLines().toList());
        return Stream.of(almanac.getSeeds())
                .map(almanac::processMappers)
                .flatMap(List::stream)
                .mapToLong(Range::getStart).min().orElse(0);
    }


    @Data
    public static class Almanac {
        private List<Range> seeds;
        private List<Mapper> mappers = new ArrayList<>();

        public static Almanac parse(List<String> data) {
            Almanac result = new Almanac();
            result.setSeeds(parseSeeds(data.get(0).split(":")[1]));
            Iterator<String> iterator = data.subList(2, data.size()).iterator();
            while (iterator.hasNext()) {
                Mapper mapper = new Mapper();
                mapper.setName(iterator.next());
                mapper.setMappings(Mapper.parseMappingList(iterator));
                result.addMapper(mapper);
            }
            return result;
        }

        private static List<Range> parseSeeds(String rawData) {
            List<Range> result = new ArrayList<>();
            List<Long> rawSeedRanges = asLongList(rawData);
            for (int i = 0; i < rawSeedRanges.size(); i = i + 2) {
                result.add(new Range(rawSeedRanges.get(i), rawSeedRanges.get(i + 1)));
            }
            return result;
        }

        public List<Range> processMappers(List<Range> ranges) {
            List<Range> result = ranges;
            for (Mapper mapper : mappers) {
                result = mapper.mapRanges(result);
            }
            return result;
        }

        private void addMapper(Mapper mapper) {
            mappers.add(mapper);
        }
    }

    @Data
    public static class Mapper {
        private String name;
        private List<Mapping> mappings;

        public static List<Mapping> parseMappingList(Iterator<String> iterator) {
            boolean mappingEnded = false;
            List<Mapping> result = new ArrayList<>();
            while (iterator.hasNext() && !mappingEnded) {
                String data = iterator.next();
                if (data == null || data.isEmpty()) {
                    mappingEnded = true;
                } else {
                    result.add(Mapping.parse(data));
                }
            }
            return result;
        }

        public List<Range> mapRanges(List<Range> ranges) {
            return ranges.stream().map(this::mapRange).flatMap(List::stream).toList();
        }

        private List<Range> mapRange(Range range) {
            List<Range> result = new ArrayList<>();
            long current = range.getStart();
            while (current <= range.getEnd()) {
                Optional<Mapping> mappingOpt = findMappingForValue(current);
                long until;
                if (mappingOpt.isEmpty()) {
                    until = Math.min(lastUnmappedValue(current), range.getEnd());
                    result.add(new Range(current, until - current + 1));
                } else {
                    Mapping mapping = mappingOpt.get();
                    until = Math.min(mapping.getSourceRangeEnd(), range.getEnd());
                    result.add(new Range(
                            current + mapping.getDestinationRangeStart() - mapping.getSourceRangeStart(),
                            until - current + 1));
                }

                current = until + 1;
            }
            return result;
        }

        private long lastUnmappedValue(long current) {
            OptionalLong min = mappings.stream()
                    .filter(k -> k.getSourceRangeStart() > current)
                    .mapToLong(Mapping::getSourceRangeStart)
                    .min();
            return min.isPresent() ? min.getAsLong() - 1 : Long.MAX_VALUE;
        }

        private Optional<Mapping> findMappingForValue(long value) {
            return mappings.stream()
                    .filter(k -> k.accepts(value))
                    .findFirst();
        }
    }

    @AllArgsConstructor
    @Data
    public static class Mapping {
        private long destinationRangeStart;
        private long sourceRangeStart;
        private long length;

        public static Mapping parse(String rawData) {
            List<Long> mappingData = asLongList(rawData);
            return new Mapping(mappingData.get(0), mappingData.get(1), mappingData.get(2));
        }

        public long getSourceRangeEnd() {
            return sourceRangeStart + length - 1;
        }

        public boolean accepts(long source) {
            return source >= sourceRangeStart && source - sourceRangeStart + 1 <= length;
        }
    }

    @Data
    @AllArgsConstructor
    public static class Range {
        private long start;
        private long length;

        public long getEnd() {
            return start + length - 1;
        }
    }
}