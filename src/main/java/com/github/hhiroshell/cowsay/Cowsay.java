package com.github.hhiroshell.cowsay;

import org.apache.commons.lang3.StringUtils;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cowsay {

    //
    @CommandLine.Parameters(hidden = true)
    private List<String> moosages = null;

    //
    @CommandLine.Option(names = "-b") private boolean cowModeB;
    @CommandLine.Option(names = "-d") private boolean cowModeD;
    @CommandLine.Option(names = "-g") private boolean cowModeG;
    @CommandLine.Option(names = "-p") private boolean cowModeP;
    @CommandLine.Option(names = "-s") private boolean cowModeS;
    @CommandLine.Option(names = "-t") private boolean cowModeT;
    @CommandLine.Option(names = "-w") private boolean cowModeW;
    @CommandLine.Option(names = "-y") private boolean cowModeY;

    @CommandLine.Option(names = {"-f"}, description = "cowfile")
    private String cowfile = Cowloader.DEFAULT_COW;

    @CommandLine.Option(names = {"-e"}, description = "eyes")
    private String eyes = null;

    @CommandLine.Option(names = {"-T"}, description = "tongue")
    private String tongue = null;

    @CommandLine.Option(names = {"-W"}, description = "word wrap")
    private String wordwrap = "0";

    @CommandLine.Option(names = {"-n"}, description = "no wrap")
    private boolean nowrap = false;

    @CommandLine.Option(names = {"-l"}, description = "list cows")
    private boolean listCows = false;

    @CommandLine.Option(names = {"-v", "--version"}, versionHelp = true, description = "display version info")
    private boolean versionInfoRequested = false;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
    private boolean usageHelpRequested = false;

    public static void main(String[] args) {
        Cowsay cowsay = new Cowsay();
        CommandLine commandLine = new CommandLine(cowsay);
        commandLine.parseArgs(args);

        if (commandLine.isUsageHelpRequested()) {
            commandLine.usage(System.out);
            return;
        }
        if (commandLine.isVersionHelpRequested()) {
            commandLine.printVersionHelp(System.out);
            return;
        }
        if (cowsay.listCows) {
            String[] files = Cowloader.listAllCowfiles();
            if (files != null) {
                System.out.println(StringUtils.join(files, System.getProperty("line.separator")));
            }
            return;
        }
        System.out.println(cowsay.say());
    }

    String say() {
        try {
            CowFace cowFace = CowFace.getByMode(this.getMode());
            if (cowFace == null) {
                // if we are in here no modes were set
                cowFace = getCowFace();
            }
            String cowTemplate = Cowloader.load(cowfile);
            if (cowTemplate != null) {
                if (moosages == null || moosages.isEmpty()) {
                    moosages = Arrays.asList(Cowsay.getPipedInput());
                }
                String moosage = StringUtils.join(this.moosages, " ");
                if (moosage != null && moosage.length() > 0) {
                    Message message = new Message(moosage, false);
                    message.setWordwrap(nowrap ? "0" : wordwrap);
                    return CowFormatter.formatCow(cowTemplate, cowFace, message);
                }
            }
        } catch (CowParseException e) {
            Logger.getLogger(Cowsay.class.getName()).log(Level.SEVERE, null, e);
        }
        return "";
    }

    private String getMode() {
        Map<String, Boolean> modeOptions = new HashMap<>(8);
        modeOptions.put("b", cowModeB);
        modeOptions.put("d", cowModeD);
        modeOptions.put("g", cowModeG);
        modeOptions.put("p", cowModeP);
        modeOptions.put("s", cowModeS);
        modeOptions.put("t", cowModeT);
        modeOptions.put("w", cowModeW);
        modeOptions.put("y", cowModeY);
        for (String key : modeOptions.keySet()) {
            if (modeOptions.get(key)) {
                return key;
            }
        }
        return null;
    }

    /**
     * Get a regular cow face optionally formatted with custom eyes and tongue from the command line.
     * @return A regular cowface, possibly formatted with custom tongue and/or eyes.
     */
    private CowFace getCowFace() {
        CowFace cowFace = new CowFace();
        if (this.eyes != null) {
            cowFace.setEyes(this.eyes);
        }
        if (this.tongue != null) {
            cowFace.setTongue(this.tongue);
        }
        return cowFace;
    }

   /**
    * Checks StdIn for piped input.
    * @return All lines from StdIn.
    */
   public static String[] getPipedInput() {
       List<String> messages = new ArrayList<>();
       try (InputStreamReader isr = new InputStreamReader(System.in)) {
           if (isr.ready()) {
               try (BufferedReader bufferedReader = new BufferedReader(isr)) {
                   String line;
                   while ((line = bufferedReader.readLine()) != null) {
                       messages.add(line);
                   }
               }
           }
       } catch (IOException ex) {
           Logger.getLogger(Cowsay.class.getName()).log(Level.WARNING, null, ex);
       }
       return messages.toArray(new String[messages.size()]);
   }

}
