/*
 * Copyright (c) 2011-present Sonatype, Inc. All rights reserved.
 * Includes the third-party code listed at http://links.sonatype.com/products/clm/attributions.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package io.github.stangles;

public class Runner
{
  public static void main(String[] args) throws Exception {
    AocExecutable executable = new Day6();
    executable.execute();
  }
}
