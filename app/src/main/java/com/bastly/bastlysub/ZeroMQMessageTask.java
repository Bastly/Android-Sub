package com.bastly.bastlysub;

/**
 * Created by xa on 1/26/15.
 */
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import org.zeromq.ZMQ;

public class ZeroMQMessageTask extends AsyncTask<String, Void, String> {
    //private final Handler uiThreadHandler;

    //public ZeroMQMessageTask(Handler uiThreadHandler) {
    //    this.uiThreadHandler = uiThreadHandler;
    //}

    @Override
    protected String doInBackground(String... params) {
        ZMQ.Context context = ZMQ.context(1);

        ZMQ.Socket socket = context.socket(ZMQ.SUB);
        socket.connect("tcp://54.154.194.204:3000");

         String filter = "kittycats";
        String filter2 = "";
        socket.subscribe(filter.getBytes());


        int update_nbr;
        long total_temp = 0;
        String msg = String.valueOf(socket.recvStr().substring(9));
        Log.d("zero", msg);
        int previous = Integer.parseInt(msg);;
        int now = 0;
        while(true){
            //  Use trim to remove the tailing '0' character
            //Log.d("zero", "waiting");
            msg = socket.recvStr();
            now = Integer.parseInt(msg.substring(9));

            if(previous + 1 != now ){
                Log.d("zero", msg);
                Log.d("zero", "lost " + (now - previous)+ " packages");
            }
            previous = now;

            //Log.d("zero", msg);



        }
        //socket.close();
        //context.term();

        /*


        //  Socket to talk to server
        System.out.println("Connecting to hello world server…");

        ZMQ.Socket requester = context.socket(ZMQ.REQ);
        requester.connect("tcp://54.154.194.204:3001");

        for (int requestNbr = 0; requestNbr != 10; requestNbr++) {
            String request = "Hello";
            Log.d("zero", "Sending Hello " + requestNbr);
            requester.send(request.getBytes(), 0);

            byte[] reply = requester.recv(0);
            Log.d("zero", new String(reply) + " " + requestNbr);
        }
        requester.close();
        context.term();

        */




        //return "";
    }

    @Override
    protected void onPostExecute(String result) {
        //uiThreadHandler.sendMessage(Util.bundledMessage(uiThreadHandler, result));
    }
}