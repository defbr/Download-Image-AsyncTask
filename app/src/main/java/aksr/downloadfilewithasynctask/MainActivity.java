package aksr.downloadfilewithasynctask;

import aksr.downloadfilewithasynctask.adapter.*;
import aksr.downloadfilewithasynctask.json.*;
import aksr.downloadfilewithasynctask.json.dataModel.*;
import android.*;
import android.annotation.*;
import android.content.*;
import android.content.pm.*;
import android.net.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.support.v4.content.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;
import com.android.volley.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    int permission;
    SeekBar progressBar4;
    public static File folder;
    private String[] mFileStrings;
    private String[] mFileImages;
    public static String[] imageURLArray;
    public static OutputStream output;
    Button permissao;
    Button Folder;
    long totalNumFiles;
    ListView list;
    CustomAdapter listAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        permission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);


        folder = new File(Environment.getExternalStorageDirectory() + File.separator + "/MyAppName");
        if(folder.canRead()){ totalNumFiles = folder.listFiles().length; CarregarDados();}

        Folder =  (Button)findViewById(R.id.folder);
        if (totalNumFiles>0) {
            Folder.setVisibility(View.VISIBLE);
        }
        Folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Folder();
            }
        });
        permissao =  (Button)findViewById(R.id.permissao);
        permissao.setVisibility(View.GONE);
        permissao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performRequest();
            }
        });


        progressBar4 =  (SeekBar)findViewById(R.id.progressBar4);
        progressBar4.setClickable(false);
        progressBar4.setEnabled(false);
        progressBar4.setMax(100);
        progressBar4.setVisibility(View.GONE);


        if (totalNumFiles==0) {
            performRequest();
        }




    }

    public void performRequest() {
         GsonGetRequest<ArrayList<DummyObject>> gsonGetRequest =
                ApiRequests.getDummyObjectARRAY
                        (
                                new Response.Listener<ArrayList<DummyObject>>() {
                                    @Override
                                    public void onResponse(ArrayList<DummyObject> dummyObjectArrayList) {

                                        if (dummyObjectArrayList.get(0).getImage().equals("sim")) {
                                            if (Build.VERSION.SDK_INT >= 23) {
                                                if (permission != PackageManager.PERMISSION_GRANTED) {
                                                    ActivityCompat.requestPermissions(MainActivity.this,
                                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                                                } if (permission == PackageManager.PERMISSION_GRANTED) {
                                                    for (int i = 0; i < dummyObjectArrayList.size(); i++) {
                                                        if (dummyObjectArrayList.get(i).getImage().equals("sim")) {
                                                            progressBar4.setVisibility(View.VISIBLE);

                                                            for (int j = 1; j < dummyObjectArrayList.size(); j++) {
                                                                imageURLArray = new String[]{dummyObjectArrayList.get(j).getImage()};
                                                                DownloadFile dloadFAsync = new DownloadFile(imageURLArray);
                                                                dloadFAsync.execute(imageURLArray);
                                                            }
                                                        }
                                                    }
                                                }
                                            }else{
                                                for (int i = 0; i < dummyObjectArrayList.size(); i++) {
                                                    if (dummyObjectArrayList.get(i).getImage().equals("sim")) {
                                                        progressBar4.setVisibility(View.VISIBLE);

                                                        for (int j = 1; j < dummyObjectArrayList.size(); j++) {
                                                            imageURLArray = new String[]{dummyObjectArrayList.get(j).getImage()};
                                                            DownloadFile dloadFAsync = new DownloadFile(imageURLArray);
                                                            dloadFAsync.execute(imageURLArray);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                ,
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(MainActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
        App.addRequest(gsonGetRequest, "JSON");
    }
    @Override
    protected void onStop() {
        App.cancelAllRequests("JSON");

        super.onStop();
    }

    @SuppressLint("StaticFieldLeak")
    public class DownloadFile extends AsyncTask<String, String, String> {
        int current=0;
        String[] paths;
        String fpath;

        DownloadFile(String[] paths) {
            super();
            this.paths = paths;
            for(int i=0; i<paths.length; i++)
                System.out.println((i+1)+":  "+paths[i]);
        }

        @Override
        protected String doInBackground(String... f_url) {
            folder = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "/MyAppName");
            if (!folder.exists()) {
                //noinspection ResultOfMethodCallIgnored
                folder.mkdirs();
            }

            int rows = f_url.length;
            while(current < rows)
            {
                int count;
                try {
                    fpath = getFileName(this.paths[current]);
                    URL url = new URL(this.paths[current]);
                    URLConnection conexion = url.openConnection();
                    conexion.connect();
                    int lenghtOfFile = conexion.getContentLength();
                    InputStream input = new BufferedInputStream(url.openStream(), 512);
                    output = new FileOutputStream(folder+"/"+fpath);

                    byte data[] = new byte[512];
                    long total = 0;
                    while ((count = input.read(data)) != -1) {
                        total += count;
                        output.write(data, 0, count);
                        progressBar4.setProgress((int)((total*100)/lenghtOfFile));
                    }
                    output.flush();
                    output.close();
                    input.close();

                    current++;
                } catch (Exception ignored) {}
            }
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            progressBar4.setVisibility(View.GONE);
            Folder.setVisibility(View.VISIBLE);
            totalNumFiles = folder.listFiles().length;
            CarregarDados();
        }
    }

    String getFileName(String wholePath)
    {
        String name=null;
        int start,end;
        start=wholePath.lastIndexOf('/');
        end=wholePath.length();
        name=wholePath.substring((start+1),end);

        return name;
    }

    public void Folder()
    {
        try
        {
            Uri selectedUri = Uri.parse(Environment.getExternalStorageDirectory() + File.separator + "/MyAppName");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(selectedUri, "resource/folder");
            startActivity(intent);
        }
        catch (Exception e)
        {
            Toast.makeText(MainActivity.this, String.valueOf(getString(R.string.gerenciador)), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    recreate();
                    permissao.setVisibility(View.GONE);
                } else {
                    permissao.setVisibility(View.VISIBLE);
                    permissao.setText(String.valueOf(getString(R.string.Permissao23)));

                }
            }
        }
    }


    public void CarregarDados() {
        if (folder.isDirectory()) {
            File[] listFile = folder.listFiles();
            mFileStrings = new String[listFile.length];
            mFileImages = new String[listFile.length];
            for (int i = 0; i < listFile.length; i++) {
                mFileStrings[i] = listFile[i].getName();
                mFileImages[i] = listFile[i].getAbsolutePath();
            }
        }
        list= (ListView)findViewById(R.id.list);
        listAdapter = new CustomAdapter(MainActivity.this, mFileStrings,mFileImages);
        list.setAdapter(listAdapter);

    }

}
