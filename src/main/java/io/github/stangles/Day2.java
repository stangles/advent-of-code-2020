package io.github.stangles;

import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2
    extends AocExecutable<String>
{
  @Override
  void execute() {
    List<String> input;
    try {
      input = getInput("/day2.txt", Function.identity());
    }
    catch (URISyntaxException ex) {
      throw new RuntimeException(ex);
    }

    printSolutions(input);
  }

  @Override
  String part1(final Collection<String> input) {
    Pattern pattern = Pattern.compile("^(\\d+)-(\\d+) (\\p{Lower}{1}): (\\p{Lower}+$)");
    int validCount = 0;
    for (String pass : input) {
      Matcher matcher = pattern.matcher(pass);
      if (!matcher.matches()) {
        throw new IllegalArgumentException(pass + " does not match the required pattern");
      }
      int min = Integer.parseInt(matcher.group(1));
      int max = Integer.parseInt(matcher.group(2));
      char ch = matcher.group(3).charAt(0);

      int count = 0;
      for (char c : matcher.group(4).toCharArray()) {
        if (c == ch) {
          count++;
        }
      }
      if (min <= count && count <= max) {
        validCount++;
      }
    }

    return ""+validCount;
  }

  @Override
  String part2(final Collection<String> input) {
    Pattern pattern = Pattern.compile("^(\\d+)-(\\d+) (\\p{Lower}{1}): (\\p{Lower}+$)");
    int validCount = 0;
    for (String pass : input) {
      Matcher matcher = pattern.matcher(pass);
      if (!matcher.matches()) {
        throw new IllegalArgumentException(pass + " does not match the required pattern");
      }
      int firstPos = Integer.parseInt(matcher.group(1));
      int lastPos = Integer.parseInt(matcher.group(2));
      char ch = matcher.group(3).charAt(0);

      boolean valid = false;
      char[] charArr = matcher.group(4).toCharArray();
      for (int i = 0; i < charArr.length; i++) {
        if (charArr[i] == ch) {
          if (i + 1 == firstPos) {
            valid = true;
          }
          else if (i + 1 == lastPos) {
            valid = !valid;
            break;
          }
        }
      }
      if (valid) {
        validCount++;
      }
    }

    return ""+validCount;
  }
}
