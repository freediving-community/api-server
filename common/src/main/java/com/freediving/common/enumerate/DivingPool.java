package com.freediving.common.enumerate;

public enum DivingPool {

	DEEPSTATION("딥스테이션"),
	PARADIVE("파라다이브"),
	K26("k-26"),
	THEME_SCUBA_POOL("테마 잠수풀(TSN)"),
	OLYMPIC_DIVING_POOL("올림픽수영장 다이빙풀"),
	SONGDO_SPORTS_PARK_POOL("송도 스포츠파크 잠수풀"),
	SEONGNAM_AQUALINE_POOL("성남 아쿠아라인 다목적풀"),
	WORLDCUP_SCUBA_POOL("월드컵 스킨스쿠버 다이빙풀"),
	DAEBU_WELFARE_POOL("대부동 복지체육센터");

	private String divingPoolName;

	DivingPool(String divingPoolName) {
		this.divingPoolName = divingPoolName;
	}
}
