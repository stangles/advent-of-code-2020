/*
 * Copyright (c) 2011-present Sonatype, Inc. All rights reserved.
 * Includes the third-party code listed at http://links.sonatype.com/products/clm/attributions.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package io.github.stangles;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7
    extends AocExecutable<String>
{
  private Pattern innerBagsPattern = Pattern.compile("\\d ([a-z]+ [a-z]+)");
  private Pattern containingBagPattern = Pattern.compile("^([a-z]+ [a-z]+) bags contain .*$");

  @Override
  String part1(final Collection<String> input) {
    Map<String, Set<Pair<Integer, String>>> rules = parseRules(input);
    int count = 0;
    for (String bag : rules.keySet()) {
      if (containsBag(rules, bag, "shiny gold")) {
        count++;
      }
    }
    return "" + count;
  }

  private Map<String, Set<Pair<Integer, String>>> parseRules(Collection<String> input) {
    Map<String, Set<Pair<Integer, String>>> rules = new HashMap<>();
    for (String s : input) {
      Matcher m = containingBagPattern.matcher(s);
      String outerBag = null;
      if (m.matches()) {
        outerBag = m.group(1);
      }

      m = innerBagsPattern.matcher(s);
      Set<Pair<Integer, String>> innerBags = new HashSet<>();
      while (m.find()) {
        String group = m.group();
        int count = Integer.parseInt(group.substring(0, 1));
        innerBags.add(new Pair<>(count, m.group().substring(2)));
      }
      rules.put(outerBag, innerBags);
    }

    return rules;
  }

  private boolean containsBag(Map<String, Set<Pair<Integer, String>>> rules, String outerBag, String targetBag) {
    Set<Pair<Integer, String>> innerBags = rules.get(outerBag);
    boolean containsBag = false;
    for (Pair<Integer, String> p : innerBags) {
      if (p.y.equals(targetBag)) {
        return true;
      }
      containsBag |= containsBag(rules, p.y, targetBag);
    }
    return containsBag;
  }

  private int countInnerBags(Map<String, Set<Pair<Integer, String>>> rules, String outerBag) {
    Set<Pair<Integer, String>> innerBags = rules.get(outerBag);

    int sum = 0;
    for (Pair<Integer, String> p : innerBags) {
      sum += p.x * (1 + countInnerBags(rules, p.y));
    }

    return sum;
  }

  @Override
  String part2(final Collection<String> input) {
    return countInnerBags(parseRules(input), "shiny gold") + "";
  }

  @Override
  void execute() throws Exception {
    printSolutions(getInput("/day7.txt", Function.identity()));
  }
}
