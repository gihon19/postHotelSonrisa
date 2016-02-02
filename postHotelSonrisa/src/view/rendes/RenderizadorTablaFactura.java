package view.rendes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class RenderizadorTablaFactura implements TableCellRenderer{

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		
		Font myFont=new Font(Font.SANS_SERIF, Font.PLAIN, 14);
		 JTextField editor = new JTextField();
		 Color color3 =Color.decode("#d4f4ff");
		 //Color color1 =Color.decode("#0009999");
		 Color color1 =new Color(60, 179, 113);
		 editor.setFont(myFont);
		 editor.setText("");
		    if (value != null)
		      editor.setText(value.toString());
		    
		    if (row % 2 == 0) {
		    	editor.setBackground(color3);
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
		   if(column==2){
			   editor.setHorizontalAlignment(SwingConstants.RIGHT);
		   }
		   if(column==3)
			   editor.setHorizontalAlignment(SwingConstants.CENTER);
		   if(column==4)
			   editor.setHorizontalAlignment(SwingConstants.RIGHT);
		   if(column==5)
			   editor.setHorizontalAlignment(SwingConstants.RIGHT);
		   if(column==6)
			   editor.setHorizontalAlignment(SwingConstants.RIGHT);
		   if(column==7)
			   editor.setHorizontalAlignment(SwingConstants.RIGHT);
		 
		    if (isSelected) {
		    	editor.setBackground(color1);
	        }
		 return editor;
	}
	
	

}
