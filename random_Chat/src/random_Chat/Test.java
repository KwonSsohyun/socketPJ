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
	public static void main(String[] args) { // �� Ŭ���̾�Ʈ
		
		Socket sock = null;
		InputStream is = null;
		
		
		// ���ڽ�Ʈ�� ��ȯ
		OutputStreamWriter osw = null;
		InputStreamReader isr = null;
		
		try {
			// Ŭ���̾�Ʈ
			
			// 1. ���� ����
			sock = new Socket();
			System.out.println("[���� ��û]");
			sock.connect(new InetSocketAddress("localhost", 5000));
			System.out.println("[���� ����]");
			
			byte[] bytes = null;
            String message = null;
			// 2. ���� ������ ����
			OutputStream outPutData = sock.getOutputStream();
			message = "Hello common.Server, I'm common.Client.";
            bytes = message.getBytes("UTF-8");
            outPutData.write(bytes);
            outPutData.flush();
            System.out.println("[Ŭ���� ��Ʈ -> ���� ������ ������ ����]");
            
            // �������� ������ �ޱ�
            InputStream rereceiveServerData = sock.getInputStream();
            bytes = new byte[100];
            int readByteCount = rereceiveServerData.read(bytes);
            message = new String(bytes,0,readByteCount,"UTF-8");
            System.out.println("[������ �ޱ� ����] " + message);
            
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
			System.out.println("[���� ��û]");
			sock.connect(new InetSocketAddress("localhost", 5000));
			System.out.println("[���� ����]");
			
			byte[] bytes = null;
			OutputStream outPutData = sock.getOutputStream();
            bytes = message.getBytes("UTF-8");
            outPutData.write(bytes);
            outPutData.flush();
            System.out.println("[Ŭ���� ��Ʈ -> ���� ������ ������ ����]");
            
            InputStream rereceiveServerData = sock.getInputStream();
            bytes = new byte[100];
            int readByteCount = rereceiveServerData.read(bytes);
            message = new String(bytes,0,readByteCount,"UTF-8");
            System.out.println("[������ �ޱ� ����] " + message);
            
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
