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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.advent.Puzzle;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class KeypadConundrum extends Puzzle {
    private Map<Long, char[]> codes;
    private static final LoadingCache<Key, Long> dijkstraCache = CacheBuilder.newBuilder()
                                                                         .build(new CacheLoader<>() {
                                                                             @Override
                                                                             public Long load(Key key) {
                                                                                 return compute(key.from(), key.to(), key.depth());
                                                                             }
                                                                         });


    @Override
    public void parseInput(List<String> lines) {
        this.codes = lines.stream()
                             .collect(Collectors.toMap(
                                     s -> Long.parseLong(s.substring(0, s.length() - 1)),
                                     String::toCharArray));
    }

    @Override
    public Object computePart1() {
        int numberOfRobots = 3;
        return getLength(numberOfRobots);
    }

    private Object getLength(int numberOfRobots) {
        return codes.entrySet().stream().map(e -> {
            var value = e.getValue();
            long amount = goToNumber(numberOfRobots, 'A', value[0]);
            for (int i = 0; i < value.length - 1; i++) {
                amount += goToNumber(numberOfRobots, value[i], value[i + 1]);
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
        // 4  389816L
        // 5  960942L
        // 6  2387042L
        // 7  5919098L

        int numberOfRobots = 25;
        return goToNumber(25, 'A', '0');
    }

    @Override
    public Object part2Answer() {
        return null;
    }

    public long goToNumber(int length, char from, char to) {
        var fromButton = NumberButton.value(from);
        var toButton = NumberButton.value(to);
        return 0L;
//        try {
//            return dijkstraCache.get(new Key(fromButton, toButton, length));
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        }
    }

    public static Long compute(DirectionalButton from, DirectionalButton to, int depth) {
        List<DirectionalButton> fromList = new ArrayList<>();
        fromList.add(from);
        IntStream.range(0, depth - 1).forEach(ignored -> fromList.add(A));
        List<DirectionalButton> toList = new ArrayList<>();
        toList.add(to);
        IntStream.range(0, depth - 1).forEach(ignored -> toList.add(A));
        return miniDijkstra(from, to, depth);
    }

    private static Long miniDijkstra(DirectionalButton start, DirectionalButton end, int depth) {
        Map<DirectionalButton, Long> distances = new HashMap<>();
        distances.put(start, 0L);
        Queue<DirectionalButton> unvisited = new PriorityQueue<>(Comparator.comparing(l -> distances.getOrDefault(l, Long.MAX_VALUE)));
        unvisited.offer(start);
        while (!unvisited.isEmpty()) {
            DirectionalButton current = unvisited.poll();
            Arrays.stream(DirectionalButton.values())
                    .forEach(button -> {
                        DirectionalButton newButton = current.press(button);
                        long newDistance = distances.get(current);
                        if (depth > 1) {
                            try {
                                newDistance += dijkstraCache.get(new Key(current, newButton, depth - 1));
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            newDistance += 1;
                        }

                        if (newButton != null && distances.getOrDefault(newButton, Long.MAX_VALUE) > newDistance) {
                            distances.put(newButton, newDistance);
                            unvisited.remove(newButton);
                            unvisited.add(newButton);
                            if (newButton.equals(end)) {
                                unvisited.clear();
                            }
                        }
                    });
            unvisited.remove(current);
        }
        return distances.getOrDefault(end, Long.MAX_VALUE);
    }

    public static Long dijkstra(Location start, Location end) {
        Map<Location, Long> distances = new HashMap<>();
        distances.put(start, 0L);
        Queue<Location> unvisited = new PriorityQueue<>(Comparator.comparing(l -> distances.getOrDefault(l, Long.MAX_VALUE)));
        unvisited.offer(start);
        AtomicBoolean foundEnd = new AtomicBoolean(false);
        while (!foundEnd.get()) {
            Location current = unvisited.poll();
            Arrays.stream(DirectionalButton.values())
                    .forEach(button -> {
                        long newDistance = distances.get(current) + 1;
                        Location newLocation = current.press(button);
                        if (newLocation != null && distances.getOrDefault(newLocation, Long.MAX_VALUE) > newDistance) {
                            distances.put(newLocation, newDistance);
                            unvisited.remove(newLocation);
                            unvisited.add(newLocation);
                            if (newLocation.equals(end)) {
                                foundEnd.set(true);
                            }
                        }
                    });
            unvisited.remove(current);
        }
        return distances.getOrDefault(end, Long.MAX_VALUE);
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
                DirectionalButton pressedButton = buttons.get(next);
                DirectionalButton nextButton = buttons.get(next - 1);
                return switch (pressedButton) {
                    case A -> pressInner(buttons, next - 1);
                    case UP, DOWN, LEFT, RIGHT -> {
                        var newButton = nextButton.press(pressedButton);
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

    private record Key(DirectionalButton from, DirectionalButton to, int depth) {
    }

    private record Buttons(List<DirectionalButton> buttons) {
        public Buttons press(DirectionalButton pressed) {
            var newButtons = new ArrayList<>(buttons);
            int i = buttons.size() - 1;
            newButtons.set(i, pressed);
            return switch (pressed) {
                case A -> pressNext(newButtons, i - 1);
                case UP, DOWN, LEFT, RIGHT -> {
                    var newButton = newButtons.get(i - 1).press(pressed);
                    if (newButton != null) {
                        newButtons.set(i - 1, newButton);
                    }
                    yield new Buttons(newButtons);
                }
            };
        }

        private Buttons pressNext(List<DirectionalButton> buttons, int next) {
            if (next == 1) {
                return switch (buttons.get(next)) {
                    case A -> throw new IllegalArgumentException("what happens here?");
                    case UP, DOWN, LEFT, RIGHT -> {
                        var newButton = buttons.getFirst().press(buttons.get(next));
                        if (newButton != null) {
                            buttons.set(0, newButton);
                        }
                        yield new Buttons(buttons);
                    }
                };
            } else {
                DirectionalButton pressedButton = buttons.get(next);
                DirectionalButton nextButton = buttons.get(next - 1);
                return switch (pressedButton) {
                    case A -> pressNext(buttons, next - 1);
                    case UP, DOWN, LEFT, RIGHT -> {
                        var newButton = nextButton.press(pressedButton);
                        if (newButton != null) {
                            buttons.set(next - 1, newButton);
                        }
                        yield new Buttons(buttons);
                    }
                };
            }
        }
    }
}
