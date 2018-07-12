package com.example.q.cardmail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.PUT;


public class Tab3Bamboo extends Fragment {
    private static final String TAG = "Tab3Bamboo";
    public final int EDIT_MEMO_ACTIVITY_CODE = 1;
    private ListView listView;
    View view;
    MemoListViewAdapter adapter;
    SwipeRefreshLayout swipeLayout;
    String jsonParse;
    Retrofit builder;
    List<MemoGet> mg;
    public static MemoGet putMemo;
    MemoGet deleteMemo = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3_bamboo, container, false);

        listView = (ListView) view.findViewById(R.id.memoListView);
        swipeLayout = view.findViewById(R.id.swipeContainer);

        swipeLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int position, long id) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("Manage Memo").setMessage("Please choose any action");
                alertDialog.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                        ad.setTitle("PASSWORD").setMessage("비밀번호를 입력해주세요.");
                        final EditText et = new EditText(getContext());
                        et.setTransformationMethod(new PasswordTransformationMethod());
                        ad.setView(et);
                        final String[] password = {""};
                        ad.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                password[0] = et.getText().toString();
                                Log.d("password!", password[0] + "1");
                                deleteMemo = (MemoGet) adapterView.getAdapter().getItem(position);
                                deleteMemo.setPassword(password[0]);
                                Log.d("password", deleteMemo.getPassword() + "2");
                                dialogInterface.dismiss();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d("password!", password[0] + "2");
                                dialogInterface.dismiss();
                            }
                        });
                        ad.show();
                     //   Log.d("password", deleteMemo.getPassword() + "1"
                    }
                }).setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent go = new Intent(getContext(), AddMemoActivity.class);
                        AddMemoActivity.isUpdate = true;
                        putMemo = (MemoGet) adapterView.getAdapter().getItem(position);
                        getContext().startActivity(go);
                    }
                });
                alertDialog.show();
            }
        });

        return view;
    }

    public class GetMemo extends AsyncTask<String, String, List<MemoGet>> {
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
        protected List<MemoGet> doInBackground(String... params) {
            jsonParse = "";
            GithubService service = builder.create(GithubService.class);
            //network - post database
            if(AddMemoActivity.mp != null) {
                Call<MemoPut> posting = service.postUserRepositories(AddMemoActivity.mp);
                posting.enqueue(new Callback<MemoPut>() {
                    @Override
                    public void onResponse(Call<MemoPut> call, Response<MemoPut> response) {
                        if (response.isSuccessful()) {
                        }
                    }

                    @Override
                    public void onFailure(Call<MemoPut> call, Throwable t) {
                    }
                });
                AddMemoActivity.mp = null;
            }

            if(deleteMemo != null) {
                MemoPut memo;
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("Title", deleteMemo.getTitle());
                map.put("Dates", deleteMemo.getDates());
                map.put("Contents", deleteMemo.getContents());
                map.put("Password", deleteMemo.getPassword());
                memo = new MemoPut(map);
                Call<MemoPut> deleting = service.deleteUserRepositories(memo);
                deleting.enqueue(new Callback<MemoPut>() {
                    @Override
                    public void onResponse(Call<MemoPut> call, Response<MemoPut> response) {
                        if (response.isSuccessful()) {
                        }
                        else {
                        }
                    }

                    @Override
                    public void onFailure(Call<MemoPut> call, Throwable t) {
                    }
                });
                deleteMemo = null;
            }

            if(AddMemoActivity.me != null) {
                Call<MemoPut> putting = service.putUserRepositories(AddMemoActivity.me);
                putting.enqueue(new Callback<MemoPut>() {
                    @Override
                    public void onResponse(Call<MemoPut> call, Response<MemoPut> response) {
                        if (response.isSuccessful()) {
                            Log.d("update", "n");
                        }
                        else
                            Log.d("update", "d");
                    }

                    @Override
                    public void onFailure(Call<MemoPut> call, Throwable t) {
                        Log.d("update", "fail");
                    }
                });
                AddMemoActivity.me = null;
            }

            //network - get database
            Call<List<MemoGet>> getting = service.getUserRepositories();
            getting.enqueue(new Callback<List<MemoGet>>() {
                @Override
                public void onResponse(Call<List<MemoGet>> call, Response<List<MemoGet>> response) {
                    if (response.isSuccessful()) {
                        mg = response.body();
                    }
                }

                @Override
                public void onFailure(Call<List<MemoGet>> call, Throwable t) {
                }
            });



            return mg;
        }

        @Override
        protected void onPostExecute(List<MemoGet> result) {
            super.onPostExecute(result);
            if (result != null) {
                adapter = new MemoListViewAdapter(getContext(),R.layout.list_memo,result);
                if (adapter!=null)
                    listView.setAdapter(adapter);
            }
            swipeLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh() {
                    //    Toast.makeText(getContext(), "REFRESH", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(Tab3Bamboo.this).attach(Tab3Bamboo.this).commitAllowingStateLoss();
                            swipeLayout.setRefreshing(false);
                        }
                    }, 5000);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetMemo().execute();
    }


}
