import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class NumberGuessingGameGUI extends JFrame {
    private int numberToGuess;
    private int numberOfTries;
    private int maxTries = 10;
    private int maxNumber = 100;
    private Random random = new Random();

    private JLabel instructionLabel;
    private JTextField guessField;
    private JButton guessButton;
    private JButton playAgainButton;
    private JComboBox<String> difficultyBox;
    private JLabel feedbackLabel;
    private JLabel attemptsLabel;

    public NumberGuessingGameGUI() {
        setTitle("Number Guessing Game");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 1));

        instructionLabel = new JLabel("Guess a number between 1 and 100", SwingConstants.CENTER);
        add(instructionLabel);

        difficultyBox = new JComboBox<>(new String[]{"Easy (1-50)", "Medium (1-100)", "Hard (1-200)"});
        add(difficultyBox);

        guessField = new JTextField();
        add(guessField);

        guessButton = new JButton("Guess");
        add(guessButton);

        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        add(feedbackLabel);

        attemptsLabel = new JLabel("Attempts left: 10", SwingConstants.CENTER);
        add(attemptsLabel);

        playAgainButton = new JButton("Play Again");
        playAgainButton.setVisible(false);
        add(playAgainButton);

        startNewGame();

        guessButton.addActionListener(e -> makeGuess());
        playAgainButton.addActionListener(e -> startNewGame());
    }

    private void startNewGame() {
        numberOfTries = 0;
        feedbackLabel.setText("");
        attemptsLabel.setText("Attempts left: " + maxTries);
        guessField.setText("");
        playAgainButton.setVisible(false);
        guessButton.setEnabled(true);
        setMaxNumberBasedOnDifficulty();
        numberToGuess = random.nextInt(maxNumber) + 1;
        instructionLabel.setText("Guess a number between 1 and " + maxNumber);
    }

    private void setMaxNumberBasedOnDifficulty() {
        int selectedIndex = difficultyBox.getSelectedIndex();
        if (selectedIndex == 0) maxNumber = 50;
        else if (selectedIndex == 1) maxNumber = 100;
        else maxNumber = 200;
    }

    private void makeGuess() {
        try {
            int guess = Integer.parseInt(guessField.getText());
            numberOfTries++;

            if (guess < numberToGuess) {
                feedbackLabel.setText("Too Low!");
            } else if (guess > numberToGuess) {
                feedbackLabel.setText("Too High!");
            } else {
                feedbackLabel.setText("Correct! You guessed it in " + numberOfTries + " tries.");
                endGame();
                return;
            }

            int attemptsLeft = maxTries - numberOfTries;
            attemptsLabel.setText("Attempts left: " + attemptsLeft);

            if (attemptsLeft == 0) {
                feedbackLabel.setText("Out of attempts! Number was: " + numberToGuess);
                endGame();
            }

        } catch (NumberFormatException ex) {
            feedbackLabel.setText("Please enter a valid number.");
        }
    }

    private void endGame() {
        guessButton.setEnabled(false);
        playAgainButton.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NumberGuessingGameGUI game = new NumberGuessingGameGUI();
            game.setVisible(true);
        });
    }
}

