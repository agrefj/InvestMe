package ru.getlect.investme.investme.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class InvestMeAuthenticatorService extends Service {
    private InvestMeAuthenticator mAuthenticator;

    public InvestMeAuthenticatorService() {
        mAuthenticator = new InvestMeAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
