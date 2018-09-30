package apps.gliger.isafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import retrofit2.Call;

import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.airbnb.lottie.LottieAnimationView;

import REST_Controller.LoginRequest;
import REST_Controller.LoginResponse;
import REST_Controller.RESTClient;
import REST_Controller.RESTInterface;
import REST_Controller.StaticIncidentData;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private EditText usernametxt, passwordtxt;
    private TextView signuptxt;
    private RESTInterface restInterface;
    private MaterialDialog dialog;
    private ConstraintLayout layout;
    private LottieAnimationView animationView;
    private CheckBox checkBox;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        passwordtxt = findViewById(R.id.PasswordTxt);
        usernametxt = findViewById(R.id.UsernameTxt);
        signuptxt = findViewById(R.id.txtsignup);
        layout = findViewById(R.id.layout_login);
        animationView = findViewById(R.id.lottieLogin);
        checkBox = findViewById(R.id.chk_staylogin);

        sharedPreferences = getSharedPreferences("iSafe_settings",0);
        editor = sharedPreferences.edit();

        animationView.addColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.ADD));
        animationView.playAnimation();

        restInterface = RESTClient.getInstance().create(RESTInterface.class);


        signuptxt.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        }));

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    editor.putBoolean("staylogin",true);
                else
                    editor.putBoolean("staylogin",false);
                editor.commit();
            }
        });

    }

    public void login(View view) {
        if (nonEmpty(usernametxt, passwordtxt)) {
            /*startActivity(new Intent(LoginActivity.this, MainMenu.class));*/
            setProgressDialog("Please Wait Until Verification");
            requestLogin(usernametxt.getText().toString(), passwordtxt.getText().toString());
        }

    }

    /**
     * Check non-empty fields. if a empty field found --> set an error message
     */
    public boolean nonEmpty(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (TextUtils.isEmpty(editText.getText())) {
                editText.setError("Please Input Required Fields");
                return false;
            }
        }
        return true;
    }

    /**
     * Request authentication through REST API
     */
    private void requestLogin(String username, String password) {
        Call<LoginResponse> call = restInterface.loginUser(new LoginRequest(username, password));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        LoginResponse loginResponse = response.body();
                        if (!TextUtils.isEmpty(loginResponse.getAccessToken())) {
                            MapController.saveToken(getApplicationContext(), loginResponse.getAccessToken());
                            startActivity(new Intent(getApplicationContext(), MainMenu.class));
                        } else
                            setMessage("Invalid Username or Password");

                    } else
                        setMessage("Network Error! Please try again");
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                setMessage("Network error! Please check your internet connection");
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void setProgressDialog(String message) {
        dialog = new MaterialDialog.Builder(LoginActivity.this)
                .content(message)
                .cancelable(false)
                .progress(true, 0)
                .show();
    }

    private void setMessage(String message) {
        Snackbar snackbar = Snackbar.make(layout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}
