package io.github.stangles;

import java.util.Collection;
import java.util.function.Function;

public class Day11
    extends AocExecutable<String>
{
  @Override
  String part1(final Collection<String> input) {
    char[][] grid = new char[input.size()][];
    int i = 0;
    for (String s : input) {
      grid[i++] = s.toCharArray();
    }

    boolean changed;
    do {
      changed = false;
      char[][] next = new char[grid.length][grid[0].length];
      for (int x = 0; x < grid.length; x++) {
        for (int y = 0; y < grid[x].length; y++) {
          if (grid[x][y] == '.') {
            next[x][y] = '.';
          }
          else if (grid[x][y] == 'L' && countAdjacent(grid, x, y) == -1) {
            changed = true;
            next[x][y] = '#';
          }
          else if (grid[x][y] == '#' && countAdjacent(grid, x, y) >= 4) {
            changed = true;
            next[x][y] = 'L';
          }
          else {
            next[x][y] = grid[x][y];
          }
        }
      }
      grid = next;
    }
    while (changed);

    return countOccupied(grid) + "";
  }

  private int countAdjacent(char[][] grid, int x, int y) {
    int count = 0;
    boolean allAdjacentEmpty = true;
    for (int i = -1; i < 2; i++) {
      for (int j = -1; j < 2; j++) {
        if (!(i == 0 && j == 0) && isCoordValid(grid, x + i, y + j)) {
          if (grid[x + i][y + j] == '#') {
            allAdjacentEmpty = false;
            count++;
          }
        }
      }
    }

    if (allAdjacentEmpty) {
      return -1;
    }
    return count;
  }

  private boolean isCoordValid(char[][] grid, int x, int y) {
    if (x < 0 || x >= grid.length) {
      return false;
    }
    return y >= 0 && y < grid[x].length;
  }

  private int countOccupied(char[][] grid) {
    int count = 0;
    for (char[] row : grid) {
      for (char c : row) {
        if (c == '#') {
          count++;
        }
      }
    }
    return count;
  }

  @Override
  String part2(final Collection<String> input) {
    char[][] grid = new char[input.size()][];
    int i = 0;
    for (String s : input) {
      grid[i++] = s.toCharArray();
    }

    boolean changed;
    do {
      changed = false;
      char[][] next = new char[grid.length][grid[0].length];
      for (int x = 0; x < grid.length; x++) {
        for (int y = 0; y < grid[x].length; y++) {
          if (grid[x][y] == '.') {
            next[x][y] = '.';
          }
          else if (grid[x][y] == 'L' && countVisible(grid, x, y) == -1) {
            changed = true;
            next[x][y] = '#';
          }
          else if (grid[x][y] == '#' && countVisible(grid, x, y) >= 5) {
            changed = true;
            next[x][y] = 'L';
          }
          else {
            next[x][y] = grid[x][y];
          }
        }
      }
      grid = next;
    }
    while (changed);

    return countOccupied(grid) + "";
  }

  private int countVisible(char[][] grid, int x, int y) {
    boolean allVisibleEmpty = true;
    int count = 0;
    for (int xAdj = -1; xAdj < 2; xAdj++) {
      for (int yAdj = -1; yAdj < 2; yAdj++) {
        for (int i = x + xAdj, j = y + yAdj; !(xAdj == 0 && yAdj == 0) && isCoordValid(grid, i, j);
             i += xAdj, j += yAdj) {
          if (grid[i][j] == '#') {
            allVisibleEmpty = false;
            count++;
            break;
          }
          else if (grid[i][j] == 'L') {
            break;
          }
        }
      }
    }
    if (allVisibleEmpty) {
      return -1;
    }
    return count;
  }

  @Override
  void execute() throws Exception {
    printSolutions(getInput("/day11.txt", Function.identity()));
  }
}
