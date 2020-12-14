package io.github.stangles;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AocExecutable<T>
{
  List<T> getInput(String resourceName, Function<String, T> mapper) throws URISyntaxException {
    File file = new File(Day1.class.getResource(resourceName).toURI());
    try (Stream<String> lines = Files.lines(file.toPath(), StandardCharsets.UTF_8)) {
      return lines.map(mapper).collect(Collectors.toList());
    }
    catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }

  void printSolutions(Collection<T> input) {
    System.out.println("part 1: " + part1(input));
    System.out.println("part 2: " + part2(input));
  }

  abstract String part1(Collection<T> input);
  abstract String part2(Collection<T> input);
  abstract void execute() throws Exception;

  static class Pair<R, S> {
    Pair(R x, S y) {
      this.x = x;
      this.y = y;
    }
    R x;
    S y;

    @Override
    public boolean equals(final Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Pair<?, ?> pair = (Pair<?, ?>) o;
      return Objects.equals(x, pair.x) && Objects.equals(y, pair.y);
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }
  }
}