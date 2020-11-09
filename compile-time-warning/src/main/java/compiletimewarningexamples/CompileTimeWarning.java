package compiletimewarningexamples;

import com.async4j.Async;

public class CompileTimeWarning
{
    @Async
    public void DoesNotHaveAsyncInName()
    {
        System.out.println("This function has no async in its name");
    }

    @Async
    private void IsNotPublicMethod()
    {
        System.out.println("This function is not public.");
    }
}
