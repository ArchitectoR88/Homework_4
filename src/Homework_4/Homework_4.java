package Homework_4;

import java.util.Random;
import java.util.Scanner;

public class Homework_4 {
    public static char[][] map;
    public static final int SIZE = 3;
    //    public static final int DOTS_TO_WIN = 3;
    public static final char DOT_EMPTY = '_';
    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';
    public static Scanner sc = new Scanner(System.in);
    public static Random rand = new Random();

    public static void humanTurn() {
        int x, y;
        do {
            System.out.println("Введите координаты в формате X Y");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (isCellValid(x, y));
        map[y][x] = DOT_X;
    }

    public static void aiTurn() {
        int x, y;
        do {
            x = rand.nextInt(SIZE);
            y = rand.nextInt(SIZE);
        } while (isCellValid(x, y));
        System.out.println("Компьютер походил в точку " + (x + 1) + " " + (y + 1));
        map[y][x] = DOT_O;
    }

//    public static boolean checkWin(char symb) {
//        if(map[0][0] == symb && map[0][1] == symb && map[0][2] == symb) return true;
//        if(map[1][0] == symb && map[1][1] == symb && map[1][2] == symb) return true;
//        if(map[2][0] == symb && map[2][1] == symb && map[2][2] == symb) return true;
//        if(map[0][0] == symb && map[1][0] == symb && map[2][0] == symb) return true;
//        if(map[0][1] == symb && map[1][1] == symb && map[2][1] == symb) return true;
//        if(map[0][2] == symb && map[1][2] == symb && map[2][2] == symb) return true;
//        if(map[0][0] == symb && map[1][1] == symb && map[2][2] == symb) return true;
//        if(map[2][0] == symb && map[1][1] == symb && map[0][2] == symb) return true;
//        return false;
//    }

    //    2-ой вариант првоерки победы:
    //     Проверяем диагонали
    public static boolean checkDiagonal(char symb) {
        boolean toRight, toLeft;
        toRight = true;
        toLeft = true;
        for (int i = 0; i < 3; i++) {
            toRight &= (map[i][i] == symb);
            toLeft &= (map[3 - i - 1][i] == symb);
        }
        if (toRight || toLeft) return true;
        return false;
    }
    //    Проверяем горизонтальные и вертикальные линии
    private static boolean checkLines(char symb) {
        boolean cols, rows;
        for (int col=0; col<3; col++) {
            cols = true;
            rows = true;
            for (int row=0; row<3; row++) {
                cols &= (map[col][row] == symb);
                rows &= (map[row][col] == symb);
            }
            // Это условие после каждой проверки колонки и столбца
            // позволяет остановить дальнейшее выполнение, без проверки
            // всех остальных столбцов и строк.
            if (cols || rows) return true;
        }
        return false;
    }

    public static boolean isCellValid(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) return true;
        if (map[y][x] == DOT_EMPTY) return false;
        return true;
    }

    public static boolean isMapFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) return false;
            }
        }
        return true;
    }

    public static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    public static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void playAgain() { // Функция повтора игры.
        boolean isExit = false;
        do {
            System.out.println("Повторить игру еще раз? 1 – да / 0 – нет");
            int answer = sc.next().charAt(0);
            if ('0' == answer) {
                isExit = true;
            } else if ('1' == answer) {
                theGame();
            }
        } while (!isExit);
    }

    public static void theGame() {
        initMap();
        printMap();
        while (true) {
            humanTurn();
            printMap();
            if (checkDiagonal(DOT_X)) {
                System.out.println("Победил человек");
                playAgain();
                break;
            }
            else if (checkLines(DOT_X)) {
                System.out.println("Победил человек");
                playAgain();
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                playAgain();
                break;
            }
            aiTurn();
            printMap();
            if (checkDiagonal(DOT_O)) {
                System.out.println("Победил Искуственный Интеллект");
                playAgain();
                break;
            }
            else if (checkLines(DOT_O)) {
                System.out.println("Победил Искуственный Интеллект");
                playAgain();
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                playAgain();
                break;
            }
        }
    }

    public static void main(String[] args) {
        theGame();
        playAgain();
    }
}
