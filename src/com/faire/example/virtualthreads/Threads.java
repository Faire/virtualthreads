package com.faire.example.virtualthreads;

/** Utility class for working with threads. */
class Threads {
  static void startThreadsDoWorkAndJoin(String[] names, Runnable[] tasks, boolean useVirtualThreads, Runnable task) {
    Thread.Builder[] threadBuilders = new Thread.Builder[tasks.length];
    for (int i = 0; i < tasks.length; i++) {
      Threads.print("Starting " + (useVirtualThreads ? "virtual " : "") + names[i]);
      threadBuilders[i] = useVirtualThreads ? Thread.ofVirtual() : Thread.ofPlatform();
      threadBuilders[i].name(names[i]);
    }
    Thread[] threads = new Thread[tasks.length];
    for (int i = 0; i < tasks.length; i++) {
      threads[i] = threadBuilders[i].start(tasks[i]);
    }

    task.run();

    Threads.print("Joining threads");
    try {
      for (Thread thread : threads) {
        thread.join();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    Threads.print("Done!");
  }

  static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  static void print(String message) {
    System.out.println(Thread.currentThread().getName() + ": " + message);
  }

  static void sayHello() {
    Threads.print("Hello");
  }
}
