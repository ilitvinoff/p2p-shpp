package com.shpp.p2p.cs.ilitvinov.assignment6.Assignment6Part2.teest.gui;
import acm.gui.TablePanel;

import java.awt.Dimension;
import javax.swing.JTextArea;

import com.shpp.p2p.cs.ilitvinov.assignment6.Assignment6Part2.teest.TeestCase;
import com.shpp.p2p.cs.ilitvinov.assignment6.Assignment6Part2.teest.TeestResult;

public class TeestCaseRow extends TablePanel {
    private ResultBox resultBox = new ResultBox();
    private JTextArea text;

    public TeestCaseRow(TeestCase test) {
        super(1, 2);
        this.text = new JTextArea(test.getName());
        this.text.setEditable(false);
        this.text.setLineWrap(true);
        this.text.setWrapStyleWord(true);
        this.text.setPreferredSize(new Dimension(150, 50));
        this.add(this.resultBox);
        this.add(this.text);
    }

    public void setResult(TeestResult result) {
        this.resultBox.setResult(result);
    }
}
