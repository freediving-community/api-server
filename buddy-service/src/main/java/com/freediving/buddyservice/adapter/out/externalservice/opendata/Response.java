package com.freediving.buddyservice.adapter.out.externalservice.opendata;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class Response {

	@XmlElement(name = "header")
	private Header header;

	@XmlElement(name = "body")
	private Body body;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Header {
		@XmlElement(name = "resultCode")
		private String resultCode;

		@XmlElement(name = "resultMsg")
		private String resultMsg;

		public String getResultCode() {
			return resultCode;
		}

		public void setResultCode(String resultCode) {
			this.resultCode = resultCode;
		}

		public String getResultMsg() {
			return resultMsg;
		}

		public void setResultMsg(String resultMsg) {
			this.resultMsg = resultMsg;
		}
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Body {
		@XmlElementWrapper(name = "items")
		@XmlElement(name = "item")
		private List<Item> items;

		public List<Item> getItems() {
			return items;
		}

		public void setItems(List<Item> items) {
			this.items = items;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		public static class Item {
			@XmlElement(name = "dateKind")
			private String dateKind;

			@XmlElement(name = "dateName")
			private String dateName;

			@XmlElement(name = "isHoliday")
			private String isHoliday;

			@XmlElement(name = "locdate")
			private int locdate;

			@XmlElement(name = "seq")
			private int seq;

			public String getDateKind() {
				return dateKind;
			}

			public void setDateKind(String dateKind) {
				this.dateKind = dateKind;
			}

			public String getDateName() {
				return dateName;
			}

			public void setDateName(String dateName) {
				this.dateName = dateName;
			}

			public String getIsHoliday() {
				return isHoliday;
			}

			public void setIsHoliday(String isHoliday) {
				this.isHoliday = isHoliday;
			}

			public int getLocdate() {
				return locdate;
			}

			public void setLocdate(int locdate) {
				this.locdate = locdate;
			}

			public int getSeq() {
				return seq;
			}

			public void setSeq(int seq) {
				this.seq = seq;
			}
		}
	}
}
