package gpacalculator;

import java.util.Map;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class UserUI extends Application {
	Scene secondScene;
	Scene firstScene;
	private TableView table1 = new TableView();
	public static void main(String[] args){
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("GPA Calculator");

		FlowPane rootNode = new FlowPane(Orientation.VERTICAL,10,10);
		rootNode.setAlignment(Pos.CENTER);
		firstScene = new Scene(rootNode,600,200);
		primaryStage.setScene(firstScene);
		Label heading = new Label("Enter the Absolute Path of the file containing the grades");
		Label example = new Label("ex) C:\\Users\\oonni\\workspace\\ideas\\src\\gpacalculator\\course_grades");
		TextField input = new TextField();
		Button btnCheck = new Button("Check");
		rootNode.getChildren().addAll(heading,input,btnCheck, example);
		Label test1 = new Label("");
		btnCheck.setOnAction(new EventHandler<ActionEvent>(){
	 		public void handle(ActionEvent ae){
	 			 GpaCalculator.loadCourse(input.getText()); //passes the pathname to anotherclass that reads
	 						// the text file and returns a double nested hashmap containing courses and grades

	 					String test2 = new String();
	 					for (String key : GpaCalculator.courseList.keySet()) {
	 						 test2 += String.valueOf(key+" "+ GpaCalculator.courseList.get(key))+"\n";
	 						test1.setText(test2);
	 					}
	 			primaryStage.setScene(secondScene);
	 		}
	 	});


		//scene 2
		FlowPane rootNode1 = new FlowPane(Orientation.VERTICAL,10,10);
		rootNode1.setAlignment(Pos.CENTER);
		 secondScene= new Scene(rootNode1,600,200);

		Label test = new Label("Current Scores");
		Separator separator = new Separator();
		separator.setPrefWidth(180);
		Button btnGPA = new Button("Calculate GPA");
		Button btnTargetGrade = new Button("Target Grade");
		rootNode1.getChildren().addAll(test,separator,test1,btnGPA,btnTargetGrade);
		primaryStage.show();


	}

}
//C:\Users\oonni\workspace\ideas\src\gpacalculator\course_grades