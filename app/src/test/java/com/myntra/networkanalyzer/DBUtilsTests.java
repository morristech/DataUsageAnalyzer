package com.myntra.networkanalyzer;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import java.util.ArrayList;

/**
 * Created by c.sivasubramanian on 09/10/16.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class DBUtilsTests {

    private static final String DATABASE_NAME = "DataUsageDetails.db";
    private DBHelper dbHelper;
    private ShadowApplication context;

    @Before
    public void setup()
    {
        context = Shadows.shadowOf(RuntimeEnvironment.application);
        dbHelper = new DBHelper(context.getApplicationContext());
        setDataInDB();
    }

    @Test
    public void DbCreationTest() {
        Assert.assertEquals(DATABASE_NAME, dbHelper.getDatabaseName());
    }

    @Test
    public void DbInsertionTest(){
       Assert.assertEquals("The insert operation not happening properly",2,dbHelper.getAllRecords().size());
    }

    @Test
    public void DbDeletionTest(){
        dbHelper.deleteAllContents();
        Assert.assertEquals("The delete operation not happening properly", 0, dbHelper.getAllRecords().size());
    }

    @Test
    public void DbCheckForValidAppNameTest(){
        Assert.assertTrue(dbHelper.checkForApp("SampleApp2"));
    }

    @Test
    public void DbCheckForNullAppNameTest(){
        Assert.assertEquals(false, dbHelper.checkForApp(null));
    }

    @Test
    public void DbCheckForEmptyAppNameTest(){
        Assert.assertEquals(false, dbHelper.checkForApp(""));
    }

    @Test
    public void DbCheckForBlankAppNameTest(){
        Assert.assertEquals(false, dbHelper.checkForApp("    "));
    }

    @Test
    public void updateDatausedFieldTests(){
        dbHelper.updateDateUsageDetail("SampleApp1", "08-10", 30.00);
        DataUsage dataUsage =dbHelper.getAllRecords().get(0);
        Assert.assertEquals(3.00,dataUsage.getDateUsed());
    }

    @Test
    public void fetchRecordsTest(){
        ArrayList<DataUsage> list = dbHelper.getAllRecords();
        Assert.assertEquals("SampleApp1",list.get(0).getAppName());
        Assert.assertEquals("SampleApp2",list.get(1).getAppName());
    }


    public void setDataInDB()
    {
        dbHelper.insertDataUsageDetail("SampleApp1", "08-10", 20.15);
        dbHelper.insertDataUsageDetail("SampleApp2", "09-10", 40.15);
    }

}
