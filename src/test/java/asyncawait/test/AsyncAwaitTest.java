package asyncawait.test;

import com.async4j.proxy.AsyncProxyFactory;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AsyncAwaitTest
{
    //Typo could either be ConcreteImpl or AbstractInterface here.
    private ConcreteImpl test = new ConcreteImpl();;
    private AbstractInterface testProxy = AsyncProxyFactory.getDynamicProxy(test, AbstractInterface.class);


    @Test
    public void asyncIntWithWaitTest()
    {
        int number = testProxy.asyncInt(true);
        assertEquals(number, 10);
    }

    @Test
    public void asyncIntWithOutWaitTest()
    {
        int number = testProxy.asyncInt(false);
        assertEquals(number, 0);
    }


    @Test
    public void asyncVoidWithWaitTest()
    {
        Instant start = Instant.now();
        testProxy.asyncVoid(true);
        Instant end = Instant.now();
        System.out.println("Start:" + start);
        System.out.println("End:" + end);

        Long dur = Duration.between(end, start).abs().toMillis();
        System.out.println("Dur:" + dur);
        assert(dur > 10_000);
    }


    @Test
    public void asyncVoidWithOutWaitTest()
    {
        Instant start = Instant.now();
        testProxy.asyncVoid(false);
        Instant end = Instant.now();
        Long dur = Duration.between(end, start).abs().toMillis();
        assert(dur < 10_000);
    }



    @Test
    public void notAsyncMethodTest()
    {
        assert (testProxy.notAsyncString().equals("NON BLOCKING NON ASYNC"));
    }


    @Test
    public void failedAsyncOnlyAllowedInInterfaceTest()
    {
        test.failedOnlyInterfaceTest();
    }


}
