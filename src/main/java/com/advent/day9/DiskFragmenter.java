package com.advent.day9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import com.advent.Puzzle;

public class DiskFragmenter extends Puzzle {
    private char[] diskMap;

    @Override
    public void parseInput(List<String> lines) {
        diskMap = lines.getFirst().toCharArray();
    }

    @Override
    public Object computePart1() {
        List<Block> diskBlocks = getPart1DiskBlocks();
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

    private List<Block> getPart1DiskBlocks() {
        List<Block> diskBlocks = new ArrayList<>();
        for (int i = 0; i < diskMap.length; i++) {
            int blockSize = Character.getNumericValue(diskMap[i]);
            if (i % 2 == 0) {
                for (int j = 0; j < blockSize; j++) {
                    diskBlocks.add(new FileBlock(i / 2, 0));
                }
            } else {
                for (int j = 0; j < blockSize; j++) {
                    diskBlocks.add(new FreeSpace(0));
                }
            }
        }
        return diskBlocks;
    }

    private int getLastFileBlock(List<Block> diskBlocks, int lastFileBlock) {
        for (int i = lastFileBlock; i > 0; i--) {
            if (diskBlocks.get(i) instanceof FileBlock) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public Object part1Answer() {
        return 6344673854800L;
    }

    @Override
    public Object computePart2() {
        List<Block> diskBlocks = getPart2DiskBlocks();
        for (int i = diskBlocks.size() - 1; i >= 0 ; i--) {
            Block block1 = diskBlocks.get(i);
            if (block1 instanceof FileBlock) {
                for (int j = 0; j < i; j++) {
                    Block block2 = diskBlocks.get(j);
                    int gap = block2.size() - block1.size();
                    if (block2 instanceof FreeSpace && gap >= 0) {
                        if (gap == 0) {
                            Collections.swap(diskBlocks, i, j);
                        }
                        if (gap > 0) {
                            diskBlocks.set(j, block1);
                            diskBlocks.set(i, new FreeSpace(block1.size()));
                            diskBlocks.add(j + 1, new FreeSpace(gap));
                        }
                        consolidateFreeSpaces(diskBlocks);
                        break;
                    }
                }
            }
        }
        long checksum = 0L;
        var it = diskBlocks.iterator();

        long i = 0L;
        while (it.hasNext()) {
            Block block = it.next();
            if (block instanceof FileBlock) {
                for (int j = 0; j < block.size(); j++) {
                    checksum += i++ * ((long) ((FileBlock) block).id());
                }
            } else {
                i += block.size();
            }
        }
        return checksum;
    }

    private List<Block> getPart2DiskBlocks() {
        List<Block> diskBlocks = new ArrayList<>();
        for (int i = 0; i < diskMap.length; i++) {
            int blockSize = Character.getNumericValue(diskMap[i]);
            if (i % 2 == 0) {
                diskBlocks.add(new FileBlock(i / 2, blockSize));

            } else {
                diskBlocks.add(new FreeSpace(blockSize));
            }
        }
        return diskBlocks;
    }

    private void consolidateFreeSpaces(List<Block> diskBlocks) {
        int i = 0;
        while (i < diskBlocks.size() - 1) {
            var block1 = diskBlocks.get(i);
            var block2 = diskBlocks.get(i + 1);
            if (block1 instanceof FreeSpace && block2 instanceof FreeSpace) {
                diskBlocks.remove(i + 1);
                diskBlocks.remove(i);
                diskBlocks.add(i, new FreeSpace(block1.size() + block2.size()));
            }
            i++;
        }
    }

    @Override
    public Object part2Answer() {
        return 6360363199987L;
    }

    public record FileBlock(int id, int size) implements Block {

    }

    public record FreeSpace(int size) implements Block {

    }

    public interface Block {

        int size();

    }
}
