/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iaik.chille.election.userclient;

import iaik.chille.elections.common.Elections;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author chille
 */
public class ElectionTableModel extends AbstractTableModel
{
  private static final long serialVersionUID = 1L;

  public ElectionTableModel(Elections els)
  {
    this.els = els;
  }

  private String[] columnNames = {"ID","Titel","Antworten #","Gültig bis"};
  Elections els = null;

  public void setData(Elections els)
  {
    if(els != this.els)
    {
      this.els = els;
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
    if(els != null)
      return els.getElection().size();
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
    if(els == null)
      return null;
    switch(col)
    {
      case 0:
        return els.getElection().get(row).getId();
      case 1:
        return els.getElection().get(row).getTitle();
      case 2:
        return els.getElection().get(row).getChoice().size();
      case 3:
        return els.getElection().get(row).getValidTo().toString();
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
