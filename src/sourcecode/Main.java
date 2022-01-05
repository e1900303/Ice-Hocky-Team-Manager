package sourcecode;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCode;

public class Main extends Application {

    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: #20B2AA; -fx-background-radius: 15px; -fx-text-fill: BLACK";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #CDE8ED; -fx-background-radius: 15px; -fx-text-fill: BLACK";
    private final TextField textField1 = new TextField();
    private final TextField textField2 = new TextField();
    private final TextField textField3 = new TextField();
    private final TextField textField4 = new TextField();
    private final TextArea textArea = new TextArea();

    private final HashMap<String,Integer> scores = new HashMap<>();
    private final FileChooser fileChooser = new FileChooser();
    private final ComboBox<String> playersComboBox = new ComboBox<>();
    private final ComboBox<Date> datesComboBox = new ComboBox<>();
    private final String playersName = "players.txt";
    ArrayList<Match> matches = new ArrayList<>();

    // Method which implements the players list from players.txt to the player combo box
    public void readPlayers() throws IOException{
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL url = classLoader.getResource(playersName);

        try(InputStream in = url.openStream(); BufferedReader input = new BufferedReader(new InputStreamReader(in))){
            String line;
            while((line = input.readLine()) != null) {
                scores.put(line,0);
            }
            List<String> names = scores.keySet().stream().sorted().collect(Collectors.toList());
            playersComboBox.setItems(FXCollections.observableArrayList(names));
        }
    }

    // Method which shows error message when the program
    // cannot read the file players.txt (for example when the file is missing or corrupted)
    @Override
    public void start(Stage primaryStage) throws Exception{

        try {

            readPlayers();

        }

        catch(IOException exception) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There are problems with the file");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
            return;

        }

        // Graphical User Interface

        BorderPane mainPanel = new BorderPane();
        mainPanel.setStyle("-fx-background-color: SKYBLUE");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(5,5,5,5));

        Text instructionText = new Text("Give details of the game:");
        instructionText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        gridPane.add(instructionText,0,0,2,1);

        Text label1 = new Text("Date (dd.mm.yyyy)");
        label1.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        gridPane.add(label1,0,1);
        gridPane.add(textField1,1,1);

        Text label2 = new Text("Opponent");
        label2.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        gridPane.add(label2,0,2);
        gridPane.add(textField2,1,2);

        Text label3 = new Text("Final result (Our score : Their score)");
        label3.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        gridPane.add(label3,0,3);
        gridPane.add(textField3,1,3);


        // "Create game" button and its method to be executed when pressed

        Button createButton = new Button ("Create game");
        createButton.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        createButton.setStyle(IDLE_BUTTON_STYLE);
        createButton.setOnMouseEntered(e -> createButton.setStyle(HOVERED_BUTTON_STYLE));
        createButton.setOnMouseExited(e -> createButton.setStyle(IDLE_BUTTON_STYLE));
        gridPane.add(createButton,1,4);

        createButton.setOnAction(new EventHandler<ActionEvent>() {

            private void createGame(ActionEvent actionEvent) {
            textArea.setText(null);
            Date date = new Date(textField1.getText());
            String opponent = textField2.getText();
            String result = textField3.getText();
            Match g = new Match(date,opponent,result);
            matches.add(g);
            datesComboBox.getItems().add(date);
            textArea.appendText(g.toString());

        }

            @Override
            public void handle(ActionEvent actionEvent) {

                createGame(actionEvent);
            }

        });


        Text instructionText2 = new Text ("Choose player who scored and give their point:");
        instructionText2.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        gridPane.add(instructionText2,0,5,2,1);

        Text label4 = new Text("Player ");
        label4.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        gridPane.add(label4,0,6);
        gridPane.add(playersComboBox,1,6);

        Text label5 = new Text("Point ");
        label5.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        gridPane.add(label5,0,7);
        gridPane.add(textField4,1,7);

        // "Add player" button and its method to be executed when pressed

        Button addButton = new Button("Add player");
        addButton.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        addButton.setStyle(IDLE_BUTTON_STYLE);
        addButton.setOnMouseEntered(e -> addButton.setStyle(HOVERED_BUTTON_STYLE));
        addButton.setOnMouseExited(e -> addButton.setStyle(IDLE_BUTTON_STYLE));
        gridPane.add(addButton,1,8);

        addButton.setOnAction(new EventHandler<ActionEvent>() {

            private void addPlayer(ActionEvent actionEvent) {

            Match g = matches.get(matches.size() - 1);
            int score = Integer.parseInt(textField4.getText());
            Player p = new Player(playersComboBox.getValue(),score);
            g.addPlayer(p);
            textArea.appendText(p.toString());
            scores.put(playersComboBox.getValue(),scores.get(playersComboBox.getValue())+score);

        }

            @Override
            public void handle(ActionEvent actionEvent) {

                addPlayer(actionEvent);
            }
        });


        Text instructionText3 = new Text("Choose a date to see the details of the game on that date:");
        instructionText3.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        gridPane.add(instructionText3,0,9,2,1);

        Text label6 = new Text("Show match on:");
        label6.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        gridPane.add(label6,0,10);
        gridPane.add(datesComboBox,1,10);


        // Method which shows match on a chosen date from the combo box

        datesComboBox.setOnAction(new EventHandler<ActionEvent>() {

            private void showGame(ActionEvent actionEvent) {
                textArea.setText(null);
                for (Match g: matches){

                    if(g.getDate().equals(datesComboBox.getValue())) {

                        textArea.appendText(g.toString());

                    }
                }

            }

            @Override
            public void handle(ActionEvent actionEvent) {

                showGame(actionEvent);
            }

        });


        Text label7 = new Text("Show stats of player's score (descending)");
        label7.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        gridPane.add(label7,0,11);


        // "Show stats of players" button and its method to be executed when pressed

        Button statsButton = new Button("Show stats");
        statsButton.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        statsButton.setStyle(IDLE_BUTTON_STYLE);
        statsButton.setOnMouseEntered(e -> statsButton.setStyle(HOVERED_BUTTON_STYLE));
        statsButton.setOnMouseExited(e -> statsButton.setStyle(IDLE_BUTTON_STYLE));
        gridPane.add(statsButton,1,11);

        statsButton.setOnAction(new EventHandler<ActionEvent>() {

            private void showStats(ActionEvent actionEvent) {
                textArea.setText(null);
                Object[] a = scores.entrySet().toArray();
                Arrays.sort(a, new Comparator() {
                    public int compare(Object o1, Object o2) {
                        return ((Map.Entry<String, Integer>) o2).getValue()
                                .compareTo(((Map.Entry<String, Integer>) o1).getValue());
                    }
                });
                for (Object k : a) {

                    int score = ((Map.Entry<String, Integer>) k).getValue();
                    if(score>0){

                        textArea.appendText(((Map.Entry<String, Integer>) k).getKey() + " | Point: "
                                + ((Map.Entry<String, Integer>) k).getValue() +"\n") ;

                    }
                }

            }

            @Override
            public void handle(ActionEvent event) {

                showStats(event);
            }

        });

        mainPanel.setCenter(gridPane);

        // Output screen
        textArea.setStyle("-fx-control-inner-background:#000000; -fx-font-family: Consolas; -fx-highlight-fill: #00ff00; -fx-highlight-text-fill: #000000; -fx-text-fill: #00ff00; ");
        textArea.setEditable(false);
        mainPanel.setBottom(textArea);

        // Menubar and the File menu

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        menuBar.getMenus().add(menuFile);

        // "New" option with its icon and keyboard shortcut

        Image newIcon = new Image(Main.class.getResourceAsStream("/icon\\new_file_icon.png"));
        ImageView newView = new ImageView(newIcon);
        newView.setFitWidth(15);
        newView.setFitHeight(15);
        MenuItem startNew = new MenuItem("New");
        startNew.setGraphic(newView);
        startNew.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        startNew.setOnAction(new EventHandler<ActionEvent>() {

            private void startNew(ActionEvent actionEvent) {

                textField1.setText(null);
                textField2.setText(null);
                textField3.setText(null);
                textField4.setText(null);
                textArea.setText(null);
                playersComboBox.getSelectionModel().clearSelection();
                datesComboBox.getSelectionModel().clearSelection();

            }

            @Override
            public void handle(ActionEvent actionEvent) {

                startNew(actionEvent);

            }

        });

        // "Open" option with its icon and keyboard shortcut

        Image openIcon = new Image(Main.class.getResourceAsStream("/icon\\open_icon.png"));
        ImageView openView = new ImageView(openIcon);
        openView.setFitWidth(15);
        openView.setFitHeight(15);
        MenuItem open = new MenuItem("Open");
        open.setGraphic(openView);
        open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));

        open.setOnAction(new EventHandler<ActionEvent>() {

            @SuppressWarnings("unchecked")
            private void readFromFile(File file) {
                textArea.setText(null);
                try (ObjectInputStream file_in = new ObjectInputStream(new FileInputStream(file))) {

                    matches = (ArrayList<Match>)file_in.readObject();

                    for (Match m : matches) {

                        datesComboBox.getItems().add(m.getDate());
                        ArrayList<Player> players = m.getPlayers();

                        for (Player p : players) {

                            scores.put(p.getName(),p.getPoint());

                        }

                    }

                }
                catch(Exception exception) {
                    System.out.println("There are problems with the file " + file);
                    exception.printStackTrace();
                }
            }

            @Override
            public void handle(ActionEvent actionEvent) {
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    readFromFile(file);
                }

            }
        });

        // "Save" option with its icon and keyboard shortcut

        Image saveIcon = new Image(Main.class.getResourceAsStream("/icon\\save_icon.png"));
        ImageView saveView = new ImageView(saveIcon);
        saveView.setFitWidth(15);
        saveView.setFitHeight(15);
        MenuItem saveAs = new MenuItem("Save");
        saveAs.setGraphic(saveView);
        saveAs.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        saveAs.setOnAction(new EventHandler<ActionEvent>() {

            private void saveToFile(File file) {
                textArea.setText(null);
                System.out.println(file.getAbsolutePath());

                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file +".dat");
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                    objectOutputStream.writeObject(matches);
                    for ( Match m : matches) {

                        ArrayList<Player> players = m.getPlayers();
                        objectOutputStream.writeObject(players);

                    }
                    objectOutputStream.close();
                    textArea.appendText("File saved.");
                }
                catch(Exception exception) {

                    exception.printStackTrace();

                }

            }

            @Override
            public void handle(ActionEvent actionEvent) {
                File file = fileChooser.showSaveDialog(primaryStage);
                if (file != null) {
                    saveToFile(file);
                }

            }
        });

        // "Exit" option with its icon and keyboard shortcut

        Image exitIcon = new Image(Main.class.getResourceAsStream("/icon\\exit_icon.png"));
        ImageView exitView = new ImageView(exitIcon);
        exitView.setFitWidth(15);
        exitView.setFitHeight(15);
        MenuItem exit = new MenuItem("Exit");
        exit.setGraphic(exitView);

        exit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {

                Platform.exit();

            }
        });

        menuFile.getItems().addAll(startNew, open, saveAs, new SeparatorMenuItem(),  exit);
        mainPanel.setTop(menuBar);


        Scene scene = new Scene(mainPanel, 600, 600);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon\\app_icon.png")));
        primaryStage.setTitle("Ice Hockey Team Manager");
        primaryStage.setScene(scene);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);

    }
}
