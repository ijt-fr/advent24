package com.advent.day9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import com.advent.Puzzle;

public class DiskFragmenter extends Puzzle {

    private ArrayList<Block> diskBlocks;

    @Override
    public void parseInput(List<String> lines) {
        char[] diskMap = lines.getFirst().toCharArray();
        diskBlocks = new ArrayList<>();
        for (int i = 0; i < diskMap.length; i++) {
            int blockSize = Character.getNumericValue(diskMap[i]);
            if (i % 2 == 0) {
                for (int j = 0; j < blockSize; j++) {
                    diskBlocks.add(new FileBlock(i / 2));
                }
            } else {
                for (int j = 0; j < blockSize; j++) {
                    diskBlocks.add(new FreeSpace());
                }
            }
        }
    }

    @Override
    public Object computePart1() {
        int lastFileBlock = diskBlocks.size() - 1;
        for (int i = 0; i < lastFileBlock; i++) {
            if (diskBlocks.get(i) instanceof FreeSpace) {
                lastFileBlock = getLastFileBlock(diskBlocks, lastFileBlock);
                if (i < lastFileBlock) {
                    Collections.swap(diskBlocks, i, lastFileBlock);
                }
            }
        }
        return IntStream.range(0, diskBlocks.size())
                       .mapToLong(i -> {
                           if (diskBlocks.get(i) instanceof FileBlock) {
                               return ((long) i) * ((long) ((FileBlock) diskBlocks.get(i)).id());
                           } else {
                               return 0L;
                           }
                       }).reduce(Long::sum).orElse(0);
    }

    private int getLastFileBlock(ArrayList<Block> diskBlocks, int lastFileBlock) {
        for (int i = lastFileBlock; i > 0; i--) {
            if (diskBlocks.get(i) instanceof FileBlock) {
                return i;
            }
        }
        return 0;
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

    public record FileBlock(int id) implements Block {

    }

    public record FreeSpace() implements Block {

    }

    public interface Block {

    }
}
