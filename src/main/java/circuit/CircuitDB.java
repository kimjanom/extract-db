package circuit;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import netscape.javascript.JSObject;
import org.bson.Document;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import static com.mongodb.client.model.Filters.*;

import org.json.JSONException;
import org.json.JSONObject;


public class CircuitDB {
    private static String path = "C:\\Program Files (Library)\\data\\data4.txt";
    public void cirDb(MongoDatabase mongoDatabase) throws IOException {
        int startY = 2020;
        int startM = 1;
        int startD = 1;
        Calendar end = new GregorianCalendar(2020,11,31);
        Date endD = end.getTime();
        new CircuitDB().next(mongoDatabase,startY,startM,startD,endD);

    }
    public void next(MongoDatabase mongoDatabase, int Y, int M, int D,Date end) throws IOException, JSONException {
        boolean a= true;
        Calendar cal = new GregorianCalendar(2020,0,1);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        File file = new File(path);
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write("_id"+"\t");
        bw.write("sourceUrl"+"\t");
        bw.write("collectDatetime"+"\t");
        bw.write("content"+"\t");
        bw.write("writeName"+"\n");
        while(a) {
            Date date = cal.getTime();
            String start =dateFormat.format(date);

            MongoCollection<Document> connect =mongoDatabase.getCollection("uninan_cloud_"+start);
            MongoCursor<Document> cursor=connect.find(eq("jobId",100101)).projection(Projections.include("_id","content","sourceUrl","collectDatetime","writeName")).iterator();
            while(true) {
                if (cursor.hasNext()) {
                    try {
                        JSONObject jsObject = new JSONObject(cursor.next().toJson());
                        System.out.println("제이슨:" + jsObject);
                        System.out.println("시작:" + start);
                        jsObject.get("content");
                        System.out.println("content:" + jsObject.get("content"));
                        System.out.println("content:" + String.valueOf(jsObject.get("content")));
                        if(String.valueOf(jsObject.get("content")) =="" || String.valueOf(jsObject.get("content")) =="null"||jsObject.get("content").equals("")||jsObject.get("content").equals("null")){
                            throw new JSONException("null content");
                        }
                        jsObject.get("sourceUrl");
                        jsObject.get("collectDatetime");
                        bw.write(String.valueOf(jsObject.getJSONObject("_id").get("$oid")) + "\t");
                        bw.write(String.valueOf(jsObject.get("sourceUrl")) + "\t");
                        bw.write(String.valueOf(jsObject.get("collectDatetime")) + "\t");
                        bw.write(String.valueOf(jsObject.get("content")).replace("\t"," ") + "\t");
                        bw.write(String.valueOf(jsObject.get("writeName")) + '\n');
                    }catch (JSONException e){
//                        bw.write('\n');
                        System.out.println(e);
                    }
                }else{
                    break;
                }
            }
            if(date.equals(end)){
                a= false;
            }
//            System.out.println(start);
            cal.add(Calendar.DATE,1);
        }
    }
}
