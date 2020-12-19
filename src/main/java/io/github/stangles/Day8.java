package io.github.stangles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.stangles.Day8.Node.Insn;

public class Day8
    extends AocExecutable<String>
{
  Pattern pattern = Pattern.compile("^(acc|jmp|nop) ([+-])(\\d+)$");

  @Override
  String part1(final Collection<String> input) {
    List<String> inputList = new ArrayList<>(input);

    Set<String> insnLog = new HashSet<>();
    int acc = 0;
    for (int i = 0; i < inputList.size(); i++) {
      String s = inputList.get(i);
      Matcher m = pattern.matcher(s);
      if (m.matches()) {
        String insn = m.group(1);
        int val = Integer.parseInt(m.group(3));
        if ("-".equals(m.group(2))) {
          val *= -1;
        }

        if ("acc".equals(insn)) {
          if (!insnLog.add(i + insn)) {
            return acc + "";
          }
          acc += val;
        }
        else if ("jmp".equals(insn)) {
          if (!insnLog.add(i + insn)) {
            return acc + "";
          }
          i += val - 1;
        }
        else {
          if (!insnLog.add(i + insn)) {
            return acc + "";
          }
        }
      }
    }
    throw new IllegalArgumentException("The program does not have an infinite loop");
  }

  @Override
  String part2(final Collection<String> input) {
    List<Node> nodes = new ArrayList<>();
    int pos = 0;
    for (String s : input) {
      Matcher m = pattern.matcher(s);
      if (m.matches()) {
        String insn = m.group(1);
        int val = Integer.parseInt(m.group(3));
        if ("-".equals(m.group(2))) {
          val *= -1;
        }

        nodes.add(new Node(insn, val, pos++));
      }
    }

    for (int i = 0; i < nodes.size(); i++) {
      Node cur = nodes.get(i);
      if (i < nodes.size() - 1) {
        if (cur.insn == Insn.JMP) {
          cur.next = nodes.get(i + cur.value);
        }
        else {
          cur.next = nodes.get(i + 1);
        }
      }
    }

    Node cur = nodes.get(0);
    Node prev = null;
    while (hasCycle(nodes.get(0))) {
      flipInsn(prev, nodes);
      prev = cur;
      cur = cur.next;
      flipInsn(prev, nodes);
    }

    return "" + runProgram(nodes.get(0));
  }

  private boolean hasCycle(Node head) {
    Node tortoise = head, hare = head.next;
    while (tortoise != hare) {
      if (hare == null || hare.next == null) {
        return false;
      }
      tortoise = tortoise.next;
      hare = hare.next.next;
    }

    return true;
  }

  private void flipInsn(Node n, List<Node> nodes) {
    if (n != null && n.insn == Insn.JMP) {
      n.insn = Insn.NOP;
      n.next = nodes.get(n.pos + 1);
    }
    else if (n != null && n.insn == Insn.NOP) {
      n.insn = Insn.JMP;
      n.next = nodes.get(n.pos + n.value);
    }
  }

  private int runProgram(Node n) {
    int acc = 0;
    while (n != null) {
      if (n.insn == Insn.ACC) {
        acc += n.value;
      }
      n = n.next;
    }
    return acc;
  }

  @Override
  void execute() throws Exception {
    printSolutions(getInput("/day8.txt", Function.identity()));
  }

  static class Node
  {
    Insn insn;

    int value;

    int pos;

    Node next;

    private Node(String insn, int value, int pos) {
      this.insn = Insn.valueOf(insn.toUpperCase(Locale.ROOT));
      this.value = value;
      this.pos = pos;
    }

    @Override
    public String toString() {
      return insn + " " + value;
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Node node = (Node) o;
      return value == node.value && insn == node.insn && Objects.equals(next, node.next);
    }

    @Override
    public int hashCode() {
      return Objects.hash(insn, value, next);
    }

    enum Insn
    {
      JMP, ACC, NOP
    }
  }
}
