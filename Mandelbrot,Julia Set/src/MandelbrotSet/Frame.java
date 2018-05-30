package MandelbrotSet;

import javax.swing.JFrame;

public class Frame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	Panel p;

	public Frame()
	{
		setSize(1000, 1000);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		p = new Panel();
		setContentPane(p);
		setVisible(true);
	}

	public static void main(String[] args)
	{
		new Frame();
				
		
	}
	
	public static boolean isPrime(int num)
	{
		if(num < 2)
			return false;
		
		for(int j = 2; j <= Math.sqrt(num); j++)
			if(num % j == 0)
				return false;
		
		return true;
	}
	
	public static boolean isTruncatable(int primeNum)
	{
		int numLength  = (primeNum + "").length();
		
		for(int i = 1; i < numLength; i++)
		{
			if(!isPrime(primeNum % (int)Math.pow(10, i)))
				return false;
			
			if(!isPrime(primeNum / (int)Math.pow(10, i)))
				return false;
		}
		
		return true;
	}
}
