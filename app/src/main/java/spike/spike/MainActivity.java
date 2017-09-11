package spike.spike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "";

    public static Map<String, User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createUsers();
    }

    public void createUsers() {
        users = new HashMap<>();
        User rob = new User("rsteilberg", "pass1");
        rob.setSecret("My credit card number is 1234567890!");
        users.put(rob.getUsername(), rob);

        User robert = new User("rcd", "pass2");
        robert.setSecret("My social security number is 000-00-0000.");
        users.put(robert.getUsername(), robert);

        User filip = new User("fjm7", "pass3");
        filip.setSecret("I am a loser...");
        users.put(filip.getUsername(), filip);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);

        EditText usernameEditText = (EditText) findViewById(R.id.username);
        EditText passwordEditText = (EditText) findViewById(R.id.password);
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        User user = users.get(username);

        if (user == null) {
            intent.putExtra(EXTRA_MESSAGE, "No user with the username " + username + " was found!");
        } else {
            intent.putExtra(EXTRA_MESSAGE, user.getSecret(password));
        }
        startActivity(intent);
    }
}

// https://developer.android.com/training/basics/firstapp/starting-activity.html#run