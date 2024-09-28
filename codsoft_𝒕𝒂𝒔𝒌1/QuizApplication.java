import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Question {
    String questionText;
    String[] options;
    int correctAnswerIndex;

    public Question(String questionText, String[] options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }
}

public class QuizApplication extends JFrame implements ActionListener {
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private JButton submitButton;
    private ButtonGroup buttonGroup;
    private JLabel timerLabel;
    private Timer timer;
    private int timeLeft = 10;

    public QuizApplication() {
        questions = loadQuestions();
        initComponents();
        startTimer(); // Initialize timer before showing the first question
        displayQuestion();
    }

    private void initComponents() {
        setTitle("Quiz Application with Timer");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        questionLabel = new JLabel();
        add(questionLabel);

        optionButtons = new JRadioButton[4];
        buttonGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            buttonGroup.add(optionButtons[i]);
            add(optionButtons[i]);
        }

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        add(submitButton);

        timerLabel = new JLabel("Time left: 10 seconds");
        add(timerLabel);
    }

    private List<Question> loadQuestions() {
        List<Question> quizQuestions = new ArrayList<>();
        quizQuestions.add(new Question("What is the capital of France?", new String[]{"Paris", "London", "Berlin", "Madrid"}, 0));
        quizQuestions.add(new Question("What is 2 + 2?", new String[]{"3", "4", "5", "6"}, 1));
        quizQuestions.add(new Question("Who wrote 'Hamlet'?", new String[]{"Shakespeare", "Dickens", "Tolkien", "Hemingway"}, 0));
        return quizQuestions;
    }

    private void displayQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionLabel.setText(currentQuestion.questionText);
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(currentQuestion.options[i]);
            }
            buttonGroup.clearSelection();
            resetTimer(); // Ensure the timer resets for each question
        } else {
            showResult();
        }
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Time left: " + timeLeft + " seconds");
                if (timeLeft <= 0) {
                    timer.stop();
                    submitAnswer();
                }
            }
        });
    }

    private void resetTimer() {
        timeLeft = 10;
        timerLabel.setText("Time left: 10 seconds");
        timer.restart(); // Ensure the timer restarts for each question
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.stop();
        submitAnswer();
    }

    private void submitAnswer() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        int selectedOption = -1;

        // Find the selected option
        for (int i = 0; i < 4; i++) {
            if (optionButtons[i].isSelected()) {
                selectedOption = i;
                break;
            }
        }

        // Check if the answer is correct
        if (selectedOption == currentQuestion.correctAnswerIndex) {
            score++;
        }

        currentQuestionIndex++;
        displayQuestion();
    }

    private void showResult() {
        JOptionPane.showMessageDialog(this, "Quiz over! Your score: " + score + "/" + questions.size());
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizApplication().setVisible(true));
    }
}
