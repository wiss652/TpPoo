public class Zone implements Suspendable {
    private String name;
    private boolean active = true;

    public Zone(String name) { this.name = name; }
    public String getName() { return name; }
    @Override public void suspend() { active = false; }
    @Override public void reactivate() { active = true; }
    @Override public boolean isActive() { return active; }
}