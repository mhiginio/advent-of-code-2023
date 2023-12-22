package com.advent._2023.day20.problem1;

import com.advent._2023.Parser;
import lombok.Data;

import java.util.*;


public class Solution {
    public long solution(Parser parser) {
        Elements elements = new Elements();
        parser.streamLines().forEach(elements::addElement);
        elements.populateSources();
        long lowPulseCount = 0;
        long highPulseCount = 0;
        for (int i = 0; i < 1000; i++) {
            long[] results = elements.pressButton();
            lowPulseCount += results[0];
            highPulseCount += results[1];
        }
        return lowPulseCount * highPulseCount;
    }


    private static final class Elements {
        public static final String BROADCASTER = "broadcaster";
        private final Map<String, Element> elements = new HashMap<>();

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

        public long[] pressButton() {
            Queue<Action> queue = new LinkedList<>();
            queue.add(new Action("null", Elements.BROADCASTER, false));
            long lowPulseCount = 0;
            long highPulseCount = 0;
            while (!queue.isEmpty()) {
                Action action = queue.poll();
                if (action.pulse) {
                    highPulseCount++;
                } else {
                    lowPulseCount++;
                }
                Element element = elements.get(action.destination());
                if (element != null) {
                    queue.addAll(element.process(action));
                }
            }
            return new long[]{lowPulseCount, highPulseCount};
        }

        public void populateSources() {
            elements.values().stream().filter(e -> e instanceof Conjunction)
                    .forEach(c -> ((Conjunction) c).populateSources(elements.values()));
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

        @Override
        public List<Action> process(Action action) {
            lastSignal.put(action.source(), action.pulse());
            boolean pulse = lastSignal.values().stream().anyMatch(k -> !k);
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