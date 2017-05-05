package gpacalculator;


import java.util.Map;

import gpacalculator.GpaCalculator.Grades;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UserUI extends Application {
	Scene secondScene;
	Scene firstScene;
	Scene thirdScene;
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
		Label test3 = new Label("");
		btnCheck.setOnAction(new EventHandler<ActionEvent>(){
	 		public void handle(ActionEvent ae){
	 			 GpaCalculator.loadCourse(input.getText()); //passes the pathname to anotherclass that reads
	 						// the text file and returns a double nested hashmap containing courses and grades

	 					String test2 = new String();

	 					for (String key : GpaCalculator.courseList.keySet()) {
	 						 test2 += String.valueOf(key+" "+ GpaCalculator.courseList.get(key))+"\n";
	 						test1.setText(test2);
	 						test3.setText(test2);
	 					}

	 			primaryStage.setScene(secondScene);
	 		}
	 	});


		//scene 2
		GridPane grid = new GridPane();
	    grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(0, 10, 0, 10));
		//FlowPane rootNode1 = new FlowPane(Orientation.VERTICAL,10,10);
		//rootNode1.setAlignment(Pos.CENTER);
		 secondScene= new Scene(grid,700,200);

		Label test = new Label("Current Scores");
		grid.add(test,2,1);
		Separator separator = new Separator();
		separator.setPrefWidth(180);
		grid.add(separator,1,2,8,2);
		grid.add(test1,0,3,10,5);
		Label instruction = new Label("'Calculate GPA' will calculate GPA of the current course list"
				+ ". 'Target GPA' will"+"\n tell you how you can improve your grade for any of these courses");
		grid.add(instruction,4,9);

		Button btnGPA = new Button("Calculate GPA");
		grid.add(btnGPA,1,9);
		btnGPA.setOnAction( (ae) -> instruction.setText(String.valueOf(GpaCalculator.getSemesterGpa())));

		Button btnTargetGrade = new Button("Target Grade");
		grid.add(btnTargetGrade,2,9);
		btnTargetGrade.setOnAction( (ae) -> primaryStage.setScene(thirdScene) );


		//Scene 3
		GridPane grid2 = new GridPane();
	    grid2.setHgap(10);
	    grid2.setVgap(15);
	    grid2.setPadding(new Insets(0, 10, 0, 10));
	    thirdScene= new Scene(grid2,700,350);

	    grid2.add(test3,0,0,8,6);

	    TextField courseName = new TextField();
	    courseName.setPromptText("Enter course");
	    grid2.add(courseName,0,9);
	    TextField assignmentName = new TextField();
	    assignmentName.setPromptText("Enter Assignment");
	    grid2.add(assignmentName,2,9);
	    TextField moreAssignment = new TextField();
	    moreAssignment.setPromptText("Enter how many more Assignments");
	    grid2.add(moreAssignment,4,9);
	    TextField target = new TextField();
	    target.setPromptText("Enter Target Score");
	    grid2.add(target,6,9);

	    Label targetInstruction = new Label("Enter course name, Assignment type, number of remaining assignments "
	    		+ "left, and the grade that you want to acheive.\n For example, if in Calculus, a student had"
	    		+ "an average test grade of 75 and had 2 more test to bump it up to an 85, \n the student would"
	    		+ " enter respectively: Calculus, Test, 2, 85. ");
	    grid2.add(targetInstruction,0,9,8,12);

	    Button btnSubmit = new Button("Submit");
	    btnSubmit.setOnAction(new EventHandler<ActionEvent>(){
	 		public void handle(ActionEvent ae){
	 			String course=courseName.getText();
	 			String assignment = assignmentName.getText();
	 			int moreAssignments = Integer.valueOf(moreAssignment.getText());
	 			double targerScore = Double.valueOf(target.getText());
	 			targetInstruction.setText(Grades.targetGrade(course,assignment,moreAssignments,targerScore));
	 		}
	    });
	    grid2.add(btnSubmit,0,19);
	    Button btnBack = new Button("Go Back");
	    btnBack.setOnAction( (ae) -> primaryStage.setScene(secondScene));
	    grid2.add(btnBack,2,19);
	    primaryStage.show();


	}

}
test