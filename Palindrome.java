import java.util.Arrays;
import java.util.Collections;

class Palindrome {
		String sentence;
		char[] charArray; 
		char letter;
		Object matcher;
		LinkedStack<Character> charStackForward = new LinkedStack<Character>();
		LinkedStack<Character> charStackReverse = new LinkedStack<Character>();
	
		public Palindrome(String string) {
			this.sentence = string;
		}
		
		public void test() {
			System.out.println(this.sentence);
			sentence = sentence.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
			charArray = sentence.toCharArray();
//			System.out.println(charArray.length);

			// forward

			for (int i = 0; (charArray.length) > i; i++) {
//				System.out.println(charArray[i]);
				letter = charArray[i];
				charStackForward.push(letter);
			}

			// reverse

			for (int i = (charArray.length) - 1; i >= 0; i--) {
//				System.out.println(charArray[i]);
				letter = charArray[i];
				charStackReverse.push(letter);
			}
			
			matcher(0, charStackForward, charStackReverse);
		}
			
		public void matcher(int matchesSoFar, LinkedStack charStackForward, LinkedStack charStackReverse) {
			int matches = matchesSoFar;
			for (int i = 0; charStackForward.size() > i; i++) {
				matcher = charStackForward.peek();
//				System.out.print("letter to match: " + matcher);
//				System.out.println(", letter to match against: " + charStackReverse.peek());
				if (matcher != charStackReverse.peek()) {
					System.out.println("this is not a palindrome");
					break;
				} else if (matcher == charStackReverse.peek()) {
					// look at the next link and run the matcher again
					matches++;
//					System.out.println(matches);
					charStackForward.pop();
					charStackReverse.pop();
					matcher(matches, charStackForward, charStackReverse);
				}
			}
			if (matches == charArray.length - 1) {
				System.out.println("Its a palindrome");
			}
		}
			
		public static void main(String[] args) {
			Palindrome palindrome = new Palindrome("Able was I ere I saw Elba"); 
			Palindrome palindrome2 = new Palindrome("radar"); 
			Palindrome palindrome3 = new Palindrome("A but tuba."); 
			Palindrome palindrome4 = new Palindrome("In public in private by Edin Denby."); 
			Palindrome palindrome5 = new Palindrome("Morning \n (ripped out of my mind again!)"); 
			palindrome.test();
			palindrome2.test();
			palindrome3.test();
			palindrome4.test();
			palindrome5.test();
		}
	}