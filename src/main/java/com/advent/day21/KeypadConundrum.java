package com.advent.day21;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.advent.Puzzle;
import com.advent.util.Direction;
import com.advent.util.Grid;
import com.advent.util.Vector2;
import com.google.common.collect.Streams;

public class KeypadConundrum extends Puzzle {
    private Map<Long, char[]> codes;

    @Override
    public void parseInput(List<String> lines) {
        this.codes = lines.stream()
                             .collect(Collectors.toMap(s -> Long.parseLong(s.substring(0, s.length() - 1)),
                                     String::toCharArray));

    }

    @Override
    public Object computePart1() {
        return 1L;
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


    abstract static class Keypad {

        private Vector2 current;

        Keypad(Vector2 current) {
            this.current = current;
        }

        abstract Grid<Character> grid();

        public Set<String> getAllRoutes(Set<String> charsSet) {
            List<Vector2> vectors = new ArrayList<>();
            vectors.add(current);
            Set<String> result = new HashSet<>();
            for (String chars : charsSet) {
                for (char ch : chars.toCharArray()) {
                    vectors.add(grid().stream()
                                        .filter(cell -> cell.entry() == ch)
                                        .map(Grid.Cell::vector)
                                        .findAny().orElseThrow());
                }
                result.addAll(go(vectors));
            }
            return result;
        }

        private Set<String> go(List<Vector2> vectors) {
            Vector2 current = vectors.get(0);
            Vector2 next = vectors.get(1);
            if (vectors.size() == 2) {
                Set<String> list = new HashSet<>(moveX(next, current, Set.of("")));
                list.addAll(moveY(next, current, Set.of("")));
                return list;
            }
            var nextVectors = new ArrayList<>(vectors.subList(1, vectors.size()));
            Set<String> list = new HashSet<>(moveX(next, current, go(nextVectors)));
            list.addAll(moveY(next, current, go(nextVectors)));
            return list;
        }

        private Set<String> moveY(Vector2 next, Vector2 current, Set<String> chars) {
            var diff = next.subtract(current);
            if (diff.x() == 0 && diff.y() == 0) {
                return chars.stream().map(ch -> ch + "A").collect(Collectors.toSet());
            }
            if (diff.y() > 0) {
                var newCurrent = current.add(Direction.SOUTH);
                if (grid().get(newCurrent) != '.') {
                    var newChars = chars.stream().map(ch -> "v" + ch).collect(Collectors.toSet());
                    var list = new HashSet<>(moveY(next, newCurrent, newChars));
                    list.addAll(moveX(next, newCurrent, newChars));
                    return list;
                }
            } else if (diff.y() < 0) {
                var newCurrent = current.add(Direction.NORTH);
                if (grid().get(newCurrent) != '.') {
                    var newChars =  chars.stream().map(ch -> "^" + ch).collect(Collectors.toSet());
                    var list = new HashSet<>(moveY(next, newCurrent, newChars));
                    list.addAll(moveX(next, newCurrent, newChars));
                    return list;
                }
            }
            return Set.of();
        }

        private Set<String> moveX(Vector2 next, Vector2 current, Set<String> chars) {
            var diff = next.subtract(current);
            if (diff.x() == 0 && diff.y() == 0) {
                return chars.stream().map(ch -> "A" + ch).collect(Collectors.toSet());
            }
            if (diff.x() > 0) {
                var newCurrent = current.add(Direction.EAST);
                if (grid().get(newCurrent) != '.') {
                    var newChars = chars.stream().map(ch ->">" + ch).collect(Collectors.toSet());
                    var list = new HashSet<>(moveY(next, newCurrent, newChars));
                    list.addAll(moveX(next, newCurrent, newChars));
                    return list;
                }
            } else if (diff.x() < 0) {
                var newCurrent = current.add(Direction.WEST);
                if (grid().get(newCurrent) != '.') {
                    var newChars = chars.stream().map(ch -> "<" + ch).collect(Collectors.toSet());
                    var list = new HashSet<>(moveY(next, newCurrent, newChars));
                    list.addAll(moveX(next, newCurrent, newChars));
                    return list;
                }
            }
            return Set.of();
        }
    }
}
