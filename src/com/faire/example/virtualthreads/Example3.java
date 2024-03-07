package com.faire.example.virtualthreads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Example3: Deadlock with two virtual threads where one is pinned due to a synchronized block.
 *
 * Unlike Example1 and Example2, where the deadlock is caused by a virtual thread being unmounted, and lock never being
 * released, this example demonstrates a deadlock where NO lock is being held.
 *
 * In this example, the lock is first acquired by the main (platform) thread. Before the lock is released, virtual
 * threads threadA and threadB try to acquire the lock. ThreadA tries first, fails, and is unmounted. ThreadB then tries,
 * fails, and is pinned (because it is executing inside a synchronized block). Now main thread releases the lock,
 * however, threadA and threadB do not make any progress. This is because the lock is released in a fair manner (even if
 * we use a non-fair lock), where threadA being the first to try to acquire the lock, will be the thread to be unblocked
 * (see https://github.com/JetBrains/jdk8u_jdk/blob/master/src/share/classes/java/util/concurrent/locks/AbstractQueuedSynchronizer.java#L1264).
 * However, since threadA is unmounted, it cannot run to acquire the lock. This is now a deadlock.
 */
class Example3 extends BaseExample {
  private static final Lock lock = new ReentrantLock();

  void threadAWork() {
    lock.lock();
    lock.unlock();
    Threads.sayHello();
  }

  synchronized void threadBWork() {  // Synchronized here is to cause virtual thread pinning.
    lock.lock();
    lock.unlock();
    Threads.sayHello();
  }

  void mainThreadWork() {
    lock.lock();
    Threads.sleep(100L); // Sleep here is to have threadA and threadB both queue up waiting for the lock.
    lock.unlock();
    Threads.sayHello();
  }
}