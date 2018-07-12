package com.example.q.cardmail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AddMemoActivity extends AppCompatActivity{

    EditText edtTitle, edtContents, edtPassword;
    Button btnAddMemo;
    View view;
    boolean isButtonClicked = false;
    String mId, mTitle, mContetns;
    View snackView;
    InputMethodManager inm;
    static MemoPut mp = null;
    static MemoPut me = null;
    public static boolean isUpdate;
    String memoTitle, memoDate, memoContents, memoPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);
        view = findViewById(R.id.rootView);
        edtTitle = (EditText) findViewById(R.id.Title);
        edtContents = (EditText) findViewById(R.id.Contents);
        edtPassword = (EditText) findViewById(R.id.Password);
        btnAddMemo = (Button) findViewById(R.id.btnAddMemo);
        inm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if(isUpdate) {
            memoTitle = Tab3Bamboo.putMemo.Title;
            memoDate = Tab3Bamboo.putMemo.Dates;
            memoContents = Tab3Bamboo.putMemo.Contents;
            memoPassword = Tab3Bamboo.putMemo.Password;
            btnAddMemo.setText("Edit Memo");
            edtTitle.setText(memoTitle);
            edtContents.setText(memoContents);
            isUpdate = false;
        }
        else {
            btnAddMemo.setText("Add Memo");
        }

        btnAddMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnAddMemo.getText().toString().matches("Add Memo")) {
                    if(edtTitle.getText().toString().trim().isEmpty()) {
                        Snackbar.make(v, "You must provide title.", Snackbar.LENGTH_LONG).show();
                    }
                    else if (edtContents.getText().toString().trim().isEmpty()) {
                        Snackbar.make(v, "You must provide contents.", Snackbar.LENGTH_LONG).show();
                    }
                    else {
                        Snackbar.make(v, "Successfully added.", Snackbar.LENGTH_LONG).show();
                        isButtonClicked = true;
                        snackView = v;
                        inm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        addMemo();
                    }
                }
                else if (btnAddMemo.getText().toString().matches("Edit Memo")) {
                    if(edtTitle.getText().toString().trim().isEmpty()) {
                        Snackbar.make(v, "You must provide title.", Snackbar.LENGTH_LONG).show();
                    }
                    else if (edtContents.getText().toString().trim().isEmpty()) {
                        Snackbar.make(v, "You must provide contents.", Snackbar.LENGTH_LONG).show();
                    }
                    else {
                        Snackbar.make(v, "Successfully edited.", Snackbar.LENGTH_LONG).show();
                        isButtonClicked = true;
                        snackView = v;
                        inm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        editMemo();
                    }
                }
            }
        });
    }

    public void addMemo() {
//        data = "{\"Title\" : \"" + edtTitle.getText().toString() + "\" , \"Contents\" : \"" + edtContents.getText().toString() + "\"}";
        //       insertOperation.add(data);
        Date d = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("Title", edtTitle.getText().toString());
        map.put("Dates", date.format(d));
        map.put("Contents", edtContents.getText().toString());
        map.put("Password", edtPassword.getText().toString());
        mp = new MemoPut(map);
    }

    public void editMemo() {
        Date d = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("Title", edtTitle.getText().toString());
        map.put("Dates",date.format(d));
        map.put("Contents", edtContents.getText().toString());
        map.put("Password", edtPassword.getText().toString());
        me = new MemoPut(map);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
