import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListDemo extends JFrame{
	JPanel p1=new JPanel();
	ImagePanel ip=new ImagePanel();
	JPanel p=new JPanel();
	
	JButton jbtSolve=new JButton("Solve");
	JButton jbtShow=new JButton("Show Solution");
	JButton jbtConfirm=new JButton("Confirm");
	JButton jbtNew=new JButton("New");
	
	Border lineBorder=new LineBorder(Color.BLACK,2);
	JTextField jt1=new JTextField();
	JTextField jt2=new JTextField();
	JTextField jt3=new JTextField();
	JTextField jt4=new JTextField();
	JTextField jt5=new JTextField();
	JTextField jt6=new JTextField();
	JTextField jt7=new JTextField();
	JTextField jt8=new JTextField();
	JTextField jt9=new JTextField();
	
	JMenuItem jmiNew,jmiExit,jmiAbout,jmiHelp;
	
	Image[] img = {
			new ImageIcon(this.getClass().getResource("image/1.gif")).getImage(),
			new ImageIcon(this.getClass().getResource("image/2.gif")).getImage(),
			new ImageIcon(this.getClass().getResource("image/3.gif")).getImage(),
			new ImageIcon(this.getClass().getResource("image/4.gif")).getImage(),
			new ImageIcon(this.getClass().getResource("image/5.gif")).getImage(),
			new ImageIcon(this.getClass().getResource("image/6.gif")).getImage(),
			new ImageIcon(this.getClass().getResource("image/7.gif")).getImage(),
			new ImageIcon(this.getClass().getResource("image/8.gif")).getImage(),
			};
	
	int [] val=new int [10];
	int [] sta=new int [10];
	
	int pos[][]=new int [10][10];
	int len,cnt,currval,currmov;
	boolean isShow=false;
	Settle st;
	Timer timer;
	static Font font = new Font("Times New Roman",Font.PLAIN+Font.ITALIC,70);
	
	public static void main(String[] args)
	{
		JFrame frame=new ListDemo();
		frame.setSize(650,450);
		frame.setTitle("Eight Puzzle Problem");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public ListDemo()
	{
		Container ct= this.getContentPane();
		
		JMenuBar jmb=new JMenuBar();
		JMenu fileMenu = new JMenu("Operation");
		JMenu fileHelp = new JMenu("Help");
		fileMenu.add(jmiNew=new JMenuItem("New Puzzle"));
		fileMenu.add(jmiExit=new JMenuItem("Exit"));
		fileHelp.add(jmiHelp=new JMenuItem("Instruction"));
		fileHelp.add(jmiAbout=new JMenuItem("About"));
		
		jmiNew.addActionListener(new NewListener());
		
		jmiExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		
		jmiHelp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null,"Input 1 to 9 in the textfields, 9 represents blank.","Instruction",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		jmiAbout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null,"Coded by lizhen","About",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		jmb.add(fileMenu);
		jmb.add(fileHelp);
		
		ct.add(jmb,BorderLayout.NORTH);
		
		for(int i=0;i<=8;i++)
			val[i]=i+1;
		
		p1.setLayout(new GridLayout(4,3));
		
		p1.add(jt1);p1.add(jt2);p1.add(jt3);
		p1.add(jt4);p1.add(jt5);p1.add(jt6);
		p1.add(jt7);p1.add(jt8);p1.add(jt9);
		
		jt1.setFont(font);jt2.setFont(font);jt3.setFont(font);
		jt4.setFont(font);jt5.setFont(font);jt6.setFont(font);
		jt7.setFont(font);jt8.setFont(font);jt9.setFont(font);
		
		p1.add(jbtConfirm);
		p1.add(jbtNew);
		p1.add(jbtSolve);
		
		jbtConfirm.addActionListener(new ConfirmListener());
		jbtSolve.addActionListener(new SolveListener());
		
	    //p1.setBorder(lineBorder);
		
		ct.add(p1,BorderLayout.EAST);
		
		ip.setBorder(lineBorder);
		ct.add(ip,BorderLayout.CENTER);
		
		//p.add(jbtSolve);
		p.add(jbtShow);
		//p.setBorder(lineBorder);
		
		jbtShow.addActionListener(new ShowListener());
		jbtNew.addActionListener(new NewListener());
		ct.add(p,BorderLayout.SOUTH);
	 }

	class NewListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			jt1.setText("");
			jt2.setText("");
			jt3.setText("");
			jt4.setText("");
			jt5.setText("");
			jt6.setText("");
			jt7.setText("");
			jt8.setText("");
			jt9.setText("");
		}
	}
	class ConfirmListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			val[0]=Integer.parseInt(jt1.getText());
			val[1]=Integer.parseInt(jt2.getText());
			val[2]=Integer.parseInt(jt3.getText());
			val[3]=Integer.parseInt(jt4.getText());
			val[4]=Integer.parseInt(jt5.getText());
			val[5]=Integer.parseInt(jt6.getText());
			val[6]=Integer.parseInt(jt7.getText());
			val[7]=Integer.parseInt(jt8.getText());
			val[8]=Integer.parseInt(jt9.getText());
			
			for(int i=0;i<3;i++)
			{
				for(int j=0;j<3;j++)
				{
					if(val[i*3+j]==9)
						continue;
					pos[val[i*3+j]][0]=110*j+30;
					pos[val[i*3+j]][1]=110*i+10;
					
					System.out.println(val[i*3+j]+" "+pos[val[i*3+j]][0]+" "+pos[val[i*3+j]][1]);
				}
			}
			
			ip.repaint();
		}
	}
	
class SolveListener implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		int rev=0;
		
		for(int i=0; i<9; i++)
	    {
	    	if(val[i]==9)
	    		continue;
	        for(int j=0; j<i; j++)
	        {
	            if(val[i]>val[j])
	            	rev++;
	        }
	    }
	   
	    if(rev%2==1)
		{
			JOptionPane.showMessageDialog(null, "No Solution!");
		}
		else
		{
			st=new Settle(val);
		
			JOptionPane.showMessageDialog(null, "Solution found in "+st.bound+" steps. Click the 'Show Solution' button to see the animation.");
		}
	}
}

class ShowListener implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		len=st.bound;
		len*=5;
		
		timer=new Timer(100,new TimerListener());
		cnt=0;
		timer.start();
		isShow=true;
		}
}

class TimerListener implements ActionListener
{
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		currval=st.sol[cnt/5];
		currmov=st.mov[cnt/5];
		
		if(cnt>len)
			timer.stop();
		else
			ip.repaint();
		cnt++;
	}
}

class ImagePanel extends JPanel{
	public void paint(Graphics g)
	{
		super.paint(g);
		
		if(!isShow)
		{
			for(int i=0;i<3;i++)
			{
				for(int j=0;j<3;j++)
				{
					if(val[i*3+j]==9)
						continue;
					g.drawImage(img[val[i*3+j]-1],110*j+30,110*i+10,110,110,this);
				}
			}
		}
		else
		{
			switch(currmov)
			{
				case 0:
					pos[currval][1]+=22; break;
				case 1:
					pos[currval][0]+=22; break;
				case 2:
					pos[currval][0]-=22; break;
				case 3:
					pos[currval][1]-=22; break;
				default: break;
			}
			
			for(int i=0;i<3;i++)
			{
				for(int j=0;j<3;j++)
				{
					if(val[i*3+j]==9)
						continue;
					g.drawImage(img[val[i*3+j]-1],pos[val[i*3+j]][0],pos[val[i*3+j]][1],110,110,this);
				}
			}
		}
	}
}
}
