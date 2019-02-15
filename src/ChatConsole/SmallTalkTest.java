package ChatConsole;

public class SmallTalkTest
{
	
	public static void main(String[] args) {
		for ( int i =0; i <10; i++) {
			final int k =i;
			Thread t = new Thread( new Runnable() {
				@Override
				public void run() {
					new ChatClient("Bot"+k);
				}
			});
			t.start();
		}
	}

}
