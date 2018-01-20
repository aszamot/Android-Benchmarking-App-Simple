package pl.atk.oeskandroidbenchmark.screens.devices.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.atk.oeskandroidbenchmark.R;

/**
 * Created by Tomasz on 19.01.2018.
 */

public class RankingViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.rank)
    TextView rank;
    @BindView(R.id.model)
    TextView model;
    @BindView(R.id.score)
    TextView score;

    public RankingViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
