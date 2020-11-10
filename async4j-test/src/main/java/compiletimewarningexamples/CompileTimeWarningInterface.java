package compiletimewarningexamples;

import com.async4j.core.Async;

public interface CompileTimeWarningInterface
{
    @Async
    public void DoesNotHaveAsyncInNameInInterface(boolean isWaiting);

    @Async
    public static void IsStaticMethodInInterface()
    {
        System.out.println("This function is not static.");
    }

    @Async
    public void doesNotHaveAnyParameterInInterface();

    @Async
    public void firstParameterIsNotBooleanInInterface(int random);
}
