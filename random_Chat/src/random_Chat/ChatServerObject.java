// ▶ ChatServerObject // 서버

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
	
	// 필드
	static ArrayList<Socket> list = new ArrayList<>();
	Socket sock = null;
	static int sum;              // 인원
	static int temp; 
	static int q;
	static int j;
	
	// 쓰는 작업
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
			bw.newLine(); // 개행
			bw.flush();
			
			if(msg.startsWith("$")){ // ▶ 인원 집계 (입장)
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
			} else if(msg.startsWith("*")){ // ▶ 인원 집계 (퇴장)
				sum--;

				// System.out.println(sum);
				bw.write(sum+"");
				bw.newLine();
				bw.flush();
				
			}
			
			
			
		
		}
		
	}
	
	@Override
	public void run() { // 쓰래드에서 할일을 run메서드에 오버라이딩한다.
		/////////////////////////////////////
		InputStream is = null;
		OutputStream os = null;
		InputStreamReader isr = null;
		OutputStreamWriter osw = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		/////////////////////////////////////
		
		try {
			is = sock.getInputStream();     // 읽는 스트림
			// os = sock.getOutputStream(); // 쓰는 스트림
			isr = new InputStreamReader(is);
			// osw = new OutputStreamWriter(os);
			br = new BufferedReader(isr);
			// bw = new BufferedWriter(osw);
			//////////////////////////////////
			
			while(true){ // 받고 있다. 
			
				String msg = br.readLine(); // 클라이언트에서 bw로 쓰니 // 서버에서는 br로 읽는다.
				// 교차연결!!! → 한 소켓의 입력스트림은 상대편 소켓의 출력스트림과 연결되고
				//               출력스트림은 입력스트림과 연결된다.
				//               그래서 한 소켓에서 출력스트림으로 데이터를 보내면 
				//               상대편 소켓에서는 입력스트림으로 받게 된다.
				
				// if(msg.startsWith("*")){ // ▶ 인원 집계 (퇴장)
					// list.remove(sock);
				// }
				// System.out.println(sock); ▶ 각각의 소켓 확인
				
				sayAll(msg); // 모든 클라이언트한테 뿌린다.
				
				// sock.isClosed() // true:소켓닫힘 // false:소켓열림
				// if(!sock.isConnected()){ // true:소켓이 연결되있음 // false:소켓미연결
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
	
	// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	
	// ▶▶ 메인 메서드
	public static void main(String[] args) { // ▶ 서버
		
		ServerSocket serve = null;
		
		try {
			serve = new ServerSocket(5000);
			
			while(true){
				ChatServerObject thr = new ChatServerObject();
				// Socket sock = null;
				thr.sock = serve.accept();
				///////////////////////////////////////////////
				// 쓰래드 코드
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
