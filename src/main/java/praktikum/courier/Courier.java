package praktikum.courier;

import java.util.concurrent.ThreadLocalRandom;

public class Courier {
    private final String login;
    private final String password;
    private final String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static Courier random() {
        int suffix = ThreadLocalRandom.current().nextInt(100_000, 200_000);
        return new Courier("Courier - " + suffix, "P@ssw0rd123", "Fast");
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }
}
