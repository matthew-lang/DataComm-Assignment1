import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class VowelServer implements Runnable {

	public static boolean stop = false;
	public static void main(String[] args) {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(12345);

		} catch (IOException e) {
			e.printStackTrace();
		}
		while (!stop) {
			try {
				Socket s = ss.accept();
				System.out.println("Client Connected!");
				//start in a new thread to keep serversocket open for more connections
				Thread t = new Thread(new VowelServer(s));
				t.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Socket socket;

	public VowelServer(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			while(!input.ready()) {
				try{Thread.sleep(100);}catch(Exception e) {}
			}
			String in = input.readLine();
			System.out.println("Parsing: " + in);
			int vowelCount = 0;
			for (char c : in.toCharArray()) {
				if (isVowel(c)) {
					vowelCount++;
				}
			}
			output.write(Integer.toString(vowelCount)+"\n");
			output.flush();
			output.close();
			input.close();
			socket.close();
			System.out.println("Job Done");
		} catch (Exception e) {
			System.out.println("Failed to generate response.");
		}
	}

	public boolean isVowel(char c) {
		return Character.toString(c).matches("a|e|i|o|u");
	}

}
