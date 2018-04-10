package SystemTray;

import java.util.function.Consumer;

import org.jnativehook.keyboard.NativeKeyAdapter;
import org.jnativehook.keyboard.NativeKeyEvent;

public class GlobalKeyEvent extends NativeKeyAdapter
{	
	private Consumer<Integer> execute;
	
	public GlobalKeyEvent(Consumer<Integer> execute)
	{
		this.execute = execute;
	}
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent e)
	{
		if(e.getKeyCode() == NativeKeyEvent.VC_PRINTSCREEN)
			execute.accept(1);
	}
}
