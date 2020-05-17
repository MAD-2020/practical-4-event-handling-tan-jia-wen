package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */

    private Button buttonLeft;
    private Button buttonMiddle;
    private Button buttonRight;
    private TextView score;
    int points = 0;
    private static final String TAG = "Whack-A-Mole 1.0!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLeft = (Button) findViewById(R.id.buttonLeft);
        buttonMiddle = (Button) findViewById(R.id.buttonMiddle);
        buttonRight = (Button) findViewById(R.id.buttonRight);
        score = (TextView) findViewById(R.id.score);

        score.setText("Score: " + points);

        Log.v(TAG, "Finished Pre-Initialisation!");
    }

    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();
        Log.v(TAG, "Starting GUI!");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    public void OnClickButton(View v)
    {
        Button button = (Button) v;
        if (check(button) == true){
            points++;
            score.setText("Score: " + points);
            doCheck(points);
            Log.v(TAG, "Hit, score added!");
        }
        else{
            if (points > 0) {
                points--;
                score.setText("Score: " + points);
                doCheck(points);
                Log.v(TAG, "Missed, score added!");
            } else {
                points = 0;
                score.setText("Score: " + points);
                Log.v(TAG, "Missed, score added!");
            }
        }
        setNewMole();
    }
    public void text(Button b)
    {
        if (b == buttonLeft) {
            Log.v(TAG, "Button Left clicked!");
        }
        if (b == buttonMiddle){
            Log.v(TAG, "Button Middle clicked!");
        }
        if (b == buttonRight){
            Log.v(TAG, "Button Right clicked!");
        }
    }

    public boolean check(Button b)
    {
        if (b.getText() == "*"){
            return true;
        }
        else {
            return false;
        }
    }

    private void doCheck(int points){
        /* Checks for hit or miss and if user qualify for advanced page.
            Triggers nextLevelQuery().*/
        if (points % 10 == 0){
            nextLevelQuery();
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void nextLevelQuery(){
        /*
        Builds dialog box here.
        Log.v(TAG, "User accepts!");
        Log.v(TAG, "User decline!");
        Log.v(TAG, "Advance option given to user!");
        belongs here*/

        Log.v(TAG, "Advance option available!");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning! Insane Whack-A-Mole coming up!");
        builder.setMessage("Would you like to proceed to advanced mode?");
        builder.setCancelable(false);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User accepts!");
                nextLevel();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User decline!");
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void nextLevel(){
        /* Launch advanced page */
        Intent proceed = new Intent(MainActivity.this, Main2Activity.class);
        proceed.putExtra("Score", points);
        startActivity(proceed);
    }

    private void setNewMole() {
        Button[] buttons = {buttonLeft, buttonMiddle, buttonRight};
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);
        Button s = buttons[randomLocation];
        for (Button b : buttons){
            if (b == s){
                b.setText("*");
            }
            else {
                b.setText("O");
            }
        }
    }
}