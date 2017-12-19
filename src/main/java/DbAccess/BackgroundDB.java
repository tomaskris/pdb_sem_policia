package main.java.DbAccess;

import main.java.Entities.MyDataClass;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BackgroundDB {
    public static void BackgroundDBAccess(Execution execution, Callback callback) {
        new SwingWorker<Boolean, RuntimeException>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                try {
                    execution.execution();
                    return true;
                } catch (RuntimeException e) {
                    publish(e);
                }
                return false;
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        callback.callback();
                    }
                } catch (InterruptedException | ExecutionException ex) {

                }
            }

            @Override
            protected void process(List<RuntimeException> chunks) {
                JOptionPane.showMessageDialog(null, chunks.get(0).getMessage(), "ERROR  ", JOptionPane.ERROR_MESSAGE);
            }
        }.execute();
    }

}
