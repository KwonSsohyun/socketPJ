// �� ChatClientObject // Ŭ���̾�Ʈ

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
	
	// �ʵ� ----------------------------------------------
	JButton exit;
	String msg = null;   // �޽��� �Է³���
	JButton sendBtn;     // ������ ��ư
	String result;       // �޽��� �Է³���
	BufferedReader msgb; // �޽��� �Է³���
	JButton yesButton;   // ��Ī ��ư
	TextField nameinput; // ��Ī �Է³���
	Dialog dlg;          // ���Ϸα�(��Ī) �˾�    
	String name;         // ��Ī �̸�
	static JTextField allsu;    // ���� �ο� 
	static int su=0;              // �ο�
	// �����������������������������������������������������
	// ��ΰ� ����
	static BufferedWriter bw;   
	static TextArea chat;     // ä��â
	static JTextField input;  // �޼��� �Է�â
	// ----------------------------------------------------

	public ChatClientObjectRoom(){  // ���� UI������
		
		// â�ݱ� ��ư
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// System.out.println("�����ϰڽ��ϴ�.");
				dispose();
			}
		});
		
		BorderLayout bl = new BorderLayout();
		Panel p = new Panel();
		p.setLayout(bl);
		
		
		// �� ���
		// ���Ϸα� name �ޱ�
		dlg = new Dialog(this, "ä�� �̸� ����");
		
		GridLayout gl = new GridLayout(3,1);
		Panel pl = new Panel();
		pl.setLayout(gl);
		dlg.add(pl);

		Label lala = new Label("ä�ÿ��� �� ��Ī�� �Է��ϼ���.");
		pl.add(lala);
		Panel pll = new Panel();
		nameinput = new TextField(30);
		pll.add(nameinput);
		pl.add(pll);
		yesButton = new JButton("Ȯ��");
		yesButton.addActionListener(this);
		pl.add(yesButton);
		
		
//		BorderLayout brl = new BorderLayout();
//		Panel pl = new Panel();
//		pl.setLayout(brl);
//		
//		TextField name = new TextField();
//		JButton yesButton = new JButton("Ȯ��");
//		
//	    // ȭ�� ��ġ
//		dlg.add(new Label("ä�ÿ��� �� ��Ī�� �Է��ϼ���."), BorderLayout.NORTH);
//		dlg.add(name, BorderLayout.CENTER);
//		dlg.add(yesButton, BorderLayout.SOUTH);
		
		
		// ���Ϸα� - â�ݱ� ��ư
		dlg.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// System.out.println("�����ϰڽ��ϴ�.");
				dlg.dispose();
			}
		});
		
		// <�ٸ� ���>
		// String name = JOptionPane.showInputDialog("ä�ÿ��� �� ��Ī�� �Է��ϼ���");
		// System.out.print("�̸� : " + name);
		

	
		GridLayout gn = new GridLayout(1,2);
		Panel pn = new Panel();
		pn.setLayout(gn);
		exit = new JButton("ä�ù� ������");
		exit.setForeground(Color.WHITE);
		exit.setBackground(new Color(230,95,63));
		pn.add(exit);
		// JLabel label = new JLabel("�ٸ� ��ȭ ����!");
		allsu = new JTextField("�����ο� : "+ su);
		allsu.setEditable(false);
		allsu.setFont(new Font("�������", Font.BOLD, 15));
		allsu.setHorizontalAlignment(JLabel.CENTER);
		allsu.addActionListener(this);
		pn.add(allsu);
		exit.addActionListener(this);
		
		
		// �� �߰�
		GridLayout gc = new GridLayout(1,1);
		Panel pc = new Panel();
		pc.setLayout(gc);
		Font font = new Font("�������", Font.BOLD, 20);
		chat = new TextArea();
		chat.setEditable(false);
		chat.setFont(font); 
		pc.add(chat);
		
		
		// �� �ϴ�
		// �ϴܿ� ��ư�� TextArea�ֱ� 
		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout()); 
		
		input = new JTextField();
		// input.addActionListener(this);
		input.addKeyListener(this);
		input.setFont(new Font("�������", Font.BOLD, 20)); 

		sendBtn = new JButton("������");
		sendBtn.addActionListener(this);
				
		bottom.add("Center",input);  // ���Ϳ� ���̱�
		bottom.add("East",sendBtn);  // ���ʿ� ���̱�
		
		// ��ü �г� ����
		p.add(pn, BorderLayout.NORTH);
		p.add(pc, BorderLayout.CENTER);
		p.add(bottom, BorderLayout.SOUTH);
		
		
		// �⺻ ����
		dlg.setBounds(250, 400, 300, 200);
		dlg.setVisible(true);
		add(p);
		setTitle("ä�ù�");
		setBounds(200, 200, 400, 700);
		setVisible(true);
	}
	

	
	// ���� �׼Ǹ����� �����������������������������������������������������������
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// ��ư Ŭ����
		Object obj = e.getSource();
		JButton button = (JButton) e.getSource();

		
		if(obj == exit){
			msg = input.getText();
			
			try {
				bw.write("*["+name+"] "+"���� ���� �߽��ϴ�.");
				bw.newLine(); // ����
				bw.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			chat.append(input.getText() + "\n" + "��ȭ�� �����մϴ�.\n");

			// �� 1.5�� ����â ���� �� ����
			//   : ���ε� ������!
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			
			setVisible(false); // ���� â �ݱ�
			new ChatClientObjectMain(); // ����â ������
			
		} else if(button == sendBtn){ // ������ ��ư Ŭ����	
			
			String msg = input.getText();
			try {
				bw.write("#["+name+"] "+ msg + " " + getTime());
				bw.newLine(); // ����
				bw.flush();
			} catch (IOException e1) {
				// e1.printStackTrace();
			}
			
			// chat.append(input.getText() + "\n");
			// input.setText("");
			// input.requestFocus(); // Ŀ�� �Է�â���� ��ġ
			
		} else if(button == yesButton){ // ��Ī ���ϱ�
			name = nameinput.getText();
			// System.out.println(name);
			dlg.setVisible(false);
			try {
				bw.write("$["+name+"] "+"���� ���� �߽��ϴ�.");
				bw.newLine(); // ����
				bw.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		} 
		
	}
	
	// ���� ���͸� ������ ���� �׼� ����������������������������������������������
	@Override
	public void keyPressed(KeyEvent en) {
		
		 if (en.getKeyCode() == KeyEvent.VK_ENTER) {
			 
				String msg = input.getText();
				try {
					bw.write("#["+name+"] "+ msg + " " + getTime());
					bw.newLine(); // ����
					bw.flush();
				} catch (IOException e1) {
					// e1.printStackTrace();
				}
				
				// chat.append(input.getText() + "\n");
				// input.setText("");
				// input.requestFocus(); // Ŀ�� �Է�â���� ��ġ
		 }
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}


	@Override
	public void keyReleased(KeyEvent e) {
	}
	// �������������������������������������������������������������������������
	
	
	
	// ���� ���� �޼���
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
			
			is = sock.getInputStream();    // �д� ��Ʈ��
			os = sock.getOutputStream();   // ���� ��Ʈ��
			isr = new InputStreamReader(is);
			osw = new OutputStreamWriter(os);
			br = new BufferedReader(isr);
			bw = new BufferedWriter(osw);
			
			while(true){
				// �������� sayAll�޼���� ���� (Ŭ�� �����ɶ����� ���Ͽ� ����)
				// ���⼭�� �д´�.
				String msg2 = br.readLine(); // ���������� �д´�.

				
				if(msg2.startsWith("$")){ // �� ���� �ο�! (����� ����)
					msg2 = msg2.substring(1);
					chat.append(msg2+"\n");
					msg2 = br.readLine(); // �������� 2�� ����, Ŭ�󿡼��� 2�� �б�!
					allsu.setText("�����ο� : " + msg2);      // �ο��� �Է�
					
				} else if(msg2.startsWith("*")){ // �� ���� �ο�! (����� ����)
					msg2 = msg2.substring(1);
					chat.append(msg2+"\n");
					msg2 = br.readLine(); // �������� 2�� ����, Ŭ�󿡼��� 2�� �б�!
					allsu.setText("�����ο� : " + msg2);      // �ο��� �Է�
		
				}
				
				if(msg2.startsWith("#")){ // �� �Ϲ� ��ȭ!
					msg2 = msg2.substring(1);
					chat.append(msg2+"\n");
				}
				input.setText("");    // �����Է�â ��ĭ���� �ʱ�ȭ
				input.requestFocus(); // Ŀ�� ��Ŀ��
				
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

	// ����ð��� ���ڿ��� ��ȯ�ϴ� �Լ�!
	static String getTime(){
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date());
	}

}
