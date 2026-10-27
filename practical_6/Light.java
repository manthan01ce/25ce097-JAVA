public class Light implements Switchable {

    private String name;
    private boolean on;

    public Light(String name) {
        this.name = name;
        this.on = false;
    }

    public boolean isOn() { return on; }

    @Override
    public void on() {
        on = true;
        System.out.println(name + " light turned ON");
    }

    @Override
    public void off() {
        on = false;
        System.out.println(name + " light turned OFF");
    }
}
