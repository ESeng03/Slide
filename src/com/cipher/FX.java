package com.cipher;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class FX extends Application {
    private TableView<HistoryItem> tblHistory;
    private Button btnCipher;
    private Button btnDecipher;
    private Button apply;
    private Label lblCipher;
    private Label lblResult;
    private Label lblDecipher;
    private Scene scene;
    private Scene preferencesScene;
    private Stage stage;
    private TextField txtDecipher;
    private TextField txtResult;
    private TextField txtCipher;
    private RadioButton rdoDark;
    private RadioButton rdoLight;
    private List<HistoryItem> history = new ArrayList<>();
    private Preferences prefs;

    public static void main(String[] args) {
        System.out.println("Starting Program...");
        launch(args);
        System.out.println("Program Terminated!");
    }

    @Override
    public void start(Stage primaryStage) {
        // Added Taskbar Icon
        try {
            java.awt.Image dockIconImg = Toolkit.getDefaultToolkit().getImage(FX.class.getResource("Logo2.0.png"));
            Taskbar taskbar = Taskbar.getTaskbar();
            taskbar.setIconImage(dockIconImg);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //Initializes Button, Label and TextField to Cipher
        lblCipher = new Label("Enter to cipher ");
        btnCipher = new Button("Cipher".toUpperCase());
        btnCipher.setOnAction(e -> cipherOnClick());

        btnCipher.getStyleClass().add("primary-button");
        btnCipher.setPrefWidth(120);
        txtCipher = new TextField();
        txtCipher.setOnAction(e -> {
            cipherOnClick();
        });
        txtCipher.setPromptText("Enter a phrase to cipher");
        txtCipher.setMinWidth(100);
        txtCipher.setMaxWidth(300);
        txtCipher.setPrefWidth(200);

        //Initializes Button, Label and TextField to Decipher
        lblDecipher = new Label("Enter to decipher ");
        btnDecipher = new Button("Decipher".toUpperCase());
        btnDecipher.setOnAction(e -> decipherOnClick());
        btnDecipher.setPrefWidth(120);
        txtDecipher = new TextField();
        txtDecipher.setOnAction(e -> {
            decipherOnClick();
        });
        txtDecipher.setPromptText("Enter a phrase to Decipher");
        txtDecipher.setMinWidth(100);
        txtDecipher.setMaxWidth(300);
        txtDecipher.setPrefWidth(200);

        // Initializes Label and Textfield for result
        lblResult = new Label("Result ");
        txtResult = new TextField();
        txtResult.setEditable(false);
        txtResult.setPromptText("Result");
        txtResult.setMinWidth(100);
        txtResult.setMaxWidth(300);
        txtResult.setPrefWidth(200);

        // Creates History Table
        tblHistory = new TableView<>();
        TableColumn input = new TableColumn("Input");
        TableColumn result = new TableColumn("Result");

        input.prefWidthProperty().bind(tblHistory.widthProperty().multiply(0.5));
        result.prefWidthProperty().bind(tblHistory.widthProperty().multiply(0.5));

        input.setResizable(false);
        result.setResizable(false);

        tblHistory.getColumns().addAll(input, result);

        // Creates pane
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setPadding(new Insets(10));
        pane.addRow(0, lblCipher, txtCipher);
        pane.addRow(1, lblDecipher, txtDecipher);
        pane.add(btnCipher, 3, 0);
        pane.add(btnDecipher, 3, 1);
        pane.addRow(2, lblResult, txtResult);
        pane.addRow(3, tblHistory);

        GridPane.setHalignment(lblCipher, HPos.RIGHT);
        GridPane.setHalignment(lblDecipher, HPos.RIGHT);
        GridPane.setHalignment(lblResult, HPos.RIGHT);
        GridPane.setHalignment(btnCipher, HPos.RIGHT);
        GridPane.setHalignment(btnDecipher, HPos.RIGHT);
        GridPane.setColumnSpan(txtCipher, 2);
        GridPane.setColumnSpan(txtDecipher, 2);
        GridPane.setColumnSpan(txtResult, 2);
        GridPane.setColumnSpan(tblHistory, 4);


        ColumnConstraints col0 = new ColumnConstraints();
        col0.setPercentWidth(25);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(25);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(25);
        pane.getColumnConstraints().addAll(col0, col1, col2, col3);

        MenuBar menuBar = new MenuBar();

        Menu menuSlide = new Menu("Slide");
        Menu menuFile = new Menu("File");

        MenuItem newWindowItem = new MenuItem("New Window");
        newWindowItem.setOnAction(e -> newWindowOnClick());
        MenuItem quitItem = new MenuItem("Quit");
        quitItem.setOnAction(e -> quitOnClick());
        MenuItem aboutMeItem = new MenuItem("About Me");
        aboutMeItem.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://atom.io"));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        MenuItem preferencesItem = new MenuItem("Preferences");
        preferencesItem.setOnAction(e -> preferencesOnClick());

        menuSlide.getItems().addAll(aboutMeItem, preferencesItem, quitItem);
        menuFile.getItems().addAll(newWindowItem);
        menuBar.getMenus().addAll(menuSlide, menuFile);

        final String os = System.getProperty("os.name");
        //if (os != null && os.startsWith("Mac"))
        //    menuBar.useSystemMenuBarProperty().set(true);

        BorderPane borderPane = new BorderPane(pane);
        borderPane.setTop(menuBar);

        // creates preferences pane
        apply = new Button("Apply Changes");
        apply.getStyleClass().add("primary-button");
        Label lblScheme = new Label("Color Scheme");
        rdoDark = new RadioButton("Dark");
        rdoLight = new RadioButton("Light");
        ToggleGroup tg = new ToggleGroup();
        rdoDark.setToggleGroup(tg);
        rdoLight.setToggleGroup(tg);
        VBox scheme = new VBox(lblScheme, rdoLight, rdoDark, apply);
        scheme.setSpacing(10);
        scheme.setPadding(new Insets(10));
        Pane preferencesPane = new Pane(scheme);
        preferencesScene = new Scene(preferencesPane);

        //Sets stage and scenes
        stage = primaryStage;

        scene = new Scene(borderPane);

        prefs = Preferences.userRoot().node("slide-cipher");
        String themePref = prefs.get("THEME", "dark");
        if (themePref.equals("light")) {
            toLight();
        } else {
            toDark();
        }

        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(FX.class.getResourceAsStream("Logo2.0.png")));
        primaryStage.setTitle("SLIDE");
        primaryStage.setMinWidth(610);
        primaryStage.setMaxWidth(610);
        primaryStage.setMaxHeight(500);
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            exitOnClick();
        });
        primaryStage.show();
    }

    private void newWindowOnClick() {
    }

    private void preferencesOnClick() {
        stage.setScene(preferencesScene);
        apply.setOnAction(e ->
                returnOnClick()
        );
    }

    private void toDark() {
        prefs.put("THEME", "dark");
        scene.getStylesheets().clear();
        preferencesScene.getStylesheets().clear();
        preferencesScene.getStylesheets().add(FX.class.getResource("stylesheetsDark.css").toExternalForm());
        scene.getStylesheets().add(FX.class.getResource("stylesheetsDark.css").toExternalForm());
    }

    private void toLight() {
        prefs.put("THEME", "light");
        scene.getStylesheets().clear();
        preferencesScene.getStylesheets().clear();
        preferencesScene.getStylesheets().add(FX.class.getResource("stylesheetsLight.css").toExternalForm());
        scene.getStylesheets().add(FX.class.getResource("stylesheetsLight.css").toExternalForm());
    }

    private void returnOnClick() {
        if (rdoDark.isSelected()) {
            toDark();
        }
        if (rdoLight.isSelected()) {
            toLight();
        }
        stage.setScene(scene);
    }

    private void quitOnClick() {
        stage.close();
    }

    private void decipherOnClick() {
        String string = txtDecipher.getText();
        char[] chars = string.toCharArray();
        ArrayList<Character> cipherFinal = new ArrayList<>();
        cipherFinal.clear();

        for (char c : chars) {
            int ascii = (int) c;
            if (ascii >= 97 && ascii <= 122) {
                ascii -= 4;
                if (ascii < 97) {
                    ascii = ascii + 26;
                }
                char l = (char) ascii;
                cipherFinal.add(l);
            }
            if (65 <= ascii && ascii <= 90) {
                ascii -= 4;
                if (ascii < 65) {
                    ascii = ascii + 26;
                }
                char l = (char) ascii;
                cipherFinal.add(l);
            }
            if (ascii == 32) {
                char l = (char) ascii;
                cipherFinal.add(l);
            }

        }
        StringBuilder sb = new StringBuilder();
        for (char c : cipherFinal) {
            sb.append(c);
        }
        String cipherEnd = sb.toString();
        history.add(new HistoryItem(string, cipherEnd));
        txtResult.setText(cipherEnd);
        txtDecipher.setText("");
        txtResult.requestFocus();
        txtResult.selectRange(0, 0);
    }

    private void cipherOnClick() {
        String string = txtCipher.getText();
        char[] chars = string.toCharArray();
        ArrayList<Character> cipherFinal = new ArrayList<>();

        for (char c : chars) {
            int ascii = (int) c;
            if (ascii >= 97 && ascii <= 122) {
                ascii += 4;
                if (ascii > 122) {
                    ascii = ascii - 26;
                }
                char l = (char) ascii;
                cipherFinal.add(l);
            }
            if (65 <= ascii && ascii <= 90) {
                ascii += 4;
                if (ascii > 90) {
                    ascii = ascii - 26;
                }
                char l = (char) ascii;
                cipherFinal.add(l);
            }
            if (ascii == 32) {
                char l = (char) ascii;
                cipherFinal.add(l);
            }

        }
        StringBuilder sb = new StringBuilder();
        for (char c : cipherFinal) {
            sb.append(c);
        }
        String cipherEnd = sb.toString();
        history.add(new HistoryItem(string, cipherEnd));
        txtResult.setText(cipherEnd);
        txtCipher.setText("");
        txtResult.requestFocus();
        txtResult.selectRange(0, 0);
        System.out.println(history.get(history.size() - 1));
    }


    private void exitOnClick() {
        stage.close();
    }
}