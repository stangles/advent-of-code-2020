package io.github.stangles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day9
    extends AocExecutable<Long>
{
  private static final int PREAMBLE_LENGTH = 25;

  @Override
  String part1(final Collection<Long> input) {
    List<Long> inputList = new ArrayList<>(input);
    Set<Long> preamble = new HashSet<>(PREAMBLE_LENGTH);

    int count = 0;
    for (Long cur : input) {
      if (count++ >= PREAMBLE_LENGTH) {
        boolean found = false;
        for (Long l : preamble) {
          if (preamble.contains(cur - l)) {
            found = true;
            break;
          }
        }
        if (!found) {
          return cur + "";
        }
        preamble.remove(inputList.get(count - PREAMBLE_LENGTH - 1));
      }
      preamble.add(cur);
    }
    throw new IllegalArgumentException("All numbers in input can be constructed via preamble");
  }

  @Override
  String part2(final Collection<Long> input) {
    long target = Long.parseLong(part1(input));
    List<Long> inputList = new ArrayList<>(input);

    long sum = 0;
    int start = 0, end = 0;
    while (true) {
      if (sum == target) {
        long min = Long.MAX_VALUE, max = Long.MIN_VALUE;
        for (int i = start; i < end; i++) {
          long cur = inputList.get(i);
          min = Math.min(min, cur);
          max = Math.max(max, cur);
        }
        return min + max + "";
      }
      else if (sum > target) {
        sum -= inputList.get(start++);
      }
      else {
        sum += inputList.get(end++);
      }
    }
  }

  @Override
  void execute() throws Exception {
    printSolutions(getInput("/day9.txt", Long::parseLong));
  }
}
