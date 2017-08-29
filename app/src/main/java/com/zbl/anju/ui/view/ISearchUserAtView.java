package com.zbl.anju.ui.view;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public interface ISearchUserAtView {

    EditText getEtSearchContent();

    RelativeLayout getRlNoResultTip();

    LinearLayout getLlSearch();
}
