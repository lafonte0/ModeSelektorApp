package it.open.modeselektor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ReceiverOnBoot extends BroadcastReceiver {

    final static String ApplyOnBoot_Path = "/sdcard/ModeSelektor/ApplyOnBoot.txt";
    final static String DefaultMode_Path = "/sdcard/ModeSelektor/DefaultMode.txt";
    final static String AutoUltraBattery_Path = "/sdcard/ModeSelektor/AutoUltraBattery.txt";
    String Read;


    @Override
    public void onReceive(Context context, Intent intent) {

        //Controlla se Apply on boot è attivo se si lo esegue

        Read(ApplyOnBoot_Path);
        if (Read.equals("Y")){
            Read(DefaultMode_Path);
            try {
                Process a = Runtime.getRuntime().exec("su -c sh /sdcard/ModeSelektor/" + Read);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Controlla se Auto Ultra Battery è attivo se si lo esegue

        Read(AutoUltraBattery_Path);
        if (Read.equals("Y")){
            context.startService(new Intent(context, ModeSelektorService.class));
        }
    }

    public void Read(String Path){
        try {
            File file = new File(Path);
            InputStream in = new FileInputStream(file);
            if (in != null) {
                InputStreamReader tmp=new InputStreamReader(in);
                BufferedReader reader=new BufferedReader(tmp);
                String str;
                StringBuilder buf=new StringBuilder();
                while ((str = reader.readLine()) != null) {
                    buf.append(str);
                }
                in.close();
                Read = (buf.toString());
            }
        }
        catch (java.io.FileNotFoundException e) {
        }
        catch (Throwable t) {
        }
    }
}

