package com.zbl.anju.ui.presenter;

import android.os.Environment;
import android.view.Gravity;
import android.widget.Toast;

import com.zbl.anju.api.ApiRetrofit;
import com.zbl.anju.app.AppConst;
import com.zbl.anju.model.response.UploadSigResponse;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.base.BasePresenter;
import com.zbl.anju.ui.view.IHandwriteAtView;
import com.zbl.anju.util.ImageUtils;
import com.zbl.anju.util.UIUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HandwriteAtPresenter extends BasePresenter<IHandwriteAtView> {

	private String mFilePath = null;

	public HandwriteAtPresenter(BaseActivity context) {
		super(context);
	}

	public void save() throws IOException {
		if (getView().getLinepathView().getTouched()) {
			Observable.just(saveLocalFile())
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe((result) -> {
						switch (result) {
							case AppConst.FILE_WRITE_OK:
								UIUtils.showToast("保存成功");
								break;
							case AppConst.FILE_WRITE_ERROR:
								UIUtils.showToast("保存失败");
						}
					});
		} else {
			UIUtils.showToast("请先书写签名");
		}
	}

	/**
	 * 将签名保存到本地
	 *
	 * @return
	 */
	private synchronized String saveLocalFile() {

		File path = new File(Environment.getExternalStorageDirectory() + "/ASig/");
		if (!path.exists()) {
			path.mkdirs();
		}
		String filename = "sig-" + System.currentTimeMillis() + ".png";
		String url = path + File.separator + filename;
		try {
			getView().getLinepathView().save(path + File.separator + filename, false, 20);
		} catch (IOException e) {
			e.printStackTrace();
			return AppConst.FILE_WRITE_ERROR;
		}
		mFilePath = url;
		return AppConst.FILE_WRITE_OK;
	}

	/**
	 * 上传签名
	 */
	public void uploadSig() {
		if (getView().getLinepathView().getTouched()) {
			Observable.just(saveLocalFile())
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe((result) -> {
						switch (result) {
							case AppConst.FILE_WRITE_OK:
								//上传
								sendBroadcast(AppConst.SIGNATURE_UPLOAD_STARTED);
								try {
									String name = getView().getEdtName().getText().toString();
									String description=getView().getEdtDescription().getText().toString();
									uploadImage(name,description);
								} catch (Exception e) {
									e.printStackTrace();
								}
								break;
							case AppConst.FILE_WRITE_ERROR:
								sendBroadcast(AppConst.SIGNATURE_UPLOAD_FAILED);
						}
					});
		} else {
			UIUtils.showToast("请先书写签名", Toast.LENGTH_SHORT, Gravity.CENTER_VERTICAL);
		}
	}

	/**
	 * 发送图片请求
	 */
	private void uploadImage(String name,String description) throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("name", name);
		params.put("description", description);
		if (mFilePath != null) {
			ApiRetrofit.getInstance().uploadSig(params, ImageUtils.image2Bytes(mFilePath))
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(new Subscriber<UploadSigResponse>() {
						@Override
						public void onCompleted() {
						}

						@Override
						public void onError(Throwable throwable) {
							sendBroadcast(AppConst.SIGNATURE_UPLOAD_FAILED);
						}

						@Override
						public void onNext(UploadSigResponse uploadSigResponse) {
							//
							if (uploadSigResponse.getCode() == 200) {
								sendBroadcast(AppConst.SIGNATURE_UPLOAD_SUCCESS);
							} else {
								sendBroadcast(AppConst.SIGNATURE_UPLOAD_FAILED);
							}
						}
					});
		}
	}
}
