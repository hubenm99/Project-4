import java.io.*;
import java.io.File;
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
            badWordsFileName = "badwords.txt";
            // creates a buffered reader by for the bad words text file and message
            file = new File(badWordsFileName).getAbsoluteFile();
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

            ArrayList<String> badWords = new ArrayList<>();
            String[] messageWords;

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                badWords.add(line);
            }

            for (int i = 0; i < badWords.size(); i++) {


                if (msg.contains(badWords.get(i))) {
                    for (int k = 0; k < badWords.get(i).length(); k++) {
                        filter += "*";
                    }

                    returnMessage = msg.replaceAll(badWords.get(i), filter);
                }
//                    } else if (!returnMessage.contains(messageWords[j])) {
//                        returnMessage += messageWords[j];
//                    }
                filter = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMessage;
    }
}