package ru.getlect.investme.investme.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class InvestMeSyncService extends Service {

    private static final Object sSyncAdapterLock = new Object();
    private static InvestMeSyncAdapter sInvestMeSyncAdapter = null;


    @Override
    public void onCreate() {
        Log.d("InvestMe", "onCreate - InvestMeSyncService");
        synchronized (sSyncAdapterLock) {
            if (sInvestMeSyncAdapter == null) {
                sInvestMeSyncAdapter = new InvestMeSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sInvestMeSyncAdapter.getSyncAdapterBinder();
    }
}
