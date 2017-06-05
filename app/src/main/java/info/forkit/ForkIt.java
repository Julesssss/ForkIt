package info.forkit;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class ForkIt extends Application {


    private static ForkIt sInstance;
    private FirebaseDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }


    public FirebaseDatabase getDatabase() {
        if (database == null)
            database = FirebaseDatabase.getInstance();
        return database;
//                DatabaseReference myRef = database.getReference("message");

    }

    public static ForkIt getInstance() {
        return sInstance;
    }
}
