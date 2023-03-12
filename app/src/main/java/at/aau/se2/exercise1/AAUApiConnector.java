package at.aau.se2.exercise1;

import android.os.Handler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Objects;
import java.util.function.Consumer;

public class AAUApiConnector {
    private static final String ENDPOINT_HOST = "se2-isys.aau.at";
    private static final int ENDPOINT_PORT = 53212;
    private static final int SOCKET_TIMEOUT_MS = 5000;

    public void getStudyTime(
            String matNr,
            Handler responseHandler,
            Consumer<String> successCallback,
            Consumer<Exception> failureCallback
    ) {
        Objects.requireNonNull(matNr);
        Objects.requireNonNull(responseHandler);
        Objects.requireNonNull(successCallback);
        Objects.requireNonNull(failureCallback);

        Thread t = new Thread(() -> internalGetStudyTime(matNr, responseHandler, successCallback, failureCallback));
        t.start();
    }

    private void internalGetStudyTime(
            String message,
            Handler resultHandler,
            Consumer<String> successCallback,
            Consumer<Exception> failureCallback
    ) {
        try (Socket socket = new Socket(ENDPOINT_HOST, ENDPOINT_PORT)) {
            socket.setSoTimeout(SOCKET_TIMEOUT_MS);
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            output.writeBytes(message + "\n");
            output.flush();

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String result = input.readLine();

            resultHandler.post(() -> successCallback.accept(result));
        } catch (IOException ex) {
            resultHandler.post(() -> failureCallback.accept(ex));
        }
    }
}
