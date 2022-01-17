// �� ChatServerObject // ����

package random_Chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ChatServerObject extends Thread{ 
	
	// �ʵ�
	static ArrayList<Socket> list = new ArrayList<>();
	Socket sock = null;
	static int sum;              // �ο�
	static int temp; 
	static int q;
	static int j;
	
	// ���� �۾�
	public void sayAll(String msg) throws IOException{
		OutputStream os = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		
		for(int i=0; i<list.size(); i++){
			Socket sock = list.get(i);
			
			os = sock.getOutputStream(); 
			osw = new OutputStreamWriter(os);
			bw = new BufferedWriter(osw);
			
			bw.write(msg);
			bw.newLine(); // ����
			bw.flush();
			
			if(msg.startsWith("$")){ // �� �ο� ���� (����)
				sum=1+j;
				temp = sum;
				sum = temp - (q*(q+1))/2;
				
				if(temp>=2){
					sum=2;
				} 
				
				j++;
				q++;
		
				// System.out.println(sum);
				bw.write(sum+"");
				bw.newLine();
				bw.flush();
				
				// int su = list.size();
				// bw.write(su+""); // bw.write(String.valueOf(su));
				// bw.newLine();
				// bw.flush();
			} else if(msg.startsWith("*")){ // �� �ο� ���� (����)
				sum--;

				// System.out.println(sum);
				bw.write(sum+"");
				bw.newLine();
				bw.flush();
				
			}
			
			
			
		
		}
		
	}
	
	@Override
	public void run() { // �����忡�� ������ run�޼��忡 �������̵��Ѵ�.
		/////////////////////////////////////
		InputStream is = null;
		OutputStream os = null;
		InputStreamReader isr = null;
		OutputStreamWriter osw = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		/////////////////////////////////////
		
		try {
			is = sock.getInputStream();     // �д� ��Ʈ��
			// os = sock.getOutputStream(); // ���� ��Ʈ��
			isr = new InputStreamReader(is);
			// osw = new OutputStreamWriter(os);
			br = new BufferedReader(isr);
			// bw = new BufferedWriter(osw);
			//////////////////////////////////
			
			while(true){ // �ް� �ִ�. 
			
				String msg = br.readLine(); // Ŭ���̾�Ʈ���� bw�� ���� // ���������� br�� �д´�.
				// ��������!!! �� �� ������ �Է½�Ʈ���� ����� ������ ��½�Ʈ���� ����ǰ�
				//               ��½�Ʈ���� �Է½�Ʈ���� ����ȴ�.
				//               �׷��� �� ���Ͽ��� ��½�Ʈ������ �����͸� ������ 
				//               ����� ���Ͽ����� �Է½�Ʈ������ �ް� �ȴ�.
				
				// if(msg.startsWith("*")){ // �� �ο� ���� (����)
					// list.remove(sock);
				// }
				// System.out.println(sock); �� ������ ���� Ȯ��
				
				sayAll(msg); // ��� Ŭ���̾�Ʈ���� �Ѹ���.
				
				// sock.isClosed() // true:���ϴ��� // false:���Ͽ���
				// if(!sock.isConnected()){ // true:������ ��������� // false:���Ϲ̿���
					// list.remove(sock);
				// }
				
				

			} 
		} catch (IOException e) {
			// e.printStackTrace();
		} finally {
			try {
				//bw.close();
				br.close();
				//osw.close();
				isr.close();
				//os.close();
				is.close();
				sock.close();
			} catch (IOException e) {
				// e.printStackTrace();
			}
		}
	}
	
	// �������������������������������������������������������������������������
	
	// ���� ���� �޼���
	public static void main(String[] args) { // �� ����
		
		ServerSocket serve = null;
		
		try {
			serve = new ServerSocket(5000);
			
			while(true){
				ChatServerObject thr = new ChatServerObject();
				// Socket sock = null;
				thr.sock = serve.accept();
				///////////////////////////////////////////////
				// ������ �ڵ�
				list.add(thr.sock);
				thr.start();
				
				
				///////////////////////////////////////////////
			}
		} catch (IOException e) {
			// e.printStackTrace();
		} finally {
			try {
				serve.close();
			} catch (IOException e) {
				// e.printStackTrace();
			}
		}
		
		
		
		
		
		
		
	}
}
