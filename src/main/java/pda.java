import java.util.Stack;

/*libraries for javafx*/
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class pda extends Application{
    private Stack states;
    public int i = 0;
    public Boolean isPDA = true;

    public pda() { // initialize the stack
        states = new Stack();
    }

    /*
     * Function to evaluate the string and find out whether it is a PDA or not
     * */
    public void evaluatePDA(String inputString, Stage primaryStage){

        /* Code for the upper half part of the GUI
         * */

        // code to accept the string in a input box
        HBox inputLabelHB = new HBox();
        inputLabelHB.setSpacing(20);
        Label testStringLbl = new Label("Input the String");
        testStringLbl.setTextFill(Color.WHITE);
        TextField textBox = new TextField();
        Button inputStringBtn = new Button("Input");
        inputLabelHB.getChildren().add(testStringLbl);
        inputLabelHB.getChildren().add(textBox);
        inputLabelHB.getChildren().add(inputStringBtn);

        // code to display the string that we need to push into the stack
        HBox stringLabelHB = new HBox();
        stringLabelHB.setMinHeight(20);
        stringLabelHB.setSpacing(10);
        stringLabelHB.setStyle("-fx-border-color: yellow");
        for(int j = 0; j < inputString.length(); j++) { // display the initial string here
            char a_char = inputString.charAt(j);
            Label label = new Label(String.valueOf(a_char));
            label.setTextFill(Color.WHITE);
            stringLabelHB.getChildren().add(label);
        }

        Button push_btn = new Button("PUSH");

        Label output_lbl = new Label("Push the string on the stack!!");
        output_lbl.setPadding(new Insets(0, 0, 0, 0));
        output_lbl.setStyle("-fx-text-fill: yellow");

        // This is a vertical layout box where we place all the input string related UI elements
        VBox topVBox = new VBox();
        topVBox.setPrefHeight(150);
        topVBox.setSpacing(20);
        topVBox.setStyle("-fx-background-color: gray;");
        topVBox.setPadding(new Insets(60, 50, 40, 80));
        topVBox.getChildren().add(inputLabelHB);
        topVBox.getChildren().add(stringLabelHB);
        topVBox.getChildren().add(push_btn);
        topVBox.getChildren().add(output_lbl);

        // code to input the string from the textbox into the display string
        inputStringBtn.setOnMouseClicked(event -> {
            stringLabelHB.getChildren().clear();
            i = 0;
            output_lbl.setText("Push the string on the stack!!");
            states.clear();
            isPDA = true;

            // get the string from the textbox and display it
            for(int j = 0; j < textBox.getText().length(); j++) {
                char a_char = textBox.getText().charAt(j);
                Label label = new Label(String.valueOf(a_char));
                label.setTextFill(Color.WHITE);
                stringLabelHB.getChildren().add(label);
            }
        });


        /* Code for the lower half part of the GUI
         * */

        Label stack = new Label("Stack");
        stack.setPadding(new Insets(20, 0, 0, 20));

        // This is a vertical layout box to created to show a stack view
        VBox bottomVBox = new VBox();
        bottomVBox.getChildren().add(stack);
        bottomVBox.setMinWidth(80);
        bottomVBox.setStyle("-fx-background-color: #C1C0C3;-fx-border-color: black;");

        // Scrollpane added incase the string overflows
        ScrollPane scrollPane = new ScrollPane(bottomVBox);
        scrollPane.setPadding(new Insets(40, 0, 0, 200));

        // code to push the string from the display box one by one and check whether our string is a PDA or not
        push_btn.setOnMouseClicked(event -> {
            Node stringLabel = topVBox.getChildren().get(1); // get the display box element, and then extract the string from it

            char[] input = inputString.toCharArray();
            Boolean uno = false;


            if(((HBox)stringLabel).getChildren().size() == 0) { // when the string is empty, then this is a PDA
                output_lbl.setText("The string is a PDA");
                isPDA = true;
            }else if(stringLabel instanceof HBox){
            for(Node nodeIn:((HBox)stringLabel).getChildren()){
                    if (nodeIn instanceof Label) {
                        if((((HBox)stringLabel).getChildren().indexOf(nodeIn)) == i) { // check for elements of a string one by one
                            Label txt = new Label(((Label) nodeIn).getText());
                            txt.setPadding(new Insets(0, 0, 0, 30));

                            if (((Label) nodeIn).getText().equals("0") && !uno) { // if 0, then add the string on the stack
                                bottomVBox.getChildren().add(0, txt);
                                states.push(((Label) nodeIn).getText().equals("0"));
                            } else if (((Label) nodeIn).getText().equals("1")) { // if 1, then remove one 0 from the stack
                                uno = true;
                                if (!states.empty()) {
                                    ObservableList<Node> lst = bottomVBox.getChildren();
                                    lst.remove(0);
                                    states.pop();
                                    //System.out.println("Popping 0 from the stack for the current 1...");
                                } else {
                                    output_lbl.setText("The string is not a PDA");
                                    System.out.println("We have nothing in the stack to pop....");
                                    isPDA = false; //We have more 1's then 0's
                                }
                            } else if (((Label)nodeIn).getText().equals("")) {
                                isPDA = true;
                            } else { // if string element other than 0 or 1, then this is not a PDA
                                output_lbl.setText("This is not part of the language");
                                //System.out.println("This is not part of the language....");
                                isPDA = false;
                            }

                            if((((HBox)stringLabel).getChildren().indexOf(nodeIn)) == (((HBox)stringLabel).getChildren().size() - 1)) { // check when reached end of the string
                                if(!states.empty() || !isPDA) {
                                    output_lbl.setText("The string is not a PDA");
                                } else {
                                    output_lbl.setText("The string is a PDA");
                                }
                            }

                            if(!isPDA) break;
                        }
                    }
                }
            }

            i = i + 1;
        });

        // Window Pane where we add the upper and lower part of the UI
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topVBox);
        borderPane.setCenter(scrollPane);

        // Add the Pane to the primary Stage provided by javafx
        Scene scene = new Scene(borderPane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("PDA");
        primaryStage.show();
    }

    /*
     * Method to show the GUI window named Stage
     * */
    @Override
    public void start(Stage primaryStage) {
        String test_string = "";//"000111"; // this is a initial test string

        /* Check the code all the below strings
        String t1 = "0011";
        String t2 = "01010101";
        String t3 = "";
        String t4 = "ababa";
        String t5 = "00011";
        String t6 = "111000";
        String t7 = "00111";
        String t8 = "0000";
        */

        pda p = new pda();
        p.evaluatePDA(test_string,primaryStage);
    }

    public static void main(String[] args) {
        launch(args); //launch the arguments for javafx
    }
}