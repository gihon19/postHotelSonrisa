package view.rendes;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class RtImpuestos implements TableCellRenderer{

	public RtImpuestos() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JLabel editor = new JLabel();
		JCheckBox editor2=new JCheckBox();
		 editor.setText("");
		    if (value != null)
		      editor.setText(value.toString());
		    
		    if (row % 2 == 0) {
		    	editor.setBackground(new Color(176, 224, 230));
	        } else {
	        	editor.setBackground(Color.white);
	        }
		    
		    //editor.setBackground((row % 2 == 0) ? Color.white : Color.cyan);
		    
		    
		   if(column==0){
				   editor.setHorizontalAlignment(SwingConstants.CENTER);
			  }
		   if(column==1){
			   editor.setHorizontalAlignment(SwingConstants.LEFT);
		   }
		   if (isSelected) {
		    	editor.setBackground(new Color(254, 172, 172));
	        }
		   if(column==0)
			   return editor;
		   else
			   return editor2;
	}

}
