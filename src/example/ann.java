package example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.LinkedList;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;

public class ann {
	
	static int count=0;
	static double pred;
	static Connection connection = null;
	static Statement statement = null;
	static String driver = "com.mysql.jdbc.Driver";
	static int days_after_currentday;
	static String tick;
	
	static int countentries=0;
	static LinkedList<Double> valuesQueue= new LinkedList<Double>();

	private static int slidingWindowSize;
	private double max = 0;
	private double min = Double.MAX_VALUE;
	private String rawDataFilePath;

	private static String learningDataFilePath = "learningData.csv";
	private String neuralNetworkModelFilePath = "stockPredictor.nnet";
	
	public ann(int slidingWindowSize, String rawDataFilePath) {
		this.rawDataFilePath = rawDataFilePath;
		this.slidingWindowSize = slidingWindowSize;
	}
	
    public double ann_predictor() throws IOException{
    	ann predictor = new ann(5, "rawTrainingData.csv");
    	predictor.prepareData();
    	System.out.println("Training starting");
    	predictor.trainNetwork();	
    	System.out.println("Testing network");
    	double predictedvalue=predictor.testNetwork();
    	return predictedvalue;
    }
	

	public void prepareData() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(rawDataFilePath));
		// Find the minimum and maximum values - needed for normalization
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(",");
				double crtValue = Double.valueOf(tokens[1]);
				if (crtValue > max) {
					max = crtValue;
				}
				if (crtValue < min) {
					min = crtValue;
				}
			}
		} finally {
			reader.close();
		}

		reader = new BufferedReader(new FileReader(rawDataFilePath));
		BufferedWriter writer = new BufferedWriter(new FileWriter(learningDataFilePath));

		// Keep a queue with slidingWindowSize + 1 values
//		valuesQueue = new LinkedList<Double>();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				double crtValue = Double.valueOf(line.split(",")[1]);
				// Normalize values and add it to the queue
				double normalizedValue = normalizeValue(crtValue);
				valuesQueue.add(normalizedValue);
				countentries++;
				

				if (valuesQueue.size() == slidingWindowSize + 1) 
				{
					String valueLine = valuesQueue.toString().replaceAll("\\[|\\]", "");
					writer.write(valueLine);
					writer.newLine();
					// Remove the first element in queue to make place for a new
					// one
					valuesQueue.removeFirst();
				}
			}
		} finally {
			reader.close();
			writer.close();
		}
	}

	 public double normalizeValue(double input) {
		return (input - min) / (max - min) * 0.8 + 0.1;
	}

	public double deNormalizeValue(double input) {
		return min + (input - 0.1) * (max - min) / 0.8;
	}

	public void trainNetwork() throws IOException {
		NeuralNetwork<BackPropagation> neuralNetwork = new MultiLayerPerceptron(slidingWindowSize,
				2 * slidingWindowSize + 1, 1);

		int maxIterations = 1000;
		double learningRate = 0.5;
		double maxError = 0.00001;
		//System.out.println(maxError);
		SupervisedLearning learningRule = neuralNetwork.getLearningRule();
		learningRule.setMaxError(maxError);
		learningRule.setLearningRate(learningRate);
		learningRule.setMaxIterations(maxIterations);
		learningRule.addListener(new LearningEventListener() {
			public void handleLearningEvent(LearningEvent learningEvent) {
				SupervisedLearning rule = (SupervisedLearning) learningEvent.getSource();
				System.out.println("Network error for interation " + rule.getCurrentIteration() + ": "
						+ rule.getTotalNetworkError());
			}
		});

		DataSet trainingSet = loadTraininigData(learningDataFilePath);
		neuralNetwork.learn(trainingSet);
		neuralNetwork.save(neuralNetworkModelFilePath);
	}

	DataSet loadTraininigData(String filePath) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		DataSet trainingSet = new DataSet(slidingWindowSize, 1);

		try {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(",");

				double trainValues[] = new double[slidingWindowSize];
				for (int i = 0; i < slidingWindowSize; i++) {
					trainValues[i] = Double.valueOf(tokens[i]);
				}
				double expectedValue[] = new double[] 
						{ Double.valueOf(tokens[slidingWindowSize]) };
				trainingSet.addRow(new DataSetRow(trainValues, expectedValue));
			}
		} finally {
			reader.close();
		}
		return trainingSet;
	}
	
	
	public double testNetwork() 
	{
		
		NeuralNetwork neuralNetwork = NeuralNetwork.createFromFile(neuralNetworkModelFilePath);
//		neuralNetwork.setInput(normalizeValue(2056.15), normalizeValue(2061.02), normalizeValue(2086.24),
//				normalizeValue(2067.89), normalizeValue(2059.69));
		neuralNetwork.setInput(valuesQueue.get(0),  valuesQueue.get(1),
				valuesQueue.get(2), valuesQueue.get(3),valuesQueue.get(4));

		neuralNetwork.calculate();
		double[] networkOutput = neuralNetwork.getOutput();
		//System.out.println("Expected value  : 2066.96");
		 pred=deNormalizeValue(networkOutput[0]);
		System.out.println("Predicted value : " +pred );
		while(count<days_after_currentday)
		{
			double normalizedValue = normalizeValue(pred);
			valuesQueue.add(normalizedValue);
			//countentries++;
			
			if (valuesQueue.size() == slidingWindowSize + 1) 
			{
				
				valuesQueue.removeFirst();
			}
			neuralNetwork.setInput(valuesQueue.get(0),  valuesQueue.get(1),
					valuesQueue.get(2), valuesQueue.get(3),valuesQueue.get(4));
			neuralNetwork.calculate();
			double[] networkOutput1 = neuralNetwork.getOutput();
//			//System.out.println("Expected value  : 2066.96");
			 pred=deNormalizeValue(networkOutput1[0]);
			System.out.println("Predicted value  " +count+": "+pred );
			//testNetwork();
			count++;
//			
		}
		System.out.println("Prediction for "+days_after_currentday+"th day from today:"+pred);
		return pred;

	}

}
