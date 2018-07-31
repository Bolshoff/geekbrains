package geekbrains.lesson1;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame {
    private static final int POS_X = 600;
    private static final int POS_Y = 200;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    private GameObject[] gameObjects = new GameObject[1];
    private int gameObjectsCount;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow());
    }

    MainWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Circles");

        GameCanvas gameCanvas = new GameCanvas(this);
        gameCanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int btn = e.getButton();
                if (btn == MouseEvent.BUTTON1)
                    addGameObject(new Ball(e.getX(), e.getY()));
                else if (btn == MouseEvent.BUTTON3)
                    removeGameObject();
            }
        });

        add(gameCanvas, BorderLayout.CENTER);
        initGame();
        setVisible(true);
    }

    private void addGameObject(GameObject gameObject) {
        if (gameObjectsCount == gameObjects.length) {
            GameObject[] newArray = new GameObject[gameObjects.length * 2];
            System.arraycopy(gameObjects, 0, newArray, 0, gameObjects.length);
            gameObjects = newArray;
        }
        gameObjects[gameObjectsCount] = gameObject;
        gameObjectsCount++;
    }

    private void removeGameObject() {
        if (gameObjectsCount > 1) gameObjectsCount--;
    }

    private void initGame() {
        gameObjectsCount = 0;
        addGameObject(new Background());
    }

  //  @Override
    public void onDrawFrame(GameCanvas canvas, Graphics g, float deltaTime) {
        update(canvas, deltaTime);
        render(canvas, g);
    }

    private void update(GameCanvas canvas, float deltaTime) {
        for (int i = 0; i < gameObjectsCount; i++) {
            gameObjects[i].update(canvas, deltaTime);
        }
    }

    private void render(GameCanvas canvas, Graphics g) {
        for (int i = 0; i < gameObjectsCount; i++) {
            gameObjects[i].render(canvas, g);
        }
    }
}
