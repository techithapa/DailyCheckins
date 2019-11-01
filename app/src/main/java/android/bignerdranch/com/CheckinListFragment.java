package android.bignerdranch.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CheckinListFragment extends Fragment {
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    private RecyclerView mCheckinRecyclerView;
    private CheckinAdapter mAdapter;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkin_record, container, false);
        mCheckinRecyclerView = (RecyclerView) view
                .findViewById(R.id.checkin_recycler_view);
        mCheckinRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_checkin_record, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_help);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_help);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_checkin:
                CheckinStore checkin = new CheckinStore();
                CheckinStore.get(getActivity()).addCheckin(checkin);
                Intent intent = CheckinActivity.newIntent(getActivity(), checkin.getId());
                startActivity(intent);
                return true;
            case R.id.show_help:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        CheckinStore checkinStore = CheckinStore.get(getActivity());
        int checkinCount = checkinStore.getCheckins().size();
        String subtitle = getString(R.string.subtitle_format, checkinCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }
    private void updateUI() {
        CheckinStore checkinStore = CheckinStore.get(getActivity());
        List<CheckinStore> checkins = checkinStore.getCheckins();
        if (mAdapter == null) {
            mAdapter = new CheckinAdapter(checkins);
            mCheckinRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setCheckins(checkins);
            mAdapter.notifyDataSetChanged();
        }
        updateSubtitle();
        //mAdapter = new CheckinAdapter(checkins);
       // mCrimeRecyclerView.setAdapter(mAdapter);
    }

    private class CheckinHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mPlaceTextView;
        private TextView mDetailsTextView;
        private TextView mDateTextView;
        private TextView mLocationTextView;
        private CheckinStore mCheckin;

        public CheckinHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_checkin, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.checkin_title);
            mPlaceTextView = (TextView) itemView.findViewById(R.id.checkin_place);
            mDetailsTextView = (TextView) itemView.findViewById(R.id.checkin_details);
            mDateTextView = (TextView) itemView.findViewById(R.id.checkin_date);
            mLocationTextView = (TextView) itemView.findViewById(R.id.saved_location);


        }
        public void bind(CheckinStore checkin) {
            mCheckin = checkin;
            mTitleTextView.setText(mCheckin.getTitle());
            mPlaceTextView.setText(mCheckin.getPlace());
            mDetailsTextView.setText(mCheckin.getDetails());
            mDateTextView.setText(mCheckin.getDate().toString());
            mLocationTextView.setText(mCheckin.getLocation());
        }

        @Override
        public void onClick(View view) {
            Intent intent = CheckinActivity.newIntent(getActivity(), mCheckin.getId());
            startActivity(intent);
        }
    }
    private class CheckinAdapter extends RecyclerView.Adapter<CheckinHolder> {
        private List<CheckinStore> mCheckins;
        public CheckinAdapter(List<CheckinStore> checkins) {
            mCheckins = checkins;
        }


        @Override
        public CheckinHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CheckinHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CheckinHolder holder, int position) {
            CheckinStore checkin = mCheckins.get(position);
            holder.bind(checkin);

        }

        @Override
        public int getItemCount() {
            return mCheckins.size();

        }

        public void setCheckins(List<CheckinStore> checkins) {
            mCheckins = checkins;
        }
    }

}
