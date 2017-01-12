package vuki.com.chatexample1;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import vuki.com.chatexample1.databinding.ActivityMainBinding;
import vuki.com.chatexample1.helpers.NotesHelper;
import vuki.com.chatexample1.models.ChatMessage;

/*
https://github.com/himanshu-soni/ChatMessageView/
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private ListView mListView;
    private Button mButtonSend;
    private EditText mEditTextMessage;
    private ImageView mImageView;
    private ChatMessageAdapter mAdapter;
    private final int NO_GOOGLE_PLAY_SERVICE_AVAILABLE = 3; //for activity on result
    FirebaseAnalytics firebaseAnalytics;
    ActivityMainBinding binding;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_main );

        isGooglePlayServicesAvailable( this );
        firebaseAnalytics = FirebaseAnalytics.getInstance( this );
        final String token = FirebaseInstanceId.getInstance().getToken() != null ? FirebaseInstanceId.getInstance().getToken() : "";
        NotesHelper.log( TAG, "Firebase token: " + token );
        firebaseAnalytics.setUserId( token );

        mListView = (ListView) findViewById( R.id.listView );
        mButtonSend = (Button) findViewById( R.id.btn_send );
        mEditTextMessage = (EditText) findViewById( R.id.et_message );
        mImageView = (ImageView) findViewById( R.id.iv_image );

        mAdapter = new ChatMessageAdapter( this, new ArrayList<ChatMessage>() );
        mListView.setAdapter( mAdapter );

        mButtonSend.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                String message = mEditTextMessage.getText().toString();
                if( TextUtils.isEmpty( message ) ) {
                    return;
                }
                sendMessage( message );
                mEditTextMessage.setText( "" );
            }
        } );

        mImageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                sendMessage();
            }
        } );
    }

    @Override
    protected void onResume() {
        super.onResume();
        isGooglePlayServicesAvailable( this );
    }

    private void sendMessage( String message ) {
        ChatMessage chatMessage = new ChatMessage( message, true, false );
        mAdapter.add( chatMessage );

        //mimicOtherMessage( message );
    }

    private void mimicOtherMessage( String message ) {
        ChatMessage chatMessage = new ChatMessage( message, false, false );
        mAdapter.add( chatMessage );
    }

    private void sendMessage() {
        ChatMessage chatMessage = new ChatMessage( null, true, true );
        mAdapter.add( chatMessage );

        mimicOtherMessage();
    }

    private void mimicOtherMessage() {
        ChatMessage chatMessage = new ChatMessage( null, false, true );
        mAdapter.add( chatMessage );
    }

    public boolean isGooglePlayServicesAvailable( Context context ) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable( context );
        if( resultCode != ConnectionResult.SUCCESS ) {
            //If user can resolve the problem
            if( apiAvailability.isUserResolvableError( resultCode ) ) {
                apiAvailability.getErrorDialog( this, resultCode, NO_GOOGLE_PLAY_SERVICE_AVAILABLE )
                        .show();
            } else {
                Toast.makeText( this, "This device is not supported", Toast.LENGTH_SHORT ).show();
                finish();
            }
            return false;
        }
        return true;
    }

}
