package com.slac.rss;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.slac.slac.VerticalSpace;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Ardil on 28/04/17.
 */

public class ReadRss extends AsyncTask<Void, Void, Void> {

    // ======= Fields ===========
    Context context;
    ProgressDialog progressDialog;
    //String address = "http://www.sciencemag.org/rss/news_current.xml";
    // String address ="http://rss.nytimes.com/services/xml/rss/nyt/World.xml";
    String address = "http://www.luganolac.ch/getRss.asp";
    ArrayList<FeedItem> feedItems;
    RecyclerView recyclerView;
    URL url;


    public ReadRss(Context context, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.context = context;

        final Handler handle = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progressDialog.incrementProgressBy(2); // Incremented By Value 2
            }
        };

        progressDialog = new ProgressDialog(context);
        // Setting Title
        progressDialog.setTitle("LAC Events");
        // Setting Message
        progressDialog.setMessage("Loading...");
        // Progress Dialog Style Horizontal
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // Progress Dialog Max Value
        progressDialog.setMax(100);
        // Fetching max value
        progressDialog.getMax();
        // Fetching current progress
        progressDialog.getProgress();
        // Incremented By Value 10
        progressDialog.incrementProgressBy(10);
        // Display Progress Dialog
        progressDialog.show();

        progressDialog.setCancelable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (progressDialog.getProgress() <= progressDialog.getMax()) {
                        Thread.sleep(10);
                        handle.sendMessage(handle.obtainMessage());
                        if (progressDialog.getProgress() == progressDialog.getMax()) {
                            progressDialog.dismiss();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    } //endReadRss

//    =================== METHODS =======================

    private void ProcessXml(Document data) {
        if (data != null) {
            feedItems = new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++) {
                Node currentchild = items.item(i);
                if (currentchild.getNodeName().equalsIgnoreCase("item")) {
                    FeedItem item = new FeedItem();
                    NodeList itemchilds = currentchild.getChildNodes();
                    for (int j = 0; j < itemchilds.getLength(); j++) {
                        Node current = itemchilds.item(j);
                        if (current.getNodeName().equalsIgnoreCase("title")) {
                            item.setTitle(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("description")) {
                            item.setDescription(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("pubDate")) {
                            item.setPubDate(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("link")) {
                            item.setLink(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("media:content")) {
                            String url = current.getAttributes().item(0).getTextContent();
                            item.setThumbnailUrl(url);
                        }


                    }
                    feedItems.add(item);

                }
            }
        }
    }

    public Document GetData() {
        try {
            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();

            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }//endOnPreExecute

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        MyAdapter adapter = new MyAdapter(context, feedItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new VerticalSpace(50));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected Void doInBackground(Void... params) {
        ProcessXml(GetData());
        return null;
    }

}
