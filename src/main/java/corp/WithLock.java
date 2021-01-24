package corp;

import java.util.function.Supplier;

public class WithLock {
    class Lock {
        void lock() {}
        void unlock() {}
    }

    public <Y> Y withLock(Lock l, Supplier<Y> action) {
        l.lock();
        try {
            return action.get();
        } finally {
            l.unlock();
        }
    }

}
