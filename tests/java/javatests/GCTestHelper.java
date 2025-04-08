package javatests;

import java.lang.ref.Cleaner.Cleanable;
import java.util.ArrayList;
import java.util.List;

import org.python.core.Py;
import org.python.core.PyCleaner;
import org.python.core.PyFloat;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;

public class GCTestHelper {

    /**
     * In contrast to NastyFinalizer, this class - still equally
     * time-consuming - calls {@code gc.notifyPreFinalization()}
     * and {@code gc.notifyPostFinalization()} and thus lets all
     * tests work as expected.
     */
    public static class NotSoNastyFinalizer implements AutoCloseable {
        private final Cleanable cleanable;

        public NotSoNastyFinalizer() {
            Runnable cleaningFunction = () -> {
                System.err.println("+++ GCTestHelper::cleaningFunction");
                org.python.modules.gc.notifyPreFinalization();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ie) {
                }
                org.python.modules.gc.notifyPostFinalization();
            };
            cleanable = PyCleaner.INSTANCE.get().register(this, cleaningFunction);
        }

        @Override
        public void close() {
            cleanable.clean();
        }
    }

    /**
     * PyReflectionTraversed is a test-PyObject that intentionally
     * violates the Traverseproc-mechanism, i.e. holds a non-static
     * reference to another PyObject, but neither implements Traverseproc
     * nor is marked as untraversable.
     */
    public static class PyReflectionTraversed extends PyObject {
        PyObject referent1;
        PyObject[] refArray = new PyObject[3];
        List<Object> refList = new ArrayList<>();
        PyObject referent2;

        public PyReflectionTraversed() {
            super();
        }
    }

    public static PyObject reflectionTraverseTestField() {
        PyReflectionTraversed result = new PyReflectionTraversed();
        result.referent1 = new PyList();
        result.refArray[0] = new PyInteger(244);
        result.refArray[1] = new PyString("test1");
        result.refArray[2] = new PyInteger(333);
        result.refList.add(new PyFloat(0.7));
        result.referent2 = result;
        return result;
    }

    public static PyObject reflectionTraverseTestList() {
        PyReflectionTraversed result = new PyReflectionTraversed();
        result.referent1 = new PyList();
        result.refArray[0] = new PyInteger(245);
        result.refArray[1] = new PyString("test2");
        result.refArray[2] = new PyInteger(334);
        result.refList.add(new PyFloat(0.71));
        result.refList.add(result);
        return result;
    }

    public static PyObject reflectionTraverseTestArray() {
        PyReflectionTraversed result = new PyReflectionTraversed();
        result.referent1 = new PyList();
        result.refArray[0] = new PyInteger(246);
        result.refArray[1] = new PyString("test3");
        result.refArray[2] = result;
        result.refList.add(new PyFloat(0.72));
        return result;
    }

    public static PyObject reflectionTraverseTestPyList() {
        PyReflectionTraversed result = new PyReflectionTraversed();
        result.referent1 = new PyList();
        result.refArray[0] = new PyInteger(247);
        result.refArray[1] = new PyString("test4");
        result.refArray[2] = new PyInteger(335);
        result.refList.add(new PyFloat(0.73));
        result.refList.add(new PyFloat(-0.73));
        PyList pl = new PyList();
        result.referent2 = pl;
        pl.add(Py.True);
        pl.add(new PyString("test5"));
        pl.add(result);
        return result;
    }

    public static PyObject reflectionTraverseTestCycle() {
        PyReflectionTraversed result = new PyReflectionTraversed();
        result.referent1 = new PyList();
        result.refArray[0] = new PyInteger(248);
        result.refArray[1] = new PyString("test6");
        result.refArray[2] = new PyInteger(336);
        result.refList.add(new PyFloat(0.74));
        result.refList.add(result.refList);
        return result;
    }
}
