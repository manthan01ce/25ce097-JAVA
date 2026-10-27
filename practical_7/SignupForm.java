public class SignupForm {

    @NotBlank
    @MaxLength(50)
    private String username;

    @NotBlank
    @MaxLength(100)
    private String email;

    @MaxLength(20)
    private String phone;

    public SignupForm(String username, String email, String phone) {
        this.username = username;
        this.email    = email;
        this.phone    = phone;
    }
}
