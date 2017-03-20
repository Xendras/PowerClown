
public class YourEvaluator extends Evaluator {

    final int Nx[] = {-2, -2, -1, -1, 1, 1, 2, 2};
    final int Ny[] = {1, -1, 2, -2, 2, -2, 1, -1};
    final int Bx[][] = {{1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7}, {-1, -2, -3, -4, -5, -6, -7}, {-1, -2, -3, -4, -5, -6, -7}};
    final int By[][] = {{1, 2, 3, 4, 5, 6, 7}, {-1, -2, -3, -4, -5, -6, -7}, {1, 2, 3, 4, 5, 6, 7}, {-1, -2, -3, -4, -5, -6, -7}};
    final int Rx[][] = {{0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}, {1, 2, 3, 4, 5, 6, 7}, {-1, -2, -3, -4, -5, -6, -7}};
    final int Ry[][] = {{1, 2, 3, 4, 5, 6, 7}, {-1, -2, -3, -4, -5, -6, -7}, {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}};
    final int Kx[] = {1, 1, 1, 0, 0, -1, -1, -1};
    final int Ky[] = {1, 0, -1, 1, -1, 1, 0, -1};

    final static int bRows = 6;
    final static int bCols = 6;

    final static int Empty = 0;
    final static int WKing = 1;
    final static int WQueen = 2;
    final static int WRook = 3;
    final static int WBishop = 4;
    final static int WKnight = 5;
    final static int WPawn = 6;
    final static int BKing = 7;
    final static int BQueen = 8;
    final static int BRook = 9;
    final static int BBishop = 10;
    final static int BKnight = 11;
    final static int BPawn = 12;
    public static int[] WKingPos = {5, 3};
    public static int[] BKingPos = {0, 3};

    final static double[][] NValues = {{0, 0, 0, 0, 0, 0},
    {50, 50, 50, 50, 50, 50},
    {10, 30, 40, 40, 30, 10},
    {5, -10, 0, 0, -10, 5},
    {5, 10, -20, -20, 10, 5},
    {0, -20, 0, 0, -20, 0}};

    final static double[][] PValues = {{-50, -40, -30, -30, -40, -50},
    {-40, -20, 50, 50, -20, -40},
    {-30, 5, 20, 20, 5, -30},
    {-30, 5, 20, 20, 5, -30},
    {-40, -20, 5, 5, -20, -40},
    {-50, -40, -30, -30, -40, -50}};

    final static double[][] RValues = {{0, 0, 0, 0, 0, 0},
    {5, 10, 10, 10, 10, 5},
    {-5, 0, 0, 0, 0, -5},
    {-5, 0, 0, 0, 0, -5},
    {-5, 0, 0, 0, 0, -5},
    {-5, 0, 5, 5, 0, -5}};

    final static double[][] QValues = {{-20, -10, -5, -5, -10, -20},
    {-10, 0, 0, 0, 0, -10},
    {-5, 0, 5, 5, 0, -5},
    {5, 0, 5, 5, 0, -5},
    {-10, 5, 0, 0, 0, -10},
    {-20, -10, -5, -5, -10, -20}};

    final static double[][] KValuesEnd = {{-50, -40, -20, -20, -40, -50},
    {-30, -20, 0, 0, -20, -30},
    {-30, -10, 30, 30, -10, -30},
    {-30, -10, 30, 30, -10, -30},
    {-30, -30, 0, 0, -30, -30},
    {-50, -30, -30, -30, -30, -50}};

    final static double[][] KValues = {{-30, -40, -50, -50, -40, -30},
    {-30, -40, -50, -50, -40, -30},
    {-20, -30, -40, -40, -30, -20},
    {-10, -20, -20, -20, -20, -10},
    {20, 20, 0, 0, 20, 20},
    {20, 30, 10, 10, 30, 20}};

    public static double[][] WControlled = {{0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0}};

    public static double[][] BControlled = {{0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0}};

    public double eval(Position p) {
        double mat = getMaterialWhite(p) - getMaterialBlack(p);
        double ret = checkWin(p) + checkLoss(p) + mat + 10 * getNextPositions(p) + kingSafety(p) + kingAttack(p);
        BControlled = new double[6][6];
        WControlled = new double[6][6];
        return ret;
    }

    public double checkWin(Position p) {
        double king = 1000000;
        for (int x = 0; x < p.board.length; x++) {
            for (int y = 0; y < p.board.length; y++) {
                if (p.board[x][y] == BKing) {
                    king = 0;
                }
            }
        }
        return king;
    }

    public double checkLoss(Position p) {
        double king = -1000000;
        for (int x = 0; x < p.board.length; x++) {
            for (int y = 0; y < p.board.length; y++) {
                if (p.board[x][y] == WKing) {
                    king = 0;
                }
            }
        }
        return king;
    }

    public double kingSafety(Position p) {
        double safety = 0;
        double danger = 0;
        int moves = 0;
        for (int i = 0; i < Kx.length; ++i) {
            int x2 = Kx[i] + WKingPos[0];
            int y2 = Ky[i] + WKingPos[1];
            if (!isInside(x2, y2)) {
                continue;
            }
            if (squaresContainSameColoredPieces(WKingPos[0], WKingPos[1], x2, y2, p)) {
                continue;
            }
            moves++;
            if (WControlled[x2][y2] - BControlled[x2][y2] > 0) {
                safety++;
            } else {
                danger++;
            }

        }
        if (moves == 0) {
            return -250;
        }
        return -500 * danger / moves;
    }

    public double kingAttack(Position p) {
        double attack = 0;
        double defense = 0;
        int moves = 0;
        for (int i = 0; i < Kx.length; ++i) {
            int x2 = Kx[i] + BKingPos[0];
            int y2 = Ky[i] + BKingPos[1];
            if (!isInside(x2, y2)) {
                continue;
            }
            if (squaresContainSameColoredPieces(BKingPos[0], BKingPos[1], x2, y2, p)) {
                continue;
            }
            moves++;
            if (WControlled[x2][y2] - BControlled[x2][y2] > 0) {
                attack++;
            } else {
                defense++;
            }

        }
        if (moves == 0) {
            return 250;
        }
        return 500 * attack / moves;
    }

    public boolean checkQueensDead(Position p) {
        for (int x = 0; x < p.board.length; x++) {
            for (int y = 0; y < p.board.length; y++) {
                if (p.board[x][y] == WQueen || p.board[x][y] == BQueen) {
                    return false;
                }
            }
        }
        return true;
    }

    public double getMaterialWhite(Position p) {
        double white = 0;
        boolean endgame = checkQueensDead(p);
        for (int x = 0; x < p.board.length; x++) {
            for (int y = 0; y < p.board.length; y++) {
                if (p.board[x][y] == p.WQueen) {
                    white += 880 + QValues[x][y];
                }
                if (p.board[x][y] == p.WRook) {
                    white += 500 + RValues[x][y];
                    /* Rook file and row dominance. Not worth it.
                    boolean emptyFile = true;
                    boolean emptyRow = true;
                    for (int i = 0; i < p.board.length; i++) {
                        if (p.board[x][i] != WRook && p.board[x][i] != Empty) {
                            emptyFile = false;
                        }
                        if (p.board[i][x] != WRook && p.board[i][x] != Empty) {
                            emptyRow = false;
                        }
                    }
                    if (!emptyFile) {
                        white += 30;
                    }
                    if (!emptyFile) {
                        white += 10;
                    }
                     */

                }
                if (p.board[x][y] == p.WKnight) {
                    white += 350 + NValues[x][y];
                }
                if (p.board[x][y] == p.WPawn) {
                    white += 100 + PValues[x][y];
                    for (int z = 0; z < p.board.length; z++) {
                        if (p.board[z][y] == p.WPawn && z != x) {
                            white -= PValues[z][y] / 2 + 30;
                        }
                    }
                }
                if (p.board[x][y] == p.WKing) {
                    WKingPos[0] = x;
                    WKingPos[1] = y;
                    if (endgame) {
                        white += 20000 + KValuesEnd[x][y];
                    } else {
                        white += 20000 + KValues[x][y];
                    }

                }

            }
        }
        return white;
    }

    public double getMaterialBlack(Position p) {
        double black = 0;
        for (int x = 0; x < p.board.length; x++) {
            for (int y = 0; y < p.board.length; y++) {
                if (p.board[x][y] == p.BQueen) {
                    black += 880 + QValues[5 - x][y];
                }
                if (p.board[x][y] == p.BRook) {
                    black += 510 + RValues[5 - x][y];
                    /* Rook file and row dominance. Not worth it.
                    boolean emptyFile = true;
                    boolean emptyRow = true;
                    for (int i = 0; i < p.board.length; i++) {
                        if (p.board[x][i] != BRook && p.board[x][i] != Empty) {
                            emptyFile = false;
                        }
                        if (p.board[i][x] != BRook && p.board[i][x] != Empty) {
                            emptyRow = false;
                        }
                    }
                    if (!emptyFile) {
                        black += 30;
                    }
                    if (!emptyFile) {
                        black += 10;
                    } 
                    */
                }
                if (p.board[x][y] == p.BKnight) {
                    black += 320 + NValues[5 - x][y];
                }
                if (p.board[x][y] == p.BPawn) {
                    black += 100 + PValues[5 - x][y];
                    for (int z = 0; z < p.board.length; z++) {
                        if (p.board[z][y] == p.BPawn && z != x) {
                            black -= PValues[5 - z][y] / 2 + 30;
                        }
                    }
                }
                if (p.board[x][y] == p.BKing) {
                    BKingPos[0] = x;
                    BKingPos[1] = y;
                    if (checkQueensDead(p)) {
                        black += 20000 + KValuesEnd[5 - x][y];
                    } else {
                        black += 20000 + KValues[5 - x][y];
                    }

                }

            }
        }
        return black;
    }

    public boolean isInside(int x, int y) {
        if (x < 0 || x >= bCols) {
            return false;
        }
        if (y < 0 || y >= bRows) {
            return false;
        }
        return true;
    }

    public boolean squaresContainSameColoredPieces(int x, int y, int x2, int y2, Position p) {
        if (isWhitePiece(p.board[x][y]) && isWhitePiece(p.board[x2][y2])) {
            return true;
        }
        if (isBlackPiece(p.board[x][y]) && isBlackPiece(p.board[x2][y2])) {
            return true;
        }
        return false;
    }

    public static boolean isWhitePiece(int pval) {
        if (pval == 0) {
            return false;
        }
        if (pval < 7) {
            return true;
        }
        return false;
    }

    public static boolean isBlackPiece(int pval) {
        if (pval == 0) {
            return false;
        }
        if (pval > 6) {
            return true;
        }
        return false;
    }

    public int getNextPositions(Position p) {
        int whiteMoves = 0;
        int blackMoves = 0;
        for (int x = 0; x < p.board.length; ++x) {
            for (int y = 0; y < p.board[x].length; ++y) {
                int pval = p.board[x][y];

                if (pval == Empty) {
                    continue;
                }

                if (isBlackPiece(pval)) {
                    BControlled[x][y] += 1;
                } else {
                    WControlled[x][y] += 1;
                }

                if (pval == WKing || pval == BKing) {
                    for (int i = 0; i < Kx.length; ++i) {
                        int x2 = Kx[i] + x;
                        int y2 = Ky[i] + y;
                        if (!isInside(x2, y2)) {
                            continue;
                        }
                        if (squaresContainSameColoredPieces(x, y, x2, y2, p)) {
                            if (pval == WKing) {
                                WControlled[x2][y2] += 1;
                            } else {
                                BControlled[x2][y2] += 1;
                            }
                            continue;
                        }
                        if (pval == WKing) {
                            WControlled[x2][y2] += 1;
                            whiteMoves++;
                        } else {
                            blackMoves++;
                            WControlled[x2][y2] += 1;
                        }
                    }
                    continue;
                }

                if (pval == WQueen || pval == BQueen) {
                    for (int i = 0; i < Bx.length; ++i) {
                        for (int j = 0; j < Bx[i].length; ++j) {
                            int x2 = Bx[i][j] + x;
                            int y2 = By[i][j] + y;
                            if (!isInside(x2, y2)) {
                                break;
                            }
                            if (squaresContainSameColoredPieces(x, y, x2, y2, p)) {
                                if (pval == WQueen) {
                                    WControlled[x2][y2] += 1;
                                } else {
                                    BControlled[x2][y2] += 1;
                                }
                                break;
                            }
                            if (pval == WQueen) {
                                WControlled[x2][y2] += 1;
                                whiteMoves++;
                            } else {
                                blackMoves++;
                                WControlled[x2][y2] += 1;
                            }

                            if (p.board[x2][y2] != Empty) {
                                break;
                            }
                        }
                    }
                    for (int i = 0; i < Rx.length; ++i) {
                        for (int j = 0; j < Rx[i].length; ++j) {
                            int x2 = Rx[i][j] + x;
                            int y2 = Ry[i][j] + y;
                            if (!isInside(x2, y2)) {
                                break;
                            }
                            if (squaresContainSameColoredPieces(x, y, x2, y2, p)) {
                                if (pval == WQueen) {
                                    WControlled[x2][y2] += 1;
                                } else {
                                    BControlled[x2][y2] += 1;
                                }
                                break;
                            }
                            if (pval == WQueen) {
                                WControlled[x2][y2] += 1;
                                whiteMoves++;
                            } else {
                                blackMoves++;
                                WControlled[x2][y2] += 1;
                            }

                            if (p.board[x2][y2] != Empty) {
                                break;
                            }
                        }
                    }
                    continue;
                }

                if (pval == WRook || pval == BRook) {
                    for (int i = 0; i < Rx.length; ++i) {
                        for (int j = 0; j < Rx[i].length; ++j) {
                            int x2 = Rx[i][j] + x;
                            int y2 = Ry[i][j] + y;
                            if (!isInside(x2, y2)) {
                                break;
                            }
                            if (squaresContainSameColoredPieces(x, y, x2, y2, p)) {
                                if (pval == WRook) {
                                    WControlled[x2][y2] += 1;
                                } else {
                                    BControlled[x2][y2] += 1;
                                }
                                break;
                            }
                            if (pval == WRook) {
                                WControlled[x2][y2] += 1;
                                whiteMoves++;
                            } else {
                                blackMoves++;
                                WControlled[x2][y2] += 1;
                            }

                            if (p.board[x2][y2] != Empty) {
                                break;
                            }
                        }
                    }
                    continue;
                }

                if (pval == WKnight || pval == BKnight) {
                    for (int i = 0; i < Nx.length; ++i) {
                        int x2 = Nx[i] + x;
                        int y2 = Ny[i] + y;
                        if (!isInside(x2, y2)) {
                            continue;
                        }
                        if (squaresContainSameColoredPieces(x, y, x2, y2, p)) {
                            if (pval == WKnight) {
                                WControlled[x2][y2] += 1;
                            } else {
                                BControlled[x2][y2] += 1;
                            }
                            continue;
                        }
                        if (pval == WKnight) {
                            WControlled[x2][y2] += 1;
                            whiteMoves++;
                        } else {
                            blackMoves++;
                            WControlled[x2][y2] += 1;
                        }
                    }
                    continue;
                }

                if (pval == WPawn) {
                    boolean allowedMoves[] = new boolean[4];
                    // 1 step forward
                    allowedMoves[0] = isInside(x, y + 1) && p.board[x][y + 1] == Empty;
                    // eat left
                    allowedMoves[2] = isInside(x - 1, y + 1) && isBlackPiece(p.board[x - 1][y + 1]);
                    // eat right
                    allowedMoves[3] = isInside(x + 1, y + 1) && isBlackPiece(p.board[x + 1][y + 1]);

                    if (allowedMoves[0]) {
                        whiteMoves++;
                        WControlled[x][y + 1] += 1;
                    }

                    if (allowedMoves[2]) {
                        whiteMoves++;
                        WControlled[x - 1][y + 1] += 1;
                    }

                    if (allowedMoves[3]) {
                        whiteMoves++;
                        WControlled[x + 1][y + 1] += 1;
                    }

                    continue;
                }

                if (pval == BPawn) {
                    boolean allowedMoves[] = new boolean[4];
                    // 1 step forward
                    allowedMoves[0] = isInside(x, y - 1) && p.board[x][y - 1] == Empty;
                    // eat right
                    allowedMoves[2] = isInside(x - 1, y - 1) && isWhitePiece(p.board[x - 1][y - 1]);
                    // eat left
                    allowedMoves[3] = isInside(x + 1, y - 1) && isWhitePiece(p.board[x + 1][y - 1]);

                    if (allowedMoves[0]) {
                        blackMoves++;
                        BControlled[x][y - 1] += 1;
                    }

                    if (allowedMoves[2]) {
                        blackMoves++;
                        BControlled[x - 1][y - 1] += 1;
                    }

                    if (allowedMoves[3]) {
                        blackMoves++;
                        BControlled[x + 1][y - 1] += 1;
                    }

                    continue;
                }
            }
        }

        return whiteMoves - blackMoves;
    }

}
