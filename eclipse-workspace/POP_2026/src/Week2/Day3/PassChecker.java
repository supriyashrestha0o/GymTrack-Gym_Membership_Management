package Week2.Day3;
public class PassChecker {
	public static void main(String[] args) {
		int score = 65;
		boolean result = isPass(score);
		System.out.println("Did the student pass? " + result);
	    }
	public static boolean isPass(int score) {
		if (score >= 50) {
			return true;
			} else {
				return false;
				}
		}
}
