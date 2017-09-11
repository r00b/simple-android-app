package spike.spike;

/**
 * @author Robert Steilberg
 */

class User {

    private String username;
    private String password;
    private String secret;

    User(String username, String password) {
        this.username = username;
        this.password = password;
        this.secret = "I haven't set my secret yet :)";
    }

    String getUsername() {
        return username;
    }

    void setSecret(String secret) {
        this.secret = secret;
    }

    String getSecret(String givenPassword) {
        if (givenPassword.equals(this.password)) {
            return "Log in successful, now displaying secret: \n" + this.secret;
        } else {
            return "Wrong password!";
        }
    }
}
