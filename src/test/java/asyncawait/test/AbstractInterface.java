package asyncawait.test;

import com.async4j.Async;

import java.util.concurrent.CompletableFuture;

public interface AbstractInterface
{
    //This needs await because it has return type
    @Async
    public int asyncInt(boolean wait);

    //This is void. So, it does not need anything
    @Async
    public void asyncVoid(boolean wait);


    public String notAsyncString();


}
