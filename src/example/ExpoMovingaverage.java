package example;

public class ExpoMovingaverage {
	
	public static void main(String[] args) {
		double[] input={22.27,22.19,22.08,22.17,2,22.18,22.13,22.23,22.43,22.24,
				22.29,22.15,22.39,22.38,22.61,23.36,24.05,23.75,23.83,23.95,23.63};
	//	double[] input ={1,2,3,4,5,5,4,3,2,1};
		int window=10;
		System.out.println("Simple moving average");
		double[]sma=myAverage(input,window);
		for(int i=0;i<sma.length;i++) System.out.println(sma[i]);
		System.out.println("Exponential moving average");
		double[]ema=myEMAverage(input,window);
		for(int i=0;i<ema.length;i++) System.out.println(ema[i]);
	}
//	public static double[] myAverage(List<ExtractData> data, int window) //simple moving average
//	{
//		double[] dataA = new double[data.size()];
//		int i=0;
//		for(ExtractData ed : data) {
//			dataA[i++] = ed.close;
//		}
//		return myAverage(dataA,window);
//	}
//	public static double[] myEMAverage(List<ExtractData> data, int window) //simple moving average
//	{
//		double[] dataA = new double[data.size()];
//		int i=0;
//		for(ExtractData ed : data) 
//		{
//			dataA[i++] = ed.close;
//		}
//		return myEMAverage(dataA,window);
//	}

	public static double[] myAverage(double[] data, int window) //simple moving average
	{
		int range = data.length;
		double[] MA = new double[range-window+1];
		for (int j=window-1 ; j<range ; j++)
			{
				double tmp = 0;
				for (int i=j-window+1; i<j+1 ; i++)
					{
						tmp = tmp+data[i];        
					}
//				System.out.println("tmp"+tmp);
				MA[j-window+1] = tmp/window;
      
			}
		return MA;
  
	}

	public static double[] myEMAverage(double[] data, int window) //exponential moving average
	{
		double[] EMA = new double[data.length-window+1];
		double sum = 0;
		double multiplier = 2/(double)(window+1);
		System.out.println("mul"+multiplier);
		for(int i=0 ; i< window ; i++)
			{
				sum = sum+data[i];        
			}
		EMA[0] = sum/window; //first term of EMA is basically the moving average for the first N terms
		for(int j=window ; j<data.length ; j++)
			{
				int index = j-window+1;
				EMA[index] = data[j] * multiplier + EMA[index-1] *(1-multiplier); 
			}
      
      
		return EMA;
	}

}
