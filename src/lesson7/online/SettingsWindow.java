package lesson7.online;

import javax.swing.*;
import java.awt.*;

public class SettingsWindow extends JFrame {
    private static final int WIN_WIDTH = 350;
    private static final int WIN_HEIGHT = 300;

    private static final int MIN_FIELD_SIZE = 3;
    private static final int MAX_FIELD_SIZE = 6;
    private static final int MIN_WIN_LENGTH = 3;


    private final GameWindow gameWindow;
    private final SettingsColorFrame settingsColor;

    private JRadioButton humanVsHuman;
    private JRadioButton humanVsAi;
    private JSlider sliderWinLength;
    private JSlider sliderFieldSize;
    private final JButton butColor;
    private Color colorMap;


    private final String FIELD_SIZE_PREFIX = "Размер поля: ";
    private final String WIN_LENGTH_PREFIX = "Условие победы: ";

    SettingsWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        setSize(WIN_WIDTH, WIN_HEIGHT);


        Rectangle gameWindowBounds = gameWindow.getBounds();
        int posX = (int) gameWindowBounds.getCenterX() - WIN_WIDTH / 2;
        int posY = (int) gameWindowBounds.getCenterY() - WIN_HEIGHT / 2;

        setLocation(posX, posY);
        setResizable(false);
        setTitle("Enter Your Settings New Game");

        settingsColor = new SettingsColorFrame(this);

        setLayout(new GridLayout(11, 1));


        initSettingControls();
        fieldSizeAndWinControl();

        butColor = new JButton("Selecting Field Color");
        butColor.addActionListener(e -> settingsColor.setVisible(true));

        add(butColor);

        JButton butStart = new JButton("Start Game");
        butStart.addActionListener(e -> gameModeControls());


        add(butStart);


    }

    private void initSettingControls() {
        add(new JLabel("Выберите режим игры"));
        humanVsHuman = new JRadioButton("2 PLAYERS");
        humanVsAi = new JRadioButton("1 PLAYERS", true);

        ButtonGroup gameMode = new ButtonGroup();
        gameMode.add(humanVsHuman);
        gameMode.add(humanVsAi);
        add(humanVsAi);
        add(humanVsHuman);

    }

    private void fieldSizeAndWinControl() {
        JLabel labelFieldSize = new JLabel(FIELD_SIZE_PREFIX + MIN_FIELD_SIZE);
        JLabel labelWinLength = new JLabel(WIN_LENGTH_PREFIX + MIN_WIN_LENGTH);

        sliderFieldSize = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MIN_FIELD_SIZE);
        sliderFieldSize.addChangeListener(e -> {
            int currentValue = sliderFieldSize.getValue();
            labelFieldSize.setText(FIELD_SIZE_PREFIX + currentValue);
            sliderWinLength.setMaximum(currentValue);
        });

        sliderWinLength = new JSlider(MIN_WIN_LENGTH, MIN_FIELD_SIZE, MIN_FIELD_SIZE);
        sliderWinLength.addChangeListener(e -> labelWinLength.setText(WIN_LENGTH_PREFIX + sliderWinLength.getValue()));


        add(new JLabel("Выберите размер поля"));
        add(labelFieldSize);
        add(sliderFieldSize);

        add(new JLabel("Выберите условия победы"));
        add(labelWinLength);
        add(sliderWinLength);

    }

    void colorButton(Color colorId) {
        butColor.setBackground(colorId);
        colorMap = colorId;
    }

    private void gameModeControls() {

        int gameMode;
        if (humanVsHuman.isSelected()) {
            gameMode = GameMap.GAME_MODE_HVH;
        } else if (humanVsAi.isSelected()) {
            gameMode = GameMap.GAME_MODE_HVA;
        } else {
            throw new RuntimeException("Неизвестный тип игры");
        }

        int fieldSize = sliderFieldSize.getValue();
        int winLength = sliderWinLength.getValue();


        gameWindow.startNewGame(gameMode, fieldSize, fieldSize, winLength, colorMap);
        setVisible(false);


    }

}
