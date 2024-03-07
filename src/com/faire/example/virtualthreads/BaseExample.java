package com.faire.example.virtualthreads;

abstract class BaseExample {
  /** Work done by first child thread. */
  abstract void threadAWork();

  /** Work done by second child thread. */
  abstract void threadBWork();

  /** Work done by the main thread after creating child threads and before joining them. */
  void mainThreadWork() {
    // Default implementation does nothing.
  }
}
