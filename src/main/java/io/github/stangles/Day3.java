package io.github.stangles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Day3
    extends AocExecutable<char[]>
{
  @Override
  String part1(final Collection<char[]> input) {
    int column = 0, trees = 0;
    for (char[] arr : input) {
      if (arr[column] == '#') {
        trees++;
      }
      column = (column + 3) % arr.length;
    }

    for (char[] arr : input) {
      System.out.println(arr);
    }
    return trees + "";
  }

  @Override
  String part2(final Collection<char[]> input) {
    List<char[]> inputList = new ArrayList<>(input);
    Map<Pair<Integer, Integer>, Integer> adjustments = new HashMap<>();
    adjustments.put(new Pair<>(1, 1), 0);
    adjustments.put(new Pair<>(3, 1), 0);
    adjustments.put(new Pair<>(5, 1), 0);
    adjustments.put(new Pair<>(7, 1), 0);
    adjustments.put(new Pair<>(1, 2), 0);

    for (int i = 1; i < inputList.size(); i++) {
      for (Pair<Integer, Integer> adj : adjustments.keySet()) {
        if (i % adj.y == 0) {
          char[] arr = inputList.get(i);
          int column = ((i / adj.y) * adj.x) % arr.length;
          if (arr[column] == '#') {
            adjustments.merge(adj, 1, Integer::sum);
          }
        }
      }
    }

    return "" + adjustments.values().stream().map(Long::new).reduce(1L, (a, b) -> a * b);
  }

  @Override
  void execute() throws Exception {
    List<char[]> input = getInput("/day3.txt", String::toCharArray);

    printSolutions(input);
  }
}
