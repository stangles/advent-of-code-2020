package io.github.stangles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class Day5
    extends AocExecutable<String>
{
  @Override
  String part1(final Collection<String> input) {
    int highest = Integer.MIN_VALUE;
    for (String s : input) {
      int l = 0, h = 127, v = 0;
      char[] arr = s.toCharArray();
      for (int i = 0; i < arr.length; i++) {
        char c = arr[i];
        if (i < 7) {
          if (c == 'F') {
            h = (h + l) / 2;
          }
          else if (c == 'B') {
            l = (int) Math.round((h + l) / 2.0);
          }
        }
        else {
          if (i == 7) {
            v = l * 8;
            l = 0;
            h = 7;
          }
          if (c == 'L') {
            h = (h + l) / 2;
          }
          else if (c == 'R') {
            l = (int) Math.round((h + l) / 2.0);
          }
          if (i == arr.length - 1) {
            v += l;
          }
        }
      }
      highest = Math.max(highest, v);
    }
    return highest + "";
  }

  @Override
  String part2(final Collection<String> input) {
    List<Integer> ids = new ArrayList<>(input.size());
    for (String s : input) {
      int l = 0, h = 127, v = 0;
      char[] arr = s.toCharArray();
      for (int i = 0; i < arr.length; i++) {
        char c = arr[i];
        if (i < 7) {
          if (c == 'F') {
            h = (h + l) / 2;
          }
          else if (c == 'B') {
            l = (int) Math.round((h + l) / 2.0);
          }
        }
        else {
          if (i == 7) {
            v = l * 8;
            l = 0;
            h = 7;
          }
          if (c == 'L') {
            h = (h + l) / 2;
          }
          else if (c == 'R') {
            l = (int) Math.round((h + l) / 2.0);
          }
          if (i == arr.length - 1) {
            v += l;
          }
        }
      }
      ids.add(v);
    }
    ids.sort(Comparator.naturalOrder());

    for (int i = 1; i < ids.size(); i++) {
      int prev = ids.get(i - 1);
      int cur = ids.get(i);
      if (cur - prev > 1) {
        return cur - 1 + "";
      }
    }
    throw new IllegalArgumentException("No missing ID found in input");
  }

  @Override
  void execute() throws Exception {
    printSolutions(getInput("/day5.txt", Function.identity()));
  }
}
