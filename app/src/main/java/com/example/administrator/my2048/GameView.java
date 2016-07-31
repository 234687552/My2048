package com.example.administrator.my2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class GameView extends GridView {
    private GridViewAdapter adapter;
    private CellView[][] cellViews;
    private int cellSpace;

    public int getScore() {
        return score;
    }

    private int score = 0;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialise();
    }

    //初始化游戏，
    private void initialise() {
        setNumColumns(4);
        cellViews = new CellView[4][4];
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                cellViews[x][y] = new CellView(getContext());
            }
        }
        addRandomNum();
        addRandomNum();
        adapter = new GridViewAdapter();
        setAdapter(adapter);

    }

    //初始自匹配屏幕
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cellSpace=w/44;
        setHorizontalSpacing(cellSpace);
        setVerticalSpacing(cellSpace);
        setPadding(cellSpace, cellSpace, cellSpace, cellSpace);
    }

    //右移处理
    public void swipeRight() {

        boolean addRamNum = false;
        for (int x = 0; x < 4; x++) {
            //从右边开始往左边进行
            for (int y = 3; y >= 0; y--) {
                //当前值为0的时候 直接从右边拽一个数过来，然后跳出循环并重新检查该cell
                if (cellViews[x][y].getNumber() == 0) {
                    //以下是x,y格 的右边格子
                    for (int i = (y - 1); i >= 0; i--) {
                        if (cellViews[x][i].getNumber() != 0) {
                            //先把非0的cell拉到右边空cell，然后y++重新检验该cell
                            cellViews[x][y].setNumber(cellViews[x][i].getNumber());
                            cellViews[x][i].setNumber(0);
                            y++;
                            addRamNum = true;
                            break;
                        }
                    }
                }
                //当前格子不为0的时候判断x，y格子右边的格子，非0且相等就合并然后跳出循环，非0不等就直接跳出循环
                else {
                    for (int i = y - 1; i >= 0; i--) {
                        if (cellViews[x][i].getNumber() != 0) {
                            if (cellViews[x][i].getNumber() == cellViews[x][y].getNumber()) {
                                cellViews[x][y].setNumber(cellViews[x][i].getNumber() * 2);
                                cellViews[x][i].setNumber(0);
                                score = score + cellViews[x][y].getNumber();
                                addRamNum = true;
                            }
                            break;
                        }
                    }
                }
            }
        }
        if (addRamNum) {
            addRandomNum();
            checkOver();
        }
    }

    //左移处理
    public void swipeLeft() {
        boolean addRamNum = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (cellViews[x][y].getNumber() == 0) {
                    for (int i = (y + 1); i < 4; i++) {
                        if (cellViews[x][i].getNumber() != 0) {
                            cellViews[x][y].setNumber(cellViews[x][i].getNumber());
                            cellViews[x][i].setNumber(0);
                            y--;
                            addRamNum = true;
                            break;
                        }
                    }
                } else {
                    for (int i = (y + 1); i < 4; i++) {
                        if (cellViews[x][i].getNumber() != 0) {
                            if (cellViews[x][i].getNumber() == cellViews[x][y].getNumber()) {
                                cellViews[x][y].setNumber(cellViews[x][i].getNumber() * 2);
                                cellViews[x][i].setNumber(0);
                                addRamNum = true;
                                score = score + cellViews[x][y].getNumber();

                            }
                            break;
                        }
                    }
                }
            }
        }
        if (addRamNum) {
            addRandomNum();
            checkOver();
        }
    }

    //上移处理
    public void swipeUp() {
        boolean addRamNum = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cellViews[x][y].getNumber() == 0) {
                    for (int i = (x + 1); i < 4; i++) {
                        if (cellViews[i][y].getNumber() != 0) {
                            cellViews[x][y].setNumber(cellViews[i][y].getNumber());
                            cellViews[i][y].setNumber(0);
                            x--;
                            addRamNum = true;
                            break;
                        }
                    }
                } else {
                    for (int i = (x + 1); i < 4; i++) {
                        if (cellViews[i][y].getNumber() != 0) {
                            if (cellViews[i][y].getNumber() == cellViews[x][y].getNumber()) {
                                cellViews[x][y].setNumber(cellViews[i][y].getNumber() * 2);
                                cellViews[i][y].setNumber(0);
                                addRamNum = true;
                                score = score + cellViews[x][y].getNumber();

                            }
                            break;
                        }
                    }
                }
            }
        }
        if (addRamNum) {
            addRandomNum();
            checkOver();
        }
    }

    //下移处理
    public void swipeDown() {
        boolean addRamNum = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {
                if (cellViews[x][y].getNumber() == 0) {
                    for (int i = (x - 1); i >= 0; i--) {
                        if (cellViews[i][y].getNumber() != 0) {
                            cellViews[x][y].setNumber(cellViews[i][y].getNumber());
                            cellViews[i][y].setNumber(0);
                            x++;
                            addRamNum = true;
                            break;
                        }
                    }
                } else {
                    for (int i = (x - 1); i >= 0; i--) {
                        if (cellViews[i][y].getNumber() != 0) {
                            if (cellViews[i][y].getNumber() == cellViews[x][y].getNumber()) {
                                cellViews[x][y].setNumber(cellViews[i][y].getNumber() * 2);
                                cellViews[i][y].setNumber(0);
                                addRamNum = true;
                                score = score + cellViews[x][y].getNumber();

                            }
                            break;
                        }
                    }
                }
            }
        }
        if (addRamNum) {
            addRandomNum();
            checkOver();
        }
    }


    //左斜下处理↙
    public void inclinedLeft() {
        boolean addRamNum = false;
        for (int sum = 0; sum < 7; sum++) {
            for (int x = Math.min(sum, 3); x >= 0; x--) {
                if ((sum - x) <= 3) {
                    int y = sum - x;
                    //当前为0的时候，从右斜上拽一个非0的数字过来
                    if (cellViews[x][y].getNumber() == 0) {
                        for (int i = x - 1; i >= 0; i--) {
                            if (sum - i <= 3) {
                                if (cellViews[i][sum - i].getNumber() != 0) {
                                    cellViews[x][y].setNumber(cellViews[i][sum - i].getNumber());
                                    cellViews[i][sum - i].setNumber(0);
                                    x++;
                                    addRamNum = true;
                                    break;
                                }
                            }
                        }
                    }
                    //当前格子不为0的时候判断x，y格子斜右上的格子，非0且相等就合并然后跳出循环，非0不等就直接跳出循环，
                    else {
                        for (int i = x - 1; i >= 0; i--) {
                            if (sum - i <= 3) {
                                if (cellViews[i][sum - i].getNumber() != 0) {
                                    if (cellViews[i][sum - i].getNumber() == cellViews[x][y].getNumber()) {
                                        cellViews[x][y].setNumber(cellViews[i][sum - i].getNumber() * 2);
                                        cellViews[i][sum - i].setNumber(0);
                                        addRamNum = true;
                                        score = score + cellViews[x][y].getNumber();

                                    }
                                    break;
                                }
                            }
                        }
                    }

                }
            }
        }
        if (addRamNum) {
            addRandomNum();
            checkOver();
        }
    }

    //右斜上处理↗
    public void ObliqueRight() {
        boolean addRamNum = false;
        for (int sum = 0; sum < 7; sum++) {
            for (int x = 0; x <= Math.min(sum, 3); x++) {
                if ((sum - x) <= 3) {
                    int y = sum - x;
                    //当前为0的时候，从右斜上拽一个非0的数字过来
                    if (cellViews[x][y].getNumber() == 0) {
                        for (int i = x + 1; i <= Math.min(sum, 3); i++) {
                            if (sum - i <= 3) {
                                if (cellViews[i][sum - i].getNumber() != 0) {
                                    cellViews[x][y].setNumber(cellViews[i][sum - i].getNumber());
                                    cellViews[i][sum - i].setNumber(0);
                                    x++;
                                    addRamNum = true;
                                    break;
                                }
                            }
                        }
                    }
                    //当前格子不为0的时候判断x，y格子斜右上的格子，非0且相等就合并然后跳出循环，非0不等就直接跳出循环，
                    else {
                        for (int i = x + 1; i <= Math.min(sum, 3); i++) {
                            if (sum - i <= 3) {
                                if (cellViews[i][sum - i].getNumber() != 0) {
                                    if (cellViews[i][sum - i].getNumber() == cellViews[x][y].getNumber()) {
                                        cellViews[x][y].setNumber(cellViews[i][sum - i].getNumber() * 2);
                                        cellViews[i][sum - i].setNumber(0);
                                        addRamNum = true;
                                        score = score + cellViews[x][y].getNumber();
                                    }
                                    break;
                                }
                            }
                        }
                    }

                }
            }
        }
        if (addRamNum) {
            addRandomNum();
            checkOver();
        }
    }

    //右斜下处理↘
    public void inclinedRight() {
        boolean addRamNum = false;
        //x-y=difference;y=x-difference;
        for (int difference = 3; difference >= -3; difference--) {
            for (int x = Math.min(difference + 3, 3); x >= Math.max(difference, 0); x--) {
                int y = x - difference;
                if (cellViews[x][y].getNumber() == 0) {
                    for (int i = x - 1; i >= Math.max(difference, 0); i--) {
                        if (cellViews[i][i - difference].getNumber() != 0) {
                            cellViews[x][y].setNumber(cellViews[i][i - difference].getNumber());
                            cellViews[i][i - difference].setNumber(0);
                            x++;
                            addRamNum = true;
                            break;
                        }
                    }
                } else {
                    for (int i = x - 1; i >= Math.max(difference, 0); i--) {
                        if (cellViews[i][i - difference].getNumber() != 0) {
                            if (cellViews[i][i - difference].getNumber() == cellViews[x][y].getNumber()) {
                                cellViews[x][y].setNumber(cellViews[i][i - difference].getNumber() * 2);
                                cellViews[i][i - difference].setNumber(0);
                                addRamNum = true;
                                score = score + cellViews[x][y].getNumber();
                            }
                            break;
                        }
                    }
                }
            }
        }

        if (addRamNum) {
            addRandomNum();
            checkOver();
        }
    }

    //左斜上处理↖
    public void ObliqueLeft() {
        boolean addRamNum = false;
        //x-y=difference;y=x-difference;
        for (int difference = 3; difference >= -3; difference--) {
            for (int x = Math.max(difference, 0); x <= Math.min(difference + 3, 3); x++) {
                int y = x - difference;

                if (cellViews[x][y].getNumber() == 0) {
                    for (int i = x + 1; i <= Math.min(difference + 3, 3); i++) {
                        if (cellViews[i][i - difference].getNumber() != 0) {
                            cellViews[x][y].setNumber(cellViews[i][i - difference].getNumber());
                            cellViews[i][i - difference].setNumber(0);
                            x++;
                            addRamNum = true;
                            break;
                        }
                    }
                } else {
                    for (int i = x + 1; i <= Math.min(difference + 3, 3); i++) {
                        if (cellViews[i][i - difference].getNumber() != 0) {
                            if (cellViews[i][i - difference].getNumber() == cellViews[x][y].getNumber()) {
                                cellViews[x][y].setNumber(cellViews[i][i - difference].getNumber() * 2);
                                cellViews[i][i - difference].setNumber(0);
                                addRamNum = true;
                                score = score + cellViews[x][y].getNumber();
                            }
                            break;
                        }
                    }
                }
            }
        }

        if (addRamNum) {
            addRandomNum();
            checkOver();
        }
    }

    //产生随机数2/4
    private void addRandomNum() {
        List<Point> blankPoint = new ArrayList<Point>();
        blankPoint.clear();
        //历尽所有点，取出空白点
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (cellViews[i][j].getNumber() == 0) {
                    blankPoint.add(new Point(i, j));
                }
            }
        }
        if (blankPoint.size() != 0) {
            Point p = blankPoint.get(new Random().nextInt(blankPoint.size()));//new Random().nextInt()制定范围的随机数
            cellViews[p.x][p.y].setNumber(Math.random() > 0.2 ? 2 : 4);   //Math.random()返回的范围为0~1；2,4出现概率比为：0.2:0.8
        }
    }

    //检查游戏结束
    public void checkOver() {
        boolean gameOver = true;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (cellViews[x][y].getNumber()==2048)
                {
                    score = 0;
                    new AlertDialog.Builder(getContext())
                            .setCancelable(false)
                            .setMessage("You Win!!")
                            .setPositiveButton("重来",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            NewGame();
                                        }
                                    }).show();
                    break;
                }
            }
        }
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (cellViews[x][y].getNumber() == 0) {
                    gameOver = false;
                    break;
                } else {
                    for (int i = Math.max(x - 1, 0); i <= Math.min(x + 1, 3); i++) {
                        for (int j = Math.max(y - 1, 0); j <= Math.min(y + 1, 3); j++) {
                            if (!(i == x && j == y)) {
                                if (cellViews[i][j].getNumber() == cellViews[x][y].getNumber()) {
                                    gameOver = false;
//
                                }
                            }
                        }

                    }
                }

            }
        }
        if (gameOver) {
            score = 0;
            new AlertDialog.Builder(getContext())
                    .setCancelable(false)
                    .setMessage("游戏结束")
                    .setPositiveButton("重来",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    NewGame();
                                }
                            }).show();
        }
    }

    //New Game
    public void NewGame() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cellViews[i][j].setNumber(0);
            }
        }
        score = 0;
        addRandomNum();
        addRandomNum();
    }


    private class GridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 16;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return cellViews[position / 4][position % 4];
        }
    }

}
