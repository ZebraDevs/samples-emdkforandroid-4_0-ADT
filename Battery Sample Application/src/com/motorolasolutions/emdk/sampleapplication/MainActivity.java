package com.motorolasolutions.emdk.sampleapplication;

import android.os.BatteryManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.TextView;
import com.motorolasolutions.emdk.batterysampleapplication.R;

public class MainActivity extends Activity {

	private static final String TAG = "BatterySampleApplication";
	
	private TextView mtxt_batterylevel;
	private TextView mtxt_batterytemp;
	private TextView mtxt_batteryvolt;
	private TextView mtxt_batterystate;
	private TextView mtxt_batterysource;
	private TextView mtxt_backupbatteryvoltage;
	private TextView mtxt_batterymanufacturedate;
	private TextView mtxt_batteryserialnumber;
	private TextView mtxt_batterypartnumber;
	private TextView mtxt_batteryuniqueid;
	private TextView mtxt_batteryratedcapacity;
	private TextView mtxt_batterychargecyclecount;
	
	Intent_Receiver mIntent_Receiver;
	IntentFilter mIntentFilter;
	
    public static final String BATTERY_STATE_CHANGED_INTENT = Intent.ACTION_BATTERY_CHANGED;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		mtxt_batterylevel = (TextView) findViewById(R.id.text_BatteryLevel);
		mtxt_batterytemp = (TextView) findViewById(R.id.text_BatteryTemp);
		mtxt_batteryvolt = (TextView) findViewById(R.id.text_BatteryVolt);
		mtxt_batterystate = (TextView) findViewById(R.id.text_BatteryState);
		mtxt_batterysource = (TextView) findViewById(R.id.text_BatterySource);
		mtxt_backupbatteryvoltage = (TextView) findViewById(R.id.text_BackupBatteryVoltage);
		mtxt_batterymanufacturedate = (TextView) findViewById(R.id.text_BatteryManufactureDate);
		mtxt_batteryserialnumber = (TextView) findViewById(R.id.text_BatterySerialNumber);
		mtxt_batterypartnumber = (TextView) findViewById(R.id.text_BatteryPartNumber);
		mtxt_batteryuniqueid = (TextView) findViewById(R.id.text_BatteryUniqueID);
		mtxt_batteryratedcapacity = (TextView) findViewById(R.id.text_BatteryRatedCapacity);
		mtxt_batterychargecyclecount = (TextView) findViewById(R.id.text_BatteryChargeCycleCount);

		// Initialize Intent Receiver
		mIntent_Receiver = new Intent_Receiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BATTERY_STATE_CHANGED_INTENT);
	    registerReceiver(mIntent_Receiver,mIntentFilter);
		
    }

    protected void onPause() {
        unregisterReceiver(mIntent_Receiver);
        super.onPause();
    }

    protected void onResume() {
        registerReceiver(mIntent_Receiver, mIntentFilter);
        super.onResume();
    }

    class Intent_Receiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            // Did we get a battery Intent?
            if (BATTERY_STATE_CHANGED_INTENT.equals(intent.getAction())) {
        		mtxt_batterylevel.setText(getResources().getString(R.string.BatteryLevel) + " " +
        				String.valueOf(intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)) + "%");
        		
        		mtxt_batterytemp.setText(getResources().getString(R.string.BatteryTemp) +  " " +
        				String.valueOf((float)intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1)/10) + 
        				getResources().getString(R.string.degreesC));
        		
        		mtxt_batteryvolt.setText(getResources().getString(R.string.BatteryVolt) +  " " +
        				String.valueOf((float)intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1)/1000) + 
        				getResources().getString(R.string.Volts));
	
        		switch (intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN)) {
	        		case BatteryManager.BATTERY_STATUS_CHARGING:
	        			mtxt_batterystate.setText(R.string.BatteryStateCharging);
	        			break;
		    		case BatteryManager.BATTERY_STATUS_DISCHARGING:
		    			mtxt_batterystate.setText(R.string.BatteryStateDischarging);
		    			break;
		    		case BatteryManager.BATTERY_STATUS_FULL:
		    			mtxt_batterystate.setText(R.string.BatteryStateFull);
		    			break;
		    		case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
		    			mtxt_batterystate.setText(R.string.BatteryStateNotcharging);
		    			break;
		    		default:
		    			mtxt_batterystate.setText(R.string.BatteryStateUnknown);
		    			break;
        		}
        		
        		switch (intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0)) {
        			case 0:
        				mtxt_batterysource.setText(R.string.BatterySourceOnbattery);
        				break;
        			case BatteryManager.BATTERY_PLUGGED_AC:
        				mtxt_batterysource.setText(R.string.BatterySourceOnAC);
	        			break;
	        		case BatteryManager.BATTERY_PLUGGED_USB:
	        			mtxt_batterysource.setText(R.string.BatterySourceOnusb);
	        			break;
	        	}
        		
        		mtxt_backupbatteryvoltage.setText(getResources().getString(R.string.BackupBatteryVoltage) +  " " +
        				intent.getExtras().getInt("bkvoltage"));
        		
        		mtxt_batterymanufacturedate.setText(getResources().getString(R.string.BatteryManufactureDate) +  " " +
        				intent.getExtras().getString("mfd"));
        		
        		mtxt_batteryserialnumber.setText(getResources().getString(R.string.BatterySerialNumber) +  " " +
        				intent.getExtras().getString("serialnumber"));
        		
        		mtxt_batterypartnumber.setText(getResources().getString(R.string.BatteryPartNumber) +  " " +
        				intent.getExtras().getString("partnumber"));
        		
        		mtxt_batteryuniqueid.setText(getResources().getString(R.string.BatteryUniqueId) +  " " +
        				intent.getExtras().getString("uniqueid"));
        		
        		mtxt_batteryratedcapacity.setText(getResources().getString(R.string.BatteryRatedCapacity) +  " " +
        				intent.getExtras().getInt("ratedcapacity"));
        		
        		mtxt_batterychargecyclecount.setText(getResources().getString(R.string.BatteryChargeCycleCount) +  " " +
        				intent.getExtras().getInt("cycle"));
        		
            } else {
            	Log.e(TAG, "Unhandled Intent Received");
            }
            
        } 
    }
}