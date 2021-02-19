package com.shpp.p2p.cs.ilitvinov.assignment4;

import acm.graphics.*;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Assignment4Part1 extends WindowProgram {
    /**
     * Width and height of application window in pixels
     */
    public static final int APPLICATION_WIDTH = 300;
    public static final int APPLICATION_HEIGHT = 600;

    /**
     * Dimensions of game board (usually the same)
     */
    private static final int WIDTH = APPLICATION_WIDTH;
    private static final int HEIGHT = APPLICATION_HEIGHT;

    private static final int FONT_SIZE = WIDTH/12;

    /**
     * Dimensions of the paddle
     */
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 10;

    /**
     * Offset of the paddle up from the bottom
     */
    private static final int PADDLE_Y_OFFSET = 30;

    /**
     * Number of bricks per row
     */
    private static final int NBRICKS_PER_ROW = 10;

    /**
     * Number of rows of bricks
     */
    private static final int NBRICK_ROWS = 7;

    /**
     * Separation between bricks
     */
    private static final int BRICK_SEP = 4;

    /**
     * Width of a brick
     */
    private static final int BRICK_WIDTH =
            (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

    /**
     * Height of a brick
     */
    private static final int BRICK_HEIGHT = 8;

    /**
     * Radius of the ball in pixels
     */
    private static final int BALL_RADIUS = 10;

    /**
     * Offset of the top brick row from the top
     */
    private static final int BRICK_Y_OFFSET = 70;

    /**
     * Number of turns
     */

    private static final int NTURNS = 3;

    /**
     * PAUSE_TIME - pause for ball's movement animation
     * ANIMATION_PAUSE - pause for starting animation
     */

    private static final double PAUSE_TIME = 20;
    private static final double ANIMATION_PAUSE = 1000;

    /**
     * Color[] BRICK_COLOR - array of colors, for different levels of bricks
     */
    private static final Color[] BRICK_COLOR = new Color[]{Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN};

    /**
     * paddle - creating paddle
     * pause - value for pause or unpause game
     */

    private GRect paddle = addPaddle();
    private boolean pause = true;

    /**
     * GameValues class - is for controlling and changing parameters of the game in each moment
     */

    private static class GameValues {

        private GOval ball;
        private double vx, vy;
        private int lives;
        private int bricksAmount;

        GameValues(GOval ball, double vx, double vy, int lives, int bricksAmount) {
            this.ball = ball;
            this.vx = vx;
            this.vy = vy;
            this.lives = lives;
            this.bricksAmount = bricksAmount;
        }

        private void setBall(GOval ball) {
            this.ball = ball;
        }

        private void setVx(double vx) {
            this.vx = vx;
        }

        private void setVy(double vy) {
            this.vy = vy;
        }

        private void setLives(int lives) {
            this.lives = lives;
        }

        private void setBricksAmount(int bricksAmount) {
            this.bricksAmount = bricksAmount;
        }

        private GOval getBall() {
            return this.ball;
        }

        private double getVx() {
            return this.vx;
        }

        private double getVy() {
            return this.vy;
        }

        private int getLives() {
            return this.lives;
        }

        private int getBricksAmount() {
            return this.bricksAmount;
        }

        /**
         * Generates starting velocity of the ball
         */

        private void startingVelocity() {
            setVy(3);

            RandomGenerator rgen = RandomGenerator.getInstance();
            setVx(rgen.nextDouble(1.0, 3.0));

            if (rgen.nextBoolean(0.5)) {

                setVx(-getVx());
            }
        }
    }


    public void run() {

        setBackground(Color.lightGray);
        addMouseListeners();
        addBricks();

        /*Starting parameters of the game*/
        GameValues gameValues = new GameValues(drawStartBall(), 0, 0, NTURNS, NBRICK_ROWS * NBRICKS_PER_ROW);

        while (gameValues.getLives() > 0) {
            playGame(gameValues);
        }
    }

    /*Draw bricks*/
    private void addBricks() {
        double x = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP - NBRICKS_PER_ROW * BRICK_WIDTH) / 2D;
        double y = BRICK_Y_OFFSET;
        int n = 0; //counter to change colors of the bricks
        int c = NBRICK_ROWS / BRICK_COLOR.length; // step of colors


        for (int i = 0; i < NBRICK_ROWS; i++) {
            if (i != 0 && i % c == 0) {
                n++;
            }

            for (int j = 0; j < NBRICKS_PER_ROW; j++) {
                Color color = Color.blue;
                if (n < BRICK_COLOR.length) {
                    color = BRICK_COLOR[n];
                }

                GRect brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
                brick.setColor(color);
                brick.setFilled(true);
                brick.setFillColor(color);
                add(brick);
                x += BRICK_WIDTH + BRICK_SEP;
            }

            y += BRICK_HEIGHT + BRICK_SEP;
            x = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP - NBRICKS_PER_ROW * BRICK_WIDTH) / 2D;
        }
    }

    /**
     * Creating paddle.
     *
     * @return - GRect object
     */

    private GRect addPaddle() {
        GRect paddle = new GRect(WIDTH / 2D - PADDLE_WIDTH / 2D, HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT,
                PADDLE_WIDTH, PADDLE_HEIGHT);

        paddle.setColor(Color.black);
        paddle.setFilled(true);
        paddle.setFillColor(Color.black);
        add(paddle);

        return (paddle);
    }

    /**
     * Listening for mouse movement, if moved - sets center of paddle to X-coordinate of cursor
     */

    public void mouseMoved(MouseEvent move) {
        double paddleX = move.getX() - PADDLE_WIDTH / 2D;
        double paddleY = paddle.getY();

        if (move.getX() > PADDLE_WIDTH / 2D && move.getX() < WIDTH - PADDLE_WIDTH / 2D) {
            paddle.setLocation(paddleX, paddleY);
        }
    }

    /**
     * Creating of ball at the center of game area
     *
     * @return - GOval object
     */

    private GOval drawStartBall() {
        GOval ball = new GOval(WIDTH / 2D - BALL_RADIUS, HEIGHT / 2D - BALL_RADIUS, 2 * BALL_RADIUS, 2 * BALL_RADIUS);
        ball.setColor(Color.black);
        ball.setFilled(true);
        ball.setFillColor(Color.blue);
        add(ball);

        return ball;
    }

    /**
     * Initiating game
     */

    private void playGame(GameValues gameValues) {

        if (gameValues.getVx() == 0) {
            gameValues.startingVelocity();
        }

        if (pause) {
            pauseAnimation();
        }

        while (gameValues.getLives() > 0 && !pause) {
            int bufferLives = gameValues.getLives();

            gameValues.getBall().move(gameValues.getVx(), gameValues.getVy());

            collidingBorders(gameValues);

            if (bufferLives > gameValues.getLives() && gameValues.getLives() > 0) {

                gameValues.startingVelocity();
                remove(gameValues.getBall());
                gameValues.setBall(drawStartBall());
                pause = !pause;
                pauseAnimation();
            }

            GObject collider = getCollidingObject(gameValues.getBall());

            if (collider == paddle) {
                if (gameValues.getBall().getX() + 2 * BALL_RADIUS > collider.getX() &&
                        gameValues.getBall().getX() < collider.getX()) {
                    gameValues.getBall().setLocation
                            (collider.getX() - 2 * BALL_RADIUS, collider.getY() - 2 * BALL_RADIUS);

                    if (gameValues.getVy() > 0) {
                        gameValues.setVy(-gameValues.getVy());
                    }
                    gameValues.setVx(-gameValues.getVx());
                    continue;
                }
                if (gameValues.getBall().getX() + 2 * BALL_RADIUS > collider.getX() + PADDLE_WIDTH &&
                        gameValues.getBall().getX() < collider.getX() + PADDLE_WIDTH) {
                    gameValues.getBall().setLocation
                            (collider.getX()+PADDLE_WIDTH +gameValues.getVx(), collider.getY() - 2 * BALL_RADIUS);

                    if (gameValues.getVy() > 0) {
                        gameValues.setVy(-gameValues.getVy());
                    }
                    gameValues.setVx(-gameValues.getVx());
                    continue;
                }
                gameValues.setVy(-gameValues.getVy());
                continue;
            }

            if (collider != null) {
                gameValues.setVy(-gameValues.getVy());
                remove(collider);
                gameValues.setBricksAmount(gameValues.getBricksAmount() - 1);
                continue;
            }

            if (gameValues.getBricksAmount() == 0) {
                gameValues.setLives(0);
                messageAnimation("YOU WON!\n CONGRATULATIONS!");
                break;
            }

            if (gameValues.getLives() == 0) {
                messageAnimation("GAME OVER!");
                break;
            }

            pause(PAUSE_TIME);
        }
    }

    /**
     * Checking if corners of rectangle that bound the ball are intersecting any object
     *
     * @param ball - GOval object
     * @return collider - GObject object that intersected with the ball
     */

    private GObject getCollidingObject(GOval ball) {
        GObject collider = getElementAt(ball.getX(), ball.getY());

        if (collider == null) {
            collider = getElementAt(ball.getX() + ball.getWidth(), ball.getY());
        }

        if (collider == null) {
            collider = getElementAt(ball.getX(), ball.getY() + ball.getHeight());
        }

        if (collider == null) {
            collider = getElementAt(ball.getX() + ball.getWidth(), ball.getY() + ball.getHeight());
        }
        return collider;
    }

    /**
     * Checking if corners of rectangle that bound the ball are intersecting with borders of game area
     */

    private void collidingBorders(GameValues values) {

        if (values.getBall().getX() < 0 || values.getBall().getX() + 2 * BALL_RADIUS > WIDTH) {
            values.setVx(-values.getVx());
        }

        if (values.getBall().getY() < 0) {
            values.setVy(-values.getVy());
        }

        if (values.getBall().getY() > HEIGHT) {
            values.setLives(values.getLives() - 1);
        }
    }

    /*Message animation*/
    private void messageAnimation(String string) {

        GLabel label = new GLabel(string);
        label.setFont("Times New Roman-" + FONT_SIZE);
        double x = (WIDTH - label.getWidth()) / 2D;
        double y = (HEIGHT - label.getHeight()) / 2D;
        label.setLocation(x, y);
        removeAll();
        add(label);
    }

    /*Pause animation*/
    private void pauseAnimation() {

        GLabel label = new GLabel("CLICK TO START");
        label.setFont("Times New Roman -"+FONT_SIZE);
        double x = (WIDTH - label.getWidth()) / 2D;
        double y = (HEIGHT - label.getHeight()) / 2D;
        label.setLocation(x, y);

        while (pause) {
            add(label);
            pause(ANIMATION_PAUSE / 2D);
            remove(label);
            pause(ANIMATION_PAUSE / 2D);
        }
    }

    /**
     * Listening of mouse. If mouse - clicked, inverts pause value
     */
    public void mouseClicked(MouseEvent click) {
        if (click != null) {
            pause = !pause;
        }
    }
}
