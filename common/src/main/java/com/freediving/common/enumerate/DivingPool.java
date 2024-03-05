package com.freediving.common.enumerate;

public enum DivingPool {

	DEEPSTATION("딥스테이션"),
	PARADIVE("파라다이브"),
	JAMSIL_DIVING_POOL("잠실 다이빙 풀"),
	DIVELIFE("다이브라이프");

	private String divingPoolName;

	DivingPool(String divingPoolName) {
		this.divingPoolName = divingPoolName;
	}
}
