package com.faire.example.virtualthreads;

/** Main class to run the examples. */
public class Main {
  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("Usage: java Main <exampleNumber> <useVirtualThreads>");
      System.exit(1);
    }
    int exampleNumber = Integer.parseInt(args[0]);
    boolean useVirtualThreads = Boolean.parseBoolean(args[1]);
    System.out.println("Running example " + exampleNumber + " with " + (useVirtualThreads ? "virtual" : "platform") + " threads");
    System.out.println();

    BaseExample example = exampleNumber == 1 ? new Example1() : exampleNumber == 2 ? new Example2() : new Example3();

    Thread.currentThread().setName("main");
    Threads.startThreadsDoWorkAndJoin(
        new String[]{"threadA", "threadB"},
        new Runnable[]{example::threadAWork, example::threadBWork},
        useVirtualThreads,
        example::mainThreadWork
    );

    System.out.println();
    System.out.println("Done running example " + exampleNumber);
  }
}