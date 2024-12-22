package com.advent.day21;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.advent.Puzzle;
import com.advent.util.Direction;
import com.advent.util.Grid;
import com.advent.util.Vector2;

public class KeypadConundrumOld extends Puzzle {
    private Map<Long, char[]> codes;

    @Override
    public void parseInput(List<String> lines) {
        this.codes = lines.stream()
                             .collect(Collectors.toMap(s -> Long.parseLong(s.substring(0, s.length() - 1)),
                                     String::toCharArray));

    }

    @Override
    public Object computePart1() {
//        return new String(NumericalKeypad.newKeypad().getAllRoutes("029A".toCharArray()));
//        return codes.entrySet().stream().map(e -> {
//            var value = DirectionalKeypad.newKeypad()
//                                .route(DirectionalKeypad.newKeypad()
//                                               .route(NumericalKeypad.newKeypad()
//                                                              .route(e.getValue())));
//            return e.getKey() * value.length;
//        }).reduce(Long::sum).orElse(0L);
        return null;
    }

    @Override
    public Object part1Answer() {
        // less than 163246
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

    static class NumericalKeypad extends Keypad {
        public static final Grid<Character> GRID = Grid.ofChars(new Character[][] {
                {'7', '8', '9'},
                {'4', '5', '6'},
                {'1', '2', '3'},
                {'.', '0', 'A'}
        });

        private NumericalKeypad(Vector2 current) {
            super(current);
        }

        @Override
        Grid<Character> grid() {
            return GRID;
        }

        public static NumericalKeypad newKeypad() {
            return new NumericalKeypad(new Vector2(2, 3));
        }
    }

    static class DirectionalKeypad extends Keypad {
        public static final Grid<Character> GRID = Grid.ofChars(new Character[][] {
                {'.', '^', 'A'},
                {'<', 'v', '>'}
        });

        private DirectionalKeypad(Vector2 current) {
            super(current);
        }

        @Override
        Grid<Character> grid() {
            return GRID;
        }

        public static DirectionalKeypad newKeypad() {
            return new DirectionalKeypad(new Vector2(2, 0));
        }
    }

    abstract static class Keypad {

        private Vector2 current;

        Keypad(Vector2 current) {
            this.current = current;
        }

        abstract Grid<Character> grid();

        public Set<char[]> getAllRoutes(char[] chars) {
            List<Vector2> vectors = new ArrayList<>();
            vectors.add(current);
            for (char ch : chars) {
                vectors.add(grid().stream()
                                    .filter(cell -> cell.entry() == ch)
                                    .map(Grid.Cell::vector)
                                    .findAny().orElseThrow());
            }
            Set<char[]> routes = go(vectors);
            return routes;
        }

        private Set<char[]> go(List<Vector2> vectors) {
            vectors.get(0);
            vectors.get(1);
            return null;
        }

        public char[] route(char[] chars) {
            StringBuilder builder = new StringBuilder();
            for (char ch : chars) {
                Vector2 v = grid().stream()
                                    .filter(cell -> cell.entry() == ch)
                                    .map(Grid.Cell::vector)
                                    .findAny().orElseThrow();
                List<Button> moves = getMoves(v);
                move(builder, Button.A, moves);
                builder.append("A");
            }
            return builder.toString().toCharArray();
        }

        private List<Button> getMoves(Vector2 v) {
            var diff = v.subtract(current);
            List<Button> moves = new ArrayList<>();
            while (!diff.equals(Vector2.ZERO)) {
                if (diff.x() > 0) {
                    var newCurrent = current.add(Direction.EAST);
                    if (grid().get(newCurrent) != '.') {
                        moves.add(Button.RIGHT);
                        current = newCurrent;
                    }
                } else if (diff.x() < 0) {
                    var newCurrent = current.add(Direction.WEST);
                    if (grid().get(newCurrent) != '.') {
                        moves.add(Button.LEFT);
                        current = newCurrent;
                    }
                }
                if (diff.y() > 0) {
                    var newCurrent = current.add(Direction.SOUTH);
                    if (grid().get(newCurrent) != '.') {
                        moves.add(Button.DOWN);
                        current = newCurrent;
                    }
                } else if (diff.y() < 0) {
                    var newCurrent = current.add(Direction.NORTH);
                    if (grid().get(newCurrent) != '.') {
                        moves.add(Button.UP);
                        current = newCurrent;
                    }
                }

                diff = v.subtract(current);
            }
            return moves;
        }

        private void move(StringBuilder builder, Button previous, List<Button> remaining) {
            var it = previous.preferences().iterator();
            while (it.hasNext()) {
                Button nextPreference = Button.of(it.next());
                if (remaining.contains(nextPreference)) {
                    builder.append(nextPreference.value());
                    remaining.remove(nextPreference);
                    move(builder, nextPreference, remaining);
                }
            }
        }
    }

    enum Button {
        A('A', List.of('^', '>', 'v', '<')),
        UP('^', List.of('^', 'v', '<', '>')),
        DOWN('v', List.of('v', '<', '^', '>')),
        LEFT('<', List.of('<', 'v', '^', '>')),
        RIGHT('>', List.of('>', 'v', '^', '<'));

        private final List<Character> preferences;
        private final char value;

        Button(char value, List<Character> preferences) {
            this.value = value;
            this.preferences = preferences;
        }

        public static Button of(Character next) {
            return switch (next) {
                case '^' -> UP;
                case 'v' -> DOWN;
                case '<' -> LEFT;
                case '>' -> RIGHT;
                default -> throw new IllegalArgumentException();
            };
        }

        public List<Character> preferences() {
            return preferences;
        }

        public char value() {
            return value;
        }
    }
}
