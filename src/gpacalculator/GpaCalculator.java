package gpacalculator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * GPA calculator that can calculate current grades in a course based on
 * returned grades. It relies on user input of their courses and grades in a
 * text file. There are two classes: GpaCaulculator and a nested Grades class.
 *
 *
 * to do: average for the semester edit fix average/make it its own method
 */
public class GpaCalculator {

	/*
	 * courselist is the main map that hold the grades for students
	 * mapOfGradeCounter couts how many grades there were collected to calc. the
	 * average
	 */
	static Map<String, Map<String, Double>> courseList = new HashMap<>();
	 static Map<String, Map<String, Integer>> mapOfGradeCounter = new HashMap<>();
	 static Map<String,Integer> courseCredits = new HashMap<>();
	/**
	 * loadCourse method uses reader to collect course info from a textfile and
	 * returns them it in a map. It then calls loadGrade method from the Grades
	 * class to fill the map with remaining grades in those courses.
	 *
	 * @param courseGradeFile
	 *            The absolute path of the input file
	 * @return courseList returns the grades and averages
	 */
	static Map<String, Map<String, Double>> loadCourse(String courseGradeFile) {
		String[] courseInfo = null;

		try (BufferedReader br = new BufferedReader(new FileReader(
				courseGradeFile))) {
			String line;

			while ((line = br.readLine()) != null) {
				courseInfo = line.split("\\s+");
				String course = courseInfo[1];
				Grades.loadGrade(course, courseGradeFile);
				courseList.put(course,
						Grades.loadGrade(course, courseGradeFile));

				mapOfGradeCounter.put(course, new HashMap<String, Integer>(
						Grades.gradeCounter));
				courseCredits.put(courseInfo[1], Integer.parseInt(courseInfo[0]));

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return courseList;

	}
	public static Map<String, Map<String, Double>> print(){
		return courseList;
	}
	/**
	 * this method returns GPA for the semester
	 * @return
	 *
	 */

	static double getSemesterGpa() {
		double average=0;
		double sum = 0;
		int counter = 0;

		for (Map.Entry<String, Map<String, Double>> course : courseList.entrySet()) {
			average=course.getValue().get("Class Average");
			 String name = course.getKey();
			if(average>=90){
				sum += 4*(courseCredits.get(name));
				counter += courseCredits.get(name);
			}else if(90>average && average>=80){
				sum += 3*(courseCredits.get(name));
				counter += courseCredits.get(name);
			}else if(80>average && average>=70){
				sum += 2*(courseCredits.get(name));
				counter += courseCredits.get(name);
			} else if (70>average && average>=65) {
				sum += 1*(courseCredits.get(name));
				counter += courseCredits.get(name);
			} else {
				counter += courseCredits.get(name);
			}

		}
		return (sum / counter);
	}

	/*
	 * Here a final array of constants was used containing the various
	 * assignments that are collected throughout a semester. I decided not to
	 * use an enum because it was too much trouble iterating through the enums
	 * and match it with the string from input
	 */
	private static final String[] ASSIGNMENTTYPE = { "Test", "Quiz",
			"Homework", "Essay", "Final_Project", "Final_Essay" };

	/**
	 * Grades is a nested class that focus on the grades of the individual
	 * courses. It has two methods: loadGrade: populates the hashmap with the
	 * actual grades for the courses targetGrade: Tells you what scores you
	 * should get for the remaining assignments in order to get the grade that
	 * you desire
	 */
	static class Grades {
		static Map<String, Integer> gradeCounter = new HashMap<>();

		/**
		 * loadGrade populates the HashMap Grade list with the grades attained
		 * in any of the assignments. It is can be used separate from the
		 * loadCourse method as well
		 *
		 * @param course
		 *            The name of the course that you want to load the
		 *            information from
		 * @param courseGradeFile
		 *            the textfile containing all the grades from various
		 *            courses
		 * @return gradeList that contains the actual grades for test,quiz,etc
		 *         along with the class average
		 */

		static Map<String, Double> loadGrade(String course,
				String courseGradeFile) {
			Map<String, Double> gradeList = new HashMap<>();
			String[] courseInfo = null;

			try (BufferedReader br = new BufferedReader(new FileReader(
					courseGradeFile))) {
				String line;
				while ((line = br.readLine()) != null) {
					courseInfo = line.split("\\s+");
					if (courseInfo[1].equals(course)) {
						break;
					}

				}
			} catch (FileNotFoundException e) {
				System.out.println("File was not found");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int sumOfPer = 0;

			double currentGrade = 0;

			for (int i = 0; i < courseInfo.length; i++) {
				if (Arrays.asList(ASSIGNMENTTYPE).contains(courseInfo[i])) {

					sumOfPer += Integer.parseInt(courseInfo[i + 1]);
					double sumOfGrade = 0;
					int counter = 0;
					for (int j = i + 2; j < courseInfo.length; j++) {
						if (Arrays.asList(ASSIGNMENTTYPE).contains(
								courseInfo[j])) {
							break;
						} else {
							sumOfGrade += Integer.parseInt(courseInfo[j]);
							counter++;
						}
					}
					double average = sumOfGrade / counter;
					gradeList.put(courseInfo[i], average);
					gradeCounter.put(courseInfo[i], counter);

				}

			}
			if (sumOfPer != 100) {
				UserUI.test.setText("Error: Check input file to make sure all the percentages for "
						+ "each assignment add to 100 in each course");
			}
			for (int i = 0; i < courseInfo.length; i++) {
				if (Arrays.asList(ASSIGNMENTTYPE).contains(courseInfo[i])) {
					double percent = 0;
					percent = Integer.parseInt(courseInfo[i + 1]);
					percent /= 100;
					currentGrade += percent * gradeList.get(courseInfo[i]);

				}
			}
			gradeList.put("Class Average", currentGrade);

			return gradeList;
		}

		/**
		 * This method tells the user what grades they should score in their
		 * upcomming x amount of assignments in order to get the grade that the
		 * user wanted.
		 *
		 * @param course
		 *            name of the course that the student wants to inquire about
		 * @param assignment
		 *            name of the assignment that they want to improve on such
		 *            as "Test", "Quiz", or "Essay"
		 * @param moreAssignments
		 *            The number of assignments that the student has left in the
		 *            specified assignment parameter
		 * @param targetScore
		 *            The grade that they are trying to attain in said
		 *            assignment
		 * @return
		 */
		static String targetGrade(String course, String assignment,
				int moreAssignments, double targetScore) {
			if(courseList.containsKey(course) && courseList.get(course).containsKey(assignment)){


			int number = mapOfGradeCounter.get(course).get(assignment);
			double sum = number * (courseList.get(course).get(assignment));
			number += moreAssignments;
			double newsum = number * targetScore;
			newsum -= sum;
			double neededGrades = newsum / moreAssignments;
			String results= new String("");
				if (neededGrades > 100) {
					results="you would need a score of at least "
							+ neededGrades + " to get the desired grade. Check to"
							+ " see if extra credit is avaialble.";
				} else if (neededGrades < 0) {
				results="Error. A negative grade of " + neededGrades
							+ " to get the score desired.";
				}else {
					results="You need a score of at least "
							+ neededGrades + " in the next " + moreAssignments
							+ " " + assignment + "s  to get a " + targetScore
							+ " average.";
				}
				return results;
			}
			else {return "Make sure you entered the correct course and assignment";
			}
		}
	}


}


