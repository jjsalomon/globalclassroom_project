package com.chess.gui;



//import static com.chess.pgn.PGNUtilities.*;
import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;
import static javax.swing.SwingUtilities.invokeLater;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import com.chess.engine.board.*;

import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Player;

import com.google.common.collect.Lists;

class DebugPanel extends JPanel implements Observer {

    private static final Dimension CHAT_PANEL_DIMENSION = new Dimension(600, 150);
    private final JTextArea jTextArea;

    public DebugPanel() {
        super(new BorderLayout());
        this.jTextArea = new JTextArea("");
        add(this.jTextArea);
        setPreferredSize(CHAT_PANEL_DIMENSION);
        validate();
        setVisible(true);
    }

    public void redo() {
        validate();
    }

    @Override
    public void update(final Observable obs,
                       final Object obj) {
        this.jTextArea.setText(obj.toString().trim());
        redo();
    }

}
