package com.krisczar.neptun;

//import com.fasterxml.jackson.annotation.JsonCreator;
//import com.fasterxml.jackson.annotation.JsonProperty;

public class TestResult {
	
	public TestResult() {
	}

	private Long id;

	private String beforeAnswers; //Phase 1

	private String afterAnswers; //Phase 2

	private Long shortestPath;

	private Long realPath;

	private Long deviation;

	private Long maxDeviation;

	private Long integralU;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBeforeAnswers() {
		return beforeAnswers;
	}

	public void setBeforeAnswers(String beforeAnswers) {
		this.beforeAnswers = beforeAnswers;
	}

	public String getAfterAnswers() {
		if (afterAnswers == null)
			return "";
		else
			return afterAnswers;
	}

	public void setAfterAnswers(String afterAnswers) {
		this.afterAnswers = afterAnswers;
	}

	public Long getShortestPath() {
		return shortestPath;
	}

	public void setShortestPath(Long shortestPath) {
		this.shortestPath = shortestPath;
	}

	public Long getRealPath() {
		return realPath;
	}

	public void setRealPath(Long realPath) {
		this.realPath = realPath;
	}

	public Long getDeviation() {
		return deviation;
	}

	public void setDeviation(Long deviation) {
		this.deviation = deviation;
	}

	public Long getMaxDeviation() {
		return maxDeviation;
	}

	public void setMaxDeviation(Long maxDeviation) {
		this.maxDeviation = maxDeviation;
	}

	public Long getIntegralU() {
		return integralU;
	}

	public void setIntegralU(Long integralU) {
		this.integralU = integralU;
	}

}
