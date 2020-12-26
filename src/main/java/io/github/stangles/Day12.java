package io.github.stangles;

import java.util.Collection;
import java.util.function.Function;

public class Day12
    extends AocExecutable<String>
{
  @Override
  String part1(final Collection<String> input) {
    int curFace = 90;
    int ns = 0, ew = 0;
    for (String s : input) {
      char insn = s.substring(0, 1).charAt(0);
      int mag = Integer.parseInt(s.substring(1));

      if (insn == 'L' || insn == 'R') {
        if (insn == 'L') {
          mag *= -1;
        }
        curFace = (360 + curFace + mag) % 360;
      }
      else {
        if ((insn == 'F' && curFace == 0) || insn == 'N') {
          ns += mag;
        }
        else if ((insn == 'F' && curFace == 90) || insn == 'E') {
          ew += mag;
        }
        else if ((insn == 'F' && curFace == 180) || insn == 'S') {
          ns -= mag;
        }
        else {
          ew -= mag;
        }
      }
    }
    return Math.abs(ns) + Math.abs(ew) + "";
  }

  @Override
  String part2(final Collection<String> input) {
    int wns = 1, wew = 10;
    int sns = 0, sew = 0;

    for (String s : input) {
      char insn = s.substring(0, 1).charAt(0);
      int mag = Integer.parseInt(s.substring(1));

      if (insn == 'L' || insn == 'R') {
        if (insn == 'L') {
          mag *= -1;
        }
        int adj = (360 + mag) % 360;
        if (adj == 90 || adj == 270) {
          int tmp = wns;
          wns = wew;
          wew = tmp;

          if (adj == 90) {
            wns *= -1;
          }
          else {
            wew *= -1;
          }
        }

        if (adj == 180) {
          wns *= -1;
          wew *= -1;
        }
      }
      else {
        if (insn == 'F') {
          sns += mag * wns;
          sew += mag * wew;
        }
        if (insn == 'S' || insn == 'W') {
          mag *= -1;
        }
        if (insn == 'N' || insn == 'S') {
          wns += mag;
        }
        else if (insn == 'E' || insn == 'W') {
          wew += mag;
        }
      }
    }

    return Math.abs(sns) + Math.abs(sew) + "";
  }

  @Override
  void execute() throws Exception {
    printSolutions(getInput("/day12.txt", Function.identity()));
  }
}
