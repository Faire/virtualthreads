package com.faire.example.virtualthreads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Example1: Deadlock with two virtual threads where one is unmounted and the other is pinned due to a synchronized block.
 *
 * The example consists of two virtual threads, threadA and threadB, being executing by a single carrier thread.
 * Both threads try to acquire the same lock.
 * Thread A will try first, succeed, and then unmount the carrier thread while holding the lock.
 * Thread B will then run and try to acquire the lock. Because B is executing inside a synchronized block,
 * on failing to acquire the lock it will be pinned to the carrier thread.
 * This is now a deadlock, since threadA, which holds the lock, cannot run to release the lock.
 */
class Example1 extends BaseExample {
  private static final Lock lock = new ReentrantLock();

  void threadAWork() {
    lock.lock();
    Threads.sleep(100L); // Sleep here is to cause virtual thread unmount the carrier thread.
    lock.unlock();
    Threads.sayHello();
  }

  synchronized void threadBWork() { // Synchronized here is to cause virtual thread pinning.
    lock.lock();
    lock.unlock();
    Threads.sayHello();
  }
}