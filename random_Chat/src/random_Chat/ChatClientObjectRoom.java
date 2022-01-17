// ▶ ChatClientObject // 클라이언트

package random_Chat;

import java.awt.ActiveEvent;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.LineMetrics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClientObjectRoom extends Frame implements ActionListener, KeyListener{
	
	// 필드 ----------------------------------------------
	JButton exit;
	String msg = null;   // 메시지 입력내용
	JButton sendBtn;     // 보내기 버튼
	String result;       // 메시지 입력내용
	BufferedReader msgb; // 메시지 입력내용
	JButton yesButton;   // 별칭 버튼
	TextField nameinput; // 별칭 입력내용
	Dialog dlg;          // 다일로그(별칭) 팝업    
	String name;         // 별칭 이름
	static JTextField allsu;    // 접속 인원 
	static int su=0;              // 인원
	// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	// 모두가 공유
	static BufferedWriter bw;   
	static TextArea chat;     // 채팅창
	static JTextField input;  // 메세지 입력창
	// ----------------------------------------------------

	public ChatClientObjectRoom(){  // ▶▶ UI쓰래드
		
		// 창닫기 버튼
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// System.out.println("종료하겠습니다.");
				dispose();
			}
		});
		
		BorderLayout bl = new BorderLayout();
		Panel p = new Panel();
		p.setLayout(bl);
		
		
		// ▶ 상단
		// 다일로그 name 받기
		dlg = new Dialog(this, "채팅 이름 설정");
		
		GridLayout gl = new GridLayout(3,1);
		Panel pl = new Panel();
		pl.setLayout(gl);
		dlg.add(pl);

		Label lala = new Label("채팅에서 쓸 별칭을 입력하세요.");
		pl.add(lala);
		Panel pll = new Panel();
		nameinput = new TextField(30);
		pll.add(nameinput);
		pl.add(pll);
		yesButton = new JButton("확인");
		yesButton.addActionListener(this);
		pl.add(yesButton);
		
		
//		BorderLayout brl = new BorderLayout();
//		Panel pl = new Panel();
//		pl.setLayout(brl);
//		
//		TextField name = new TextField();
//		JButton yesButton = new JButton("확인");
//		
//	    // 화면 배치
//		dlg.add(new Label("채팅에서 쓸 별칭을 입력하세요."), BorderLayout.NORTH);
//		dlg.add(name, BorderLayout.CENTER);
//		dlg.add(yesButton, BorderLayout.SOUTH);
		
		
		// 다일로그 - 창닫기 버튼
		dlg.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// System.out.println("종료하겠습니다.");
				dlg.dispose();
			}
		});
		
		// <다른 방법>
		// String name = JOptionPane.showInputDialog("채팅에서 쓸 별칭을 입력하세요");
		// System.out.print("이름 : " + name);
		

	
		GridLayout gn = new GridLayout(1,2);
		Panel pn = new Panel();
		pn.setLayout(gn);
		exit = new JButton("채팅방 나가기");
		exit.setForeground(Color.WHITE);
		exit.setBackground(new Color(230,95,63));
		pn.add(exit);
		// JLabel label = new JLabel("바른 대화 예절!");
		allsu = new JTextField("접속인원 : "+ su);
		allsu.setEditable(false);
		allsu.setFont(new Font("맑은고딕", Font.BOLD, 15));
		allsu.setHorizontalAlignment(JLabel.CENTER);
		allsu.addActionListener(this);
		pn.add(allsu);
		exit.addActionListener(this);
		
		
		// ▶ 중간
		GridLayout gc = new GridLayout(1,1);
		Panel pc = new Panel();
		pc.setLayout(gc);
		Font font = new Font("맑은고딕", Font.BOLD, 20);
		chat = new TextArea();
		chat.setEditable(false);
		chat.setFont(font); 
		pc.add(chat);
		
		
		// ▶ 하단
		// 하단에 버튼과 TextArea넣기 
		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout()); 
		
		input = new JTextField();
		// input.addActionListener(this);
		input.addKeyListener(this);
		input.setFont(new Font("맑은고딕", Font.BOLD, 20)); 

		sendBtn = new JButton("보내기");
		sendBtn.addActionListener(this);
				
		bottom.add("Center",input);  // 센터에 붙이기
		bottom.add("East",sendBtn);  // 동쪽에 붙이기
		
		// 전체 패널 조정
		p.add(pn, BorderLayout.NORTH);
		p.add(pc, BorderLayout.CENTER);
		p.add(bottom, BorderLayout.SOUTH);
		
		
		// 기본 셋팅
		dlg.setBounds(250, 400, 300, 200);
		dlg.setVisible(true);
		add(p);
		setTitle("채팅방");
		setBounds(200, 200, 400, 700);
		setVisible(true);
	}
	

	
	// ▶▶ 액션리스너 ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// 버튼 클릭시
		Object obj = e.getSource();
		JButton button = (JButton) e.getSource();

		
		if(obj == exit){
			msg = input.getText();
			
			try {
				bw.write("*["+name+"] "+"님이 퇴장 했습니다.");
				bw.newLine(); // 개행
				bw.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			chat.append(input.getText() + "\n" + "대화를 종료합니다.\n");

			// ▶ 1.5초 현재창 유지 후 닫힘
			//   : 메인도 쓰래드!
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			
			setVisible(false); // 현재 창 닫기
			new ChatClientObjectMain(); // 메인창 열리기
			
		} else if(button == sendBtn){ // 보내기 버튼 클릭시	
			
			String msg = input.getText();
			try {
				bw.write("#["+name+"] "+ msg + " " + getTime());
				bw.newLine(); // 개행
				bw.flush();
			} catch (IOException e1) {
				// e1.printStackTrace();
			}
			
			// chat.append(input.getText() + "\n");
			// input.setText("");
			// input.requestFocus(); // 커서 입력창으로 위치
			
		} else if(button == yesButton){ // 별칭 정하기
			name = nameinput.getText();
			// System.out.println(name);
			dlg.setVisible(false);
			try {
				bw.write("$["+name+"] "+"님이 입장 했습니다.");
				bw.newLine(); // 개행
				bw.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		} 
		
	}
	
	// ▶▶ 엔터를 눌렀을 때의 액션 ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	@Override
	public void keyPressed(KeyEvent en) {
		
		 if (en.getKeyCode() == KeyEvent.VK_ENTER) {
			 
				String msg = input.getText();
				try {
					bw.write("#["+name+"] "+ msg + " " + getTime());
					bw.newLine(); // 개행
					bw.flush();
				} catch (IOException e1) {
					// e1.printStackTrace();
				}
				
				// chat.append(input.getText() + "\n");
				// input.setText("");
				// input.requestFocus(); // 커서 입력창으로 위치
		 }
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}


	@Override
	public void keyReleased(KeyEvent e) {
	}
	// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	
	
	
	// ▶▶ 메인 메서드
	public static void main(String[] args) {
		new ChatClientObjectRoom(); // UI
		
		//////////////////////////////////////////////
		Socket sock = null;
		InputStream is = null;
		OutputStream os = null;
		InputStreamReader isr = null;
		OutputStreamWriter osw = null;
		BufferedReader br = null;
		//////////////////////////////////////////////
		
		try {
			sock = new Socket("127.0.0.1", 5000);
			
			is = sock.getInputStream();    // 읽는 스트림
			os = sock.getOutputStream();   // 쓰는 스트림
			isr = new InputStreamReader(is);
			osw = new OutputStreamWriter(os);
			br = new BufferedReader(isr);
			bw = new BufferedWriter(osw);
			
			while(true){
				// 서버에서 sayAll메서드로 쓰니 (클라가 생성될때마다 소켓에 쓰니)
				// 여기서는 읽는다.
				String msg2 = br.readLine(); // 개행전까지 읽는다.

				
				if(msg2.startsWith("$")){ // ▶ 접속 인원! (입장시 집계)
					msg2 = msg2.substring(1);
					chat.append(msg2+"\n");
					msg2 = br.readLine(); // 서버에서 2번 쓰니, 클라에서도 2번 읽기!
					allsu.setText("접속인원 : " + msg2);      // 인원수 입력
					
				} else if(msg2.startsWith("*")){ // ▶ 접속 인원! (퇴장시 집계)
					msg2 = msg2.substring(1);
					chat.append(msg2+"\n");
					msg2 = br.readLine(); // 서버에서 2번 쓰니, 클라에서도 2번 읽기!
					allsu.setText("접속인원 : " + msg2);      // 인원수 입력
		
				}
				
				if(msg2.startsWith("#")){ // ▶ 일반 대화!
					msg2 = msg2.substring(1);
					chat.append(msg2+"\n");
				}
				input.setText("");    // 쓰는입력창 빈칸으로 초기화
				input.requestFocus(); // 커서 포커스
				
			}
			
			
		} catch (UnknownHostException e) {
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		} finally {
			try {
				bw.close();
				br.close();
				osw.close();
				isr.close();
				os.close();
				is.close();
				sock.close();
			} catch (IOException e) {
				// e.printStackTrace();
			}
		}
	
	}

	// 현재시간을 문자열로 반환하는 함수!
	static String getTime(){
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date());
	}

}
