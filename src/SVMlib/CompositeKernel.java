package SVMlib;



import java.util.ArrayList;
import java.util.List;

/**
 * Reference from github -libsvm-kernel-master
 */
public class CompositeKernel implements CustomKernel {
    private List<CustomKernel> kernels;

    public CompositeKernel() {
        this(new ArrayList<CustomKernel>());
    }   
    
    public CompositeKernel(List<CustomKernel> kernels) {
        this.kernels = kernels;
    }
   
    @Override
    public double evaluate(svm_node x, svm_node y) {
        double value = 0.0;
        for (CustomKernel kernel : kernels) {
            value += kernel.evaluate(x, y);
        }
        return value;
    }
}
