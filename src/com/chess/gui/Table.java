package com.chess.gui;

import com.chess.engine.board.*;
import com.chess.engine.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;
import static javax.swing.SwingUtilities.invokeLater;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.player.Player;
import com.frames.gui.SingletonAccount;
import com.frames.network.sConnectListenHandler;
import com.frames.resource.MoveBuffer;
import com.google.common.collect.Lists;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Observable;
import javax.swing.filechooser.FileFilter;


public final class Table extends Observable implements Runnable {

    public final JFrame gameFrame;
    private final GameHistoryPanel gameHistoryPanel;
    private final TakenPiecesPanel takenPiecesPanel;

    private final BoardPanel boardPanel;
    private final MoveLog moveLog;

    private Board chessBoard;
    private Move computerMove;

    private Tile sourceTile;
    private Tile destinationTile;


    private int SourceT;
    private int DestT;

    private Piece humanMovedPiece;
    private BoardDirection boardDirection;
    private String pieceIconPath;

    private Color lightTileColor = Color.decode("#FFFACD");
    private Color darkTileColor = Color.decode("#593E1A");

    private String getCurrentPlayer;
    private String getPieceAllegianceClicked;

    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
    private static String Challenger;
    private static String Challenged;

    private static Table INSTANCE;
    sConnectListenHandler sclh = sConnectListenHandler.getInstance();
    //    ConnectListenHandler connectListenHandler;
    MoveBuffer moveBuffer = MoveBuffer.getFirstInstance();
    SingletonAccount sgaccount = SingletonAccount.getFirstInstance();


    private Table() {
//        connectListenHandler = new ConnectListenHandler();
        this.gameFrame = new JFrame("Chess Master ~ " + sgaccount.username.getText());
        this.gameFrame.setLocationRelativeTo(sgaccount);
        final JMenuBar tableMenuBar = new JMenuBar();
        populateMenuBar(tableMenuBar);
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setLayout(new BorderLayout());
        this.chessBoard = Board.createStandardBoard();
        this.Challenger = moveBuffer.getChallenger();
        this.Challenged = moveBuffer.getChallenged();

        System.out.println("Challenger In table" + this.Challenger);
        System.out.println("Challenged In table" + this.Challenged);

        if (Challenger.equals(sgaccount.username.getText())) {
            this.boardDirection = BoardDirection.NORMAL;
        }
        if (Challenged.equals(sgaccount.username.getText())) {
            this.boardDirection = BoardDirection.FLIPPED;
        }


        this.pieceIconPath = "art/holywarriors/";
        this.gameHistoryPanel = new GameHistoryPanel();

        this.takenPiecesPanel = new TakenPiecesPanel();
        this.boardPanel = new BoardPanel();
        this.moveLog = new MoveLog();



        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);

        setDefaultLookAndFeelDecorated(true);
        this.gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);

        this.gameFrame.setVisible(true);


        //when user wants to log out by X close button
        this.gameFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {

                int confirm = JOptionPane.showOptionDialog(gameFrame,
                        "Are you sure you want exit game",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.out.println("CLOSING GAME");
                    //for some reason it wont show new board when invited again
                    try {
                        sclh.writer.println("Interrupt:" + sgaccount.username.getText() + ":" + moveBuffer.getChallenged() + ":" + moveBuffer.getChallenger());
                        sclh.writer.flush();
                    } catch (Exception ex) {
                        System.out.println("You cannot log out, Try again");
                        ex.printStackTrace();
                    }
                }
                if (confirm == JOptionPane.NO_OPTION) {
                    gameFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }
                if (confirm == JOptionPane.CLOSED_OPTION) {
                    gameFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }


    public static Table get() {
        return INSTANCE;
    }

    public static void setNull() {
        INSTANCE = null;
    }

    public static void set() {
        INSTANCE = new Table();
    }

    private JFrame getGameFrame() {
        return this.gameFrame;
    }

    private Board getGameBoard() {
        return this.chessBoard;
    }

    private MoveLog getMoveLog() {
        return this.moveLog;
    }

    private BoardPanel getBoardPanel() {
        return this.boardPanel;
    }

    private GameHistoryPanel getGameHistoryPanel() {
        return this.gameHistoryPanel;
    }

    private TakenPiecesPanel getTakenPiecesPanel() {
        return this.takenPiecesPanel;
    }




    public void show() {
        Table.get().getMoveLog().clear();
        Table.get().getGameHistoryPanel().redo(chessBoard, Table.get().getMoveLog());
        Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
        Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());

        sgaccount.setVisible(false);
    }

    private void populateMenuBar(final JMenuBar tableMenuBar) {

        tableMenuBar.add(createPreferencesMenu());

    }

    private static void center(final JFrame frame) {
        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        final int w = frame.getSize().width;
        final int h = frame.getSize().height;
        final int x = (dim.width - w) / 2;
        final int y = (dim.height - h) / 2;
        frame.setLocation(x, y);
    }


    private JMenu createPreferencesMenu() {

        final JMenu preferencesMenu = new JMenu("Preferences");

        final JMenu colorChooserSubMenu = new JMenu("Choose Colors");
        colorChooserSubMenu.setMnemonic(KeyEvent.VK_S);

        final JMenuItem chooseDarkMenuItem = new JMenuItem("Choose Dark Tile Color");
        colorChooserSubMenu.add(chooseDarkMenuItem);

        final JMenuItem chooseLightMenuItem = new JMenuItem("Choose Light Tile Color");
        colorChooserSubMenu.add(chooseLightMenuItem);



        preferencesMenu.add(colorChooserSubMenu);

        chooseDarkMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final Color colorChoice = JColorChooser.showDialog(Table.get().getGameFrame(), "Choose Dark Tile Color",
                        Table.get().getGameFrame().getBackground());
                if (colorChoice != null) {
                    Table.get().getBoardPanel().setTileDarkColor(chessBoard, colorChoice);
                }
            }
        });

        chooseLightMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final Color colorChoice = JColorChooser.showDialog(Table.get().getGameFrame(), "Choose Light Tile Color",
                        Table.get().getGameFrame().getBackground());
                if (colorChoice != null) {
                    Table.get().getBoardPanel().setTileLightColor(chessBoard, colorChoice);
                }
            }
        });

        return preferencesMenu;

    }


    private class BoardPanel extends JPanel {

        final List<TilePanel> boardTiles;

        BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setBackground(Color.decode("#8B4726"));
            validate();
        }

        public void drawBoard(final Board board) {
            removeAll();
            for (final TilePanel boardTile : boardDirection.traverse(boardTiles)) {
                boardTile.drawTile(board);
                add(boardTile);
            }
            validate();
            repaint();
        }

        public void setTileDarkColor(final Board board,
                                     final Color darkColor) {
            for (final TilePanel boardTile : boardTiles) {
                boardTile.setDarkTileColor(darkColor);
            }
            drawBoard(board);
        }

        public void setTileLightColor(final Board board,
                                      final Color lightColor) {
            for (final TilePanel boardTile : boardTiles) {
                boardTile.setLightTileColor(lightColor);
            }
            drawBoard(board);
        }

    }

    public enum BoardDirection {
        NORMAL {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles) {
                return boardTiles;
            }

            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }

            @Override
            BoardDirection opposite() {
                return NORMAL;
            }
        };

        abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);

        abstract BoardDirection opposite();

    }

    public static class MoveLog {

        private final List<Move> moves;

        MoveLog() {
            this.moves = new ArrayList<>();
        }

        public List<Move> getMoves() {
            return this.moves;
        }

        public void addMove(final Move move) {
            this.moves.add(move);
        }

        public int size() {
            return this.moves.size();
        }

        public void clear() {
            this.moves.clear();
        }


    }

    private class TilePanel extends JPanel {

        private final int tileId;

        TilePanel(final BoardPanel boardPanel,
                  final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);
            highlightTileBorder(chessBoard);

            // this instance passes the data so if you hard code 52 it'll get the sourcetile of the 5th pawn behind the king
            // uncomment these two piece of code and click on the gui to see the change automatically because the tileid was entered

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent event) {
                    // transformed those two values to string


                    // sourceTile = chessBoard.getTile(52);
                    // destinationTile = chessBoard.getTile(36);

                    if (isLeftMouseButton(event)) {


                        if (sourceTile == null) {

                            //sourcetile equal the
                            //ChessBoard is an istanc of board
                            // to get the Piece in the source tile a method in board is used getTile which
                            // takes on parameter thats gonna be the position of the tile clicked
                            sourceTile = chessBoard.getTile(tileId);

                            SourceT = tileId;

                            //getPosition =tileId;

                            //humanMovedPiece is to check if human clicked on a tile with a piece
                            //sourceTile.getPiece() will return the piece clicked
                            humanMovedPiece = sourceTile.getPiece();

                            getCurrentPlayer = humanMovedPiece.getPieceAllegiance().toString();
                            getPieceAllegianceClicked = chessBoard.currentPlayer().toString();

                            if ((Challenger.equals(sgaccount.username.getText()))) {

                                if (getCurrentPlayer.equals("Black")) {
                                    sourceTile = null;
                                    humanMovedPiece = null;
                                    destinationTile = null;
                                }

                                System.out.println("insideI" + Challenger);
                            }
                            if (Challenged.equals(sgaccount.username.getText())) {
                                if (getCurrentPlayer.equals("White")) {
                                    sourceTile = null;
                                    humanMovedPiece = null;
                                    destinationTile = null;
                                    System.out.println("insideIC" + Challenged);
                                }
                            }


                            if (humanMovedPiece == null) {
                                sourceTile = null;

                            }

                            System.out.println("this is SourceT" + sourceTile);
                            System.out.println("this is Tiled" + tileId);
                            System.out.println("this is humand moved P" + humanMovedPiece);
                            System.out.println("Piece Clicked" + getCurrentPlayer);
                            System.out.println("Current Player" + getPieceAllegianceClicked);


                        } else {

                            // uncomment this piece of code to get the chess engine running properly


                            destinationTile = chessBoard.getTile(tileId);
                            DestT = tileId;


                            System.out.println("Destination: " + tileId);


                            //it checks if destination tile is equal to source tile if yah then set the instances do null

                            System.out.println("Piece Clicked: " + getCurrentPlayer);
                            System.out.println("Current Player: " + getPieceAllegianceClicked);


                            // MoveFactory this class will check for all the legal moves when you clickn on a piece
                            // if user chooses a legal move the it returns the move if not the it returns a
                            // nullVale theres a boolean ismovelegal if thats true it draws the piece o the
                            // board  if not it deselect the sourcetile instance and other instances


//                                System.out.println("Tile Dest" +destinationTile.getTileCoordinate());

                            // System.out.println("Tile Source: " +sourceTile.getTileCoordinate());
                            final Move move = MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(),
                                    destinationTile.getTileCoordinate());


                            System.out.println("Get Boolean: " + MoveFactory.getBollean());
                            System.out.println("This is the legalMoveChosen: " + move);

                            //String moveee =  move.toString();
                            // System.out.println("nullmoveeee"+chessBoard.getAllLegalMoves());
                            // that checks if the boolean is true of false if true then the move can be done if not #
                            // then it will set the sourceTile destinationtile and humanmovePiece to null
                            if (MoveFactory.getBollean().equals(true)) {

                                try {

                                    sclh.writer.println("Move" + ":" + moveBuffer.getSend() + ":" + moveBuffer.getFrom() + ":" + SourceT + ":" + DestT);
                                    sclh.writer.flush();


                                } catch (Exception ex) {
                                    System.out.println("You Cannot send data try again");
                                    ex.printStackTrace();
                                }
                                //Read response information from server
                                sclh.ListenThread();



                                final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);


                                System.out.println("Alliance: " + chessBoard.currentPlayer().getAlliance());
                                if (transition.getMoveStatus().isDone()) {

                                    // board will be rendered again and will add move
                                    chessBoard = transition.getToBoard();

                                    //this add the move to the movelog
                                    moveLog.addMove(move);
                                }
                            }


                            sourceTile = null;
                            destinationTile = null;
                            humanMovedPiece = null;
                        }
                    }


                    invokeLater(new Runnable() {
                        public void run() {
                            gameHistoryPanel.redo(chessBoard, moveLog);
                            takenPiecesPanel.redo(moveLog);

                            //this will redraw the board
                            boardPanel.drawBoard(chessBoard);
                            System.out.println("endGame"+BoardUtils.isEndGame(chessBoard));

                            System.out.println("Piece Clicked: " + getCurrentPlayer);
                            System.out.println("Current Player: " + getPieceAllegianceClicked);

                            //print the winner to the screen

                            if(getPieceAllegianceClicked.equals("White") && BoardUtils.isEndGame(chessBoard) ==true)
                            {
                                System.out.println(""+Challenger+ " Wins");
                                sclh.writer.println("Win"+":"+Challenger+":"+"Lose"+":"+Challenged);
                                sclh.writer.flush();

                            }
                            if(getPieceAllegianceClicked.equals("Black")&& BoardUtils.isEndGame(chessBoard) ==true)
                            {
                                System.out.println(""+Challenged+ " Wins");
                                sclh.writer.println("Win"+":"+Challenged+":"+"Lose"+":"+Challenger);
                                sclh.writer.flush();
                            }

                        }
                    });
                }

                @Override
                public void mouseExited(final MouseEvent e) {
                }

                @Override
                public void mouseEntered(final MouseEvent e) {
                }

                @Override
                public void mouseReleased(final MouseEvent e) {
                }

                @Override
                public void mousePressed(final MouseEvent e) {
                }
            });
            validate();
        }


        public void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            highlightTileBorder(board);
            validate();
            repaint();
        }

        public void setLightTileColor(final Color color) {
            lightTileColor = color;
        }

        public void setDarkTileColor(final Color color) {
            darkTileColor = color;
        }

        private void highlightTileBorder(final Board board) {
            if (humanMovedPiece != null &&
                    humanMovedPiece.getPieceAllegiance() == board.currentPlayer().getAlliance() &&
                    humanMovedPiece.getPiecePosition() == this.tileId) {
                setBorder(BorderFactory.createLineBorder(Color.cyan));
            } else {
                setBorder(BorderFactory.createLineBorder(Color.GRAY));
            }
        }



        private void assignTilePieceIcon(final Board board) {
            this.removeAll();
            if (board.getTile(this.tileId).isTileOccupied()) {
                try {
                    final BufferedImage image = ImageIO.read(new File(pieceIconPath +
                            board.getTile(this.tileId).getPiece().getPieceAllegiance().toString().substring(0, 1) + "" +
                            board.getTile(this.tileId).getPiece().toString() +
                            ".gif"));
                    add(new JLabel(new ImageIcon(image)));
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void assignTileColor() {
            if (BoardUtils.INSTANCE.FIRST_ROW.get(this.tileId) ||
                    BoardUtils.INSTANCE.THIRD_ROW.get(this.tileId) ||
                    BoardUtils.INSTANCE.FIFTH_ROW.get(this.tileId) ||
                    BoardUtils.INSTANCE.SEVENTH_ROW.get(this.tileId)) {
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            } else if (BoardUtils.INSTANCE.SECOND_ROW.get(this.tileId) ||
                    BoardUtils.INSTANCE.FOURTH_ROW.get(this.tileId) ||
                    BoardUtils.INSTANCE.SIXTH_ROW.get(this.tileId) ||
                    BoardUtils.INSTANCE.EIGHTH_ROW.get(this.tileId)) {
                setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }
    }


    @Override
    public void run() {
        System.out.println("Source" + moveBuffer.getSourceTile());
        System.out.println("DestinInrun" + moveBuffer.getDestinationTile());


    }


    public static class RenderBoard implements Runnable {

        String client1, client2,
                src, desti;





        public RenderBoard(String client1, String client2, String src, String dest) {
            this.client1 = client1;
            this.client2 = client2;
            this.src = src;
            this.desti = dest;


        }

        @Override
        public void run() {

            //render board method
            int sourc;
            int dest;


            sourc = Integer.parseInt(Table.get().moveBuffer.getSourceTile());
            dest = Integer.parseInt(Table.get().moveBuffer.getDestinationTile());


            System.out.println("SourceT" + sourc);
            System.out.println("dest: " + dest);


            Table.get().sourceTile = Table.get().chessBoard.getTile(sourc);
            Table.get().destinationTile = Table.get().chessBoard.getTile(dest);

            System.out.println("SourceT" + Table.get().sourceTile);
            System.out.println("dest: " + Table.get().destinationTile);


            //sourceTile = chessBoard.getTile(52);
            //destinationTile = chessBoard.getTile(36);

            // System.out.println("Tile Source: " +sourceTile.getTileCoordinate());

            final Move move = MoveFactory.createMove(Table.get().chessBoard, Table.get().sourceTile.getTileCoordinate(),
                    Table.get().destinationTile.getTileCoordinate());


            System.out.println("F Move: " + move);

            System.out.println("This is the legalMoveChosen: " + Table.get().destinationTile);
            //chessBoard.currentPlayer();

            //String moveee =  move.toString();
            // System.out.println("nullmoveeee"+chessBoard.getAllLegalMoves());
            // that checks if the boolean is true of false if true then the move can be done if not #
            // then it will set the sourceTile destinationtile and humanmovePiece to null


            if (MoveFactory.getBollean().equals(true)) {

                //Read response information from server


                final MoveTransition transition = Table.get().chessBoard.currentPlayer().makeMove(move);


                System.out.println("Alliance: " + Table.get().chessBoard.currentPlayer().getAlliance());
                if (transition.getMoveStatus().isDone()) {

                    // board will be rendered again and will add move
                    Table.get().chessBoard = transition.getToBoard();


                }

                //moveBuffer.setSwitchboolean(false);
                System.out.println("Get Boolean: " + MoveBuffer.getBoolean());
            }


            Table.get().sourceTile = null;
            Table.get().destinationTile = null;
            Table.get().humanMovedPiece = null;


            //boardPanel.drawBoard(chessBoard);
            invokeLater(new Runnable() {
                public void run() {

                    //this will redraw the board
                    Table.get().takenPiecesPanel.redo(Table.get().moveLog);
                    //this add the move to the movelog
                    Table.get().moveLog.addMove(move);
                    Table.get().getBoardPanel().drawBoard(Table.get().chessBoard);


                }
            });


        }
    }
}
