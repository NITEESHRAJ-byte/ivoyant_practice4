import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class MvcDemo {

    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String submitForm(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            Model model) {

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.user", "Passwords must match");
        }

        if (result.hasErrors()) {
            return "register";
        }

        model.addAttribute("user", user);
        return "success";
    }

    public static class User {

        @NotBlank(message = "Username required")
        private String username;

        @Email(message = "Invalid email")
        @NotBlank
        private String email;

        @Min(value = 18, message = "Age must be 18+")
        private int age;

        @Size(min = 6, message = "Password must be 6+ chars")
        private String password;

        private String confirmPassword;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getConfirmPassword() { return confirmPassword; }
        public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
    }
}
