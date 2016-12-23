package com.example.student.dicegame;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
   private int ComputerScore =0;
    private int UserScore =0;
    private int UserTurn =0;
    private int ComputerTurnScore=0;
    private  Button roll,hold,reset;
    private TextView  userScore,computerScore,message,roundSocre;
    private ImageView dice;
    int[] images = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        roll = (Button) findViewById(R.id.button);
        hold=(Button) findViewById(R.id.button2);
        reset=(Button)findViewById(R.id.button3);
        userScore= (TextView) findViewById(R.id.textView2);
        computerScore=(TextView) findViewById(R.id.textView3);
         message=(TextView) findViewById(R.id.textView5);
        dice = (ImageView) findViewById(R.id.imageView);
        roundSocre=(TextView)findViewById(R.id.textView6);
    }

    public void ROLL(View view) {

        RollDice();
    }

    public void RollDice() {
        Random r = new Random();
        int score = r.nextInt(6) + 1;
        dice.setImageResource(images[score - 1]);
        if (score == 1) {
                roundSocre.setText("Your this round Score=0");
                UserTurn = 0;
                Toast to = Toast.makeText(this, "You Lost Your Turn Now Computer Turn", Toast.LENGTH_SHORT);
                to.show();
                message.setText("Computer Turn");
            new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ComputerTurn();
                        }
                    });
                }
            }.start();

            }
            else {
                UserTurn = UserTurn + score;
            roundSocre.setText("Your this round Score="+Integer.toString(UserTurn));
            }
        }




    public void HOLD(View view) {
        HoldMethod();

    }

    public void HoldMethod() {
        UserScore=UserScore+UserTurn;
        userScore.setText(Integer.toString(UserScore));
        UserTurn=0;
        if(UserScore>=100) {
            showDialog("User Win !!!");
            Reset();
        }
        else
        { message.setText("Computer Turn");

        ComputerTurn();
        }
}


    public void RESET(View view) {
      Reset();
    }
    public void Reset()
    {
        ComputerScore=0;
        UserScore=0;
        userScore.setText(Integer.toString(UserScore));
        computerScore.setText(Integer.toString(UserScore));
        roundSocre.setText("");
        roll.setEnabled(true);
        hold.setEnabled(true);

    }

    public void ComputerTurn()
    {
        roll.setEnabled(false);
        hold.setEnabled(false);
        Random r= new Random();
        int score= r.nextInt(6)+1;
        dice.setImageResource(images[score-1]);

        ComputerTurnScore=ComputerTurnScore+score;

        if(score==1)
        { roundSocre.setText("Computer this round Score="+0);
            ComputerTurnScore=0;
            ComputerScore=ComputerScore+ComputerTurnScore;
            computerScore.setText(Integer.toString(ComputerScore));
            Toast to = Toast.makeText(this, "Computer Lost Turn", Toast.LENGTH_SHORT);
            to.show();
            message.setText("User Turn");
            roll.setEnabled(true);
            hold.setEnabled(true);

        }
        else

        {
            roundSocre.setText("Computer this round Score="+Integer.toString(ComputerTurnScore));
            if (ComputerTurnScore < 20) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ComputerTurn();
                            }
                        });
                    }
                }.start();
            }
            else
            {

                ComputerScore=ComputerScore+ComputerTurnScore;
                computerScore.setText(Integer.toString(ComputerScore));

                    if(ComputerScore>=100)
                    {
                        showDialog(" Computer Win !!!");
                        Reset();

                    }

                else
                {
                    message.setText("User Turn");
                    roll.setEnabled(true);
                    hold.setEnabled(true);
                    ComputerTurnScore = 0;

                }

            }
        }


    }
    public void showDialog(String mess)
    {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Game Finish");
        dialog.setMessage(mess);
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}


