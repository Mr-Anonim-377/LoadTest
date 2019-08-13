package com.ruSkidexLoadTest.TestControl;



import com.ruSkidexLoadTest.TestGo.CompaniesAndBrandsData;
import com.ruSkidexLoadTest.TestGo.PaperCatalogAndProductData;
import org.testng.TestNG;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;




public class Main {
     static Class[] classesTests =  new Class[] {PaperCatalogAndProductData.class,CompaniesAndBrandsData.class};


    @BeforeSuite
    public  void start() throws IOException {


    }
    @AfterSuite
    public void stop () throws IOException {


    }

    public static void main(String[] args) {
        TestNG testng = new TestNG();

        testng.setTestClasses(classesTests);
        testng.run();



    }
}
