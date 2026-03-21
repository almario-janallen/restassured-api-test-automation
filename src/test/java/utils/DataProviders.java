package utils;

import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "createUserData")
    public static Object[][] createUserData() {
        return CsvDataReader.read("testdata/create_user.csv");
    }

    @DataProvider(name = "loginValidData")
    public static Object[][] loginValidData() {
        return CsvDataReader.read("testdata/login_valid.csv");
    }

    @DataProvider(name = "loginInvalidData")
    public static Object[][] loginInvalidData() {
        return CsvDataReader.read("testdata/login_invalid.csv");
    }

    @DataProvider(name = "registerValidData")
    public static Object[][] registerValidData() {
        return CsvDataReader.read("testdata/register_valid.csv");
    }

    @DataProvider(name = "registerInvalidData")
    public static Object[][] registerInvalidData() {
        return CsvDataReader.read("testdata/register_invalid.csv");
    }
}