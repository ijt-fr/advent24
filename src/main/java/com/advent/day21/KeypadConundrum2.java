package com.advent.day21;

import static com.advent.day21.KeypadConundrum2.Button.A;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import com.advent.Puzzle;
import com.advent.util.Direction;
import com.advent.util.Grid;
import com.advent.util.InputUtils;
import com.advent.util.Vector2;

public class KeypadConundrum2 extends Puzzle {
    private Map<Long, char[]> codes;
    private static final Grid<Character> NUMERICAL_GRID = Grid.ofChars(InputUtils.toCharMatrix(List.of("789", "456", "123", ".0A")));
    private static final Grid<Character> DIRECTIONAL_GRID = Grid.ofChars(InputUtils.toCharMatrix(List.of(".^A", "<v>")));
    private Set<Vector2> numericalVectors;
    private Set<Vector2> directionalVectors;
    private Set<Location> numericalLocations;

    public KeypadConundrum2() {
        numericalVectors = NUMERICAL_GRID.stream()
                                   .filter(c -> !c.entry().equals('.'))
                                   .map(Grid.Cell::vector)
                                   .collect(Collectors.toSet());
        numericalLocations = numericalVectors.stream()
                                     .flatMap(v -> Arrays.stream(Button.values())
                                                           .flatMap(b -> Arrays.stream(Button.values())
                                                                                 .map(b2 -> new Button[] { b, b2 }))
                                                           .map(b -> new Location(v, b[0], b[1])))
                                     .collect(Collectors.toSet());
        directionalVectors = DIRECTIONAL_GRID.stream()
                                     .filter(c -> !c.entry().equals('.'))
                                     .map(Grid.Cell::vector).collect(Collectors.toSet());
    }

    @Override
    public void parseInput(List<String> lines) {
        this.codes = lines.stream()
                             .collect(Collectors.toMap(s -> Long.parseLong(s.substring(0, s.length() - 1)),
                                     String::toCharArray));


    }

    @Override
    public Object computePart1() {
        return codes.entrySet().stream().map(e -> {
            long amount = 0L;
            var value = e.getValue();
            for (int i = 0; i < value.length - 1; i++) {
                amount += goToNumber(value[i], value[i + 1]);
            }
            return e.getKey() * amount;
        }).reduce(Long::sum).orElse(0L);
    }

    @Override
    public Object part1Answer() {
        return null;
    }

    @Override
    public Object computePart2() {
        return null;
    }

    @Override
    public Object part2Answer() {
        return null;
    }

    public long goToNumber(char from, char to) {
        var fromVector = NUMERICAL_GRID.anyVectorOf(from).orElseThrow();
        var toVector = NUMERICAL_GRID.anyVectorOf(to).orElseThrow();
        var distances = dijkstra(numericalLocations, new Location(fromVector, A, A));
        return distances.get(new Location(toVector, A, A));
    }

    public static Map<Location, Long> dijkstra(Set<Location> vectors, Location start) {
        Map<Location, Long> distances = vectors.stream()
                                               .collect(Collectors.toMap(v -> v, v -> Long.MAX_VALUE));
        distances.put(start, 0L);
        Queue<Location> unvisited = new PriorityQueue<>(Comparator.comparing(distances::get));
        unvisited.offer(start);
        while (!unvisited.isEmpty()) {
            Location current = unvisited.poll();
            Arrays.stream(Button.values())
                    .forEach(button -> {
                        long currentDistance = distances.get(current);
                        long newDistance = currentDistance + 1;
                        var newLocation = current.pressed(button);
                        if (newLocation != null && distances.containsKey(newLocation) &&
                                    distances.get(newLocation) > newDistance) {
                            distances.put(newLocation, newDistance);
                            unvisited.remove(newLocation);
                            unvisited.add(newLocation);
                        }
                    });
            unvisited.remove(current);
        }
        return distances;
    }

    public record Location(Vector2 vector, Button button, Button pressed) {

        public Location pressed(Button pressed) {
            return switch (pressed) {
                case A -> switch (button) {
                    case A -> new Location(vector, button, pressed);
                    case UP, DOWN, LEFT, RIGHT -> {
                        if (button.asDirection() != null) {
                            yield new Location(vector.add(button.asDirection()), button, pressed);
                        }
                        yield null;
                    }
                };
                case UP, DOWN, LEFT, RIGHT -> {
                    if (button.add(pressed) != null) {
                        yield new Location(vector, button.add(pressed), pressed);
                    }
                    yield null;
                }
            };
        }
    }

    public enum Button {
        A,
        UP,
        DOWN,
        LEFT,
        RIGHT;

        public Direction asDirection() {
            return switch (this) {
                case A -> null;
                case UP -> Direction.NORTH;
                case DOWN -> Direction.SOUTH;
                case LEFT -> Direction.EAST;
                case RIGHT -> Direction.WEST;
            };
        }

        public Button add(Button pressed) {
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
}
