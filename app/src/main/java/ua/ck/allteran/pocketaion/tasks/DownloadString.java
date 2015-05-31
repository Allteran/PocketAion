package ua.ck.allteran.pocketaion.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Allteran on 5/28/2015.
 */
public class DownloadString {
    private String mDownloadedString;

    public DownloadString(String urlAddress) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(urlAddress);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String buffString;
            while ((buffString = bufferedReader.readLine()) != null)
                stringBuilder.append(buffString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mDownloadedString = stringBuilder.toString();
    }

    public String getDownloadedString() {
        return mDownloadedString;
    }

}
