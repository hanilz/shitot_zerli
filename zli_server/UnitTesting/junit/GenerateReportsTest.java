package junit;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entities.Report;
import util.DataBaseController;
import util.GenerateReports;

class GenerateReportsTest {

	private Report reportExistingBranch;
	private Report reportNotExistingBranch;
	private Report diffReportType;
	private Report reportNoData;

	private Map<String, Integer> existingBranchData = new HashMap<>();
	private Map<String, Integer> complaintsData = new HashMap<>();

	@BeforeEach
	void setUp() throws Exception {
		reportExistingBranch = new Report("'2022-04-01 00:00:00' and '2022-06-29 00:00:00'", "income histogram", 1);
		reportNotExistingBranch = new Report("'2022-04-01 00:00:00' and '2022-06-31 00:00:00'", "income histogram", 4);
		diffReportType = new Report("'2022-04-01 00:00:00' and '2022-06-29 00:00:00'", "complaints", 1);
		reportNoData = new Report("'2022-01-01 00:00:00' and '2022-03-29 00:00:00'", "income histogram", 3);
		
		existingBranchData = getReportExistingBranchData();
		complaintsData = getComplaintsData();
		DataBaseController.defultConnect();
	}

	//checking functionality: getting data of an existing income histogram report
	//input data: object Report(DateRange: '2022-04-01 00:00:00' and '2022-06-29 00:00:00', ReportType: income histogram, idBranch = 1)
	//expected result: HashMap<String, Integer> with data: (April, 25324), (May, 254092), (June, 19387)
	@Test
	void getIncomeHistogramReport_ExistingBranch() {
		Map<String, Integer> expected = existingBranchData;
		Map<String, Integer> result = GenerateReports.getIncomeHistogramReport(reportExistingBranch);
		assertEquals(expected, result);
	}

	//checking functionality: getting income histogram from non existing branch
	//input data: object Report(DateRange: '2022-04-01 00:00:00' and '2022-06-31 00:00:00', ReportType: income histogram, idBranch = 4)
	//expected result: empty HashMap<String, Integer>
	@Test
	void getIncomeHistogramReport_BranchIsNotExist() {
		Map<String, Integer> expected = new HashMap<>();
		Map<String, Integer> result = GenerateReports.getIncomeHistogramReport(reportNotExistingBranch);
		assertEquals(expected, result);
	}

	//checking functionality: getting income histogram from null report object
	//input data: object Report(null)
	//expected result: NullPointerException
	@Test
	void getIncomeHistogramReport_ReportIsNull() {
		try {
			GenerateReports.getIncomeHistogramReport(null);
			fail("Did not caught the null pointer exception!");
		} catch (NullPointerException e) {
		}
	}

	//checking functionality: getting income histogram from different type of report
	//input data: object Report(DateRange: '2022-04-01 00:00:00' and '2022-06-29 00:00:00', ReportType: complaints, idBranch = 1)
	//expected result: empty HashMap<String, Integer>
	@Test
	void getIncomeHistogramReport_diffrenetReportType() {
		Map<String, Integer> expected = complaintsData;
		Map<String, Integer> result = GenerateReports.getIncomeHistogramReport(diffReportType);
		assertNotEquals(expected, result);
	}
	
	//checking functionality: getting income histogram from report with no existing data
	//input data: object Report(DateRange: '2022-01-01 00:00:00' and '2022-03-29 00:00:00', ReportType: income histogram, idBranch = 3)
	//expected result: empty HashMap<String, Integer>
	@Test
	void getIncomeHistogramReport_reportWithNoData() {
		Map<String, Integer> expected = new HashMap<>();
		Map<String, Integer> result = GenerateReports.getIncomeHistogramReport(reportNoData);
		assertEquals(expected, result);
	}

	private Map<String, Integer> getReportExistingBranchData() {
		Map<String, Integer> reportData = new HashMap<>();
		reportData.put("April", (int) 25324.03);
		reportData.put("May", (int) 254092.14);
		reportData.put("June", (int) 19387.28);
		return reportData;
	}

	private Map<String, Integer> getComplaintsData() {
		Map<String, Integer> reportData = new HashMap<>();
		reportData.put("April", 5);
		reportData.put("May", 6);
		reportData.put("June", 13);
		return reportData;
	}

}
