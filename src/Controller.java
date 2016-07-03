package grapics;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class Controller implements Initializable {
    @FXML
    Label outNameLabel;
    @FXML
    TextField fileField;
    @FXML
    TextArea outputTextArea;
    @FXML
    Button startButton;
    @FXML Button loadButton;
    int[] inpInt = null;
    int[] readyArr = null;
    int size = 0;

    @FXML
    public void loadButtonClicked(ActionEvent event) {
        fileOk();
    }

    @FXML
    public void startButtonClicked(ActionEvent actionEvent) {
        System.out.println("start");
        int j;
        for(j=0; j<inpInt.length;j++) if(inpInt[j]==0) break;
        readyArr = new int[j];
        for(int i=0; i<j; i++) readyArr[i] = inpInt[i];
        GraphFrame frm = new GraphFrame(readyArr);
        frm.setVisible(true);
    }


    @FXML
    public void fileOk() {
        int i = 0;
        String buff, buff2 = new String();
        DataInputStream input = null;
        try (DataInputStream streambuff = new DataInputStream(new FileInputStream(fileField.getText()))) {
            size = streambuff.available();
        } catch (IOException ex2) {
            outputTextArea.setText("Wrong path");
            outputTextArea.setVisible(true);
            return;
        }
        inpInt = new int[size];
        try {
            input = new DataInputStream(new FileInputStream(fileField.getText()));

            while ((buff = input.readLine()) != null) {
                StringTokenizer stoken = new StringTokenizer(buff);
                while (stoken.hasMoreTokens()) {
                    int ibuff = Integer.parseInt(stoken.nextToken());
                    inpInt[i] = ibuff;
                    buff2 += Integer.toString(ibuff) + " ";
                    if (i % 3 == 1) buff2 += "\n";
                    System.out.println(inpInt[i]);
                    i++;
                }
            }
        } catch (IOException ex) {

        }

        outputTextArea.setText(buff2);
        outputTextArea.setVisible(true);
        startButton.setDisable(false);
        //drawButton.setDisable(false);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startButton.setDisable(true);
        fileField.setText("graph.txt");
    }
}
