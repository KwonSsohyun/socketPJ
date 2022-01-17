// �� ChatClientObject // Ŭ���̾�Ʈ

package random_Chat;

import java.awt.Button;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class ChatClientObjectMain extends Frame implements ActionListener{
	
	// �ʵ� --------------------------------------------
	JButton btn;

	public ChatClientObjectMain(){
		
		// â�ݱ� ��ư 
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("�����ϰڽ��ϴ�.");
				dispose();
			}
		});
		
		
		Panel p = new Panel();
		p.setLayout(new GridLayout(2,1));
		p.setLayout(null);
		ImageIcon image = new ImageIcon("main.png");
		JLabel label = new JLabel(image);
		label.setHorizontalAlignment(JLabel.CENTER);
		
		label.setSize(400,300); // ��ư ũ�� 80x50
		label.setLocation(0,0); // ���� �𼭸� ��ġ�� (20,20)���� ��ġ
		
		p.add(label);
		btn = new JButton("���� ä�� ����! (Ŭ��!)");
		btn.setFont(new Font("���� ���", Font.BOLD,30));
		btn.setSize(400,362);
		btn.setLocation(0,300);
		p.add(btn);
		btn.addActionListener(this);
		
		
		add(p);
		setTitle("����ä��");
		setBounds(200, 200, 400, 700);
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		new ChatClientObjectMain();

	}

	
	// �׼Ǹ����� 
	@Override
	public void actionPerformed(ActionEvent e) {
		// ��ư Ŭ����
		Object obj = e.getSource();
		
		if(obj == btn){
			setVisible(false);
			new ChatClientObjectRoom();
		}
		
		
	}

}
