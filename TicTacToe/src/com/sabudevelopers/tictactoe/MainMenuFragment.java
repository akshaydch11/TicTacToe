package com.sabudevelopers.tictactoe;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A placeholder fragment containing a main menu view.
 */
public class MainMenuFragment extends Fragment {


    Button onePlayer, twoPlayer;

    public MainMenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu_fragment, container, false);

        if (rootView != null) {
            onePlayer = (Button) rootView.findViewById(R.id.one_player);
            twoPlayer = (Button) rootView.findViewById(R.id.two_player);
        }
        onePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(v);
            }
        });

        twoPlayer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onButtonClick(view);
            }
        });

        return rootView;
    }

    /**
     * Catches button event and take user to next fragment
     * containing player information
     */
    public void onButtonClick(View v) {

        String temp = "";

        if (v instanceof Button) {
            Button btn = (Button) v;
            temp = (String) btn.getText();
        }
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, new PlayerInfoFragment(temp));
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}