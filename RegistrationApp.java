import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class RegistrationApp {
    public record UserForm(String email, String password, int age) {
        public UserForm {
            if (email == null || email.isBlank()) {
                throw new IllegalArgumentException("Email cannot be blank");
            }
            if (password == null || password.isBlank()) {
                throw new IllegalArgumentException("Password cannot be blank");
            }
        }
    }

    public static class UserValidator {
        private final List<Predicate<UserForm>> rules = new ArrayList<>();

        public void addRule(Predicate<UserForm> rule) {
            rules.add(rule);
        }

        public boolean isValid(UserForm form) {
            return rules.stream().allMatch(rule -> rule.test(form));
        }
    }

    public static void main(String[] args) {
        UserValidator validator = new UserValidator();
        validator.addRule(form -> form.email().contains("@"));
        validator.addRule(form -> form.password().length() >= 8);
        validator.addRule(form -> form.age() >= 18);

        UserForm form = new UserForm("anna@example.com", "secure123", 20);
        System.out.println(validator.isValid(form));
    }
}