package ps.demo.util.matrix;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ps.demo.util.MyBeanUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@ToString
public class MyMatrix implements Serializable {

    protected Integer rows;

    protected Integer cols;

    protected List<List<MyCell>> table;

    public MyMatrix() {

    }

    public MyMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        table = new ArrayList<>();
        initTable();
    }

    public MyMatrix(List<List<MyCell>> table) {
        this.table = table;
        this.rows = table.size();
        this.cols = table.get(0).size();
    }

    public void initTable() {
        for (int r = 0; r < this.rows; r++) {
            List<MyCell> row = new ArrayList<>();
            for (int c = 0; c < this.cols; c++) {
                row.add(new MyCell(c, r));
            }
            table.add(row);
        }
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.cols;
    }

    public MyCell getCell(int x, int y) {
        return table.get(y).get(x);
    }

    public void setCell(int x, int y, Object data) {
        MyCell myCell = this.getCell(x, y);
        myCell.setData(data);
    }

    public MyMatrix reverseRowColumn() {
        if (table == null) {
            return new MyMatrix(this.getCols(), this.getRows());
        }
        List<List<MyCell>> newTable = new ArrayList<>();
        for (int c = 0; c < this.cols; c++) {
            List<MyCell> row = new ArrayList<>();
            for (int r = 0; r < this.rows; r++) {
                MyCell myCell = this.getCell(c, r);
                MyCell newCell = (MyCell) MyBeanUtil.clone(myCell);
                newCell.setX(myCell.getY());
                newCell.setY(myCell.getX());
                row.add(newCell);
            }
            newTable.add(row);
        }
        MyMatrix newMatrix = new MyMatrix(newTable);
        return newMatrix;
    }

    public List<List<Object>> toListList() {
        List<List<Object>> result = new ArrayList<>();
        for (int y = 0; y < this.rows; y++) {
            List<Object> row = new ArrayList<>();
            for (int x = 0; x < this.cols; x++) {
                MyCell cell = this.getCell(x, y);
                row.add(cell.getData());
            }
            result.add(row);
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (int r = 0; r < this.getRows(); r++) {
            for (int c = 0; c < this.getCols(); c++) {
                buffer.append(this.getCell(c, r));
                buffer.append("\t");
            }
            buffer.append(System.lineSeparator());
        }
        return buffer.toString();
    }


}
