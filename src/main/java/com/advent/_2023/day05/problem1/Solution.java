package com.advent._2023.day05.problem1;

import com.advent._2023.Parser;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.advent._2023.ParseUtils.asLongList;

public class Solution {
    public long solution(Parser parser) {
        Almanac almanac = Almanac.parse(parser.streamLines().toList());
        return almanac.getSeeds().stream().mapToLong(almanac::processMappers).min().orElse(0);
    }


    @Data
    public static class Almanac {
        private List<Long> seeds;
        private List<Mapper> mappers = new ArrayList<>();

        public static Almanac parse(List<String> data) {
            Almanac result = new Almanac();
            result.setSeeds(asLongList(data.get(0).split(":")[1]));
            Iterator<String> iterator = data.subList(2, data.size()).iterator();
            while (iterator.hasNext()) {
                Mapper mapper = new Mapper();
                mapper.setName(iterator.next());
                mapper.setMappings(Mapper.parseMappingList(iterator));
                result.addMapper(mapper);
            }
            return result;
        }

        public long processMappers(long seed) {
            long result = seed;
            for (Mapper mapper : mappers) {
                result = mapper.mapValue(result);
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

        public long mapValue(long source) {
            return mappings.stream()
                    .filter(k -> k.accepts(source))
                    .findFirst()
                    .map(k -> k.convert(source))
                    .orElse(source);
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

        public boolean accepts(long source) {
            return source >= sourceRangeStart && source - sourceRangeStart + 1 <= length;
        }

        public long convert(long source) {
            return source - sourceRangeStart + destinationRangeStart;
        }
    }
}