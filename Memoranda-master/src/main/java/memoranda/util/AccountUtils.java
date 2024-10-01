package main.java.memoranda.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.lang.String;
import java.lang.StringBuilder;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;

/*$Id: AccountUtils.java, v1.0 2024/02/08 jrfeathe Exp $*/
public class AccountUtils {

    public enum Rank {
        INSTRUCTOR("INSTRUCTOR"),
        GRADER("GRADER"),
        TA("TA"),
        STUDENT("STUDENT");
        private final String rankName;

        Rank(String rankName) {
            this.rankName = rankName;
        }

        public String toString() {
            return rankName;
        }
    }

    public static final String usernameKey = "USERNAME";
    public static final String passwordKey = "PASSWORD";
    public static final String permissionKey = "RANK";
    public static final String nullKey = "NULL";
    public static final String defaultFilename = "accounts.json";
    public static final String defaultCurrUserFile = "currentUser.json";

    /**
     * Gets the data of an account.
     * @param data The entire list of accounts
     * @param username The user to retrieve
     * @return The account, or an empty account containing {"NULL": true} when it is not found
     */
    public static JSONObject getUser(JSONArray data, String username) {
        JSONObject jsonAccount = new JSONObject();
        // Check the array and locate a user with the username from parameters
        for (int i = 0; i < data.length(); i++) {
            if (data.getJSONObject(i).has(usernameKey)
                && data.getJSONObject(i).has(passwordKey)
                && data.getJSONObject(i).has(permissionKey)) {

                String thisUser = data.getJSONObject(i).getString(usernameKey);
                if (thisUser.equalsIgnoreCase(username)) {
                    jsonAccount = data.getJSONObject(i);
                    return jsonAccount;
                }

            } else {
                if (!data.getJSONObject(i).has(usernameKey)) {
                    System.out.println("Item " + i + " of data does not contain " + usernameKey);
                }
                if (!data.getJSONObject(i).has(passwordKey)) {
                    System.out.println("Item " + i + " of data does not contain " + passwordKey);
                }
                if (!data.getJSONObject(i).has(permissionKey)) {
                    System.out.println("Item " + i + " of data does not contain " + permissionKey);
                }
            }
        }
        jsonAccount.put(nullKey, true);   // Return an account with only flag NULL set to true
        System.out.println("Could not find user in file.");
        return jsonAccount;
    }

    /**
     * Reads a file and outputs a JSONArray of the data.
     * @param filename The file to read
     * @return JSONArray contained in file, or empty array if error
     */
    public static JSONArray readFile(String filename) {

        JSONArray jsonArray;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename, StandardCharsets.UTF_8))) {
            StringBuilder jsonStringBuilder = new StringBuilder();
            String line;

            // Read each line and append to string builder
            while ((line = reader.readLine()) != null) {
                jsonStringBuilder.append(line);
            }

            // Create JSONArray from this built string
            jsonArray = new JSONArray(jsonStringBuilder.toString());
            return jsonArray;

        } catch (FileNotFoundException e) {
            System.out.println("File \"" + filename + "\" does not exist.");
        } catch (IOException e) {
            System.out.println("Could not read " + filename);
        }
        jsonArray = new JSONArray();
        return jsonArray;
    }

    /**
     * Reads the specified file and returns the data of the specified user.
     * @param username The user to search for
     * @param filename The file to search in
     * @return The account, or an empty account containing {"NULL": true} when it is not found
     */
    public static JSONObject readFile(String username, String filename) {

        JSONObject jsonAccount = new JSONObject();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename, StandardCharsets.UTF_8))) {
            StringBuilder jsonStringBuilder = new StringBuilder();
            String line;

            // Read each line and append to string builder
            while ((line = reader.readLine()) != null) {
                jsonStringBuilder.append(line);
            }

            // Create JSONArray from this built string
            JSONArray jsonArray = new JSONArray(jsonStringBuilder.toString());

            // Check the array and locate a user with the username from parameters
            jsonAccount = getUser(jsonArray, username);
            return jsonAccount;

        } catch (FileNotFoundException e) {
            System.out.println("File \"" + filename + "\" does not exist.");
        } catch (IOException e) {
            System.out.println("Could not read " + filename);
        }

        jsonAccount.put(nullKey, true);   // Return an account with only flag NULL set to true
        System.out.println("Could not open file.");
        return jsonAccount;
    }

    /**
     * Reads the specified file and returns the rank of the current user.
     * @param filename The file to search in
     * @return The rank of the current user
     */
    public static Rank readRank(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            StringBuilder jsonStringBuilder = new StringBuilder();
            String line;

            // Read each line and append to string builder
            while ((line = reader.readLine()) != null) {
                jsonStringBuilder.append(line);
            }

            // Create JSON object from this built string
            JSONObject jsonAccount = new JSONObject(jsonStringBuilder.toString());
            Rank rank = Rank.STUDENT;
            if (jsonAccount.has("RANK")) {
                rank = Rank.valueOf(jsonAccount.getString("RANK"));
            }

            return rank;

        } catch (FileNotFoundException e) {
            System.out.println("File \"" + filename + "\" does not exist.");
        } catch (IOException e) {
            System.out.println("Could not read " + filename);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid rank found in the file " + filename);
        }

        System.out.println("Could not open file.");
        return Rank.STUDENT; // return a default rank, adjust according to your preference
    }

    /**
     * Saves the JSONArray to a file.
     * @param accountList The JSONArray to save
     * @param filename The file to save the data
     */
    public static void writeFile(JSONArray accountList, String filename) {
        try (FileWriter fileWriter = new FileWriter(filename)) {

            fileWriter.write(accountList.toString());
        } catch (IOException e) {
            System.out.println("Error saving accounts to file.");
        }
    }

    /**
     * Saves the JSONObject to a file.
     * @param accountObj The JSONObject to save
     * @param filename The file to save the data
     */
    public static void writeFile(JSONObject accountObj, String filename) {
        try (FileWriter fileWriter = new FileWriter(filename)) {

            fileWriter.write(accountObj.toString());
        } catch (IOException e) {
            System.out.println("Error saving account to file.");
        }
    }

    /**
     * Converts a string representation of a rank to the corresponding enum value.
     * @param input the string representation of the rank
     * @return the enum value representing the rank, or STUDENT if the input is not valid
     */
    public static AccountUtils.Rank toEnum(String input) {

        AccountUtils.Rank output;
        switch (input) {
            default:
                System.out.println("\n\n[DEBUG] Error converting string to Visibility enum\n\n");
                // No break --> output student
            case "STUDENT": case "student": case "Student":
                output = AccountUtils.Rank.STUDENT;
                break;
            case "GRADER": case "grader": case "Grader":
                output = AccountUtils.Rank.GRADER;
                break;
            case "TA": case "ta": case "Ta":
                output = AccountUtils.Rank.TA;
                break;
            case "INSTRUCTOR": case "instructor": case "Instructor":
                output = AccountUtils.Rank.INSTRUCTOR;
                break;
        }
        return output;
    }

    /**
     * Checks if a viewer with a given rank has permission to view an item with a given rank.
     * @param viewerRank The rank of the viewer
     * @param itemRank The rank of the item
     * @return true if the viewer has permission to view the item, false otherwise
     */
    public static boolean hasPermission(AccountUtils.Rank viewerRank, AccountUtils.Rank itemRank) {
        if (viewerRank == itemRank) {
            return true;
        } else {
            if (viewerRank == AccountUtils.Rank.STUDENT) {
                return false;
            } else if (viewerRank == AccountUtils.Rank.TA
                || viewerRank == AccountUtils.Rank.GRADER) {
                if (itemRank == AccountUtils.Rank.STUDENT) {
                    return true;
                } else {
                    return false;
                }
            } else if (viewerRank == AccountUtils.Rank.INSTRUCTOR) {
                return true;
            } else {
                return false;
            }
        }
    }
}
