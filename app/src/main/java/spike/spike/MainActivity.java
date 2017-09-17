package spike.spike;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

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
        Intent intent = new Intent(this, DisplayActorsActivity.class);

        EditText usernameEditText = (EditText) findViewById(R.id.username);
        EditText passwordEditText = (EditText) findViewById(R.id.password);
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        User user = users.get(username);

        if (user == null) {
            // none found
            displayBadLoginAlert();
        } else if (user.authenticate(password)) {
            // fetch from database and display everything
            startActivity(intent);
        } else {
            displayBadLoginAlert();
        }
    }

    public void displayBadLoginAlert() {
        AlertDialog.Builder userNotFoundDialog = new AlertDialog.Builder(this);
        userNotFoundDialog.setMessage("Incorrect username and password combination")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = userNotFoundDialog.create();
        alert.show();
    }
}

// https://developer.android.com/training/basics/firstapp/starting-activity.html#run
// http://www.androidhive.info/2012/05/how-to-connect-android-with-php-mysql/