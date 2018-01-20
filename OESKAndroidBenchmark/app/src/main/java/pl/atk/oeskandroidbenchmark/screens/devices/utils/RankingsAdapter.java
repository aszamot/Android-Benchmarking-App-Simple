package pl.atk.oeskandroidbenchmark.screens.devices.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import pl.atk.oeskandroidbenchmark.R;
import pl.atk.oeskandroidbenchmark.model.TestResult;

/**
 * Created by Tomasz on 19.01.2018.
 */

public class RankingsAdapter extends RecyclerView.Adapter<RankingViewHolder> {

    private List<TestResult> rankings;
    private List<TestResult> rankingsCopy = new LinkedList<>();

    public RankingsAdapter(List<TestResult> rankings) {
        this.rankings = rankings;
        Collections.sort(this.rankings, new Comparator<TestResult>() {
            @Override
            public int compare(TestResult t1, TestResult t2) {
                return t1.getFullTime().compareTo(t2.getFullTime());
            }
        });
        this.rankingsCopy.addAll(rankings);
    }

    @Override
    public RankingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_devices, parent, false);

        return new RankingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RankingViewHolder holder, int position) {
        holder.rank.setText((position + 1) + "");
        holder.model.setText(rankings.get(position).getDeviceName());
        holder.score.setText(rankings.get(position).getShortTime().toString());
    }

    @Override
    public int getItemCount() {
        return rankings.size();
    }

    public void filter(String text) {
        rankings.clear();
        if (text.isEmpty()) {
            rankings.addAll(rankingsCopy);
        } else {
            text = text.toLowerCase();
            for (TestResult item : rankingsCopy) {
                if (item.getDeviceName().toLowerCase().contains(text)) {
                    rankings.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
