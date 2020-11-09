package asyncawait.test;

import com.async4j.Async;

import java.lang.reflect.Parameter;
import java.util.concurrent.TimeUnit;

public class ConcreteImpl implements AbstractInterface {
    @Override
    public int asyncInt(boolean wait) {
        System.out.println("int: Preparing to run time consuming task that takes about least 10 seconds.");
        try {
            TimeUnit.SECONDS.sleep(10);
            System.out.println("int: Finished time consuming task.");
            return 10;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void asyncVoid(boolean wait) {
        System.out.println("void: Preparing to run time consuming task that takes about least 10 seconds.");
        try {
            TimeUnit.SECONDS.sleep(10);
            System.out.println("void: Finished time consuming task.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String notAsyncString()
    {
        return "NON BLOCKING NON ASYNC";
    }

    public void nonAsyncNonBlockingMethodTest()
    {
        System.out.println("Blocking Non Async Task");
    }


    public int giveMePlus5(int number)
    {
        return number + 5;
    }

    @Async
    public void failedOnlyInterfaceTest()
    {
        System.out.println("You can only have Async annotation in interface.");
    }

}
