package com.zbl.anju.ui.view;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.zbl.anju.widget.StateButton;

/**
 * @author James
 * @描述 登录界面的View
 */
public interface ILoginAtView {
    MaterialEditText getEdtUsername();
    MaterialEditText getEdtPassword();
    StateButton getBtnLogin();
}
