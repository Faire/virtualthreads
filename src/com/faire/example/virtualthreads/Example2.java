package com.faire.example.virtualthreads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** Singleton shared lock. */
class SharedLock {
  private static final Lock lock = new ReentrantLock();

  private SharedLock() {}

  static Lock getInstance() {
    return lock;
  }
}

/** A class that acquires a lock in a static initializer. */
class ClassWithLockInStaticInitializer {
  static int someField = initializeField();

  private static int initializeField() {
    Lock lock = SharedLock.getInstance();
    lock.lock();
    lock.unlock();
    return 0;
  }
}

/**
 * Example2: Deadlock with two virtual threads where one is unmounted and the other is pinned due to static class
 * initializer.
 *
 * This is same as Example1, but with the lock being acquired in a static class initializer.
 */
class Example2 extends BaseExample {

  void threadAWork() {
    Lock lock = SharedLock.getInstance();
    lock.lock();
    Threads.sleep(100L); // Sleep here is to cause virtual thread unmount the carrier thread.
    lock.unlock();
    Threads.sayHello();
  }

  void threadBWork() {
    // The locking happens in static class initializer, which is here to cause virtual thread pin the carrier thread.
    ClassWithLockInStaticInitializer a = new ClassWithLockInStaticInitializer();
    Threads.sayHello();
  }
}