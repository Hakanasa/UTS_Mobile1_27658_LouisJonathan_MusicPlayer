package umn.ac.id.uts_27658;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class List extends AppCompatActivity{

    RecyclerView rv;
    String[] items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        rv = findViewById(R.id.rvSong);

        runtimePermission();
        getSupportActionBar().setTitle("List Lagu");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));


    }

    public void runtimePermission(){
        Dexter.withContext(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        displaySongs();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(java.util.List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    public ArrayList<File> findSong (File file)
    {
        ArrayList<File> arrayList = new ArrayList<>();

        File[] files = file.listFiles();

        for(File singlefile: files)
        {
            if(singlefile.isDirectory() && !singlefile.isHidden())
            {
                arrayList.addAll(findSong(singlefile));
            }
            else
            {
                if(singlefile.getName().endsWith(".mp3") || singlefile.getName().endsWith(".wav"))
                {
                    arrayList.add(singlefile);
                }
            }
        }

        return arrayList;
    }

    void displaySongs()
    {
        final ArrayList<File> mySongs = findSong(Environment.getExternalStorageDirectory());

        items = new String[mySongs.size()];
        for(int i = 0; i<mySongs.size(); i++)
        {
            items[i] = mySongs.get(i).getName().toString().replace(".mp3", "").replace(".wav", "");

        }

        rv.setAdapter(new MusicAdapter(this, mySongs, items));
        rv.setLayoutManager(new LinearLayoutManager(this));

    }


    //ADAPTER BUAT RECYCLER VIEW saya satuin di 1 file java
    public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder>{

        private ArrayList<File> mySongs;
        private Context mContext;
        private TextView textsong;
        private String[] items;

        public MusicAdapter(Context mainActivity, ArrayList<File> mySong, String[] item) {
            mContext = mainActivity;
            mySongs = mySong;
            items = item;
        }

        @NonNull
        @Override
        public MusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(
                    LayoutInflater.from(mContext)
                            .inflate(R.layout.list_item, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(@NonNull MusicAdapter.ViewHolder holder, int position) {
            final File file = mySongs.get(position);
            textsong = holder.itemView.findViewById(R.id.txtsongname);
            textsong.setSelected(true);
            textsong.setText(items[position]);
            holder.parentView.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), PlayerActivity.class)
                    .putExtra("songs", mySongs)
                    .putExtra("songname", items[position])
                    .putExtra("pos", position)));
        }

        @Override
        public int getItemCount() {
            return this.mySongs.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private View parentView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.parentView = itemView;
            }
        }
    }

    //Menampilkan Option Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    //Action Dari Option List
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.profilein:
                Intent intent = new Intent(getApplicationContext(), profile2.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(getApplicationContext(), Login.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), "Klik back button di atas",
                    Toast.LENGTH_LONG).show();

        return false;
        // Disable back button..............
    }


}