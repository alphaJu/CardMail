package com.example.q.cardmail;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //Variables for Tabbed Activity
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    int mDefaultColor = R.color.defaultTextColor;

    //Variables for Tab3 Card
    public static final int IMAGE_GALLERY_REQUEST = 20;
    private static final String TAG = "MainActivity";
    public Uri imageUri;

    private CallbackManager callbackManager;
    LoginButton loginButton;

    public static UserPut up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FacebookSdk.sdkInitialize(this.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try{
                                    HashMap<String, Object> map = new HashMap<String, Object>();
                                    map.put("id", object.getString("id"));
                                    map.put("name", object.getString("name"));
                                    map.put("email", object.getString("email"));
                                    up = new UserPut(map);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        final ImageView minionFab = (ImageView) findViewById(R.id.minion_fab);
        final FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddMemoActivity.isUpdate = false;
                Intent goToAddMemo = new Intent(MainActivity.this, AddMemoActivity.class);
                startActivity(goToAddMemo);
            }
        });


        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        //Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
          @Override
          public void onPageSelected(int position) {
              if(position == 0) {
                  fab2.hide();
                  minionFab.setVisibility(View.GONE);

              } else if (position == 2){
                  fab2.show();
                  minionFab.setVisibility(View.VISIBLE);
              } else {
                  fab2.hide();
                  minionFab.setVisibility(View.GONE);
              }
          }
        });


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    //Add Fragments
    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Contacts(), "[  CONTACTS  ]");
        adapter.addFragment(new Tab2Images(), "[  IMAGES  ]");
        adapter.addFragment(new Tab3Bamboo(), "[  BAMBOO  ]");
        viewPager.setAdapter(adapter);
    }
}