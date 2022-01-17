// ▶ ChatClientObject // 클라이언트

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
	
	// 필드 --------------------------------------------
	JButton btn;

	public ChatClientObjectMain(){
		
		// 창닫기 버튼 
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("종료하겠습니다.");
				dispose();
			}
		});
		
		
		Panel p = new Panel();
		p.setLayout(new GridLayout(2,1));
		p.setLayout(null);
		ImageIcon image = new ImageIcon("main.png");
		JLabel label = new JLabel(image);
		label.setHorizontalAlignment(JLabel.CENTER);
		
		label.setSize(400,300); // 버튼 크기 80x50
		label.setLocation(0,0); // 왼쪽 모서리 위치를 (20,20)으로 위치
		
		p.add(label);
		btn = new JButton("랜덤 채팅 시작! (클릭!)");
		btn.setFont(new Font("맑은 고딕", Font.BOLD,30));
		btn.setSize(400,362);
		btn.setLocation(0,300);
		p.add(btn);
		btn.addActionListener(this);
		
		
		add(p);
		setTitle("랜덤채팅");
		setBounds(200, 200, 400, 700);
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		new ChatClientObjectMain();

	}

	
	// 액션리스너 
	@Override
	public void actionPerformed(ActionEvent e) {
		// 버튼 클릭시
		Object obj = e.getSource();
		
		if(obj == btn){
			setVisible(false);
			new ChatClientObjectRoom();
		}
		
		
	}

}
