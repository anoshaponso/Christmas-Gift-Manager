package com.anosh.christmasgiftmanager.Tabs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.anosh.christmasgiftmanager.Classes.Group_Class;
import com.anosh.christmasgiftmanager.Classes.Person_Class;
import com.anosh.christmasgiftmanager.Group_Update;
import com.anosh.christmasgiftmanager.MainActivity;
import com.anosh.christmasgiftmanager.R;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PeopleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PeopleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PeopleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Spinner s;
    TextView spentPeople, budgetPeople, remainPeople, daysRemain;
    ArrayList<Group_Class> group;
    ArrayList<Person_Class> person;
    ArrayList<Integer> personID;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public PeopleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PeopleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PeopleFragment newInstance() {
        PeopleFragment fragment = new PeopleFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_people, container, false);
        daysRemain = (TextView) rootView.findViewById(R.id.daysRemain);
        s = (Spinner) rootView.findViewById(R.id.groupSpinner);
        loadSpinner1();
        person = MainActivity.person;
        group = MainActivity.group;
        spentPeople = (TextView) rootView.findViewById(R.id.spentpeople);
        budgetPeople = (TextView) rootView.findViewById(R.id.budgetpeople);
        remainPeople = (TextView) rootView.findViewById(R.id.remainpeople);
        daysRemain.setText(getDateCount() + " Shopping Days Left ");
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSpinner1();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public String getDateCount() {
        Calendar thatDay = Calendar.getInstance();
        thatDay.set(Calendar.DAY_OF_MONTH, 25);
        thatDay.set(Calendar.MONTH, 11); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));

        Calendar today = Calendar.getInstance();

        long diff = thatDay.getTimeInMillis() - today.getTimeInMillis();
        return String.valueOf((diff) / (24 * 60 * 60 * 1000));
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

    public void loadSpinner1() {
        String arr[] = new String[MainActivity.group.size() + 1];
        arr[0] = "All";
        for (int i = 1; i <= MainActivity.group.size(); i++) {
            arr[i] = MainActivity.group.get(i - 1).getName();
        }

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, arr);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                RelativeLayout r = (RelativeLayout) getActivity().findViewById(R.id.relativeLayout);
                if (position == 0) {
                    r.setEnabled(false);
                    r.requestDisallowInterceptTouchEvent(false);
                    setData();
                    new PepoleLoader(getActivity(), group);
                } else {
                    r.setEnabled(true);
                    r.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Spinner s = (Spinner) getActivity().findViewById(R.id.groupSpinner);
                            Intent in = new Intent(getActivity(), Group_Update.class);
                            in.putExtra("name", s.getSelectedItem().toString());

                            startActivity(in);
                        }
                    });

                    setData(group.get(position - 1).getId());
                    new PepoleLoader(getActivity(), getGroup_Data1(group.get(position - 1).getId()), group);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                setData();
                new PepoleLoader(getActivity(), group);

            }

        });
    }

    public void setData() {
        float budget = 0, onList = 0;
        for (int i = 0; i < MainActivity.group.size(); i++) {
            budget = budget + group.get(i).getBudget();
        }
        for (int j = 0; j < person.size(); j++) {

            onList = onList + person.get(j).getBudget();
        }
        budgetPeople.setText(String.valueOf(budget));
        spentPeople.setText(String.valueOf(onList));
        remainPeople.setText(String.valueOf(budget - onList));
    }

    public void setData(int id) {
        double budget = 0.0, onList = 0.0;
        for (int i = 0; i < group.size(); i++) {
            if (id == (group.get(i).getId())) {
                budget = (group.get(i).getBudget());
            }
        }
        for (int j = 0; j < person.size(); j++) {
            if (id == (person.get(j).getGroup())) {
                onList = onList + (person.get(j).getBudget());
            }
        }
        budgetPeople.setText(String.valueOf(budget));
        spentPeople.setText(String.valueOf(onList));
        remainPeople.setText(String.valueOf(budget - onList));
    }

    public ArrayList<Integer> getGroup_Data1(int group1) {
        personID = new ArrayList<Integer>();
        for (int j = 0; j < person.size(); j++) {
            if (group1 == (person.get(j).getGroup())) {
                personID.add((person.get(j).getId()));
            }
        }
        return personID;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
