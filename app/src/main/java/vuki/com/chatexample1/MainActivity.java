package vuki.com.chatexample1;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import vuki.com.chatexample1.databinding.ActivityMainBinding;

/*
https://github.com/himanshu-soni/ChatMessageView/
 */
public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private Button mButtonSend;
    private EditText mEditTextMessage;
    private ImageView mImageView;
    private ChatMessageAdapter mAdapter;

    ActivityMainBinding binding;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_main );

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

    private void sendMessage( String message ) {
        ChatMessage chatMessage = new ChatMessage( message, true, false );
        mAdapter.add( chatMessage );

        mimicOtherMessage( message );
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

}