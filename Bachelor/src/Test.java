
public class Test {
	public static void min(String[]args) {
		boolean right_start = false;
		boolean horizontal = false;
		 String[] text = {"0 5 0 0 2 6 3 6 1 2 7 2 5 6 5 4 7",
				 "0 7 1 1 1 2 7 4 7",
				 "0 3 1 0 3 2 7 2 5 4 7 4 3 4 1 6 3 6 1",
				 "2 7 0 0 2 4 1 4 7 0 5 0 7 0 3 6 1",
				 "2 1 1 0 1 4 1 6 1",
				 "2 5 1 1 3 6 3 6 5 6 1 0 5 0 3 4 1 4 7",
				 "4 1 0 0 2 2 5 2 7 2 1 6 3 6 1 0 3",
				 "4 3 1 0 1 6 3 0 3",
				 "4 7 1 1 3 2 7 2 5 0 5 0 3 0 7 6 3 6 1"};
		 for (int i = 0; i < text.length; i ++) {
			 String[] parts = text[i].split("\\s");
			 for (int j = 0; j < parts.length; j ++ ) {
				 if (!(j == 2 || j == 3 || j == 4)) {
					 System.out.print(Integer.parseInt(parts[j])+16 + " ");
				 } else {
					 System.out.print(parts[j] + " ");
				 }
				 
			 }
			 System.out.println();
		 }
		
		
		if (horizontal) {
			if (right_start) {

			}else {

			}
		} else {
			if (right_start) {

			} else {

			}
		}
		
		
		
		
		
	}
}
