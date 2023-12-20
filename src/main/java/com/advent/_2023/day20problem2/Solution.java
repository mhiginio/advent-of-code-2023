package com.advent._2023.day20problem2;

import com.advent._2023.LCMCalculator;
import com.advent._2023.Parser;
import lombok.Data;

import java.util.*;

/**
 * "rx" will receive low only if rg receive high from kd, zf, vg, gs
 * Analyzing the patterns we have that kd, zf, vg & vs return true in fixed cycles, so
 * the solution is the lcm of these cycles.
 */
public class Solution {
    private static long count;

    public long solution(Parser parser) {
        Elements elements = new Elements();
        parser.streamLines().forEach(elements::addElement);
        elements.populateConjunctions();
        boolean end = false;
        while (!end) {
            count++;
            end = elements.pressButton();
        }
        return elements.getSolution();
    }


    private static final class Elements {
        public static final String BROADCASTER = "broadcaster";
        private static final List<String> WATCHED_CONJUNCTIONS = List.of("kd", "zf", "vg", "gs");
        private final Map<String, Element> elements = new HashMap<>();
        private final Map<String, Long> watchedValues = new HashMap<>();

        public void addElement(String rawElement) {
            if (rawElement.startsWith("%")) {
                String name = rawElement.substring(1, rawElement.indexOf(" "));
                elements.put(name, FlipFlop.parseElement(rawElement, name));
            } else if (rawElement.startsWith("&")) {
                String name = rawElement.substring(1, rawElement.indexOf(" "));
                elements.put(name, Conjunction.parseElement(rawElement, name));
            } else {
                elements.put(rawElement.substring(0, rawElement.indexOf(" ")),
                        Broadcast.parseElement(rawElement, Elements.BROADCASTER));
            }
        }

        public boolean pressButton() {
            Queue<Action> queue = new LinkedList<>();
            queue.add(new Action("null", Elements.BROADCASTER, false));

            while (!queue.isEmpty()) {
                Action action = queue.poll();
                if (action.pulse() &&
                        WATCHED_CONJUNCTIONS.contains(action.source()) &&
                        !watchedValues.containsKey(action.source())) {
                    watchedValues.put(action.source(), count);
                }
                Element element = elements.get(action.destination());
                if (element != null) {
                    queue.addAll(element.process(action));
                }
            }
            return watchedValues.size() == 4;
        }

        public void populateConjunctions() {
            elements.values().stream().filter(e -> e instanceof Conjunction)
                    .forEach(c -> ((Conjunction) c).populateSources(elements.values()));
        }

        public long getSolution() {
            return LCMCalculator.compute(watchedValues.values().stream().toList());
        }
    }

    private interface Element {
        List<Action> process(Action action);

        List<String> getDestinations();

        String getName();
    }

    @Data
    private static class FlipFlop implements Element {
        private final List<String> destinations;
        private final String name;
        private boolean status = false;

        @Override
        public List<Action> process(Action action) {
            if (action.pulse) {
                return Collections.emptyList();
            }
            status = !status;
            return destinations.stream().map(k -> new Action(name, k, status)).toList();
        }

        public static FlipFlop parseElement(String rawElement, String name) {
            String[] destinations = rawElement.substring(rawElement.indexOf('>') + 1).split(", ");
            return new FlipFlop(Arrays.stream(destinations).map(String::trim).toList(), name);
        }
    }

    @Data
    private static class Conjunction implements Element {
        private final List<String> destinations;
        private final String name;
        private Map<String, Boolean> lastSignal;
        private List<Long> highSignals = new ArrayList<>();


        @Override
        public List<Action> process(Action action) {
            lastSignal.put(action.source(), action.pulse());
            boolean pulse = lastSignal.values().stream().anyMatch(k -> !k);
            if (pulse) {
                highSignals.add(count);
            }
            return destinations.stream().map(k -> new Action(name, k, pulse)).toList();
        }

        public void populateSources(Collection<Element> allElements) {
            lastSignal = new HashMap<>();
            for (Element element : allElements) {
                if (element.getDestinations().contains(name)) {
                    lastSignal.put(element.getName(), false);
                }
            }
        }

        public static Conjunction parseElement(String rawElement, String name) {
            String[] destinations = rawElement.substring(rawElement.indexOf('>') + 1).split(", ");
            return new Conjunction(Arrays.stream(destinations).map(String::trim).toList(), name);
        }
    }

    @Data
    private static final class Broadcast implements Element {
        private final List<String> destinations;
        private final String name;

        @Override
        public List<Action> process(Action action) {
            return destinations.stream().map(k -> new Action(name, k, false)).toList();
        }

        public static Broadcast parseElement(String rawElement, String name) {
            String[] destinations = rawElement.substring(rawElement.indexOf('>') + 1).split(",");
            return new Broadcast(Arrays.stream(destinations).map(String::trim).toList(), name);
        }
    }

    private record Action(String source, String destination, boolean pulse) {
    }
}