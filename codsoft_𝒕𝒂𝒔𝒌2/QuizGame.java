import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class QuizGame {

    // Class to store questions, options, and correct answers
    static class Question {
        String questionText;
        String[] options;
        int correctAnswer;

        Question(String questionText, String[] options, int correctAnswer) {
            this.questionText = questionText;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int score = 0;
        int totalQuestions = 3;

        // Array to store quiz questions
        Question[] quiz = new Question[totalQuestions];
        quiz[0] = new Question("What is the capital of France?",
                new String[]{"1. Berlin", "2. Madrid", "3. Paris", "4. Rome"}, 3);
        quiz[1] = new Question("Which planet is known as the Red Planet?",
                new String[]{"1. Earth", "2. Mars", "3. Jupiter", "4. Venus"}, 2);
        quiz[2] = new Question("Who wrote 'To Kill a Mockingbird'?",
                new String[]{"1. Harper Lee", "2. J.K. Rowling", "3. Mark Twain", "4. George Orwell"}, 1);

        boolean[] correctAnswers = new boolean[totalQuestions];

        for (int i = 0; i < totalQuestions; i++) {
            System.out.println("Question " + (i + 1) + ": " + quiz[i].questionText);
            for (String option : quiz[i].options) {
                System.out.println(option);
            }

            // Implementing a timer (10 seconds per question)
            long startTime = System.currentTimeMillis();
            int answer = -1;

            while (System.currentTimeMillis() - startTime < TimeUnit.SECONDS.toMillis(10)) {
                if (scanner.hasNextInt()) {
                    answer = scanner.nextInt();
                    break;
                }
            }

            if (answer == quiz[i].correctAnswer) {
                System.out.println("Correct!");
                score++;
                correctAnswers[i] = true;
            } else {
                System.out.println("Incorrect or time ran out.");
                correctAnswers[i] = false;
            }

            System.out.println();
        }

        // Displaying the final score and summary
        System.out.println("Quiz Over!");
        System.out.println("Your final score is: " + score + "/" + totalQuestions);
        System.out.println("Summary:");
        for (int i = 0; i < totalQuestions; i++) {
            System.out.println("Question " + (i + 1) + ": " + (correctAnswers[i] ? "Correct" : "Incorrect"));
        }

        scanner.close();
    }
}
