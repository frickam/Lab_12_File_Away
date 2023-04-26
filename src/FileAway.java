import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFileChooser;
public class FileAway
{
    public static void main(String[] args)
    {
        JFileChooser chooser = new JFileChooser();
        String rec = "";
        ArrayList<String> lines = new ArrayList();
        int FIELDS_LENGTH = 0;

        try {
            File workingDirectory = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(workingDirectory);
            if (chooser.showOpenDialog((Component)null) == 0) {
                File selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();
                InputStream in = new BufferedInputStream(Files.newInputStream(file, StandardOpenOption.CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                int line = 0;

                while(reader.ready()) {
                    rec = reader.readLine();
                    lines.add(rec);
                    ++line;
                    System.out.printf("\nLine %4d %-60s ", line, rec);
                }

                reader.close();
                System.out.println("\n\nData file read!");
                Iterator var17 = lines.iterator();

                while(var17.hasNext()) {
                    String l = (String)var17.next();
                    String[] fields = l.split(",");
                    if (fields.length == 5) {
                        String id = fields[0].trim();
                        String firstName = fields[1].trim();
                        String lastName = fields[2].trim();
                        String title = fields[3].trim();
                        int yob = Integer.parseInt(fields[4].trim());
                        System.out.printf("\n%-8s%-25s%-25s%-6s%6d", id, firstName, lastName, title, yob);
                    } else {
                        System.out.println("Found a record that may be corrupt: ");
                        System.out.println(l);
                    }
                }
            } else {
                System.out.println("Failed to choose a file to process");
                System.out.println("Run the program again!");
                System.exit(0);
            }
        } catch (FileNotFoundException var19) {
            System.out.println("File not found!!!");
            var19.printStackTrace();
        } catch (IOException var20) {
            var20.printStackTrace();
        }
    }
}
