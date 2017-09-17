package spike.spike;

/**
 * @author Robert Steilberg
 */

class User {

    private String username;
    private String password;

    @Deprecated
    private String secret;

    User(String username, String password) {
        this.username = username;
        this.password = password;
        this.secret = "I haven't set my secret yet :)";
    }

    String getUsername() {
        return username;
    }

    boolean authenticate(String candidatePassword) {
        return candidatePassword.equals(password);
    }

    @Deprecated
    void setSecret(String secret) {
        this.secret = secret;
    }

    @Deprecated
    String getSecret(String givenPassword) {
        if (givenPassword.equals(this.password)) {
            return "Log in successful, now displaying secret: \n" + this.secret;
        } else {
            return "Wrong password!";
        }
    }
}
