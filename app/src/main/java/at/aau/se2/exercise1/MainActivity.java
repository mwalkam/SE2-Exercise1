package at.aau.se2.exercise1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final AAUApiConnector aauApiConnector = new AAUApiConnector();

    private Handler responseHandler;

    private Button btnSend;
    private TextView textViewResult;
    private EditText editTextMatNr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        responseHandler = new Handler(getMainLooper());

        btnSend = findViewById(R.id.btnSend);
        textViewResult = findViewById(R.id.textViewResult);
        editTextMatNr = findViewById(R.id.editTextMatNr);

        btnSend.setOnClickListener((view) -> {
            setInputsEnabled(false);
            textViewResult.setText(R.string.loading);

            aauApiConnector.getStudyTime(
                    editTextMatNr.getText().toString(),
                    responseHandler,
                    (resultStr) -> {
                        setInputsEnabled(true);
                        textViewResult.setText(resultStr);
                    },
                    (error) -> {
                        setInputsEnabled(true);
                        textViewResult.setText(error.getLocalizedMessage());
                    }
            );
        });
    }

    private void setInputsEnabled(boolean value) {
        this.btnSend.setEnabled(value);
        this.editTextMatNr.setEnabled(value);
    }
}