import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class QuizApplication {
    private static List<Question> questions = new ArrayList<>();
    private static int score = 0;
    private static int questionIndex = 0;
    private static Timer timer = new Timer();
    private static boolean answered = false;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadQuestions();
        for (questionIndex = 0; questionIndex < questions.size(); questionIndex++) {
            answered = false;
            askQuestion(questions.get(questionIndex));
        }
        displayResults();
        scanner.close();
    }

    private static void loadQuestions() {
        questions.add(new Question("What is the capital of France?", new String[]{"A) Berlin", "B) Madrid", "C) Paris", "D) Rome"}, 'C'));
        questions.add(new Question("What is 2 + 2?", new String[]{"A) 3", "B) 4", "C) 5", "D) 6"}, 'B'));
        questions.add(new Question("What is the largest planet?", new String[]{"A) Earth", "B) Mars", "C) Jupiter", "D) Saturn"}, 'C'));
    }

    private static void askQuestion(Question question) {
        System.out.println(question.getQuestionText());
        for (String option : question.getOptions()) {
            System.out.println(option);
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!answered) {
                    System.out.println("Time's up!");
                    nextQuestion();
                }
            }
        }, 10000); // 10 seconds timer

        String answer = scanner.next();
        answered = true;
        timer.cancel();
        timer = new Timer();
        
        if (answer.toUpperCase().charAt(0) == question.getCorrectAnswer()) {
            score++;
            System.out.println("Correct!");
        } else {
            System.out.println("Wrong! The correct answer is: " + question.getCorrectAnswer());
        }

        nextQuestion();
    }

    private static void nextQuestion() {
        System.out.println();
    }

    private static void displayResults() {
        System.out.println("Quiz Over!");
        System.out.println("Your score: " + score + "/" + questions.size());
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println((i + 1) + ". " + q.getQuestionText());
            System.out.println("Your answer: " + (answered ? q.getCorrectAnswer() : "None"));
            System.out.println("Correct answer: " + q.getCorrectAnswer());
        }
    }

    static class Question {
        private String questionText;
        private String[] options;
        private char correctAnswer;

        public Question(String questionText, String[] options, char correctAnswer) {
            this.questionText = questionText;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }

        public String getQuestionText() {
            return questionText;
        }

        public String[] getOptions() {
            return options;
        }

        public char getCorrectAnswer() {
            return correctAnswer;
        }
    }
}
