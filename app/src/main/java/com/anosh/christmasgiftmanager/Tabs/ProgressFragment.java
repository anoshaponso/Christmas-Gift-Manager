package com.anosh.christmasgiftmanager.Tabs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anosh.christmasgiftmanager.Classes.Gift_Class;
import com.anosh.christmasgiftmanager.Classes.Group_Class;
import com.anosh.christmasgiftmanager.Classes.Person_Class;
import com.anosh.christmasgiftmanager.MainActivity;
import com.anosh.christmasgiftmanager.Progress_Tab.Progress_view;
import com.anosh.christmasgiftmanager.R;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProgressFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProgressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgressFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int[] COLORS = new int[]{Color.GREEN, Color.BLUE, Color.MAGENTA, Color.RED, Color.CYAN};
    public ArrayList<Person_Class> person;
    public ArrayList<Group_Class> group;
    LinearLayout layout;
    ArrayList<Gift_Class> gift;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CategorySeries mSeries = new CategorySeries("");
    private DefaultRenderer mRenderer = new DefaultRenderer();
    private GraphicalView mChartView;

    private OnFragmentInteractionListener mListener;

    public ProgressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProgressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgressFragment newInstance(String param1, String param2) {
        ProgressFragment fragment = new ProgressFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gift = MainActivity.gift;
        group = MainActivity.group;
        person = MainActivity.person;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRenderer.setStartAngle(180);
        mRenderer.setPanEnabled(false);
        mSeries.clear();
        mRenderer.removeAllRenderers();

        int value[] = getdata();
        String name[] = {"Not purchased (" + value[0] + ")", "Purchased (" + value[1] + ")", "Shipped (" + value[2] + ")", "Ordered (" + value[3] + ")", "Wrapped (" + value[4] + ")"};
        for (int i = 0; i < 5; i++) {

            mSeries.add(name[i], value[i]);
            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);

            mRenderer.addSeriesRenderer(renderer);
            mRenderer.setShowLabels(false);
            mRenderer.setLegendTextSize(25);
        }

        View rootView = inflater.inflate(R.layout.progress, container, false);
        layout = (LinearLayout) rootView.findViewById(R.id.chart);

        TextView groups = (TextView) rootView.findViewById(R.id.noOfGroups);
        TextView people1 = (TextView) rootView.findViewById(R.id.noOfPeople);
        TextView spent = (TextView) rootView.findViewById(R.id.spentAll);
        TextView groupSpent = (TextView) rootView.findViewById(R.id.groupBudget);
        TextView peopleBudget = (TextView) rootView.findViewById(R.id.peopleBudget);
        TextView noOfGifts = (TextView) rootView.findViewById(R.id.noOfGifts);
        set(groups, people1, spent, groupSpent, peopleBudget, noOfGifts);


        return rootView;
    }

    private void set(TextView groups, TextView people1, TextView spent, TextView groupSpent, TextView peopleBudget, TextView noOfGifts) {
        double spentAmount = 0.0;
        double groupBudget = 0.0;
        double peopleBudgetAmount = 0.0;
        for (int i = 0; i < group.size(); i++) {
            groupBudget = groupBudget + (group.get(i).getBudget());
        }
        groupSpent.setText(String.valueOf(groupBudget));
        for (int i = 0; i < person.size(); i++) {
            peopleBudgetAmount = peopleBudgetAmount + (person.get(i).getBudget());
        }
        peopleBudget.setText(String.valueOf(peopleBudgetAmount));
        for (int i = 0; i < gift.size(); i++) {
            spentAmount = spentAmount + (gift.get(i).getPrice());
        }
        spent.setText(String.valueOf(spentAmount));
        groups.setText(String.valueOf(group.size()));
        people1.setText(String.valueOf(person.size()));
        noOfGifts.setText(String.valueOf(gift.size()));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mChartView = ChartFactory.getPieChartView(getActivity(), mSeries, mRenderer);
        mRenderer.setClickEnabled(true);
        mChartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
                if (seriesSelection == null) {
//                        Toast.makeText(getActivity(), "No chart element selected", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < mSeries.getItemCount(); i++) {
                        mRenderer.getSeriesRendererAt(i).setHighlighted(i == seriesSelection.getPointIndex());

                    }
                    if (seriesSelection.getPointIndex() == 0) {
                        Intent in = new Intent(getActivity(), Progress_view.class);
                        in.putExtra("status", "Not purchased");
                        startActivity(in);

                    } else if (seriesSelection.getPointIndex() == 1) {
                        Intent in = new Intent(getActivity(), Progress_view.class);
                        in.putExtra("status", "Purchased");
                        startActivity(in);

                    } else if (seriesSelection.getPointIndex() == 2) {
                        Intent in = new Intent(getActivity(), Progress_view.class);
                        in.putExtra("status", "Shipped");
                        startActivity(in);

                    } else if (seriesSelection.getPointIndex() == 3) {
                        Intent in = new Intent(getActivity(), Progress_view.class);
                        in.putExtra("status", "Ordered");
                        startActivity(in);

                    } else if (seriesSelection.getPointIndex() == 4) {
                        Intent in = new Intent(getActivity(), Progress_view.class);
                        in.putExtra("status", "Wrapped");
                        startActivity(in);

                    }
                    mChartView.repaint();

                }
            }
        });
        layout.addView(mChartView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public int[] getdata() {
        String status;
        int not = 0, purchased = 0, shipped = 0, ordered = 0, wrapped = 0;
        for (int i = 0; i < gift.size(); i++) {
            status = gift.get(i).getStatus();
            if (status.equals("Not purchased")) {
                not += 1;
            } else if (status.equals("Purchased")) {
                purchased += 1;
            } else if (status.equals("Shipped")) {
                shipped += 1;
            } else if (status.equals("Ordered")) {
                ordered += 1;
            } else if (status.equals("Wrapped")) {
                wrapped += 1;
            }
        }
        return new int[]{not, purchased, shipped, ordered, wrapped};
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
