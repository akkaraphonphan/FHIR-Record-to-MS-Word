package swing;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.net.*;

import org.json.*;
import org.apache.commons.math3.util.Pair;
import java.awt.Component;
import java.util.HashMap;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import swing.JsonReader;


public class GUI {
    private JButton runButton;
    private JButton openFileButton;
    private JTextField textField1;
    private JPanel rootPanel;
    private File filePath;
    private JFrame frame = new JFrame("GUI");
    Patient patient;
    private ArrayList<Pair<String, String>> macros = new ArrayList<Pair<String, String>>();

    private void setUpMacros() {
        macros.clear();
        if (patient == null) {
            return;
        }
        macros.add(new Pair<>(patient.versionID, "<#VERSIONID#>") );
        macros.add(new Pair<>(patient.lastUpdated, "<#LASTUPDATED#>") );
        macros.add(new Pair<>(patient.patientID, "<#PATIENTID#>") );
        macros.add(new Pair<>(patient.prefixName, "<#PREFIXNAME#>") );
        macros.add(new Pair<>(patient.givenName, "<#GIVENNAME#>") );
        macros.add(new Pair<>(patient.familyName, "<#FAMILYNAME#>") );
        macros.add(new Pair<>(patient.gender, "<#GENDER#>") );
        macros.add(new Pair<>(patient.birthDate, "<#BIRTHDATE#>") );
        macros.add(new Pair<>(patient.maritalStatus, "<#MARITALSTATUS#>") );
        macros.add(new Pair<>(patient.socialSecurityNumber, "<#SOCIALSECURITYNUMBER#>") );
        macros.add(new Pair<>(patient.driverLicense, "<#DRIVERLICENSE#>") );
        macros.add(new Pair<>(patient.phoneType, "<#PHONETYPE#>") );
        macros.add(new Pair<>(patient.phone, "<#PHONE#>") );
        macros.add(new Pair<>(patient.street, "<#STREET#>") );
        macros.add(new Pair<>(patient.city, "<#CITY#>") );
        macros.add(new Pair<>(patient.state, "<#STATE#>") );
        macros.add(new Pair<>(patient.postalCode, "<#POSTALCODE#>") );
        macros.add(new Pair<>(patient.country, "<#COUNTRY#>") );
    }

    private boolean checkIfDocx(String name) {
        if (name.length() < 5) {
            return false;
        }

        String fileExtension = name.substring(name.length() - 5);
        return fileExtension.equals(".docx");
    }

    private void getPatientDetails() {
        JTextField component = textField1;
        String patientID = component.getText();
        URL url;
        try {
            String urlString = "http://localhost:5000/api/Patient/" + patientID;
            System.out.println(urlString);
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException");
            return;
        }
        HttpURLConnection con;
        try {
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            System.out.println("IOException2");
            return;
        }
        try {
            con.setRequestMethod("GET");
        } catch (ProtocolException e) {
            System.out.println("ProtocolException");
            return;
        }

        JSONTokener tokener;
        try {
            tokener = new JSONTokener(url.openStream());
        } catch (IOException e) {
            System.out.println("IOException1");
            System.out.println(e.getMessage());
            return;
        }
        JSONObject root = new JSONObject(tokener);
        System.out.println(root);
        patient = new Patient(root);
        System.out.println(root);
        setUpMacros();
    }

    private void replaceMacro(String replacement, String macro, XWPFRun run) {
        String text = run.getText(0);
        if (text != null && text.contains(macro)) {
            text = text.replace(macro, replacement);
            run.setText(text, 0);
        }
    }

    private void updateRun(XWPFRun run) {
        System.out.println("updaterun: " + run.getText(0));
        for (Pair<String, String> macro : macros) {
            replaceMacro(macro.getFirst(), macro.getSecond(), run);
        }
        System.out.println("updaterun 2: " + run.getText(0));
    }

    private void updateForm(File document) {
        // Based on answer by Gagravarr and SysHex at https://stackoverflow.com/questions/22268898/replacing-a-text-in-apache-poi-xwpf
        // Sometimes text can be separated by MS Word, the macro text could be split up in different runs, ensure macros are written all at once
        getPatientDetails();
        XWPFDocument doc;
        try {
            doc = new XWPFDocument(OPCPackage.open(document));
        } catch (InvalidFormatException e) {
            System.out.println("invalid format");
            return;
        } catch (IOException e) {
            System.out.println("ioexception");
            return;
        }
        for (XWPFParagraph p : doc.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    updateRun(r);
                }
            }
        }
        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        for (XWPFRun r : p.getRuns()) {
                            updateRun(r);
                        }
                    }
                }
            }
        }

        try {
            String editedFileName = document.getPath();
            editedFileName = editedFileName.substring(0, editedFileName.length() - 5) + "_edited.docx";
            doc.write(new FileOutputStream(editedFileName));
        } catch (IOException e) {
            System.out.println("ioexception write");
            return;
        }
    }


    public GUI() {
        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser open = new JFileChooser();
                int returnValue = open.showOpenDialog(openFileButton);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    filePath = open.getSelectedFile();
                    System.out.println(filePath.getName());
                } else {
                    System.out.println("cancelled");
                }
            }
        });
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (filePath == null) {
                    System.out.println("no file specified");
                    return;
                }

                if (!checkIfDocx(filePath.getName())) {
                    System.out.println("not a .docx file");
                    return;
                }

                updateForm(filePath);
                System.out.println("document edited");

            }
        });
        textField1.addComponentListener(new ComponentAdapter() {
        });
    }


    public static void main(String[] args) {
        GUI gui = new GUI();
        JsonReader reader = new JsonReader();
        gui.frame.setContentPane(gui.rootPanel);
        gui.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.frame.pack();
        gui.frame.setVisible(true);
//        gui.apiRequest();
    }
}
