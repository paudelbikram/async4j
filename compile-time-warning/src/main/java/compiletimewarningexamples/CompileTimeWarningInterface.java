package compiletimewarningexamples;

import com.async4j.Async;

public interface CompileTimeWarningInterface
{
    @Async
    public void DoesNotHaveAsyncInName();

    @Async
    public static void IsStaticMethod()
    {
        System.out.println("This function is not static.");
    }

    @Async
    public void doesNotHaveAnyParameter();

    @Async
    public void firstParameterIsNotBoolean(int random);
}
