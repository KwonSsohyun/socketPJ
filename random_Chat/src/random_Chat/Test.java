package random_Chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Test extends Thread {
	public static void main(String[] args) { // ▶ 클라이언트
		
		Socket sock = null;
		InputStream is = null;
		
		
		// 문자스트림 변환
		OutputStreamWriter osw = null;
		InputStreamReader isr = null;
		
		try {
			// 클라이언트
			
			// 1. 소켓 연결
			sock = new Socket();
			System.out.println("[연결 요청]");
			sock.connect(new InetSocketAddress("localhost", 5000));
			System.out.println("[연결 성공]");
			
			byte[] bytes = null;
            String message = null;
			// 2. 소켓 데이터 전송
			OutputStream outPutData = sock.getOutputStream();
			message = "Hello common.Server, I'm common.Client.";
            bytes = message.getBytes("UTF-8");
            outPutData.write(bytes);
            outPutData.flush();
            System.out.println("[클라이 언트 -> 서버 데이터 보내기 성공]");
            
            // 서버에게 데이터 받기
            InputStream rereceiveServerData = sock.getInputStream();
            bytes = new byte[100];
            int readByteCount = rereceiveServerData.read(bytes);
            message = new String(bytes,0,readByteCount,"UTF-8");
            System.out.println("[데이터 받기 성공] " + message);
            
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				sock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public String sendMessageToServer(String message) {
		
		Socket sock = null;
		
		try {
			sock = new Socket();
			System.out.println("[연결 요청]");
			sock.connect(new InetSocketAddress("localhost", 5000));
			System.out.println("[연결 성공]");
			
			byte[] bytes = null;
			OutputStream outPutData = sock.getOutputStream();
            bytes = message.getBytes("UTF-8");
            outPutData.write(bytes);
            outPutData.flush();
            System.out.println("[클라이 언트 -> 서버 데이터 보내기 성공]");
            
            InputStream rereceiveServerData = sock.getInputStream();
            bytes = new byte[100];
            int readByteCount = rereceiveServerData.read(bytes);
            message = new String(bytes,0,readByteCount,"UTF-8");
            System.out.println("[데이터 받기 성공] " + message);
            
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				sock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return message;
	}
}
