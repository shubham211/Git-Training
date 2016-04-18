package com.android.happilyunmarried;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.listeners.OnLogoutListener;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import app.App;
import staticfiles.aboutus.AboutUsFragment;
import staticfiles.contactus.ContactUsFragment;
import staticfiles.faq.FAQFragment;
import staticfiles.policies.PolicyMainFragment;
import tabs.gifts.GiftFinder;
import tabs.merchandise.MerchandiseTab;
import utils.Constants;
import utils.HUMParams;
import utils.ParcelableClass;
import utils.ServiceUtils;
import utils.widgets.ExpandList;

/**
 * Created by foofys on 6/5/15.
 */
public class BaseActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<People.LoadPeopleResult> {

    public ExpandList mDrawerOptionsView, mDrawerOrderView, mDrawerShareView, mDrawerAboutView;
    public TableRow mTableRow;

    GoogleApiClient mGoogleApiClient;
    boolean mSignInClicked;

    SimpleFacebook mSimpleFacebook;

    public String mView = "mImageView";
    public String mTitle = "mTitle";


    public String[] from = {mView, mTitle};
    public int[] to = {R.id.imageView, R.id.textOption};

    public String[] titleFrom = {mTitle};
    public int[] titleTo = {R.id.textSingleOption};

    public String[] mTitles =
            {"Browse Feed", "Store", "Ustraa", "Looking to Gift someone?", "Co-Branded Merchandise"};

//    public String[] mTitles_1 =
//            {"Track your orders", "FAQ", "Contact us"};
    public String[] mTitles_1 =
            {"Track Your Orders", "Frequently Asked Questions", "Contact us"};

    public String[] mTitles_2 =
            {"Invite Friends to Happily Unmarried", "Rate this app on Google PlayStore"};

    public String[] mTitles_3 =
            {"Policies", "About Us", "Sign out"};


    public int mCurrentSelectedPosition = 0;

    public TextView mUserProfileName;

    public FloatingActionButton fabButton;

    public ActionBarDrawerToggle actionBarDrawerToggle;

    public DrawerLayout mDrawerLayout;

    public ImageView mInstagramIcon ,mFacebookIcon, mTwitterIcon;

    public int[] drawables = new int[]{
            R.drawable.browse_feed,
            R.drawable.store,
            R.drawable.ustraa,
            R.drawable.gift,
            R.drawable.brand_merchandise
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();


    }



    public void SignOutUser() {

        SharedPreferences.Editor   editor = getSharedPreferences("LOGIN", 0).edit();
        SharedPreferences.Editor   twittereditor = getSharedPreferences(HUMParams.TWITTER_SHARED_PREF, MODE_PRIVATE).edit();
        SharedPreferences.Editor   userAccPrefs = getSharedPreferences(HUMParams.USER_CONNECTED_ACCOUNTS_PREFS, MODE_PRIVATE).edit();
        SharedPreferences.Editor   notificationPrefsEditor = getSharedPreferences(HUMParams.USER_NOTIFICATION, Context.MODE_PRIVATE).edit();
//if logged in via facebook, clear session
        OnLogoutListener onLogoutListener = new OnLogoutListener() {

            @Override
            public void onLogout() {
                Log.i("FACEBOOK ", "You are logged out");
            }

        };
       try {
           mSimpleFacebook.logout(onLogoutListener);
       }catch(Exception e){
           e.printStackTrace();
       }
//if logged in via google+
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            // updateUI(false);
           Log.v("GOOGLE LOG OUT ", " SUCESS ");
        }


//        editor.putString(HUMParams.LOGIN, "NA");
//        editor.putString(HUMParams.LOGIN_MODE, "NA");
//        editor.putString(HUMParams.USER_FIRSTNAME, "NA");
//        editor.putString(HUMParams.USER_LASTNAME, "NA");
//        editor.putString(HUMParams.USER_NAME, "NA");
//        editor.putString(HUMParams.USER_GENDER, "NA");
//        editor.putString(HUMParams.USER_DOB, "NA");
//        editor.putString(HUMParams.USER_PROFILE_URI, "NA");
//        editor.putString(HUMParams.USER_CITY, "NA");
//        editor.putString(HUMParams.USER_PHONE, "NA");
//
//        editor.putString(HUMParams.USER_CONNECTED_ACCOUNT_FACEBOOK, "NA");
//        editor.putString(HUMParams.USER_CONNECTED_ACCOUNT_TWITTER, "NA");
//
//        editor.putString(HUMParams.USER_CONNECTED_ACCOUNT_INSTAGRAM,"NA");
//        editor.putString(HUMParams.USER_INSTAGRAM_ACCESS_TOKEN,"NA");
//        editor.putString(HUMParams.USER_INSTAGRAM_USERNAME,"NA");
//        editor.putString(HUMParams.USER_INSTAGRAM_NAME,"NA");
//
//        twittereditor.putString(HUMParams.USER_CONNECTED_ACCOUNT_TWITTER, "NA");
//        twittereditor.putString(HUMParams.TWITTER_USER_SCREEN_NAME, "NA");
//        twittereditor.putString(HUMParams.TWITTER_USER_NAME, "NA");
//        twittereditor.putString(HUMParams.TWITTER_USER_PIC, "NA");
//
//        userAccPrefs.putBoolean(HUMParams.FACEBOOK_USER_CONN_ACC_STATUS,false);
//        userAccPrefs.putBoolean(HUMParams.TWITTER_USER_CONN_ACC_STATUS,false);
//
//
//        notificationPrefsEditor.putBoolean(HUMParams.USER_NOTIFICATION_SMS,false);
//        notificationPrefsEditor.putBoolean(HUMParams.USER_NOTIFICATION_WHATSAPP,false);
//        notificationPrefsEditor.putBoolean(HUMParams.USER_NOTIFICATION_APP_NOTIFY,false);
//        notificationPrefsEditor.putBoolean(HUMParams.USER_NOTIFICATION_EMAIL,false);


        editor.clear();
        editor.remove("login");
        twittereditor.clear();
        userAccPrefs.clear();
        notificationPrefsEditor.clear();
        editor.commit();
        twittereditor.commit();
        userAccPrefs.commit();
        notificationPrefsEditor.commit();
        App.getInstance().clearDb();
        deleteDatabase(Constants.HUM_DATABASE_NAME);
        ServiceUtils.NavigatetoIntent(BaseActivity.this, HUMParams.CLEAR_BACK_STACK, null, null);
        Intent intent = new Intent(BaseActivity.this,LoginActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void NavigateToOption(Context context,String from) {

            Log.e("from", from);
            if (from.equalsIgnoreCase("FAQ")) {

                Log.e("FAQ", "FAQ");

//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.MainContainer, new FAQFragment())
//                        .commit();
                Intent myIntent = new Intent(context, FAQFragment.class);
                startActivity(myIntent);

            } else if (from.equalsIgnoreCase("contactus")) {

                Log.e("FAQ", "contactus");

//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.MainContainer, new ContactUsFragment())
//                        .commit();
                Intent myIntent = new Intent(context, ContactUsFragment.class);
                startActivity(myIntent);

            } else if (from.equalsIgnoreCase("aboutus")) {

            Log.e("ABOUT", "aboutus");
            Intent myIntent = new Intent(context, AboutUsFragment.class);
            startActivity(myIntent);

        }else if (from.equalsIgnoreCase("policies")) {

            Log.e("POlicy", "policies");

            Intent myIntent = new Intent(context, PolicyMainFragment.class);
            startActivity(myIntent);

        }else if (from.equals(HUMParams.NAV_T0_GIFTS)) {

                Log.e("gifts", "gifts");

                Intent myIntent = new Intent(context, GiftFinder.class);
                startActivity(myIntent);

            }
 else if(from.equals((HUMParams.NAV_T0_BRAND_MERCH))){

                Log.e("branded merchandise", "bm");

                Intent myIntent = new Intent(context, MerchandiseTab.class);
                startActivity(myIntent);
            }
        else{

                Intent intent = new Intent(context, MVCActivity.class);
                ParcelableClass parcelableClass = new ParcelableClass(null, null, from, null);
                intent.putExtra(from, parcelableClass);
                intent.putExtra("bundle", new Bundle());
                intent.putExtra("from", from);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out) ;
                finish();
            }
            }

//google+ methods


    public void onConnected(Bundle arg0) {
        // TODO Auto-generated method stub
        mSignInClicked = false;

        // updateUI(true);
        Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(
                this);
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        // TODO Auto-generated method stub
        mGoogleApiClient.connect();
        // updateUI(false);
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub

    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(this);
    }

    @Override
    public void onResult(People.LoadPeopleResult arg0) {
        // TODO Auto-generated method stub

    }
}
