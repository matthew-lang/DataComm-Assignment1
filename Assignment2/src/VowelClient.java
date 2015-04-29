import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class VowelClient {
	public static void main(String[] args) throws IOException{
		Socket s = new Socket(args[0], 12345);
		String sentence = "";
		for(int i = 1;i<args.length;i++) {
			sentence+= args[i]+" ";
		}
		sentence = sentence.trim();
		System.out.println("Sending sentence: " + sentence);
		
		BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));
		output.write(sentence+"\n");
		output.flush();
		while(!input.ready()) {
			try{Thread.sleep(100);}catch(Exception e) {}
		}
		String in = input.readLine();
		System.out.println("It has " + in + " vowels!");
		input.close();
		output.close();
		s.close();
	}
}
