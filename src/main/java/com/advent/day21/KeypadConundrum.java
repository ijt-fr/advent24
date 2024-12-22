package com.advent.day21;


import static com.advent.day21.KeypadConundrum.DirectionalButton.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.advent.Puzzle;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class KeypadConundrum extends Puzzle {
    private Map<Long, char[]> codes;
    private final LoadingCache<Location, Map<Location, Long>> dijkstraCache;

    KeypadConundrum() {
        dijkstraCache = CacheBuilder.newBuilder()
                                .build(new CacheLoader<>() {
                                    @Override
                                    public Map<Location, Long> load(Location key) {
                                        return dijkstra(key);
                                    }
                                });
    }

    @Override
    public void parseInput(List<String> lines) {
        this.codes = lines.stream()
                             .collect(Collectors.toMap(
                                     s -> Long.parseLong(s.substring(0, s.length() - 1)),
                                     String::toCharArray));
    }

    @Override
    public Object computePart1() {
        return codes.entrySet().stream().map(e -> {
            var value = e.getValue();
            long amount = goToNumber(3, 'A', value[0]);
            for (int i = 0; i < value.length - 1; i++) {
                amount += goToNumber(3, value[i], value[i + 1]);
            }
            return e.getKey() * amount;
        }).reduce(Long::sum).orElse(0L);
    }

    @Override
    public Object part1Answer() {
        return 157230L;
    }

    @Override
    public Object computePart2() {
        return null;
    }

    @Override
    public Object part2Answer() {
        return codes.entrySet().stream().map(e -> {
            var value = e.getValue();
            long amount = goToNumber(25, 'A', value[0]);
            for (int i = 0; i < value.length - 1; i++) {
                amount += goToNumber(25, value[i], value[i + 1]);
            }
            return e.getKey() * amount;
        }).reduce(Long::sum).orElse(0L);
    }

    public long goToNumber(int length, char from, char to) {
        var fromButton = NumberButton.value(from);
        var toButton = NumberButton.value(to);
        List<DirectionalButton> aButtons = new ArrayList<>();
        IntStream.range(0, length).forEach(i -> {
            aButtons.add(A);
        });
        Location startLocation = new Location(fromButton, aButtons);

        Location endButtons = new Location(toButton, aButtons);

        Map<Location, Long> distances = null;
        try {
            distances = dijkstraCache.get(startLocation);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return distances.get(endButtons);
    }

    public static Map<Location, Long> dijkstra(Location start) {
        Map<Location, Long> distances = new HashMap<>();
        distances.put(start, 0L);
        Queue<Location> unvisited = new PriorityQueue<>(Comparator.comparing(l -> distances.getOrDefault(l, Long.MAX_VALUE)));
        unvisited.offer(start);
        while (!unvisited.isEmpty()) {
            Location current = unvisited.poll();
            Arrays.stream(DirectionalButton.values())
                    .forEach(button -> {
                        long newDistance = distances.get(current) + 1;
                        Location newLocation = current.press(button);
                        if (newLocation != null && distances.getOrDefault(newLocation, Long.MAX_VALUE) > newDistance) {
                            distances.put(newLocation, newDistance);
                            unvisited.remove(newLocation);
                            unvisited.add(newLocation);
                        }
                    });
            unvisited.remove(current);
        }
        return distances;
    }

    public record Location(NumberButton number, List<DirectionalButton> buttons) {

        public Location press(DirectionalButton pressed) {
            var newButtons = new ArrayList<>(buttons);
            int i = buttons.size() - 1;
            newButtons.set(i, pressed);
            return switch (pressed) {
                case A -> pressInner(newButtons, i - 1);
                case UP, DOWN, LEFT, RIGHT -> {
                    var newButton = newButtons.get(i - 1).press(pressed);
                    if (newButton != null) {
                        newButtons.set(i - 1, newButton);
                    }
                    yield new Location(number, newButtons);
                }
            };
        }

        private Location pressInner(List<DirectionalButton> buttons, int next) {
            if (next == 1) {
                return switch (buttons.get(next)) {
                    case A -> number.press(buttons.getFirst()) != null ? new Location(number.press(buttons.getFirst()), buttons) : null;
                    case UP, DOWN, LEFT, RIGHT -> {
                        var newButton = buttons.getFirst().press(buttons.get(next));
                        if (newButton != null) {
                            buttons.set(0, newButton);
                        }
                        yield new Location(number, buttons);
                    }
                };
            } else {
                return switch (buttons.get(next)) {
                    case A -> pressInner(buttons, next - 1);
                    case UP, DOWN, LEFT, RIGHT -> {
                        var newButton = buttons.get(next - 1).press(buttons.get(next));
                        if (newButton != null) {
                            buttons.set(next - 1, newButton);
                        }
                        yield new Location(number, buttons);
                    }
                };
            }
        }
    }

    public enum DirectionalButton {
        A,
        UP,
        DOWN,
        LEFT,
        RIGHT;

        public DirectionalButton press(DirectionalButton pressed) {
            return switch (this) {
                case A -> switch (pressed) {
                    case A -> A;
                    case RIGHT, UP -> null;
                    case DOWN -> RIGHT;
                    case LEFT -> UP;
                };
                case UP -> switch (pressed) {
                    case A -> UP;
                    case RIGHT -> A;
                    case LEFT, UP -> null;
                    case DOWN -> DOWN;
                };
                case DOWN -> switch (pressed) {
                    case A -> DOWN;
                    case DOWN -> null;
                    case UP -> UP;
                    case RIGHT -> RIGHT;
                    case LEFT -> LEFT;
                };
                case LEFT -> switch (pressed) {
                    case A -> LEFT;
                    case UP, LEFT, DOWN -> null;
                    case RIGHT -> DOWN;
                };
                case RIGHT -> switch (pressed) {
                    case A -> RIGHT;
                    case UP -> A;
                    case RIGHT, DOWN -> null;
                    case LEFT -> DOWN;
                };
            };
        }
    }

    public enum NumberButton {
        SEVEN,EIGHT,NINE,FOUR,FIVE,SIX,ONE,TWO,THREE,ZERO,A;

        public static NumberButton value(char from) {
            return switch (from) {
                case 'A' -> A;
                case '0' -> ZERO;
                case '1' -> ONE;
                case '2' -> TWO;
                case '3' -> THREE;
                case '4' -> FOUR;
                case '5' -> FIVE;
                case '6' -> SIX;
                case '7' -> SEVEN;
                case '8' -> EIGHT;
                case '9' -> NINE;
                default -> throw new IllegalArgumentException("Invalid number");
            };
        }

        public NumberButton press(DirectionalButton button) {
            return switch (this) {
                case SEVEN -> switch (button) {
                    case A -> SEVEN;
                    case UP -> null;
                    case DOWN -> FOUR;
                    case LEFT -> null;
                    case RIGHT -> EIGHT;
                };
                case EIGHT -> switch (button) {
                    case A -> EIGHT;
                    case UP -> null;
                    case DOWN -> FIVE;
                    case LEFT -> SEVEN;
                    case RIGHT -> NINE;
                };
                case NINE -> switch (button) {
                    case A -> NINE;
                    case UP -> null;
                    case DOWN -> SIX;
                    case LEFT -> EIGHT;
                    case RIGHT -> null;
                };
                case FOUR -> switch (button) {
                    case A -> FOUR;
                    case UP -> SEVEN;
                    case DOWN -> ONE;
                    case LEFT -> null;
                    case RIGHT -> FIVE;
                };
                case FIVE -> switch (button) {
                    case A -> FIVE;
                    case UP -> EIGHT;
                    case DOWN -> TWO;
                    case LEFT -> FOUR;
                    case RIGHT -> SIX;
                };
                case SIX -> switch (button) {
                    case A -> SIX;
                    case UP -> NINE;
                    case DOWN -> THREE;
                    case LEFT -> FIVE;
                    case RIGHT -> null;
                };
                case ONE -> switch (button) {
                    case A -> ONE;
                    case UP -> FOUR;
                    case DOWN -> null;
                    case LEFT -> null;
                    case RIGHT -> TWO;
                };
                case TWO -> switch (button) {
                    case A -> TWO;
                    case UP -> FIVE;
                    case DOWN -> ZERO;
                    case LEFT -> ONE;
                    case RIGHT -> THREE;
                };
                case THREE -> switch (button) {
                    case A -> THREE;
                    case UP -> SIX;
                    case DOWN -> A;
                    case LEFT -> TWO;
                    case RIGHT -> null;
                };
                case ZERO -> switch (button) {
                    case A -> ZERO;
                    case UP -> TWO;
                    case DOWN -> null;
                    case LEFT -> null;
                    case RIGHT -> A;
                };
                case A -> switch (button) {
                    case A -> A;
                    case UP -> THREE;
                    case DOWN -> null;
                    case LEFT -> ZERO;
                    case RIGHT -> null;
                };
            };
        }
    }
}
