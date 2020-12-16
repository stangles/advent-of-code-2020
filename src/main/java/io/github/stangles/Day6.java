/*
 * Copyright (c) 2011-present Sonatype, Inc. All rights reserved.
 * Includes the third-party code listed at http://links.sonatype.com/products/clm/attributions.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package io.github.stangles;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class Day6
    extends AocExecutable<String>
{
  @Override
  String part1(final Collection<String> input) {
    int count = 0;
    Set<Character> unique = new HashSet<>();
    for (String s : input) {
      if (s.isEmpty()) {
        count += unique.size();
        unique.clear();
      }
      for (char ch : s.toCharArray()) {
        unique.add(ch);
      }
    }
    return count + "";
  }

  @Override
  String part2(final Collection<String> input) {
    int count = 0;
    Set<Character> unique = new HashSet<>();
    boolean first = true;
    for (String s : input) {
      if (s.isEmpty()) {
        count += unique.size();
        unique.clear();
        first = true;
      }
      else {
        if (first) {
          for (char ch : s.toCharArray()) {
            unique.add(ch);
          }
          first = false;
        }
        else {
          Set<Character> answers = new HashSet<>();
          for (char ch : s.toCharArray()) {
            answers.add(ch);
          }
          unique.retainAll(answers);
        }
      }
    }
    return count + "";
  }

  @Override
  void execute() throws Exception {
    printSolutions(getInput("/day6.txt", Function.identity()));
  }
}
