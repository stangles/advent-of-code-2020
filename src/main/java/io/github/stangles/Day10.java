package io.github.stangles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day10
    extends AocExecutable<Integer>
{
  @Override
  String part1(final Collection<Integer> input) {
    List<Integer> complete = getComplete(input);

    int oneVolt = 0, threeVolt = 0;
    for (int i = 0; i < complete.size() - 1; i++) {
      int diff = complete.get(i + 1) - complete.get(i);
      if (diff == 1) {
        oneVolt++;
      }
      else if (diff == 3) {
        threeVolt++;
      }
    }
    return oneVolt * threeVolt + "";
  }

  private List<Integer> getComplete(Collection<Integer> input) {
    List<Integer> complete = new LinkedList<>(Collections.singletonList(0));
    List<Integer> sorted = input.stream().sorted().collect(Collectors.toList());
    sorted.add(sorted.get(sorted.size() - 1) + 3);
    complete.addAll(sorted);

    return complete;
  }

  @Override
  String part2(final Collection<Integer> input) {
    List<Integer> complete = getComplete(input);

    return countConfigurations(complete) + "";
  }

  private long countConfigurations(List<Integer> adapters) {
    List<Integer> removed = new ArrayList<>();
    for (int i = 0; i < adapters.size() - 2; i++) {
      if (adapters.get(i + 2) - adapters.get(i) <= 3) {
        removed.add(i + 1);
      }
    }

    long count = 1;
    for (int i = 0; i < removed.size(); i++) {
      int groupSize = 1;
      while (i < removed.size() - 1 && removed.get(i + 1) - removed.get(i) == 1) {
        groupSize++;
        i++;
      }

      if (groupSize < 3) {
        count *= Math.pow(2.0, groupSize);
      }
      else {
        count *= 7;
      }
    }
    return count;
  }

  private long countConfigurationsLolGoodLuck(List<Integer> adapters, int subIdx) {
    long count = 1;
    for (int i = subIdx; i < adapters.size() - 2; i++) {
      if (adapters.get(i + 2) - adapters.get(i) <= 3) {
        int removed = adapters.remove(i + 1);
        count += countConfigurationsLolGoodLuck(adapters, i);
        adapters.add(i + 1, removed);
      }
    }

    return count;
  }

  @Override
  void execute() throws Exception {
    printSolutions(getInput("/day10.txt", Integer::parseInt));
  }
}
