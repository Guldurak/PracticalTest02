package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientThread extends Thread {

    private final String address;
    private final int port;
//    private final String city;
    private final String operator_1;
    private final String operator_2;
    private final TextView weatherForecastTextView;

    private Socket socket;

    public ClientThread(String address, int port, String operator_1, String operator_2, TextView weatherForecastTextView) {
        this.address = address;
        this.port = port;
        this.operator_1 = operator_1;
        this.operator_2 = operator_2;
        this.weatherForecastTextView = weatherForecastTextView;
    }

    @Override
    public void run() {
        try {
            // tries to establish a socket connection to the server
            socket = new Socket(address, port);

            // gets the reader and writer for the socket
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);

            // sends the operators and information type to the server
            printWriter.println(operator_1);
            printWriter.flush();
            printWriter.println(operator_2);
            printWriter.flush();
            String weatherInformation;

            // reads the weather information from the server
            while ((weatherInformation = bufferedReader.readLine()) != null) {
                final String finalizedWeateherInformation = weatherInformation;

                // updates the UI with the weather information. This is done using postt() method to ensure it is executed on UI thread
                weatherForecastTextView.post(() -> weatherForecastTextView.setText(finalizedWeateherInformation));
            }
        } // if an exception occurs, it is logged
        catch (IOException ioException) {
            Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        } finally {
            if (socket != null) {
                try {
                    // closes the socket regardless of errors or not
                    socket.close();
                } catch (IOException ioException) {
                    Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
                    if (Constants.DEBUG) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
    }

}