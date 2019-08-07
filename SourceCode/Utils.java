import java.util.Scanner;

public class Utils {
    public static boolean get_yes_no() {
        while (true) {
            Scanner scan = new Scanner(System.in);
            String player_input = scan.next();

            player_input = player_input.toLowerCase();

            if (player_input.equals("yes")) {
                return true;
            } else if (player_input.equals("no")) {
                return false;
            } else {
                System.out.println("Valid responses are \"yes\" and \"no\".");
            }
        }
    }

    public static String get_character_string(String space_to_match, char c) {
        String whitespace = "";
        for (int i = 0; i < space_to_match.length(); i++) {
            whitespace += c;
        }

        return whitespace;
    }

    public static String get_whitespace_of_length(int num_whitespaces) {
        String whitespace = "";
        for (int i = 0; i < num_whitespaces; i++) {
            whitespace += " ";
        }

        return whitespace;
    }
}