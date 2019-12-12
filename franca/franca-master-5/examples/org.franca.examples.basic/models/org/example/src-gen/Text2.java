import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Text2 {
	
	public static void main(String[] args) {
		new Text2().run();
	}
	
	
	protected void run() {
		boolean executeActions = true;
		String currentState = "";
		String lastEvent = null;
		while (true) {
			
		}
	}
	
	private String receiveEvent() {
		System.out.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			return br.readLine();
		} catch (IOException ioe) {
			System.out.println("Problem reading input");
			return "";
		}
	}
}
