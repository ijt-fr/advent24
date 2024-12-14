package com.advent.day14;

import static com.advent.day14.RestroomRedoubt.Quadrant.BOTTOM_LEFT;
import static com.advent.day14.RestroomRedoubt.Quadrant.BOTTOM_RIGHT;
import static com.advent.day14.RestroomRedoubt.Quadrant.TOP_LEFT;
import static com.advent.day14.RestroomRedoubt.Quadrant.TOP_RIGHT;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.advent.Puzzle;
import com.advent.util.InputUtils;
import com.advent.util.Vector2;

public class RestroomRedoubt extends Puzzle {

    private static final Pattern PATTERN = Pattern.compile("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)");

    private Set<Bot> bots;
    private static int MAX_X;
    private static int MAX_Y;

    @Override
    public void parseInput(List<String> lines) {
        MAX_X = 0;
        MAX_Y = 0;
        bots = lines.stream()
                       .map(line -> {
                           var matcher = PATTERN.matcher(line);
                           if (!matcher.matches()) {
                               throw new IllegalStateException();
                           }
                           var posX = Integer.parseInt(matcher.group(1));
                           if (posX > MAX_X) {
                               MAX_X = posX;
                           }
                           var posY = Integer.parseInt(matcher.group(2));
                           if (posY > MAX_Y) {
                               MAX_Y = posY;
                           }
                           var velX = Integer.parseInt(matcher.group(3));
                           var velY = Integer.parseInt(matcher.group(4));
                           return new Bot(new Vector2(posX, posY), new Vector2(velX, velY));
                       })
                       .collect(Collectors.toSet());
        MAX_X++;
        MAX_Y++;

    }

    @Override
    public Object computePart1() {
        return bots.stream()
                       .map(bot -> {
                            for (int i = 0; i < 100; i++) {
                                bot = bot.move();
                            }
                            return bot;
                        })
                       .map(Bot::position)
                       .map(this::computeQuadrant)
                       .filter(Optional::isPresent)
                       .map(Optional::get)
                       .collect(Collectors.toMap(q -> q, v -> 1L, Long::sum))
                       .values().stream()
                       .reduce((a, b) -> a * b).orElse(0L);
    }

    @Override
    public Object part1Answer() {
        return 216772608L;
    }

    @Override
    public Object computePart2() {
        int seconds = 0;
        while (true) {
            seconds++;
            bots = bots.stream().map(Bot::move).collect(Collectors.toSet());
            if (hasLongStraightLine()) {
                visualise(seconds);
                return seconds;
            }
        }
    }

    @Override
    public Object part2Answer() {
        return 6888;
    }

    private boolean hasLongStraightLine() {
        List<Vector2> positions = bots.stream().map(Bot::position)
                .filter(p -> Optional.of(TOP_LEFT).equals(computeQuadrant(p)))
                .toList();

        for (Vector2 p : positions) {
            for (int i = 0; i < positions.size(); i++) {
                int positionsOnLine = 2;
                Vector2 vector = p.subtract(positions.get(i));
                if (vector.x() == 0 & vector.y() == 0) {
                    continue;
                }
                Vector2 newPos = p;
                while (newPos.x() < MAX_X && newPos.y() < MAX_Y && newPos.x() >= 0 && newPos.y() >= 0) {
                    newPos = newPos.add(vector);
                    if (positions.contains(newPos)) {
                        positionsOnLine++;
                    }
                }
                newPos = p;
                while (newPos.x() < MAX_X && newPos.y() < MAX_Y && newPos.x() >= 0 && newPos.y() >= 0) {
                    newPos = newPos.subtract(vector);
                    if (positions.contains(newPos)) {
                        positionsOnLine++;
                    }
                }
                if (positionsOnLine > 20) {
                    return true;
                }
            }

        }
        return false;
    }

    private void visualise(int seconds) {
        var chars = new Character[MAX_Y][MAX_X];
        for (int x = 0; x < MAX_X; x++) {
            for (int y = 0; y < MAX_Y; y++) {
                chars[y][x] = '.';
            }
        }
        bots.stream().map(Bot::position)
                .forEach(p -> {
                    chars[p.y()][p.x()] = '#';
                });
        System.out.println(seconds);
        System.out.println(InputUtils.toString(chars));
    }

    Optional<Quadrant> computeQuadrant(Vector2 position) {
        int halfX = (MAX_X - 1) / 2;
        int halfY = (MAX_Y - 1) / 2;
        if (position.x() == halfX || position.y() == halfY) {
            return Optional.empty();
        }
        if (position.x() < halfX) {
            if (position.y() < halfY) {
                return Optional.of(TOP_LEFT);
            }
            return Optional.of(BOTTOM_LEFT);
        }
        if (position.y() < halfY) {
            return Optional.of(TOP_RIGHT);
        } else {
            return Optional.of(BOTTOM_RIGHT);
        }
    }

    enum Quadrant {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }

    record Bot(Vector2 position, Vector2 velocity) {
        Bot move() {
            var newPosition = position().add(velocity());
            if (newPosition.x() >= MAX_X) {
                newPosition = new Vector2(newPosition.x() - MAX_X, newPosition.y());
            } else if (newPosition.x() < 0) {
                newPosition = new Vector2(newPosition.x() + MAX_X, newPosition.y());
            }
            if (newPosition.y() >= MAX_Y) {
                newPosition = new Vector2(newPosition.x(), newPosition.y() - MAX_Y);
            } else if (newPosition.y() < 0) {
                newPosition = new Vector2(newPosition.x(), newPosition.y() + MAX_Y);
            }
            return new Bot(newPosition, velocity());
        }
    }
}
