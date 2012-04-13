/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iaik.chille.election.userclient;

import iaik.chille.elections.common.Election;
import iaik.chille.elections.common.Elections;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author chille
 */
public class ChoiceTableModel extends AbstractTableModel
{
  private static final long serialVersionUID = 1L;

  public ChoiceTableModel()
  {
  }

  private String[] columnNames = {"ID","Antwort","Result"};
  Election el = null;

  public void setData(Election el)
  {
    if(el != this.el)
    {
      this.el = el;
      this.fireTableDataChanged();
    }
  }

  @Override
  public int getColumnCount()
  {
    return columnNames.length;
  }

  @Override
  public int getRowCount()
  {
    if(el != null)
      return el.getChoice().size();
    else
      return 0;
  }

  @Override
  public String getColumnName(int col)
  {
    return columnNames[col];
  }

  @Override
  public Object getValueAt(int row, int col)
  {
    if(el == null)
      return null;
    switch(col)
    {
      case 0:
        return el.getChoice().get(row).getId();
      case 1:
        return el.getChoice().get(row).getAnswer();
      case 2:
        return el.getChoice().get(row).getResult();
      default:
        return "-";
    }
  }

  @Override
  public boolean isCellEditable(int row, int col)
  {
    return false;
  }

}