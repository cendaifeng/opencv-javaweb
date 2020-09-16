import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import static org.opencv.highgui.HighGui.*;

class Det {
    public void run() {
        /**
         * 级联加载器（滑动窗口机制+级联分类）
         * opencv4.4.0文档/javadoc/org/opencv/objdetect/CascadeClassifier.html
         * 训练参考博客 https://blog.csdn.net/Txiaomiao/article/details/64132273
         * 官方训练好的数据集在 C:\APP\environment\opencv\sources\data\haarcascades 下
         * 视频人脸识别官方C++示例代码 http://www.opencv.org.cn/opencvdoc/2.3.2/html/doc/tutorials/objdetect/cascade_classifier/cascade_classifier.html
         */
        // 从一个训练文件中加载一个分类器
        CascadeClassifier classifier = new CascadeClassifier("src/main/resources/haarcascade_frontalface_alt.xml");
        // 读取图像创建 Mat 对象
        Mat img = Imgcodecs.imread("src/main/resources/img-01.jpg");
        // 测试图像显示
        namedWindow("showimg", WINDOW_NORMAL);
        imshow("showimg", img);
        waitKey(2000);
        // 矩形容器 MatOfRect 用以框定人脸
        MatOfRect faceDet = new MatOfRect();
        // 检测
        classifier.detectMultiScale(img, faceDet);
        // 输入检测到的人脸数量
        System.out.println(String.format("Detected %s faces", faceDet.toArray().length));
        // 作画
        for (Rect rect : faceDet.toArray()) {
            Imgproc.rectangle(img, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
        }
        // 保存图像
        String filename = "faceDetImg.png";
        System.out.println(String.format("Writing %s", filename));
        Imgcodecs.imwrite(filename, img);

    }
}


public class FaceDetTest {
    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        new Det().run();
    }
}
