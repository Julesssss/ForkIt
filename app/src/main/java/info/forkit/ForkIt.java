package info.forkit;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ForkIt extends Application {

    private static ForkIt sInstance;
    private FirebaseDatabase database;
    private FirebaseUser user;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public FirebaseDatabase getDatabase() {
        if (database == null) {
            database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
        }
        return database;
    }

     public FirebaseUser getUser() {
        if (user == null) {
            user = FirebaseAuth.getInstance().getCurrentUser();
        }
        return user;
    }

    public static ForkIt getInstance() {
        return sInstance;
    }
}
