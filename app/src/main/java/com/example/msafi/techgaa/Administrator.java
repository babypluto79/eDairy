package com.example.msafi.techgaa;


import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Administrator extends Fragment {

    Button button, generate;
    Context context;
    Calendar calendar;
    FirebaseDatabase database;
    DatabaseReference reference, withdrawals;
    ArrayAdapter<String> adapter;
    ArrayList<String> list, withList;
    ListView listView;
    TextView textView;
    String[] item;
    private Canvas canvas;
    String report;

    public Administrator() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_administrator, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        context = getContext();
        if (view != null) {
            listView = view.findViewById(R.id.milk_report);
            textView = view.findViewById(R.id.noRecords);
            list = new ArrayList<>();
            withList = new ArrayList<>();
            generate = view.findViewById(R.id.generate_report);

            button = view.findViewById(R.id.Price);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context, Price.class));
                }
            });

            //getting an instance to the Firebase Database
            database = FirebaseDatabase.getInstance();
            reference = database.getReference("Milk");
            withdrawals = database.getReference("Her");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
            Date date = new Date();
            calendar = Calendar.getInstance();
            String currentDate = dateFormat.format(date);


            //query time!!!
            Query query = reference.orderByChild("Date").equalTo(currentDate);
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                        Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                        while (iterator.hasNext()) {
                            DataSnapshot next = (DataSnapshot) iterator.next();
                            String maziwa = (String) next.child("Quantity").getValue();
                            String collector = (String) next.child("Collector").getValue();
                            String date = (String) next.child("Date").getValue();
                            String time = (String) next.child("Time").getValue();
                            String email = (String) next.child("email").getValue();
                            report = maziwa + "\t" + "Litres" + "\t" + "Recorded on" + "\t" + date + "\t" + "At" + "\t" + time + "\t" + "By" +
                                    "\t" + collector + "\t" + "For" + "\t" + email;
                            list.add(report);
                            adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, list);
                            listView.setAdapter(adapter);
                            //converting the arraylist to an array
                            item = list.toArray(new String[list.size()]);
                            generate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   printPdf();
                                }
                            });
                        }
                    } else {
                        textView.setText("No milk records today");

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            query.addValueEventListener(valueEventListener);
            Query query1 = withdrawals.orderByChild("date").equalTo(currentDate);
            ValueEventListener valueEventListener1 = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                        Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                        while (iterator.hasNext()) {
                            DataSnapshot next = (DataSnapshot) iterator.next();
                            String amount = (String) next.child("Amount").getValue();
                            String date = (String) next.child("date").getValue();
                            String email = (String) next.child("email").getValue();

                            String combo = amount + "\t" + "was given to" + "\t" + email + "\t" + "on" + date;
                            withList.add(combo);


                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
        }


    }
    public void printPdf(){
        try {
            String path =  Environment.getExternalStorageDirectory().getPath() + "/eDairy/Report" + ".pdf";
            File pdfFolder = new File(path);
            if (!pdfFolder.exists()) {
                pdfFolder.mkdir();
                Toast.makeText(context, "pdf created!!!", Toast.LENGTH_LONG).show();


            }
            Date date = new Date();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
            File myfile = new File(pdfFolder + timestamp + ".pdf");

            OutputStream output = new FileOutputStream(myfile);
            Document document = new Document();

            Rectangle pagesize = new Rectangle(216f, 720f);
            Document document1 = new Document(pagesize,20f, 40f, 108f, 180f);


            PdfWriter pdfWriter =   PdfWriter.getInstance(document1,output);
            pdfWriter.setSpaceCharRatio(PdfWriter.NEWLINE);
            pdfWriter.setSpaceCharRatio(PdfWriter.SPACE_CHAR_RATIO_DEFAULT);

            document1.open();
            com.itextpdf.text.List list1 = new com.itextpdf.text.List();
            com.itextpdf.text.List list2 = new com.itextpdf.text.List();

            for(String item: list){
                list1.add(item);

            }
            for(String with: withList){
                list2.add(with);
            }
            document1.add(list1);
            document1.add(list2);



            document1.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch(DocumentException e){
            e.printStackTrace();
        }


    }


}
