import java.util.regex.Pattern;

/** Input validators using compiled regex patterns. */
public class Validator {

    private static final Pattern MOBILE = Pattern.compile("[6-9][0-9]{9}");
    private static final Pattern EMAIL  = Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PAN    = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]");
    private static final Pattern IFSC   = Pattern.compile("[A-Z]{4}0[A-Z0-9]{6}");

    public static boolean isValidMobile(String v) { return v != null && MOBILE.matcher(v).matches(); }
    public static boolean isValidEmail(String v)  { return v != null && EMAIL.matcher(v).matches(); }
    public static boolean isValidPan(String v)    { return v != null && PAN.matcher(v).matches(); }
    public static boolean isValidIfsc(String v)   { return v != null && IFSC.matcher(v).matches(); }
    public static boolean isValidAmount(long amt) { return amt > 0; }
}
