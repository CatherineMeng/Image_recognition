package ImageRecognition;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifier;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifiers;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.CreateClassifierOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.DeleteClassifierOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.DetectFacesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.DetectedFaces;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.GetClassifierOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ListClassifiersOptions;

public class testPost {
	private static String dirFile = "./Image/";
	private static String key = "kSNDmdLt2ZVJsbqpWbP_Rd0bfDp_laplkz2pmARGxXvi";
	private static IamOptions options = new IamOptions.Builder().apiKey(key).build();
	private static VisualRecognition service = new VisualRecognition("2018-03-19", options);
	
	public static void face_recognition(String filename) throws FileNotFoundException {
		DetectFacesOptions detectFacesOptions = new DetectFacesOptions.Builder()
				.imagesFile(new File(dirFile + filename)).build();
		
		DetectedFaces result = service.detectFaces(detectFacesOptions).execute();
		System.out.println(result);
	}
	 
	
	public static void costomerize_test(String filename, String owner) throws FileNotFoundException {
		InputStream imagesStream = new FileInputStream(dirFile + filename);
		ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
		.imagesFile(imagesStream)
		.imagesFilename(filename)
		.threshold((float) 0.6)
		.owners(Arrays.asList(owner))
		.build();
		ClassifiedImages result = service.classify(classifyOptions).execute();
		System.out.println(result);
	}
	
	public static void createClassifier() throws FileNotFoundException {
		System.out.println("creating objects...");
		CreateClassifierOptions createClassifierOptions = new CreateClassifierOptions.Builder()
		.name("newly_dogs")
		.addClass("beagle", new File("./Image/beagle.zip"))
		.addClass("goldenretriever",new File("./Image/golden-retriever.zip"))
		.addClass("husky", new File("./Image/husky.zip"))
		.negativeExamples(new File("./Image/cats.zip"))
		.build();

		Classifier classifier = service.createClassifier(createClassifierOptions).execute();
		System.out.println(classifier);
	}
	
	public static String getClassifier() {
		ListClassifiersOptions listClassifiersOptions = new ListClassifiersOptions.Builder()
		.verbose(true)
		.build();
		Classifiers classifiers = service.listClassifiers(listClassifiersOptions).execute();
		//System.out.println(classifiers);
		return classifiers.toString();
	}
	
	public static void getClassifier(String name) {
		GetClassifierOptions getClassifierOptions = new GetClassifierOptions.Builder(name).build();
		Classifier classifier = service.getClassifier(getClassifierOptions).execute();
		
		System.out.println(classifier);
	}
	
	public static void deleteClassifier(String name) {
		DeleteClassifierOptions deleteClassifierOptions = new DeleteClassifierOptions.Builder(name).build();
		service.deleteClassifier(deleteClassifierOptions).execute();
	}
	public static void main(String args[]) throws FileNotFoundException {
		testPost.face_recognition("prez.jpg");
//		testPost.costomerize_test("fruitbowl.jpg", "IBM");
		testPost.costomerize_test("dogs.jpg", "me");
		//testPost.createClassifier();
		String val = testPost.getClassifier();
		System.out.println(val);
//		testPost.createClassifier();
//		System.out.println("-----------------------------");
		//testPost.deleteClassifier("newly_dogs_1245910926");
		//testPost.getClassifier();
		//testPost.getClassifier("dogs_1818370440");
	}
}