import java.io.*;
import java.util.ArrayList;

public class ChatFilter {
    private String badWordsFileName;

    public ChatFilter(String badWordsFileName) {
        this.badWordsFileName = badWordsFileName;
    }

    public String filter(String msg) {
        String returnMessage= "";
        File file;
        FileReader fileReader;
        BufferedReader bufferedReader;

        try {
            String filter = "";

            // creates a buffered reader by for the bad words text file and message
            file = new File("NickHuber/IdeaProjects/CS180/Project4/src/badwords.txt");
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

            ArrayList<String> badWords = new ArrayList<>();
            String[] messageWords;

            while (bufferedReader.readLine() != null) {
                badWords.add(bufferedReader.readLine());
            }
            messageWords = msg.split(msg);


            for (int i = 0; i < msg.length(); i++) {
                for (int j = 0; j < messageWords.length; j++) {
                    if (messageWords[j].equals(badWords.get(i))) {
                        for (int k = 0; k < messageWords[j].length(); k++) {
                            filter += "*";
                        }
                        returnMessage += filter;
                    } else {
                        returnMessage += messageWords[j];
                    }
                    filter = "";
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMessage;
    }
}