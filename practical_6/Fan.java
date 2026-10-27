public class Fan implements Switchable {

    private String name;
    private boolean on;

    public Fan(String name) {
        this.name = name;
        this.on = false;
    }

    public boolean isOn() { return on; }

    @Override
    public void on() {
        on = true;
        System.out.println(name + " fan turned ON");
    }

    @Override
    public void off() {
        on = false;
        System.out.println(name + " fan turned OFF");
    }
}
