import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener extends Method_container implements ITestListener
{

    int i=1;
    @Override
    public void onTestStart(ITestResult result)
    {
        System.out.println("**Test case "+i+" started**");
    }
    @Override
    public void onTestSuccess(ITestResult result)
    {

        System.out.println("---Test case "+i+" ran successfully---");
        i+=1;
    }

}