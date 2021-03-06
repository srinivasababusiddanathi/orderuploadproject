
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.progresssoft.fileupload.dao.Order;

public class FlleReaderTestCase1 {
	
	/*  This test class displays time taken to read from the input file located in the directory ./src/test/java/testdata/test_file_1.csv 
	 *  This class doesn't save into database
	 * 
	 * 
	 */

	public static Function<String, Order> mapToOrder = (line) -> {
		String[] p = line.split(",");
		if(p.length <5 || p.length > 5){
			return new Order(null,null,null,null,0,Arrays.deepToString(p));
	    }
		return new Order(p[0], p[1], p[2], p[3], Float.parseFloat(p[4]),null);
	};

	public static void main(String[] args) {
		
		String fileName = "./src/test/java/testdata/test_file_1.csv";
		List<Order> orders = null;
		java.util.Date date = new java.util.Date();
		Timestamp timestamp1 = new Timestamp(date.getTime());
		try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
			orders =  lines.parallel().map(mapToOrder).collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
			}
       	
		java.util.Date date2 = new java.util.Date();
		Timestamp timestamp2 = new Timestamp(date2.getTime());
		float seconds = Long.valueOf(timestamp2.getTime() - timestamp1.getTime()).floatValue() / 1000;
		System.out.println("Time taken to read file having " +orders.size()+ " records  in seconds is  " + seconds);

	}

}
