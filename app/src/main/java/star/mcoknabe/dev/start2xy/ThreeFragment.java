package star.mcoknabe.dev.start2xy;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import star.mcoknabe.dev.start2xy.model.StarContract;
import star.mcoknabe.dev.start2xy.model.Stop;
import star.mcoknabe.dev.start2xy.model.StopTime;
import star.mcoknabe.dev.start2xy.model.Trip;

import static star.mcoknabe.dev.start2xy.TwoFragment.agrsone;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ThreeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ThreeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThreeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM1 = "param1";
    public static final String ARG_PARAM2 = "param2";
    public static final String ARG_PARAM3 = "param3";
    public static final String ARG_PARAM4 = "param4";
    public static final String ARG_PARAM5 = "param5";
    public static final String ARG_PARAM6 = "param6";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;
    private String mParam6;

    TextView ligne ;
    TextView dateHeur ;
    TextView arret ;
    ListView listePassage ;
    private MainActivity mListener;

    List<StopTime> stopTimes = new ArrayList<>();

    public ThreeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThreeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThreeFragment newInstance(String param1, String param2) {
        ThreeFragment fragment = new ThreeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static ThreeFragment newInstance() {
        ThreeFragment fragment = new ThreeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
            mParam5 = getArguments().getString(ARG_PARAM5);
            mParam6 = getArguments().getString(ARG_PARAM6);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_three,container, false);

        ligne = view.findViewById(R.id.textViewLigne);
        dateHeur = view.findViewById(R.id.textViewDateheur3);
        arret = view.findViewById(R.id.textViewDestination3);
        listePassage = view.findViewById(R.id.listHeurPassageArret);

        ligne.setText(mParam3);
        dateHeur.setText(mParam1+" / "+mParam2);
        arret.setText(mParam4);

        ArrayList<String> values = new ArrayList<>();
        for (StopTime timesp: gethoraire(""+mParam6,""+mParam5,""+MainActivity.dateFormat(mParam1),""+MainActivity.HeurFormat(mParam2)))
        {
            values.add(timesp.getArrivalTime());
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(),R.layout.arretlayout, values);

        listePassage.setAdapter(adapter);

        listePassage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // - trip id - heur
                agrsone.putString(ThreeFragment.ARG_PARAM1, ""+mParam1);
                agrsone.putString(ThreeFragment.ARG_PARAM2, ""+mParam2);
                agrsone.putString(ThreeFragment.ARG_PARAM3, ""+mParam3);
                agrsone.putString(ThreeFragment.ARG_PARAM4, ""+stopTimes.get(i).getTripId());
                agrsone.putString(ThreeFragment.ARG_PARAM5, ""+stopTimes.get(i).getArrivalTime());
                agrsone.putString(ThreeFragment.ARG_PARAM6, ""+mParam4);

                Log.e("XXXX"," -> " + agrsone.toString())  ;
                mListener.switchFrag(agrsone,4);


                mListener = null;
                mParam1 = null;
                mParam3 = null;
                mParam2 = null;
                mParam4 = null;
                mParam5 = null;
                mParam6 = null;
                stopTimes.clear();

            }
        });



        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
           // mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mListener = (MainActivity) context;
        }/* else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
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

    public List<StopTime> gethoraire(String arretId, String busId , String date, String heure){


        String[] selargs = {arretId, busId, date,heure};
        //String[] selargs = {"1258", "0005", "20180105","21:59:00"};
        Log.d("STARXTEST", "cursocount ..." + selargs[0] +" - "+ selargs[1] +" - "+ selargs[2] +" - "+ selargs[3] +" - " );
        Cursor cursor = mListener.getContentResolver().query(Uri.withAppendedPath(StarContract.AUTHORITY_URI, StarContract.StopTimes.CONTENT_PATH),
                null,
                null,
                selargs,
                StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME);
        Log.d("STARXTEST", "cursocount ..." + cursor.getCount());
        if (cursor.moveToFirst()){
            do {
                StopTime item = new StopTime(
                        cursor.getInt(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.TRIP_ID)),
                        cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)),
                        cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME)),
                        cursor.getInt(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.STOP_ID)),
                        cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE))
                );
                Log.d("STARXTEST", "from provider ..." + item);
                stopTimes.add(item);
            } while (cursor.moveToNext());
        }
        return stopTimes;
    }
}
