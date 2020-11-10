package compiletimewarningexamples;

import com.async4j.core.Async;

public class CompileTimeWarning
{
    @Async
    public void DoesNotHaveAsyncInNameInClass()
    {
        System.out.println("This function has no async in its name");
    }

    @Async
    private void IsNotPublicMethodInClass()
    {
        System.out.println("This function is not public.");
    }
}
