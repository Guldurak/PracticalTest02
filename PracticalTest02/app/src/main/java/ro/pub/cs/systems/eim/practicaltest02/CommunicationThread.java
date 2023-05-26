package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;


public class CommunicationThread extends Thread {

    private final ServerThread serverThread;
    private final Socket socket;

    // Constructor of the thread, which takes a ServerThread and a Socket as parameters
    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    // run() method: The run method is the entry point for the thread when it starts executing.
    // It's responsible for reading data from the client, interacting with the server,
    // and sending a response back to the client.
    @Override
    public void run() {
        // It first checks whether the socket is null, and if so, it logs an error and returns.
        if (socket == null) {
            Log.e(Constants.TAG, "[COMMUNICATION THREAD] Socket is null!");
            return;
        }
        try {
            // Create BufferedReader and PrintWriter instances for reading from and writing to the socket
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            Log.i(Constants.TAG, "[COMMUNICATION THREAD] Waiting for parameters from client (operator_1 / information type!");
            Log.i(Constants.TAG, "[COMMUNICATION THREAD] Waiting for parameters from client (operator_2 / information type!");

            // Read the operator_1 and informationType values sent by the client
            String operator_1 = bufferedReader.readLine();
            String operator_2 = bufferedReader.readLine();

            String informationType = bufferedReader.readLine();
            if (operator_1 == null || operator_1.isEmpty() || informationType == null || informationType.isEmpty()) {
                Log.e(Constants.TAG, "[COMMUNICATION THREAD] Error receiving parameters from client (operator_1 / information type!");
                return;
            }

            if (operator_2 == null || operator_2.isEmpty() || informationType == null || informationType.isEmpty()) {
                Log.e(Constants.TAG, "[COMMUNICATION THREAD] Error receiving parameters from client (operator_2 / information type!");
                return;
            }

            Calculus calculus = null;

            // Send the information back to the client
            Integer result;
            switch (informationType) {
                case Constants.ADD:
                    result = calculus.getAdd();
                    break;
                case Constants.MUL:
                    result = calculus.getMul();
                    break;
                default:
                    Log.i(Constants.TAG, "[COMMUNICATION THREAD] Wrong information type (all / add / mul)!");
            }
            // Send the result back to the client
            printWriter.println(result);
            printWriter.flush();
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        } finally {
            try {
                socket.close();
            } catch (IOException ioException) {
                Log.e(Constants.TAG, "[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());
                if (Constants.DEBUG) {
                    ioException.printStackTrace();
                }
            }
        }
    }

}