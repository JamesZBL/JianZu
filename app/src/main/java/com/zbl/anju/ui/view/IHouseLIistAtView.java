package com.zbl.anju.ui.view;

import android.widget.ListView;

import com.lqr.recyclerview.LQRRecyclerView;

/**
 * Created by James on 2017/7/9.
 */

public interface IHouseLIistAtView {
	ListView getListViewHouseTypes();
	ListView getListViewPrices();
	LQRRecyclerView getRvHouses();
}
