package com.eva.me.mysquarescreenlock;

import com.eva.me.mysquarescreenlock.unlock.util.PasswordUtil;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    DevicePolicyManager deviceMgr;
    ComponentName comp;


	private static final String TAG = "MainActivity";
	
	private Button btnStaSer;
	private Button btnStaPsdSetting;
	private Button btnAbout, btnLeave;
	private Context context;
	
	private void showToast(String str,Context context) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        if (BuildConfig.DEBUG) {
            Log.d("ScreenLock", "MainActivity oncreate.");
        }
        deviceMgr = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        comp = new ComponentName(this, ScreenLockDeviceAdminReceiver.class);


        if (!deviceMgr.isAdminActive(comp)) {
            Log.d("ScreenLock", "Main :admin is false");
            Intent intent = new Intent(
                    DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, comp);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    "devicePolicyManagerMessage");

            startActivityForResult(intent, 0);
        }


        init();
	}

	private void init() {
		context = MainActivity.this;

		btnStaSer = (Button) findViewById(R.id.button1);
		btnStaSer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent jmpLS = new Intent();
				jmpLS.setAction("com.eva.service.LocalService");
				MainActivity.this.startService(jmpLS);
				showToast("Open the lock screen", MainActivity.this);
//				PasswordUtil.setDefaultPsd(context);//这个是测试用，初始密码
			}
		});
		
		btnStaPsdSetting = (Button) findViewById(R.id.button2);
		btnStaPsdSetting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent jmpPS = new Intent(MainActivity.this, SetPsdActivity.class);
				startActivity(jmpPS);
				showToast("Gesture password here", MainActivity.this);
			}
		});
		
		btnAbout = (Button) findViewById(R.id.button3);
		btnAbout.setOnClickListener(new OnClickListener() {
			
			@Override
            public void onClick(View v) {

				showToast("Here is Software", context);
                deviceMgr.lockNow();

			}
		});
		
		btnLeave = (Button) findViewById(R.id.button4);
		btnLeave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showToast("Bye", context);
				finish();
				PasswordUtil.curPsd="";//还是处理一下
			}
		});

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
