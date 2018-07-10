package com.small.saasdriver.activity;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.saascardriver.R;
import com.small.saasdriver.application.MyApplication;
import com.small.saasdriver.entity.MessageCommit;
import com.small.saasdriver.utils.AnFQNumEditText;
import com.small.saasdriver.utils.MessageCommitInterface;

public class MessageCommitActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
	private Button mButton;
	private AnFQNumEditText anFQNumEditText;
	private Spinner spinner;
	private List<String> booksList;
	private ArrayAdapter arrayAdapter;
	private String index;
	public static final String PERCENTAGE = "Percentage";// 类型2(百分比类型)
	private Retrofit retrofit;

	private AutoCompleteTextView autotext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.messagecommit);
		mButton = (Button) findViewById(R.id.comit);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		anFQNumEditText = (AnFQNumEditText) findViewById(R.id.anetDemo);
		autotext = (AutoCompleteTextView) findViewById(R.id.receiver_message);

		autotext.setThreshold(1);

		String[] autoStrings = new String[] { "小猫科技", "小狗科技", "小猫科技有限公司", "大猫科技", "小猫支付公司", "小熊科技公司" };

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(MessageCommitActivity.this,
				R.layout.autocompletetextview, autoStrings);

		autotext.setAdapter(adapter);
		iv_back.setOnClickListener(this);
		mButton.setOnClickListener(this);

		anFQNumEditText.setEtMinHeight(160)// 设置最小高度，单位px
				.setLength(60)// 设置总字数
				.setType(AnFQNumEditText.PERCENTAGE)// TextView显示类型(SINGULAR单数类型)(PERCENTAGE百分比类型)
				.setLineColor("#3F51B5")// 设置横线颜色
				.show();

		spinner = (Spinner) findViewById(R.id.spinner);
		spinner.setOnItemSelectedListener(this);

		// 1. 设置数据源
		booksList = new ArrayList<String>();
		booksList.add("政企后台");
		booksList.add("租赁平台");

		// 2. 新建ArrayAdapter
		arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, booksList);
		// 3. 设置下拉菜单的样式
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 4. 为Spinner加载适配器
		spinner.setAdapter(arrayAdapter);
		// 5. 为Spinner设置监听器

	}

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.comit:

			retrofit = MyApplication.getInstance().createRetrofit();
			MessageCommitInterface apiService = retrofit.create(MessageCommitInterface.class);

			Call<MessageCommit> call = apiService.getUser(anFQNumEditText.getContent(),
					MyApplication.getInstance().getDriverID(), MyApplication.getInstance().getIdentifyt(), index,
					autotext.getText().toString()

			);

			Log.i("lin", anFQNumEditText.getContent());

			call.enqueue(new Callback<MessageCommit>() {

				@Override
				public void onFailure(Throwable t) {
					// Log error here since request failed
				}

				@Override
				public void onResponse(Response<MessageCommit> arg0, Retrofit arg1) {

					MessageCommit messageCommit = arg0.body();
					Log.i("tan", messageCommit.getData() + "");
					Log.i("tan", messageCommit.getErrCode() + "");

					Toast.makeText(getApplicationContext(), messageCommit.getErrMsg(), Toast.LENGTH_SHORT).show();

				}
			});

			break;

		case R.id.iv_back:
			finish();
			break;

		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		index = String.valueOf(position);
		Log.i("lin", index);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}
}
