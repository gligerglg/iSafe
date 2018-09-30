package apps.gliger.isafe;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import net.ralphpina.permissionsmanager.PermissionsManager;
import net.ralphpina.permissionsmanager.PermissionsResult;

import apps.gliger.isafe.RoomDB.TripDB;
import rx.functions.Action1;

public class MainMenu extends AppCompatActivity {

    private ImageButton btn_navigation, btn_neabyIncident, btn_history, btn_profile, btn_settings, btn_logout;
    private TripDB tripDB;
    private ConstraintLayout layout;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu);

        initializeConponents();
        initializeMethods();
    }

    private void initializeConponents() {
        layout = findViewById(R.id.layout_home);
        btn_navigation = findViewById(R.id.btn_mnu_navigate);
        btn_neabyIncident = findViewById(R.id.btn_mnu_incidents);
        btn_history = findViewById(R.id.btn_mnu_history);
        btn_profile = findViewById(R.id.btn_mnu_profile);
        btn_settings = findViewById(R.id.btn_mnu_settings);
        btn_logout = findViewById(R.id.btn_mnu_logout);
        sharedPref = getSharedPreferences("iSafe_settings", 0);
        editor = sharedPref.edit();

        tripDB = Room.databaseBuilder(getApplicationContext(), TripDB.class, "TripDB").fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    private void initializeMethods() {
        btn_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this, Navigation.class));
            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this, SettingsActivity.class));
            }
        });

        btn_neabyIncident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this, NearbyMap.class));
            }
        });

        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tripDB.tripDao().getAllTrips().size() == 0)
                    setMessage("No Records Found!");
                else
                    startActivity(new Intent(MainMenu.this, DriverHistory.class));
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this, ProfileActivity.class));
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("staylogin",false);
                editor.commit();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
    }

    private void setMessage(String message) {
        Snackbar snackbar = Snackbar.make(layout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
