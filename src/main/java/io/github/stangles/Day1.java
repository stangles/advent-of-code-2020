package io.github.stangles;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day1
    extends AocExecutable<Integer>
{
  @Override
  void execute() {
    List<Integer> input;
    try {
      input = getInput("/day1.txt", Integer::parseInt);
    }
    catch (URISyntaxException ex) {
      throw new RuntimeException(ex);
    }

    printSolutions(input);
  }

  @Override
  String part1(Collection<Integer> input) {
    Set<Integer> seen = new HashSet<>(input.size());
    for (Integer integer : input) {
      int sought = 2020 - integer;
      if (seen.contains(sought)) {
        return sought * integer + "";
      }
      seen.add(integer);
    }
    throw new IllegalArgumentException("No two numbers in the input add to 2020");
  }

  @Override
  String part2(Collection<Integer> input) {
    // first, subtract each number in the input from 2020 and save it back to the list (transform)
    // second, for every transformed number in the list, subtract each number in the list from it, if the difference
    // from that number is present in the list, then you've found three numbers that sum to 2020
    Set<Integer> transformed = new HashSet<>(input);

    for (Integer first : transformed) {
      int intermediate = 2020 - first;
      for (Integer third : transformed) {
        if (transformed.contains(intermediate - third)) {
          return first * (intermediate - third) * third + "";
        }
      }
    }
    throw new IllegalArgumentException("No three numbers in the input add to 2020");
  }
}