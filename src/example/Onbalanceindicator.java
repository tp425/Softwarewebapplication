package example;

import java.util.ArrayList;
import java.util.HashMap;

public class Onbalanceindicator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Onbalanceindicator ind=new Onbalanceindicator();
		//ind.obv();

	}
	
	public ArrayList<Integer> obv(double[] close_price,int[] input){//close_price and volume
		
		int result = 0;
		ArrayList<Integer> result_array = new ArrayList<>();
		//double[] close_price={53.26,53.30,53.32,53.72,54.19,53.92,54.65,54.60};
//		int[] input= {8200,8100,8300,8900,9200,13300,10300,9900};
		//result=input[0];
		for(int i=0;i< close_price.length-1;i++){			
//			for(int j=i+1;j<input.length;j++){
			System.out.println("i= "+close_price[i]+ " i+1= "+close_price[i+1]);
				if(close_price[i]<close_price[i+1]){
					System.out.println("+1 up");
					result=result+input[i];
					System.out.println(result);
					result_array.add(result);
					
				}
				else {
					System.out.println("-1 down");					
					result=result-input[i];
					System.out.println(result);
					result_array.add(result);
					
				}
//			}
		}
		System.out.println("Final"+result_array);		
		return result_array;
	}

}
