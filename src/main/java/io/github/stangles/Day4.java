package io.github.stangles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

public class Day4
    extends AocExecutable<String>
{
  private static Pattern hairColorPattern = Pattern.compile("^#[0-9a-f]{6}$");

  private static Pattern eyeColorPattern = Pattern.compile("^amb|blu|brn|gry|grn|hzl|oth$");

  private static Pattern passportIdPattern = Pattern.compile("^[0-9]{9}$");

  @Override
  String part1(final Collection<String> input) {
    List<Passport> passports = new ArrayList<>();
    StringBuilder cur = new StringBuilder();
    for (String line : input) {
      if (!line.trim().isEmpty()) {
        cur.append(" ").append(line.trim());
      }
      else {
        Passport passport = new Passport();
        String record = cur.append(" ").toString();
        passport.birthYear = getField("byr:", record);
        passport.issueYear = getField("iyr:", record);
        passport.expirationYear = getField("eyr:", record);
        passport.height = getField("hgt:", record);
        passport.hairColor = getField("hcl:", record);
        passport.eyeColor = getField("ecl:", record);
        passport.passportId = getField("pid:", record);
        passport.countryId = getField("cid:", record);

        passports.add(passport);
        cur.delete(0, cur.length());
      }
    }

    int count = 0;
    for (Passport p : passports) {
      count += p.isPresent() ? 1 : 0;
    }

    return "" + count;
  }

  private String getField(String fieldName, String str) {
    int idx = str.indexOf(fieldName);
    if (idx == -1) {
      return null;
    }
    return str.substring(idx + 4, str.indexOf(" ", idx + 4));
  }

  @Override
  String part2(final Collection<String> input) {
    List<Passport> passports = new ArrayList<>();
    StringBuilder cur = new StringBuilder();
    for (String line : input) {
      if (!line.trim().isEmpty()) {
        cur.append(" ").append(line.trim());
      }
      else {
        Passport passport = new Passport();
        String record = cur.append(" ").toString();
        passport.birthYear = getField("byr:", record);
        passport.issueYear = getField("iyr:", record);
        passport.expirationYear = getField("eyr:", record);
        passport.height = getField("hgt:", record);
        passport.hairColor = getField("hcl:", record);
        passport.eyeColor = getField("ecl:", record);
        passport.passportId = getField("pid:", record);
        passport.countryId = getField("cid:", record);

        passports.add(passport);
        cur.delete(0, cur.length());
      }
    }

    int count = 0;
    for (Passport p : passports) {
      count += p.isValid() ? 1 : 0;
    }

    return "" + count;
  }

  @Override
  void execute() throws Exception {
    printSolutions(getInput("/day4.txt", Function.identity()));
  }

  static class Passport
  {
    String birthYear;

    String issueYear;

    String expirationYear;

    String height;

    String hairColor;

    String eyeColor;

    String passportId;

    String countryId;

    boolean isPresent() {
      return birthYear != null && issueYear != null && expirationYear != null && height != null && hairColor != null &&
          eyeColor != null && passportId != null;
    }

    boolean isValid() {
      if (!isPresent()) {
        return false;
      }

      int bYear = Integer.parseInt(birthYear);
      if (1920 > bYear || 2002 < bYear) {
        return false;
      }

      int iYear = Integer.parseInt(issueYear);
      if (2010 > iYear || 2020 < iYear) {
        return false;
      }

      int eYear = Integer.parseInt(expirationYear);
      if (2020 > eYear || 2030 < eYear) {
        return false;
      }

      if (height.contains("cm")) {
        int h = Integer.parseInt(height.substring(0, height.indexOf("c")));
        if (150 > h || 193 < h) {
          return false;
        }
      }
      else if (height.contains("in")) {
        int h = Integer.parseInt(height.substring(0, height.indexOf("i")));
        if (59 > h || 76 < h) {
          return false;
        }
      }
      else {
        return false;
      }

      if (!hairColorPattern.matcher(hairColor).matches()) {
        return false;
      }

      if (!eyeColorPattern.matcher(eyeColor).matches()) {
        return false;
      }

      if (!passportIdPattern.matcher(passportId).matches()) {
        return false;
      }

      return true;
    }
  }
}
