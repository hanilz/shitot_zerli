package reportTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entities.Report;
import report.ReportsController;

class ReportsControllerTest {

	private Report incomeHistogramReport;
	private Report diffReportType;
	private Report emptyReport;
	private Report emptyReportNoDate;
	
	private Report existingQuarterReport;
	private Report notExistingQuarterReport;
	
	private static ReportsController reportsStaticController;
	private ReportsController reportsController;
	
	private Method testSetDateRangeQuarterForQuery;

	@BeforeEach
	void setUp() throws Exception {
		incomeHistogramReport = new Report("'2022-04-01 00:00:00' and '2022-06-29 00:00:00'", "income histogram", 1);
		diffReportType = new Report("'2022-04-01 00:00:00' and '2022-04-29 00:00:00'", "orders", 1);
		emptyReport = new Report("","",1);
		existingQuarterReport = new Report(2,"2022-06-29");
		notExistingQuarterReport = new Report(5,"2022-06-29");
		emptyReportNoDate = new Report(1, "");
		
		reportsStaticController = new ReportsController();
		reportsController = new ReportsController();
		
		testSetDateRangeQuarterForQuery = ReportsController.class.getDeclaredMethod("setDateRangeQuarterForQuery",
				Report.class);
		testSetDateRangeQuarterForQuery.setAccessible(true);
	}

	// checking functionality: check if true when the report type is income histogram
	// input data: object Report(DateRange: '2022-04-01 00:00:00' and '2022-06-29 00:00:00', Report Type: income histogram, idBranch: 1)
	// expected result: true
	@Test
	void isReportHistogram_sameType() {
		boolean result = reportsController.isReportHistogram(incomeHistogramReport);
		assertTrue(result);
	}

	// checking functionality: check if false when the report type is orders
	// input data: object Report(DateRange: '2022-04-01 00:00:00' and '2022-04-29 00:00:00', Report Type: orders, idBranch: 1)
	// expected result: false
	@Test
	void isReportHistogram_diffrenetType() {
		boolean result = reportsController.isReportHistogram(diffReportType);
		assertFalse(result);
	}
	
	// checking functionality: check if throw exception for report null
	// input data: object Report(null)
	// expected result: NullPointerException
	@Test
	void isReportHistogram_reportNull() {
		try {
			reportsController.isReportHistogram(null);
			fail("The method did not throw null pointer exception");
		} catch(NullPointerException e) {}
	}
	
	// checking functionality: check if false when the report type is empty string
	// input data: object Report(DateRange: "", ReportType: "" ,idBranch: 1)
	// expected result: false
	@Test
	void isReportHistogram_reportEmpty() {
		boolean result = reportsController.isReportHistogram(emptyReport);
		assertFalse(result);
	}
	
	// checking functionality: check the dateToString of quarter 2 is between April-June
	// input data: object Report(dateToString: "2022-06-29", quarter: 2)
	// expected result: string "'2022-04-01 00:00:00' and '2022-06-29 00:00:00'"
	@Test
	void setDateRangeQuarterForQuery_existingQuarterReport() {
		try {
			String expected = "'2022-04-01 00:00:00' and '2022-06-29 00:00:00'";
			String result = (String)testSetDateRangeQuarterForQuery.invoke(reportsStaticController, existingQuarterReport);
			assertEquals(expected, result);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	// checking functionality: check if the dateToString is empty for non existing quarter
	// input data: object Report(dateToString: "2022-06-29", quarter: 5)
	// expected result: empty string
	@Test
	void setDateRangeQuarterForQuery_noteExistingQuarter() {
		try {
			String expected = "";
			String result = (String)testSetDateRangeQuarterForQuery.invoke(reportsStaticController, notExistingQuarterReport);
			assertEquals(expected, result);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	// checking functionality: check if the dateToString is empty for existing quarter
	// input data: object Report(dateToString: "", quarter: 1)
	// expected result: empty string
	@Test
	void setDateRangeQuarterForQuery_emptyReportWithNoDate() {
		try {
			String expected = "";
			String result = (String)testSetDateRangeQuarterForQuery.invoke(reportsStaticController, emptyReportNoDate);
			assertEquals(expected, result);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
		}
	}
	
	// checking functionality: check if throw exception for null report
	// input data: object Report(null)
	// expected result: NullPointerException
	@Test
	void setDateRangeQuarterForQuery_nullReport() {
		try {
			testSetDateRangeQuarterForQuery.invoke(reportsStaticController, (Object)null);
			fail("This method did not throw a NullPointerException!");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
		}
	}

}