public interface Switchable {
    void on();
    void off();

    default void toggle(boolean isOn) {
        if (isOn) off();
        else on();
    }
}
