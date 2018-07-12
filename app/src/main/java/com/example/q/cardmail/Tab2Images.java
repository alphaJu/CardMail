package com.example.q.cardmail;

import android.content.pm.PackageManager;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.provider.MediaStore.MediaColumns;
import android.widget.BaseAdapter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.provider.MediaStore;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

public class Tab2Images extends Fragment {
    private static final String TAG = "Tab2Images";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    // public static Bitmap[] thumbnails;

    public Tab2Images() {
        // Required empty public constructor
    }


    private static final int REQUEST_TAKE_ALBUM = 3333;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab2.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab2Images newInstance(String param1, String param2) {
        Tab2Images fragment = new Tab2Images();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /* @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
         // Inflate the layout for this fragment
         return inflater.inflate(R.layout.fragment_tab2, container, false);
     }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab2_images, container, false);

        Button btn = (Button) view.findViewById(R.id.galleryUpdate);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                GridView gridView = getView().findViewById(R.id.gridView);
                gridUpdate( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, gridView);
            }
        });

        Button downloadButton = (Button) view.findViewById(R.id.galleryDownload);
        downloadButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                GridView gridView = getView().findViewById(R.id.gridView);
                gridDownload( gridView);
            }
        });

        GridView gridView = view.findViewById(R.id.gridView);
        //gridUpdate( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Intent i = new Intent(getContext(),Imageupg.class);
//                i.putExtra("index", position);
//                startActivity(i);
            }
        });

        return view;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public ArrayList<Bitmap> getThumbList( Uri uri, String[] projection){
        ArrayList<Bitmap> thumbList = new ArrayList<>();
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if(cursor.moveToFirst()){
            do{

                Bitmap bmp = BitmapFactory.decodeFile(cursor.getString(0));
                Bitmap resized = Bitmap.createScaledBitmap(bmp, 360, 360, true);
                thumbList.add(resized);
            }while(cursor.moveToNext());
        }
        return thumbList;
    }

    public void gridUpdate(Uri uri, GridView view){
        ArrayList<Bitmap> thumbList = getThumbList(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] {MediaStore.Images.Media.DATA});

        GridAdapter gridadapter = new GridAdapter(getContext(), thumbList);
        view.setAdapter(gridadapter);
        JSONArray jsonList = new JSONArray();

        for(int i=0; i<thumbList.size(); i++){
            try {
                JSONObject json = new JSONObject();


                Bitmap bmp = thumbList.get(i);
                //Bitmap resized = Bitmap.createScaledBitmap(bmp, 360, 360, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG,100, baos);
                byte[] b = baos.toByteArray();
                String encodeImg = Base64.encodeToString(b,Base64.DEFAULT);
                json.accumulate("img", encodeImg);

                jsonList.put(json);

                Log.d("Here", "jsonList.put done");
            }catch (Exception e){
                Log.d("Here", "put exception");
            }

        }
        String message;
        try{ message = jsonList.getString(3); }catch (Exception e){ message =  "empty";}
        Log.d("jsonarray(3)", message);
        NetworkTask postDBimg = new NetworkTask("api/images","post", null, jsonList);
        postDBimg.execute();
    }
    public void gridDownload(GridView view) {
        NetworkTask getImages = new NetworkTask("api/getAllImages", "get", null, null);
        getImages.execute();
        Log.d("Here", "gridDownload");
        try {
            String s = getImages.get();

            JSONArray imageList = new JSONArray(s);
            ArrayList<Bitmap> thumbList = new ArrayList<>();
            Log.d("why",""+imageList.length());
            for (int i = 0; i < imageList.length(); i++) {

                JSONObject json = (JSONObject) imageList.get(i);
                String imageString = json.getString("img");
                Log.d("getImages", imageString.substring(0,15));
                byte [] encodeByte=Base64.decode(imageString,Base64.DEFAULT);

                InputStream inputStream  = new ByteArrayInputStream(encodeByte);
                Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
//                Bitmap resized = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
                thumbList.add(bitmap);
            }

            GridAdapter gridadapter = new GridAdapter(getContext(), thumbList);
            Log.d("OOO" ,""+thumbList.size());
            view.setAdapter(gridadapter);
        } catch (Exception e) {
            Log.d("oh", e.getMessage());
        }
    }

}