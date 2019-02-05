package com.example.viraj.swimmingapp;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.graphics.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

import android.graphics.pdf.PdfDocument.Page;
import android.graphics.pdf.PdfDocument.PageInfo;
import android.graphics.pdf.PdfRenderer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HeatLaneDisplayFragment extends Fragment {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    //List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    Map<String, List<String>> map;

    Bundle inputBundle;
    ScrollView scroll;
    EditText text;
    ArrayList<String> order;
    String orderPool;
    String type;
    int numLanes;
    String event;
    String eventPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_heat_lane_display, container, false);

    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        expandableListDetail = getData();
        ArrayList<String> temp = new ArrayList<String>(expandableListDetail.keySet());
        Collections.sort(temp);
        //expandableListTitle = new ArrayList<String>();
        expandableListAdapter = new CustomExpandableListAdapter(getActivity(), temp, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
              /*  Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
              /*  Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();*/

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                /*Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();*/
                return false;
            }
        });
    /*

        text = (EditText) view.findViewById(R.id.orderSwimmers);
        text.setEnabled(false); //prevent the et_laps to be editable

        scroll = (ScrollView) view.findViewById(R.id.scrollview3);
        inputBundle = getArguments();
        order = inputBundle.getStringArrayList("Order");
        setHeatLane(order);
        for(int x = 0; x < order.size(); x ++){
            text.append(order.get(x) + "\n");
        }*/

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();

                final View dialogView = inflater.inflate(R.layout.heat_lane_display_pdf, null);

                String text = order.get(0);
                for(int x = 1; x < order.size(); x++)
                    text = text + ", " + order.get(x);
                createandDisplayPdf(text);

                /*ImageView pdfView = view1.findViewById(R.id.pdfView);
                PdfDocument document = new PdfDocument();
                final AlertDialog dialog = builder.create();
                dialog.show();

                // repaint the user's text into the page
                View content = dialogView.findViewById(R.id.pdf_content);


                // crate a page description
                int pageNumber = 1;
                System.out.println("Content!$:" + content + "Width:" + content.getWidth() + "Height:" + content.getHeight());
                PageInfo pageInfo = new PageInfo.Builder(getWidthOfView(content),
                        getHeightOfView(content) - 20, pageNumber).create();

                // create a new page from the PageInfo
                Page page = document.startPage(pageInfo);


                content.draw(page.getCanvas());

                // do final processing of the page
                document.finishPage(page);

                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
                String pdfName = "pdfdemo"
                        + sdf.format(Calendar.getInstance().getTime()) + ".pdf";

                String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                System.out.println("Path!!!!:" + path);
                File outputFile = new File(path, pdfName);
                if (!outputFile.exists()) {
                    System.out.println("Inside if for output file");
                    outputFile.mkdirs();
                }

               // File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + "PDFDemo_AndroidSRC" + "/" + pdfName);
               // Uri path = Uri.fromFile(pdfFile);
                //File outputFile = new File("/Internal Storage/PDFDemo_AndroidSRC/", pdfName);
                //String filePath = outputFile.getAbsolutePath();
                try {
                    outputFile.createNewFile();
                    OutputStream out = new FileOutputStream(outputFile);
                    document.writeTo(out);
                    document.close();
                    out.close();
                } catch (IOException e) {
                    System.out.println("outputfile error");
                    e.printStackTrace();
                }

                /*if(outputFile.isDirectory()) {

                    File[] file1 = outputFile.listFiles();
                    for (int i = 0; i < file1.length; i++) {

                        // Your List of Files
                        System.out.println("List:1 + " + String.valueOf(file1[i]));

                    }
                }*/

               // File file = new File(filePath);
// FileDescriptor for file, it allows you to close file when you are
// done with it
                /*System.out.println("OF:" + outputFile);
                ParcelFileDescriptor mFileDescriptor = null;
                try {
                    mFileDescriptor = ParcelFileDescriptor.open(outputFile,
                            ParcelFileDescriptor.MODE_READ_ONLY);
                } catch (FileNotFoundException e) {
                    System.out.println("File nto found ex");
                    e.printStackTrace();
                }
// PdfRenderer enables rendering a PDF document
                PdfRenderer mPdfRenderer = null;
                System.out.println("Mfiledescr:" + mFileDescriptor);
                try {
                    mPdfRenderer = new PdfRenderer(mFileDescriptor);
                } catch (IOException e) {
                    e.printStackTrace();
                }

// Open page with specified index
                PdfRenderer.Page mCurrentPage = mPdfRenderer.openPage(1);
                Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(),
                        mCurrentPage.getHeight(), Bitmap.Config.ARGB_8888);

// Pdf page is rendered on Bitmap
                mCurrentPage.render(bitmap, null, null,
                        PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
// Set rendered bitmap to ImageView (pdfView in my case)
                pdfView.setImageBitmap(bitmap);

                mCurrentPage.close();
                mPdfRenderer.close();
                try {
                    mFileDescriptor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
               /* builder.setView(dialogView)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {


                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });*/

                //dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                /*Button addSwimmer = (Button)findViewById(R.id.add_swimmer);
                addSwimmer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                EditText et = findViewById(R.id.firstName);*/
            }
        });
                /*try
                {
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(Environment.getExternalStorageDirectory() + "/hello.pdf"));
                    document.open();
                    document.add(new Paragraph("Hello World"));
                    document.close();
                    Log.d("OK", "done");
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                catch (DocumentException e)
                {
                    e.printStackTrace();
                }*/

               /* Uri path = Uri.fromFile(file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(path, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
      /*      }
        });*/


    }



    public void makeLanes(int a){
        for(int x = 0; x < a; x ++) {
            String temp = order.get(x);
            order.set(x, temp +" H" + x/5 + " L" + x%5);
        }
    }

    public void setHeatLane(List<String> cheese) {
        for (int a = 0; a < cheese.size(); a++) {
            int x = a%4 + 1;
            int y = a/4 + 1;
            String temp = cheese.get(a);
            cheese.set(a, temp + " H" + y + " L" + x);
        }
    }

    public HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        inputBundle = getArguments();
        System.out.println("Input:" + inputBundle.getString("Lanes"));
        numLanes = Integer.parseInt(inputBundle.getString("Lanes"));
        order = inputBundle.getStringArrayList("Order");
        orderPool = inputBundle.getString("OrderPool");
        type = inputBundle.getString("Type");
        event = inputBundle.getString("Event");
        //setHeatLane(order);
        for(int x = 0; x <order.size(); x ++)
            System.out.print(order.get(x) + ", ");
        //ArrayList<String> swimmers = new ArrayList<String>();
        /*swimmers.addAll(Arrays.asList("ryan ng", "sumer hajela", "bryan ku",
                "jason kim", "brandon fung", "michael cheng", "boris strots",
                "taesu yim", "nilay kundu", "cara lee", "mei matsumoto",
                "alice cheng", "lei otsuka", "casey tsai", "ivy li",
                "jane choi", "patricia saito", "neeti badve", "jinsu yim",
                "iris dong", "isabel lee", "sherry lin", "olivia candelaria",
                "jacqueleine liu", "lukas peng", "viraj shitole", "iris yuh"));*/

        if(type.equals("Regular")){
        if(orderPool.equals("Heats")){
        for(int x = 0; x < order.size(); x++){
            if(x%numLanes==0){
                System.out.println("XIS:" + x);
                List<String> heat = new ArrayList<String>();
                for(int a = 0; a < numLanes; a ++) {
                    int lane = a + 1;
                    try{heat.add(lane + " " + order.get(x + a));}
                    catch(Exception e){}
                }
//                System.out.println("NAMES:" + heat.get(0) + heat.get(1));
                int heatNum = x/numLanes + 1;
                System.out.println("heatNum:" + heatNum);
                expandableListDetail.put("Heat " + heatNum, heat);
            }
        }}
        else if(orderPool.equals("Lanes")) {
            boolean in = false;
            for (int a = 1; a <= numLanes; a++) {
                if (a == numLanes) {
                    in = true;
                }
                System.out.println("beforeA:" + a);
                List<String> lane = new ArrayList<String>();
                for (int x = 1; x <= order.size(); x++) {
                    if (in == true) {
                        if (x % numLanes == 0) {
                            System.out.println("XXXX:" + x + "A:" + a + "NumLanes:" + numLanes + "Order" + order.get(x - 1));
                            int heat = x / numLanes;
                            try {
                                lane.add(heat + " " + order.get(x - 1));
                            } catch (Exception e) {
                            }
                        }
                    } else if (x % numLanes == a) {
                        System.out.println("XXXX:" + x + "A:" + a + "NumLanes:" + numLanes + "Order" + order.get(x - 1));
                        int heat = x / numLanes + 1;
                        try {
                            lane.add(heat + " " + order.get(x - 1));
                        } catch (Exception e) {
                        }
                    }
                }
                expandableListDetail.put("Lane " + a, lane);
            }
        }

        }
        else if(type.equals("Meet")) {
            String [][] arr = circleSeeder(order, numLanes);
            if(orderPool.equals("Heats"))
                arr = transposeMatrix(arr);

            for(int x = 0; x < arr[0].length; x++){
                for(int y = 0; y < arr.length; y ++){
                    int sum = x + 1;
                    if(arr[y][x] == null){arr[y][x] = null;}
                    else{
                    arr[y][x] = sum + " "  +arr[y][x];}

                }
            }
            if(orderPool.equals("Lanes")){
                for(int a = 0; a<numLanes; a++) {
                    int lane3 = a + 1;
                    ArrayList<String> temp = new ArrayList<String>();
                    for(int e = 0; e < arr[a].length; e ++){
                        if(arr[a][e] != null)
                            temp.add(arr[a][e]);
                    }
                    expandableListDetail.put("Lane " + lane3, temp);
                }
            }
            else if(orderPool.equals("Heats")){
                for(int a = 0; a<arr.length; a++) {
                    int heat3 = a + 1;
                    ArrayList<String> temp = new ArrayList<String>();
                    for(int e = 0; e < arr[a].length; e ++){
                        if(arr[a][e] != null)
                            temp.add(arr[a][e]);
                    }
                    expandableListDetail.put("Heat " + heat3, temp);
                }
            }
           /* if(orderPool.equals("Heats")) {
                String[][] arr = new String[(order.size() / numLanes) + 1][numLanes];
                for(int x = 1; x <= order.size(); x ++){
                    arr[(x-1)/numLanes][(x-1)%numLanes] = order.get(x-1);
                }
                for(int a = 0; a < order.size()/numLanes  + 1; a++){
                    for(int b = 0; b < numLanes; b ++)
                    System.out.println("Array fix:" + ":" + numLanes + ":" + a  + b + ":" +arr[a][b]);
                }
                String [][] arr2 = transposeMatrix(arr);
                if(numLanes%2 == 1) {
                    for (int a = (numLanes / 2); a > 0; a++) {
                        ArrayList<String> e = new ArrayList<>(Arrays.asList(arr[a]));
                        expandableListDetail.put("Lane " + a, e);
                        if(a%2 == 1){a = a-2;}
                        else {a = a + 1;}
                    }
                }
            }*/


        }

        return expandableListDetail;
    }

    public static String[][] transposeMatrix(String [][] m){
        String[][] temp = new String[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }

    /**
     * Started 12-5-2018
     * Patricia Saito
     */

    /*{"jason kim", "sumer hajela", "ryan ng", "bryan ku", "jason kim"}
    {"nilay kundu", "boris strots", "michael cheng", "taesu yim", "cara lee"}
    {"casey tsai", "alice cheng", "mei matsumoto", "lei otsuka", "ivy li"}
    {"jinsu yim", "patricia saito", "jane choi", "neeti badve", "iris dong"}
    {"jacqueleine liu", "sherry lin", "isabel lee", "olivia candelaria", "lukas peng"}
    {null, "iris yuh", "viraj shitole", null, null}*/
    /**
     * Circle seeds a list of swimmers into a given number of lanes.
     * For an odd number of lanes:
     * 		Begin at the middle number, and alternate adding swimmers to
     * 		the lane on the right and left from the center.
     * For an even number of lanes:
     * 		Begin at the lower of the 2 middle values, and alternate
     * 		adding swimmers to the left, then right from the center.
     * Continue adding swimmers until the last lane (always numLanes) is
     * filled, and proceed to the next heat (rows) until no more swimmers
     * remain.
     * If the last heat contains less than 3 swimmers, swimmers from the
     * previous heat will be moved to the last heat so no heat contains
     * less than 3 swimmers.
     * @param swimmers ArrayList of swimmers ranked by time in an event.
     * @param numLanes Lanes available for seeding.
     * @return 2D array containing lane x heat for swimmers.
     */
    public String[][] circleSeeder(ArrayList<String> swimmers, int numLanes)
    {
        double numHeats = (double)swimmers.size()/numLanes;
        if(swimmers.size()%numLanes!=0)
            numHeats+=1;
        else if (swimmers.size()<=numLanes)
            numHeats = 1;
        String[][] heatLanes  = new String [numLanes][(int)numHeats];
        if(numLanes%2!=0) //odd lane case
        {
            for(int heatIndex = 0; heatIndex < (int)numHeats; heatIndex++)
            {
                int laneIndex = numLanes/2 + 1;
                int next = 0;
                while(heatLanes[numLanes-1][heatIndex]== null && swimmers.size()!=0)
                //as long as last lane is empty and swimmers still remain
                {
                    heatLanes[laneIndex-1][heatIndex] = swimmers.remove(0);
                    next = (Math.abs(next)+1);
                    if(next%2!=0)
                        next *= -1;
                    laneIndex += next;
                }
            }
            if(numLanes>3 && (int)numHeats>1)
            {
                int count = 0;
                for(int i=0; i<numLanes; i++)
                {
                    if(heatLanes[i][(int)numHeats-1] != null)
                    {
                        count++;
                    }
                }
                if(count==1)
                {
                    heatLanes[numLanes/2+1][(int)numHeats-1] = heatLanes[numLanes/2][(int)numHeats-1];
                    heatLanes[numLanes/2][(int)numHeats-1] = heatLanes[0][(int)numHeats-2];
                    heatLanes[0][(int)numHeats-2] = null;
                    heatLanes[numLanes/2-1][(int)numHeats-1] = heatLanes[numLanes-1][(int)numHeats-2];
                    heatLanes[numLanes-1][(int)numHeats-2] = null;
                }
                // 0 1 2 3 4
                // 1 2 3 4 5
                //   2 1 3
                else if(count==2)
                {
                    heatLanes[numLanes/2+1][(int)numHeats-1] = heatLanes[numLanes/2-1][(int)numHeats-1];
                    heatLanes[numLanes/2-1][(int)numHeats-1] = heatLanes[numLanes/2][(int)numHeats-1];
                    heatLanes[numLanes/2][(int)numHeats-1] = heatLanes[numLanes-1][(int)numHeats-2];
                    heatLanes[numLanes-1][(int)numHeats-2] = null;
                }
            }
        }
        else if(numLanes%2==0) //even lane case
        {
            for(int heatIndex = 0; heatIndex < (int)numHeats; heatIndex++)
            {
                int laneIndex = numLanes/2;
                int next = 0;
                while(heatLanes[numLanes-1][heatIndex]== null && swimmers.size()!=0)
                //as long as last lane is empty and swimmers still remain
                {
                    heatLanes[laneIndex-1][heatIndex] = swimmers.remove(0);
                    next = (Math.abs(next)+1);
                    if(next%2==0)
                        next *= -1;
                    laneIndex += next;
                }
            }
            if(numLanes>3 && (int)numHeats>1)
            {
                int count = 0;
                for(int i=0; i<numLanes; i++)
                {
                    if(heatLanes[i][(int)numHeats-1] != null)
                    {
                        count++;
                    }
                }
                if(count==1)
                {
                    heatLanes[numLanes/2-2][(int)numHeats-1] = heatLanes[numLanes/2-1][(int)numHeats-1];
                    heatLanes[numLanes/2-1][(int)numHeats-1] = heatLanes[0][(int)numHeats-2];
                    heatLanes[0][(int)numHeats-2] = null;
                    heatLanes[numLanes/2][(int)numHeats-1] = heatLanes[numLanes-1][(int)numHeats-2];
                    heatLanes[numLanes-1][(int)numHeats-2] = null;
                }
                // 0 1 2 3 4 5
                // 1 2 3 4 5 6
                //   3 1 2
                else if(count==2)
                {
                    heatLanes[numLanes/2-2][(int)numHeats-1] = heatLanes[numLanes/2][(int)numHeats-1];
                    heatLanes[numLanes/2][(int)numHeats-1] = heatLanes[numLanes/2-1][(int)numHeats-1];
                    heatLanes[numLanes/2-1][(int)numHeats-1] = heatLanes[numLanes-1][(int)numHeats-2];
                    heatLanes[numLanes-1][(int)numHeats-2] = null;
                }
            }
        }
        return heatLanes;
    }

    public void printHeatLanes(String[][]matrix)
    {
        System.out.print("         ");
        for(int h = 0; h< matrix[0].length; h++)
        {
            System.out.printf("  %-20s","Heat " + (h+1) + ":");
        }
        System.out.println();
        for(int lane = 0; lane < matrix.length; lane++)
        {
            System.out.printf("Lane %2d: ", lane+1);
            for(int heat = 0; heat < matrix[0].length; heat++)
            {
                if(matrix[lane][heat] != null)
                    System.out.printf("  %-20s",matrix[lane][heat]);
                else System.out.printf("  %-20s","<empty>");
            }
            System.out.println();
        }
    }

    private int getHeightOfView(View contentview) {
        contentview.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        //contentview.getMeasuredWidth();
        return contentview.getMeasuredHeight();
    }

    private int getWidthOfView(View contentview) {
        contentview.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        //contentview.getMeasuredWidth();
        return contentview.getMeasuredWidth();
    }

    public void createandDisplayPdf(String text) {

        Document doc = new Document();
        File file = null;
        String filePath = "";
        try {
            //String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";

            //File dir = new File(path);
           // if(!dir.exists())
              //  dir.mkdirs();

            filePath = getActivity().getFilesDir().getPath().toString() + "/pdfFile.pdf";
            file = new File(filePath);
            //file = new File("mypdffile.pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            Paragraph p1 = new Paragraph(text);
            Font paraFont= new Font(Font.FontFamily.COURIER);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setFont(paraFont);

            //add paragraph to document
            doc.add(p1);
            /*System.out.println("Exists or not: previous method" + file.exists());
            Uri uri = Uri.fromFile(file);
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
            StorageReference path1 = mStorageRef.child("heats");
            UploadTask uploadTask = path1.putFile(uri);
*/
            // Register observers to listen for when the download is done or if it fails
          /*  uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Log.d("uploadFail", "" + exception);

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    //sendNotification("upload backup", 1);

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    Log.d("downloadUrl", "" + downloadUrl);
                }
            });*/

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally {
            doc.close();
        }
        System.out.println("Exists or not later : previous method" + file.exists());
        File pdfFile = new File(filePath);
        Uri path = Uri.fromFile(pdfFile);
        uploadFile(path);
       // StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
       // StrictMode.setVmPolicy(builder.build());
       // Intent downloadIntent = new Intent(Intent.ACTION_VIEW);
       // downloadIntent.setData(Uri.parse(path.toString()));
       // startActivity(downloadIntent);
        //viewPdf("mypdffile.pdf", "PDF");
        System.out.println("viewing pdf mode");

    }

    // Method for opening a pdf file
    private void viewPdf(String file, String directory) {

        //File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + directory + "/" + file);
        String filePath = getActivity().getFilesDir().getPath().toString() + "/pdfFile.pdf";
        File pdfFile = new File(filePath);


        Uri path = Uri.fromFile(pdfFile);
        //Uri path = FileProvider.getUriForFile(getActivity(), "my.authority.fileprovider", pdfFile);
        String mimeType= MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(filePath));
       //try{
        System.out.println("Absolute Path:" + pdfFile.getAbsolutePath());//}
      //  catch(IOException e){}
        //System.out.println("Exists or not:" + pdfFile.exists());
        //Uri path = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider",pdfFile);
        // Setting the intent for pdf reader
        //System.out.println("Mim type:" + mimeType);

        //final Intent viewIntent = new Intent();
        //viewIntent.setAction(Intent.ACTION_VIEW);


        //viewIntent.setDataAndType(fileUri, "application/pdf");

        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);

        //pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pdfIntent.setDataAndType(path ,mimeType);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        startActivity(pdfIntent);
        /*try
        {
            startActivity(pdfIntent ); }
        catch (ActivityNotFoundException e)
        {
            System.out.println("didn't work");
            /*Toast.makeText(EmptyBlindDocumentShow.this,"No
                    Application available to viewPDF",
                    Toast.LENGTH_SHORT).show();
        }*/

       /* Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(path, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        startActivity(intent);*/


       /* Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            System.out.println("Can't read pdf file");
        }*/
    }


    public void pdf(){
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

        //Uri file = Uri.fromFile(new File("2018JulySectEvent #1 Finals.txt"));
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
    final StorageReference riversRef = mStorageRef.child(eventPath);
        //final StorageReference riversRef = mStorageRef.child(user.getUid()).child("testing/test1.pdf");

        System.out.println("Reference value:" + riversRef.toString());
        try{
            final File localFile = File.createTempFile("results", "pdf");
            riversRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            // ...
                            //String url = taskSnapshot.getDownloadUrl().toString();
                            System.out.println("Success!");
                            //Log.e("firebase file is here ",";local tem file created  created " +localFile.toString());

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle failed download
                            // ...
                            System.out.println("Fail!");
                        }
                    });

            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //System.out.println(riversRef.getDownloadUrl() + " is the url");
                    //DownloadManager.Request r = new DownloadManager.Request(uri);
                    //Intent temp = new Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString()));

                    Intent downloadIntent = new Intent(Intent.ACTION_VIEW);
                    downloadIntent.setData(Uri.parse(uri.toString()));
                    startActivity(downloadIntent);

                    /*r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "fileName");
                    r.allowScanningByMediaScanner();
                    r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(r);*/
                    // Got the download URL for 'users/me/profile.png'
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }
        catch(Exception e){}
    }

    private void uploadFile(Uri filePath) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            eventPath = user.getUid() + "//" + event + ".pdf";
            final StorageReference riversRef = storageReference.child(eventPath);
            //final StorageReference riversRef = storageReference.child(user.getUid()).child("testing/test1.pdf");
            System.out.println("First storage reference:" + riversRef);

            //StorageReference riversRef = storageReference.child("testing/test.pdf");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying a success toast
                            Toast.makeText(getContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                            pdf();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }


}
