package simple.packages.spring.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NickName implements Serializable {

	public String first;
	public String last;
	
	public NickName() {
			super();
	}
	
	/*
	example of using the @JsonCreator annotation to customize the constructor 
	used during deserialization
	//////////////////////
	putting json on constructor vs on fields or sphagetti spread out to methods???
	*/
	@JsonCreator
	public NickName(
		@JsonProperty("first_nick_name")  String first, 
		@JsonProperty("last_nick_name")  String last) {
		
		super();
		this.first = first;
		this.last = last;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	@Override
	public String toString() {
		return "NickName [first=" + first + ", last=" + last + "]";
	}
	
}
