/**
 * Created by radames on 29/09/15.
 */

package com.eva.me.mysquarescreenlock;


import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class ScreenLockDeviceAdminReceiver extends DeviceAdminReceiver {
    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);

    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        //Toast.makeText(context, R.string.disableDeviceAdmin, Toast.LENGTH_LONG).show();
        Uri uri = Uri.fromParts("package", "com.eva.me.mysquarescreenlock", null);
        Intent i = new Intent(Intent.ACTION_DELETE,uri );
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

}
