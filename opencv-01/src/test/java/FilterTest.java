import filter.Filter2d;
import org.opencv.core.Core;

public class FilterTest {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

//        new Filter2d().NormalizedBox(null, -1);
        new Filter2d().GaussianFilter(null, -1);
    }
}
