package com.mbeez.nugshare2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SetupNewUserProfileActivity extends AppCompatActivity {

    private UserProfile profile;
    private boolean newUserAlert = false;
    private TextView email;
    private TextView name;
    private TextView id;
    private TextView idToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_new_user_profile);

    }

    public void onStart() {
        super.onStart();

        Bundle receivedProfile = this.getIntent().getExtras();
        email = (TextView) findViewById(R.id.UsersEmail);
        email.setText(receivedProfile.getCharSequence("email"));

        name = (TextView) findViewById(R.id.UsersName);
        name.setText(receivedProfile.getCharSequence("name"));

        id = (TextView) findViewById(R.id.UsersId);
        id.setText(receivedProfile.getCharSequence("id"));
    }

    public UserProfile getProfile() {
        return profile;
    }
}
