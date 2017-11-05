package custom.number.phone.call;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Build;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView listView ;
    ArrayList<String> StoreContacts ;
    ArrayAdapter<String> arrayAdapter ;
    Cursor cursor ;
    String name, phonenumber ;
    public  static final int RequestPermissionCode  = 1 ;
    Button button;
    RecyclerView recycler_view ;
    ArrayList<PhoneData> phoneData ;
    DataAdapter dataAdapter ;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = this.getResources();
// Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(LocaleHelper.getLanguage(this))); // API 17+ only.
// Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm);

        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listview1);

        button = (Button)findViewById(R.id.button1);

        StoreContacts = new ArrayList<String>();
        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((this));
        recycler_view.setLayoutManager(layoutManager);
        phoneData= new ArrayList<>();

        EnableRuntimePermission();
        for (PackageInfo pack : getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS)) {
            ProviderInfo[] providers = pack.providers;
            if (providers != null) {
                for (ProviderInfo provider : providers) {
                    Log.d("Example", "provider: " + provider.authority);
                }
            }
        }

//        GetContactsIntoArrayList();
        arrayAdapter = new ArrayAdapter<String>(
                MainActivity.this,
                R.layout.items_listview,
                R.id.textView, StoreContacts
        );

        listView.setAdapter(arrayAdapter);
        button.setVisibility(View.GONE);


    }

    public void GetContactsIntoArrayList(){


        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            StoreContacts.add(name + " "  + ":" + " " + phonenumber);
            phoneData.add(new PhoneData(name ,phonenumber)) ;
            dataAdapter= new DataAdapter(phoneData,MainActivity.this);
            recycler_view.setAdapter(dataAdapter);
            dataAdapter.notifyDataSetChanged();
        }

        cursor.close();

    }

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS))

        {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] {Manifest.permission.READ_CONTACTS},
                    REQUEST_CODE_ASK_PERMISSIONS);
            insertDummyContactWrapper();

            Toast.makeText(MainActivity.this,"Hello", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(MainActivity.this,"this", Toast.LENGTH_LONG).show();

            insertDummyContactWrapper();
//            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
//                    Manifest.permission.READ_CONTACTS}, RequestPermissionCode);

        }
    }
    private void insertDummyContactWrapper() {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CONTACTS);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_CONTACTS)) {
                showMessageOKCancel("You need to allow access to Contacts",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                ActivityCompat.requestPermissions(MainActivity.this,
//                                        new String[] {Manifest.permission.WRITE_CONTACTS},
//                                        REQUEST_CODE_ASK_PERMISSIONS);
                                ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                                        Manifest.permission.READ_CONTACTS}, RequestPermissionCode);
                            }
                        });

                return;
            }
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] {Manifest.permission.READ_CONTACTS},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
        GetContactsIntoArrayList();
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                    insertDummyContactWrapper();

                    Toast.makeText(MainActivity.this,"Permission Granted, Now your application can access CONTACTS.", Toast.LENGTH_LONG).show();

                } else {


                    Toast.makeText(MainActivity.this,"You Need to Permission to Access contacts ", Toast.LENGTH_LONG).show();

                }
                break;
            default:
                super.onRequestPermissionsResult(RC, per, PResult);
        }
    }

//    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
//
//    private static final String TAG = "Contacts";
//    private void insertDummyContact() {
//        // Two operations are needed to insert a new contact.
//        ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>(2);
//
//        // First, set up a new raw contact.
//        ContentProviderOperation.Builder op =
//                ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
//                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
//                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null);
//        operations.add(op.build());
//
//        // Next, set the name for the contact.
//        op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//                .withValue(ContactsContract.Data.MIMETYPE,
//                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
//                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
//                        "__DUMMY CONTACT from runtime permissions sample");
//        operations.add(op.build());
//
//        // Apply the operations.
//        ContentResolver resolver = getContentResolver();
//        name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//
//        phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//        StoreContacts.add(name + " "  + ":" + " " + phonenumber);
//        phoneData.add(new PhoneData(name ,phonenumber)) ;
//        dataAdapter= new DataAdapter(phoneData,MainActivity.this);
//        recycler_view.setAdapter(dataAdapter);
//        try {
//            GetContactsIntoArrayList();
//            resolver.applyBatch(ContactsContract.AUTHORITY, operations);
//        } catch (RemoteException e) {
//            Log.d(TAG, "Could not add a new contact: " + e.getMessage());
//        } catch (OperationApplicationException e) {
//            Log.d(TAG, "Could not add a new contact: " + e.getMessage());
//        }
//
//    }
//
//    private void insertDummyContactWrapper() {
//        List<String> permissionsNeeded = new ArrayList<String>();
//
//        final List<String> permissionsList = new ArrayList<String>();
//        if (!addPermission(permissionsList, Manifest.permission.READ_CONTACTS))
//            permissionsNeeded.add("Read Contacts");
//        if (!addPermission(permissionsList, Manifest.permission.WRITE_CONTACTS))
//            permissionsNeeded.add("Write Contacts");
//
//        if (permissionsList.size() > 0) {
//            if (permissionsNeeded.size() > 0) {
//                // Need Rationale
//                String message = "You need to grant access to " + permissionsNeeded.get(0);
//                for (int i = 1; i < permissionsNeeded.size(); i++)
//                    message = message + ", " + permissionsNeeded.get(i);
//                showMessageOKCancel(message,
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
//                                            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
//                                }
//                            }
//                        });
//                return;
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
//                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
//            }
//            return;
//        }
//
//        insertDummyContact();
//    }
//
//    private boolean addPermission(List<String> permissionsList, String permission) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
//                permissionsList.add(permission);
//                // Check for Rationale Option
//                if (!shouldShowRequestPermissionRationale(permission))
//                    return false;
//            }
//        }
//        return true;
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_CODE_ASK_PERMISSIONS:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // Permission Granted
//                    insertDummyContact();
//                } else {
//                    // Permission Denied
//                    Toast.makeText(MainActivity.this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT)
//                            .show();
//                }
//                break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(MainActivity.this,Tour.class);
        startActivity(setIntent);
        finish();
    }

}
