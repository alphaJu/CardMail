package com.example.q.cardmail;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tab1Contacts extends Fragment {
    private static final String TAG = "Tab1Contacts";

    public final int EDIT_CONTACT_ACTIVITY_CODE = 1;
    private static final int RC_LOCATION_CONTACTS_PERM = 123;

    List<UserGet> ug;
    SwipeRefreshLayout swipeLayout;
    private ListView listView;
    String jsonParse;
    Retrofit builder;
    UserListViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1_contacts, container, false);
        listView = (ListView) view.findViewById(R.id.userListView);

        swipeLayout = view.findViewById(R.id.swipeContainer2);

        swipeLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );

        return view;
    }

    private class GetContact extends AsyncTask<String, String, List<UserGet>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            builder =
                    new Retrofit.Builder()
                            .baseUrl("http://52.231.66.41:8080")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
        }

        @Override
        protected List<UserGet> doInBackground(String... params) {
            jsonParse = "";
            GithubService service = builder.create(GithubService.class);
            //network - post database
            if(MainActivity.up != null) {
                Call<UserPut> putting = service.putUserData(MainActivity.up);
                putting.enqueue(new Callback<UserPut>() {
                    @Override
                    public void onResponse(Call<UserPut> call, Response<UserPut> response) {
                        if (response.isSuccessful()) {
                            Log.d("*******", "line 77: " + response.body().name);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserPut> call, Throwable t) {
                        //  Log.e("*************", "onFailure(): " + t.getMessage());
                        Log.d("*******", "line 84: " + t.getMessage());
                    }
                });
            }

            swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    //    Toast.makeText(getContext(), "REFRESH", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(Tab1Contacts.this).attach(Tab1Contacts.this).commit();
                            swipeLayout.setRefreshing(false);
                        }
                    }, 2000);
                }
            });


            //network - get database
            Call<List<UserGet>> getting = service.getUserData();
            getting.enqueue(new Callback<List<UserGet>>() {
                @Override
                public void onResponse(Call<List<UserGet>> call, Response<List<UserGet>> response) {
                    if (response.isSuccessful()) {
                        Log.d("***********", "line 111: " + response.body());
                        ug = response.body();
                    }
                }

                @Override
                public void onFailure(Call<List<UserGet>> call, Throwable t) {
                    Log.d("***********", "line 118: " + t.getMessage());
                }
            });
            return ug;
        }

        @Override
        protected void onPostExecute(List<UserGet> result) {
            super.onPostExecute(result);
            if (result != null) {
                adapter = new UserListViewAdapter(getContext(),R.layout.list_item,result);
                if (adapter!=null)
                    listView.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetContact().execute();

    }

}