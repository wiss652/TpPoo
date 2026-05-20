public interface Suspendable {
    void suspend();
    void reactivate();
    boolean isActive();
}
