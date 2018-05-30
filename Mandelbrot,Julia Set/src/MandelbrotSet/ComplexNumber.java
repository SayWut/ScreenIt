package MandelbrotSet;

public class ComplexNumber
{
	private double real = 0;
	private double imaginery = 0;

	public ComplexNumber(double a, double b)
	{
		real = a;
		imaginery = b;
	}

	public ComplexNumber sum(ComplexNumber cn)
	{
		return new ComplexNumber(real + cn.real, imaginery + cn.imaginery);
	}
	
	public static ComplexNumber pow2(ComplexNumber cn)
	{
		double real = Math.pow(cn.real, 2) - Math.pow(cn.imaginery, 2);
		double imaginery = 2*cn.real*cn.imaginery;
				
		return new ComplexNumber(real, imaginery);
	}
	
	public double abs()
	{
		return Math.sqrt(Math.pow(real, 2) + Math.pow(real, 2));
	}
	
	@Override
	public String toString()
	{
		return String.format("%f + %f", real, imaginery);
	}

	public void setImaginery(double imaginery)
	{
		this.imaginery = imaginery;
	}

	public void setReal(double real)
	{
		this.real = real;
	}

	public double getReal()
	{
		return real;
	}

	public double getImaginery()
	{
		return imaginery;
	}
}
